package pe.com.admequipo.stepdefinition;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverBuilder {

	private WebDriver driver;

	private String path;
	String user = "jcesarqa";
	String token = "fccc4649-c11d-4e25-b0db-311158a2d86c";
	private String urlServer="https://"+user+":"+token+"@ondemand.us-west-1.saucelabs.com:443/wd/hub";
	
	
	boolean isGrid=false;
	public WebDriverBuilder() {
		File f = new File("src/test/resources/drivers");
		path = f.getAbsolutePath();
		String grid=System.getProperty("GRID");
		if(grid!=null && grid.toLowerCase().equals("yes")){
			isGrid=true;
		}
	}

	public WebDriver getDriver(BROWSER browser) {

		switch (browser) {
		case CHROME:
			if(isGrid){
				buildDriverForGridServer(BrowserType.CHROME);
			}else{
				buildChromeDriver();
			}
		
			break;
		case FIREFOX:
			if(isGrid){
			buildDriverForGridServer(BrowserType.FIREFOX);
			}else{
				buildFirefoxDriver();
			}
			
			break;
		case EDGE:
			buildEdgeDriver();
			break;
		case IE:
			buildIEDriver();
			break;

		default:
			System.setProperty("webdriver.chrome.driver", path + "/chromedriver.exe");
			driver = new ChromeDriver();
			break;

		}
		System.out.println("returning " + driver);
		return driver;
	}

	private void buildIEDriver() {
		System.setProperty("webdriver.ie.driver", path + "/IEDriverServer.exe");
		System.setProperty("BROWSER", BROWSER.IE.toString());
		driver = new InternetExplorerDriver();
	}

	private void buildEdgeDriver() {
		System.setProperty("webdriver.edge.driver", path + "/MicrosoftWebDriver.exe");
		System.setProperty("BROWSER", BROWSER.EDGE.toString());
		driver = new EdgeDriver();
	}

	private void buildFirefoxDriver() {
		System.setProperty("webdriver.gecko.driver", path + "/geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions().setProfile(new FirefoxProfile());
		System.setProperty("BROWSER", BROWSER.FIREFOX.toString());
		driver = new FirefoxDriver(options);
	}

	private void buildChromeDriver() {
		System.setProperty("webdriver.chrome.driver", path + "/chromedriver.exe");
		System.setProperty("BROWSER", BROWSER.CHROME.toString());
		String mode = System.getProperty("Mode");
		if ("Mobile".equals(mode))
			driver = new ChromeDriver(getChromeMobileConfiguration());
		else
			driver = new ChromeDriver();
	}
	
	public WebDriver getDefaultDriver(){
		String driverType=System.getProperty("BROWSER");
		
		if(driverType==null){
			driverType=BROWSER.CHROME.toString();
		}
		return getDriver(BROWSER.valueOf(driverType));
		
	}
	
	public void buildDriverForGridServer(String browser)
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.WIN10);
		URL urlSeleniumGrid=null;
		
		try {
			urlSeleniumGrid = new URL(urlServer);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		driver = new RemoteWebDriver(urlSeleniumGrid, capabilities);	

	}
	
	
	public ChromeOptions getChromeMobileConfiguration(){
		
		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "Nexus 5");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		return chromeOptions;
	}
	
	
	public enum BROWSER{
		CHROME,
		FIREFOX,
		EDGE,
		IE
		
		
	}

}
