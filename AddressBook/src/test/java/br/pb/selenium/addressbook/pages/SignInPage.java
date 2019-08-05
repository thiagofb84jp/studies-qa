package br.pb.selenium.addressbook.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignInPage {
	
	WebDriver driver;
	
	By email = By.id("session_email");
	By password = By.id("session_password");
	By btnSignIn = By.xpath("//input[@value='Sign in']");
	By badMessage = By.xpath("//div[@class='alert alert-notice']");
	By linkSignIn = By.linkText("Sign in");
	By userLogged = By.xpath("//span[@class='navbar-text']");
	By goodMessage = By.xpath("//div[@class='container']//h4");
	By linkSignOut =By.linkText("Sign out");
	
	public SignInPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setEmail(String strEmail) {
		driver.findElement(email).click();
		driver.findElement(email).clear();
		driver.findElement(email).sendKeys(strEmail);
	}
	
	public void setPassword(String strPassword) {
		driver.findElement(password).click();
		driver.findElement(password).clear();
		driver.findElement(password).sendKeys(strPassword);
	}
	
	public void clickBtnSignIn() {
		driver.findElement(btnSignIn).click();
	}
	
	public void clickLinkSignIn() {
		driver.findElement(linkSignIn).click();
	}
	
	public void clickLinkSignOut() {
		driver.findElement(linkSignOut).click();
	}
	
	public void validarMsgFalha(String strBadMessage) {
		String getResult = driver.findElement(badMessage).getText();
		Assert.assertEquals(getResult, strBadMessage);
	}
	
	public void validarAcessoUsuarioLogado(String strUserLogged, String StrGoodMessage) {
		String getResultUser = driver.findElement(userLogged).getText();
		Assert.assertEquals(getResultUser, strUserLogged);
		
		String getGoodMessage = driver.findElement(goodMessage).getText();
		Assert.assertEquals(getGoodMessage, StrGoodMessage);
	}
}