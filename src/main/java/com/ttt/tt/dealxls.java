package com.ttt.tt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;



public class dealxls {
	
	String uri;
	String userName;
	String passwd;
	ArrayList<HashMap<String, String>> apis;
	
	public dealxls() throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream("/Users/tcxy/git/ttt/ttt/src/resources/apis.xlsx");
//		InputStream inp = new FileInputStream("/Users/zhengjiali/workspace/ttt/tt/src/resources/apis.xlsx");
		
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    int lastRowNum = sheet.getLastRowNum();
	    System.out.println("last row num:"+sheet.getLastRowNum());
	    Row row = sheet.getRow(0);
	    Cell cell = row.getCell(1);
	    uri = cell.getStringCellValue();
	    System.out.println(uri);
	    row = sheet.getRow(1);
		userName = row.getCell(1).getStringCellValue();
		System.out.println(userName);
//		System.out.println(row.getCell(3).getCellType());
		passwd = row.getCell(3).getStringCellValue();
//		System.out.println(sheet.getRow(3).getCell(0).getStringCellValue());
		apis = new ArrayList<HashMap<String,String>>();
		for(int i=3;i<=lastRowNum;i++){
			HashMap<String,String> t = new HashMap<String,String>();
		    t.put("group",sheet.getRow(i).getCell(1).getStringCellValue());
		    t.put("path",sheet.getRow(i).getCell(3).getStringCellValue());
		    t.put("method",sheet.getRow(i).getCell(4).getStringCellValue());
		    if(sheet.getRow(i).getCell(5) != null)
		    	t.put("param",sheet.getRow(i).getCell(4).getStringCellValue());
		    else{
		    	t.put("param", null);
		    }
		    apis.add(t);
		    t=null;
		}
//		System.out.println(apis.get(0).get("group"));
	}
	
	public void write(String code){
	    // Write the output to a file
//	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//	    wb.write(fileOut);
//	    fileOut.close();
	    
	}
	
	public void tearDown(){
		
	}

}
