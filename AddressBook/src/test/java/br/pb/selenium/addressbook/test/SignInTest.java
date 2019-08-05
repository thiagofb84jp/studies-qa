package br.pb.selenium.addressbook.test;

import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarEmail;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarSenha;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.pb.selenium.addressbook.pages.SignInPage;

public class SignInTest {
	
	private static final String URL_ADDRESS_BOOK = "http://a.testaddressbook.com/";
	
	public String strEmail = "samuelthalesaragao@tilapiareal.com.br";
	public String strSenha = "7i7f4q0BHI";
	
	public String strInvLogin = gerarEmail();
	public String strInvSenha = gerarSenha();
	
	WebDriver driver;
	SignInPage objSignInPage;
	
	@Before
	public void setup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(URL_ADDRESS_BOOK);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		objSignInPage = new SignInPage(driver);
		objSignInPage.clickLinkSignIn();
	}
	
	@Test
	public void acessarComTodosOsCamposVazios() {
		objSignInPage.clickBtnSignIn();
		
		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@Test
	public void acessarComCampoEmailVazio() {
		objSignInPage.setEmail("");
		objSignInPage.setPassword(strInvSenha);
		objSignInPage.clickBtnSignIn();
		
		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@Test
	public void acessarComCampoSenhaVazio() {
		objSignInPage.setEmail(strInvLogin);
		objSignInPage.setPassword("");
		objSignInPage.clickBtnSignIn();
		
		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@Test
	public void acessarComUsuarioInexistente() {
		objSignInPage.setEmail(gerarEmail());
		objSignInPage.setPassword(gerarSenha());
		objSignInPage.clickBtnSignIn();

		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@Test
	public void acessarSistemaComSucesso() {
		objSignInPage.setEmail(strEmail);
		objSignInPage.setPassword(strSenha);
		objSignInPage.clickBtnSignIn();
		objSignInPage.validarAcessoUsuarioLogado(strEmail, "A simple web app for showing off your testing");
		objSignInPage.clickLinkSignOut();
	}
	
	@Test
	public void acessarComSenhaIncorreta() {
		objSignInPage.setEmail(strEmail);
		objSignInPage.setPassword(strInvSenha);
		objSignInPage.clickBtnSignIn();
		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@After
	public void killDriver() {
		driver.close();
	}
}