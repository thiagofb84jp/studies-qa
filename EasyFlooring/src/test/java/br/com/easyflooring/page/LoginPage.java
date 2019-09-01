package br.com.easyflooring.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	By usuarioNome = By.id("login_name");
	By usuarioSenha = By.id("login_password");
	By btnLogin = By.xpath("//button[@type='submit']");
	By linkEsquecerSenha = By.linkText("Forgot password");
	By btnRecover = By.xpath("//button[@type='submit']");
	By emailRecSenha = By.id("forgot_password_emailAddress");
	
	WebDriver driver;
	
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/***************** Textos ***************************/
	public void setUsuario(String usuario) {
		driver.findElement(usuarioNome).clear();
		driver.findElement(usuarioNome).click();
		driver.findElement(usuarioNome).sendKeys(usuario);
	}
	
	public void setSenha(String senha) {
		driver.findElement(usuarioSenha).clear();
		driver.findElement(usuarioSenha).click();
		driver.findElement(usuarioSenha).sendKeys(senha);
	}
	
	public void setEmailRecuperacaoSenha(String recuperarSenha) {
		driver.findElement(emailRecSenha).clear();
		driver.findElement(emailRecSenha).click();
		driver.findElement(emailRecSenha).sendKeys(recuperarSenha);
	}

	/***************** URLs ***************************/
	public void acessarLink(String url) {
		driver.get(url);
	}
	
	public void linkEsqueceuSenha() {
		driver.findElement(By.linkText("Forgot password")).click();;
	}
	
	/***************** Botões ***************************/
	public void btnLogin() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogin));
		
		driver.findElement(btnLogin).click();
	}
	
	public void btnRecuperarSenha() {
		driver.findElement(btnRecover).click();
	}

	/***************** Verificações e Validações ***************************/
	public void verificarMsgUsuarioLogado(String strMsgUsuarioLogado) {
		By msgUsuarioLogado = By.xpath("//div/ul/li[1]/a/span/span[contains(text(), '"+ strMsgUsuarioLogado +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgUsuarioLogado));
		
		String obterTexto = driver.findElement(msgUsuarioLogado).getText();
		Assert.assertEquals(strMsgUsuarioLogado, obterTexto);
	}
	
	public void verificarMsgUsuarioOuSenhaInvalidos(String strMsgUsuarioSenhaInv) {
		By msgUsuarioSenhaInv = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgUsuarioSenhaInv +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgUsuarioSenhaInv));
		
		String obterTexto = driver.findElement(msgUsuarioSenhaInv).getText();	
		Assert.assertEquals(strMsgUsuarioSenhaInv, obterTexto);
	}
	
	public void verificarMsgCampoEmailVazio(String strMsgEmail) {
		By msgEmail = By.xpath("//div[2]/div/div/div[contains(text(), '"+ strMsgEmail +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgEmail));
		
		String obterTexto = driver.findElement(msgEmail).getText();
		Assert.assertEquals(strMsgEmail, obterTexto);
	}
	
	public void verificarMsgCampoSenhaVazio(String strMsgSenha) {
		By msgSenha = By.xpath("//div[3]/div/div/div[contains(text(), '"+ strMsgSenha +"')]");

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgSenha));

		String obterTextoSenha = driver.findElement(msgSenha).getText();
		Assert.assertEquals(strMsgSenha, obterTextoSenha);
	}
	
	public void verificarMsgRecuperacaoSenha(String strMsgRecSenha) {
		By msgRecSenha = By.xpath("//h1[@class='css-lfm64q' and contains(text(), '"+ strMsgRecSenha +"')]");

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgRecSenha));

		String obterTextoSenha = driver.findElement(msgRecSenha).getText();
		Assert.assertEquals(strMsgRecSenha, obterTextoSenha);		
	}

}