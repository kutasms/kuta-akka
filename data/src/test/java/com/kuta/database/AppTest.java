package com.kuta.database;

import java.util.Date;

import org.bson.Document;

import com.kuta.base.util.MD5Util;
import com.kuta.data.mongo.dao.RunningLogDao;
import com.kuta.data.mongo.pojo.LogLevel;
import com.kuta.data.mongo.pojo.RunningLog;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
        assertTrue( true );
    }
}
