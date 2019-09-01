package br.com.easyflooring.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReasonsPage {

	By usuarioNome = By.id("login_name");
	By usuarioSenha = By.id("login_password");
	By btnLogin = By.xpath("//button[@type='submit']");
	By linkWorkOrders = By.xpath("//div[1]/div/ul/li[4]/div/span/span");
	By linkReasons = By.linkText("Reasons");
	By linkNewReason = By.linkText("New");
	By reasonDescription = By.id("reason_description");
	By linkEditReason = By.xpath("//table/tbody/tr[1]/td[3]/span/a[1 and contains(text(), 'Edit')]");
	By linkExcluirReason = By.xpath("//table/tbody/tr[1]/td[3]/span/a[2 and contains(text(), 'Delete')]");
	By btnConfirmarExclusao = By.xpath("//div[@class='ant-popover-inner-content']/div[2]/button[2]");
	By btnExcluirSelecionados = By.xpath("//button/span[contains(text(), 'Delete Selected')]");
	By checkSelecionar = By.xpath("//table/thead/tr/th[1]/div/div/label");
			
	WebDriver driver;
	
	
	public ReasonsPage(WebDriver driver) {
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

	public void linkReasons() {
		driver.findElement(linkReasons).click();
	}

	public void cadastrarNovoReason() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkNewReason));

		driver.findElement(linkNewReason).click();
	}
	
	public void editarReason() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkEditReason));

		driver.findElement(linkEditReason).click();		
	}
	
	public void excluirReason() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkExcluirReason));

		driver.findElement(linkExcluirReason).click();		
	}
	
	/***************** Checkboxes ***************************/
	public void selecionarCheckbox() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(checkSelecionar));

		driver.findElement(checkSelecionar).click();;
	}
	
	/***************** Textos ***************************/
	public void setReason(String nomeBuilder) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(reasonDescription));
		
		driver.findElement(reasonDescription).clear();
		driver.findElement(reasonDescription).click();
		driver.findElement(reasonDescription).sendKeys(nomeBuilder);
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
	public void verificarMsgReasonCadastradoComSucesso(String strMsgBuilderCadSucesso) {
		By msgBuilderCadSucesso = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgBuilderCadSucesso +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgBuilderCadSucesso));
		
		String obterTexto = driver.findElement(msgBuilderCadSucesso).getText();
		Assert.assertEquals(strMsgBuilderCadSucesso, obterTexto);
	}
	
	public void verificarMsgNomeReasonVazio(String strMsgEmptyBuilder) {
		By msgEmptyBuilder = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgEmptyBuilder +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgEmptyBuilder));
		
		String obterTexto = driver.findElement(msgEmptyBuilder).getText();
		Assert.assertEquals(strMsgEmptyBuilder, obterTexto);
	}
	
	public void verificarMsgReasonComMesmoNome(String strMsgSameBuilder) {
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

	public void verificarMsgRegistroReason(String strBuilderEditado) {
		By msgBuilderEditado = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strBuilderEditado +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgBuilderEditado));
		
		String obterTexto = driver.findElement(msgBuilderEditado).getText();
		Assert.assertEquals(strBuilderEditado, obterTexto);
	}

	public void verificarReasonNaListagem(String strReason) {
		By reason = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strReason +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(reason));
		
		String obterTexto = driver.findElement(reason).getText();
		Assert.assertEquals(strReason, obterTexto);		
	}
}