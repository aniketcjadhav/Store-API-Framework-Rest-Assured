package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import routes.Routes;
import utils.ConfigReader;

public class BaseClass {
	
	ConfigReader configReader;
	
	RequestLoggingFilter requestLoggingFilter;
	ResponseLoggingFilter responseLoggingFilter;

	
	@BeforeClass
	public void setUp() throws FileNotFoundException
	{
		RestAssured.baseURI = Routes.Base_URL;
		
		configReader = new ConfigReader();
		
		// Setup filters for logging
	    FileOutputStream fos = new FileOutputStream(".\\logs\\test_logging.log");
	    PrintStream log = new PrintStream(fos, true);
	    
	    requestLoggingFilter = new RequestLoggingFilter(log);
	    responseLoggingFilter = new ResponseLoggingFilter(log);
	    
	    RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

	}
	
	//Helper Methods
	boolean isSortedDesceding( List<Integer> idList)
	{
		for(int i = 0 ; i<idList.size()-1 ; i++)
		{
			if(idList.get(i)<idList.get(i+1))
			{
				return false;
			}
		}
		
		return true;
	}
	
	boolean isSortedAsceding (List<Integer> idList)
	{
		for(int i = 0 ; i<idList.size()-1 ; i++)
		{
			if(idList.get(i)>idList.get(i+1))
			{
				return false;
			}
		}
		
		return true;
	}
}
