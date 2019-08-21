package br.pb.selenium.addressbook.test;

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

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class EnderecoTesteAntesDoPageObjects {
	
	WebDriver driver;
	
	@Test
	public void adicionarEndereco() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://a.testaddressbook.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.findElement(By.id("sign-in")).click();

		driver.findElement(By.id("session_email")).clear();
		driver.findElement(By.id("session_email")).click();
		driver.findElement(By.id("session_email")).sendKeys("thiago@email.com");
		
		driver.findElement(By.id("session_password")).clear();
		driver.findElement(By.id("session_password")).click();
		driver.findElement(By.id("session_password")).sendKeys("abcd_123");
		
		driver.findElement(By.name("commit")).click();
		
		driver.findElement(By.xpath("//a[@class='nav-item nav-link' and contains(text(), 'Addresses')]")).click();
		
		driver.findElement(By.xpath("//a[@class='row justify-content-center' and contains(text(), 'New Address')]")).click();
		
		driver.findElement(By.id("address_first_name")).clear();
		driver.findElement(By.id("address_first_name")).click();
		driver.findElement(By.id("address_first_name")).sendKeys(gerarPrimeiroNome());

		driver.findElement(By.id("address_last_name")).clear();
		driver.findElement(By.id("address_last_name")).click();
		driver.findElement(By.id("address_last_name")).sendKeys(gerarUltimoNome());
		
		driver.findElement(By.id("address_street_address")).clear();
		driver.findElement(By.id("address_street_address")).click();
		driver.findElement(By.id("address_street_address")).sendKeys(gerarEndereco());

		driver.findElement(By.id("address_secondary_address")).clear();
		driver.findElement(By.id("address_secondary_address")).click();
		driver.findElement(By.id("address_secondary_address")).sendKeys(gerarEndereco());

		driver.findElement(By.id("address_city")).clear();
		driver.findElement(By.id("address_city")).click();
		driver.findElement(By.id("address_city")).sendKeys(gerarCidade());
		
		Select state = new Select(driver.findElement(By.id("address_state")));
		state.selectByVisibleText("New York");
		
		driver.findElement(By.id("address_zip_code")).clear();
		driver.findElement(By.id("address_zip_code")).click();
		driver.findElement(By.id("address_zip_code")).sendKeys(gerarCep());
		
		WebElement country = driver.findElement(By.id("address_country_us"));
		country.click();
		
		driver.findElement(By.id("address_birthday")).clear();
		driver.findElement(By.id("address_birthday")).sendKeys(gerarDataComDiferencaDias(100));
		
		driver.findElement(By.id("address_age")).clear();
		driver.findElement(By.id("address_age")).click();
		driver.findElement(By.id("address_age")).sendKeys(gerarIdade());
		
		driver.findElement(By.id("address_website")).clear();
		driver.findElement(By.id("address_website")).click();
		driver.findElement(By.id("address_website")).sendKeys(gerarWebSite());
		
		driver.findElement(By.id("address_phone")).clear();
		driver.findElement(By.id("address_phone")).click();
		driver.findElement(By.id("address_phone")).sendKeys(gerarTelefone());
		
		WebElement interests1 = driver.findElement(By.id("address_interest_climb"));
		interests1.click();
		
		WebElement interests2 = driver.findElement(By.id("address_interest_dance"));
		interests2.click();
		
		WebElement interests3 = driver.findElement(By.id("address_interest_read"));
		interests3.click();
		
		driver.findElement(By.id("address_note")).clear();
		driver.findElement(By.id("address_note")).click();
		driver.findElement(By.id("address_note")).sendKeys(gerarTextoNota());
		
		driver.findElement(By.name("commit")).click();
		
		String obterTexto = driver.findElement(By.xpath("//div[@class='alert alert-notice' and contains(text(), 'Address was successfully created.')]")).getText();
		Assert.assertEquals("Address was successfully created.", obterTexto);
		
		Thread.sleep(3000);
		
		driver.close();
	}
}