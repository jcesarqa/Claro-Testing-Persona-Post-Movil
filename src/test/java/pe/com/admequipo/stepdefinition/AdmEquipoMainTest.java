package pe.com.admequipo.stepdefinition;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.ibmgarage.ExtentReporter;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		snippets = SnippetType.CAMELCASE,
		plugin = {"html:output/html-report", "com.ibmgarage.ExtentFormatter:target/extent-report/index.html"},		
features={"src/test/resources/features"}

)
public class AdmEquipoMainTest {

	public AdmEquipoMainTest() {
		// TODO Auto-generated constructor stub
		
	}
	@BeforeClass
	public static void setup(){
		System.setProperty("java.net.preferIPv4Stack", "true");
		ExtentReporter.setConfig("src/test/resources/extent-config.xml");
	}
	
	@AfterClass
	public static void tearDown(){
		
		String browser=System.getProperty("BROWSER");
		browser=browser==null?"CHROME":browser;
        ExtentReporter.setSystemInfo("Browser", browser);
       // ExtentReporter.setSystemInfo("Selenium", "v2.53.1");      
		BaseStep.getDriverLogin().close();
		if(!browser.equals("FIREFOX")){
		BaseStep.getDriverLogin().quit();
		}
       
        
	}
}
