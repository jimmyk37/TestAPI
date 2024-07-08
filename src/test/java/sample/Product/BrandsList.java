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
		test.log(Status.INFO, "Starting testGetAllBrandsListSuccess API test...");
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		test.log(Status.INFO, "API test completed.");
	}

	public void testMinimumBrands() {
		test.log(Status.INFO, "Starting testMinimumBrands API test...");
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Brand list is empty");
		test.log(Status.INFO, "API test testMinimumBrands completed.");
	}

	@Test
	public void testResponseTime() {
		test.log(Status.INFO, "Starting testResponseTime API test...");
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		test.log(Status.INFO, "API test testResponseTime completed.");
	}

	@Test
	public void testContentType() {
		test.log(Status.INFO, "Starting testContentType API test...");
		Assert.assertEquals(response.getContentType(), "text/html; charset=utf-8");
		test.log(Status.INFO, "API test testContentType completed.");
	}

	@Test
	public void testResponseContainsBrands() {
		test.log(Status.INFO, "Starting testResponseContainsBrands API test...");
		Assert.assertNotNull(response.jsonPath().getList("brands"), "Brand list is null");
		test.log(Status.INFO, "API test testResponseContainsBrands completed.");
		
	}

	@Test
	public void testProductStructure() {
		
		test.log(Status.INFO, "Starting testProductStructure API test...");
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("id"), "Brand ID is missing");
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("brand"), "Brand name is missing");
		test.log(Status.INFO, "API test testProductStructure completed.");
	}

	@Test
	public void testSpecificBrandDetails() {
		test.log(Status.INFO, "Starting testSpecificBrandDetails API test...");
		String brandName = response.jsonPath().getString("brands.find { it.id == 3 }.brand");
		Assert.assertEquals(brandName, "Madame");
		test.log(Status.INFO, "API test testSpecificBrandDetails completed.");
	}
	
	public void testInvalidEndpoint() {
		test.log(Status.INFO, "Starting testInvalidEndpoint API test...");
		Response responsen = RestAssured.given().get("/api/brandsList1111");
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	    test.log(Status.INFO, "API test testInvalidEndpoint completed.");
	}
	
	public void testUnsupportedMethod() {
		test.log(Status.INFO, "Starting testUnsupportedMethod API test...");
		Response responsen = RestAssured.given().post("/api/brandsList");
	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Not Found");
	    test.log(Status.INFO, "API test testUnsupportedMethod completed.");
	}
	
	

}
