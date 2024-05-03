package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class RegisterTest extends TestBase {

    @Test
    public void ableToRegisterANewUser() {

        log.debug("ableToRegisterANewUser started");
        test.info("ableToRegisterANewUser started").assignAuthor("Henry").assignCategory("Register");

        String registerURL = "https://awesomeqa.com/ui/index.php?route=account/register";

        driver.get(registerURL);
        test.info("navigated to registration page");

        sendKeys(By.id(objectLocatorProps.getProperty("firstName")), "firstName", "John");

        sendKeys(By.id(objectLocatorProps.getProperty("lastName")), "lastName", "Doe");
        sendKeys(By.id(objectLocatorProps.getProperty("email")), "email", "john.doe@gmail.com");
        sendKeys(By.id(objectLocatorProps.getProperty("telephone")), "telephone", "000-000-0000");
        sendKeys(By.id(objectLocatorProps.getProperty("password")), "password", "F6pJhNYa4fLD2M@");
        sendKeys(By.id(objectLocatorProps.getProperty("passwordConfirm")), "passwordConfirm", "F6pJhNYa4fLD2M@");
        click(By.xpath(objectLocatorProps.getProperty("privacyPolicy")), "privacyPolicy");

        Reporter.log("navigated to registration page");
        Reporter.log("added first name");
        Reporter.log("added last name");
        Reporter.log("added email");
        Reporter.log("added phone number");
        Reporter.log("added password");
        Reporter.log("confirmed the password");
        Reporter.log("checked privacy policy");

        WebElement btnContinue = driver.findElement(By.xpath(objectLocatorProps.getProperty("continue")));
        Assert.assertTrue(btnContinue.isDisplayed());

        log.debug("ableToRegisterANewUser completed");
        test.info("ableToRegisterANewUser completed");

    }
}
