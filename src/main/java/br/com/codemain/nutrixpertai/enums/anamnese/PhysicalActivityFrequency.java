package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PhysicalActivityFrequency {
    ONE_TO_TWO("1–2x por semana"),
    THREE_TO_FOUR("3–4x por semana"),
    FIVE_OR_MORE("5 ou mais vezes por semana");

    private final String displayName;

    PhysicalActivityFrequency(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static PhysicalActivityFrequency fromDisplayName(String displayName) {
        for (PhysicalActivityFrequency frequency : PhysicalActivityFrequency.values()) {
            if (frequency.displayName.equalsIgnoreCase(displayName)) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Valor inválido para PhysicalActivityFrequency: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
