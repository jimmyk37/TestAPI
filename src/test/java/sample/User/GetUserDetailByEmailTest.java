package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class GetUserDetailByEmailTest extends BaseTestClass {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";
	}

	@Test
	public void testGetUserDetailByEmailSuccess() {
		test.log(Status.INFO, "Starting testGetAllBrandsListSuccess API test...");

		String email = "jimmy@example.com";

		Response response = RestAssured.given().param("email", email).get("/api/getUserDetailByEmail");
		System.out.println("testGetUserDetailByEmailSuccess" + response.getBody().asPrettyString());

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		Assert.assertEquals(response.jsonPath().getString("user.email"), email);

		test.log(Status.INFO, "API test completed.");

	}

	@Test
	public void testNonexistentEmail() {
		test.log(Status.INFO, "Starting testGetAllBrandsListSuccess API test...");

		String email = "nonexistent@example.com";

		Response response = RestAssured.given().param("email", email).get("/api/getUserDetailByEmail");
		System.out.println("testNonexistentEmail" + response.getBody().asPrettyString());

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");

		test.log(Status.INFO, "API test completed.");

	}

	@Test
	public void testMissingEmailParameter() {
		test.log(Status.INFO, "Starting testGetAllBrandsListSuccess API test...");

		Response response = RestAssured.given().param("email", "").get("/api/getUserDetailByEmail");
		System.out.println("testMissingEmailParameter" + response.getBody().asPrettyString());
		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}

	@Test
	public void testInvalidEmailFormat() {
		test.log(Status.INFO, "Starting testInvalidEmailFormat API test...");

		String invalidEmail = "invalidemail";

		Response response = RestAssured.given().param("email", invalidEmail).get("/api/getUserDetailByEmail");
		System.out.println("testMissingEmailParameter" + response.getBody().asPrettyString());

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");

		test.log(Status.INFO, "API test completed.");

	}

}
