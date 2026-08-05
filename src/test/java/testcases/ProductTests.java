package testcases;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import routes.Routes;

public class ProductTests extends BaseClass{

	@Test
	public void testGetAllProducts() {
		
		given()
		.when()
			.get(Routes.GET_ALL_PRODUCTS)
		.then()
			.statusCode(200)
			.log().all();
//			.body("size()", greaterThan(0));
		
	}
	
}

