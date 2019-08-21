package br.pb.selenium.addressbook.pages;

import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarCep;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarCidade;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarDataComDiferencaDias;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarEndereco;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarIdade;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarPrimeiroNome;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarTelefone;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarTextoNota;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarUltimoNome;
import static br.pb.selenium.addressbook.utils.GeradorDeDados.gerarWebSite;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class EnderecoPage {
	
	By primeiroNome = By.id("address_first_name");
	By ultimoNome = By.id("address_last_name");
	
	By primeiroEndereco = By.id("address_street_address");
	By segundoEndereco = By.id("address_secondary_address");
	
	
	WebDriver driver;
	
	public EnderecoPage(WebDriver driver) {
		this.driver = driver;
	}

	public void realizarLogin(String email, String senha) {
		driver.findElement(By.id("sign-in")).click();
		
		driver.findElement(By.id("session_email")).clear();
		driver.findElement(By.id("session_email")).click();
		driver.findElement(By.id("session_email")).sendKeys(email);
		
		driver.findElement(By.id("session_password")).clear();
		driver.findElement(By.id("session_password")).click();
		driver.findElement(By.id("session_password")).sendKeys(senha);
	}
	
	public void clicarBotaoLogar() {
		driver.findElement(By.name("commit")).click();
	}
	
	public void acessarLinkEnderecos() {
		driver.findElement(By.xpath("//a[@class='nav-item nav-link' and contains(text(), 'Addresses')]")).click();
	}
	
	public void adicionarEndereco() {
		driver.findElement(By.xpath("//a[@class='row justify-content-center' and contains(text(), 'New Address')]")).click();
	}
	
	public void adicionarPrimeiroNome() {
		driver.findElement(primeiroNome).clear();
		driver.findElement(primeiroNome).click();
		driver.findElement(primeiroNome).sendKeys(gerarPrimeiroNome());		
	}
	
	public void adicionarUltimoNome() {
		driver.findElement(ultimoNome).clear();
		driver.findElement(ultimoNome).click();
		driver.findElement(ultimoNome).sendKeys(gerarUltimoNome());		
	}
	
	public void adicionarPrimeiroEndereco() {
		driver.findElement(primeiroEndereco).clear();
		driver.findElement(primeiroEndereco).click();
		driver.findElement(primeiroEndereco).sendKeys(gerarEndereco());
	}
	
	public void adicionarSegundoEndereco() {
		driver.findElement(segundoEndereco).clear();
		driver.findElement(segundoEndereco).click();
		driver.findElement(segundoEndereco).sendKeys(gerarEndereco());
	}
	
	public void adicionarCidade() {
		driver.findElement(By.id("address_city")).clear();
		driver.findElement(By.id("address_city")).click();
		driver.findElement(By.id("address_city")).sendKeys(gerarCidade());
	}
	
	public void adicionarEstado(String estado) {
		Select state = new Select(driver.findElement(By.id("address_state")));
		state.selectByVisibleText(estado);
	}
	
	public void adicionarCEP() {
		driver.findElement(By.id("address_zip_code")).clear();
		driver.findElement(By.id("address_zip_code")).click();
		driver.findElement(By.id("address_zip_code")).sendKeys(gerarCep());
	}
	
	public void selecionarEUA() {
		WebElement country = driver.findElement(By.id("address_country_us"));
		country.click();
	}
	
	public void selecionarCanada() {
		WebElement country = driver.findElement(By.id("address_country_canada"));
		country.click();		
	}
	
	public void selecionarPais(String pais) {
		if (pais.equals("Canada")) {
			WebElement country = driver.findElement(By.id("address_country_us"));
			country.click();			
		}else {
			WebElement country = driver.findElement(By.id("address_country_canada"));
			country.click();			
		}
	}
	
	public void adicionarDataAniversario() {
		driver.findElement(By.id("address_birthday")).clear();
		driver.findElement(By.id("address_birthday")).sendKeys(gerarDataComDiferencaDias(100));
	}
	
	public void adicionarIdade() {
		driver.findElement(By.id("address_age")).clear();
		driver.findElement(By.id("address_age")).click();
		driver.findElement(By.id("address_age")).sendKeys(gerarIdade());
	}
	
	public void adicionarWebsite() {
		driver.findElement(By.id("address_website")).clear();
		driver.findElement(By.id("address_website")).click();
		driver.findElement(By.id("address_website")).sendKeys(gerarWebSite());
	}
	
	public void adicionarTelefone() {
		driver.findElement(By.id("address_phone")).clear();
		driver.findElement(By.id("address_phone")).click();
		driver.findElement(By.id("address_phone")).sendKeys(gerarTelefone());
	}
	
	public void marcarInteresseAtividadeUm() {
		WebElement interests1 = driver.findElement(By.id("address_interest_climb"));
		interests1.click();
	}
	
	public void marcarInteresseAtividadeDois() {
		WebElement interests2 = driver.findElement(By.id("address_interest_dance"));
		interests2.click();
	}

	public void marcarInteresseAtividadeTres() {
		WebElement interests3 = driver.findElement(By.id("address_interest_read"));
		interests3.click();
	}
	
	public void adicionarNota() {
		driver.findElement(By.id("address_note")).clear();
		driver.findElement(By.id("address_note")).click();
		driver.findElement(By.id("address_note")).sendKeys(gerarTextoNota());
	}
	
	public void criarEndereco() {
		driver.findElement(By.name("commit")).click();
	}
	
	public void verificarMsgSucesso(String msgSucesso) {
		String obterTexto = driver.findElement(By.xpath("//div[@class='alert alert-notice' and contains(text(), 'Address was successfully created.')]")).getText();
		Assert.assertEquals(msgSucesso, obterTexto);
	}
}