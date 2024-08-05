package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class ProductsListTest extends BaseTestClass{

	static Response response;
	

	@Test
	public void testGetAllProductsListSuccess() {
		response = requestHandler.sendGetRequest("/api/productsList");
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	}

	public void testMinimumProducts() {
		response = requestHandler.sendGetRequest("/api/productsList");
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Products list is empty");
	}

	@Test
	public void testResponseTime() {		
		response = requestHandler.sendGetRequest("/api/productsList");
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	}

	@Test
	public void testContentType() {		
		response = requestHandler.sendGetRequest("/api/productsList");
		Assert.assertEquals(response.getContentType(), "application/json");
	}

	@Test
	public void testResponseContainsProducts() {	
		response = requestHandler.sendGetRequest("/api/productsList");
		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
		
	}

	@Test
	public void testProductStructure() {
		response = requestHandler.sendGetRequest("/api/productsList");
		getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
		getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
		getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
		getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
		getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"),
				"Product category is missing");		
	}

	@Test
	public void testSpecificProductDetails() {
		response = requestHandler.sendGetRequest("/api/productsList");
		String productName = response.jsonPath().getString("products.find { it.id == 1 }.name");
		String productPrice = response.jsonPath().getString("products.find { it.id == 1 }.price");
		String productBrand = response.jsonPath().getString("products.find { it.id == 1 }.brand");
		String productCategory = response.jsonPath().getString("products.find { it.id == 1 }.category.category");

		getSoftAssert().assertEquals(productName, "Blue Top");
		getSoftAssert().assertEquals(productPrice, "Rs. 500");
		getSoftAssert().assertEquals(productBrand, "Polo");
		getSoftAssert().assertEquals(productCategory, "Tops");
	}

	@Test
	public void testFilterByCategory() {
		response = requestHandler.sendGetRequest("/api/productsList");
		String category = "Electronic";
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 200);
		getSoftAssert().assertTrue(
				response.jsonPath().getList("products.findAll { it.category.category == '" + category + "' }").isEmpty(),
				"No products found for the category");
	}
     
	
	@Test(enabled = false)
	public void testInvalidEndpoint() {		
		Response responsen = requestHandler.sendGetRequest("/api/productsList111");
	    getSoftAssert().assertNotEquals(responsen.getStatusCode(), 200);
	    getSoftAssert().assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

	}
	
	@Test
	public void testUnsupportedMethod() {
		
		Response responsen = RestAssured.given().post("/api/productsList");
		getSoftAssert().assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
		getSoftAssert().assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

	}
}
