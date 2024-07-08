package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class VerifyLoginTest extends BaseTestClass {
	
	@BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com";
    }
	
	@Test
	public void testVerifyLoginSuccess() {
		test.log(Status.INFO, "Starting testVerifyLoginSuccess API test...");

	    Response response =RestAssured.given()
	    		.params("email", "jimmy@example.com","password", "jimmy12345")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getString("message"), "User exists!");
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	    Assert.assertEquals(response.getContentType(), "application/json");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testIncorrectPassword() {
		test.log(Status.INFO, "Starting testIncorrectPassword API test...");

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example.com","password", "incorrectPassword")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Unauthorized");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testNonexistentUser() {
		test.log(Status.INFO, "Starting testNonexistentUser API test...");

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example1.com","password", "incorrectPassword")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

		test.log(Status.INFO, "API test completed.");

	}
	
	@Test
	public void testMissingEmail() {
		test.log(Status.INFO, "Starting testMissingEmail API test...");

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example1.com","password", "")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}
    
	@Test
	public void testEmptyRequest() {
		test.log(Status.INFO, "Starting testEmptyRequest API test...");

		Response response =RestAssured.given()
	    		.params("email", "","password", "")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}


	


	
}
