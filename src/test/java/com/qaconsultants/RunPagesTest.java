package com.qaconsultants;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//@formatter:off
@CucumberOptions(  plugin = {"pretty"
        , "html:target/features-report/qacToyotaAuto"
        , "json:target/cucumber_report/cucumber.json"
}
        , features = {"src/test/resources/features"}
        , glue = {"com/qaconsultants"}
        , tags = "@tag1"
)
@RunWith(Cucumber.class)
public class RunPagesTest   {}
