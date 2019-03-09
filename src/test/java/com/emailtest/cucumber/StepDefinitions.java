package com.emailtest.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {

    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "./chromedriver";
    private final String GMAIL_URL = "https://mail.google.com/";
    private final String USER_MAIL = "yufeitestdev@gmail.com";
    private final String USER_PASSWORD = "lyf940915";


    @Given("^I am logged in to gmail main page$")
    public void givenIamLoggedIn() throws InterruptedException {
        setupSeleniumWebDrivers();

        driver.get(GMAIL_URL);
        WebElement user_field = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.name("identifier")));
        user_field.sendKeys(USER_MAIL);

        WebElement userNext = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("identifierNext")));
        userNext.click();

        WebElement password_field = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.name("password")));
        password_field.sendKeys(USER_PASSWORD);

        WebElement passwordNext = (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("passwordNext")));
        passwordNext.click();

        waitUntilRefreshed();


        checkSystemInInitialState();
    }

    @When("^I click on Compose$")
    public void whenIClickOnCompose() throws InterruptedException {
        WebElement compose = driver.findElement(By.className("z0"));
        compose.click();
        waitUntilRefreshed();
    }

    @And("^I add recipient A to my email$")
    public void andIAddRecipientA() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.name("to"))));
        WebElement recipient = driver.findElement(By.name("to"));
        recipient.sendKeys("yufeitestdev@gmail.com");
    }

    @And("^I add recipient B to my email$")
    public void andIAddRecipientB() {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.name("to"))));

        WebElement recipient = driver.findElement(By.name("to"));
        recipient.sendKeys("kevenliu94@gmail.com");
    }

    @And("^I add a subject to my email$")
    public void andIAddSubject() {
        WebElement subject = driver.findElement(By.name("subjectbox"));
        subject.sendKeys("ECSE 428 A2 Test Email");
    }

    @And("^I add Picture A to my email$")
    public void andIAddPictureA() throws AWTException, InterruptedException {

        waitUntilRefreshed();

        File image = new File("cat.jpg");
        System.out.println(image);

        String path = image.getAbsolutePath();
        System.out.println(path);

        driver.findElement(By.name("Filedata")).sendKeys(path);

    }

    @And("^I add Picture B to my email$")
    public void andIAddPictureB() throws AWTException, InterruptedException {
        waitUntilRefreshed();

        File image = new File("dog.jpeg");

        String path = image.getAbsolutePath();

        driver.findElement(By.name("Filedata")).sendKeys(path);
    }

    @And("^I click Send$")
    public void andIClickSend() {
        WebElement sendButton = driver.findElement(By.xpath("//div[text()='Send']"));
        sendButton.click();
    }

    @Then("^the email should be sent$")
    public void thenTheEmailShouldBeSent() throws InterruptedException {
        waitUntilRefreshed();
        // Navigate to sent messages
        WebElement sentEmails = driver.findElement(By.xpath("//*[@class='TN bzz aHS-bnu']"));
        sentEmails.click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("//div[text()='To: ']"))));

        // Check that the email is sent
        if (driver.getPageSource().contains("ECSE 428 A2 Test Email") != true) {
            driver.close();
            throw new Error("Email not sent!");
        }

        checkSystemInInitialState();
        driver.close();
    }

    @Then("^an error message is displayed specifying recipient is needed$")
    public void thenAnErrorMessageIsDisplayedSpecifyingRecipientIsNeeded() throws InterruptedException, AWTException {

        waitUntilRefreshed();
        driver.findElement(By.name("ok")).click();

        checkSystemInInitialState();
        driver.close();
    }

    private void setupSeleniumWebDrivers()  {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private void checkSystemInInitialState() {

        // Check that I am logged in and on the Gmail home page
        if (!driver.getPageSource().contains("Yufei")
                || !driver.getPageSource().contains("Social")
                || !driver.getPageSource().contains("Promotions")) {
            driver.close();
            throw new Error("System not in initial state!");
        }

    }

    private void waitUntilRefreshed() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);

    }

}

