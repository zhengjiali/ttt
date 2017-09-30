package com.ttt.tt;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import org.testng.annotations.*;


public class LoginTest {
	
	HashMap<String,String> data = new HashMap<String,String>();
	
	@BeforeClass
	public void setUp(){
		baseURI = "http://risk.bat.tcredit.com";
		data.put("name", "admin1");
		data.put("pwd", "123456");
	}
	
	@Test(priority=1)
	public void loginNoPara(){
		given()
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));
	}
	
	@Test(priority=2)
	public void loginNoPwd(){
		given()
			.formParam("userName", data.get("name"))
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));
	}
	
	@Test(priority=3)
	public void loginWrongPwd(){
		given()
			.formParam("userName",data.get("name"))
			.formParam("password", data.get("pwd"))
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));	
	}
	
	@Test(priority=0)
	public void loginRightPwd(){
		given()
			.formParam("userName", data.get("name"))
			.formParam("password", data.get("pwd"))
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", equalTo(0));
	}
}
