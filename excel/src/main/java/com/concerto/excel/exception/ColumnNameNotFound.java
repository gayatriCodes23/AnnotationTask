package com.concerto.excel.exception;

public class ColumnNameNotFound extends Exception {

	public ColumnNameNotFound() {
		super();

	}

	public ColumnNameNotFound(String columnName) {
		super(getErrorMessage(columnName));
	}

	private static String getErrorMessage(String columnName) {
		if (columnName == null || columnName.isEmpty()) {
			return "Column name is Empty";
		} else {
			return "Column name '" + columnName + "' not found";
		}
	}

}
