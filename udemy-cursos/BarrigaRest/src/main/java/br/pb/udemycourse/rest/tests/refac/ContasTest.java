package br.pb.udemycourse.rest.tests.refac;

import static br.pb.udemycourse.rest.utils.BarrigaUtils.getIdContaPeloNome;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;

public class ContasTest extends BasteTest{
	
	private static String CONTA_NAME = "Conta " + System.nanoTime();
	
	@Test
	public void deveIncluirContaComSucesso() {
					given()
						.body("{\"nome\": \""+ CONTA_NAME +"\"}")
					.when()
						.post(APILinks.BARRIGA_REST_CONTAS)
					.then()
						.statusCode(201)
						.body("nome", is(CONTA_NAME));
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		Integer CONTA_ID = getIdContaPeloNome("Conta para alterar");
		
		given()
			.body("{\"nome\": \"Conta alterada\"}")
			.pathParam("id", CONTA_ID)
		.when()
			.put(APILinks.BARRIGA_REST_CONTAS + "{id}") //solicita o ID da conta alterada
		.then()
			.statusCode(200)
			.body("nome", is("Conta alterada"));
	}
	
	@Test
	public void naoDeveInserirContaComOMesmoNome() {
		given()
			.body("{\"nome\": \"Conta mesmo nome\"}")
		.when()
			.post(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
	}
}