package testcases;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import pojo.Product;
import routes.Routes;

public class ProductDataDrivenTest extends BaseClass{

	//Add new Product Data Driven
	
	@Test(dataProvider ="jsonDataProvider" ,dataProviderClass = utils.DataProviders.class)
	public void testAddNewProduct( Map<String , String> data)
	{
		String title = data.get("title");
		double price = Double.parseDouble(data.get("price"));
		String description = data.get("description");
		String image = data.get("image");
		String category = data.get("category");
		
		Product newProduct=new Product(title,price,description,category,image);

		
		given()
		.contentType(ContentType.JSON)
		.body(newProduct)
		.when()
			.post(Routes.CREATE_PRODUCT)
		.then()
		.statusCode(201)
		.log().body();
	}
}
