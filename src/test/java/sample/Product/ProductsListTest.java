package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductsListTest {

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
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	}

	public void testMinimumProducts() {
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Products list is empty");
	}

	@Test
	public void testResponseTime() {
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
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
		Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"),
				"Product category is missing");
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
	public void testFilterByCategory() {
		String category = "Electronics";
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertFalse(
				response.jsonPath().getList("products.findAll { it.category == '" + category + "' }").isEmpty(),
				"No products found for the category");
	}
     
	
	@Test
	public void testInvalidEndpoint() {
		Response responsen = RestAssured.given().get("/api/productsList111");
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
