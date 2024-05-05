package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtils;

import java.util.HashMap;
import java.util.Hashtable;

public class SearchTest extends TestBase {

    @Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
    public void ableToSearchProducts(Hashtable<String, String> data) {

        log.debug("ableToSearchProducts started");
        test.info("ableToSearchProducts started").assignAuthor("Hank").assignCategory("Search");
        String searchURL = "https://awesomeqa.com/ui/index.php?route=common/home";

        driver.get(searchURL);
        test.info("navigated to home page");

        sendKeys(By.xpath(objectLocatorProps.getProperty("searchBox")), "searchBox", data.get("ProductName"));
        click(By.xpath(objectLocatorProps.getProperty("searchBtn")), "searchBtn");

        String productName = driver.findElement(By.xpath(objectLocatorProps.getProperty("productName"))).getText();
        String productPrice = driver.findElement(By.xpath(objectLocatorProps.getProperty("productPrice"))).getText();
        productPrice = processAndSplit(productPrice);
        test.info(productName + " " + productPrice);

        Assert.assertEquals(productName, data.get("ProductName"));
        Assert.assertEquals(productPrice, data.get("ProductPrice"));

        log.debug("ableToSearchProducts completed");
        test.info("ableToSearchProducts completed");
    }

    public static String processAndSplit(String input) {
        // Remove '\n' and replace with space
        String cleanedInput = input.replaceAll("\\n", " ");

        // Split the string based on space
        String[] parts = cleanedInput.split(" ");

        // Return the first item
        return parts[0];
    }
}





