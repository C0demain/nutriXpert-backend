package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StressLevel {
    LOW("baixo"),
    MEDIUM("moderado"),
    HIGH("alto");

    private final String displayName;

    StressLevel(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static StressLevel fromDisplayName(String displayName) {
        for (StressLevel level : StressLevel.values()) {
            if (level.displayName.equalsIgnoreCase(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Valor inválido para StressLevel: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
