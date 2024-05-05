package utilities;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

public class TestUtils extends TestBase {

    public static void takeSnapShot(String fileName) {

        String fileWithPath = userDir + "/src/test/resources/screenshots/" + fileName + ".png";

        try {
            TakesScreenshot scrShot = ((TakesScreenshot) driver);
            File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(fileWithPath);
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            Reporter.log("Unable to capture screenshot!");
        }
    }

    public static String getScreenshot(WebDriver driver) {
        String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        return screenshotBase64;
    }

    @DataProvider(name = "dp")
    public Object[][] getData(Method method) {

        String sheetName = method.getName();
        int rows = reader.getRowCount(sheetName);
        int columns = reader.getColumnCount(sheetName);
        Object[][] data = new Object[rows - 1][1];

        for (int iRow = 2; iRow <= rows; iRow++) {
            Hashtable<String, String> hashed = new Hashtable<String, String>();
            for (int iCol = 0; iCol < columns; iCol++) {
                hashed.put(reader.getCellData(sheetName, iCol, 1).toString(),
                        reader.getCellData(sheetName, iCol, iRow).toString());
                data[iRow - 2][0] = hashed;
            }
        }
        return data;
    }

}
