package com.concerto.excel;

import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.concerto.excel.bean.ExcelBean;
import com.concerto.excel.service.ExcelService;
import com.concerto.excel.service.impl.ExcelServiceImpl;

@SpringBootApplication
public class ExcelAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelAnnotationApplication.class, args);
		processExcelData();
	}
	
	
		public static void processExcelData() {
		    try {
		        ExcelService excelService = new ExcelServiceImpl();
		        Map<String, List<ExcelBean>> dataMap = excelService.upload("D:\\SampleExcelData\\ExcelBean.xls", 0);
		        
		        List<ExcelBean> validDataList = dataMap.get("validData");
		        List<ExcelBean> invalidDataList = dataMap.get("invalidData");
		        
		        // Process the valid data as needed
		        System.out.println("Valid Data:");
		        for (ExcelBean validData : validDataList) {
		            System.out.println(validData.toString());
		        }
		        
		        // Process the invalid data or validation errors as needed
		        System.out.println("Invalid Data or Validation Errors:");
		        for (ExcelBean invalidData : invalidDataList) {
		            System.out.println(invalidData.toString());
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}


}
