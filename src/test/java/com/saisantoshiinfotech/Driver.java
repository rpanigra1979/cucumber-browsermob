package com.saisantoshiinfotech;

import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;


/**
 * Based on shared webdriver implementation in cucumber-jvm examples
 * A new instance of SharedDriver is created for each Scenario and passed to  Stepdef classes via Dependency Injection
 */
public class Driver extends EventFiringWebDriver {

    enum BrowserDriver {
        FIREFOX,
        CHROME,
        SAFARI,
        IE
    }

    private static WebDriver REAL_DRIVER = null;
    private static DesiredCapabilities capabilities;

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.quit();
        }
    };

    static {
        try {

            setChromeDriverInClassPath();
            Proxy proxy = customProxy();
            initiateRealDriverWithCapabilities(initialiseDesiredCapabilities(proxy), BrowserDriver.FIREFOX);
            customiseRealDriver();
            Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void customiseRealDriver() {
        REAL_DRIVER.manage().window().maximize();
        REAL_DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private static Proxy customProxy() {
        Proxy proxy;
        String PROXY = "localhost:5556";
        proxy = new Proxy();
        proxy.setHttpProxy(PROXY)
                .setFtpProxy(PROXY)
                .setSslProxy(PROXY);
        return proxy;
    }

    private static DesiredCapabilities initialiseDesiredCapabilities(Proxy proxy) {
        capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);
        return capabilities;
    }

    private static void setChromeDriverInClassPath() {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    }

    private static void initiateRealDriverWithCapabilities(DesiredCapabilities cap, BrowserDriver browserDriver) {
        switch (browserDriver) {
            case FIREFOX:
                REAL_DRIVER = new FirefoxDriver(cap);
                return;
            case CHROME:
                REAL_DRIVER = new ChromeDriver(cap);
                return;
            case SAFARI:
                REAL_DRIVER = new SafariDriver(cap);
                return;
            case IE:
                REAL_DRIVER = new InternetExplorerDriver(cap);
                return;
        }
    }

    public Driver() {
        super(REAL_DRIVER);
    }

    public static ProxyServer getProxyServerInstance(final int port) {
        return new ProxyServer(port);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    public static String getBrowserName()
    {
        Capabilities cp = ((RemoteWebDriver)REAL_DRIVER).getCapabilities();
        String browserName = cp.getBrowserName().toLowerCase();
        return browserName;
    }

}