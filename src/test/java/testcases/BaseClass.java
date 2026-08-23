package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	
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
	
	boolean validateCartDatesWithinRange(List<String> cartDates,String startDate,String endDate)
	{
		LocalDate start = LocalDate.parse(startDate,formatter);

		LocalDate end = LocalDate.parse(endDate,formatter);
		
		for(String cartDate : cartDates)
		{
			LocalDate cart = LocalDate.parse(cartDate.substring(0, 10),formatter);
			if(cart.isBefore(start)|| cart.isAfter(end))
			{
				return false;
			}
		}
		return true;
	}
}
