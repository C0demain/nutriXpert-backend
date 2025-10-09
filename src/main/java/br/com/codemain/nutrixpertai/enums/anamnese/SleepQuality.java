package br.com.codemain.nutrixpertai.enums.anamnese;

public enum SleepQuality {
    GOOD("boa"),
    REGULAR("regular"),
    BAD("ruim");



    private final String displayName;

    SleepQuality(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
