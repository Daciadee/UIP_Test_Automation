package utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static utils.GetProperties.REGISTER_NEW_USER;
import static utils.GetProperties.UNREGISTER_SQL;
import static utils.GetProperties.USER_EMAIL;

public class Queries {

    private DatabaseUtils dbu = new DatabaseUtils();

    public String getInterpreterId(String userEmail) {
        String id = null;
        try {
            String query = "SELECT id FROM interpreter WHERE email = '"+ userEmail +"'";
            id = dbu.getQueryResult(query, "ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public String getInterpreterLastName() {
        String lastName = "";
        try {
            lastName = dbu.getQueryResult("SELECT last_name FROM interpreter WHERE email = '"+ USER_EMAIL +"'", "LAST_NAME");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastName;
    }

    public void resetHonorAgreement(String userEmail) {
        try {
            String id = getInterpreterId(userEmail);
            String query = "DELETE FROM agreement WHERE interpreter_id = " + id;
            dbu.executeQuery(query);

            query = "UPDATE certification_progress SET honor_flag = 'C' WHERE interpreter_id = " +id;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetExamPayment(String userEmail) {
        try {
            String id = getInterpreterId(userEmail);
            String query =  "UPDATE payment SET status_flag = 'N', " +
                            "completed_date = null,  " +
                            "payment_method_code = null," +
                            "third_party = null " +
                            "WHERE interpreter_id = " + id;

            dbu.executeQuery(query);

            query = "UPDATE certification_progress SET ke_pay_flag = 'C' WHERE interpreter_id = " +id;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetKnowledgeExamSchedule(String userEmail) {
        try {
            String id = getInterpreterId(userEmail);
            String query = "UPDATE certification_progress SET ke_schedule_flag = 'C' WHERE interpreter_id = " + id;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetKnowledgeExam(String userEmail) {
        try {
            String id = getInterpreterId(userEmail);
            String query = "DELETE FROM knowledge_exam WHERE interpreter_id = " + id;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetCertificationStep(String table, String userEmail) {
        try {
            String id = getInterpreterId(userEmail);
            String query = "DELETE FROM "+ table +" WHERE interpreter_id = " + id;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetPerformanceExamSchedule() {
        try {
            String id = getInterpreterId(USER_EMAIL);
            String query =  "UPDATE exam_schedule" +
                            " SET interpreter_id = null, exam_id = null, held = 0" +
                            " WHERE interpreter_id = " + id +
                            " AND TEST_TYPE = 'P'";
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public List<Integer> getQuestionNumbersAnsweredIncorrectly() {
        List<Integer> results = new LinkedList<>();
        try {
            String id = getInterpreterId(USER_EMAIL);
            String query =  "SELECT ke_exam.question_nmbr FROM knowledge_exam" +
                            " JOIN ke_exam ON knowledge_exam.id = ke_exam.ke_id" +
                            " WHERE knowledge_exam.interpreter_id = " + id +
                            " AND ke_exam.correct_flag <> ke_exam.answer_flag" +
                            " ORDER BY ke_exam.question_nmbr";
            results = dbu.executeQueryAndGetAllResultIntegerValues(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public int getNumberOfFailedPerformanceExams(String email) {
        String id = getInterpreterId(email);
        int count = 0;
        try {
            String query =  "SELECT count(*) FROM" +
                            " (SELECT * FROM interpreters.view_pe_exam_component)" +
                            " WHERE interpreter_id = " + id +
                            " AND pass_fail_flag = 'F'";
            count = Integer.valueOf(dbu.getQueryResult(query, "COUNT(*)"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getOrientationVideoPIN(int id) {
        String pin = null;
        try {
            String query = "SELECT pin FROM VIDEO_LINK WHERE CERTIFICATION_TYPE_ID = " + id;
            pin = dbu.getQueryResult(query, "PIN");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pin;
    }

    public void unregisterInterpreter(String email) {
        try {
            dbu.runScript(UNREGISTER_SQL);
            String interpreterId = getInterpreterId(email);
            String query = String.format("DELETE FROM interpreter_email WHERE interpreter_id = %s", interpreterId);
            dbu.executeQuery(query);
            query = String.format("DELETE FROM interpreter WHERE id = %s", interpreterId);
            dbu.executeQuery(query);
//            dbu.executeQuery("DELETE FROM interpreter WHERE id = (SELECT id FROM interpreter WHERE email = '"+ email +"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getKnowledgeExam(String  email){
        int keId = 0;
        try {
            String interpreterId = getInterpreterId(email);

            String query = "SELECT id FROM knowledge_exam ke WHERE interpreter_id = " + interpreterId + " AND score IS null";
            List <Integer> idList = dbu.executeQueryAndGetAllResultIntegerValues(query);
            keId = idList.get(0);
        } catch (Exception e){
            e.printStackTrace();
        }
        return keId;
    }

    public List<Integer> getCorrectAnswers(Integer keId){
        List <Integer> idList = new ArrayList<>();
        try {
            String query = "SELECT id FROM ke_exam WHERE ke_id = " + keId + " AND correct_flag = 'Y'";
            idList = dbu.executeQueryAndGetAllResultIntegerValues(query);
        } catch (Exception e){
            e.printStackTrace();
        }
        return idList;
    }


    public void setCycleDates(String email, String startDate, String endDate) {
        try {
            // Cycle start and end date should be in the format: MM/DD/YYY
            String interpreterId = getInterpreterId(email);
            String query =  "UPDATE interpreter " +
                            "SET cycle_start_date = to_date('"+ startDate +"','MM/DD/YYYY'), " +
                            "cycle_end_date = to_date('"+ endDate +"','MM/DD/YYYY') " +
                            "WHERE id = " + interpreterId;
            dbu.executeQuery(query);
            System.out.println("Cycle date set from '" + startDate + "' to '" + endDate + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCertificationExpirationDate(String email, String certName, String expirationDate) {
        try {
            String interpreterId = getInterpreterId(email);
            String certTypeId = getCertificationTypeId(certName);
            String certId = getCertificationId(interpreterId, certTypeId);
            String query = "UPDATE certification " +
                    "SET expiration_date = to_date('" + expirationDate + "','MM/DD/YYYY') " +
                    "WHERE id = " + certId;
            dbu.executeQuery(query);
            System.out.println(certName + " expiration date set to " + expirationDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCertificationRecognizedAcknowledgedDate(String email, String certName, String recognizedDate) {
        try {
            String interpreterId = getInterpreterId(email);
            String certTypeId = getCertificationTypeId(certName);
            String certId = getCertificationId(interpreterId, certTypeId);
            String query = "UPDATE certification " +
                    "SET acknowledged_date = to_date('" + recognizedDate + "','MM/DD/YYYY') " +
                    "WHERE id = " + certId;
            dbu.executeQuery(query);
            System.out.println(certName + " expiration date set to " + recognizedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCertificationRenewalDate(String email, String renewalDate) {
        try {
            String interpreterId = getInterpreterId(email);
            String query = "UPDATE interpreter " +
                    "SET renewal_date = to_date('" + renewalDate + "','MM/DD/YYYY') " +
                    "WHERE id = " + interpreterId;
            dbu.executeQuery(query);
            System.out.println("Interpreter ID " + interpreterId + " expiration date set to " + renewalDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCeId(String email) {
        String result = null;
        try {
            String interpreterId = getInterpreterId(email);
            String query = "SELECT id FROM ce_credit WHERE interpreter_id = " + interpreterId;
            result = dbu.getQueryResult(query, "ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void clearCEHCredit(String email) {
        try {
            String interpreterId = getInterpreterId(email);
            String query = "SELECT count(ID) FROM (SELECT id FROM ce_credit WHERE interpreter_id = " + interpreterId + ")";
            int events = Integer.valueOf(dbu.getQueryResult(query, "COUNT(ID)"));

            for (int i = 0; i < events; i++) {
                query = "DELETE FROM ce_credit WHERE id IN (" + getCeId(email) + ")";
                dbu.executeQuery(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCertificationTypeId(String certName) {
        String certTypeId = null;
        try {
            String query = "SELECT id FROM certification_type WHERE name = '" + certName + "'";
            certTypeId = dbu.getQueryResult(query, "ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certTypeId;
    }

    public String getCertificationId(String interpreterId, String certTypeId) {
        String certId = null;
        try {
            String query = "SELECT id FROM certification WHERE interpreter_id = " + interpreterId +
                    " AND certification_type_id = " + certTypeId;
            certId = dbu.getQueryResult(query, "ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return certId;
    }

    public void removeUploadedCertificationDocument(String email, String certName) {
        try {
            String interpreterId = getInterpreterId(email);
            String query = "SELECT id FROM certification" +
                    " WHERE interpreter_id = " + interpreterId +
                    " AND certification_type_id = " +
                    "(SELECT id FROM certification_type WHERE name = '" + certName + "')";
            String recordId = dbu.getQueryResult(query, "ID");
            query = "DELETE FROM attachment WHERE record_fk = " + recordId;
            dbu.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearCertificationCredit(String email, String certName) {
        try {
            String interpreterID = getInterpreterId(email);
            String certTypeId = getCertificationTypeId(certName);
            String query = "DELETE FROM certification WHERE interpreter_id = " + interpreterID +
                    " AND certification_type_id = " + certTypeId;
            dbu.executeQuery(query);
            System.out.println(certName + " certification removed from the " + email + " account.");
            removeUploadedCertificationDocument(email, certName);
            System.out.println("Removed all uploaded documents associated with certification.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
