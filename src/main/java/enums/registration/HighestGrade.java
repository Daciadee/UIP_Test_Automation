package enums.registration;

public enum HighestGrade {

    HIGH_SCHOOL_GED("High School Diploma/GED"),
    SOME_COLLEGE("Some College"),
    ASSOCIATES_DEGREE("Associate Degree"),
    BACHELORS_DEGREE("Bachelor Degree"),
    MASTERS_DEGREE("Master Degree"),
    DOCTORAL_DEGREE("Doctoral Degree");

    private String grade;

    HighestGrade(String grade) {
        this.grade = grade;
    }

    public String grade() {
        return grade;
    }

}
