package br.pb.udemycourse.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;

public class FileTest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = Constants.API_REST;
	}
	
	@Test
	public void deveObrigarEnvioArquivo() {
		given()
			.log().all()
		.when()
			.post(Constants.API_UPLOAD)
		.then()
			.log().all()
			.statusCode(404) //Deveria ser 400
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadArquivo() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/users.pdf"))
		.when()
			.post(Constants.API_UPLOAD)
		.then()
			.log().all()
			.statusCode(200)
			.body("name", is("users.pdf"))
			.body("md5", notNullValue())
			.body("size", notNullValue())
		;
	}
	
	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		given()
			.log().all()
			.multiPart("arquivo", new File("src/main/resources/image1mb.jpg"))
		.when()
			.post(Constants.API_UPLOAD)
		.then()
			.log().all()
			.time(lessThan(10000L))
			.statusCode(413)
		;
	}
	
	@Test
	public void deveBaixarArquivo() throws IOException {
		byte[] image = given()
						.log().all()
					  .when()
					  	.get(Constants.API_DOWNLOAD)
					  .then()
					  	.statusCode(200)
					  	.extract().asByteArray() //Extrai o arquivo baixado como um array de bytes
		;
		
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		System.out.println(image.length);
		Assert.assertThat(imagem.length(), lessThan(100000L));
	}
}