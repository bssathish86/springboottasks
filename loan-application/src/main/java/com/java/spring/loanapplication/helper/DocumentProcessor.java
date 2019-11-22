package com.java.spring.loanapplication.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

@Component
public class DocumentProcessor {

	private Map<Integer, String> fieldsMap = null;

	private Map<Integer, String> dataMap = null;

	List<Map<Integer, String>> dataList = new ArrayList<>();

	public List<Map<Integer, String>> processDocument(String fileName) throws IOException, InvalidFormatException {

		Workbook workbook = WorkbookFactory.create(new File(fileName));

		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		System.out.println("Retrieving Sheets :");
		workbook.forEach(sheet -> {
			System.out.println("=> " + sheet.getSheetName());
		});

		DataFormatter dataFormatter = new DataFormatter();

		System.out.println(" iterating row wise column data from the  sheet :");

		fieldsMap = new LinkedHashMap<>();

		workbook.forEach(sheet -> {

			sheet.forEach(row -> {

				dataMap = new LinkedHashMap<>();

				row.forEach(cell -> {

					String cellValue = dataFormatter.formatCellValue(cell);

					if (cell.getRowIndex() == 0)
						fieldsMap.put(cell.getColumnIndex(), cellValue);
					else
						dataMap.put(cell.getColumnIndex(), cellValue);

					System.out.print(cellValue + "\t");
				});

				System.out.println(dataMap.toString());
			});

			dataList.add(dataMap);
		});

		dataList.add(0, fieldsMap);
		workbook.close();
		return dataList;
	}
}
