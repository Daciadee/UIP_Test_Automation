package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    private static final String TEST_PROPERTIES_FILE = "src/main/resources/properties/test.properties";
    private static final String PRIVATE_PROPERTIES_FILE="src/main/resources/properties/private.properties";

    public static final String ADMIN_EMAIL = getPrivateProperty("ADMIN_EMAIL");
    public static final String ADMIN_PASSWORD = getPrivateProperty("ADMIN_PASSWORD");
    public static String USER_EMAIL = getPrivateProperty("USER_EMAIL");
    public static final String PASSWORD = getPrivateProperty("PASSWORD");
    public static final String USER_PATH = getPrivateProperty("USER_PATH");

    public static final String ENVIRONMENT = getProperty("ENVIRONMENT");
    public static final String BASE_URL = getEnvironment() + getProperty("BASE_URL");
    public static final String ADMIN_URL = getEnvironment() + getProperty("ADMIN_URL");
    public static final String PROD_URL = getProperty("PROD_URL");
    public static final String DASHBOARD_URL = getEnvironment() + getProperty("DASHBOARD_URL");
    public static final String KNOWLEDGE_EXAM_URL = getEnvironment() + getProperty("KNOWLEDGE_EXAM_URL");

    public static final String CHROME_BINARY = getProperty("CHROME_BINARY");
    public static final String CHROME_DRIVER_PATH = getProperty("CHROME_DRIVER_PATH");
    public static final String FIREFOX_DRIVER_PATH = getProperty("FIREFOX_DRIVER_PATH");
    public static final String MS_DRIVER_PATH = getProperty("MS_DRIVER_PATH");
    public static final String IE_DRIVER_PATH = getProperty("IE_DRIVER_PATH");
    public static final String OPERA_BINARY = getProperty("OPERA_BINARY");
    public static final String OPERA_DRIVER_PATH = getProperty("OPERA_DRIVER_PATH");

    public static final String DB_DRIVER = getProperty("DB_DRIVER");
    public static final String DB_USER = getProperty("DB_USER");
    public static final String DB_PASSWORD = getProperty("DB_PASSWORD");
    public static final String DB_URL_ALPHA = getProperty("DB_URL_ALPHA");
    public static final String DB_URL_BETA = getProperty("DB_URL_BETA");
    public static final String DB_URL_PROD = getProperty("DB_URL_PROD");

    public static final String UNREGISTER_SQL = getProperty("UNREGISTER_SQL");
    public static final boolean REGISTER_NEW_USER = getBooleanProperty("REGISTER_NEW_USER");

    public static final String VOUCHER_PATH = USER_PATH + getProperty("VOUCHER_PATH");
    public static final String OUTPUT_FILE = getProperty("OUTPUT_FILE");
    public static final String REPORT_FILE = getProperty("REPORT_FILE");

    public static final String CERT_TYPE = getProperty("CERT_TYPE");
    public static String LOGIN_TYPE = getProperty("LOGIN_TYPE");
    public static final String THIRD_PARTY = getProperty("THIRD_PARTY");
    public static final String KNOWLEDGE_PAYMENT_TYPE = getProperty("KNOWLEDGE_PAYMENT_TYPE");
    public static final String PERFORMANCE_PAYMENT_TYPE = getProperty("PERFORMANCE_PAYMENT_TYPE");
    public static final boolean PAYMENT_APPROVAL = getBooleanProperty("PAYMENT_APPROVAL");

    public static final int KNOWLEDGE_EXAM_SCORE = getIntegerProperty("KNOWLEDGE_EXAM_SCORE");
    public static final int PERFORMANCE_EXAM_SCORE = getIntegerProperty("PERFORMANCE_EXAM_SCORE");
    public static final String PERFORMANCE_EXAM_RESULT = getProperty("PERFORMANCE_EXAM_RESULT");
    public static final boolean RETAKE_FAILED_EXAM = getBooleanProperty("RETAKE_FAILED_EXAM");

    public static final String UIP_VIDEO_1 = getProperty("UIP_VIDEO_1");
    public static final String UIP_VIDEO_2 = getProperty("UIP_VIDEO_2");
    public static final String UIP_VIDEO_3 = getProperty("UIP_VIDEO_3");
    public static final String UIP_VIDEO_4 = getProperty("UIP_VIDEO_4");
    public static final String UIP_VIDEO_5 = getProperty("UIP_VIDEO_5");

    public static final String DEAF_RATER_1 = getProperty("DEAF_RATER_1");
    public static final String DEAF_RATER_2 = getProperty("DEAF_RATER_2");
    public static final String DEAF_RATER_3 = getProperty("DEAF_RATER_3");
    public static final String ENGLISH_RATER_1 = getProperty("ENGLISH_RATER_1");
    public static final String ENGLISH_RATER_2 = getProperty("ENGLISH_RATER_2");
    public static final String ENGLISH_RATER_3 = getProperty("ENGLISH_RATER_3");
    public static final String INTERPRETER_RATER_1 = getProperty("INTERPRETER_RATER_1");
    public static final String INTERPRETER_RATER_2 = getProperty("INTERPRETER_RATER_2");
    public static final String INTERPRETER_RATER_3 = getProperty("INTERPRETER_RATER_3");

    public static final String DEAF_RATER_1_COOKIE = getProperty("DEAF_RATER_1_COOKIE");
    public static final String DEAF_RATER_2_COOKIE = getProperty("DEAF_RATER_2_COOKIE");
    public static final String DEAF_RATER_3_COOKIE = getProperty("DEAF_RATER_3_COOKIE");
    public static final String ENGLISH_RATER_1_COOKIE = getProperty("ENGLISH_RATER_1_COOKIE");
    public static final String ENGLISH_RATER_2_COOKIE = getProperty("ENGLISH_RATER_2_COOKIE");
    public static final String ENGLISH_RATER_3_COOKIE = getProperty("ENGLISH_RATER_3_COOKIE");
    public static final String INTERPRETER_RATER_1_COOKIE = getProperty("INTERPRETER_RATER_1_COOKIE");
    public static final String INTERPRETER_RATER_2_COOKIE = getProperty("INTERPRETER_RATER_2_COOKIE");
    public static final String INTERPRETER_RATER_3_COOKIE = getProperty("INTERPRETER_RATER_3_COOKIE");

    public static final String DEVICE_NAME = getProperty("DEVICE_NAME");
    public static final int DEVICE_WIDTH = getIntegerProperty("DEVICE_WIDTH");
    public static final int DEVICE_HEIGHT = getIntegerProperty("DEVICE_HEIGHT");
    public static final float DEVICE_PIXEL_RATIO = getFloatProperty("DEVICE_PIXEL_RATIO");

    private static String getEnvironment() {
        String env = getProperty("ENVIRONMENT");
        if (env.equalsIgnoreCase("prod")){
            return "https://";
        } else {
            return "https://" + env + ".";
        }
    }

    private static String getProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(property);
    }

    private static String getPrivateProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(PRIVATE_PROPERTIES_FILE)) {
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props.getProperty(property);
    }

    private static boolean getBooleanProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.parseBoolean(props.getProperty(property));
    }

    private static int getIntegerProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.valueOf(props.getProperty(property));
    }

    private static float getFloatProperty(String property) {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream(TEST_PROPERTIES_FILE)){
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Float.valueOf(props.getProperty(property));
    }
}
