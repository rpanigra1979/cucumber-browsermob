package stepdefinitions;


import com.saisantoshiinfotech.*;
import HTTPArchive.HTTPArchiveHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageobjects.LoginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class TrackingStepDefinitions {

    private BasePage page;
    private LoginPage loginPage;
    private String har, pageRef;
    private BrowserMobProxy proxy = new BrowserMobProxy();
    private HTTPArchiveHelper harUtil = new HTTPArchiveHelper();


    private static final String GOOGLE_ANALYTICS_URL;
    private static final String GOOGLE_ANALYTICS_SSL_URL;
    private static final String GOOGLE_ANALYTICS_ALTERNATIVE_URL;
    private static final String PROXY_URL;


    public TrackingStepDefinitions(final Driver webDriver) {
        page = new BasePage(webDriver);
        loginPage = new LoginPage(webDriver);
    }


    static {
        PROXY_URL = "http://localhost:5555/proxy/5556";
        GOOGLE_ANALYTICS_URL = "http://www.google-analytics.com/__utm.gif?";
        GOOGLE_ANALYTICS_SSL_URL = "https://ssl.google-analytics.com/__utm.gif?";
        GOOGLE_ANALYTICS_ALTERNATIVE_URL = "http://stats.g.doubleclick.net/__utm.gif?";

    }


    @Given("^I am on page (.*)$")
    public void I_navigate_to(final String url) {
        page.I_have_navigated_to(url);
    }

    @Given("^I have created a new HAR page for the proxy server$")
    public void I_have_created_a_new_HAR_page_for_the_proxy_server() throws Throwable {
        pageRef = UUID.randomUUID().toString();
        proxy.createNewPageForHar(PROXY_URL, pageRef);
    }

    @When("^I bring up Campaigns Which Conversation from the main menu$")
    public void I_bring_up_campaigns_which_conversation_page() {
        //A small delay is necessary for the event to be captured by the interceptor
        pauseFor(10000);
        page.navigateToCampaignsWhichConversation();
    }

    @When("^I login$")
    public void I_have_logged_in() {
        page.navigateToLoginFromGlobalNav();
        assertTrue("Still not logged in!", loginPage.login("saisantoshiinfotech", "password").amIloggedIn());
    }

    @Then("^the clickout event (.*) should be fired$")
    public void the_clickout_event_should_get_fired(String value) throws Throwable {
        Pattern eventPattern = PatternUtilities.getPattern("5\\(Clickout - Which [eE]xit\\*" + value);
        assertTrue("Wrong values for exit link event fired!"
                + eventPattern, PatternUtilities.ifListContainsPattern(getParameterValueListForUtme(), eventPattern));
    }

    @Then("^the custom variable (.*) with the value (.*) should be set")
    public void the_custom_variable_should_be_fired(String variable, String value) throws Throwable {

        Pattern eventPattern = PatternUtilities.getPattern("" + variable + ".*" + value);
        assertTrue("custom variable '" + variable + "' was not fired!"
                + eventPattern, PatternUtilities.ifListContainsPattern(getParameterValueListForUtme(), eventPattern));
    }

    private List<String> getParameterValueListForUtme() throws Exception {
        pauseFor(5000);

        //Wait for google analytics requests to complete before intercepting
        page.waitForPageToLoad();

        List<String> listOfUtmeEvents = new ArrayList<String>();

        har = BrowserMobProxy.getHar(PROXY_URL);

        listOfUtmeEvents.addAll(harUtil.getValueOfParameter("utme", harUtil.getRequestListForGoogleAnalyticsUrl(har, pageRef, GOOGLE_ANALYTICS_URL)));
        listOfUtmeEvents.addAll(harUtil.getValueOfParameter("utme", harUtil.getRequestListForGoogleAnalyticsUrl(har, pageRef, GOOGLE_ANALYTICS_SSL_URL)));
        listOfUtmeEvents.addAll(harUtil.getValueOfParameter("utme", harUtil.getRequestListForGoogleAnalyticsUrl(har, pageRef, GOOGLE_ANALYTICS_ALTERNATIVE_URL)));


        return listOfUtmeEvents;
    }



    private void pauseFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
