package br.com.easyflooring.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommunityPage {

	By usuarioNome = By.id("login_name");
	By usuarioSenha = By.id("login_password");
	By btnLogin = By.xpath("//button[@type='submit']");
	By linkWorkOrders = By.xpath("//div[1]/div/ul/li[4]/div/span/span");
	By linkCommunity = By.linkText("Communities");
	By linkNewCommunity = By.linkText("New");
	By nameCommunity = By.id("community_name");
	By cityCommunity = By.id("community_city");
	By zipCodeCommunity = By.id("community_zipCode");
	By linkEditCommunity = By.xpath("//table/tbody/tr[1]/td[6]/span/a[1 and contains(text(), 'Edit')]");
	By linkExcluirCommunity = By.xpath("//table/tbody/tr[1]/td[6]/span/a[1 and contains(text(), 'Delete')]");
	By btnConfirmarExclusao = By.xpath("//div[@class='ant-popover-inner-content']/div[2]/button[2]");
	By btnExcluirSelecionados = By.xpath("//button/span[contains(text(), 'Delete Selected')]");
	By checkSelecionar = By.xpath("//table/thead/tr/th[1]/div/div/label");
			
	WebDriver driver;
	
	
	public CommunityPage(WebDriver driver) {
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

	public void linkCommunity() {
		driver.findElement(linkCommunity).click();
	}

	public void cadastrarNovaCommunity() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkNewCommunity));

		driver.findElement(linkNewCommunity).click();
	}
	
	public void editarCommunity() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkEditCommunity));

		driver.findElement(linkEditCommunity).click();		
	}
	
	public void excluirCommunity() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(linkExcluirCommunity));

		driver.findElement(linkExcluirCommunity).click();		
	}
	
	/***************** Checkboxes ***************************/
	public void selecionarCheckbox() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(checkSelecionar));

		driver.findElement(checkSelecionar).click();;
	}
	
	/***************** Textos ***************************/
	public void setCommunity(String nome) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(nameCommunity));
		
		driver.findElement(nameCommunity).clear();
		driver.findElement(nameCommunity).click();
		driver.findElement(nameCommunity).sendKeys(nome);
	}
	
	public void setCity(String city) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(cityCommunity));
		
		driver.findElement(cityCommunity).clear();
		driver.findElement(cityCommunity).click();
		driver.findElement(cityCommunity).sendKeys(city);		
	}

	public void selectState() throws InterruptedException {
		By stateCommunity = By.xpath("//div[@class='ant-form-item-control-wrapper']/div/span/div[@id='community_state']/div");

		driver.findElement(stateCommunity).click();

		driver.findElement(stateCommunity).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(stateCommunity).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(stateCommunity).sendKeys(Keys.ARROW_DOWN);
		
		driver.findElement(stateCommunity).sendKeys(Keys.ENTER);
	}

	public void setZipCode(String zipCode) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(zipCodeCommunity));
		
		driver.findElement(zipCodeCommunity).clear();
		driver.findElement(zipCodeCommunity).click();
		driver.findElement(zipCodeCommunity).sendKeys(zipCode);		
	}
	
	/***************** Botões ***************************/
	public void btnSave() {
		driver.findElement(btnLogin).click();		
	}
	
	public void btnConfirmarExclusaoCommunity() {
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
	public void verificarMsgCommunityCadastradoComSucesso(String strMsgBuilderCadSucesso) {
		By msgBuilderCadSucesso = By.xpath("//div[2]/div/span/div/div/div/span[contains(text(), '"+ strMsgBuilderCadSucesso +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgBuilderCadSucesso));
		
		String obterTexto = driver.findElement(msgBuilderCadSucesso).getText();
		Assert.assertEquals(strMsgBuilderCadSucesso, obterTexto);
	}
	
	public void verificarMsgNomeCommunityVazio(String strMsgNomeVazio) {
		By msgNomeVazio = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgNomeVazio +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgNomeVazio));
		
		String obterTexto = driver.findElement(msgNomeVazio).getText();
		Assert.assertEquals(strMsgNomeVazio, obterTexto);
	}
	
	public void verificarMsgCidadeCommunityVazio(String strMsgCidadeVazio) {
		By msgCidadeVazio = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgCidadeVazio +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgCidadeVazio));
		
		String obterTexto = driver.findElement(msgCidadeVazio).getText();
		Assert.assertEquals(strMsgCidadeVazio, obterTexto);
	}
	
	public void verificarMsgEstadoCommunityVazio(String strMsgEstadoVazio) {
		By msgEstadoVazio = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgEstadoVazio +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgEstadoVazio));
		
		String obterTexto = driver.findElement(msgEstadoVazio).getText();
		Assert.assertEquals(strMsgEstadoVazio, obterTexto);
	}

	public void verificarMsgZipCodeCommunityVazio(String strMsgZipCodeVazio) {
		By msgZipCodeVazio = By.xpath("//div[@class='ant-form-explain' and contains(text(), '"+ strMsgZipCodeVazio +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(msgZipCodeVazio));
		
		String obterTexto = driver.findElement(msgZipCodeVazio).getText();
		Assert.assertEquals(strMsgZipCodeVazio, obterTexto);
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

	public void verificarCommunityNaListagem(String strReason) {
		By reason = By.xpath("//table/tbody/tr[1]/td[2 and contains(text(), '"+ strReason +"')]");
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(reason));
		
		String obterTexto = driver.findElement(reason).getText();
		Assert.assertEquals(strReason, obterTexto);		
	}

}