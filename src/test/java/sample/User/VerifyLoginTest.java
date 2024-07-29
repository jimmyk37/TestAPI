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
	    Response response =RestAssured.given()
	    		.params("email", "jimmy@example.com","password", "jimmy12345")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getString("message"), "User exists!");
	    Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
	    Assert.assertEquals(response.getContentType(), "application/json");

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
	
	@Test
	public void testIncorrectPassword() {

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example.com","password", "incorrectPassword")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Unauthorized");

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
	
	@Test
	public void testNonexistentUser() {

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example1.com","password", "incorrectPassword")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
	
	@Test
	public void testMissingEmail() {

		Response response =RestAssured.given()
	    		.params("email", "johndoe@example1.com","password", "")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
    
	@Test
	public void testEmptyRequest() {
		Response response =RestAssured.given()
	    		.params("email", "","password", "")
	    		.post("/api/verifyLogin");	    
	    System.out.println(response.getBody().asPrettyString());

	    Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
	    Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}


	


	
}
