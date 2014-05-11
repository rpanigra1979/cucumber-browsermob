package pageobjects;

import com.saisantoshiinfotech.BasePage;
import com.saisantoshiinfotech.Driver;


public class LoginPage extends BasePage {

    private Driver webDriver;

    public LoginPage(Driver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
        waitForPageToLoad();
    }

    public LoginPage enterUserName(String userName) {
        enterIntoInputField("#username", userName);
        return this;
    }

    public LoginPage enterPassword(String password) {
        enterIntoInputField("#password", password);
        return this;
    }

    public WelcomePage submitLogin() {
        clickButton("input[alt='Log in']");
        return new WelcomePage(webDriver);
    }

    public WelcomePage login(String userName, String password) {
        return enterUserName(userName).enterPassword(password).submitLogin();
    }

}


