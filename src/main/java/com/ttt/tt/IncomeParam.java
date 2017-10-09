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
	
	Boolean debug = true;
	static HashMap<String,String> data = new HashMap<String,String>();
	static HashMap<String,Integer> resDb = new HashMap<String,Integer>();
	
	public void log(String s){
		if(debug)
			System.out.println(s);
	}
	
	@BeforeSuite
	public void prepare(){
		
		log("beforeclass------> prepare.....");
		data.put("name", "admin1");
		data.put("pwd", "123456");
		data.put("nameEn", "rrestassured");
		baseURI = "http://risk.bat.tcredit.com";
		login(data.get("name"),data.get("pwd"),baseURI);
		log(data.get("_risk_user"));
	}
	
	public void login(String username,String passwd,String uri){
		Response res_login=given().formParam("userName",username).formParam("password",passwd).post(uri+"/riskData/trics/doLogin");
		 data.put("_risk_user", res_login.getCookie("_risk_user"));
		 log(data.get("_risk_user"));
		return ;
	}
	@Test(groups="Search")
	public class Search{

		@Test(priority=0)
		public void noneParam(){
			
			log("Search--->noneParam");
			log(data.get("_risk_user"));
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
			
			log("Search---->funCodeParam1");
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
			
			log("Search---->funCodeParam2");
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
			
			log("Search--->funCodeParam3");
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
			
			log("Search---->getParam");
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
			
			log("Search---->getAllInEdit");
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
			
			log("Search---->nameEnSearch");
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&nameEn=restAssured")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("data.count",equalTo(0));
		}
		
		/*todo
		 * page传1001时，后端返回-1查询失败，传1000时，查询成功，list为空；
		 * 如果数据大于一万条，可能会有问题
		 * @Test(priority=7)
		public void PagePara1(){
			
			log("Search---->PagePara1");
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/query?sortType=desc&funCode=301&current=1001")
			.then()
				.statusCode(200)
				.body("status", equalTo(0));
		}*/
		
	}
	
	@Test(groups="Create")
	public class createParam{
		
/*	todo	
 * 运行不通过，后端未判断
 * 		@Test(priority=0)
		public void checkNameEn(){
			
			log("createParam---->checkNameEn");
			given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/checkName")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1));
		}*/
		
		@Test(priority=2)
		public void checkNameEn2(){
			
			log("createParam----->checkNameEn2");
			Integer status = given()
				.cookie("_risk_user",data.get("_risk_user"))
			.when()
				.get("/riskData/trics/param/checkName?nameEn="+data.get("nameEn"))
			.then()
				.statusCode(200)
			.extract()
			.path("status");
			if(status == -1){
				data.put("nameEn", data.get("nameEn")+"_1");
			}
		}
		
		@Test(priority=1)
		public void checkNameEnExist(){
			
			log("createParam----->checkNameEnExist");
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
			
			log("createParam----->EditAttrNocookie");
			given()
			.when()
				.post("/riskData/trics/param/editAttr")
			.then()
				.statusCode(302);
		}
		
		@Test(priority=4)
		public void EditAttr(){
			
			log("createParam----->EditAttr");
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
			
			log("createParam----->EditAttrProline");
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
			
			log("createParam----->EditAttrFuncode");
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
			
			log("createParam----->EditAttrEn");
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
			
			log("createParam----->EditAttrNull");
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
			log("createParam----->EditAttrNull2");
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
			log("createParam----->createParam");
			log("nameEn = "+data.get("nameEn"));
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
			String sql = "select * from risk_data_param where is_deleted=0 and name_En = '"+ data.get("nameEn") +"'";
			resDb = db.searchDb(sql);
			Assert.assertEquals(id, resDb.get("id"));
		}
		
		@Test(priority=10)
		public void getParam(){
			
			log("createParam----->getParam");
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
		
	/*todo
	 * 测试不通过---后端bug
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
	}
	@Test(groups="Submit")
	public class submitParam{
		
		@Test
		public void subParam(){
			
			log("subParam----->subParam");
			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.post("/riskData/trics/param/submit")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("message", equalTo("提交成功"));
			String sql = "select rdp.*,rdi.status,rdi.`used_count` from risk_data_param as rdp left join risk_data_info as rdi on rdp.id = rdi.`source_table_id` where rdp.`is_deleted`=0 and rdi.`company_id`=5 and rdi.`source_id`=3 and rdp.id="+Integer.parseInt(data.get("CreateParamId"));
			resDb = db.searchDb(sql);
			Assert.assertEquals(resDb.get("status").intValue(), 2);//1:可编辑、2:已提交、3:使用中
		}
		
		/*
		@Test
		public void subParamTwice(){
			given()
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.post("/riskData/trics/param/submit")
			.then()
				.statusCode(200)
				.body("status", equalTo(-1))
				.body("message", equalTo(""));
		}*/
	}
	@Test(groups="Reset")
	public class resetParam{
		
		@Test
		public void resetParam(){
			
			log("resetParam----->resetParam");

			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.post("/riskData/trics/param/reset")
			.then()
				.statusCode(200)
				.body("status", equalTo(0))
				.body("message", equalTo("撤回成功"));
			String sql = "select rdp.*,rdi.status,rdi.`used_count` from risk_data_param as rdp left join risk_data_info as rdi on rdp.id = rdi.`source_table_id` where rdp.`is_deleted`=0 and rdi.`company_id`=5 and rdi.`source_id`=3 and rdp.id="+Integer.parseInt(data.get("CreateParamId"));
			resDb = db.searchDb(sql);
			Assert.assertEquals(resDb.get("status").intValue(), 1);
			
		}
	}
	@Test(groups="Delete")
	public class deleteParam{
		
		/* todo
		 * 测试不通过
		@Test(priority=0)
		public void DeleteParamNocookie(){
			given()
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.get("/riskData/trics/param/delete")
			.then()
				.statusCode(302);
		}*/	
		
		@Test(priority=1)
		public void DeleteParam(){
			
			log("deleteParam----->deleteParam");

			given()
				.cookie("_risk_user",data.get("_risk_user"))
				.formParam("id", Integer.parseInt(data.get("CreateParamId")))
			.when()
				.get("/riskData/trics/param/delete")
			.then()
				.statusCode(200)
				.body("status",equalTo(0))
				.body("message", equalTo("删除成功"));
			String sql = "select * from risk_data_param where id = "+ Integer.parseInt(data.get("CreateParamId"));
			resDb = db.searchDb(sql);
			Assert.assertEquals(resDb.get("isdeleted").intValue(),1);
		}	
	}
	
	@AfterSuite
	public void logout(){
		log("Logout....");
		
	}
	

}
