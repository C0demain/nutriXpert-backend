package br.com.codemain.nutrixpertai.enums.anamnese;

public enum AlcoholConsumption {
    NONE("não consome"),
    SOCIALLY_1X_2X("Socialmente 1-2 x por semana"),
    FREQUENTLY_3X_4X("Frequente 3-4 x por semana"),
    DAILY("Uso diário");

    private final String displayName;

    AlcoholConsumption(String displayName) {
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
