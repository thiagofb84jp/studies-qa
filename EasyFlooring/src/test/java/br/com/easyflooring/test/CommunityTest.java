package br.com.easyflooring.test;

import static br.com.easyflooring.utils.GeradorDeDados.gerarCep;
import static br.com.easyflooring.utils.GeradorDeDados.gerarCidade;
import static br.com.easyflooring.utils.GeradorDeDados.gerarCommunityName;

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

import br.com.easyflooring.page.CommunityPage;

public class CommunityTest {
	
	@Rule
	public TestName testName = new TestName();
	
	WebDriver driver;
	
	CommunityPage communityPage;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://rokauser.stsupgradeyourfloors.com");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		communityPage = new CommunityPage(driver);
	}
		
	@Test
	public void cadastrarCommunityComTodosOsCamposVazios() throws InterruptedException {
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		communityPage.cadastrarNovaCommunity();		
		communityPage.btnSave();
		
		communityPage.verificarMsgNomeCommunityVazio("Please input name!");
		communityPage.verificarMsgCidadeCommunityVazio("Please input the city!");
		communityPage.verificarMsgEstadoCommunityVazio("Please select the state!");
		communityPage.verificarMsgZipCodeCommunityVazio("Please input the Zip Code!");
	}
	
	@Test
	public void cadastrarCommunityComOMesmoNome() throws InterruptedException {
		String name = gerarCommunityName();
		String city = gerarCidade();
		String zipCode = gerarCep();
		
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		for (int i = 0; i < 1; i++) {
			communityPage.cadastrarNovaCommunity();
			
			communityPage.setCommunity(name);
			communityPage.setCity(city);			
			communityPage.setZipCode(zipCode);
			communityPage.selectState();
			
			communityPage.btnSave();		
		}
		
		communityPage.cadastrarNovaCommunity();
		
		communityPage.setCommunity(name);
		communityPage.setCity(city);		
		communityPage.setZipCode(zipCode);
		communityPage.selectState();
	
		communityPage.btnSave();		
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("Name already exist, please choose another name"); //Please, choose another one.
	}
	
	@Test
	public void cadastrarCommunityComPerfilAdministrador() throws InterruptedException {
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		communityPage.cadastrarNovaCommunity();
		
		communityPage.setCommunity(gerarCommunityName());
		communityPage.setCity(gerarCidade());		
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
	
		communityPage.btnSave();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("saved success!"); //Please, choose another one.
	}
	
	@Test
	public void listarCommunitiesComPerfilAdmin() throws InterruptedException {
		String nomeCommunity = gerarCommunityName();
		
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		communityPage.cadastrarNovaCommunity();
		
		communityPage.setCommunity(nomeCommunity);
		communityPage.setCity(gerarCidade());		
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
	
		communityPage.btnSave();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("saved success!"); //Please, choose another one.
		communityPage.verificarCommunityNaListagem(nomeCommunity);
	}

	@Test
	public void cadastrarCommunityComPerfilSupervisor() throws InterruptedException {
		communityPage.acessarSistema("francisca_viana", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		communityPage.cadastrarNovaCommunity();
		
		communityPage.setCommunity(gerarCommunityName());
		communityPage.setCity(gerarCidade());		
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
	
		communityPage.btnSave();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("saved success!"); //Please, choose another one.
	}
	
	@Test
	public void listarCommunitiesComPerfilSupervisor() throws InterruptedException {
		String nomeCommunity = gerarCommunityName();
		
		communityPage.acessarSistema("francisca_viana", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		communityPage.cadastrarNovaCommunity();
		
		communityPage.setCommunity(nomeCommunity);
		communityPage.setCity(gerarCidade());		
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
	
		communityPage.btnSave();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("saved success!"); //Please, choose another one.
		communityPage.verificarCommunityNaListagem(nomeCommunity);
	}
	
	@Test
	public void acessarOpcaoDeCommunityComPerfilConcrete() throws InterruptedException {
		communityPage.acessarSistema("joaquim_lima", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeCommunityComPerfilFieldManager() throws InterruptedException {
		communityPage.acessarSistema("francisco_pires", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void acessarOpcaoDeCommunityComPerfilSubcontractor() throws InterruptedException {
		communityPage.acessarSistema("laura_moura", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}

	@Test
	public void acessarOpcaoDeCommunityComPerfilFinanceiro() throws InterruptedException {
		communityPage.acessarSistema("rita_almada", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/");
	}

	@Test
	public void cadastrarCommunityComPerfilConcrete() throws InterruptedException {
		communityPage.acessarSistema("joaquim_lima", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/new");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarCommunityComPerfilFieldManager() throws InterruptedException {
		communityPage.acessarSistema("francisco_pires", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/new");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void cadastrarCommunityComPerfilSubcontractor() throws InterruptedException {
		communityPage.acessarSistema("laura_moura", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/new");
		
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}

	@Test
	public void cadastrarCommunityComPerfilFinanceiro() throws InterruptedException {
		communityPage.acessarSistema("rita_almada", "abcd_123");
		
		communityPage.aguardarComponente(2000);
		communityPage.acessarLink("https://rokauser.stsupgradeyourfloors.com/communities/new");
	
		communityPage.verificarMsgRestricaoAcesso("Unauthorized");
		communityPage.verificarMsgRestricaoAcesso("go back");
	}
	
	@Test
	public void editarCommunityComPerfilAdmin() throws InterruptedException {
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		for (int i = 0; i < 3; i++) {
			communityPage.cadastrarNovaCommunity();
			
			communityPage.setCommunity(gerarCommunityName());
			communityPage.setCity(gerarCidade());			
			communityPage.setZipCode(gerarCep());
			communityPage.selectState();
			
			communityPage.btnSave();		
		}
		
		communityPage.aguardarComponente(2000);
		communityPage.editarCommunity();
		
		communityPage.setCommunity(gerarCommunityName());
		communityPage.setCity(gerarCidade());			
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
	
		communityPage.btnSave();
		communityPage.verificarMsgCommunityCadastradoComSucesso("edited success!");
	}
	
	@Test
	public void removerCommunityComPerfilAdmin() {
		communityPage.acessarSistema("manuela_neves", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		communityPage.excluirCommunity();
		communityPage.btnConfirmarExclusaoCommunity();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasCommunitiesComPerfilAdmin() throws InterruptedException {
		communityPage.acessarSistema("manuela_neves", "abcd_123");

		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
	
		for (int i = 0; i < 3; i++) {
			communityPage.cadastrarNovaCommunity();
			
			communityPage.setCommunity(gerarCommunityName());
			communityPage.setCity(gerarCidade());			
			communityPage.setZipCode(gerarCep());
			communityPage.selectState();
			
			communityPage.btnSave();		
		}
		
		communityPage.aguardarComponente(2000);
		
		communityPage.selecionarCheckbox();		
		communityPage.excluirSelecionados();
		communityPage.verificarMsgCommunityCadastradoComSucesso("deleted success!");
	}

	@Test
	public void removerCommunityComPerfilSupervisor() {
		communityPage.acessarSistema("francisca_viana", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		communityPage.excluirCommunity();
		communityPage.btnConfirmarExclusaoCommunity();
		
		communityPage.verificarMsgCommunityCadastradoComSucesso("deleted success!");		
	}
	
	@Test
	public void removerVariasCommunitiesComPerfilSupervisor() throws InterruptedException {
		communityPage.acessarSistema("francisca_viana", "abcd_123");

		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
	
		for (int i = 0; i < 3; i++) {
			communityPage.cadastrarNovaCommunity();
			
			communityPage.setCommunity(gerarCommunityName());
			communityPage.setCity(gerarCidade());			
			communityPage.setZipCode(gerarCep());
			communityPage.selectState();
			
			communityPage.btnSave();		
		}
		
		communityPage.aguardarComponente(2000);
		
		communityPage.selecionarCheckbox();		
		communityPage.excluirSelecionados();
		communityPage.verificarMsgCommunityCadastradoComSucesso("deleted success!");
	}
	
	@Test
	public void editarCommunityComPerfilSupervisor() throws InterruptedException {
		communityPage.acessarSistema("francisca_viana", "abcd_123");
		
		communityPage.linkWorkOrders();
		communityPage.linkCommunity();
		
		for (int i = 0; i < 3; i++) {
			communityPage.cadastrarNovaCommunity();
			
			communityPage.setCommunity(gerarCommunityName());
			communityPage.setCity(gerarCidade());			
			communityPage.setZipCode(gerarCep());
			communityPage.selectState();
			
			communityPage.btnSave();		
		}
		
		communityPage.aguardarComponente(2000);
		communityPage.editarCommunity();
		
		communityPage.setCommunity(gerarCommunityName());
		communityPage.setCity(gerarCidade());			
		communityPage.setZipCode(gerarCep());
		communityPage.selectState();
		communityPage.aguardarComponente(3000);
	
		communityPage.btnSave();
		communityPage.verificarMsgCommunityCadastradoComSucesso("edited success!");
	}

	@After
	public void killDriver() throws IOException {		
		TakesScreenshot ss = (TakesScreenshot) driver;
		File arquivo  = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(arquivo, new File("target" + File.separator + "screenshot" + File.separator + testName.getMethodName() + ".jpg"));
		
		driver.close();
	}
}