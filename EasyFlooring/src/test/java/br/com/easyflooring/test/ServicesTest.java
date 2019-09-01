package br.com.easyflooring.test;

import static br.com.easyflooring.utils.GeradorDeDados.gerarLaborPrice;
import static br.com.easyflooring.utils.GeradorDeDados.gerarPrice;
import static br.com.easyflooring.utils.GeradorDeDados.gerarServiceName;
import static br.com.easyflooring.utils.GeradorDeDados.gerarValorRandomico;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.easyflooring.page.ServicesPage;

public class ServicesTest {
	
	@Rule
	public TestName testName = new TestName();

	WebDriver driver;
	
	ServicesPage servicesPage;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rokauser.stsupgradeyourfloors.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		servicesPage = new ServicesPage(driver);
	}
		
	@Test
	public void cadastrarServicoComTodosOsCamposVazios() throws InterruptedException {
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgNomeServiceVazio("Please input name!");
		servicesPage.verificarMsgUnitVazio("Please select unit!");
		servicesPage.verificarMsgPriceVazio("Please input price!");
		servicesPage.verificarMsgLaborPriceVazio("Please input labor price!");
	}
	
	@Test
	public void cadastrarServicoComOMesmoNome() throws InterruptedException {
		String name = gerarServiceName();
		String price = gerarPrice();
		String laborPrice = gerarLaborPrice();
		
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		for (int i = 0; i < 1; i++) {
			servicesPage.cadastrarNovoService();
			
			servicesPage.setNome(name);
			servicesPage.selectUnit();
			servicesPage.setPrice(price);
			servicesPage.setLaborPrice(laborPrice);
			
			servicesPage.btnSave();
		}
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(name);
		servicesPage.selectUnit();
		servicesPage.setPrice(price);
		servicesPage.setLaborPrice(laborPrice);
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("Service name already exist, please choose another service name");
	}
	
	@Test
	public void cadastrarServicoComCampoLaborPriceMaiorQuePrice() {
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(gerarServiceName());
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarValorRandomico());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgLaborPriceAcimaDoValorDoPrice("Price must be greater than labor price");		
	}
	
	@Test
	public void cadastrarServicoComPerfilAdministrador() throws InterruptedException {
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(gerarServiceName());
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("saved success!");
	}
	
	@Test
	public void listarServicesComPerfilAdmin() {
		String serviceName = gerarServiceName();
		
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(serviceName);
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("saved success!");
		servicesPage.verificarServiceNaListagem(serviceName);
	}


	@Test
	public void cadastrarServiceComPerfilSupervisor() {
		servicesPage.acessarSistema("francisca_viana", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(gerarServiceName());
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("saved success!");		
	}
	
	@Test
	public void listarServicesComPerfilSupervisor() {
		String serviceName = gerarServiceName();
		
		servicesPage.acessarSistema("francisca_viana", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.cadastrarNovoService();
		
		servicesPage.setNome(serviceName);
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("saved success!");
		servicesPage.verificarServiceNaListagem(serviceName);
	}

	@Test
	public void acessarOpcaoDeServicesComPerfilConcrete() {
		servicesPage.acessarSistema("joaquim_lima", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeServicesComPerfilFieldManager() {
		servicesPage.acessarSistema("francisco_pires", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeServicesComPerfilSubcontractor() {
		servicesPage.acessarSistema("laura_moura", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeServicesComPerfilFinanceiro() {
		servicesPage.acessarSistema("rita_almada", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/");
	}

	@Test
	public void cadastrarServiceComPerfilConcrete() {
		servicesPage.acessarSistema("joaquim_lima", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/new");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarServiceComPerfilFieldManager() {
		servicesPage.acessarSistema("francisco_pires", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/new");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarServiceComPerfilSubcontractor() throws InterruptedException {
		servicesPage.acessarSistema("laura_moura", "abcd_123");
		
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/new");
		
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}

	@Test
	public void cadastrarServiceComPerfilFinanceiro() throws InterruptedException {
		servicesPage.acessarSistema("rita_almada", "abcd_123");
		
		servicesPage.aguardarComponente(3000);
		servicesPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/services/new");
	
		servicesPage.verificarMsgRestricaoAcesso("Unauthorized");
		servicesPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void editarServiceComPerfilAdmin() {
		String serviceName = gerarServiceName();
		
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();

		servicesPage.editarService();
		
		servicesPage.setNome(serviceName);
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("edited success!");
		servicesPage.verificarMsgRegistroService(serviceName);
	}
	
	@Test
	public void removerServiceComPerfilAdmin() {
		servicesPage.acessarSistema("manuela_neves", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.excluirService();
		servicesPage.btnConfirmarExclusaoService();
		
		servicesPage.verificarMsgOperacaoService("deleted success!");		
	}
	
	@Test
	public void removerVariasServicesComPerfilAdmin() throws InterruptedException {
		servicesPage.acessarSistema("manuela_neves", "abcd_123");

		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
	
		for (int i = 0; i < 5; i++) {
			servicesPage.cadastrarNovoService();
			
			servicesPage.setNome(gerarServiceName());
			servicesPage.selectUnit();
			servicesPage.setPrice(gerarPrice());
			servicesPage.setLaborPrice(gerarLaborPrice());
			
			servicesPage.btnSave();
			
			servicesPage.verificarMsgOperacaoService("saved success!");
		}
		
		servicesPage.aguardarComponente(2000);
		servicesPage.selecionarCheckbox();
		
		servicesPage.excluirSelecionados();
		servicesPage.verificarMsgOperacaoService("deleted success!");
	}

	@Test
	public void removerServiceComPerfilSupervisor() {
		servicesPage.acessarSistema("francisca_viana", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
		
		servicesPage.excluirService();
		servicesPage.btnConfirmarExclusaoService();
		
		servicesPage.verificarMsgOperacaoService("deleted success!");		
	}
	
	@Test
	public void removerVariasServicesComPerfilSupervisor() throws InterruptedException {
		servicesPage.acessarSistema("francisca_viana", "abcd_123");

		servicesPage.linkWorkOrders();
		servicesPage.linkServices();
	
		for (int i = 0; i < 5; i++) {
			servicesPage.cadastrarNovoService();
			
			servicesPage.setNome(gerarServiceName());
			servicesPage.selectUnit();
			servicesPage.setPrice(gerarPrice());
			servicesPage.setLaborPrice(gerarLaborPrice());
			
			servicesPage.btnSave();
			
			servicesPage.verificarMsgOperacaoService("saved success!");
		}
		
		servicesPage.aguardarComponente(2000);
		servicesPage.selecionarCheckbox();
		
		servicesPage.excluirSelecionados();
		servicesPage.verificarMsgOperacaoService("deleted success!");
	}
	
	@Test
	public void editarServiceComPerfilSupervisor() {
		String serviceName = gerarServiceName();
		
		servicesPage.acessarSistema("francisca_viana", "abcd_123");
		
		servicesPage.linkWorkOrders();
		servicesPage.linkServices();

		servicesPage.editarService();
		
		servicesPage.setNome(serviceName);
		servicesPage.selectUnit();
		servicesPage.setPrice(gerarPrice());
		servicesPage.setLaborPrice(gerarLaborPrice());
		
		servicesPage.btnSave();
		
		servicesPage.verificarMsgOperacaoService("edited success!");
		servicesPage.verificarMsgRegistroService(serviceName);
	}

	@After
	public void killDriver() throws IOException {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo  = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testName.getMethodName() + ".jpg"));

		driver.close();
	}
}