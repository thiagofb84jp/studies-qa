package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class AuthTest {
	
	@Test
	public void deveAcessarSwapAPI() {
		given()
			.log().all()
		.when()
			.get(Constants.SWAPI_PEOPLE + 1)
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("Luke Skywalker"));
	}
	
	@Test
	public void deveObterClima() {
		given()
			.log().all()
			.queryParam("q", "Joao Pessoa")
			.queryParam("appid", "f58c59a3989265c9359d138d87a01c66")
			.queryParam("units", "metric")
		.when()
			.get(Constants.API_WEATHER)
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("João Pessoa"))
			.body("coord.lon", is(-38.52f))
			.body("main.temp", greaterThan(25f));
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().all()
		.when()
			.get(Constants.API_BASIC_AUTH)
		.then()
			.log().all()
			.statusCode(401);
	}
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		
	}
}