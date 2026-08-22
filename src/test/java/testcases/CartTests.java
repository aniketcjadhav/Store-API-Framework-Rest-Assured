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
}
