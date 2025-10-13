package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NightAwakeningFrequency {
    NAO("nao"),
    AT_LEAST_ONCE("pelo menos uma vez"),
    MORE_THAN_ONCE("mais de uma vez");

    private final String displayName;

    NightAwakeningFrequency(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static NightAwakeningFrequency fromDisplayName(String displayName) {
        for (NightAwakeningFrequency frequency : NightAwakeningFrequency.values()) {
            if (frequency.displayName.equalsIgnoreCase(displayName)) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Valor inválido para NightAwakeningFrequency: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
