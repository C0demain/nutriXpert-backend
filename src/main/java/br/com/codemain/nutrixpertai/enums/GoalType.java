package br.com.codemain.nutrixpertai.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GoalType {
    WEIGHT_LOSS("Emagrecimento"),
    MUSCLE_GAIN("Ganho de massa muscular"),
    DIABETES_CONTROL("Controle de diabetes"),
    NUTRITIONAL_REEDUCATION("Reeducação alimentar"),
    PHYSICAL_MENTAL_PERFORMANCE("Performance física e mental"),
    WEIGHT_GAIN("Ganho de peso"),
    FAT_LOSS("Perda de gordura"),
    MAINTENANCE("Manutenção do peso");

    private final String displayName;

    GoalType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static GoalType fromDisplayName(String displayName) {
        for (GoalType type : GoalType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para GoalType: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
