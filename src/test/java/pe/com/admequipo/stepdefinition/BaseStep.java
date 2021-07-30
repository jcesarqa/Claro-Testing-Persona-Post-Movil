package pe.com.admequipo.stepdefinition;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.ibmgarage.ExtentReporter;
import cucumber.api.Scenario;


public abstract class BaseStep {
	private static WebDriver driver=getDriverLogin();
	public BaseStep() {
		// TODO Auto-generated constructor stub
	}
	
	
	public WebDriver getDriver(){
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		ChromeOptions chromeOptions = new ChromeOptions();
		
		mobileEmulation.put("deviceName", "iPhone 5/SE");		
		chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		
		//System.setProperty("webdriver.chrome.driver","./src/test/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver(chromeOptions);
		
		return driver;
	}
	
	public static WebDriver getDriverLogin(){
		
		if(driver==null){
			 driver=new WebDriverBuilder().getDefaultDriver();
			
		}

		
		
		return driver;
	}

	public void waitForLoad() {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void esperarSegundos(int segundos) {
		driver.manage().timeouts().implicitlyWait(segundos, TimeUnit.SECONDS);
	}
	
	@Deprecated
	public void waitForElementIsVisible(WebElement element) {
		   final WebElement el=element;
		    ExpectedCondition<Boolean> waitCondition = new
		            ExpectedCondition<Boolean>() {
		                public Boolean apply(WebDriver driver) {
		                    return el.isDisplayed();
		                }
		            };
		    WebDriverWait wait = new WebDriverWait(driver, 30);
		    wait.until(waitCondition);
		}
	
	public WebElement waitForElementIsVisible(By locator) {
		 
		   WebElement dynamicElement = (new WebDriverWait(driver, 30))
				   .until(ExpectedConditions.visibilityOfElementLocated(locator));
		  return dynamicElement;
		}
	
	public void clickByJavascript(WebElement element){
		String js = "arguments[0].click();";
		// ejecutamos el evento click en el elemento
		((JavascriptExecutor) driver).executeScript(js, element);
		
	}

	public void captura(String archivo, String nombre) throws InterruptedException {
		File caputura = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(caputura, new File (".\\target\\extent-report\\"+archivo+"\\"+nombre+".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void takeScreenshot(){
		System.out.println("cerrando after");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //The below method will save the screen shot in d drive with name "screenshot.png"
       //File f=new File("");
		//FileUtils.copyFile(scrFile, new File("D:\\screenshot.png"));
	
		/*try {
			ExtentReporter.addScreenCaptureFromPath(scrFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
	protected void capturarScreenShoot(Scenario scenario,int step) {
		WebDriver driver=BaseStep.getDriverLogin();
	   	File srcScreenShoot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   	File fileReport=new File( ExtentReporter.getExtentHtmlReport().config().getFilePath());
	   	String fname=scenario.hashCode()+"_"+step+".png";
		File destScreenShoot= new File(fileReport.getParent()+File.separator+fname);
	try {
		java.nio.file.Files.copy(srcScreenShoot.toPath(), destScreenShoot.toPath());
		ExtentReporter.addScreenCaptureFromPath(fname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Actions accion;
	Select lista1;

	public WebElement findElement (By locator) {
		return driver.findElement(locator);
	}
	
	public void mover (WebElement element) {
		accion = new Actions(driver);
	    accion.moveToElement(element).perform();
	}
	
	public void scroll (String script,String argument) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo"+script,argument);
	}
	
	public void scrollfinal () {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);","");
	}
	
	public void scrollinicio () {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);","");
	}
	
	public String ubicacion (By locator) {
		String ubicacion = String.valueOf(driver.findElement(locator).getLocation());
		return ubicacion;
	}
	
	public String ubicacion (WebElement locator) {
		String ubicacion = String.valueOf(locator.getLocation());
		return ubicacion;
	}
	
	public List<WebElement> findElements (By locator) {
		return driver.findElements(locator);
	}
	
	public void selectList (WebElement element,int index) {
		lista1 = new Select(element);
		lista1.selectByIndex(index);
	}
	
	public String getText (WebElement element) {
		return element.getText();
	}
	
	public void clear (By locator) {
		driver.findElement(locator).clear();
	}
	
	public void type (String inputText, By locator) {
		driver.findElement(locator).sendKeys(inputText);
	}
	
	public void click (By locator) {
		driver.findElement(locator).click();
	}
	
	public Boolean isDisplay (By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e){ 
			return false;
		}
	}
	
	public void visit (String url) {
		driver.get(url);
	}
	
	public void switchTo (By frame) {
		driver.switchTo().frame(driver.findElement(frame));
	}
	
	public int random(int min, int max) {
		return new Random().nextInt((max-min) + max)+min;
	}
	
}
