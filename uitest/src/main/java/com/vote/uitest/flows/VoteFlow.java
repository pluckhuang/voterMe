package com.vote.uitest;

import org.openqa.selenium.WebDriver;

public class VoteFlow {

    private VoteIndexPage voteIndexPage;

    private WebDriver driver; // current Page

    public VoteFlow() {
    }

    public void execute() {
        voteIndexPage.fillAndSubmit();
    }

    public VoteFlow withStartPage(WebDriver driver) {
        this.driver = driver;
        voteIndexPage = new VoteIndexPage(this.driver);
        voteIndexPage.checkBadge().execute();
        return this;
    }

    public WebDriver getEndPage() {
        return driver;
    }
}
