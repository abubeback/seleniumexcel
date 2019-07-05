package com.abu;

import com.abu.domain.TestCaseConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;

public class HelloWorld {
    public static void main(String[] args) throws MalformedURLException {
        //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), DesiredCapabilities.chrome());
        WebDriver driver = getDriver("en");

//        System.setProperty("webdriver.gecko.driver","D:\\Software\\Java\\driver\\geckodriver.exe");
//        String proxyName = "cn-proxy.jp.oracle.com:80";
//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy(proxyName)
//                .setFtpProxy(proxyName)
//                .setSslProxy(proxyName);
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
//        desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
//
//        ProfilesIni pi = new ProfilesIni();
//        FirefoxProfile profile = pi.getProfile("default");
//        FirefoxOptions options = new FirefoxOptions(desiredCapabilities).setProfile(profile);
//        WebDriver driver = new FirefoxDriver(options);

        //driver.get("http://www.itest.info");

        WebDriverWait wait = new WebDriverWait(driver, 10000L);
        try {
            driver.get("https://jira.oraclecorp.com/jira/browse/AEDM-5444");

            //driver.navigate().to("https://jira.oraclecorp.com/jira/browse/AEDM-5444");
            driver.findElement(By.xpath("//input[@id='sso_username']")).sendKeys("zhe.w.wang@oracle.com");
            driver.findElement(By.xpath("//input[@id='ssopassword']")).sendKeys("67535539Orcl!");

            driver.findElement(By.xpath("//a[contains(text(),'Sign')]")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='create_link']")));
            driver.findElement(By.xpath("//a[@id='create_link']")).click();


            createTest(driver,wait,"111","111111");
            createTest(driver,wait,"222","222222");
        } finally {
            //driver.close();
        }


        String title = driver.getTitle();
        System.out.printf(title);

        driver.close();
    }

    public static WebDriver getDriver(String locale){
        System.setProperty("webdriver.chrome.driver", "C:\\ProgramData\\chocolatey\\bin\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--lang=" + locale);
        return new ChromeDriver(options);
    }

    public static void createTest(WebDriver driver, WebDriverWait wait, String title, String desc){


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='project-single-select']/input")));
        driver.findElement(By.xpath("//div[@id='project-single-select']/input")).sendKeys("Agile EDM (AEDM)");
        driver.findElement(By.xpath("//div[@id='project-single-select']/input")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='issuetype-single-select']/input")));
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']/input")).sendKeys("Test");
        driver.findElement(By.xpath("//div[@id='issuetype-single-select']/input")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='summary']")));
        driver.findElement(By.xpath("//input[@id='summary']")).sendKeys(title);
        driver.findElement(By.xpath("//div[@id='create-issue-dialog']/div[2]/div/div/form/div/div[2]/div")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='customfield_10100:1']")));
        Select comp = new Select(driver.findElement(By.xpath("//select[@id='customfield_10100:1']")));
        comp.selectByVisibleText("Oracle Agile Engineering Data Management (4436) / Base Component (BAS)");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='customfield_10100']")));
        Select subcomp = new Select(driver.findElement(By.xpath("//select[@id='customfield_10100']")));
        subcomp.selectByVisibleText("Extensions for STEP AP214 (214)");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='assignee-field']")));
        driver.findElement(By.xpath("//input[@id='assignee-field']")).sendKeys(TestCaseConstants.EMAIL);
        driver.findElement(By.xpath("//input[@id='assignee-field']")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='description']")));
        //driver.findElement(By.xpath("//textarea[@id='description']")).click();
        driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys(desc);
        driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='create-issue-submit']")));
        driver.findElement(By.xpath("//input[@id='qf-create-another']")).click();
        driver.findElement(By.xpath("//input[@id='create-issue-submit']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".aui-message.aui-message-success.closeable")));

    }

    public void setAttribute(WebDriver driver, WebElement element, String attName, String attValue) {

//        driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
//                element, attName, attValue);
    }
}
