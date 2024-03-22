package com.bookit.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class Driver {
    /*
    Creating the private constructor so this class object
    is not reachable from outside
     */
    private Driver() {}

    /*
    Making our 'driver' instance private so that it is not reachable from outside the class.
    We make it static, because we want it to run before everything else, and also we will use it in a static method.
     */

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<WebDriver>();

        /*
        Creating re-usable utility method that will return same 'driver' instance everytime we call it.
         */

    public static WebDriver getDriver() {

        if (driverPool.get() == null) {

            synchronized (Driver.class){

                /*
                We read our browser type from configuration.properties file using.
                getProperty method we're creating in ConfigurationReader clas.
                 */
            String browserType = ConfigurationReader.getProperty("browser") != null ? browserType =
                    System.getProperty("browser") : ConfigurationReader.getProperty("browser");

                /*
                Depending on the browser type our switch statement will determine to open specific type of browser/driver
                 */
            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
//                    driverPool.get().manage().window().maximize();
//                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
//                    driverPool.get().manage().window().maximize();
//                    driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                    break;
                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "ie" :
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                }
            }
        }

            /*
            Same driver instance will be returned every time we call Driver.getDriver(); method
             */
        return driverPool.get();
    }

        /*
        This method makes sure we have some form of driver session or driver id has.
        Either null or not null is must exist.
         */

    public static void closeDriver() {
        if(driverPool.get()!=null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }

}
