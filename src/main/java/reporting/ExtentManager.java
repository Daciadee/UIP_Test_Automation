package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import static utils.GetProperties.REPORT_FILE;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getReporter() {
        if (extent == null)
            extent = createReport(REPORT_FILE);
        return extent;
    }

    private static ExtentReports createReport(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setReportName(fileName);
        htmlReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        return extent;
    }
}
