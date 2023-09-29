package com.concerto.excel.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.concerto.excel.bean.ExcelBean;

public interface ExcelService {

	// public List<?> processExcelUpload(Class<?> clazz, int sheetIndex) throws
	// Exception;
	public  Map<String, List<ExcelBean>> upload(String filePath, int sheetIndex) throws Exception;

	int getColumnIndex(Sheet sheet, String columnName);

	// Object createDataObject(Class<?> clazz, Sheet sheet, Row row) throws
	// Exception;

}
