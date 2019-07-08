package br.pb.udemycourse.rest.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;

public class BarrigaTest extends BasteTest{
	
	private String TOKEN;
	
	@Before
	public void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "brunobryancarloseduardopereira@lbrazil.com.br");
		login.put("senha", "abcd_123");
		
			    TOKEN = given()
							.body(login)
					   .when()
					   		.post(APILinks.BARRIGA_REST_SIGN_IN)
					   .then()
					   		.statusCode(200)
					   		.extract().path("token");
	}
	
	@Test
	public void naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(401);
	}
	
	@Test
	public void deveIncluirContaComSucesso() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta qualquer\"}")
		.when()
			.post(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveAlterarContaComSucesso() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta atualizada\"}")
		.when()
			.put(APILinks.BARRIGA_REST_CONTAS + "21849") //solicita o ID da conta alterada
		.then()
			.statusCode(200)
			.body("nome", is("conta atualizada"));
	}
	
	@Test
	public void naoDeveInserirContaComOMesmoNome() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta atualizada\"}")
		.when()
			.post(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
	}
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(21526);
//		mov.setUsuarioId(usuarioId);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao("01/01/2000");
		mov.setData_pagamento("10/05/2010");
		mov.setValor(100f);
		mov.setStatus(true);
		
		
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body(mov)
		.when()
			.post(APILinks.BARRIGA_REST_TRANSACOES)
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{}")
		.when()
			.post(APILinks.BARRIGA_REST_TRANSACOES)
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
					"Data da Movimentação é obrigatório",
					"Data do pagamento é obrigatório",
					"Descrição é obrigatório",
					"Interessado é obrigatório",
					"Valor é obrigatório",
					"Valor deve ser um número",
					"Conta é obrigatório",
					"Situação é obrigatório"		
					))
			;
	}
}