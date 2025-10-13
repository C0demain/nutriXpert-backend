package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SleepQuality {
    GOOD("boa"),
    REGULAR("regular"),
    BAD("ruim");


    private final String displayName;

    SleepQuality(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static SleepQuality fromDisplayName(String displayName) {
        for (SleepQuality quality : SleepQuality.values()) {
            if (quality.displayName.equalsIgnoreCase(displayName)) {
                return quality;
            }
        }
        throw new IllegalArgumentException("Valor inválido para SleepQuality: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
