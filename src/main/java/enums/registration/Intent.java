package enums.registration;

public enum Intent {

    THIRD_PARTY("Third Party"),
    NOVICE("Novice"),
    PROFESSIONAL("Professional");

    private String intent;

    Intent(String intent) {
        this.intent = intent;
    }

    public String intent() {
        return intent;
    }
}
