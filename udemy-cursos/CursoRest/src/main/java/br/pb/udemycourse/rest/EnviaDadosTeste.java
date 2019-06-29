package br.pb.udemycourse.rest;

import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class EnviaDadosTeste {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = Constants.API_REST;
	}
	
	@Test
	public void deveEnviarValorViaQueryXML() {
		given()
			.log().all()
		.when()
			.get(Constants.API_USERS_FORMAT_XML)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML); //Valida o atributo que vem no cabeçalho da resposta
	}
	
	@Test
	public void deveEnviarValorViaQueryJSON() {
		given()
			.log().all()
		.when()
			.get(Constants.API_USERS_FORMAT_JSON)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.JSON); //Valida o atributo que vem no cabeçalho da resposta
	}
	
	@Test
	public void deveEnviarValorViaQueryViaParam() {
		given()
			.log().all()
			.queryParam("format", "xml")
			.queryParam("outra", "coisa")
		.when()
			.get(Constants.API_V2_USERS)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML) //Valida o atributo que vem no cabeçalho da resposta
			.contentType(containsString("utf-8"));
	}
	
	@Test
	public void deveEnviarValorViaHeader() {
		given()
			.log().all()
			.accept(ContentType.XML)
		.when()
			.get(Constants.API_V2_USERS)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.XML) //Valida o atributo que vem no cabeçalho da resposta
			
			;
	}
}