package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Hydration {
    LESS_THAN_ONE("Menos de 1L"),
    BETWEEN_ONE_TWO("Entre 1L e 2L"),
    MORE_THAN_TWO("Mais de 2L");

    private final String displayName;

    Hydration(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static Hydration fromDisplayName(String displayName) {
        for (Hydration hydration : Hydration.values()) {
            if (hydration.displayName.equalsIgnoreCase(displayName)) {
                return hydration;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Hydration: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
