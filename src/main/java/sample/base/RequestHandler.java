package sample.base;

import java.util.Map;

import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestHandler {
    
    private SoftAssert softAssert;

    public RequestHandler() {
        this.softAssert = new SoftAssert();
    }

       // Method to make a GET request
    public Response getRequest(String endpoint) {
        RequestSpecification request = RestAssured.given();
        return request.get(endpoint);
    }

       // Method to make a GET request with form parameters
    public Response getFormRequest(String endpoint, Map<String, String> formParams) {
        RequestSpecification request = RestAssured.given();
        request.queryParams(formParams);
        return request.get(endpoint);
    }

    // Method to make a POST request with JSON body
    public Response postRequest(String endpoint, Object body) {
        RequestSpecification request = RestAssured.given();
        request.contentType("application/json");
        request.body(body);
        return request.post(endpoint);
    }
    

    // Method to make a POST request with form parameters
    public Response postFormRequest(String endpoint, Map<String, String> formParams) {
        RequestSpecification request = RestAssured.given();
        request.contentType("application/x-www-form-urlencoded");
        request.formParams(formParams);
        return request.post(endpoint);
    }

    
    // Method to make a PUT request with JSON body
    public Response putRequest(String endpoint, Object body) {
        RequestSpecification request = RestAssured.given();
        request.contentType("application/json");
        request.body(body);
        return request.put(endpoint);
    }

    
    // Method to make a PUT request with form parameters
    public Response putFormRequest(String endpoint, Map<String, String> formParams) {
        RequestSpecification request = RestAssured.given();
        request.contentType("application/x-www-form-urlencoded");
        request.formParams(formParams);
        return request.put(endpoint);
    }

    // Method to make a DELETE request
    public Response deleteformRequest(String endpoint, Map<String, String> formParams) {
        RequestSpecification request = RestAssured.given();
        request.contentType("application/x-www-form-urlencoded");
        request.formParams(formParams);
        return request.delete(endpoint);
    }

    // Get SoftAssert instance
    public SoftAssert getSoftAssert() {
        return softAssert;
    }
}
