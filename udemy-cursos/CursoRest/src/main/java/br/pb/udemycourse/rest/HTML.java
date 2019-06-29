package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class HTML {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = Constants.API_REST;
	}
	
	@Test
	public void deveFazerBuscasComHTML() {
		given()
			.log().all()
		.when()
			.get(Constants.API_V2_USERS)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
			.appendRootPath("html.body.div.table.tbody")
			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"));
	}
	
	@Test
	public void deveFazerBuscasComXpathEmHTML() {
		given()
			.log().all()
		.when()
			.get(Constants.API_USERS_FORMAT_CLEAN_HTML)
		.then()
			.log().all()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")))
			;
	}
}