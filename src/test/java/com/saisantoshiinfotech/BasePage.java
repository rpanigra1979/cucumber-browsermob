package com.saisantoshiinfotech;


import org.joda.time.DateTime;

import static org.junit.Assert.assertTrue;


public class BasePage {
    private SeleniumUtilities selenium;

    private static final String HOST = "which.co.uk";
    private static final String HOMEPAGE_URL = "www." + HOST;
    private static String HTTP_HOMEPAGE_URL = "http://" + HOMEPAGE_URL;


    public BasePage(final Driver webDriver) {
        selenium = new SeleniumUtilities(webDriver);
    }

    public void enterIntoInputField(String cssLocator, String charSequence) {
        selenium.enterIntoInputField(cssLocator, charSequence);
    }

    public void clickButton(String cssLocator) {
        selenium.click(cssLocator);
    }

    public void navigateToLoginFromGlobalNav(){
        selenium.click("div[class='button show-signin'] span:nth-of-type(2)");
    }

    public void navigateToCampaignsWhichConversation(){
        selenium.click("#campaigns-button");
        selenium.click("a[href*='conversation.which.co.uk']");
    }

    public Boolean isElementPresent(String cssSelector){
        return selenium.isWebElementPresent(cssSelector);
    }

    public void waitForPageToLoad(){
        selenium.waitForPageToLoad();
    }

    public String getText(String parentCssLocator, String childCssLocator) {
        return selenium.getText(parentCssLocator, childCssLocator);
    }

    public void I_have_navigated_to(String url) {
        if (url.startsWith(HOST) || url.startsWith("/")) {
            String year = "{current_year}";
            if (url.contains(year)) {
                url = HTTP_HOMEPAGE_URL + url.replace(year, Integer.toString(DateTime.now().getYear()));
            } else {
                url = HTTP_HOMEPAGE_URL + url;
            }
        }
        selenium.navigateTo(url);
        selenium.waitForPageToLoad();
    }

}

