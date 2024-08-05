package sample.base;

import java.util.Map;

import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class RequestHandler{
	
	private SoftAssert softAssert;

    public RequestHandler() {
        this.softAssert = new SoftAssert();
    }

	private RequestSpecification getRequestSpecification() {
        return given()
                .header("Content-Type", "application/x-www-form-urlencoded");
    }
	
	public Response sendGetRequest(String endpoint) {
        return getRequestSpecification()
                .when()
                .get(endpoint)
                .then()
                .extract().response();
    }
	
	 public Response sendGetRequestwithformparam(String endpoint, String paramName, String paramValue) {
	        return getRequestSpecification()
	                .param(paramName, paramValue)
	                .when()
	                .get(endpoint)
	                .then()
	                .extract().response();
	    }

    public Response sendPostRequest(String endpoint, String paramName, String paramValue) {
        return getRequestSpecification()
                .param(paramName, paramValue)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }

    public Response sendPostRequestWithParams(String endpoint, Map<String, String> params) {
        return getRequestSpecification()
                .params(params)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }
    
    public Response sendDeleteRequest(String endpoint, Map<String, String> formParams) {
        return getRequestSpecification()
                .formParams(formParams)
                .when()
                .delete(endpoint)
                .then()
                .extract().response();
    }
    
 // Get SoftAssert instance
    public SoftAssert getSoftAssert() {
        return softAssert;
    }
}
