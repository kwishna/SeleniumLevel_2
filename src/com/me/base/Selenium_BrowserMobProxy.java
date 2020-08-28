package Rough;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

/**
 * https://www.seleniumeasy.com/selenium-tutorials/browsermob-proxy-selenium-example
 *
 */
public class Selenium_BrowserMobProxy {

    private static BrowserMobProxy proxy;

    private static BrowserMobProxy startBrowserMobProxy(int port){

        proxy = new BrowserMobProxyServer();
        proxy.start(port);
        return proxy;
    }

    private static void stopBrowserMobProxy(BrowserMobProxy prxy){

        prxy.stop();
    }

    private static Proxy getSeleniumProxy(BrowserMobProxy prxy){

        return ClientUtil.createSeleniumProxy(prxy);
    }

    public static void main(String[] args) throws IOException {

        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");


//        op.addArguments(CapabilityType.PROXY, getSeleniumProxy(startProwserMobProxy(8000)))

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability(CapabilityType.PROXY, getSeleniumProxy(startBrowserMobProxy(8000)));

        ChromeOptions op = new ChromeOptions();
        op.merge(caps);

        WebDriver driver = new ChromeDriver(op);

        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar("krish");


        driver.get("https://www.google.co.in");
        driver.findElement(By.name("q")).sendKeys("Hi Hello");
        driver.findElement(By.name("btnK")).submit();

        Har har = proxy.getHar();
        har.writeTo(new File("file.har"));

       stopBrowserMobProxy(proxy);
       driver.close();

    }
}
