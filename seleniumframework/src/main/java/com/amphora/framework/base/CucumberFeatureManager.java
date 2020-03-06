package com.amphora.framework.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;


/**
 * Base for cucumber runner
 * Picks up all options which are mentioned in cucumber runner file and run scenarios based on tag notations given in runner file
 * Customize cucumber report after completion of execution
 *
 */
public class CucumberFeatureManager extends DriverManager{
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleEventWrapper pickleWrapper, CucumberFeatureWrapper featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickleEvent());
	}

	@DataProvider
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass
	public void tearDownClass() {
		testNGCucumberRunner.finish();
        File reportOutputDirectory = new File("target/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-reports/cucumber-html-report.json");

        String buildNumber = propFile.getProperty("BuildNumber");
        String projectName = "Alchemy Project";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        
        configuration.addClassifications("Project URL", propFile.getProperty("URL"));
        configuration.addClassifications("Execution", propFile.getProperty("Execution"));
        configuration.addClassifications("OS", propFile.getProperty("OS"));
        configuration.addClassifications("Browser", propFile.getProperty("BrowserName"));
        configuration.addClassifications("Branch", propFile.getProperty("Branch"));
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        // points to the demo trends which is not used for other tests
        configuration.setTrendsStatsFile(new File("target/test-classes/featurebuildtrends.json"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
	}
}
