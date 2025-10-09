package br.com.codemain.nutrixpertai.enums.anamnese;

public enum PhysicalActivityFrequency {
    ONE_TO_TWO("1–2x por semana"),
    THREE_TO_FOUR("3–4x por semana"),
    FIVE_OR_MORE("5 ou mais vezes por semana");

    private final String displayName;

    PhysicalActivityFrequency(String displayName) {
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
