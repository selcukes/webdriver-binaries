package io.github.selcukes.tests;

import io.github.selcukes.dp.DriverPool;

public class WebDriverBinaryTest {
    public static void main(String[] args) {

        DriverPool.chromeDriver().targetPath("temp").setup();
        DriverPool.ieDriver().targetPath("temp").setup();
        DriverPool.firefoxDriver().targetPath("temp").setup();

    }
}
