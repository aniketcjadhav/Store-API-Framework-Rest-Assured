package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	Properties prop;
	private static final String CONFIG_FILE = ".\\src\\test\\resources\\config.properties";
	
	public ConfigReader(){
		 prop = new Properties();
		try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)){
			
			prop.load(fileInputStream);
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
	}
	
	public String getProperty(String key)
	{
		return prop.getProperty(key);
	}
	
	public int getIntProperty(String key)
	{
		return Integer.parseInt(prop.getProperty(key));
	}
}
