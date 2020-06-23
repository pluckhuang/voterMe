package com.vote.uitest;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;

import static org.junit.Assert.*;

public class VoteIndexPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 5;

    @FindBy(id = "1_2")
    @CacheLookup
    private WebElement basketball;

    @FindBy(id = "2_5")
    @CacheLookup
    private WebElement no;

    private final String pageLoadedText = "whta is your favorite sports";

    private final String pageUrl = "http://localhost:8080/index.html";

    @FindBy(id = "1_1")
    @CacheLookup
    private WebElement pingpong;

    @FindBy(id = "1_3")
    @CacheLookup
    private WebElement soccer;

    @FindBy(css = "button.btn.btn-primary")
    @CacheLookup
    private WebElement submit;

    @FindBy(id = "2_4")
    @CacheLookup
    private WebElement yes;

    public VoteIndexPage() {
    }

    public VoteIndexPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public VoteIndexPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public VoteIndexPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    public VoteIndexPage execute() {
        PageFactory.initElements(driver, this);
        return this;
    }

    public VoteIndexPage checkBadge() {
        WebElement countDownBadge = new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("countDown")));

        assertTrue(countDownBadge.getText().contains("remaining time:"));
        return this;
    }

    public VoteIndexPage checkResult() {
        String title = driver.getTitle();
        assertTrue(title.contains("vote display"));
        return this;
    }

    /**
     * Click on Submit Button.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage clickSubmitButton() {
        submit.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage fill() {
        setPingpongCheckboxField();
        setBasketballCheckboxField();
        setSoccerCheckboxField();
        setYesCheckboxField();
        // setNoCheckboxField();
        return this;
    }

    /**
     * Fill every fields in the page and submit it to target page.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage fillAndSubmit() {
        fill();
        return submit();
    }

    /**
     * Set Basketball Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage setBasketballCheckboxField() {
        if (!basketball.isSelected()) {
            basketball.click();
        }
        return this;
    }

    /**
     * Set No Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage setNoCheckboxField() {
        if (!no.isSelected()) {
            no.click();
        }
        return this;
    }

    /**
     * Set Pingpong Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage setPingpongCheckboxField() {
        if (!pingpong.isSelected()) {
            pingpong.click();
        }
        return this;
    }

    /**
     * Set Soccer Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage setSoccerCheckboxField() {
        if (!soccer.isSelected()) {
            soccer.click();
        }
        return this;
    }

    /**
     * Set Yes Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage setYesCheckboxField() {
        if (!yes.isSelected()) {
            yes.click();
        }
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage submit() {
        clickSubmitButton();
        return this;
    }

    /**
     * Unset Basketball Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage unsetBasketballCheckboxField() {
        if (basketball.isSelected()) {
            basketball.click();
        }
        return this;
    }

    /**
     * Unset No Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage unsetNoCheckboxField() {
        if (no.isSelected()) {
            no.click();
        }
        return this;
    }

    /**
     * Unset Pingpong Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage unsetPingpongCheckboxField() {
        if (pingpong.isSelected()) {
            pingpong.click();
        }
        return this;
    }

    /**
     * Unset Soccer Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage unsetSoccerCheckboxField() {
        if (soccer.isSelected()) {
            soccer.click();
        }
        return this;
    }

    /**
     * Unset Yes Checkbox field.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage unsetYesCheckboxField() {
        if (yes.isSelected()) {
            yes.click();
        }
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage verifyPageLoaded() {
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
     * @return the VoteIndexPage class instance.
     */
    public VoteIndexPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
