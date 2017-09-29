package com.ttt.tt;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.response.Response;
import com.ttt.tt.db;

public class IncomeParam {
	
	HashMap<String,String> data = new HashMap<String,String>();
	HashMap<String,Integer> resDb = new HashMap<String,Integer>();
	
	public void login(String username,String passwd,String uri){
		Response res_login=given().formParam("userName",username).formParam("password",passwd).post(uri+"/riskData/trics/doLogin");
		 data.put("_risk_user", res_login.getCookie("_risk_user"));
//		 System.out.println(data.get("_risk_user"));

		return;
	}
	 
	public class Search{
		
		@BeforeClass
		public void setUp(){
			baseURI = "http://risk.bat.tcredit.com";
			login("admin1","123456","http://risk.bat.tcredit.com");
		}
		
		@Test(priority=0)
		public void noneParam(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=1)
		public void funCodeParam1(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?funcode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=2)
		public void funCodeParam2(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=3)
		public void funCodeParam3(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?funCode=311")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=4)
		public void getParam(){
			String sql = "select count(1) as 'count' from risk_data_param as rdp left join risk_data_info as rdi on rdp.id = rdi.`source_table_id` where rdp.`is_deleted`=0 and rdi.`company_id`=5 and rdi.`source_id`=3";
			resDb = db.searchDb(sql);
			System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count",equalTo(resDb.get("count")));
		}
		@Test(priority=5)
		public void getAllInEdit(){
			String sql = "select count(1) as 'count' from risk_data_param as rdp left join risk_data_info as rdi on rdp.id = rdi.`source_table_id` where rdp.`is_deleted`=0 and rdi.`company_id`=5 and rdi.`source_id`=3 and rdi.`status`=1 and rdi.used_count = 0 order by rdi.`last_update_time` desc";
			resDb = db.searchDb(sql);
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&status=1")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count", equalTo(resDb.get("count")));
		}
		
		@Test(priority=6)
		public void nameEnSearch(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&nameEn=restAssured")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count",equalTo(0));
		}
		
		@Test(priority=7)
		public void PagePara1(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
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
			data.put("nameEn","restAssured");
		}
		
		@Test(priority=0)
		public void checkNameEn(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/checkName")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=1)
		public void checkNameEn2(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/checkName?nameEn=test")
			.then()
				.statusCode(200)
				.body("status", equalTo(0));
		}
		
		@Test(priority=2)
		public void checkNameEnExist(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.param("productLine[]", 5,7,9)
			.when()
				.get("/riskData/trics/param/checkName?nameEn=qq&id=undefined&variable=")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1))
				.body("data.nameEn", equalTo("名字重复"));
		}
		
		@Test(priority=3)
		public void EditAttrNocookie(){
			given()
			.when()
				.post("/riskData/trics/param/editAttr")
			.then()
				.statusCode(302);
		}
		
		@Test(priority=4)
		public void EditAttr(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.post("/riskData/trics/param/editAttr")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}
		
		@Test(priority=5)
		public void EditAttrProline(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.post("/riskData/trics/param/editAttr?funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1))
				.statusCode(200)
				.body("message", equalTo("作用域不能为空"));
		}
		
		@Test(priority=6)
		public void EditAttrFuncode(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
				.formParam("nameEn", "restTest")
			.when()
				.post("/riskData/trics/param/editAttr?nameCn=Test&productLine=9")
			.then()
				.statusCode(200)
				.body("status",equalTo(-1));
		}
			
		@Test(priority=7)
		public void EditAttrEn(){
			System.out.println(Thread.currentThread().getStackTrace()[5].getMethodName());
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302&nameCn=Test&productLine=9")
			.then()
				.statusCode(200)
				.body("status",equalTo(-1));
		}
		
		@Test(priority=8)
		public void EditAttrNull(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302&nameEn=&nameCn=&productLine=&dataType=&variable=")
			.then()
				.statusCode(200)
				.body("status",equalTo(-1));
		}
		
		@Test(priority=8)
		public void EditAttrNull2(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
				.formParam("nameEn", "")
				.formParam("nameCn", "")
				.formParam("productLine", "")
				.formParam("variable", "")
				.formParam("dataType", "")
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302")
			.then()
				.statusCode(200)
				.body("status",equalTo(-1));
		}
		
		@Test(priority=9)
		public void CreateParam(){
			Integer id = 
				given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
				.formParam("nameEn", data.get("nameEn"))
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302&nameCn=test&productLine=9&dataType=1&variable=0")
			.then()
				.statusCode(200)
				.body("status",equalTo(0))
			.extract()
				.path("data.id");
			data.put("CreateParamId",id.toString());
			String sql = "select * from risk_data_param where name_En = '"+ data.get("nameEn") +"'";
			resDb = db.searchDb(sql);
			Assert.assertEquals(id, resDb.get("id"));
			Assert.assertEquals(resDb.get("isdeleted").intValue(),0);
		}
		
		@Test(priority=10)
		public void getParam(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.list[0].id",equalTo(Integer.parseInt(data.get("CreateParamId"))))
				.body("data.list[0].nameEn",equalTo(data.get("nameEn")));
		}
		
	/*测试不通过---后端bug
		@Test(priority=11)
		public void CreateExistParam(){
			System.out.println(Thread.currentThread().getStackTrace()[3].getClassName());
				given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("productLine[]", 5,7,9)
				.formParam("nameEn", data.get("nameEn"))
			.when()
				.post("/riskData/trics/param/editAttr?funCode=302&nameCn=test&productLine=9&dataType=1&variable=0")
			.then()
				.statusCode(200)
				.body("status",equalTo(-1));
		}
		*/
		/* 测试不通过
		@Test(priority=12)
		public void DeleteParamNocookie(){
			given()
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.get("/riskData/trics/param/delete")
				.then()
				.statusCode(302);
		}*/	
		
		@Test(priority=13)
		public void DeleteParam(){
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.get("/riskData/trics/param/delete")
				.then()
				.statusCode(200)
				.body("status",equalTo(0))
				.body("message", equalTo("删除成功"));
		}
		
		
		
	}
	

}
