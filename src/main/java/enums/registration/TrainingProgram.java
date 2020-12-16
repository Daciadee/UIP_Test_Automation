package enums.registration;

public enum TrainingProgram {

    SALT_LAKE_COMMUNITY_COLLEGE("Salt Lake Community College"),
    UTAH_VALLEY_UNIVERSITY("Utah Valley University"),
    DATC("DATC"),
    DIXIE_STATE_UNIVERSITY("Dixie State University"),
    OUT_OF_STATE("Out of State Program"),
    OTHER("Other");

    private String program;

    TrainingProgram(String program) {
        this.program = program;
    }

    public String program() {
        return program;
    }
}
