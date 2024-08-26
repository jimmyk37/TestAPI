package sample.User;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class VerifyLoginTest extends BaseTestClass {
	
	
	
	private static final String VERIFY_LOGIN_ENDPOINT = "/api/verifyLogin";

    @Test
    public void testVerifyLoginSuccess() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", "jimmy@example.com");
        formParams.put("password", "jimmy12345");

        Response response = requestHandler.postFormRequest(VERIFY_LOGIN_ENDPOINT, formParams);

        getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 200);
        getSoftAssert().assertEquals(response.jsonPath().getString("message"), "User exists!");
        getSoftAssert().assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
        getSoftAssert().assertEquals(response.getContentType(), "application/json");
    }

    @Test
    public void testIncorrectPassword() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", "johndoe@example.com");
        formParams.put("password", "incorrectPassword");

        Response response = requestHandler.postFormRequest(VERIFY_LOGIN_ENDPOINT, formParams);

        getSoftAssert().assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
        getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Unauthorized");
    }

    @Test
    public void testNonexistentUser() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", "johndoe@example1.com");
        formParams.put("password", "incorrectPassword");

        Response response = requestHandler.postFormRequest(VERIFY_LOGIN_ENDPOINT, formParams);

        getSoftAssert().assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
        getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Not Found");
    }

    @Test
    public void testMissingEmail() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", "johndoe@example1.com");
        formParams.put("password", "");

        Response response = requestHandler.postFormRequest(VERIFY_LOGIN_ENDPOINT, formParams);
        System.out.println("testMissingEmail: " + response.getBody().asPrettyString());

        getSoftAssert().assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
        getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");
    }

    @Test
    public void testEmptyRequest() {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", "");
        formParams.put("password", "");

        Response response = requestHandler.postFormRequest(VERIFY_LOGIN_ENDPOINT, formParams);
        
        getSoftAssert().assertNotEquals(response.jsonPath().getInt("responseCode"), 200);
        getSoftAssert().assertEquals(response.jsonPath().getInt("responseCode"), 404, "Expected status code is 404 Bad Request");
    }
}
