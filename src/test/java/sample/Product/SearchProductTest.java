package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class SearchProductTest extends BaseTestClass{

	static Response response;
	private static final String SEARCH_PRODUCT_ENDPOINT = "/api/searchProduct";

	
	@Test
	public void testResponseTime() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");

	}
	@Test
	public void testSearchProductSuccess() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");	

	}
	
	@Test
	public void testContentType() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
	    Assert.assertEquals(response.getContentType(), "text/html");

	}
    
	@Test
	public void testResponseContainsProducts() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
	    
	}
	
	@Test
	public void testProductStructure() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"), "Product category is missing");

	}

	@Test
	public void testSpecificProductDetails() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
		String productName = response.jsonPath().getString("products.find { it.id == 1 }.name");
		String productPrice = response.jsonPath().getString("products.find { it.id == 1 }.price");
		String productBrand = response.jsonPath().getString("products.find { it.id == 1 }.brand");
		String productCategory = response.jsonPath().getString("products.find { it.id == 1 }.category");

		getSoftAssert().assertEquals(productName, "Blue Top");
		getSoftAssert().assertEquals(productPrice, "Rs. 500");
		getSoftAssert().assertEquals(productBrand, "Polo");
		getSoftAssert().assertEquals(productCategory, "Tops");
	}
	
	@Test
	public void testInvalidProductName() {

		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "invalid Product");
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertTrue(response.jsonPath().getList("products").isEmpty(), "Products list is not empty");

	}
	
	@Test
	public void testUnsupportedMethod() {
		response = requestHandler.sendGetRequestwithformparam(SEARCH_PRODUCT_ENDPOINT, "search_product", "tshirt");
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

	}
	
	@Test
	public void testMissingRequiredParameter() {
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "", "");

		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	    

	}
	
	@Test
	public void testSpecialCharactersInProductName() {
		
		response = requestHandler.sendPostRequest(SEARCH_PRODUCT_ENDPOINT, "search_product", "!@#$%^&*()");
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertTrue(response.jsonPath().getList("products").isEmpty(), "Products list is not empty");

	}




	
}
