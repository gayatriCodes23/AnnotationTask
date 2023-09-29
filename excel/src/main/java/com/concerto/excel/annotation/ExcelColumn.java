package com.concerto.excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {

	String columnName() default ""; // Column name in the Excel file

	String description() default ""; // Description of the field

	String dateFormat() default ""; // Format of the field (e.g., date format)

	boolean required() default false; // Indicates if the field is required

	boolean emailFormat() default false; // email format validation

	boolean decimalFormat() default false; // price format validation

	int minLength() default 0; // Minimum length constraint

	int maxLength() default Integer.MAX_VALUE; // Maximum length constraint
}