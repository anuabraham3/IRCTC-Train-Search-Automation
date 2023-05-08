package TrainSearchAutomation;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TrainSearch {
	
	public static WebDriver driver;
	
	//verify the correct page is opened
	public void verify() {
		String expectedResult = "IRCTC Next Generation eTicketing System";
		String actualResult=driver.getTitle();
		if(actualResult.equalsIgnoreCase(expectedResult))
			System.out.println("\nWebsite launch is verified \n");
		else
			System.out.println("\nWebsite launch is incorrect!! \n");
	}
	
	//Enter "Hyd" in “From city” field and select the "HYDERABAD DECAN - HYB" station
	public void fromHyd() throws InterruptedException {
		WebElement from =driver.findElement(By.xpath("//*[@id=\"origin\"]/span/input"));
		from.sendKeys("Hyd");
		Thread.sleep(2000);
		from.sendKeys(Keys.ARROW_DOWN);
		from.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
	}
	
	//Enter "Pune" in "To city” field and select the "PUNE JN - PUNE" station
	public void toPune() throws InterruptedException {
		WebElement to = driver.findElement(By.xpath("//*[@id=\"destination\"]/span/input"));
		to.sendKeys("Pune");
		Thread.sleep(2000);
		to.sendKeys(Keys.ARROW_DOWN);
		to.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
	}
	
	//Choose future date after 4days from today in the journey date field
	public void choosedate() throws InterruptedException {
        int days=5;
		LocalDate currentDate = LocalDate.now();
        int futureDate = currentDate.getDayOfWeek().plus(days).getValue();
        
        driver.findElement(By.xpath("//*[@id=\"jDate\"]/span/input"));
 	    driver.findElement(By.xpath("//*[@id=\"jDate\"]/span/input")).click();
 	    Thread.sleep(1000);
 	    driver.findElement(By.xpath("//*[@id=\"jDate\"]/span/div/div/div[2]/table/tbody/tr[2]/td["+futureDate+"]/a")).click();
	    Thread.sleep(1000);  
	}
	
	//Select "Sleeper Class" from all classes dropdown box
	public void sleeperClass() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"journeyClass\"]/div/div[3]/span")).click();
	    Thread.sleep(1000);
	    driver.findElement(By.xpath("//span[contains(text(),'Sleeper (SL)')]")).click();
	    Thread.sleep(1000);
	}
	
	//Select the checkbox "Divyaang concession"
	public void divyaangCheckbox() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[4]/div/span[1]/label")).click();
	    Thread.sleep(2000);
	}
	
	//Accepting the alerts that appear
	public void acceptAlerts() {
		driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/p-confirmdialog/div/div/div[3]/button/span[2]")).click();		
	}
	
	//Click on "Search" button and display the number of trains available and names on console
	public void search() throws InterruptedException
	{
		int count=0;
		driver.findElement(By.xpath("//*[@id=\"divMain\"]/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[5]/div[1]/button")).click();	
		String beforeXpath="/html/body/app-root/app-home/div[3]/div/app-train-list/div[4]/div/div[5]/div[";
		String afterXpath="]/div[1]/app-train-avl-enq/div[1]/div[1]/div[1]/strong";
		System.out.println("Available Trains are : \n");
		for(int i=1;i<=5;i++) {
			String actualXpath=beforeXpath+i+afterXpath;
			List<WebElement> results =driver.findElements(By.xpath(actualXpath));
			count+=results.size();
						
			for (WebElement webElement : results) {
				System.out.println(webElement.getText());
				}
		}
		System.out.println("\nTotal number of results : "+count);
		Thread.sleep(2000);
		
	}
	
	//Capture the results screenshot
	public void screenshot() 
	{
		TakesScreenshot ss = (TakesScreenshot)driver;
		File f = ss.getScreenshotAs(OutputType.FILE);
		String filePath="C:\\Users\\ANU ABRAHAM\\eclipse-workspace\\MiniProject\\target\\screenshot.png";
		File t = new File(filePath);
		try {
			FileUtils.copyFile(f,t);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		System.out.println("\nScreenshot taken successfully");
	}
	
	//Close the Application
	public void closeBrowser() {
		driver.quit();
	}
	

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
			System.out.println("\nEnter Browser Name(Edge/Chrome/Firefox) :");
			Scanner sc = new Scanner(System.in);
			String browser = sc.nextLine();
			 
			//incase of invalid input
			 if(!(browser.equalsIgnoreCase("edge")|| browser.equalsIgnoreCase("firefox")|| browser.equalsIgnoreCase("chrome"))) {
				 System.out.println("Invalid browser name!!");
				 System.out.println("\nPROGRAM TERMINATED");
				 System.exit(0);
			 }
			
			 //Setting up Edge browser
			 else if(browser.equalsIgnoreCase("Edge")) {
				
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();			 
			 }
			 
			 //Setting up Firefox browser
			 else if(browser.equalsIgnoreCase("firefox")) {
				 WebDriverManager.firefoxdriver().setup();
				 driver = new FirefoxDriver();
			 }
			 
			 //Setting up Chrome browser
			 else if(browser.equalsIgnoreCase("chrome")) {
				 WebDriverManager.chromedriver().setup();
				 driver = new ChromeDriver();
			 }
			 
			 //Launching the web application
			 driver.get("https://www.irctc.co.in");
			 Thread.sleep(2000);
			 
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			 TrainSearch train = new TrainSearch();
			 train.verify();
			 train.fromHyd();
			 train.toPune();
			 train.choosedate();
			 train.sleeperClass();
			 driver.findElement(By.xpath("//*[@id=\"disha-banner-close\"]")).click();
			 train.divyaangCheckbox();
			 train.acceptAlerts();
			 train.search();
			 train.screenshot();
			 train.closeBrowser();		 
			 
	}

}
