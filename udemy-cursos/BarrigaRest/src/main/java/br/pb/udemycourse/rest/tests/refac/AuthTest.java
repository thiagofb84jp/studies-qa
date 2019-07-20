package br.pb.udemycourse.rest.tests.refac;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;

public class AuthTest extends BasteTest{
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(401);
	}
}