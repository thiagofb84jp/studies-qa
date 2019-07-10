package br.pb.udemycourse.rest.tests;

import static io.restassured.RestAssured.given;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void t01_naoDeveAcessarAPISemToken() {
		given()
		.when()
			.get(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(401);
	}
	
	@Test
	public void t02_deveIncluirContaComSucesso() {
		given()
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta qualquer\"}")
		.when()
			.post(APILinks.BARRIGA_REST_CONTAS)
		.then()
			.statusCode(201);
	}
	
	@Test
	@Ignore
	public void deveIncluirMaisDeUmaContaComSucesso() {
		for (int i = 0; i < 10; i++) {
			String nomeConta = "Conta " + RandomStringUtils.randomNumeric(3);
			
			given()
				.header("Authorization", "JWT " + TOKEN)
				.body("{\"nome\": \""+ nomeConta +"\"}")
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
			.header("Authorization", "JWT " + TOKEN)
			.body("{\"nome\": \"conta atualizada\"}")
		.when()
			.put(APILinks.BARRIGA_REST_CONTAS + "21849") //solicita o ID da conta alterada
		.then()
			.statusCode(200)
			.body("nome", is("conta atualizada"));
	}
	
	@Test
	public void t04_naoDeveInserirContaComOMesmoNome() {
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
	public void t05_deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
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
	public void t06_deveValidarCamposObrigatoriosMovimentacao() {
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
	
	@Test
	public void t07_naoDeveInserirMovimentacaoComDataFutura() {
		Movimentacao mov = getMovimentacaoValida();
		
		mov.setData_transacao(genDayAfterAcutalDate());
		
		given()
			.header("Authorization", "JWT " + TOKEN)
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
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.delete(APILinks.BARRIGA_REST_CONTAS + "21526")
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
		;
	}
	
	@Test
	public void t09_deveCalcularSaldoContas() {
		given()
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.get(APILinks.BARRIGA_REST_SALDO)
		.then()
			.statusCode(200)
			.body("find{it.conta_id == 21526}.saldo", is("1100.00"))
		;
	}
	
	@Test
	public void t10_deveDeveRemoverMovimentacao() {
		given()
			.header("Authorization", "JWT " + TOKEN)
		.when()
			.delete(APILinks.BARRIGA_REST_TRANSACOES + "14907")
		.then()
			.statusCode(204)
		;
	}
		
	private Movimentacao getMovimentacaoValida() {
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
		
		return mov;
	}
	
	private static String genDayAfterAcutalDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dateObject = new Date();
		dateFormat.format(dateObject);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, + 1);

		return dateFormat.format(cal.getTime()).toString();
	}
}