package com.concerto.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface ExcelSheet {
	
	String value(); // Excel file path
	int sheetIndex() default 0; // Default sheet name
	
}
