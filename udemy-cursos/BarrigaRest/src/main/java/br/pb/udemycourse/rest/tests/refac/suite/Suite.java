package br.pb.udemycourse.rest.tests.refac.suite;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.pb.udemycourse.rest.core.APILinks;
import br.pb.udemycourse.rest.tests.BarrigaTest;
import br.pb.udemycourse.rest.tests.refac.AuthTest;
import br.pb.udemycourse.rest.tests.refac.ContasTest;
import br.pb.udemycourse.rest.tests.refac.MovimentacaoTest;
import br.pb.udemycourse.rest.tests.refac.SaldoTeste;
import io.restassured.RestAssured;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	ContasTest.class,
	MovimentacaoTest.class,
	SaldoTeste.class,
	AuthTest.class
})
public class Suite extends BarrigaTest{
	
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
		
		RestAssured.get(APILinks.BARRIGA_RESET).then().statusCode(200);
	}
}