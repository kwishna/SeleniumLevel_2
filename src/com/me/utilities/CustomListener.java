package com.me.utilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;
import com.me.base.BaseClass;

public class CustomListener extends BaseClass implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		exTest = extentReport.createTest(result.getMethod().getDescription());

		
		exTest.log(Status.INFO, "Starting Excecution Of : "+result.getMethod().getMethodName().toUpperCase());
		Reporter.log("Starting Excecution Of : "+result.getMethod().getMethodName().toUpperCase());
	} 

	@Override
	public void onTestSuccess(ITestResult result) {
		
		exTest.log(Status.PASS, "This Test Case Passed : "+result.getMethod().getDescription().toUpperCase());
		Reporter.log("This Test Case Passed : "+result.getMethod().getDescription().toUpperCase());
		extentReport.flush();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {

		exTest.log(Status.FAIL, result.getThrowable());
		Reporter.log("This Test Case Failed : "+result.getMethod().getDescription().toUpperCase()+" With Error : "+result.getThrowable().getMessage());
		
		try {
			
			MyUtilities.captureAndSaveScreenshot();
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		extentReport.flush();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		
		Reporter.log("This Test Case Is Skipped : "+result.getMethod().getDescription().toUpperCase());
		exTest.log(Status.SKIP, "This Test Case Is Skipped : "+result.getMethod().getDescription().toUpperCase());
		extentReport.flush();
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

}
