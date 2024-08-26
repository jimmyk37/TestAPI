package sample.User;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class DeleteAccountTest extends BaseTestClass {

	private static final String DELETE_ACCOUNT_ENDPOINT = "/api/deleteAccount";

	public void testDeleteAccountSuccess() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("email", "jimmy@example.com");
		formParams.put("password", "jimmy12345");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 200);
		Assert.assertEquals(response.jsonPath().getString("message"), "Account deleted!");
	}

	@Test
	public void testNonexistentEmail() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("email", "nonexistent@example.com");
		formParams.put("password", "password123");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
	}

	@Test
	public void testMissingEmailParameter() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("password", "password123");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}

	@Test
	public void testMissingPasswordParameter() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("email", "jimmy@example.com");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}

	@Test
	public void testInvalidEmailFormat() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("email", "invalidemail");
		formParams.put("password", "password123");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Bad Request");
	}

	@Test
	public void testIncorrectPassword() {
		Map<String, String> formParams = new HashMap<>();
		formParams.put("email", "jimmy@example.com");
		formParams.put("password", "wrongpassword");

		Response response = requestHandler.deleteformRequest(DELETE_ACCOUNT_ENDPOINT, formParams);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 401,
				"Expected status code is 401 Unauthorized");
	}

}
