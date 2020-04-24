package br.ce.wcaquino.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {

	public WebDriver acessarAplicacao(){


		System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("http://localhost:8001/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	@Test
	public void deveSalvarTarefaComSucesso(){
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
				
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				driver.findElement(By.id("dueDate")).sendKeys("24/04/2020");
				
				driver.findElement(By.id("saveButton")).click();
				
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
	public void naodeveSalvarTarefaSemDescricao(){
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("dueDate")).sendKeys("24/04/2020");
				
				driver.findElement(By.id("saveButton")).click();
				
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
	public void naodeveSalvarTarefaSemData(){
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				driver.findElement(By.id("saveButton")).click();
				
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
	public void naodeveSalvarTarefaComDataPassada(){
		
		WebDriver driver = acessarAplicacao();
		
		try
		{
				driver.findElement(By.id("addTodo")).click();
								
				driver.findElement(By.id("task")).sendKeys("Test via Selenium");
				
				driver.findElement(By.id("dueDate")).sendKeys("20/04/2020");
				
				driver.findElement(By.id("saveButton")).click();
				
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
