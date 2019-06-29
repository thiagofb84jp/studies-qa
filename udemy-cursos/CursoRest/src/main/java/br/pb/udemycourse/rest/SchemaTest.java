package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXParseException;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaTest {
	
	@BeforeClass
	public static void setup() {
		baseURI = Constants.API_REST;
	}
	
	@Test
	public void deveValidarSchemaXML() {
		given()
			.log().all()
		.when()
			.get(Constants.API_USERS_XML)
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
	}
	
	@Test(expected=SAXParseException.class)
	public void naoDeveValidarSchemaXML() {
		given()
			.log().all()
		.when()
			.get(Constants.API_INVALID_USERS_XML)
		.then()
			.log().all()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
	}
	
	@Test
	public void deveValidarSchemaJSON() {
		given()
			.log().all()
		.when()
			.get(Constants.API_USERS)
		.then()
			.log().all()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"));
	}
}