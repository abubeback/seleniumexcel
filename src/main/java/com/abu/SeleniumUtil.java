package com.abu;

import com.abu.domain.TestCase;
import com.abu.domain.TestCaseConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {

    private static final String login = "zhe.w.wang@oracle.com";
    private static final String pwd = "67535539Orcl!";

    public static WebDriver driver;
    public static WebDriverWait wait;

    private SeleniumUtil() {
    }

    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\ProgramData\\chocolatey\\bin\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=en");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10000L);
        return driver;
    }

    public static void login(){
        driver.get("https://jira.oraclecorp.com/jira/browse/AEDM-5444");

        //driver.navigate().to("https://jira.oraclecorp.com/jira/browse/AEDM-5444");
        driver.findElement(By.xpath("//input[@id='sso_username']")).sendKeys(login);
        driver.findElement(By.xpath("//input[@id='ssopassword']")).sendKeys(pwd);

        driver.findElement(By.xpath("//a[contains(text(),'Sign')]")).click();
    }

    public static void firstCreate(){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='create_link']")));
        driver.findElement(By.xpath("//a[@id='create_link']")).click();
    }

    public static void createTestCase(TestCase testCase){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='project-single-select']/input")));
        driver.findElement(By.xpath("//div[@id='project-single-select']/input")).sendKeys("Agile EDM (AEDM)");
        driver.findElement(By.xpath("//div[@id='project-single-select']/input")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='issuetype-single-select']/input")));
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']/input")).sendKeys("Test");
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']/input")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='summary']")));
        driver.findElement(By.xpath("//input[@id='summary']")).sendKeys(testCase.getTitle());
        driver.findElement(By.xpath("//div[@id='create-issue-dialog']/div[2]/div/div/form/div/div[2]/div")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='customfield_10100:1']")));
        Select comp = new Select(driver.findElement(By.xpath("//select[@id='customfield_10100:1']")));
        comp.selectByVisibleText(testCase.getComponent());

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='customfield_10100']")));
        Select subcomp = new Select(driver.findElement(By.xpath("//select[@id='customfield_10100']")));
        subcomp.selectByVisibleText(testCase.getSubComponent());

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='assignee-field']")));
        driver.findElement(By.xpath("//input[@id='assignee-field']")).sendKeys(testCase.getAssign());
        driver.findElement(By.xpath("//div[@id='create-issue-dialog']/div[2]/div/div/form/div/div[2]/div")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='description']")));
        //driver.findElement(By.xpath("//textarea[@id='description']")).click();
        for(String line : testCase.getDescriptions()){
            driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys(line);
            driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys(Keys.ENTER);
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='create-issue-submit']")));
        driver.findElement(By.xpath("//input[@id='qf-create-another']")).click();
        driver.findElement(By.xpath("//input[@id='create-issue-submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".aui-message.aui-message-success.closeable")));
    }
}
