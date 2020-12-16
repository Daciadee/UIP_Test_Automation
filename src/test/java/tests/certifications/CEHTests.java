package tests.certifications;

import base.TestBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CertificationsPage;
import pages.LoginPage;
import pages.admin.AdminCEHAddWorkShopPage;
import pages.admin.AdminCEHManageWorkshopsPage;
import pages.admin.AdminInboxPage;
import pages.admin.AdminPage;
import pages.ceh.CEHApprovalRequestPage;
import pages.ceh.CEHPage;
import pages.ceh.CEHUploadDocumentationPage;
import utils.Queries;
import utils.TestHelpers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static utils.GetProperties.LOGIN_TYPE;
import static utils.GetProperties.USER_EMAIL;

public class CEHTests {

    private WebDriver driver;
    private AdminInboxPage adminInbox;
    private AdminCEHManageWorkshopsPage adminManageWorkshop;
    private LoginPage login;
    private CEHPage cehPage;
    private CEHApprovalRequestPage cehApproval;
    private CEHUploadDocumentationPage cehUpload;
    private String lastName;

    @Parameters({"browser"})
    @BeforeTest
    public void beforeTest(@Optional("chrome") String browser) throws MalformedURLException {
        TestBase base = new TestBase();
        driver = base.getDriver(browser);

        adminInbox = new AdminInboxPage(driver);
        adminManageWorkshop = new AdminCEHManageWorkshopsPage(driver);
        login = new LoginPage(driver);
        cehPage = new CEHPage(driver);
        cehApproval = new CEHApprovalRequestPage(driver);
        cehUpload = new CEHUploadDocumentationPage(driver);

        login.loginToUIP(LOGIN_TYPE);

        TestHelpers th = new TestHelpers(driver);
        lastName = th.getLastName();
    }

    @Test
    public void setCycleCehDates() {
        CertificationsPage certifications = new CertificationsPage(driver);
        certifications.setCycleDates(USER_EMAIL);
    }

    @Test
    public void uploadCehDocumentationAndApproveTest(){
        // If the cycle dates are not set before running this method, it will not work
        String workshopName = "Demo Test 2";
        adminManageWorkshop.addOrUpdateWorkshopDates(workshopName, 80);

        login.logInToOpenSession();
        cehPage.openCehTabAndBeginUpload();
        cehUpload.uploadCehDocument(workshopName);
        cehUpload.approveCehUpload(lastName);

        login.logInToOpenSession();
        cehPage.cehTab.click();
    }

    @Test
    public void uploadCehDocumentationAndDenyTest(){
        cehPage.openCehTabAndBeginUpload();
        List<String> workshops = new ArrayList<>();
        workshops.add("Bill Test");
        workshops.add("Karl");

        for (String workshop : workshops) {
            cehUpload.uploadCehDocument(workshop);
            cehUpload.approveCehUpload(lastName);
            login.googleProfileBtn.click();
        }
    }

    @Test
    public void submitCEHRequest() {
        TestHelpers th = new TestHelpers(driver);
        String rand = String.valueOf(th.getRandomNumberBetween(10, 99));

        cehPage.openCEHRequestPage();
        List<String> requestData = new LinkedList<>();
        requestData.add("Demo Activity-" + rand);
        requestData.add("09/30/2026");
        requestData.add("09/30/2026");
        requestData.add("Demo Presenter");
        requestData.add("10");
        requestData.add("A");
        requestData.add("B");
        requestData.add("C");
        requestData.add("D");

        cehApproval.fillAndSubmitCEHRequestForm(requestData);

        adminInbox.approveCEHRequestApproval("Lindsey", "Professional Education");
    }

    @Test
    public void createCEHNewWorkshop() {
        AdminCEHAddWorkShopPage addWorkshop = new AdminCEHAddWorkShopPage(driver);
        TestHelpers th = new TestHelpers(driver);

        String rand = String.valueOf(th.getRandomNumberBetween(1, 99));
        List<String> formData = new ArrayList<>();

        String title = "My Test Workshop-" + rand;
        formData.add(title);

        String presenter = "Mr. Bill";
        formData.add(presenter);

        String description = "This is a temp test workshop.";
        formData.add(description);

        String ceh = "40";
        formData.add(ceh);

        String cehType = "General Studies";
        formData.add(cehType);

        String startDate = "08/28/2018";
        formData.add(startDate);

        String endDate = "08/30/2018";
        formData.add(endDate);

        addWorkshop.updateOrAddNewWorkshop(formData, true, false);
    }

    @Test
    public void approvedCEHRequest() {
        AdminCEHAddWorkShopPage adminAddWorkshop = new AdminCEHAddWorkShopPage(driver);
        Queries queries = new Queries();
        String lastName = queries.getInterpreterLastName();

        // CEH Approval
        String startDate = "09/12/2018";
        String endDate = "09/14/2018";
        String workshopName = "Demo Test 2";
        int hours = 80;

        if (adminManageWorkshop.checkIfWorkshopExists(workshopName)) {
            adminManageWorkshop.addOrUpdateWorkshopDates(workshopName, hours);
        } else {
            adminAddWorkshop.navigateToAddWorkshopPage();
            adminAddWorkshop.fillWorkshopFormAndSubmit(workshopName, startDate, endDate, hours);
        }

        login.loginToUIP(LOGIN_TYPE);
        cehPage.openCehTabAndBeginUpload();
        cehUpload.uploadCehDocument(workshopName);
        cehUpload.approveCehUpload(lastName);

        login.logInToOpenSession();
        cehPage.cehTab.click();
    }

    @Test
    public void clearAllCEHCredit() {
        Queries queries = new Queries();
        queries.clearCEHCredit(USER_EMAIL);
    }
}
