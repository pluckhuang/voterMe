package com.vote.uitest;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

public class LogoutPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 5;

    @FindBy(css = "button.btn.btn-lg.btn-primary.btn-block")
    @CacheLookup
    private WebElement logoutBtn;

    private final String pageLoadedText = "Are you sure you want to log out";

    private final String pageUrl = "http://localhost:8080/logout";

    public LogoutPage() {
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public LogoutPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public LogoutPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public LogoutPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    public LogoutPage execute() {
        driver.get(pageUrl);
        PageFactory.initElements(driver, this);
        return this;
    }

    public LogoutPage checkLogoutToLogin() {
        String title = driver.getTitle();
        assertTrue(title.contains("Please sign in"));
        System.out.print(driver.getCurrentUrl());
        return this;
    }

    public LogoutPage checkPageTitle() {
        boolean result = new WebDriverWait(driver, 3).until(ExpectedConditions.titleIs("Confirm Log Out?"));
        assertTrue(result);
        return this;
    }

    /**
     * Click on Log Out Button.
     *
     * @return the LogoutPage class instance.
     */
    public LogoutPage clickLogOutButton() {
        logoutBtn.click();
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the LogoutPage class instance.
     */
    public LogoutPage submit() {
        clickLogOutButton();
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the LogoutPage class instance.
     */
    public LogoutPage verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the LogoutPage class instance.
     */
    public LogoutPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
