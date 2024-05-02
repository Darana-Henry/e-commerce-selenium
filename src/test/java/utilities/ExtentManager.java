package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;
    private static String reportPath;
    private static String configPath;

    public static ExtentReports getInstance() {
        reportPath = System.getProperty("user.dir");
        configPath = System.getProperty("user.dir");

        reportPath += "/src/test/resources/reports/extent.html";
        configPath += "/src/test/resources/configs/extent.xml";


        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().thumbnailForBase64(true);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Data Driven");
            spark.config().setReportName("Execution Report");
            extent.attachReporter(spark);
        }
        return extent;
    }
}
