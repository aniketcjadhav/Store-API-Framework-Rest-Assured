package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

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
	
	@Test
	public void testGetUserById()
	{
		
		int userID = configReader.getIntProperty("userId");
		
		given()
			.pathParam("id", userID)
		.when()
			.get(Routes.GET_USER_BY_ID)
		.then()
			.statusCode(200)
			.log().body()
			.body("id", equalTo(userID));
		
	}	
	
	@Test
	public void testGetUsersWithLimit()
	{
		int limit=configReader.getIntProperty("limit");
		given()
			.pathParam("limit",limit)
		.when()
			.get(Routes.GET_USERS_WITH_LIMIT)
		.then()
			.statusCode(200)
			.log().body()
			.body("size()",equalTo(limit));

	}
	
	@Test
	void testGetUsersSorted()
	{
		Response response =
		given()
			.pathParam("order", "desc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		List<Integer> list = response.jsonPath().getList("id");
		
		assertThat(isSortedDesceding(list), is(true));
		
	}	
}
