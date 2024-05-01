package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest extends TestBase {

    @Test(dataProvider = "productNamesAndPrices")
    public void ableToSearchProducts(String name, String price) {

        log.debug("ableToSearchProducts started");
        String searchURL = "https://awesomeqa.com/ui/index.php?route=common/home";

        driver.get(searchURL);
        driver.findElement(By.xpath(objectLocatorProps.getProperty("searchBox"))).sendKeys(name);
        driver.findElement(By.xpath(objectLocatorProps.getProperty("searchBtn"))).click();

        String productName = driver.findElement(By.xpath(objectLocatorProps.getProperty("productName"))).getText();
        String productPrice = driver.findElement(By.xpath(objectLocatorProps.getProperty("productPrice"))).getText();
        productPrice = processAndSplit(productPrice);

        Assert.assertEquals(productName, name);
        Assert.assertEquals(productPrice, price);

        log.debug("ableToSearchProducts completed");
    }

    @DataProvider(name = "productNamesAndPrices")
    public Object[][] productNamesAndPrices() {

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

//        return new Object[][]
//                {
//                        {"Canon EOS 5D", "$98.00"},
//                        {"Samsung Galaxy Tab 10.1", "$241.99"},
//                        {"MacBook Air", "$1,202.00"}
//                };
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





