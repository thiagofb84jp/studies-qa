package br.com.easyflooring.test;

import static br.com.easyflooring.utils.GeradorDeDados.*;

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

import br.com.easyflooring.page.BuildersPage;

public class BuildersTest {
	
	@Rule
	public TestName testName = new TestName();

	WebDriver driver;
	
	BuildersPage buildersPage;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rokauser.stsupgradeyourfloors.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		buildersPage = new BuildersPage(driver);
	}
		
	@Test
	public void cadastrarBuilderComTodosOsCamposVazios() throws InterruptedException {
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.cadastrarNovoBuilder();		
		buildersPage.btnSave();
		
		buildersPage.verificarMsgNomeBuilderVazio("Please input name!");
	}
	
	@Test
	public void cadastrarBuilderComOMesmoNome() {
		String nameBuild = gerarNomeBuilder();
		
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		for (int i = 0; i < 1; i++) {
			buildersPage.cadastrarNovoBuilder();
			buildersPage.setNome(nameBuild);
			buildersPage.btnSave();			
		}
		
		buildersPage.cadastrarNovoBuilder();
		buildersPage.setNome(nameBuild);
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("Name already exist, please choose another name");
	}
	
	@Test
	public void cadastrarBuilderComPerfilAdministrador() throws InterruptedException {
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.cadastrarNovoBuilder();
		buildersPage.setNome(gerarNomeBuilder());
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!");
	}
	
	@Test
	public void listarBuildComPerfilAdmin() {
		String nameBuild = gerarNomeBuilder();
		
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.cadastrarNovoBuilder();
		buildersPage.setNome(nameBuild);
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!"); //Created or saved? padronizar mensagens de criação
		buildersPage.verificarBuildNaListagem(nameBuild);
	}


	@Test
	public void cadastrarBuilderComPerfilSupervisor() {
		buildersPage.acessarSistema("francisca_viana", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.cadastrarNovoBuilder();
		
		buildersPage.setNome(gerarNomeBuilder());
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!");		
	}
	
	@Test
	public void listarBuildComPerfilSupervisor() {
		String nameBuild = gerarNomeBuilder();
		
		buildersPage.acessarSistema("francisca_viana", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.cadastrarNovoBuilder();
		buildersPage.setNome(nameBuild);
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!"); //Created or saved? padronizar mensagens de criação
		buildersPage.verificarBuildNaListagem(nameBuild);
	}

	@Test
	public void acessarOpcaoDeBuilderComPerfilConcrete() {
		buildersPage.acessarSistema("joaquim_lima", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeBuilderComPerfilFieldManager() {
		buildersPage.acessarSistema("francisco_pires", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeBuilderComPerfilSubcontractor() {
		buildersPage.acessarSistema("laura_moura", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeBuilderComPerfilFinanceiro() {
		buildersPage.acessarSistema("rita_almada", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/");
	}

	@Test
	public void cadastrarBuilderComPerfilConcrete() {
		buildersPage.acessarSistema("joaquim_lima", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/new");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarBuilderComPerfilFieldManager() {
		buildersPage.acessarSistema("francisco_pires", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/new");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarBuilderComPerfilSubcontractor() throws InterruptedException {
		buildersPage.acessarSistema("laura_moura", "abcd_123");
		
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/new");
		
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}

	@Test
	public void cadastrarBuilderComPerfilFinanceiro() throws InterruptedException {
		buildersPage.acessarSistema("rita_almada", "abcd_123");
		
		buildersPage.aguardarComponente(3000);
		buildersPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/builders/new");
	
		buildersPage.verificarMsgRestricaoAcesso("Unauthorized");
		buildersPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void editarBuilderComPerfilAdmin() {
		String nameBuild = gerarNomeBuilder();
		
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.editarBuilder();
		buildersPage.setNome(nameBuild);
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("edited success!");
		buildersPage.verificarMsgRegistroBuilder(nameBuild);
	}
	
	@Test
	public void removerBuilderComPerfilAdmin() {
		buildersPage.acessarSistema("manuela_neves", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.excluirBuilder();
		buildersPage.btnConfirmarExclusaoBuilder();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasBuildersComPerfilAdmin() throws InterruptedException {
		buildersPage.acessarSistema("manuela_neves", "abcd_123");

		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
	
		for (int i = 0; i < 5; i++) {
			buildersPage.cadastrarNovoBuilder();
			buildersPage.setNome(gerarNomeBuilder());
			buildersPage.btnSave();
			
			buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!");
		}

		buildersPage.aguardarComponente(3000);
		buildersPage.selecionarCheckbox();
		
		buildersPage.excluirSelecionados();
		buildersPage.verificarMsgBuilderCadastradoComSucesso("deleted success!");
	}

	@Test
	public void removerBuilderComPerfilSupervisor() {
		buildersPage.acessarSistema("francisca_viana", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.excluirBuilder();
		buildersPage.btnConfirmarExclusaoBuilder();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasBuildersComPerfilSupervisor() throws InterruptedException {
		buildersPage.acessarSistema("francisca_viana", "abcd_123");

		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
	
		for (int i = 0; i < 5; i++) {
			buildersPage.cadastrarNovoBuilder();
			buildersPage.setNome(gerarNomeBuilder());
			buildersPage.btnSave();
			
			buildersPage.verificarMsgBuilderCadastradoComSucesso("saved success!");
		}

		buildersPage.aguardarComponente(3000);
		buildersPage.selecionarCheckbox();
		
		buildersPage.excluirSelecionados();
		buildersPage.verificarMsgBuilderCadastradoComSucesso("deleted success!");
	}
	
	@Test
	public void editarBuilderComPerfilSupervisor() {
		String nameBuild = gerarNomeBuilder();
		
		buildersPage.acessarSistema("francisca_viana", "abcd_123");
		
		buildersPage.linkWorkOrders();
		buildersPage.linkBuilders();
		
		buildersPage.editarBuilder();
		buildersPage.setNome(nameBuild);
		buildersPage.btnSave();
		
		buildersPage.verificarMsgBuilderCadastradoComSucesso("edited success!");
		buildersPage.verificarMsgRegistroBuilder(nameBuild);
	}

	@After
	public void killDriver() throws IOException {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo  = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testName.getMethodName() + ".jpg"));

		driver.close();
	}
}