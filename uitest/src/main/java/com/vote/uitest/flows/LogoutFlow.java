package com.vote.uitest;

import org.openqa.selenium.WebDriver;

public class LogoutFlow {

    private LogoutPage logoutPage;

    private WebDriver driver; // current Page

    public LogoutFlow() {
    }

    public void execute() {
        logoutPage = new LogoutPage(driver);
        logoutPage.execute();
        logoutPage.clickLogOutButton();
        driver.quit();
    }

    public LogoutFlow withStartPage(WebDriver driver) {
        this.driver = driver;
        return this;
    }

}