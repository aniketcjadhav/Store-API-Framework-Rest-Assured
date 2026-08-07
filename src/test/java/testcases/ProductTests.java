package testcases;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import routes.Routes;

public class ProductTests extends BaseClass{

//	@Test
	public void testGetAllProducts() {
		
		given()
		.when()
			.get(Routes.GET_ALL_PRODUCTS)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
			.log().all();
		
	}
	
//	@Test
	public void testGetSingleProductById()
	{
		
		int productId = configReader.getIntProperty("productId");
		
		given()
			.pathParam("id", productId)
		.when()
			.get(Routes.GET_PRODUCT_BY_ID)
		.then()
		.statusCode(200)
		.body("id", equalTo(productId))
		.log().body();
	}
	
//	@Test
	public void testGetLimitedProducts()
	{
		int limit = 3;
		
		given()
		.pathParam("limit", limit) //here we are using pathparam instead of query param
		.when()
			.get(Routes.GET_PRODUCTS_WITH_LIMIT)
		.then()
		.statusCode(200)
		.body("size()", equalTo(limit))
		.log().body();
	}
	
	@Test // descending order and validate order by id 
	public void testGetSortedProducts()    //descending order
	{
		Response response = 
		given()
		.pathParam("order", "desc")
		.when()
			.get(Routes.GET_PRODUCTS_SORTED)
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();
		
		List<Integer> idList = response.jsonPath().getList("id");
		
		assertThat(isSortedDesceding(idList), is(true));
		
		
	}
	
}

