package com.chong.testdex;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;

public class UIAutoTest    {

    @Test
    public void testDemo() throws UiObjectNotFoundException {

        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        uiDevice.pressHome();


        UiObject Calculator = uiDevice.findObject(new UiSelector().description("计算器"));

        Calculator.clickAndWaitForNewWindow();

        UiObject seven =uiDevice.findObject(new UiSelector().text("7"));
        seven.click();

        uiDevice.pressBack();
        uiDevice.swipe(uiDevice.getDisplayWidth(),uiDevice.getDisplayHeight()/2,0,uiDevice.getDisplayHeight()/2,100);
    }

}
