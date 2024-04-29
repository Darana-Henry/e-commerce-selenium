package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class RegisterTest extends TestBase {

    @Test
    public void ableToRegisterANewUser() {

        String registerURL = "https://awesomeqa.com/ui/index.php?route=account/register";

        driver.get(registerURL);
        driver.findElement(By.id(objectLocatorProps.getProperty("firstName"))).sendKeys("John");
        driver.findElement(By.id(objectLocatorProps.getProperty("lastName"))).sendKeys("Doe");
        driver.findElement(By.id(objectLocatorProps.getProperty("email"))).sendKeys("john.doe@gmail.com");
        driver.findElement(By.id(objectLocatorProps.getProperty("telephone"))).sendKeys("000-000-0000");
        driver.findElement(By.id(objectLocatorProps.getProperty("password"))).sendKeys("F6pJhNYa4fLD2M@");
        driver.findElement(By.id(objectLocatorProps.getProperty("passwordConfirm"))).sendKeys("F6pJhNYa4fLD2M@");
        driver.findElement(By.xpath(objectLocatorProps.getProperty("privacyPolicy"))).click();
    }
}