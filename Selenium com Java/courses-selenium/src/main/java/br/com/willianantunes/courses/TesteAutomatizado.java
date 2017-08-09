package br.com.willianantunes.courses;

import java.io.IOException;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class TesteAutomatizado {
	public static void main(String[] args) throws IOException {		
		viaChrome();
	}
	
	private static void viaChrome() throws IOException {
        System.setProperty("webdriver.chrome.driver", Paths.get("src/main/resources/chromedriver.exe").toRealPath().toString()); 
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.bing.com/");
        WebElement q = driver.findElement(By.name("q"));
        q.sendKeys("Caelum");
        q.submit();
	}
	
	private static void viaFirefox() throws IOException {
		System.setProperty("webdriver.gecko.driver", Paths.get("src/main/resources/geckodriver.exe").toRealPath().toString());

		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
		capabilities.setCapability("marionette", true);
		WebDriver myDriver = new FirefoxDriver(capabilities);
		
		myDriver.get("http://www.bing.com/");
		
		WebElement campoDeTexto = myDriver.findElement(By.name("q"));
		campoDeTexto.sendKeys("Caelum");
		campoDeTexto.submit();
	}
}
