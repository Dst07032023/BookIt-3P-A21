package com.bookit.step_defenitions;

import com.bookit.utilities.Driver;
import io.cucumber.core.gherkin.Scenario;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

public class Hooks {
    @Before
    public void setUp(){
    Driver.getDriver().manage().window().maximize();
    Driver.getDriver().manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }

    @After
    public void tearDown(Scenario scenario) {
        //only takes a screenshot if the scenario fails
//        if(scenario.isFailed()){
//            //taking a screenshot
//            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
//            scenario.embed(screenshot,"image/png");
//        }
        Driver.closeDriver();
    }

}
