package sample.Product;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class BrandsList extends BaseTestClass {

	static Response response;

	

	@Test
	public void testGetAllBrandsListSuccess() {
		response = requestHandler.getRequest("/api/brandsList");
		getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 200);
		getSoftAssert().assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		getSoftAssert().assertEquals(response.getContentType(), "text/html; charset=utf-8");
		getSoftAssert().assertNotNull(response.jsonPath().getList("brands"), "Brand list is null");
		getSoftAssert().assertTrue(response.jsonPath().getMap("brands[0]").containsKey("id"), "Brand ID is missing");
		getSoftAssert().assertTrue(response.jsonPath().getMap("brands[0]").containsKey("brand"), "Brand name is missing");
		String brandName = response.jsonPath().getString("brands.find { it.id == 3 }.brand");
		getSoftAssert().assertEquals(brandName, "Madame");

	}
    @Test
	public void testInvalidEndpoint() {

		Response responsen = requestHandler.getRequest("/api/brandsList1111");
		Assert.assertEquals(responsen.getStatusCode(), 404, "Expected status code is 404 Not Found");
		
	}
 
    
    @Test
	public void testUnsupportedMethod() {
		Response responsen = RestAssured.given().post("/api/brandsList");
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 405, "Expected status code is 405 Not Found");
		
	}

}
