package com.concerto.excel.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.concerto.excel.annotation.ExcelColumn;
import com.concerto.excel.bean.ExcelBean;
import com.concerto.excel.exception.ColumnNameNotFound;

import com.concerto.excel.service.ExcelService;

@Service
public class ExcelServiceImpl implements ExcelService {

	DataFormatter dataFormatter = new DataFormatter();

	CustomValidator lCustomValidator = new CustomValidator();

	public ExcelServiceImpl() {
		super();
	}

	@Override
	public int getColumnIndex(Sheet sheet, String columnName) {
		Row headerRow = sheet.getRow(0);
		for (Cell cell : headerRow) {
			if (cell.getStringCellValue().equals(columnName)) {
				return cell.getColumnIndex();
			}
		}
		return -1; // Column not found
	}

	@Override
	public Map<String, List<ExcelBean>> upload(String filePath, int sheetIndex) throws Exception {
		Map<String, List<ExcelBean>> resultMap = new HashMap<>();
		List<ExcelBean> dataList = new ArrayList<>();
		List<ExcelBean> invalidDataList = new ArrayList<>();

		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fileInputStream)) {

			Sheet sheet = workbook.getSheetAt(sheetIndex);

			// Get the header row to validate column names
			Row headerRow = sheet.getRow(0);
			Map<String, Field> fieldMap = getFieldMap(ExcelBean.class);

			// Validate column names
			for (Cell cell : headerRow) {
				String columnName = cell.getStringCellValue().trim();
				Field field = fieldMap.get(columnName);

				if (field == null) {
					throw new ColumnNameNotFound(columnName);
				}

			}

			// FileInputStream fis = new FileInputStream(new File(filePath));
			// Workbook workbook = WorkbookFactory.create(fis);
			// Sheet sheet = workbook.getSheetAt(sheetIndex); // Assuming you're working
			// with the first sheet

			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext()) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				ExcelBean dataObject = ExcelBean.class.getDeclaredConstructor().newInstance();

				boolean isEmptyRow = true; // Flag to check if the row is empty

				for (Field field : ExcelBean.class.getDeclaredFields()) {
					if (field.isAnnotationPresent(ExcelColumn.class)) {
						ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
						String columnName = annotation.columnName();
						int columnIndex = getColumnIndex(sheet, columnName);
						if (columnIndex != -1) {
							Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
							field.setAccessible(true);
							String cellValue = dataFormatter.formatCellValue(cell);

							if ("BIRTHDATE".equals(columnName)) {
								String date = lCustomValidator.dateConvertor(cellValue);

								dataObject.setBirthdate(date);
							}

							// Check if the cell is empty, if so, set it to "Not Mentioned"
							if (cellValue.isEmpty()) {
								cell.setCellValue("Not Mentioned");
							} else {
								isEmptyRow = false; // The row is not empty if a cell has a non-empty value
							}

							field.set(dataObject, cellValue);
						}
					}
				}

				// Perform validation here
				if (!lCustomValidator.isValid(dataObject)) {
					invalidDataList.add(dataObject);
				} else {
					// Add the dataObject to the dataList only if the row is not empty and valid
					if (!isEmptyRow) {
						dataList.add(dataObject);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		}

		resultMap.put("validData", dataList);
		resultMap.put("invalidData", invalidDataList);

		return resultMap;
	}

	private static Map<String, Field> getFieldMap(Class<?> clazz) {
		Map<String, Field> fieldMap = new HashMap<>();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (field.isAnnotationPresent(ExcelColumn.class)) {
				ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
				String columnName = excelColumn.columnName();
				fieldMap.put(columnName, field);
			}
		}

		return fieldMap;
	}

}

// package com.concerto.excel.service.impl;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.lang.reflect.Field;
// import java.util.ArrayList;
// import java.util.Iterator;
// import java.util.List;
//
// import org.apache.poi.ss.usermodel.Cell;
// import org.apache.poi.ss.usermodel.DataFormatter;
// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.ss.usermodel.Sheet;
// import org.apache.poi.ss.usermodel.Workbook;
// import org.apache.poi.ss.usermodel.WorkbookFactory;
// import org.springframework.stereotype.Service;
//
// import com.concerto.excel.annotation.ExcelColumn;
// import com.concerto.excel.bean.ExcelBean;
// import com.concerto.excel.service.ExcelService;
//
// @Service
// public class ExcelServiceImpl implements ExcelService {
//
// DataFormatter dataFormatter = new DataFormatter();
//
// CustomValidator lCustomValidator = new CustomValidator();
//
// public ExcelServiceImpl() {
// super();
// }
//
// @Override
// public int getColumnIndex(Sheet sheet, String columnName) {
// Row headerRow = sheet.getRow(0);
// for (Cell cell : headerRow) {
// if (cell.getStringCellValue().equals(columnName)) {
// return cell.getColumnIndex();
// }
// }
// return -1; // Column not found
// }
//
// @Override
// public List<ExcelBean> upload(String filePath, int sheetIndex) throws
// Exception {
// List<ExcelBean> dataList = new ArrayList<>();
//
// FileInputStream fis = new FileInputStream(new File(filePath));
// Workbook workbook = WorkbookFactory.create(fis);
// Sheet sheet = workbook.getSheetAt(sheetIndex); // Assuming you're working
// with the first sheet
//
// Iterator<Row> rowIterator = sheet.iterator();
//
// if (rowIterator.hasNext()) {
// rowIterator.next();
// }
//
// while (rowIterator.hasNext()) {
// Row row = rowIterator.next();
// ExcelBean dataObject =
// ExcelBean.class.getDeclaredConstructor().newInstance();
//
// boolean isEmptyRow = true; // Flag to check if the row is empty
//
// for (Field field : ExcelBean.class.getDeclaredFields()) {
// if (field.isAnnotationPresent(ExcelColumn.class)) {
// ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
// String columnName = annotation.columnName();
// int columnIndex = getColumnIndex(sheet, columnName);
// if (columnIndex != -1) {
// Cell cell = row.getCell(columnIndex,
// Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
// field.setAccessible(true);
// String cellValue = dataFormatter.formatCellValue(cell);
//
// if ("BIRTHDATE".equals(columnName)) {
// String date = lCustomValidator.dateConvertor(cellValue);
// dataObject.setBirthdate(date);
// }
//
// // Check if the cell is empty, if so, set it to "Not Mentioned"
// if (cellValue.isEmpty()) {
// cell.setCellValue("Not Mentioned");
// } else {
// isEmptyRow = false; // The row is not empty if a cell has a non-empty value
// }
//
// field.set(dataObject, cellValue);
// }
// }
// }
//
// // Add the dataObject to the dataList only if the row is not empty
// if (!isEmptyRow) {
// dataList.add(dataObject);
// }
// }
//
// workbook.close();
// fis.close();
//
// // Perform validation here
// if (!lCustomValidator.isValid(dataList)) {
// throw new IllegalArgumentException("Data validation failed.");
// }
//
// return dataList;
// }
// }
