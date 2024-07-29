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
<<<<<<< HEAD
=======

>>>>>>> origin/main
		String email = "jimmy@example.com";
		String password = "jimmy12345";
//	 
		Response response = given().formParam("email", email).formParam("password", password).when()
				.delete("/api/deleteAccount").then().extract().response();

		System.out.println(response.getBody().asPrettyString());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
<<<<<<< HEAD
		Assert.assertEquals(response.jsonPath().getString("message"), "Account deleted!");	

	
=======
		Assert.assertEquals(response.jsonPath().getString("message"), "Account deleted!");
>>>>>>> origin/main
	}
	
	@Test
	public void testNonexistentEmail() {
<<<<<<< HEAD
=======

>>>>>>> origin/main
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
<<<<<<< HEAD


=======
>>>>>>> origin/main
	}
	
	@Test
	public void testMissingEmailParameter() {
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

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
	
	@Test
	public void testMissingPasswordParameter() {
<<<<<<< HEAD
=======

>>>>>>> origin/main
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

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
    
	@Test
	public void testInvalidEmailFormat() {
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

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}
    
	@Test
	public void testIncorrectPassword() {
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

<<<<<<< HEAD

=======
>>>>>>> origin/main
	}




}
