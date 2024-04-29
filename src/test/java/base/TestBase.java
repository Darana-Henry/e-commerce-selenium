package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

    public static WebDriver driver;
    public static Properties configProps = new Properties();
    public static Properties objectLocatorProps = new Properties();
    public static FileInputStream fis;
    public static String userDir;

    @BeforeSuite
    public void setup() {

        if (driver == null) {
            userDir = System.getProperty("user.dir");

            try {
                fis = new FileInputStream(userDir + "/src/test/resources/properties/config.properties");
                configProps.load(fis);

                fis = new FileInputStream(userDir + "/src/test/resources/properties/object-repository.properties");
                objectLocatorProps.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (configProps.get("browser").toString()) {
                case "chrome":
                    WebDriverManager.chromedriver().clearDriverCache().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().clearDriverCache().setup();
                    driver = new FirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("Invalid Browser Choice!");
            }

            long iWait = Long.parseLong(configProps.getProperty("iWait"));
            driver.get(configProps.getProperty("site"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(iWait));

        }
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }

    @AfterTest
    public void holdFor2Seconds() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
