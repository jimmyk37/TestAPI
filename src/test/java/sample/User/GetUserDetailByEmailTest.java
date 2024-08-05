package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class GetUserDetailByEmailTest extends BaseTestClass {

	private static final String GET_USER_DETAIL_ENDPOINT = "/api/getUserDetailByEmail";
	

	@Test
	public void testGetUserDetailByEmailSuccess() {
		String email = "jimmy@example.com";

		Response response = requestHandler.sendGetRequestwithformparam(GET_USER_DETAIL_ENDPOINT, "email", email);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		Assert.assertEquals(response.jsonPath().getString("user.email"), email);
	}

	@Test
	public void testNonexistentEmail() {
		String email = "nonexistent@example.com";

		Response response = requestHandler.sendGetRequestwithformparam(GET_USER_DETAIL_ENDPOINT, "email", email);

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	}

	@Test
	public void testMissingEmailParameter() {
		Response response = requestHandler.sendGetRequestwithformparam(GET_USER_DETAIL_ENDPOINT, "email", "");

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}

	@Test
	public void testInvalidEmailFormat() {
		String invalidEmail = "invalidemail";

		Response response = requestHandler.sendGetRequestwithformparam(GET_USER_DETAIL_ENDPOINT, "email", invalidEmail);

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");
	}

}
