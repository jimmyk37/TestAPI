package sample.User;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class CreateAccountTest extends BaseTestClass {

	private static final String CREATE_ACCOUNT_ENDPOINT = "/api/createAccount";

	@Test()
	public void testCreateAccountSuccess() {

		Map<String, String> formParams = new HashMap<>();
        formParams.put("name", "Jimmy");
        formParams.put("email", "jimmy@example.com");
        formParams.put("password", "jimmy12345");
        formParams.put("title", "Mr");
        formParams.put("birth_date", "1");
        formParams.put("birth_month", "January");
        formParams.put("birth_year", "1990");
        formParams.put("firstname", "John");
        formParams.put("lastname", "Doe");
        formParams.put("company", "Example Corp");
        formParams.put("address1", "123 Main St");
        formParams.put("address2", "Apt 4B");
        formParams.put("country", "United States");
        formParams.put("zipcode", "12345");
        formParams.put("state", "California");
        formParams.put("city", "Los Angeles");
        formParams.put("mobile_number", "1234567890");

        Response response = requestHandler.postFormRequest(CREATE_ACCOUNT_ENDPOINT, formParams);
        
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 201);
		Assert.assertEquals(response.jsonPath().getString("message"), "User created!");
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		Assert.assertEquals(response.getContentType(), "application/json");

	}

	@Test
	public void testMissingRequiredFields() {
        
		Map<String, String> params = new HashMap<>();
        params.put("name", "John Doe");
        params.put("email", "johndoe@example.com");
        params.put("password", "password123");
        params.put("title", "Mr");
        
        Response response = requestHandler.postFormRequest(CREATE_ACCOUNT_ENDPOINT, params);
        
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400,
				"Expected status code is 400 Bad Request");
	}

	@Test
	public void testInvalidEmailFormat() {
		
		Map<String, String> params = new HashMap<>();
        params.put("name", "John Doe");
        params.put("email", "johndoe");
        params.put("password", "password123");
        params.put("title", "Mr");
        params.put("birth_date", "1");
        params.put("birth_month", "January");
        params.put("birth_year", "1990");
        params.put("firstname", "John");
        params.put("lastname", "Doe");
        params.put("company", "Example Corp");
        params.put("address1", "123 Main St");
        params.put("address2", "Apt 4B");
        params.put("country", "United States");
        params.put("zipcode", "12345");
        params.put("state", "California");
        params.put("city", "Los Angeles");
        params.put("mobile_number", "9999999999");


        Response response = requestHandler.postFormRequest(CREATE_ACCOUNT_ENDPOINT, params);
        
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400,
				"Expected status code is 400 Bad Request");
	}

	@Test
	public void testExistingEmail() {

		Map<String, String> params = new HashMap<>();
        params.put("name", "John Doe");
        params.put("email", "johndoe@example.com");
        params.put("password", "password123");
        params.put("title", "Mr");
        params.put("birth_date", "1");
        params.put("birth_month", "January");
        params.put("birth_year", "1990");
        params.put("firstname", "John");
        params.put("lastname", "Doe");
        params.put("company", "Example Corp");
        params.put("address1", "123 Main St");
        params.put("address2", "Apt 4B");
        params.put("country", "United States");
        params.put("zipcode", "12345");
        params.put("state", "California");
        params.put("city", "Los Angeles");
        params.put("mobile_number", "9999999999");
        
        Response response = requestHandler.postFormRequest(CREATE_ACCOUNT_ENDPOINT, params);

		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Conflict");
	}
}
