package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SearchProductTest {

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
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	}
	@Test
	public void testSearchProductSuccess() {
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
	}
	
	@Test
	public void testContentType() {
	    Assert.assertEquals(response.getContentType(), "application/json");
	}
    
	@Test
	public void testResponseContainsProducts() {
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
	}
	
	@Test
	public void testProductStructure() {
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"), "Product category is missing");
	}

	@Test
	public void testSpecificProductDetails() {
		String productName = response.jsonPath().getString("products.find { it.id == 1 }.name");
		String productPrice = response.jsonPath().getString("products.find { it.id == 1 }.price");
		String productBrand = response.jsonPath().getString("products.find { it.id == 1 }.brand");
		String productCategory = response.jsonPath().getString("products.find { it.id == 1 }.category");

		Assert.assertEquals(productName, "Blue Top");
		Assert.assertEquals(productPrice, "Rs. 500");
		Assert.assertEquals(productBrand, "Polo");
		Assert.assertEquals(productCategory, "Tops");
	}
	
	@Test
	public void testInvalidProductName() {
		Response responsen = RestAssured.given()
				.param("search_product", "invalidProduct")
				.post("/api/searchProduct");
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");
	}
	
	@Test
	public void testUnsupportedMethod() {
		Response responsen = RestAssured.given()
				.param("search_product", "tshirt")
				.get("/api/searchProduct");
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");
	}
	
	@Test
	public void testMissingRequiredParameter() {
		Response responsen = RestAssured.given()
				.post("/api/searchProduct");

	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}
	
	@Test
	public void testSpecialCharactersInProductName() {
		Response responsen = RestAssured.given()
				.param("search_product", "!@#$%^&*()")
				.get("/api/searchProduct");
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");
	}




	
}