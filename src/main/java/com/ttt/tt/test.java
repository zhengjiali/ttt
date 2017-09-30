package com.ttt.tt;
import org.testng.annotations.*;

public class test {
	
	public void log(String s){
		Boolean debug = true;

		if(debug)
			System.out.println(s);
	}
	
	@BeforeSuite
	public void setup(){
		log("BeforeSuite....");
	}
	
	@Test(priority=10)
	public class Zookkk{
		
		@Test(priority=1)
		public void paraCreate(){
			log("Test--->Zookkk");
		}
	}
	
	@Test(priority=1)
	public class TelePara{
		
		@Test(priority=2)
		public void para1Create(){
			log("Test--->TelePara");
		}
	}
	
	@Test(priority=2)
	public class AdminPara{
		
		@Test(priority=3)
		public void para2Create(){
			log("Test--->AdminPara");
		}
	}

}
