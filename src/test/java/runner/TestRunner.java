package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resources"},
        tags = { "@presentation" },
        format = { "html:target/cucumber-report/cores" },
        glue = {"stepdefinitions"})
public class TestRunner {
}


