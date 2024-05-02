package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utilities.TestUtils;

public class CustomListeners implements ITestListener {


    public void onTestStart(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has started");
    }

    public void onTestSuccess(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has passed");
    }

    public void onTestFailure(ITestResult result) {
        String name = getName(result);
        Reporter.log(name + " has failed");
        TestUtils.takeSnapShot(name);
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

    public void onStart(ITestContext context) {
        Reporter.log("Run Forrest Run!");
    }

    public void onFinish(ITestContext context) {
        Reporter.log("Lets End This Now!");
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
            return result.getMethod().getMethodName() + " - " + result.getParameters()[0];
        else
            return result.getMethod().getMethodName();
    }
}
