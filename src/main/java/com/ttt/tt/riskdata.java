package com.ttt.tt;

import com.ttt.tt.dealxls;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	HashMap<String,String> data;
	
	ArrayList<HashMap<String, String>> apis;
	
	@BeforeTest
	public void setUp() throws InvalidFormatException, IOException{
		dealxls x = new dealxls();
		baseURI=x.uri;
		apis = x.apis;
		System.out.println(apis.toString());
		login(x.userName,x.passwd,x.uri);
	}
	
	public void login(String username,String passwd,String uri){
		Response res_login=given().formParam("userName",username).formParam("password",passwd).post(uri+"/riskData/trics/doLogin");
		 data.put("_risk_user", res_login.getCookie("_risk_user"));
		return;
	}
	
	
	@Test
	public void test01(){
		if (apis.get(0).get("param") == null)
			System.out.println(given()
								.cookie("_risk_user",data.get("_risk_user"))
							   .when()
							   	.get(apis.get(0).get("path"))
							   .statusCode());
	}
	
	@Test(groups = {"paraData"})
	public void testQuery301() {
		given()
			.cookie("_risk_user",data.get("_risk_user"))
		.when()
			.get("/riskData/trics/param/query?sortType=desc&funCode=301")
		.then()
			.statusCode(200)
			.body("status", equalTo(0));
	}
	
	@Test(groups = {"paraData"})
	public void checkEnName_exist(){
		given()
			.cookie("_risk_user",data.get("_risk_user"))
		.when()
			.get("/riskData/trics/param/checkName?nameEn=appId&productLine[]=5,7,9&id=undefined&variable=")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));
	}
	@Test(groups = {"paraData"})
	public void checkEnName_nonexist(){
		given()
			.cookie("_risk_user",data.get("_risk_user"))
			.parameter("nameEn", "")
			.parameter("productLine[]", "5,7,9")
			.parameter("id", "undefined")
			.parameter("cariable","")
		.when()
			.get("/riskData/trics/param/checkName")
		.then()
			.statusCode(200)
			.body("status", equalTo(0));
	}
	
	@Test(groups = {"dBData"})
	public void testQuery101(){
		given().cookie("_risk_user",data.get("_risk_user"))
		.when()
			.get("/riskData/trics/data/query?sortType=desc&funCode=101")
		.then()
			.statusCode(200)
			.body("status", equalTo(0));
	}
	
	@Test(groups = {"dBData"})
	public void DBcheckEnName(){
		Response res_cen = given().cookie("_risk_user",data.get("_risk_user")).when().get("/riskData/trics/checkName?nameEn=dbtest1&productLine[]=5,7,9&id=undefined&variable=");
		System.out.println(res_cen.getBody().asString());
		res_cen.then().body("status",equalTo(0));
		given()
			.cookie("_risk_user",data.get("_risk_user"))
		.when()
			.get("/riskData/trics/checkName?nameEn=appId&productLine[]=5,7,9&id=undefined&variable=")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));
	}
	
	@Test(groups = {"dBData"})
	public void DBcreate(){
		
	}
	

}
