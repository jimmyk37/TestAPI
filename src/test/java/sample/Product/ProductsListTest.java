package sample.Product;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import sample.base.HelperClass;

public class ProductsListTest extends HelperClass{

	static Response response;
	

	@Test
	public void testGetAllProductsListSuccess() {
		response = requestHandler.getRequest("/api/productsList");
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	}

	public void testMinimumProducts() {
		response = requestHandler.getRequest("/api/productsList");
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Products list is empty");
	}

	@Test
	public void testResponseTime() {		
		response = requestHandler.getRequest("/api/productsList");
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	}

	@Test
	public void testContentType() {		
		response = requestHandler.getRequest("/api/productsList");
		Assert.assertEquals(response.getContentType(), "application/json");
	}

	@Test
	public void testResponseContainsProducts() {	
		response = requestHandler.getRequest("/api/productsList");
		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
		
	}

	@Test
	public void testProductStructure() {
		response = given()
		            .when()
		            .get("/api/productsList")
		            .then()
		            .statusCode(200)
		            .time(lessThan(5000L))
		            .extract().response();

		   
		        response.then()
		            .body("products[0]", hasKey("id"))  
		            .body("products[0]", hasKey("name"))  // Check if the "name" key is present
		            .body("products[0]", hasKey("price"))  // Check if the "price" key is present
		            .body("products[0]", hasKey("brand"))  // Check if the "brand" key is present
		            .body("products[0]", hasKey("category"));  // Check if the "category" key is present		
	}

	@Test
	public void testSpecificProductDetails() {
		
        given()
        .when()
        .get("/api/productsList")
        .then()
        .statusCode(200)  // Verify the status code is 200
        .body("products.find { it.id == 1 }.name", equalTo("Blue Top"))  // Verify the product name
        .body("products.find { it.id == 1 }.price", equalTo("Rs. 500"))  // Verify the product price
        .body("products.find { it.id == 1 }.brand", equalTo("Polo"))  // Verify the product brand
        .body("products.find { it.id == 1 }.category.category", equalTo("Tops"));  // Verify the product category
}

	@Test
	public void testFilterByCategory() {
		response = requestHandler.getRequest("/api/productsList");
		String category = "Electronic";
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertTrue(
				response.jsonPath().getList("products.findAll { it.category.category == '" + category + "' }").isEmpty(),
				"No products found for the category");
	}
     
	
	@Test(enabled = false)
	public void testInvalidEndpoint() {		
		Response responsen = requestHandler.getRequest("/api/productsList111");
		Assert.assertNotEquals(responsen.getStatusCode(), 200);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

	}
	
	@Test
	public void testUnsupportedMethod() {
		
		Response responsen = RestAssured.given().post("/api/productsList");
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

	}
}
