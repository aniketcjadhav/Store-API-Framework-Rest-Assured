package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.User;
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
	
	@Test
	void testGetUsersSortedAsc()
	{
		Response response=given()
			.pathParam("order", "asc")
		.when()
			.get(Routes.GET_USERS_SORTED)
		.then()
			.statusCode(200)
			.extract().response();
	
		List<Integer> userIds=response.jsonPath().getList("id", Integer.class);
		
		
		assertThat(isSortedAsceding(userIds), is(true));
	}
	
	@Test
	public void testCreateUser()
	{
		User newUser=Payload.userPayload();
				
		int id=given()
			.contentType(ContentType.JSON)
			.body(newUser)
		.when()
			.post(Routes.CREATE_USER)
		.then() 
			.log().body()
			.statusCode(201)
			.body("id", notNullValue())
			.extract().jsonPath().getInt("id");
		
		System.out.println("Generated UserID =:"+ id);

	}
	
	@Test
	public void testUpdateUser()
	{
		int userId=configReader.getIntProperty("userId");
		
		User updateUser=Payload.userPayload();
				
		given()
			.contentType(ContentType.JSON)
			.pathParam("id", userId)
			.body(updateUser)
		.when()
			.put(Routes.UPDATE_USER)
		.then() 
			.log().body()
			.statusCode(200)
			.body("username",equalTo(updateUser.getUsername()));
				
	}
	

	
	@Test
	void testDeleteUser()
	{

		int userId=configReader.getIntProperty("userId");
		
		given()
			.pathParam("id", userId)
		.when()
			.delete(Routes.DELETE_USER)
		.then()
			.statusCode(200);
		}



}
