package com.me.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

import static com.me.base.BaseClass.*;

public class DriverCustomListener implements WebDriverEventListener {
	
	@Override
	public void afterAlertAccept(WebDriver arg0) {
		
		
	}

	@Override
	public void afterAlertDismiss(WebDriver arg0) {

		
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		// If This Method Shows Any Error. Enter CTRL+S. Or Write Something Like System.out.println(); And Save Later You Can Remove It Also.
		System.out.println();
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeAlertAccept(WebDriver arg0) {
		
		exTest.log(Status.INFO, "Accepting Alert On Page : "+arg0.getTitle());
		Reporter.log("Accepting Alert On Page : "+arg0.getTitle());
		
	}

	@Override
	public void beforeAlertDismiss(WebDriver arg0) {
		
		exTest.log(Status.INFO, "Dismissing Alert On Page : "+arg0.getTitle());
		Reporter.log("Dismissing Alert On Page : "+arg0.getTitle());
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		
		exTest.log(Status.INFO, "Changing Value In [ "+arg0.getTagName()+" ] On Page : "+arg1.getTitle());
		Reporter.log("Changing Value In [ "+arg0.getTagName()+" ] On Page : "+arg1.getTitle());
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		
		exTest.log(Status.INFO, "Clicking On { "+arg0.getText()+" } - [ "+arg0.getTagName()+" ] On Page : "+arg1.getTitle());
		Reporter.log("Clicking On { "+arg0.getText()+" } - [ "+arg0.getTagName()+" ] On Page : "+arg1.getTitle());
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
	
		exTest.log(Status.INFO, "Finding : [ "+arg0.toString()+" ] On Page : "+arg2.getTitle());
		Reporter.log("Finding : [ "+arg0.toString()+" ] On Page : "+arg2.getTitle());
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		
		exTest.log(Status.INFO, "Navigating To : "+arg0);
		Reporter.log("Navigating To : "+arg0);	
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) {
		
		exTest.log(Status.INFO, "Applying JS Executer On Page : "+arg1.getTitle());
		Reporter.log("Applying JS Executer On Page : "+arg1.getTitle());
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {
		
		
	}

	@Override
	public <X> void afterGetScreenshotAs(OutputType<X> arg0, X arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

}
