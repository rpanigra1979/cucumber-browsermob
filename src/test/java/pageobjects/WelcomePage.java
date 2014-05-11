package pageobjects;

import com.saisantoshiinfotech.BasePage;
import com.saisantoshiinfotech.Driver;
import com.saisantoshiinfotech.SeleniumUtilities;


public class WelcomePage extends BasePage {

    private Driver webDriver;

    private static final String MY_ACCOUNT_CONTAINER_CSS = "div[class='button show-account']";
    private static final String MY_ACCOUNT_CSS = "span[class='account-btn-label']";
    private static final String LOGOUT_CSS = "a[href*='logout']";
    private static final String MEMBERS_AREA_CONTAINER_CSS = "div[id='homepage-members-area']";
    private static final String WELCOME_MESSAGE_CSS = "section[class='which-product-summary'] h2";
    private SeleniumUtilities selenium;

    public WelcomePage(Driver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        selenium = new SeleniumUtilities(webDriver);
        waitForPageToLoad();
    }

    public Boolean amIloggedIn() {
        return isElementPresent(MY_ACCOUNT_CONTAINER_CSS);
    }

    public String getWelcomeMessage() {
        return getText(MEMBERS_AREA_CONTAINER_CSS, WELCOME_MESSAGE_CSS);
    }

}

