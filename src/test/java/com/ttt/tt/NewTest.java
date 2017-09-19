package com.ttt.tt;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NewTest {
  
	@Test()
    public void testEmailGenerator() {

        RandomEmailGenerator obj = new RandomEmailGenerator();
        String email = obj.generate();

        Assert.assertNotNull(email);
        Assert.assertEquals(email, "948037694@qq.com");

    }
	
}
