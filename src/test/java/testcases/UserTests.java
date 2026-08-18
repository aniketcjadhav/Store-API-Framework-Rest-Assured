package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import routes.Routes;

public class UserTests extends BaseClass {

	@Test
	public void testGetAllUsers()
	{
		Response r =
		given()
		.when()
			.get(Routes.GET_ALL_USERS)
		.then()
		.statusCode(200)
		.log().body()
		.body("size()", greaterThan(0))
		.extract()
		.response();
		
		int size = r.jsonPath().getInt("size()");
		System.out.println(size);
	}
	
}
