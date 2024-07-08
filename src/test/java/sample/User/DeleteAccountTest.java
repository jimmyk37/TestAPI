package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class DeleteAccountTest extends BaseTestClass {
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
	}

	@Test
	public void testDeleteAccountSuccess() {
		test.log(Status.INFO, "Starting testDeleteAccountSuccess API test...");

		String email = "jimmy@example.com";
		String password = "password123";
//	 
		Response response = given().formParam("email", email).formParam("password", password).when()
				.delete("/api/deleteAccount").then().extract().response();

		System.out.println(response.getBody().asPrettyString());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getString("message"), "Account deleted!");	

		test.log(Status.INFO, "API test completed.");
	
	}
	
	@Test
	public void testNonexistentEmail() {
		test.log(Status.INFO, "Starting testNonexistentEmail API test...");

	    String email = "nonexistent@example.com";
	    String password = "password123";

	    Response response =
	        given().
	            formParam("email", email).
	            formParam("password", password).
	        when().
	            delete("/api/deleteAccount").
	        then().
	            extract().response();

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testMissingEmailParameter() {
		test.log(Status.INFO, "Starting testMissingEmailParameter API test...");

	    String password = "password123";

	    Response response =
	        given().
	            formParam("password", password).
	        when().
	            delete("/api/deleteAccount").
	        then().
	            extract().response();

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testMissingPasswordParameter() {
		test.log(Status.INFO, "Starting testMissingPasswordParameter API test...");

	    String email = "jimmy@example.com";

	    Response response =
	        given().
	            formParam("email", email).
	        when().
	            delete("/api/deleteAccount").
	        then().
	            extract().response();

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}
    
	@Test
	public void testInvalidEmailFormat() {
		test.log(Status.INFO, "Starting testInvalidEmailFormat API test...");

	    String email = "invalidemail";
	    String password = "password123";

	    Response response =
	        given().
	            formParam("email", email).
	            formParam("password", password).
	        when().
	            delete("/api/deleteAccount").
	        then().
	            extract().response();

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}
    
	@Test
	public void testIncorrectPassword() {
		test.log(Status.INFO, "Starting testIncorrectPassword API test...");

	    String email = "jimmy@example.com";
	    String password = "wrongpassword";

	    Response response =
	        given().
	            formParam("email", email).
	            formParam("password", password).
	        when().
	            delete("/api/deleteAccount").
	        then().
	            extract().response();

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 401, "Expected status code is 401 Unauthorized");

		test.log(Status.INFO, "API test completed.");

	}




}
