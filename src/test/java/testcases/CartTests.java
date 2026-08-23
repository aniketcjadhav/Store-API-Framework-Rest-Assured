package testcases;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Cart;
import routes.Routes;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class CartTests extends BaseClass{

 	@Test
    public void testGetAllCarts() {
    	
 		Response r =
    	given()
    	.when()
    		.get(Routes.GET_ALL_CARTS)
    	.then()
    		.statusCode(200)
    		.log().body()
    		.body("size()", greaterThan(0))
    		.extract().response();
 		/*
 		List<Integer> idlist = r.jsonPath().getList("id");
 		for(int i :idlist)
 		{
 			System.out.println(i);
 		}
 		*/

 		int size = r.jsonPath().getList("id").size();

 		System.out.println("Number of orders: " + size);
    }
	
    @Test
    public void testCreateCart() {
     
    	int userId = configReader.getIntProperty("userId");
    	
    	Cart cart =Payload.cartPayload(userId);
    	
    	given()
    		.contentType(ContentType.JSON)
    		.body(cart)
    	.when()
    		.post(Routes.CREATE_CART)
    	.then()
    		.statusCode(201)
    		.log().body()
    		.body("id", notNullValue())
    		.body("userId", notNullValue())
    	    .body("products.size()", greaterThan(1));
    }
    
    @Test
    public void testUpdateCart() {
        
    	int userId = configReader.getIntProperty("userId");
    	int cartId = configReader.getIntProperty("cartId");
    	
    	Cart updateCart=Payload.cartPayload(userId); //userId passing
    	given()
            .pathParam("id", cartId)
            .contentType(ContentType.JSON)
            .body(updateCart)
            .when()
                .put(Routes.UPDATE_CART)
            .then()
                .statusCode(200)
                .body("id", equalTo(cartId)) 
                .body("userId", notNullValue());
    }
    
    @Test
    public void testDeleteCart() {
    	int cartId = configReader.getIntProperty("cartId");
        given()
            .pathParam("id", cartId)
            .when()
                .delete(Routes.DELETE_CART)
            .then()
                .statusCode(200); // Validate that the response status code is 204 (No Content)
    }
    
    @Test
    public void testGetCartById() {
    	
    	int cartId = configReader.getIntProperty("cartId");
    	
    	given()
    	.pathParam("id", cartId)
    	.when()
    		.get(Routes.GET_CART_BY_ID)
    	.then()
    		.statusCode(200)
    		.log().body()
    		.body("userId", equalTo(cartId));
    	
    }
    
	@Test
    public void testGetCartsByDateRange() {
     
    	 String startDate = configReader.getProperty("startdate");
    	 String endDate = configReader.getProperty("enddate");
    	    
        Response response=given()
            .pathParam("startdate", startDate)
            .pathParam("enddate", endDate)
            .when()
                .get(Routes.GET_CARTS_BY_DATE_RANGE)
            .then()
                .statusCode(200)
                .log().body()
                .body("size()", greaterThan(0)) 
                .extract().response();
        
     // Extract the list of cart dates
        List<String> cartDates = response.jsonPath().getList("date");

        
        assertThat(validateCartDatesWithinRange(cartDates, startDate, endDate), is(true));
        
    }
	
	 @Test
	    public void testGetUserCart() {
	        int userId = configReader.getIntProperty("userId");
	        
	        given()
	            .pathParam("userId", userId)
	        .when()
	            .get(Routes.GET_USER_CART)
	        .then()
	        	.statusCode(200)
	        	.log().body()
	            .body("userId", everyItem(equalTo(userId))); // Validate that the response contains the correct user ID
	    }

	 
	 @Test
	    public void testGetCartsWithLimit() {
	        int limit = configReader.getIntProperty("limit");
	        given()
	            .pathParam("limit", limit)
	        .when()
	            .get(Routes.GET_CARTS_WITH_LIMIT)
	        .then()
	            .statusCode(200)
	            .body("size()", lessThanOrEqualTo(limit)); // Validate that the response size is within the limit
	    }

	 @Test
	    public void testGetCartsSorted() {
	    	Response response = given()
	            .pathParam("order", "desc")
	            .when()
	                .get(Routes.GET_CARTS_SORTED)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)) 
	                .extract().response();
	         
	        
	         List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	         assertThat(isSortedDesceding(cartIds), is(true));
	    }  
	    
	   @Test
	    public void testGetCartsSortedAsc() {
	    	Response response = given()
	            .pathParam("order", "asc")
	            .when()
	                .get(Routes.GET_CARTS_SORTED)
	            .then()
	                .statusCode(200)
	                .body("size()", greaterThan(0)) 
	                .extract().response();
	         
	       
	         List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	         assertThat(isSortedAsceding(cartIds), is(true));
	    }  
	    

}
