package com.saisantoshiinfotech;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SeleniumUtilities {

    private WebDriver webDriver;
    private static final Integer WAIT_TIMEOUT = 30;
    private Logger logger = Logger.getLogger(getClass());
    private Actions action;

    public SeleniumUtilities(final Driver webDriver) {
        this.webDriver = webDriver;
        action = new Actions(webDriver);
    }

    public WebDriver getDriver(){
        return webDriver;
    }

    public void navigateTo(String url) {
        webDriver.navigate().to(url);
    }

    public WebElement elementIfVisible(WebElement element) {
        return element.isDisplayed() ? element : null;
    }

    public WebElement getWebElement(final By selector) {
        WebElement webElement =(new WebDriverWait(getDriver(), WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(selector));
        if (Driver.getBrowserName().equals("chrome")) {
            try {
                ((JavascriptExecutor) webDriver).executeScript(
                        "arguments[0].scrollIntoView(true);", webElement);
            } catch (Exception e) {

            }
        }

        return webElement;

    }

    public WebElement getWebElement(final WebElement parentSelector, final By childSelector) {
        return (new WebDriverWait(getDriver(), WAIT_TIMEOUT))
                .until(new ExpectedCondition<WebElement>(){
                    public WebElement apply(WebDriver d) {
                        try {
                            return elementIfVisible(findElement(parentSelector, childSelector));
                        } catch (StaleElementReferenceException e) {
                            System.out.println("Stale element referenced!");
                            return null;
                        }
                    }});
    }

    public boolean isWebElementPresent(final String element) {
        return (getDriver().findElements(By.cssSelector(element)).size() > 0);
    }

    private static WebElement findElement(WebElement parentSelector, By childSelector) {
        try {
            return parentSelector.findElement(childSelector);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (WebDriverException e) {
            System.out.println("WebDriverException thrown by findElement(%s)");
            throw e;
        }
    }

    public void click(String cssLocator) {
        getWebElement(By.cssSelector(cssLocator)).click();
    }

    public void clearField(String cssLocator) {
        getWebElement(By.cssSelector(cssLocator)).clear();
    }

    public String getText(String parentCssLocator, String childCssLocator) {
        return getWebElement(getWebElement(By.cssSelector(parentCssLocator)), By.cssSelector(childCssLocator)).getText();
    }

    public void enterIntoInputField(String cssLocator, String charSequence){
        clearField(cssLocator);
        getWebElement(By.cssSelector(cssLocator)).sendKeys(charSequence);
    }

    public void waitForPageToLoad() {
        new WebDriverWait(getDriver(), WAIT_TIMEOUT).until(new
                                                                   ExpectedCondition<Boolean>() {
                                                                       public Boolean apply(WebDriver driver) {
                                                                           return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                                                                       }
                                                                   });
    }

}

