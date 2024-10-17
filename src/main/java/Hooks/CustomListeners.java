package Hooks;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomListeners  implements ITestListener {

    //This belongs to ITestListener and will execute before starting of Test set/batch
    public void onStart(ITestContext arg) {

        System.out.println("Starts Test execution" + arg.getName());
    }

    //This belongs to ITestListener and will execute after starting of Test set/batch
    public void onFinish(ITestContext arg) {

        System.out.println("Ends Test execution" + arg.getName());
    }

    //This belongs to ITestListener and will execute before the main test start i.e. @Test
    public void onTestStart(ITestResult arg0) {

        System.out.println("Starts Test execution" + arg0.getName());
    }

    //This belongs to ITestListener and will execute when a test is skipped
    public void onTestSkipped(ITestResult arg0) {

        System.out.println("Skipped Test execution" + arg0.getName());
    }

    //This belongs to ITestListener and will execute when a test is passed
    public void onTestSuccess(ITestResult arg0) {

        System.out.println("Success Test execution" + arg0.getName());
    }

    //This belongs to ITestListener and will execute when a test is failed
    public void onTestFailure(ITestResult arg0) {

        System.out.println("Failure Test execution" + arg0.getName());
    }
}
