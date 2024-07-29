package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;
import sample.base.RestAssuredLogHelper;

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
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");

	}
	@Test
	public void testSearchProductSuccess() {
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");	

	}
	
	@Test
	public void testContentType() {
<<<<<<< HEAD

	    Assert.assertEquals(response.getContentType(), "application/json");
=======
	    Assert.assertEquals(response.getContentType(), "text/html");
>>>>>>> origin/main

	}
    
	@Test
	public void testResponseContainsProducts() {
<<<<<<< HEAD

=======
>>>>>>> origin/main
		Assert.assertNotNull(response.jsonPath().getList("products"), "Products list is null");
	    
	}
	
	@Test
	public void testProductStructure() {
<<<<<<< HEAD

=======
>>>>>>> origin/main
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("id"), "Product ID is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("name"), "Product name is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("price"), "Product price is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("brand"), "Product brand is missing");
	    Assert.assertTrue(response.jsonPath().getMap("products[0]").containsKey("category"), "Product category is missing");

	}

	@Test
	public void testSpecificProductDetails() {
<<<<<<< HEAD

=======
>>>>>>> origin/main
		String productName = response.jsonPath().getString("products.find { it.id == 1 }.name");
		String productPrice = response.jsonPath().getString("products.find { it.id == 1 }.price");
		String productBrand = response.jsonPath().getString("products.find { it.id == 1 }.brand");
		String productCategory = response.jsonPath().getString("products.find { it.id == 1 }.category");

		Assert.assertEquals(productName, "Blue Top");
		Assert.assertEquals(productPrice, "Rs. 500");
		Assert.assertEquals(productBrand, "Polo");
		Assert.assertEquals(productCategory, "Tops");
<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
	
	@Test
	public void testInvalidProductName() {
<<<<<<< HEAD
		Response responsen = RestAssured.given()
				.param("search_product", "invalidProduct")
				.post("/api/searchProduct");
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");
	    }
=======

		Response responsen = RestAssured.given()
				.param("search_product", "invalidProduct")
				.post("/api/searchProduct");
		Assert.assertEquals(responsen.getStatusCode(), 200);
		Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");

	}
>>>>>>> origin/main
	
	@Test
	public void testUnsupportedMethod() {
		Response responsen = RestAssured.given()
				.param("search_product", "tshirt")
				.get("/api/searchProduct");
<<<<<<< HEAD
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");

=======
		Assert.assertEquals(responsen.getStatusCode(), 200);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Method Not Allowed");
>>>>>>> origin/main

	}
	
	@Test
	public void testMissingRequiredParameter() {
		Response responsen = RestAssured.given()
				.post("/api/searchProduct");

	    Assert.assertEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
<<<<<<< HEAD

=======
	    
>>>>>>> origin/main

	}
	
	@Test
	public void testSpecialCharactersInProductName() {
		Response responsen = RestAssured.given()
				.param("search_product", "!@#$%^&*()")
				.get("/api/searchProduct");
<<<<<<< HEAD
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertTrue(responsen.jsonPath().getList("products").isEmpty(), "Products list is not empty");
=======
		Assert.assertEquals(responsen.getStatusCode(), 200);
	    Assert.assertNull(responsen.jsonPath().getList("products"), "Products list is not empty");
>>>>>>> origin/main

	}




	
}
