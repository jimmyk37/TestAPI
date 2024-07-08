package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class ProductsListTest extends BaseTestClass{

	static Response response;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
		response = RestAssured.given().get("/api/productsList");
		String body = response.getBody().asPrettyString();
		System.out.println(body);
	}

	@Test
	public void testGetAllProductsListSuccess() {
		test.log(Status.INFO, "Starting testGetAllProductsListSuccess API test...");
		
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);

		test.log(Status.INFO, "API test completed.");

	}

	public void testMinimumProducts() {
		test.log(Status.INFO, "Starting testMinimumProducts API test...");

		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Products list is empty");
		
		test.log(Status.INFO, "API test completed.");

	}

	@Test
	public void testResponseTime() {
		test.log(Status.INFO, "Starting testResponseTime API test...");
		
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");

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
		Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"),
				"Product category is missing");		

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
	public void testFilterByCategory() {
		test.log(Status.INFO, "Starting testFilterByCategory API test...");
		
		String category = "Electronics";
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertFalse(
				response.jsonPath().getList("products.findAll { it.category == '" + category + "' }").isEmpty(),
				"No products found for the category");
		
		test.log(Status.INFO, "API test completed.");

	}
     
	
	@Test
	public void testInvalidEndpoint() {
		test.log(Status.INFO, "Starting testInvalidEndpoint API test...");
		
		Response responsen = RestAssured.given().get("/api/productsList111");
	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	    
		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testUnsupportedMethod() {
		test.log(Status.INFO, "Starting testUnsupportedMethod API test...");
		
		Response responsen = RestAssured.given().post("/api/productsList");
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");
	    
		test.log(Status.INFO, "API test completed.");

	}
}
