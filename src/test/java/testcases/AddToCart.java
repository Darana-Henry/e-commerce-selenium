package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AddToCart extends TestBase {


    @Test
    public void loginToApp() {

        test.info("loginToApp started").assignAuthor("Henirich").assignCategory("Add Cart");

        String loginURL = "https://awesomeqa.com/ui/index.php?route=account/login";
        driver.get(loginURL);

        sendKeys(By.id(objectLocatorProps.getProperty("emailTxt")), "emailTxt", "first.last@gmail.com");
        sendKeys(By.id(objectLocatorProps.getProperty("passwordTxt")), "passwordTxt", "password");
        click(By.xpath(objectLocatorProps.getProperty("loginBtn")), "loginBtn");

        String expectedMyAccountLbl = "My Account";
        String actualMyAccountLbl = driver.findElement(
                By.xpath(objectLocatorProps.getProperty("myAccountLbl"))).getText();

        Assert.assertEquals(actualMyAccountLbl, expectedMyAccountLbl);

        test.info("loginToApp completed");
    }

    @Test(dependsOnMethods = {"loginToApp"})
    public void clearCart() {

        for (int i = 0; i < 3; i++) {
            click(By.id(objectLocatorProps.getProperty("cartTotalTxt")), "cartTotalTxt");
            List<WebElement> removes = driver.findElements(By.xpath(objectLocatorProps.getProperty("removeBtn")));
            for (WebElement e : removes) {
                click(By.id(objectLocatorProps.getProperty("cartTotalTxt")), "cartTotalTxt");
                click(e, "removeBtn");
                break;
            }
            click(By.id(objectLocatorProps.getProperty("cartTotalTxt")), "cartTotalTxt");
            if (driver.findElements(By.xpath(objectLocatorProps.getProperty("emptyCartLbl"))).size() == 1)
                break;
        }
    }

    @Test(dataProvider = "ableToAddToCartDP", dependsOnMethods = {"clearCart"})
    public void ableToAddToCart(String product, String price, String quantity) {

        test.info("ableToAddToCart started").assignAuthor("Henirich").assignCategory("Add Cart");

        String searchURL = "https://awesomeqa.com/ui/index.php?route=common/home";

        driver.get(searchURL);
        test.info("navigated to home page");


        sendKeys(By.xpath(objectLocatorProps.getProperty("searchBox")), "searchBox", product);
        click(By.xpath(objectLocatorProps.getProperty("searchBtn")), "searchBtn");

        String productName = driver.findElement(By.xpath(objectLocatorProps.getProperty("productName"))).getText();
        String productPrice = driver.findElement(By.xpath(objectLocatorProps.getProperty("productPrice"))).getText();
        productPrice = SearchTest.processAndSplit(productPrice);
        test.info(productName + " and price " + productPrice);

        Assert.assertEquals(productName, product);
        Assert.assertEquals(productPrice, price);

        click(By.xpath(objectLocatorProps.getProperty("productThumbnailBtn")), "productThumbnailBtn");
        sendKeys(By.id(objectLocatorProps.getProperty("quantityTxt")), "quantityTxt", quantity);
        click(By.id(objectLocatorProps.getProperty("addToCartBtn")), "addToCartBtn");

        test.info("ableToAddToCart completed");
    }

    @Test(dependsOnMethods = {"ableToAddToCart"})
    public void checkCartTotal() {

        test.info("checkCartTotal started").assignAuthor("Henirich").assignCategory("Add Cart");

        String cartTotal = driver.findElement(By.id(objectLocatorProps.getProperty("cartTotalTxt"))).getText();
        Assert.assertEquals(cartTotal, "6 item(s) - $779.99");

        test.info("checkCartTotal completed");
    }

    @DataProvider(name = "ableToAddToCartDP")
    public Object[][] ableToAddToCartDP() {

        String sheetName = "ableToAddToCart";
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

}
