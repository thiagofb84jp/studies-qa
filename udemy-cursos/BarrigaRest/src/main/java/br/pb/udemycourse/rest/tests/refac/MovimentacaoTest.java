package br.pb.udemycourse.rest.tests.refac;

import static br.pb.udemycourse.rest.utils.BarrigaUtils.getIdContaPeloNome;
import static br.pb.udemycourse.rest.utils.BarrigaUtils.getIdMovPelaDescricao;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.core.BasteTest;
import br.pb.udemycourse.rest.tests.Movimentacao;
import br.pb.udemycourse.rest.utils.DateUtils;

public class MovimentacaoTest extends BasteTest{
	
	@Test
	public void deveInserirMovimentacaoComSucesso() {
		Movimentacao mov = getMovimentacaoValida();
		
		given()
			.body(mov)
		.when()
			.post(APILinks.BARRIGA_REST_TRANSACOES)
		.then()
			.statusCode(201);
	}
	
	@Test
	public void deveValidarCamposObrigatoriosMovimentacao() {
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
	public void naoDeveInserirMovimentacaoComDataFutura() {
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
	public void naoDeveRemoverContaComMovimentacao() {
		Integer CONTA_ID = getIdContaPeloNome("Conta com movimentacao");
		
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
	public void deveRemoverMovimentacao() {
		Integer MOV_ID = getIdMovPelaDescricao("Movimentacao para exclusao");
		
		given()
			.pathParam("id", MOV_ID)
		.when()
			.delete(APILinks.BARRIGA_REST_TRANSACOES + "{id}")
		.then()
			.statusCode(204)
		;
	}
	
	private Movimentacao getMovimentacaoValida() {
		Movimentacao mov = new Movimentacao();
		mov.setConta_id(getIdContaPeloNome("Conta para movimentacoes"));
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