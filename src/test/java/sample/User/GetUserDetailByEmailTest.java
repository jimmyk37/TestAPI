package sample.User;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> params = new HashMap<>();
		params.put("email", email);
		Response response = requestHandler.getFormRequest(GET_USER_DETAIL_ENDPOINT, params);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		Assert.assertEquals(response.jsonPath().getString("user.email"), email);
	}

	@Test
	public void testNonexistentEmail() {
		String email = "nonexistent@example.com";
		Map<String, String> params = new HashMap<>();
		params.put("email", email);
		Response response = requestHandler.getFormRequest(GET_USER_DETAIL_ENDPOINT, params);

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	}

	@Test
	public void testMissingEmailParameter() {
		Map<String, String> params = new HashMap<>();
		params.put("email", "");
		Response response = requestHandler.getFormRequest(GET_USER_DETAIL_ENDPOINT, params);

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}

	@Test
	public void testInvalidEmailFormat() {
		String invalidEmail = "invalidemail";
		Map<String, String> params = new HashMap<>();
		params.put("email", invalidEmail);
		Response response = requestHandler.getFormRequest(GET_USER_DETAIL_ENDPOINT, params);

		Assert.assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");
	}

}
