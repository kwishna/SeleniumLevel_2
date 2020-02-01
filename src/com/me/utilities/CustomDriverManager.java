package com.me.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class CustomDriverManager {

	private static WebDriver driver;

	public static EventFiringWebDriver getInstance() {

		if (driver == null) {

			String browserToUse = "chrome"; // By Default Chrome Will Be Used

			try {

				browserToUse = new PropertyReader("config.properties").getValue("browser");

			} catch (Exception e) {

				e.printStackTrace();
			}

			String driverPath = System.getProperty("user.dir") + "/Resources/drivers/";

			if (browserToUse.equalsIgnoreCase("firefox") || browserToUse.equalsIgnoreCase("mozilla")
					|| browserToUse.equalsIgnoreCase("gecko")) {

				System.setProperty("webdriver.gecko.marionette", driverPath + "geckodriver.exe");
				driver = new FirefoxDriver();
			}

			else if (browserToUse.equalsIgnoreCase("edge")) {

				System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
			}

			else if (browserToUse.equalsIgnoreCase("ie")) {

				System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}

			else {

				System.setProperty("webdriver.chrome.driver", driverPath + "chromedriverold.exe");
				driver = new ChromeDriver();
			}

		}

		return new EventFiringWebDriver(driver);

	}
}
