package pe.com.admequipo.stepdefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import pe.com.claro.miclaroagileqa.PropertyManager;

public class LoginBackground extends BaseStep {

	private final String userCorpName = PropertyManager.getPropety("");
	private final String userCorpPassword = PropertyManager.getPropety("");

	private final String userConsumerName = PropertyManager.getPropety("");
	private final String userConsumerPassword = PropertyManager.getPropety("");
	private final String loginUrl = PropertyManager.getPropety("");

	private WebDriver driver = null;

	public LoginBackground() {
		driver = super.getDriver();
	}

	private void openBrowser() {

		driver.get(loginUrl);
	}

	private void ingresoCredenciales(String user, String pwd) {
		esperarSegundos(10);
		String URL = driver.getCurrentUrl();
		if (URL.contains("wps/portal/baseportal/login")) {
			WebElement btnLogin = driver.findElement(By.id("login.button.login"));
			if (btnLogin != null && btnLogin.isEnabled()) {
				esperarSegundos(5);
				WebElement userTxt = driver.findElement(By.id("userID"));
				WebElement passwdTxt = driver.findElement(By.id("password"));
				userTxt.sendKeys(user);
				passwdTxt.sendKeys(pwd);
				btnLogin.click();

			}
		} else {
			System.out.println("ya existe una sesion abierta");
		}

	}

	private void estoyEnHome() {
		waitForLoad();
		String URL = driver.getCurrentUrl();
		esperarSegundos(10);
		if (!URL.contains("/home/") && !URL.contains("wps/portal/baseportal/login")) {
			WebElement inicio = driver.findElement(By.xpath("//*[@class='menu']//a[@class='inicio']"));
			inicio.click();

		}

		//assertTrue(URL.contains("/home/"));
	}

	@Given("^usuario corporativo logueado$")
	public void usuarioCorporativoLogueado() throws Throwable {
		System.out.println("llamando a corporativo logueado " + driver.getCurrentUrl());
		String userType = "corporativo";
		checkUserTypeInSession(userType);
		openBrowser();
		driver.manage().addCookie(new Cookie("userType", userType));
		ingresoCredenciales(userCorpName, userCorpPassword);
		estoyEnHome();
	}

	@Given("^usuario consumer logueado$")
	public void usuarioConsumerLogueado() throws Throwable {
		String userType = "consumer";
		checkUserTypeInSession(userType);
		openBrowser();
		driver.manage().addCookie(new Cookie("userType", userType));
		ingresoCredenciales(userConsumerName, userConsumerPassword);
		estoyEnHome();
	}

	public void checkUserTypeInSession(String userType) {
		if (driver.manage().getCookieNamed("userType") != null) {
			if (!driver.manage().getCookieNamed("userType").getValue().equals(userType)) {
				driver.manage().deleteAllCookies();
				System.out.println("borrando cookies");
				driver.manage().addCookie(new Cookie("userType", userType));
			}
		} 
	}

}
