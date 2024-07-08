package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class SearchProductTest extends BaseTestClass{

	static Response response;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
		response = RestAssured.given()
				.param("search_product", "tshirt")
				.post("/api/searchProduct");
		String body = response.getBody().asPrettyString();
		System.out.println(body);
	}
	
	@Test
	public void testResponseTime() {
		test.log(Status.INFO, "Starting testResponseTime API test...");
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		test.log(Status.INFO, "API test completed.");

	}
	@Test
	public void testSearchProductSuccess() {
		test.log(Status.INFO, "Starting testSearchProductSuccess API test...");

	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");		
		
		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testContentType() {
		test.log(Status.INFO, "Starting testContentType API test...");

	    Assert.assertEquals(response.getContentType(), "application/json");

		test.log(Status.INFO, "API test completed.");

	}
    
	@Test
	public void testResponseContainsProducts() {
		test.log(Status.INFO, "Starting testResponseContainsProducts API test...");

		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");

		test.log(Status.INFO, "API test completed.");
	    
	}
	
	@Test
	public void testProductStructure() {
		test.log(Status.INFO, "Starting testProductStructure API test...");

	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"), "Product category is missing");

		test.log(Status.INFO, "API test completed.");

	}

	@Test
	public void testSpecificProductDetails() {
		test.log(Status.INFO, "Starting testSpecificProductDetails API test...");

		String productName = response.jsonPath().getString("products.find { it.id == 1 }.name");
		String productPrice = response.jsonPath().getString("products.find { it.id == 1 }.price");
		String productBrand = response.jsonPath().getString("products.find { it.id == 1 }.brand");
		String productCategory = response.jsonPath().getString("products.find { it.id == 1 }.category");

		Assert.assertEquals(productName, "Blue Top");
		Assert.assertEquals(productPrice, "Rs. 500");
		Assert.assertEquals(productBrand, "Polo");
		Assert.assertEquals(productCategory, "Tops");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testInvalidProductName() {
		test.log(Status.INFO, "Starting testInvalidProductName API test...");

		Response responsen = RestAssured.given()
				.param("search_product", "invalidProduct")
				.post("/api/searchProduct");
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testUnsupportedMethod() {
		test.log(Status.INFO, "Starting testUnsupportedMethod API test...");

		Response responsen = RestAssured.given()
				.param("search_product", "tshirt")
				.get("/api/searchProduct");
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testMissingRequiredParameter() {
		test.log(Status.INFO, "Starting testMissingRequiredParameter API test...");

		Response responsen = RestAssured.given()
				.post("/api/searchProduct");

	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testSpecialCharactersInProductName() {
		test.log(Status.INFO, "Starting testSpecialCharactersInProductName API test...");

		Response responsen = RestAssured.given()
				.param("search_product", "!@#$%^&*()")
				.get("/api/searchProduct");
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");

		test.log(Status.INFO, "API test completed.");

	}




	
}
