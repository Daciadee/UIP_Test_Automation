package enums.registration;

public enum LearnSign {

    SCHOOL("School"),
    INTERPRETER_TRAINING_PROGRAM("Interpreter Training Program"),
    CHURCH("Church"),
    MISSION("Mission"),
    DEAF_PARENTS("Deaf parents"),
    DEAF_IMMEDIATE_FAMILY("Deaf immediate family"),
    DEAF_EXTENDED_FAMILY("Deaf extended family"),
    DEAF_FRIEND("Deaf friend"),
    OTHER("Other");

    private String learned;

    LearnSign(String learned) {
        this.learned = learned;
    }

    public String learned() {
        return learned;
    }
}
