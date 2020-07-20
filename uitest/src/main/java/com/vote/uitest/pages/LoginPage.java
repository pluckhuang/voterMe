package com.vote.uitest;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

public class LoginPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 5;

    private final String pageLoadedText = "";

    private final String pageUrl = "http://localhost:8080/login";

    @FindBy(id = "password")
    @CacheLookup
    private WebElement password;

    @FindBy(css = "button.btn.btn-lg.btn-primary.btn-block")
    @CacheLookup
    private WebElement signIn;

    @FindBy(id = "username")
    @CacheLookup
    private WebElement username;

    public LoginPage() {
    }

    public LoginPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public LoginPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public LoginPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    public LoginPage execute() {
        driver.get(pageUrl);
        PageFactory.initElements(driver, this);
        return this;
    }

    /**
     * Click on Sign In Button.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage clickSignInButton() {
        signIn.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage fill() {
        setUsernameTextField();
        setPasswordPasswordField();
        return this;
    }

    /**
     * Fill every fields in the page and submit it to target page.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage fillAndSubmit() {
        fill();
        return submit();
    }

    /**
     * Set default value to Password Password field.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage setPasswordPasswordField() {
        return setPasswordPasswordField(data.get("password"));
    }

    /**
     * Set value to Password Password field.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage setPasswordPasswordField(String passwordValue) {
        password.sendKeys(passwordValue);
        return this;
    }

    /**
     * Set default value to Username Text field.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage setUsernameTextField() {
        return setUsernameTextField(data.get("username"));
    }

    /**
     * Set value to Username Text field.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage setUsernameTextField(String usernameValue) {
        username.sendKeys(usernameValue);
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage submit() {
        clickSignInButton();
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the LoginPage class instance.
     */
    public LoginPage verifyPageLoaded() {
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
     * @return the LoginPage class instance.
     */
    public LoginPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
