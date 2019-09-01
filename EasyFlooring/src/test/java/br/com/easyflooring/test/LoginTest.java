package br.com.easyflooring.test;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.easyflooring.page.LoginPage;

public class LoginTest {
	
	WebDriver driver;
	
	LoginPage loginPage;
	
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://thiagohml.stsupgradeyourfloors.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		loginPage = new LoginPage(driver);
	}
	
	@Test
	public void fazerLoginComEmailComSubdominioVinculado() {
		loginPage.setUsuario("wayow@simplemail.top");
		loginPage.setSenha("abcd_123");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioLogado("Dashboard");
	}
	
	@Test
	public void fazerLoginComUsuarioComSubdominioVinculado() {
		loginPage.setUsuario("thiagohml");
		loginPage.setSenha("abcd_123");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioLogado("Dashboard");
	}
	
	@Test
	public void fazerLoginComEmailSemSubdominioVinculado() {
		loginPage.setUsuario("differentsubdomain");
		loginPage.setSenha("abcd_123");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioOuSenhaInvalidos("wrong username or password");
		
		loginPage.acessarLink("https://differentsubdomain.stsupgradeyourfloors.com/");
		
		loginPage.setUsuario("thiagohml");
		loginPage.setSenha("abcd_123");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioOuSenhaInvalidos("wrong username or password");
	}
	
	@Test
	public void fazerLoginComOsCamposVazios() throws InterruptedException {
		loginPage.btnLogin();
		
		loginPage.verificarMsgCampoEmailVazio("Please input your username/email!");
		loginPage.verificarMsgCampoSenhaVazio("Please input your Password!");		
	}
	
	@Test
	public void fazerLoginComOCampoEmailVazio() throws InterruptedException {
		loginPage.setSenha("abcd_123");
		loginPage.btnLogin();
		
		loginPage.verificarMsgCampoEmailVazio("Please input your username/email!");
	}
	
	@Test
	public void fazerLoginComOCampoSenhaVazio() throws InterruptedException {
		loginPage.setUsuario("thiagohml");
		loginPage.btnLogin();
		
		loginPage.verificarMsgCampoSenhaVazio("Please input your Password!");
	}
	
	@Test
	public void fazerLoginComUsuarioValidoESenhaInvalida() {
		loginPage.setUsuario("thiagohml");
		loginPage.setSenha("abcd_333");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioOuSenhaInvalidos("wrong username or password");		
	}
	
	@Test
	public void fazerLoginComUsuarioValidoESenhaEmBranco() {
		loginPage.setUsuario("thiagohml");
		loginPage.setSenha("");
		loginPage.btnLogin();
		
		loginPage.verificarMsgCampoSenhaVazio("Please input your Password!");		
	}
	
	@Test
	public void fazerLoginComEmailValidoESenhaEmBranco() {
		loginPage.setUsuario("wayow@simplemail.top");
		loginPage.setSenha("");
		loginPage.btnLogin();
		
		loginPage.verificarMsgCampoSenhaVazio("Please input your Password!");		
	}
	
	@Test
	public void fazerLoginComEmailValidoESenhaInvalida() {
		loginPage.setUsuario("wayow@simplemail.top");
		loginPage.setSenha("abcd_222");
		loginPage.btnLogin();
		
		loginPage.verificarMsgUsuarioOuSenhaInvalidos("wrong username or password");		
	}
	
	@Test
	public void recuperarSenha() {
		loginPage.linkEsqueceuSenha();
		loginPage.setEmailRecuperacaoSenha("email@email.com");
		loginPage.btnRecuperarSenha();
		
		loginPage.verificarMsgRecuperacaoSenha("A recovery password link was sent to your email.");
	}
	
	@Test
	public void recuperarSenhaComOCampoEmailVazio() {
		loginPage.linkEsqueceuSenha();

		loginPage.btnRecuperarSenha();
		
		loginPage.verificarMsgCampoEmailVazio("Please input your email!");		
	}
	
	@After
	public void killDriver() {
		driver.close();
	}	
}