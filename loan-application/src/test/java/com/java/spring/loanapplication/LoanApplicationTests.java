package com.java.spring.loanapplication;

import java.io.IOException;
import java.text.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.java.spring.loanapplication.controller.UploadController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoanApplication.class)
public class LoanApplicationTests {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UploadController controller;

	@Value("${file.dir.path.upload}")
	private String UPLOAD_DIR_PATH;

	@Test
	public void contextLoads() {
		logger.info("Test is running");
	}

	@Test
	public void process_Documen() throws InvalidFormatException, IllegalAccessException, IOException, ParseException {

		controller.processDocument();

	}

	@Test
	public void testString() {
		logger.info("Test is running");
		String str = "First name";
		String[] columnArr = str.trim().split(" ");
		for (String str1 : columnArr)
			logger.info(" {} " + str1);
	}

}
