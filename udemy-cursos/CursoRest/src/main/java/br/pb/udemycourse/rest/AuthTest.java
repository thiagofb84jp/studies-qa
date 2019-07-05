package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

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
			.body("name", is("Joao Pessoa"))
			.body("coord.lon", is(-34.86f))
			.body("main.temp", greaterThan(20));
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
		given()
			.log().all()
		.when()
			.get(Constants.API_BASIC_AUTH_WITH_PWD)
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"));
	}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
			.log().all()
			.auth().basic("admin", "senha")
		.when()
			.get(Constants.API_BASIC_AUTH_WITH_PWD)
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"));
	}
	
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		given()
			.log().all()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get(Constants.API_BASIC_AUTH_WITH_PWD2)
		.then()
			.log().all()
			.statusCode(200)
			.body("status", is("logado"));
	}
	
	@Test
	public void deveFazerAutenticacaoComTokenJWT() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "brunobryancarloseduardopereira@lbrazil.com.br");
		login.put("senha", "abcd_123");
		
		//Receber o token do usuário
		String token = given()
						  .log().all()
						  .body(login)
						  .contentType(ContentType.JSON)
					   .when()
					   	  .post(Constants.API_BARRIGA_REST_SIGN_IN)
					   .then()
					   	  .log().all()
						  .statusCode(200)
						  .extract().path("token");
		
		//Obter as contas
		given()
			.log().all()
			.header("Authorization", "JWT " + token)
		.when()
			.get(Constants.API_BARRIGA_REST_ACCOUNTS)
		.then()
			.log().all()
			.statusCode(200)
			.body("nome", hasItem("Conta 001"))
			.body("nome", hasItem("Conta 002"));
	}
	
	@Test
	public void deveAcessarAplicacaoWeb() {
		//Realizar o login no sistema e extrair o cookie
		String cookie = given()
							.log().all()
							.formParam("email", "brunobryancarloseduardopereira@lbrazil.com.br")
							.formParam("senha", "abcd_123")
							.contentType(ContentType.URLENC.withCharset("UTF-8"))
						.when()
							.post(Constants.SEU_BARRIGA_LOGIN)
						.then()
							.log().all()
							.statusCode(200)
							.extract().header("set-cookie")
						;
		
		cookie = cookie.split("=")[1].split(";")[0];
		System.out.println(cookie);
		
		//Obter as contas
		String body = given()
						  .log().all()
						  .cookie("connect.sid", cookie)
					  .when()
					  	  .get(Constants.SEU_BARRIGA_CONTAS)
					  .then()
					  	  .log().all()
					  	  .statusCode(200)
					  	  .body("html.body.table.tbody.tr[0].td[0]", is("Conta 002"))
					  	  .extract().body().asString()
			;
		
		System.out.println("/****************** Extracting Xpath **************************/");
		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));
	}
}