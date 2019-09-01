package br.com.easyflooring.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuildersPage {

	By usuarioNome = By.id("login_name");
	By usuarioSenha = By.id("login_password");
	By btnLogin = By.xpath("//button[@type='submit']");
	By linkWorkOrders = By.xpath("//div[1]/div/ul/li[4]/div/span/span");
	By linkBuilders = By.linkText("Builders");
	By linkNewBuilder = By.linkText("New");
	By builderName = By.id("builder_name");
	By linkEditBuilder = By.xpath("//table/tbody/tr[1]/td[3]/span/a[1 and contains(text(), 'Edit')]");
	By linkExcluirBuilder = By.xpath("//table/tbody/tr[1]/td[3]/span/a[2 and contains(text(), 'Delete')]");
	By btnConfirmarExclusao = By.xpath("//div[@class='ant-popover-inner-content']/div[2]/button[2]");
	By btnExcluirSelecionados = By.xpath("//button/span[contains(text(), 'Delete Selected')]");
	By checkSelecionar = By.xpath("//table/thead/tr/th[1]/div/div/label");
			
	WebDriver driver;
	
	
	public BuildersPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/***************** Acesso ao Sistema ***************************/
	public void acessarSistema(String usuario, String senha) {
		driver.findElement(usuarioNome).clear();
		driver.findElement(usuarioNome).click();
		driver.findElement(usuarioNome).sendKeys(usuario);

		driver.findElement(usuarioSenha).clear();
		driver.findElement(usuarioSenha).click();
		driver.findElement(usuarioSenha).sendKeys(senha);
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogin));
		
		driver.findElement(btnLogin).click();
	}

	/***************** URLs e Links ***************************/
	public void acessarLink(String url) {
		driver.get(url);
	}

	public void linkWorkOrders() {
		driver.findElement(linkWorkOrders).click();
	}

	public void linkBuilders() {
		driver.findElement(linkBuilders).click();
	}

	public void cadastrarNovoBuilder() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkNewBuilder));

		driver.findElement(linkNewBuilder).click();
	}
	
	public void editarBuilder() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkEditBuilder));

		driver.findElement(linkEditBuilder).click();		
	}
	
	public void excluirBuilder() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkExcluirBuilder));

		driver.findElement(linkExcluirBuilder).click();		
	}
	
	/***************** Checkboxes ***************************/
	public void selecionarCheckbox() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(checkSelecionar));

		driver.findElement(checkSelecionar).click();;
	}
	
	/***************** Textos ***************************/
	public void setNome(String nomeBuilder) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(builderName));
		
		driver.findElement(builderName).clear();
		driver.findElement(builderName).click();
		driver.findElement(builderName).sendKeys(nomeBuilder);
	}

	/***************** Botões ***************************/
	public void btnSave() {
		driver.findElement(btnLogin).click();		
	}
	
	public void btnConfirmarExclusaoBuilder() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(btnConfirmarExclusao));

		driver.findElement(btnConfirmarExclusao).click();		
	}
	public void excluirSelecionados() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(btnExcluirSelecionados));

		WebElement element = driver.findElement(btnExcluirSelecionados);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();				
	}
	
	/***************** Outros Componentes ****************************/

	public void aguardarComponente(Integer tempoEspera) throws InterruptedException{
		Thread.sleep(tempoEspera);
	}
	
	/***************** Verificações e Validações ***************************/
	public void verificarMsgBuilderCadastradoComSucesso(String strMsgBuilderCadSucesso) {
		By msgBuilderCadSucesso = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgBuilderCadSucesso +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgBuilderCadSucesso));
		
		String obterTexto = driver.findElement(msgBuilderCadSucesso).getText();
		Assert.assertEquals(strMsgBuilderCadSucesso, obterTexto);
	}
	
	public void verificarMsgNomeBuilderVazio(String strMsgEmptyBuilder) {
		By msgEmptyBuilder = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgEmptyBuilder +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgEmptyBuilder));
		
		String obterTexto = driver.findElement(msgEmptyBuilder).getText();
		Assert.assertEquals(strMsgEmptyBuilder, obterTexto);
	}
	
	public void verificarMsgBuilderComMesmoNome(String strMsgSameBuilder) {
		By msgSameBuilder = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgSameBuilder +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgSameBuilder));
		
		String obterTexto = driver.findElement(msgSameBuilder).getText();
		Assert.assertEquals(strMsgSameBuilder, obterTexto);
	}
	
	public void verificarMsgRestricaoAcesso(String strMsgRestricaoAcesso) {
		By msgRestricaoAcesso = By.xpath("//h1[contains(text(), '"+ strMsgRestricaoAcesso +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgRestricaoAcesso));
		
		String obterTexto = driver.findElement(msgRestricaoAcesso).getText();
		Assert.assertEquals(strMsgRestricaoAcesso, obterTexto);		
	}

	public void verificarMsgRegistroBuilder(String strBuilderEditado) {
		By msgBuilderEditado = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strBuilderEditado +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgBuilderEditado));
		
		String obterTexto = driver.findElement(msgBuilderEditado).getText();
		Assert.assertEquals(strBuilderEditado, obterTexto);
	}

	public void verificarBuildNaListagem(String strNameBuild) {
		By nameBuild = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strNameBuild +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(nameBuild));
		
		String obterTexto = driver.findElement(nameBuild).getText();
		Assert.assertEquals(strNameBuild, obterTexto);		
	}

}