package com.ttt.tt;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import io.restassured.matcher.RestAssuredMatchers.*;


import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class q {
	
//	@BeforeClass
//	public void beforeClass(){
//		RestAssured.baseURI="https://api.douban.com/v2/book";
//		RestAssured.port=443;
//	}
//	
//	@Test
//	public void testapi1(){
//		get("/1220562").then().body("title", equalTo("满月之夜白鲸现"));
//	}
//	
//	@AfterClass
//	public void afterClass(){
//		System.out.println("After Class!");
//	}
	
	
	@BeforeTest    // 标记指定所有测试进行前呼叫此 method
    public void beforeAllTest(){
        System.out.println("Before All Test"); 
        RestAssured.baseURI="https://api.douban.com/v2";
		RestAssured.port=443;
    }
    
    @BeforeMethod    // 标记指定每个测试进行前呼叫此 method
    public void setUp() {
        System.out.println("Before Each Test Method");

    }

    @Test(groups = { "book" })    // 标记为测试程式，并为分组 group1
    public void group1Test1() {
        System.out.println("book");
		get("/book/1220562").then().body("title", equalTo("满月之夜白鲸现"));
    }
    
    @Test(groups = { "book" })    // 标记为测试程式，并为分组 group1
    public void group1Test2() {
        System.out.println("book");
		get("/book/1220562").then().body("title", equalTo("满月之夜白鲸现k"));
    }

    @Test(groups = { "movie" })    // 标记为测试程式，并为分组 group2
    public void group2Test() {
        System.out.println("movie");
        get("/movie/subject/1764796").then().body("title", equalTo("机器人9号"));
        
    }
    
    @Test(groups = { "music" })    // 标记为测试程式，并为分组 group3
    public void group3Test() {
        System.out.println("music");
        get("/music/26393566").then().body("title", equalTo("演员"));
    }
    
    @AfterMethod   // 标记指定每个测试进行后呼叫此 method
    public void tearDown(){
        System.out.println("After Each Test Method");
    }
    
    @AfterTest   // 标记指定所有测试进行后呼叫此 method
    public void afterAllTest(){
        System.out.println("After All Test");        
    }
    
    @Test(dataProvider = "range-provider")
    public void testIsBetween(int n, int lower,
    int upper, boolean expected)
    {
     System.out.println("Received " + n + " " + lower + "-"
    + upper + " expected: " + expected);
    Assert.assertEquals(expected, isBetween(n, lower, upper));
    }
    
    @DataProvider(name = "range-provider")
    public Object[][] rangeData() {
    int lower = 5;
    int upper = 10;
    return new Object[][] {
    { lower-1, lower, upper, false },
    { lower, lower, upper, true },
    { lower+1, lower, upper, true },
    { upper, lower, upper, true},
    { upper+1, lower, upper, false },
    };
    
    }
    
    public boolean isBetween( int n, int lower,int upper){
     if (n>=lower&&n<=upper){
      return true;
     }else
     {
      return false;
     }    
    }
    

}
