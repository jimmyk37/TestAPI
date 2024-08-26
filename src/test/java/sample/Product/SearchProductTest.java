package sample.Product;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import sample.base.BaseTestClass;

public class SearchProductTest extends BaseTestClass{

	static Response response;
	private static final String SEARCH_PRODUCT_ENDPOINT = "/api/searchProduct";

	
	@Test
	public void testResponseTime() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");

	}
	@Test
	public void testSearchProductSuccess() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");	

	}
	
	@Test
	public void testContentType() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
	    Assert.assertEquals(response.getContentType(), "text/html");

	}
    
	@Test
	public void testResponseContainsProducts() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
	    
	}
	
	@Test
	public void testProductStructure() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
	    getSoftAssert().assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"), "Product category is missing");

	}

	@Test
	public void testSpecificProductDetails() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "tshirt");
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
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
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "invalid Product");

		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertTrue(response.jsonPath().getList("products").isEmpty(), "Products list is not empty");

	}
	
	@Test
	public void testUnsupportedMethod() {
		
		response = requestHandler.getRequest(SEARCH_PRODUCT_ENDPOINT);
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

	}
	
	@Test
	public void testMissingRequiredParameter() {
		Map<String, String> param = new HashMap<>();
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);

		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	    

	}
	
	@Test
	public void testSpecialCharactersInProductName() {
		Map<String, String> param = new HashMap<>();
		param.put("search_product", "!@#$%^&*()");
		
		response = requestHandler.postFormRequest(SEARCH_PRODUCT_ENDPOINT, param);
		
		getSoftAssert().assertEquals(response.getStatusCode(), 200);
		getSoftAssert().assertTrue(response.jsonPath().getList("products").isEmpty(), "Products list is not empty");

	}




	
}
