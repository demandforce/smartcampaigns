package com.demandforce;

import com.demandforce.service.SmartCampaignService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SmartCampaignServiceTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SmartCampaignServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SmartCampaignServiceTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testGetSlowAlertMessage()
    {
    	String businessId = "124001690";
    	SmartCampaignService smartCampaignService = new SmartCampaignService();
    	AlertBusiness alertBusiness = smartCampaignService.getSlowAlertMessage(businessId);
    	
    	assertNotNull("alertBusiness is not null", alertBusiness);
    	assertEquals("alertBusiness id is correct", businessId, alertBusiness.getBusinessID());
    	assertEquals("alertBusiness name is correct", "Monitoring Business", alertBusiness.getBusinessName());
    	assertEquals("alertBusiness user email is correct", 1, alertBusiness.getEmails().size());
    }
}
