package br.pb.selenium.addressbook.test;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.pb.selenium.addressbook.pages.EnderecoPage;


public class EnderecoTeste {
	
	WebDriver driver;
	
	EnderecoPage endereco;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://a.testaddressbook.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		endereco = new EnderecoPage(driver);		
		endereco.realizarLogin("thiago@email.com", "abcd_123");
	}
	
	@Test
	public void adicionarEndereco() throws InterruptedException {
		endereco.clicarBotaoLogar();
		
		endereco.acessarLinkEnderecos();
		
		endereco.adicionarEndereco();
		
		endereco.adicionarPrimeiroNome();
		
		endereco.adicionarUltimoNome();
				
		endereco.adicionarPrimeiroEndereco();
		
		endereco.adicionarSegundoEndereco();
		
		endereco.adicionarCidade();
		
		endereco.adicionarEstado("New York");

		endereco.adicionarCEP();
		
		endereco.selecionarEUA();
		
		endereco.adicionarDataAniversario();
		
		endereco.adicionarIdade();
		
		endereco.adicionarWebsite();
		
		endereco.adicionarTelefone();
		
		endereco.marcarInteresseAtividadeUm();
		
		endereco.adicionarNota();
		
		endereco.criarEndereco();
		
		endereco.verificarMsgSucesso("Address was successfully created.");
		
		Thread.sleep(3000);
		
		driver.close();
	}
}