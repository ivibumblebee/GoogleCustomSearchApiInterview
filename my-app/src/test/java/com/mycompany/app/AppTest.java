package com.mycompany.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testURL1() throws UnsupportedEncodingException
    {
    	assertNotNull(App.getQuery("sample_input"));
    }
    
    @Test
    public void testURL2() throws UnsupportedEncodingException
    {
    	assertNotNull(App.getQuery("sample input"));
    	assertEquals(App.getQuery("sample input"), "sample+input");
    }

}
