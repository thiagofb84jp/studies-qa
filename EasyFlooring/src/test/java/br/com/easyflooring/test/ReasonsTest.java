package br.com.easyflooring.test;

import static br.com.easyflooring.utils.GeradorDeDados.gerarReason;

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

import br.com.easyflooring.page.ReasonsPage;

public class ReasonsTest {
	
	@Rule
	public TestName testName = new TestName();
	
	WebDriver driver;
	
	ReasonsPage reasonsPage;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rokauser.stsupgradeyourfloors.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		reasonsPage = new ReasonsPage(driver);
	}
		
	@Test
	public void cadastrarReasonComTodosOsCamposVazios() throws InterruptedException {
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.cadastrarNovoReason();		
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgNomeReasonVazio("Please input description!");
	}
	
	@Test
	public void cadastrarReasonComOMesmoNome() {
		String reason = gerarReason();
		
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		for (int i = 0; i < 1; i++) {
			reasonsPage.cadastrarNovoReason();
			reasonsPage.setReason(reason);
			reasonsPage.btnSave();			
		}
		
		reasonsPage.cadastrarNovoReason();
		reasonsPage.setReason(reason);
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("Description already exist, please choose another description"); //Please, choose another one.
	}
	
	@Test
	public void cadastrarReasonComPerfilAdministrador() throws InterruptedException {
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.cadastrarNovoReason();
		reasonsPage.setReason(gerarReason());
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!"); //Created or saved? padronizar mensagens de criação
	}
	
	@Test
	public void listarReasonsComPerfilAdmin() {
		String reason = gerarReason();
		
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.cadastrarNovoReason();
		reasonsPage.setReason(reason);
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!"); //Created or saved? padronizar mensagens de criação
		reasonsPage.verificarReasonNaListagem(reason);
	}

	@Test
	public void cadastrarReasonComPerfilSupervisor() {
		reasonsPage.acessarSistema("francisca_viana", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.cadastrarNovoReason();
		
		reasonsPage.setReason(gerarReason());
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!");		
	}
	
	@Test
	public void listarReasonsComPerfilSupervisor() {
		String reason = gerarReason();
		
		reasonsPage.acessarSistema("francisca_viana", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.cadastrarNovoReason();
		reasonsPage.setReason(reason);
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!"); //Created or saved? padronizar mensagens de criação
		reasonsPage.verificarReasonNaListagem(reason);
	}
	
	@Test
	public void acessarOpcaoDeReasonComPerfilConcrete() throws InterruptedException {
		reasonsPage.acessarSistema("joaquim_lima", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeReasonComPerfilFieldManager() throws InterruptedException {
		reasonsPage.acessarSistema("francisco_pires", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeReasonsComPerfilSubcontractor() throws InterruptedException {
		reasonsPage.acessarSistema("laura_moura", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarReasonComPerfilConcrete() throws InterruptedException {
		reasonsPage.acessarSistema("joaquim_lima", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/new");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarReasonComPerfilFieldManager() throws InterruptedException {
		reasonsPage.acessarSistema("francisco_pires", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/new");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarReasonComPerfilSubcontractor() throws InterruptedException {
		reasonsPage.acessarSistema("laura_moura", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/new");
		
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}

	@Test
	public void cadastrarReasonComPerfilFinanceiro() throws InterruptedException {
		reasonsPage.acessarSistema("rita_almada", "abcd_123");
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/reasons/new");
	
		reasonsPage.verificarMsgRestricaoAcesso("Unauthorized");
		reasonsPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void editarReasonComPerfilAdmin() throws InterruptedException {
		String reason = gerarReason();
		
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		for (int i = 0; i < 5; i++) {
			reasonsPage.cadastrarNovoReason();
			
			reasonsPage.setReason(gerarReason());
			reasonsPage.btnSave();
		}
		
		reasonsPage.aguardarComponente(3000);
		reasonsPage.editarReason();
		reasonsPage.setReason(reason);
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("edited success!");
		reasonsPage.verificarMsgRegistroReason(reason);
	}
	
	@Test
	public void removerReasonComPerfilAdmin() {
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.excluirReason();
		reasonsPage.btnConfirmarExclusaoBuilder();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasReasonsComPerfilAdmin() throws InterruptedException {
		reasonsPage.acessarSistema("manuela_neves", "abcd_123");

		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
	
		for (int i = 0; i < 5; i++) {
			reasonsPage.cadastrarNovoReason();
			reasonsPage.setReason(gerarReason());
			reasonsPage.btnSave();
			
			reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!");
		}

		reasonsPage.aguardarComponente(3000);
		reasonsPage.selecionarCheckbox();
		
		reasonsPage.excluirSelecionados();
		reasonsPage.verificarMsgReasonCadastradoComSucesso("deleted success!");
	}

	@Test
	public void removerReasonComPerfilSupervisor() {
		reasonsPage.acessarSistema("francisca_viana", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.excluirReason();
		reasonsPage.btnConfirmarExclusaoBuilder();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasReasonsComPerfilSupervisor() throws InterruptedException {
		reasonsPage.acessarSistema("francisca_viana", "abcd_123");

		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
	
		for (int i = 0; i < 5; i++) {
			reasonsPage.cadastrarNovoReason();
			reasonsPage.setReason(gerarReason());
			reasonsPage.btnSave();
			
			reasonsPage.verificarMsgReasonCadastradoComSucesso("created success!");
		}

		reasonsPage.aguardarComponente(3000);
		reasonsPage.selecionarCheckbox();
		
		reasonsPage.excluirSelecionados();
		reasonsPage.verificarMsgReasonCadastradoComSucesso("deleted success!");
	}
	
	@Test
	public void editarReasonComPerfilSupervisor() {
		String reason = gerarReason();
		
		reasonsPage.acessarSistema("francisca_viana", "abcd_123");
		
		reasonsPage.linkWorkOrders();
		reasonsPage.linkReasons();
		
		reasonsPage.editarReason();
		reasonsPage.setReason(reason);
		reasonsPage.btnSave();
		
		reasonsPage.verificarMsgReasonCadastradoComSucesso("edited success!");
		reasonsPage.verificarMsgRegistroReason(reason);
	}

	@After
	public void killDriver() throws IOException {		
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo  = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testName.getMethodName() + ".jpg"));
		
		driver.close();
	}
}