package com.concerto.excel.bean;

import com.concerto.excel.annotation.ExcelColumn;

public class ExcelBean {

	@ExcelColumn(columnName = "FEES", decimalFormat = true)
	private String fees;

	@ExcelColumn(columnName = "ID")
	private String id;

	@ExcelColumn(columnName = "LOCATION")
	private String location;

	@ExcelColumn(columnName = "BIRTHDATE", dateFormat = "dd-MM-yyyy")
	private String birthdate;

	@ExcelColumn(columnName = "EMAIL", emailFormat = true)
	private String email;

	@ExcelColumn(columnName = "NAME")
	private String name;

	public ExcelBean(String id, String name, String email, String birthdate, String fees, String location) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birthdate = birthdate;
		this.fees = fees;
		this.location = location;
	}

	public ExcelBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "MyData [id=" + id + ", name=" + name + ", email=" + email + ", birthdate=" + birthdate + ", fees="
				+ fees + ", location=" + location + "]";
	}

}
