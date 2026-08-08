package testcases;

import java.util.List;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import routes.Routes;
import utils.ConfigReader;

public class BaseClass {
	
	ConfigReader configReader;
	@BeforeClass
	public void setUp()
	{
		RestAssured.baseURI = Routes.Base_URL;
		
		configReader = new ConfigReader();
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
