package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest extends TestBase {

    @Test(dataProvider = "ableToSearchProductsDP")
    public void ableToSearchProducts(String name, String price) {

        log.debug("ableToSearchProducts started");
        test.info("ableToSearchProducts started").assignAuthor("Hank").assignCategory("Search");
        String searchURL = "https://awesomeqa.com/ui/index.php?route=common/home";

        driver.get(searchURL);
        test.info("navigated to home page");

        sendKeys(By.xpath(objectLocatorProps.getProperty("searchBox")), "searchBox", name);
        click(By.xpath(objectLocatorProps.getProperty("searchBtn")), "searchBtn");

        String productName = driver.findElement(By.xpath(objectLocatorProps.getProperty("productName"))).getText();
        String productPrice = driver.findElement(By.xpath(objectLocatorProps.getProperty("productPrice"))).getText();
        productPrice = processAndSplit(productPrice);
        test.info(productName + " " + productPrice);

        Assert.assertEquals(productName, name);
        Assert.assertEquals(productPrice, price);

        log.debug("ableToSearchProducts completed");
        test.info("ableToSearchProducts completed");
    }

    @DataProvider(name = "ableToSearchProductsDP")
    public Object[][] ableToSearchProductsDP() {

        String sheetName = "ableToSearchProducts";
        int rows = reader.getRowCount(sheetName);
        int columns = reader.getColumnCount(sheetName);
        Object[][] data = new Object[rows - 1][columns];

        for (int iRow = 2; iRow <= rows; iRow++) {
            for (int iCol = 0; iCol < columns; iCol++) {
                data[iRow - 2][iCol] = reader.getCellData(sheetName, iCol, iRow);
            }

        }

        return data;
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





