package br.pb.selenium.enderecos.test;

import static br.pb.selenium.enderecos.utils.GeradorDeDados.gerarEmail;
import static br.pb.selenium.enderecos.utils.GeradorDeDados.gerarSenha;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.pb.selenium.enderecos.pages.SignInPage;

public class SignInTest {
	
	private static final String URL_ADDRESS_BOOK = "http://a.testaddressbook.com/";
	
	public String strEmail = "<por_o_email_do_seu_usuario_recem_criado>";
	public String strSenha = "<por_a_senha_aqui_do_seu_usuario>";
	
	public String strInvLogin = "<dica_que_tal_chamar_o_gerador_de_dados?>";
	public String strInvSenha = "<dica_que_tal_chamar_o_gerador_de_dados?>";
	
	WebDriver driver;
	SignInPage objSignInPage;
	
	@Before
	public void setup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		//driver = new ChromeDriver(); //Comentei este trecho, não sei porquê, nem lembro mais :(
		driver.manage().window().maximize();
		driver.get(URL_ADDRESS_BOOK);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		objSignInPage = new SignInPage(driver);
		objSignInPage.clickLinkSignIn();
	}
	
	@Test
	public void acessarSistemaComTodosOsCamposVazios() {
		objSignInPage.clickBtnSignIn();
		
		objSignInPage.validarMsgFalha("Você tem fome de quê? Você tem sede de quê?");
	}
	
	@Test
	public void acessarSistemaComCampoEmailVazio() {
		objSignInPage.validarMsgFalha("Bad email or password.");
	}
	
	@Test
	public void acessarSistemaComCampoSenhaVazio() {
		objSignInPage.setEmail(strInvLogin);
	}
	
	@Test
	public void acessarSistemaComUsuarioInexistente() {
		objSignInPage.setPassword(gerarSenha());
	}
	
	@Test
	public void acessarSistemaComSucesso() {
		objSignInPage.validarAcessoUsuarioLogado(strEmail, "A simple web app for showing off your testing");
		objSignInPage.clickLinkSignOut();
	}
	
	@Test
	public void acessarSistemaComSenhaIncorreta() {
		objSignInPage.clickBtnSignIn();
	}
	
	@After
	public void killDriver() {
		driver.close();
	}
}