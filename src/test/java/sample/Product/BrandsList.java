package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class BrandsList extends BaseTestClass {

	static Response response;

	@BeforeClass
	public void setup() {
		
		RestAssured.baseURI = "https://automationexercise.com";
		response = RestAssured.given().get("/api/brandsList");
		String body = response.getBody().asPrettyString();
//		System.out.println(body);
	}

	@Test
<<<<<<< HEAD
	public void testGetAllBrandsListSuccess() { 
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	}

	public void testMinimumBrands() {
		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Brand list is empty");
=======
	public void testGetAllBrandsListSuccess() {

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);

	}

	public void testMinimumBrands() {

		Assert.assertFalse(response.jsonPath().getList("products").isEmpty(), "Brand list is empty");

>>>>>>> origin/main
	}

	@Test
	public void testResponseTime() {
<<<<<<< HEAD
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
=======

		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");

>>>>>>> origin/main
	}

	@Test
	public void testContentType() {
<<<<<<< HEAD
		Assert.assertEquals(response.getContentType(), "text/html; charset=utf-8");
=======

		Assert.assertEquals(response.getContentType(), "text/html; charset=utf-8");

>>>>>>> origin/main
	}

	@Test
	public void testResponseContainsBrands() {
<<<<<<< HEAD
		Assert.assertNotNull(response.jsonPath().getList("brands"), "Brand list is null");
		
=======

		Assert.assertNotNull(response.jsonPath().getList("brands"), "Brand list is null");

>>>>>>> origin/main
	}

	@Test
	public void testProductStructure() {
<<<<<<< HEAD
		
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("id"), "Brand ID is missing");
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("brand"), "Brand name is missing");
=======

		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("id"), "Brand ID is missing");
		Assert.assertTrue(response.jsonPath().getMap("brands[0]").containsKey("brand"), "Brand name is missing");

>>>>>>> origin/main
	}

	@Test
	public void testSpecificBrandDetails() {
<<<<<<< HEAD
		String brandName = response.jsonPath().getString("brands.find { it.id == 3 }.brand");
		Assert.assertEquals(brandName, "Madame");
=======

		String brandName = response.jsonPath().getString("brands.find { it.id == 3 }.brand");
		Assert.assertEquals(brandName, "Madame");

>>>>>>> origin/main
	}

	public void testInvalidEndpoint() {
<<<<<<< HEAD
		Response responsen = RestAssured.given().get("/api/brandsList1111");
	    Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
=======

		Response responsen = RestAssured.given().get("/api/brandsList1111");
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

>>>>>>> origin/main
	}

	public void testUnsupportedMethod() {
<<<<<<< HEAD
		Response responsen = RestAssured.given().post("/api/brandsList");
	    Assert.assertNotEquals(responsen.getStatusCode(), 200);
	    Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Not Found");
=======

		Response responsen = RestAssured.given().post("/api/brandsList");
		Assert.assertNotEquals(responsen.getStatusCode(), 200);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Not Found");

>>>>>>> origin/main
	}

}
