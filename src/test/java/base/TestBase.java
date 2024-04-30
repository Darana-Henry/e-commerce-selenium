package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
    //public static Logger log = Logger.getLogger("devpinoyLogger");
    public static Logger log = LogManager.getLogger(TestBase.class.getName());

    @BeforeSuite
    public void setup() {

        if (driver == null) {
            userDir = System.getProperty("user.dir");

            try {
                fis = new FileInputStream(userDir + "/src/test/resources/properties/config.properties");
                configProps.load(fis);
                log.debug("Configuration Properties file loaded.");

                fis = new FileInputStream(userDir + "/src/test/resources/properties/object-repository.properties");
                objectLocatorProps.load(fis);
                log.debug("Object Locator Properties file loaded.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (configProps.get("browser").toString()) {
                case "chrome":
                    WebDriverManager.chromedriver().clearDriverCache().setup();
                    ChromeOptions cOptions = new ChromeOptions();
                    cOptions.addArguments("--headless");
                    driver = new ChromeDriver(cOptions);
                    log.debug("Chrome browser launched.");
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().clearDriverCache().setup();
                    FirefoxOptions fOptions = new FirefoxOptions();
                    fOptions.addArguments("--headless");
                    driver = new FirefoxDriver(fOptions);
                    log.debug("Firefox browser launched.");
                    break;
                default:
                    throw new RuntimeException("Invalid Browser Choice!");
            }

            long iWait = Long.parseLong(configProps.getProperty("iWait"));
            driver.get(configProps.getProperty("site"));
            log.debug("Navigated to " + configProps.getProperty("site"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(iWait));

        }
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
        log.debug("Driver stopped and quit");
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
