package pe.com.admequipo.stepdefinition;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.ibmgarage.ExtentReporter;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;



public class Hooks {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
	
	@Before
	public void generalSetUp(){
		System.out.println("inicio::::::");
	}
	@After
	public void generalTearDown(Scenario scenario){
		
	 capturarScreenShoot(scenario);
	}
	
	
	private void capturarScreenShoot(Scenario scenario) {
		WebDriver driver=BaseStep.getDriverLogin();
	   	File srcScreenShoot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	   	File fileReport=new File( ExtentReporter.getExtentHtmlReport().config().getFilePath());
	   	String dt=sdf.format(new Date());
	   	String fname=scenario.hashCode()+"_"+dt+".png";
		File destScreenShoot= new File(fileReport.getParent()+File.separator+fname);
	try {
		java.nio.file.Files.copy(srcScreenShoot.toPath(), destScreenShoot.toPath());
		ExtentReporter.addScreenCaptureFromPath(fname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
