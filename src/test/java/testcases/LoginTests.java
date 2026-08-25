package testcases;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import payloads.Payload;
import pojo.Login;
import routes.Routes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class LoginTests extends BaseClass{

	
	@Test
	public void testInvalidUserLogin() {
		
		Login login = Payload.loginPayload();
		
		given()
		 	.log().all()
			.contentType(ContentType.JSON)
			.body(login)
		.when()
			.post(Routes.AUTH_LOGIN)
		.then()
			.log().body()
			.statusCode(400)
			.body(equalTo("username and password are not provided in JSON format"));
		
		
	}
	
	
	@Test
	public void testValidUserLogin() {
		
    
    	String username = configReader.getProperty("username");
      	String password = configReader.getProperty("password");
		
      	Login newLogin=new Login(username,password);
      	System.out.println(newLogin.toString());
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(newLogin)
		.when()
			.post(Routes.AUTH_LOGIN)
		.then() 
			.log().body()
			.statusCode(201)
			.body("token", notNullValue()); 
		
	}
}
