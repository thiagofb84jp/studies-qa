package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.http.ContentType;

public class ReqResTest {
	
	
	@BeforeClass
	public static void setup() {
		baseURI = "https://reqres.in";
	}
	
	@Test
	public void adicionarNovoUsuario() {
		UserDTO novoUsuario = new UserDTO("Johnny Marr", "Musician");
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(novoUsuario)
		.when()
			.post("/api/users")
		.then()
			.log().all()
			.statusCode(201)
			.body("name", Matchers.is(novoUsuario.getName()))
			.body("job", Matchers.is(novoUsuario.getJob()))
			.body("id", Matchers.notNullValue())
			.body("createdAt", Matchers.notNullValue())
		;
	}
	
	@Test
	public void consultarUsuario() {
		given()
			.log().all()
		.when()
			.get("/api/users/" + 2)
		.then()
			.log().all()
			.statusCode(200)
			.body("data.id", Matchers.is(2))
			.body("data.email", Matchers.is("janet.weaver@reqres.in"))
			.body("data.first_name", Matchers.is("Janet"))
			.body("data.last_name", Matchers.is("Weaver"))
			.body("data.avatar", Matchers.is("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"))
		;
	}
	
	@Test
	public void consultarUsuarioInexistente() {
		given()
			.log().all()
		.when()
			.get("/api/users/" + 404)
		.then()
			.log().all()
			.statusCode(404)
		;
	}
	
	@Test
	public void consultarListaDeUsuarios() {
		given()
			.log().all()
		.when()
			.get("/api/unknown")
		.then()
			.log().all()
			.statusCode(200)
			.body("page", Matchers.is(1))
			.body("data.name[1]", Matchers.is("fuchsia rose"))
			.body("data.year[1]", Matchers.is(2001))
			.body("data.color[1]", Matchers.is("#C74375"))
			.body("data.pantone_value[1]", Matchers.is("17-2031"))
		;
	}
	
	@Test
	public void atualizarUsuario() {
		UserDTO atualizarUsuario = new UserDTO("Morrissey", "Singer");
		
		given()
			.log().all()
			.contentType(ContentType.JSON)
			.body(atualizarUsuario)
		.when()
			.put("/api/users")
		.then()
			.log().all()
			.statusCode(200)
			.body("name", Matchers.is(atualizarUsuario.getName()))
			.body("job", Matchers.is(atualizarUsuario.getJob()))
			.body("updatedAt", Matchers.notNullValue())
		;
	}
	
	@Test
	public void excluirUsuario() {
		given()
			.log().all()
		.when()
			.delete("/api/users/" + 3)
		.then()
			.log().all()
			.statusCode(204)
		;
	}
}