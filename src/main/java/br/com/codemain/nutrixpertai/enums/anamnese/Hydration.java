package br.com.codemain.nutrixpertai.enums.anamnese;

public enum Hydration {
    LESS_THAN_ONE("Menos de 1L"),
    BETWEEN_ONE_TWO("Entre 1L e 2L"),
    MORE_THAN_TWO("Mais de 2L");

    private final String displayName;

    Hydration(String displayName) {
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
