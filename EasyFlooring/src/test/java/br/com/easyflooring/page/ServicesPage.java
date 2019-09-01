package br.com.easyflooring.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ServicesPage {

	By usuarioNome = By.id("login_name");
	By usuarioSenha = By.id("login_password");
	By btnLogin = By.xpath("//button[@type='submit']");
	By linkWorkOrders = By.xpath("//div[1]/div/ul/li[4]/div/span/span");
	By linkServices = By.linkText("Services");
	By linkNewService = By.linkText("New");
	By serviceName = By.id("service_serviceName");
	By servicePrice = By.id("service_price");;
	By serviceLaborPrice = By.id("service_laborPrice");;
	By linkEditService = By.xpath("//table/tbody/tr[1]/td[6]/span/a[contains(text(), 'Edit')]");
	By linkExcluirService = By.xpath("//table/tbody/tr[1]/td[6]/span/a[contains(text(), 'Delete')]");
	By btnConfirmarExclusao = By.xpath("//div[@class='ant-popover-inner-content']/div[2]/button[2]");
	By btnExcluirSelecionados = By.xpath("//button/span[contains(text(), 'Delete Selected')]");
	By checkSelecionar = By.xpath("//table/thead/tr/th[1]/div/div/label");
			
	WebDriver driver;
	
	public ServicesPage(WebDriver driver) {
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

	public void linkServices() {
		driver.findElement(linkServices).click();
	}

	public void cadastrarNovoService() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkNewService));

		driver.findElement(linkNewService).click();
	}
	
	public void editarService() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkEditService));

		driver.findElement(linkEditService).click();		
	}
	
	public void excluirService() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkExcluirService));

		driver.findElement(linkExcluirService).click();		
	}
	
	/***************** Checkboxes ***************************/
	public void selecionarCheckbox() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(checkSelecionar));

		driver.findElement(checkSelecionar).click();;
	}
	
	/***************** Textos ***************************/
	public void setNome(String name) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(serviceName));
		
		driver.findElement(serviceName).clear();
		driver.findElement(serviceName).click();
		driver.findElement(serviceName).sendKeys(name);
	}
	
	public void selectUnit() {
		By serviceUnit = By.xpath("//div[@class='ant-form-item-control-wrapper']/div/span/div[@id='service_unit']/div");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(serviceUnit));
		
		driver.findElement(serviceUnit).click();
		
		driver.findElement(serviceUnit).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(serviceUnit).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(serviceUnit).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(serviceUnit).sendKeys(Keys.ARROW_DOWN);
		
		driver.findElement(serviceUnit).sendKeys(Keys.ENTER);
	}

	public void setPrice(String price) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(servicePrice));
		
		driver.findElement(servicePrice).clear();
		driver.findElement(servicePrice).click();
		driver.findElement(servicePrice).sendKeys(price);		
	}

	public void setLaborPrice(String laborPrice) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(serviceLaborPrice));
		
		driver.findElement(serviceLaborPrice).clear();
		driver.findElement(serviceLaborPrice).click();
		driver.findElement(serviceLaborPrice).sendKeys(laborPrice);		
	}

	/***************** Botões ***************************/
	public void btnSave() {
		driver.findElement(btnLogin).click();		
	}
	
	public void btnConfirmarExclusaoService() {
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
	public void verificarMsgOperacaoService(String strMsgOperacaoService) {
		By msgOperacaoService = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgOperacaoService +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgOperacaoService));
		
		String obterTexto = driver.findElement(msgOperacaoService).getText();
		Assert.assertEquals(strMsgOperacaoService, obterTexto);
	}

	public void verificarMsgLaborPriceAcimaDoValorDoPrice(String strMsgLaborPriceAcimaDoValor) {
		By msgLaborPriceAcimaDoValor = By.xpath("//div[3]/div/span/div/div/div/span[contains(text(), '"+ strMsgLaborPriceAcimaDoValor +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgLaborPriceAcimaDoValor));
		
		String obterTexto = driver.findElement(msgLaborPriceAcimaDoValor).getText();
		Assert.assertEquals(strMsgLaborPriceAcimaDoValor, obterTexto);
	}

	public void verificarMsgNomeServiceVazio(String strMsgNameService) {
		By msgNameService = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgNameService +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgNameService));
		
		String obterTexto = driver.findElement(msgNameService).getText();
		Assert.assertEquals(strMsgNameService, obterTexto);
	}
	
	public void verificarMsgUnitVazio(String strMsgUnit) {
		By msgUnit = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgUnit +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgUnit));
		
		String obterTexto = driver.findElement(msgUnit).getText();
		Assert.assertEquals(strMsgUnit, obterTexto);		
	}

	public void verificarMsgPriceVazio(String strMsgPrice) {
		By msgPrice = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgPrice +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgPrice));
		
		String obterTexto = driver.findElement(msgPrice).getText();
		Assert.assertEquals(strMsgPrice, obterTexto);		
	}

	public void verificarMsgLaborPriceVazio(String strMsgLaborPrice) {
		By msgLaborPrice = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgLaborPrice +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgLaborPrice));
		
		String obterTexto = driver.findElement(msgLaborPrice).getText();
		Assert.assertEquals(strMsgLaborPrice, obterTexto);		
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

	public void verificarMsgRegistroService(String strMsgServiceEditado) {
		By msgServiceEditado = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strMsgServiceEditado +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgServiceEditado));
		
		String obterTexto = driver.findElement(msgServiceEditado).getText();
		Assert.assertEquals(strMsgServiceEditado, obterTexto);
	}

	public void verificarServiceNaListagem(String strNameService) {
		By nameService = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strNameService +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(nameService));
		
		String obterTexto = driver.findElement(nameService).getText().trim();
		Assert.assertEquals(strNameService, obterTexto);		
	}

}