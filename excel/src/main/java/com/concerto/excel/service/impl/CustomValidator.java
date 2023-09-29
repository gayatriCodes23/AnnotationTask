package com.concerto.excel.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.springframework.stereotype.Component;

import com.concerto.excel.bean.ExcelBean;

@Component
public class CustomValidator {
	
	
	public String dateConvertor(String birthdate) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = inputDateFormat.parse(birthdate);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            // If parsing fails, return null to indicate invalid data
            return null;
        }
    }

	public boolean isValid(ExcelBean value) {
		boolean validFlag = true;

		if (value == null) {
			throw new IllegalArgumentException("ExcelBean object is null.");
			
		}

		if (value.getId() == null || value.getId().isEmpty()) {
			validFlag = false;
			System.out.println("Field is required: ID");
		}

		if (value.getName() == null || value.getName().isEmpty()) {
			validFlag = false;
			System.out.println("Field is required: NAME");
		
		}

		if (value.getLocation() == null || value.getLocation().isEmpty()) {
			validFlag = false;
			System.out.println("Field is required: LOCATION");
		}

		if (value.getEmail() == null || value.getEmail().isEmpty()) {
			validFlag = false;
			System.out.println("Field is required: EMAIL");
		}

		if (value.getFees() == null || value.getFees().isEmpty()) {
			validFlag = false;
			System.out.println("Field is required: FEES");
		}

		if (value.getEmail() != null) {
			String email = value.getEmail();
			String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
			if (!email.matches(emailRegex) && !email.equalsIgnoreCase("Not Mentioned")) {
				validFlag = false;
				System.out.println("Invalid Email Format: " + email);
			}
		}

		if (value.getFees() != null) {
			String decimal = value.getFees();
			String decimalRegex = "^\\d+(\\.\\d{0,3})?$";
			if (!decimal.matches(decimalRegex) && !decimal.equalsIgnoreCase("Not Mentioned")) {
				validFlag = false;
				System.out.println("Invalid Decimal Format: " + decimal);
			}
		}

		return validFlag;
	}

}
