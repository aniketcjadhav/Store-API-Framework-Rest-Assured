package testcases;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Payload;
import pojo.Product;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import routes.Routes;
import utils.ConfigReader;

public class ProductTests extends BaseClass{

	@Test
	public void testGetAllProducts() {
		
		given()
		.when()
			.get(Routes.GET_ALL_PRODUCTS)
		.then()
			.statusCode(200)
			.body("size()", greaterThan(0))
			.log().all();
		
	}
	
	@Test
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
	
	@Test
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
	
	@Test
	public void testGetSortedProductsAsc()
	{
		
		Response response = 
				given()
				.pathParam("order", "asc")
				.when()
					.get(Routes.GET_PRODUCTS_SORTED)
				.then()
				.statusCode(200)
				.log().body()
				.extract().response();
				
				List<Integer> idList = response.jsonPath().getList("id");
				
				assertThat(isSortedAsceding(idList), is(true));
	}
	
	@Test
	public void testGetAllCategories()
	{
	
		given()
		.when()
			.get(Routes.GET_ALL_CATEGORIES)
		.then()
		.statusCode(200)
		.body("size()", greaterThan(0))
		.log().body();
		
	}
	
	@Test
	public void testGetProductsByCategory()
	{

		given()
		.pathParam("category", "electronics")  //"electronics","jewelery","men's clothing","women's clothing"
		.when()
			.get(Routes.GET_PRODUCTS_BY_CATEGORY)
		.then()
		.statusCode(200)
		.body("size()", greaterThan(0))
		.body("category",everyItem(notNullValue()))
		.body("category",everyItem(equalTo("electronics")))  //use everyItem to check array 
		.log().body();
	}
	
	@Test
	public void testAddNewProduct()
	{
		
		Product product = Payload.productPayload();
		
		given()
		.contentType(ContentType.JSON)
		.body(product)
		.when() 
			.post(Routes.CREATE_PRODUCT)
		.then()
		.log().body()
		.statusCode(201)
		.body("id", notNullValue())
		.body("title", equalTo( product.getTitle()));
		
		
	}
	
	@Test
	public void testUpdateProduct()
	{
		int id = configReader.getIntProperty("productId");
	
		Product updatedProductDetails = Payload.productPayload();
		
		given()
		.contentType(ContentType.JSON)
		.pathParam("id", id)
		.body(updatedProductDetails)
		.when()
			.put(Routes.UPDATE_PRODUCT)
		.then()
		.statusCode(200)
		.log().body()
		.body("title", equalTo(updatedProductDetails.getTitle()))
		.body("id", is(id));
		
	}
	
	@Test
	public void testDeleteProduct()
	{
		int id = configReader.getIntProperty("productId");
		
		given()
		.pathParam("id", id)
		.when()
			.delete(Routes.DELETE_PRODUCT)
		.then()
		.statusCode(200)
		.log().body();
		
	}

	
	
}

