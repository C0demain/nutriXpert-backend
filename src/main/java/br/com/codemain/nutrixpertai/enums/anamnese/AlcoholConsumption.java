package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AlcoholConsumption {
    NONE("não consome"),
    SOCIALLY_1X_2X("Socialmente 1-2 x por semana"),
    FREQUENTLY_3X_4X("Frequente 3-4 x por semana"),
    DAILY("Uso diário");

    private final String displayName;

    AlcoholConsumption(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static AlcoholConsumption fromDisplayName(String displayName) {
        for (AlcoholConsumption consumption : AlcoholConsumption.values()) {
            if (consumption.displayName.equalsIgnoreCase(displayName)) {
                return consumption;
            }
        }
        throw new IllegalArgumentException("Valor inválido para AlcoholConsumption: " + displayName);
    }


    @Override
    public String toString() {
        return displayName;
    }
}
