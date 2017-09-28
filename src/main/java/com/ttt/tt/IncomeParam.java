package com.ttt.tt;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import org.testng.annotations.*;

import io.restassured.response.Response;

public class IncomeParam {
	HashMap<String,String> data;
	String cookie;
	
	
	public void login(String username,String passwd,String uri){
		Response res_login=given().formParam("userName",username).formParam("password",passwd).post(uri+"/riskData/trics/doLogin");
		 cookie = res_login.getCookie("_risk_user");
		return;
	}
	
	public class Search{
		
		@BeforeClass
		public void setUp(){
			baseURI = "http://risk.bat.tcredit.com";
//			data.put("userName","admin1");
//			data.put("pwd","123456");
			login("admin1","123456","http://risk.bat.tcredit.com");
		}
		
		@Test
		public void noneParam(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test
		public void funCodeParam1(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?funcode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test
		public void funCodeParam2(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test
		public void funCodeParam3(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?funCode=311")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test
		public void getAllParam(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count",equalTo(159));
		}
		@Test
		public void getAllInEdit(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&status=1")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count", equalTo(94));
		}
		
		@Test
		public void nameEnSearch(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&nameEn=restAssured")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count",equalTo(0));
		}
		
		@Test
		public void PagePara1(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&current=311")
			.then()
				.statusCode(200)
				.body("status", equalTo(0));
		}
		
	}
	
	public class createParam{
		
		@BeforeClass
		public void setUp(){
			baseURI = "http://risk.bat.tcredit.com";
//			data.put("userName","admin1");
//			data.put("pwd","123456");
			login("admin1","123456","http://risk.bat.tcredit.com");
		}
		
		@Test
		public void checkNameEn(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/checkName")
			.then()
				.statusCode(200)
				.body("status", equalTo(0));
		}
		
		@Test
		public void checkNameEn2(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.get("/riskData/trics/param/checkName?nameEn=test")
			.then()
				.statusCode(200)
				.body("status", equalTo(0));
		}
		
		@Test
		public void checkNameEnExist(){
			given()
				.cookie("_risk_user",cookie)
				.param("productLine[]", 5,7,9)
			.when()
				.get("/riskData/trics/param/checkName?nameEn=qq&id=undefined&variable=")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1))
				.body("data.nameEn", equalTo("名字重复"));
		}
		
		@Test
		public void EditAttr(){
			given()
				.cookie("_risk_user",cookie)
			.when()
				.post("/riskData/trics/param/editAttr?funCode=301")
			.then()
				.log().all();
		}
		
		@Test
		public void EditAttr2(){
			given()
				.cookie("_risk_user",cookie)
				.formParam("productLine[]", 5,7,9)
				.formParam("nameEn", "restTest")
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302&nameCn=Test&productLine=9")
			.then()
				.log().all();
		}
		
		
	}
	

}
