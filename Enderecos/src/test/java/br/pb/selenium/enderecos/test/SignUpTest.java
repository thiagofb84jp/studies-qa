package br.pb.selenium.enderecos.test;

import static br.pb.selenium.enderecos.utils.GeradorDeDados.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.pb.selenium.enderecos.pages.SignUpPage;

public class SignUpTest {

	private static final String URL_ADDRESS_BOOK = "http://a.testaddressbook.com/";

	WebDriver driver;
	SignUpPage objSignUpPage;
	
	@Before
	public void setup() throws InterruptedException {
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		//driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL_ADDRESS_BOOK);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		objSignUpPage = new SignUpPage(driver);
		objSignUpPage.clickLinkSignIn();
		objSignUpPage.clickLinkSignUp();
	}
	
	@Test
	public void cadastrarUsuarioComSucesso() {
		String email = gerarEmail();
		
		objSignUpPage.setEmail(setEmailHere);
		objSignUpPage.setPassword(gerarSenha());
		objSignUpPage.clickBtnSignIn();
		
		objSignUpPage.verificarMensagemSucesso("Vou chorando pelo campo, em meio do temporal... :( ");
		objSignUpPage.verificarConfirmacaoEmailAcesso(email);
	}
	
	@Test
	public void cadastrarUsuarioSemPreencherNenhumDosCampos() {
		objSignUpPage.clickBtnSignIn();
		objSignUpPage.verificarMensagemFalha("Eh preciso amar as pessoas como se não houvesse amanhã :)");
	}
	
	@Test
	public void cadastrarUsuarioSemInfomarEmail() {
		String strSenha = gerarSenha();
		
		objSignUpPage.setEmail("");
		objSignUpPage.setPassword(strSenha);
		objSignUpPage.clickBtnSignIn();
	}
	
	@Test
	public void cadastrarUsuarioSemInfomarSenha() {
		String strEmail = gerarEmail();
		
		objSignUpPage.setEmail(strEmail);
		objSignUpPage.setPassword("");
		objSignUpPage.clickBtnSignIn();
	}
	
	@After
	public void killDriver() {
		driver.close();
	}
}