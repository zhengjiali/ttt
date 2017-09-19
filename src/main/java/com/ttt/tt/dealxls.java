package com.ttt.tt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;



public class dealxls {
	
	public void read1() throws InvalidFormatException, IOException{
//		InputStream inp = new FileInputStream("/Users/tcxy/Desktop/ttt/tt/src/resources/apis.xlsx");
		InputStream inp = new FileInputStream("/Users/zhengjiali/workspace/ttt/tt/src/resources");
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    Row row = sheet.getRow(2);
	    Cell cell = row.getCell(2);
	    System.out.println(cell.getStringCellValue());
//	    if (cell == null)
//	        cell = row.createCell(3);
//	    cell.setCellType(CellType.STRING);
//	    cell.setCellValue("a test");
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
