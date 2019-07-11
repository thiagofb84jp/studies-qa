package br.pb.udemycourse.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;
import br.pb.udemycourse.rest.utils.DateUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BarrigaTest extends BasteTest{
	
	private static String CONTA_NAME = "Conta " + System.nanoTime();
	
	private static Integer CONTA_ID;
	
	private static Integer MOV_ID;
	
	@BeforeClass
	public static void login() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("email", "brunobryancarloseduardopereira@lbrazil.com.br");
		login.put("senha", "abcd_123");
		
		String TOKEN = given()
							.body(login)
					   .when()
					   		.post(APILinks.BARRIGA_REST_SIGN_IN)
					   .then()
					   		.statusCode(200)
					   		.extract().path("token");
			    
		RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
	}
	
	@Test
	public void t02_deveIncluirContaComSucesso() {
		CONTA_ID = given()
						.body("{\"nome\": \""+ CONTA_NAME +"\"}")
					.when()
						.post(APILinks.BARRIGA_REST_CONTAS)
					.then()
						.statusCode(201)
						.extract().path("id")
						;
	}
	
	@Test
	@Ignore
	public void deveIncluirMaisDeUmaContaComSucesso() {
		for (int i = 0; i < 10; i++) {
			//String nomeConta = "Conta " + System.nanoTime();
			
			given()
				.body("{\"nome\": \""+ CONTA_NAME +"\"}")
			.when()
				.post(APILinks.BARRIGA_REST_CONTAS)
			.then()
				.statusCode(201);
			
			System.out.println(i);
		}
	}
	
	@Test
	public void t03_deveAlterarContaComSucesso() {
		given()
			.body("{\"nome\": \""+ CONTA_NAME +" atualizada\"}")
			.pathParam("id", CONTA_ID)
		.when()
			.put(APILinks.BARRIGA_REST_CONTAS + "{id}") //solicita o ID da conta alterada
		.then()
			.statusCode(200)
			.body("nome", is(CONTA_NAME +" atualizada"));
	}
	
	@Test
	public void t04_naoDeveInserirContaComOMesmoNome() {
		given()
			.body("{\"nome\": \""+ CONTA_NAME +" atualizada\"}")
		.when()
			.post(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));
	}
	
	@Test
	public void t05_deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		MOV_ID = given()
					.body(mov)
				.when()
					.post(APILinks.BARRIGA_REST_TRANSACOES)
				.then()
					.statusCode(201)
					.extract().path("id")
				;
	}
	
	@Test
	public void t06_deveValidarCamposObrigatoriosMovimentacao() {
		given()
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
	
	@Test
	public void t07_naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov = getMovimentacaoValida();
		
		mov.setData_transacao(DateUtils.getDataComDiferencaDias(2));
		
		given()
			.body(mov)
		.when()
			.post(APILinks.BARRIGA_REST_TRANSACOES)
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
		;
	}
	
	@Test
	public void t08_naoDeveRemoverContaComMovimentacao() {
		given()
			.pathParam("id", CONTA_ID)
		.when()
			.delete(APILinks.BARRIGA_REST_CONTAS + "{id}")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void t09_deveCalcularSaldoContas() {
		given()
		.when()
			.get(APILinks.BARRIGA_REST_SALDO)
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+ CONTA_ID +"}.saldo", is("100.00"))
		;
	}
	
	@Test
	public void t10_deveDeveRemoverMovimentacao() {
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete(APILinks.BARRIGA_REST_TRANSACOES + "{id}")
		.then()
			.statusCode(204)
		;
	}
		
	@Test
	public void t11_naoDeveAcessarAPISemToken() {
		FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
		req.removeHeader("Authorization");
		
		given()
		.when()
			.get(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(401);
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(CONTA_ID);
//		mov.setUsuarioId(usuarioId);
		mov.setDescricao("Descricao da movimentacao");
		mov.setEnvolvido("Envolvido na mov");
		mov.setTipo("REC");
		mov.setData_transacao(DateUtils.getDataComDiferencaDias(-1));
		mov.setData_pagamento(DateUtils.getDataComDiferencaDias(5));
		mov.setValor(100f);
		mov.setStatus(true);
		
		return mov;
	}
}