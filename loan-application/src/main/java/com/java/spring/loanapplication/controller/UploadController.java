package com.java.spring.loanapplication.controller;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.java.spring.loanapplication.entity.Address;
import com.java.spring.loanapplication.entity.LoanDetails;
import com.java.spring.loanapplication.entity.ProfessionalDetails;
import com.java.spring.loanapplication.entity.User;
import com.java.spring.loanapplication.helper.DocumentProcessor;
import com.java.spring.loanapplication.repository.UserRepository;

@RestController
@CrossOrigin
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	private Map<String, String> dataMap = null;

	@Autowired
	private DocumentProcessor processot;

	@Autowired
	private UserRepository repository;

	@Value("${file.dir.path.upload}")
	private String UPLOAD_DIR_PATH;

	@GetMapping("/upload")
	public String showUpload() {
		return "upload";
	}

	@GetMapping("/uploadStatus")
	public String uploadStatus() {
		return "uploadStatus";
	}

	@PostMapping("/uploadFile")
	public RedirectView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws InvalidFormatException, IllegalArgumentException, IllegalAccessException, ParseException {

		logger.info("singleFileUpload");

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to  upload");
			new RedirectView("/success", true);
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOAD_DIR_PATH + file.getOriginalFilename());
			Files.write(path, bytes);

			logger.info("Filename : " + file.getOriginalFilename());

			processDocument();

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RedirectView("/success", true);
	}

	public void processDocument() throws IOException, InvalidFormatException, IllegalAccessException, ParseException {
		List<Map<Integer, String>> list = processot.processDocument(UPLOAD_DIR_PATH + "TestDocument.xlsx");

		List<User> users = getUserDetails(list);

		for (User user : users)
			repository.save(user);
	}

	private List<User> getUserDetails(List<Map<Integer, String>> list)
			throws IllegalArgumentException, IllegalAccessException, ParseException {

		Map<Integer, String> fieldMap = null;

		List<User> users = new ArrayList<User>();
		if (list != null)
			fieldMap = list.remove(0);

		for (Map<Integer, String> map1 : list) {

			Optional<User> user = null;
			dataMap = new LinkedHashMap<>();

			for (Integer index : map1.keySet()) {

				String fieldName = fieldMap.get(index);
				String fieldValue = map1.get(index);

				System.out.println("Key = " + index + ", Value = " + fieldName);
				dataMap.put(fieldName, fieldValue);
			}

			user = setProperty(dataMap);

			if (user.isPresent())
				users.add(user.get());
		}

		dataMap.forEach((index, data) -> {
			// fieldsData.put(fieldMap.get(index), data);
			System.out.println("Key : " + index + " Value : " + data);
		});

		return users;
	}

	private Optional<User> setProperty(Map<String, String> dataMap)
			throws IllegalArgumentException, IllegalAccessException, ParseException {

		List<T> results = new ArrayList<>();

		User user = setEntityFieldData(dataMap);
		Optional<User> userData = Optional.of(user);

		return userData;
	}

	private User setEntityFieldData(Map<String, String> dataMap) throws IllegalAccessException, ParseException {

		Class<User> clazz = User.class;
		User user = new User();

		Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);

		processEntityField(dataMap, user, fields);

		return user;
	}

	private <T> void processEntityField(Map<String, String> dataMap, T object, Field[] fields)
			throws IllegalAccessException, ParseException {
		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			for (Field field : fields) {

				String fieldName = field.getName().replace(" ", "").toLowerCase();
				String columnName = entry.getKey().trim().replace(" ", "").toLowerCase();

				if (columnName.equalsIgnoreCase(fieldName)) {
					setFieldNameWithDataType(field, entry.getValue(), object);
				}
			}
		}
	}

	private <T> void setFieldNameWithDataType(Field field, String value, T object)
			throws IllegalArgumentException, IllegalAccessException, ParseException {

		Class clazz = field.getType();
		User user = (User) object;

		if (clazz.equals(String.class))
			field.set(object, value);
		else if (clazz.equals(Long.class))
			field.set(object, Long.parseLong(value));
		else if (clazz.equals(Float.class))
			field.set(object, Float.parseFloat(value));
		else if (clazz.equals(Integer.class))
			field.set(object, Integer.parseInt(value));
		else if (clazz.equals(Date.class)) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			field.set(object, formatter.parse(value));
		} else if (clazz.equals(Address.class)) {
			Address address = new Address();
			processEntity(Address.class, address);
			user.setAddress(address);
		} else if (clazz.equals(LoanDetails.class)) {
			LoanDetails loanDetails = new LoanDetails();
			processEntity(LoanDetails.class, loanDetails);
			user.setLoanDetails(loanDetails);
		} else if (clazz.equals(ProfessionalDetails.class)) {
			ProfessionalDetails professionalDetails = new ProfessionalDetails();
			processEntity(ProfessionalDetails.class, professionalDetails);
			user.setProfessionalDetails(professionalDetails);
		}
	}

	private <T> void processEntity(Class<T> clazz, T object) throws IllegalAccessException, ParseException {

		Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		processEntityField(dataMap, object, fields);
	}
}
