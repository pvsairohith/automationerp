package Testcases;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class klerp {
	private static WebDriver driver;
	private String burl;
	@BeforeTest
	public void setup()
	{
		System.setProperty("webdriver.gecko.driver","C:\\selenium\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
	}
	@Test(priority=0)
	public void test1() throws IOException, TesseractException, InterruptedException
	{
		burl = "https://newerp.kluniversity.in/";
		driver.get(burl);
		driver.findElement(By.xpath("//span[text()='  Login']")).click();
		for(String handle:driver.getWindowHandles())
		{
			driver.switchTo().window(handle);
		}
		try
		{
			Thread.sleep(2000);
		driver.findElement(By.id("loginform-username")).sendKeys("190031212");
		Thread.sleep(2000);
		driver.findElement(By.id("loginform-password")).sendKeys("24/05/2002");
		Thread.sleep(2000);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		WebElement imageelement = driver.findElement(By.id("loginform-captcha-image"));
		File src= ((TakesScreenshot)imageelement).getScreenshotAs(OutputType.FILE);
		String path=("C:\\selenium\\pic5.txt"+".png");
		FileHandler.copy(src,new File(path));
		
		Tesseract image = new Tesseract();
		image.setDatapath("C:\\Users\\Hp\\eclipse-workspace\\WebDriverMavenProject\\testdata");
		String str=	image.doOCR(new File(path));
		System.out.println(str);
		Thread.sleep(2000);
		driver.findElement(By.id("loginform-captcha")).sendKeys(str);
		
		driver.findElement(By.xpath("//button[@class='btn btn-primary']")).click();
		Thread.sleep(4000);
		WebElement element = driver.findElement(By.linkText("My CGPA"));
		Actions  actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
		Thread.sleep(5000);
		
		//new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.cssSelector("li.active:nth-child(14) > a:nth-child(1)"))).click();
	}
	@Test(priority=2)
	public void test2() throws InterruptedException
	{
		WebElement element1 = driver.findElement(By.linkText("Profile"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element1).click().build().perform();
		
		Thread.sleep(2000);
		WebElement element2 = driver.findElement(By.xpath("//a[@href='/index.php?r=usermanagement%2Fuser%2Fresetpassword']"));
		Actions actionss = new Actions(driver);
		actionss.moveToElement(element2).click().build().perform();
		
		Thread.sleep(2000);
		String password1 = "2002@sairohith";
		driver.findElement(By.id("dynamicmodel-enterpassword")).sendKeys(password1);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@type='password'])[2]")).sendKeys(password1);
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		
		WebElement message = driver.findElement(By.xpath("//*[@id=\"w1-success-0\"]"));
		String message1 = message.getText();
		if(message1 =="New password saved.")
		{
			System.out.println("password updated");
		}
		else
		{
			System.out.println(message1);
		}
		Thread.sleep(2000);
		WebElement element = driver.findElement(By.linkText("My CGPA"));
		Actions  actions1 = new Actions(driver);
		actions1.moveToElement(element).click().build().perform();
		Thread.sleep(2000);
		
		List<WebElement> rowelements=driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[5]/table/tbody/tr"));
		int rowsize = rowelements.size();
		
		List<WebElement> columnelements = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[5]/table/tbody/tr[1]/td"));
		int columnsize = columnelements.size();
		float sum=0;
		float sum1=0;
		int count=0;
		for(int i=1;i<=rowsize;i++)
		{
			WebElement score = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[5]/table/tbody/tr["+ i +"]/td[7]"));
			String res =  score.getText();
			Thread.sleep(2000);
			WebElement credit = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[5]/table/tbody/tr[1]/td[8]"));
			String cgp = credit.getText();
			float gpa = Float.parseFloat(cgp);
			sum1=sum1+gpa;
			
			int marks = Integer.parseInt(res);
			sum = sum+(marks*gpa);
			count++;
		}
		float cgpascore = sum/sum1;
		WebElement realcgpa = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div[1]/div[1]/div[2]/div[3]"));
		String realcgp = realcgpa.getText();
		  System.out.println(realcgp);
			System.out.println(cgpascore);
			
	}
	
	@Test(priority=3)
	public void attendancecheck() throws InterruptedException
	{
		WebElement elem = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[1]/div/div/div/ul/li[4]/a"));
		Actions act = new Actions(driver);
		act.moveToElement(elem).click().build().perform();
		Thread.sleep(2000);
		
		WebElement year = driver.findElement(By.id("dynamicmodel-academicyear"));
		Select selcateg = new Select(year);
		selcateg.selectByVisibleText("2021-2022");
		
		Thread.sleep(2000);
		WebElement sem = driver.findElement(By.id("dynamicmodel-semesterid"));
		Select selsem = new Select(sem);
		selsem.selectByVisibleText("Odd Sem");
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div/form/div/div[4]/button[1]")).click();
		
		List<WebElement>row1 = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[1]/div[1]/div/div[1]/table/tbody/tr"));
		int p = row1.size();
		
		List<WebElement> col1 = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[1]/div[1]/div/div[1]/table/tbody/tr[1]/td"));
		int q = col1.size();
		
		for(int i=1;i<=p;i++)
		{
			WebElement atten = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[1]/div[1]/div/div[1]/table/tbody/tr["+ i +"]/td[9]"));
			String attend = atten.getText();
			int conduct = Integer.parseInt(attend);
			
			WebElement attende = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[1]/div[1]/div/div[1]/table/tbody/tr["+ i +"]/td[10]"));
			String attended = attende.getText();
			int completed = Integer.parseInt(attended);
			
			System.out.println(conduct);
			System.out.println(completed);
			float result = (completed*100)/conduct;
			System.out.println(result);
			
			WebElement attendresult = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[1]/div[1]/div/div[1]/table/tbody/tr["+ i +"]/td[13]"));
			String attres = attendresult.getText();
			System.out.println(attres);
		}
		
	}
	@Test(priority=4)
	public void download() throws InterruptedException
	{
		Thread.sleep(2000);
		WebElement down = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[1]/div/div/div/ul/li[5]/a"));
		Actions act = new Actions(driver);
		act.moveToElement(down).click().build().perform();
		
		Thread.sleep(2000);
		
		WebElement down1 = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[1]/div/div/div/ul/li[5]/ul/li[1]/a"));
		Actions act1 = new Actions(driver);
		act1.moveToElement(down1).click().build().perform();
		
		Thread.sleep(2000);
		WebElement load = driver.findElement(By.id("dynamicmodel-academicyear"));
		Select handload = new Select(load);
		handload.selectByVisibleText("2021-2022");
		Thread.sleep(2000);
		
		WebElement load1 = driver.findElement(By.id("dynamicmodel-semester"));
		Select handload1 = new Select(load1);
		handload1.selectByVisibleText("Odd Sem");
		Thread.sleep(3000);
		
		
	    driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div/form/div/div[3]/button[1]")).click();
	    
	    Thread.sleep(3000);
		
		WebElement save = driver.findElement(By.xpath("(//span[@class='glyphicon glyphicon-eye-open'])[1]"));
		Actions act11 = new Actions(driver);
		act11.moveToElement(save).click().build().perform();
		
		
		for(String handle: driver.getWindowHandles())
		{
			driver.switchTo().window(handle);
		}
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Download/Print']")).click();
		for(String handle: driver.getWindowHandles())
		{
			driver.switchTo().window(handle);
		}
		driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div[4]/div/div/div[1]/button")).click();
		System.out.println("course handout we can download");
		
	}
	@Test(priority=5)
	public void checktimetable() throws InterruptedException
	{
		WebElement time = driver.findElement(By.xpath("//div[@id='mainlayout_sidenav']/div[1]/div[1]/ul[1]/li[13]/a[1]"));
		Actions act = new Actions(driver);
		act.moveToElement(time).click().build().perform();
		Thread.sleep(2000);
		
		WebElement time1 = driver.findElement(By.xpath("//div[@id='mainlayout_sidenav']/div[1]/div[1]/ul[1]/li[13]/ul[1]/li[1]/a[1]"));
		Actions act1 = new Actions(driver);
		act1.moveToElement(time1).click().build().perform();
		Thread.sleep(2000);
		
		WebElement timetab = driver.findElement(By.id("universitymasteracademictimetableview-academicyear"));
		Select s = new Select(timetab);
		s.selectByVisibleText("2021-2022");
		
		Thread.sleep(2000);
		WebElement timetab1 = driver.findElement(By.id("universitymasteracademictimetableview-semesterid"));
		Select s1 = new Select(timetab1);
		s1.selectByVisibleText("Odd Sem");
		
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary')]")).click();
		
		Thread.sleep(2000);
		List<WebElement> webtime = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div/div/div/table/tbody/tr"));
		int timerow = webtime.size();
		
		List<WebElement> webtime1 = driver.findElements(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div/div/div/table/tbody/tr[1]/td"));
		int timecol = webtime1.size();
		
		for(int i=1;i<=timerow;i++)
		{
			for(int j=1;j<=timecol;j++)
			{
			WebElement val = driver.findElement(By.xpath("/html/body/div[1]/div[4]/div/div/div[2]/div/div/div/div/table/tbody/tr["+ i +"]/td["+ j +"]"));
			String stat = val.getText();
			if(stat!="-")
			{
				System.out.println(stat);
			}
			}
		}
		
		
	}
	
	@Test(priority = 6)
	public void logout() throws InterruptedException
	{
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[contains(@class,'btn btn-link')]")).click();
		driver.findElement(By.xpath("//span[text()='  Login']")).click();
		
	}
	
	
	@AfterClass
	public void end() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.quit();
	}
	
}
