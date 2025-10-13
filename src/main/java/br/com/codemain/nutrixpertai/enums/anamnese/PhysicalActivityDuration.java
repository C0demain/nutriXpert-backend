package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PhysicalActivityDuration {
    THIRTY_MIN("30 min"),
    SIXTY_MIN("60 min"),
    NINETY_MIN("90 min");

    private final String displayName;

    PhysicalActivityDuration(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static PhysicalActivityDuration fromDisplayName(String displayName) {
        for (PhysicalActivityDuration duration : PhysicalActivityDuration.values()) {
            if (duration.displayName.equalsIgnoreCase(displayName)) {
                return duration;
            }
        }
        throw new IllegalArgumentException("Valor inválido para PhysicalActivityDuration: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
