package com.ttt.tt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;



public class dealxls {
	
	String uri;
	String userName;
	String passwd;
	
	
	public dealxls() throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream("/Users/tcxy/git/ttt/ttt/src/resources/apis.xlsx");
//		InputStream inp = new FileInputStream("/Users/zhengjiali/workspace/ttt/tt/src/resources/apis.xlsx");
		
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    Row row = sheet.getRow(0);
	    Cell cell = row.getCell(1);
	    if (cell == null)
	        uri = null;
	    else{
	    	uri = cell.getStringCellValue();
	    	System.out.println(uri);
	    }
	    row = sheet.getRow(1);
	    userName = row.getCell(1).getStringCellValue();
	    System.out.println(userName);
	    System.out.println(row.getCell(3).getCellType());
	    passwd = row.getCell(3).getStringCellValue();
	    System.out.println(passwd);
	    

	}
	
	public void read(){
	    // Write the output to a file
//	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//	    wb.write(fileOut);
//	    fileOut.close();
	    
	}
	
	public void tearDown(){
		
	}

}
