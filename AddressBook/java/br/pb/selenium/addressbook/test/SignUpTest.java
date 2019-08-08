package br.pb.selenium.addressbook.test;

import static br.pb.selenium.addressbook.utils.GeradorDeDados.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.pb.selenium.addressbook.pages.SignUpPage;

public class SignUpTest {

	private static final String URL_ADDRESS_BOOK = "http://a.testaddressbook.com/";

	WebDriver driver;
	SignUpPage objSignUpPage;
	
	@Before
	public void setup() throws InterruptedException {
		//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL_ADDRESS_BOOK);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		objSignUpPage = new SignUpPage(driver);
		objSignUpPage.clickLinkSignIn();
		objSignUpPage.clickLinkSignUp();
	}
	
	@Test
	public void acessarPaginaComSucesso() {
		String email = gerarEmail();
		
		objSignUpPage.setEmail(email);
		objSignUpPage.setPassword(gerarSenha());
		objSignUpPage.clickBtnSignIn();
		
		objSignUpPage.verificarMensagemSucesso("Welcome to Address Book");
		objSignUpPage.verificarConfirmacaoEmailAcesso(email);
	}
	
	@Test
	public void acessarSemPreencherNenhumDosCampos() {
		objSignUpPage.clickBtnSignIn();
		objSignUpPage.verificarMensagemFalha("Bad email or password.");
	}
	
	@Test
	public void acessarComCampoEmailSemPreenchimento() {
		String strSenha = gerarSenha();
		
		objSignUpPage.setEmail("");
		objSignUpPage.setPassword(strSenha);
		objSignUpPage.clickBtnSignIn();
	}
	
	@Test
	public void acessarComCampoSenhaSemPreenchimento() {
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