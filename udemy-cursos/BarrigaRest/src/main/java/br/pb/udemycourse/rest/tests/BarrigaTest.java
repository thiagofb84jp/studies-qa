package br.pb.udemycourse.rest.tests;

import static io.restassured.RestAssured.given;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BasteTest{
	
	private static String CONTA_NAME = "Conta " + System.nanoTime();
	
	@Test
	@Ignore
	public void deveIncluirMaisDeUmaContaComSucesso() {
		for (int i = 0; i < 10; i++) {
			given()
				.body("{\"nome\": \""+ CONTA_NAME +"\"}")
			.when()
				.post(APILinks.BARRIGA_REST_CONTAS)
			.then()
				.statusCode(201);
			
			System.out.println(i);
		}
	}
}