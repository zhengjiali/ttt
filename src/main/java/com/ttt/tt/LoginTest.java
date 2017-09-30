package com.ttt.tt;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.*;


public class LoginTest {
	
	@BeforeClass
	public void setUp(){
		baseURI = "http://risk.bat.tcredit.com";
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
			.formParam("userName", "admin1")
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));
	}
	
	@Test(priority=3)
	public void loginWrongPwd(){
		given()
			.formParam("userName","admin1")
			.formParam("password", "1234567")
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.statusCode(200)
			.body("status", equalTo(-1));	
	}
	
	@Test(priority=0)
	public void loginRightPwd(){
		given()
			.formParam("userName", "admin1")
			.formParam("password", "123456")
		.when()
			.post("/riskData/trics/doLogin")
		.then()
			.log().all()
			.statusCode(200)
			.body("status", equalTo(0));
	}
}
