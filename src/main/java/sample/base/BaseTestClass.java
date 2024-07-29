package sample.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTestClass {

    protected static ExtentReports extent;
    protected static Logger logger = Logger.getLogger(BaseTestClass.class);
    public static ExtentTest test;

    @BeforeSuite
    public void setUpSuite() {
        extent = new ExtentReports();
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Test Results");
        sparkReporter.config().setReportName("RestAssured API Test Report");
        sparkReporter.config().setTheme(Theme.STANDARD);       
        
        extent.attachReporter(sparkReporter);
        
        // Set system information
        extent.setSystemInfo("HostName", "MyHost");
        extent.setSystemInfo("ProjectName", "ECom API Sample");
        extent.setSystemInfo("Tester", "Jimmy Gupta");
        extent.setSystemInfo("OS", "Win10");

        logger.info("Extent Report initialized successfully.");
    }

    @AfterSuite
    public void tearDownSuite() {
        extent.flush();
        logger.info("Extent Report flushed successfully.");
    }
    
    @BeforeMethod
    public void setUp(ITestResult result) {
    	ApiLoggingUtil.clearLogs();
        test = extent.createTest(getClass().getSimpleName() + "." + result.getMethod().getMethodName());
        ApiLoggingUtil.configureLogging();
        logger.info("Test Case Started: " + result.getMethod().getMethodName());
        test.log(Status.INFO, "Test Case Started: " + result.getMethod().getMethodName());
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
    	
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed: " + result.getMethod().getMethodName());
            test.log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());
            logger.info("Test Case Failed: " + result.getMethod().getMethodName());
            logger.info("Failure Reason: " + result.getThrowable().getMessage());
            logger.info("Request Log:\n" + ApiLoggingUtil.getRequestLog());
            logger.info("Response Log:\n" + ApiLoggingUtil.getResponseLog());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed: " + result.getMethod().getMethodName());
            logger.info("Test Case Passed: " + result.getMethod().getMethodName());
            test.log(Status.INFO, "API test completed.");
        }

        // Clear logs after test execution
        ApiLoggingUtil.clearLogs();
        extent.flush();
    }
}
