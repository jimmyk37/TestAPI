package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class BrandsList extends BaseTestClass{

	static Response response;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
		response = RestAssured.given().get("/api/brandsList");
		String body = response.getBody().asPrettyString();
		System.out.println(body);
	}

	@Test
	public void testGetAllBrandsListSuccess() { 
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	}

	public void testMinimumBrands() {
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Brand list is empty");
	}

	@Test
	public void testResponseTime() {
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	}

	@Test
	public void testContentType() {
		Assert.assertEquals(response.getContentType(), "text/html; charset=utf-8");
	}

	@Test
	public void testResponseContainsBrands() {
		Assert.assertNotNull(response.jsonPath().getList("brands"), "Brand list is null");
		
	}

	@Test
	public void testProductStructure() {
		
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("id"), "Brand ID is missing");
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("brand"), "Brand name is missing");
	}

	@Test
	public void testSpecificBrandDetails() {
		String brandName = response.jsonPath().getString("brands.find { it.id == 3 }.brand");
		Assert.assertEquals(brandName, "Madame");
	}
	
	public void testInvalidEndpoint() {
		Response responsen = RestAssured.given().get("/api/brandsList1111");
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	}
	
	public void testUnsupportedMethod() {
		Response responsen = RestAssured.given().post("/api/brandsList");
	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Not Found");
	}
	
	

}
