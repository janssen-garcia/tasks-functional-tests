package br.ce.wcaquino.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {

	public WebDriver acessarAplicacao() throws MalformedURLException{


		System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
		//WebDriver driver = new ChromeDriver();
		ChromeOptions chrome_options = new ChromeOptions();
				chrome_options.addArguments("--headless");
				chrome_options.addArguments("--no-sandbox");
				chrome_options.addArguments("--disable-dev-shm-usage");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY,chrome_options);
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.2:4444/wd/hub"),cap);
		
		driver.navigate().to("http://192.168.0.2:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException, InterruptedException{
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
				
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				LocalDate hoje = LocalDate.now();
				
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		        String hojeFormatado = hoje.format(formatter);				
				
				driver.findElement(By.id("dueDate")).sendKeys(hojeFormatado);
				
				driver.findElement(By.id("saveButton")).click();
				
				Thread.sleep(5000);
				
				String message = driver.findElement(By.id("message")).getText();
				
				Assert.assertEquals("Success!", message);
				
				driver.quit();
		}
		finally
		{
			driver.quit();
		}
				
	}
	
	
	@Test
	public void naodeveSalvarTarefaSemDescricao() throws MalformedURLException, InterruptedException{
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("dueDate")).sendKeys("24/04/2020");
				
				driver.findElement(By.id("saveButton")).click();
				
				Thread.sleep(5000);
				
				String message = driver.findElement(By.id("message")).getText();
								
				Assert.assertEquals("Fill the task description", message);
				
				driver.quit();
		}
		finally
		{
			driver.quit();
		}
				
	}
	
	@Test
	public void naodeveSalvarTarefaSemData() throws MalformedURLException, InterruptedException{
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				driver.findElement(By.id("saveButton")).click();
				
				Thread.sleep(5000);
				
				String message = driver.findElement(By.id("message")).getText();
				
				
				Assert.assertEquals("Fill the due date", message);
				
				driver.quit();
		}
		finally
		{
			driver.quit();
		}
				
	}
	
	@Test
	public void naodeveSalvarTarefaComDataPassada() throws MalformedURLException, InterruptedException{
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				driver.findElement(By.id("dueDate")).sendKeys("20/04/2020");
				
				driver.findElement(By.id("saveButton")).click();
				
				Thread.sleep(5000);
				
				String message = driver.findElement(By.id("message")).getText();
				
				Assert.assertEquals("Due date must not be in past", message);
				
				driver.quit();
		}
		finally
		{
			driver.quit();
		}
				
	}
	
}
