package listeners;

import base.TestBase;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utilities.TestUtils;

public class CustomListeners extends TestBase implements ITestListener {

    public void onTestStart(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has started");
        test = extent.createTest(name);
        test.info(name + " has started");
    }

    public void onTestSuccess(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has passed");
        TestBase.test.pass(name + " has passed");
    }

    public void onTestFailure(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has failed");
        TestUtils.takeSnapShot(name);
        String screenshot = userDir + "/src/test/resources/screenshots/" + name + ".png";
        test.fail(name + " has failed");
        test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(
                "data:image/png;base64," + TestUtils.getScreenshot(driver)).build());
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

    public void onStart(ITestContext context) {
        Reporter.log("Run Forrest Run!");
        //test.info("Run Forrest Run!");
    }

    public void onFinish(ITestContext context) {
        Reporter.log("Lets End This Now!");
        //test.log(Status.INFO, "Lets End This Now!");
        extent.flush();
    }

    public String getName(ITestResult result) {
        boolean hasParameter;

        try {
            String p = result.getParameters()[0].toString();
            hasParameter = true;
        } catch (Exception e) {
            hasParameter = false;
        }

        if (hasParameter)
            return result.getMethod().getMethodName() + "-" + result.getParameters()[0].toString().trim();
        else
            return result.getMethod().getMethodName().toString().trim();
    }
}
