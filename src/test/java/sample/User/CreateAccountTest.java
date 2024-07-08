package sample.User;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import sample.base.BaseTestClass;

public class CreateAccountTest extends BaseTestClass {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://automationexercise.com";

	}

	@Test()
	public void testCreateAccountSuccess() {

		Response response = given().formParam("name", "Jimmy").formParam("email", "jimmy@example.com")
				.formParam("password", "jimmy12345").formParam("title", "Mr").formParam("birth_date", "1")
				.formParam("birth_month", "January").formParam("birth_year", "1990").formParam("firstname", "John")
				.formParam("lastname", "Doe").formParam("company", "Example Corp").formParam("address1", "123 Main St")
				.formParam("address2", "Apt 4B").formParam("country", "United States").formParam("zipcode", "12345")
				.formParam("state", "California").formParam("city", "Los Angeles")
				.formParam("mobile_number", "1234567890").when().post("/api/createAccount").then().extract().response();

		String body = response.getBody().asPrettyString();
		System.out.println("testCreateAccountSuccess" + body);
		Assert.assertEquals(response.jsonPath().getInt("responseCode"), 201);
		Assert.assertEquals(response.jsonPath().getString("message"), "User created!");
		Assert.assertTrue(response.getTime() < 5000L, "Response time is greater than 5 seconds");
		Assert.assertEquals(response.getContentType(), "application/json");

	}

	@Test
	public void testMissingRequiredFields() {

		Response responsen = RestAssured.given()
				.params("name", "John Doe", "email", "johndoe@example.com", "password", "password123", "title", "Mr")
				.post("/api/createAccount");
		String body = responsen.getBody().asPrettyString();
		System.out.println("testMissingRequiredFields" + body);
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 201);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400,
				"Expected status code is 400 Bad Request");
	}

	@Test
	public void testInvalidEmailFormat() {

		Response responsen = RestAssured.given()
				.params("name", "John Doe", "email", "johndoe", "password", "password123", "title", "Mr", "birth_date",
						"1", "birth_month", "January", "birth_year", "1990", "firstname", "John", "lastname", "Doe",
						"company", "Example Corp", "address1", "123 Main St", "address2", "Apt 4B", "country",
						"United States", "zipcode", "12345", "state", "California", "city", "Los Angeles",
						"mobile_number", "9999999999")
				.post("/api/createAccount");
		String body = responsen.getBody().asPrettyString();
		System.out.println("testInvalidEmailFormat" + body);
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 201);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400,
				"Expected status code is 400 Bad Request");
	}

	@Test
	public void testExistingEmail() {

		Response responsen = RestAssured.given()
				.params("name", "John Doe", "email", "johndoe@example.com", "password", "password123", "title", "Mr",
						"birth_date", "1", "birth_month", "January", "birth_year", "1990", "firstname", "John",
						"lastname", "Doe", "company", "Example Corp", "address1", "123 Main St", "address2", "Apt 4B",
						"country", "United States", "zipcode", "12345", "state", "California", "city", "Los Angeles",
						"mobile_number", "9999999999")
				.post("/api/createAccount");
		String body = responsen.getBody().asPrettyString();
		System.out.println("testExistingEmail" + body);
		Assert.assertNotEquals(responsen.jsonPath().getInt("responseCode"), 201);
		Assert.assertEquals(responsen.jsonPath().getInt("responseCode"), 400, "Expected status code is 400 Conflict");
	}
}
