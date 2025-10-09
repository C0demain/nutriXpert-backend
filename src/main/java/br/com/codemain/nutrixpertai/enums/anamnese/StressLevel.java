package br.com.codemain.nutrixpertai.enums.anamnese;

public enum StressLevel {
    LOW("baixo"),
    MEDIUM("moderado"),
    HIGH("alto");

    private final String displayName;

    StressLevel(String displayName) {
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
