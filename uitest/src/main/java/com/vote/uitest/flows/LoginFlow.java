package com.vote.uitest;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginFlow {

    private LoginPage loginPage;

    private WebDriver driver; // current Page

    private Map<String, String> loginData;

    public LoginFlow(LoginParams loginParams) {

        setParams(loginParams);

        setDriver();

        genPage();
    }

    private void setParams(LoginParams loginParams) {
        loginData = new HashMap<>();
        loginData.put("username", loginParams.getUsername());
        loginData.put("password", loginParams.getPassword());
    }

    private void setDriver() {
        driver = new ChromeDriver();
    }

    private void genPage() {
        loginPage = new LoginPage(driver, loginData);
    }

    public void execute() {
        loginPage.execute().fillAndSubmit();
    }

    public WebDriver getEndPage() {
        return driver;
    }

}