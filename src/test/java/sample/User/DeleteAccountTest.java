package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteAccountTest {
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
	}

	@Test
	public void testDeleteAccountSuccess() {
		String email = "jimmy@example.com";
		String password = "password123";
//	 
		Response response = given().formParam("email", email).formParam("password", password).when()
				.delete("/api/deleteAccount").then().extract().response();

		System.out.println(response.getBody().asPrettyString());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getString("message"), "Account deleted!");		
	}
	
	@Test
	public void testNonexistentEmail() {
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
	}
	
	@Test
	public void testMissingPasswordParameter() {
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
	}




}
