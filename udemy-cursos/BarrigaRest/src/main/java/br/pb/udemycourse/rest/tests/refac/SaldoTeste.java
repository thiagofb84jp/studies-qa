package br.pb.udemycourse.rest.tests.refac;

import static br.pb.udemycourse.rest.utils.BarrigaUtils.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;

public class SaldoTeste extends BasteTest{
	
	@Test
	public void deveCalcularSaldoContas() {
		Integer CONTA_ID = getIdContaPeloNome("Conta para saldo");
		
		given()
		.when()
			.get(APILinks.BARRIGA_REST_SALDO)
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+ CONTA_ID +"}.saldo", is("534.00"))
		;
	}
}