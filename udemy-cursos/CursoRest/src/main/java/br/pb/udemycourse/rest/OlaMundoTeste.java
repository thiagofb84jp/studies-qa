package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OlaMundoTeste {
	
	@Test
	public void testOlaMundo() {
		Response response = request(Method.GET, Constants.API_OLA_MUNDO);
		
		Assert.assertTrue(response.getBody().asString().equals("Ola Mundo!"));
		Assert.assertTrue(response.statusCode() == 200);
		Assert.assertTrue("O status code deveria ser 200", response.statusCode() == 200);
		
		Assert.assertEquals(200, response.statusCode());
		
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
	}
	
	@Test
	public void devoConhecerOutrasFormasRestAssured() {
		Response response = request(Method.GET, Constants.API_OLA_MUNDO);
		ValidatableResponse validacao = response.then();
		validacao.statusCode(200);
				
		/************** Design Pattern Rest Assured ************/
		given()
		.when()
			.get(Constants.API_OLA_MUNDO)
		.then()
			//.assertThat()
			.statusCode(200);
	}
	
	@Test
	public void devoConhecerMatchersHamcrest() {
		Assert.assertThat("Mary", is("Mary"));
		Assert.assertThat(128, is(128));
		Assert.assertThat(128, isA(Integer.class));
		Assert.assertThat(128d, isA(Double.class));
		Assert.assertThat(128d, greaterThan(120d));
		Assert.assertThat(128d, lessThan(130d));
		
		List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9);
		assertThat(impares, hasSize(5));
		assertThat(impares, contains(1, 3, 5, 7, 9));
		assertThat(impares, containsInAnyOrder(1, 3, 5, 7, 9));
		assertThat(impares, hasItem(1));
		assertThat(impares, hasItems(1, 5));
		
		assertThat("Maria", is(not("João")));
		assertThat("Maria", not("João"));
		assertThat("Joaquina", anyOf(is("Maria"), is("Joaquina")));
		assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qui")));
	}
	
	@Test
	public void devoValidarBody() {
		String itShouldString = "Ola Mundo!";
		
		given()
		.when()
			.get(Constants.API_OLA_MUNDO)
		.then()
			.statusCode(200)
			.body(is(itShouldString))
			.body(containsString("Mundo"))
			.body(is(not(nullValue())));
	}
}