package com.ttt.tt;

import com.ttt.tt.dealxls;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class riskdata {
	
	String cookie;
	
	@BeforeTest
	public void setUp(){
//		RestAssured.baseURI="http://risk.bat.tcredit.com";
//		cookie = login();
	}
	
	public String login(){
		Response res_login=RestAssured.given().formParam("userName","admin1").formParam("password","123456").post("http://risk.bat.tcredit.com/riskData/trics/doLogin");
		String risk_user = res_login.getCookie("_risk_user");
		return risk_user;
	}
	
	@Test
	public void test_read1() throws InvalidFormatException, IOException{
//		InputStream inp = new FileInputStream("/Users/tcxy/Desktop/ttt/tt/src/resources/apis.xlsx");
		InputStream inp = new FileInputStream("/Users/zhengjiali/workspace/ttt/tt/src/resources/apis.xlsx");
		Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(0);
	    Row row = sheet.getRow(0);
	    Cell cell = row.getCell(1);
	    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
	    System.out.println(cell.getStringCellValue());
	    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//	    if (cell == null)
//	        cell = row.createCell(3);
//	    cell.setCellType(CellType.STRING);
//	    cell.setCellValue("a test");
	}
	
	@Test(groups = {"para-data"})
	public void testQuery301() {
		RestAssured.basePath="/riskData/trics/param/query?sortType=desc&funCode=301";
//		RestAssured.given().cookie("_risk_user",cookie).when().get().then().body("status", equalTo(0));
	}
	
	@Test(groups = {"DB-data"})
	public void testQuery101(){
//		RestAssured.given().cookie("_risk_user",cookie).when().get("/riskData/trics/data/query?sortType=desc&funCode=101").then().body("status", equalTo(0));
	}
	
	@Test(groups = {"DB-data"})
	public void DBcheckEnName(){
//		Response res_cen = RestAssured.given().cookie("_risk_user",cookie).when().get("/riskData/trics/checkName?nameEn=dbtest1&productLine[]=5,7,9&id=undefined&variable=");
//		System.out.println(res_cen.getBody().asString());
//		res_cen.then().body("status",equalTo(0));
//		RestAssured.given().cookie("_risk_user",cookie).when().get("/riskData/trics/checkName?nameEn=appId&productLine[]=5,7,9&id=undefined&variable=").then().body("status", equalTo(-1));
	}
	
	@Test(groups = {"DB-data"})
	public void DBcreate(){
		
	}
	
	@Test
	public void checkEnName(){
//		Response res_cen = RestAssured.given().cookie("_risk_user",cookie).when().get("/riskData/trics/param/checkName?nameEn=app&productLine[]=5,7,9&id=undefined&variable=");
//		System.out.println(res_cen.getBody().asString());
//		res_cen.then().body("status",equalTo(0));
//		RestAssured.given().cookie("_risk_user",cookie).when().get("/riskData/trics/param/checkName?nameEn=appId&productLine[]=5,7,9&id=undefined&variable=").then().body("status", equalTo(-1));
	}

}
