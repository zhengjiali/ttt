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
	
	@Test(groups="Test1")
	public class Zookkk{
		
		@Test
		public void paraCreate(){
			log("Test--->Zookkk");
		}
	}
	
	@Test(groups="Test2")
	public class TelePara{
		
		@Test(priority=0)
		public void para1Create(){
			log("Test--->TelePara2");
		}
		@Test(priority=1)
		public void para0Create(){
			log("Test--->TelePara0");
		}
	}
	
	@Test(groups="Test3")
	public class AdminPara{
		
		@Test(priority=2)
		public void para0Create(){
			log("Test--->AdminPara2");
		}
		
		@Test(priority=0)
		public void para1Create(){
			log("Test--->AdminPara1");
		}
	}

}
