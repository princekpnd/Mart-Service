package com.shop.shopservice.utils;

import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Cell;

import com.shop.shopservice.entity.ExcelStringEntity;

import springfox.documentation.spring.web.json.Json;

public class Calculation {

	public float DecimalCalculation(float value) {
		DecimalFormat df = new DecimalFormat("#.##");
		float data = Float.parseFloat(df.format(value));
		return data;
	}
	
	public ExcelStringEntity ExcelToString(Cell cell) {
		ExcelStringEntity data = new ExcelStringEntity();
		String cellType = cell.getCellType().toString();
		if (cellType == "STRING") {
			String cellValue = cell.getStringCellValue();
			String withoutSpace = new String(cellValue).trim();
			String replacedSpace = withoutSpace.replace(" ", "_");
			
			data.setName(withoutSpace);
			data.setTitle(replacedSpace);
		} else if (cellType == "NUMERIC") {
			long cellValue = (long) cell.getNumericCellValue();
			String intToString = String.valueOf(cellValue);
			data.setName(intToString);
			data.setTitle(intToString);
		}
		
		return data;
	}
	
	public int ExcelToInteger(Cell cell) {
		int data = 0;
		String cellType = cell.getCellType().toString();
		if (cellType == "STRING") {			
			data = 0;
		} else if (cellType == "NUMERIC") {
			int cellValue = (int) cell.getNumericCellValue();
			data = cellValue;
		}
		
		return data;
	}
	
	public float ExcelToFloat(Cell cell) {
		float data = 0;
		String cellType = cell.getCellType().toString();
		if (cellType == "STRING") {			
			data = 0;
		} else if (cellType == "NUMERIC") {
			float cellValue = DecimalCalculation((float) cell.getNumericCellValue());
			data = cellValue;
		}		
		
		return data;
	}
	
	public Boolean ExcelToBoolean(Cell cell) {
		Boolean data = true;
		String cellType = cell.getCellType().toString();
		if (cellType == "BOOLEAN") {			
			data = cell.getBooleanCellValue();
		} else if (cellType == "NUMERIC") {
			data = true;
		}		
		
		return data;
	}
}
