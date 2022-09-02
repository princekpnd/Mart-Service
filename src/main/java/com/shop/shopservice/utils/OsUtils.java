package com.shop.shopservice.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.shop.shopservice.entity.Item;
import com.shop.shopservice.service.IItemService;
import org.aspectj.weaver.IUnwovenClassFile;
@RestController
@RequestMapping("/api/utiles")
public class OsUtils
{
	
	@Autowired
	private IItemService itemService;  
   /**
    * types of Operating Systems
    */
   public enum OSType {
     Windows, MacOS, Linux, Other
   };

   // cached result of OS detection
   protected static OSType detectedOS;
   public static OSType getOperatingSystemType() {
	    if (detectedOS == null) {
	      String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
	      if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
	        detectedOS = OSType.MacOS;
	      } else if (OS.indexOf("win") >= 0) {
	        detectedOS = OSType.Windows;
	      } else if (OS.indexOf("nux") >= 0) {
	        detectedOS = OSType.Linux;
	      } else {
	        detectedOS = OSType.Other;
	      }
	    }
	    return detectedOS;
	  }
   
   @GetMapping("excel")
	public ResponseEntity<?> getExcelFile(){
//	   public static final String Book1 = "D:\java-workspace\Book1.xlsx";
		String requestUrl = "Welcome";
		 try {
			   FileInputStream file = new FileInputStream(new File("D:\\java-workspace\\Book3.xlsx"));
			   Workbook workbook = new XSSFWorkbook(file);
			   DataFormatter dataFormatter = new DataFormatter();
			   Iterator<Sheet> sheets = workbook.sheetIterator();
			   while(sheets.hasNext()) {
				   Sheet sh = sheets.next();
				   System.out.println("Sheet name is "+sh.getSheetName());
				   System.out.println("Sheet name is "+sh.getLastRowNum());
				   System.out.println("Sheet cell no is "+sh.getRow(0).getLastCellNum());
				   System.out.println("col data ==" +sh.getRow(2).getCell(3));
				   System.out.println("------------");
				   Iterator<Row> iterator = sh.iterator(); 
				   
				   for(int i= 1; i<=sh.getLastRowNum(); i++) {
					   Item item = new Item();
					   for(int j= 0;j<sh.getRow(i).getLastCellNum(); j++) {
//						   switch()
						    String cellData =sh.getRow(0).getCell(j).toString();
						    String cellData1 =sh.getRow(i).getCell(j).toString();
						    System.out.print("cell data" +cellData);
						   
						    switch (cellData) {
					        case "NAME":
					        	item.setName(cellData1);
					            break;
					        case "CATEGORY":
					        	item.setCategory((int) Float.parseFloat(cellData1));
					        	break;
					        case "BRAND":
					        	item.setBrand(Integer.parseInt(cellData1));
					        	break;
					        case "SHOP_ID":
					        	item.setShopId(cellData1);
					        	break;
					        case "REVIEW":
					        	item.setReview(cellData1);
					        	break;
					        case "MEASUREMENT":
					        	item.setMeasurement(Integer.parseInt(cellData1));
					        	break;
					        case "SHOP_NAME":
					        	item.setShopName(cellData1);
					        	break;
					        case "IS_ACTIVE":
					        	item.setActive(Boolean.TRUE);
					        	break;
					        case "IS_DELETED":
					        	item.setDeleted(Boolean.FALSE);
					        	break;
					        case "CREATED_ON":
					        	item.setCreatedOn(new Date());
					        	break;
					        case "DESCRIPTION":
					        	item.setDescription(cellData1);
					        	break;
					        	default:
					        		String s = "Last data";
					        		
						    }
					   }
					   itemService.createItem(item);
				   }
//				   while(iterator.hasNext()) {
//					   Row row = iterator.next();
//					   Iterator<Cell> cellIterator = row.iterator();
//					   while(cellIterator.hasNext()) {
//						   Cell cell = cellIterator.next();
//						   String cellValue = dataFormatter.formatCellValue(cell);
////						   if(cell.getCellType() == CellType.STRING) {
////							   
////						   }
//						   System.out.print(cellValue+ "\t");
//					   }
//					   System.out.println();
//				   }
				   
			   }
			   workbook.close();
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
		return new ResponseEntity<>(requestUrl, HttpStatus.OK);
	}
}