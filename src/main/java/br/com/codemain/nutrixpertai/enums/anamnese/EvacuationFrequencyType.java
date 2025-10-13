package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EvacuationFrequencyType {
    EVERY_DAY("Todo dia"),
    FIVE_TIMES_PER_WEEK("5x por semana"),
    THREE_TIMES_PER_WEEK("3x por semana"),
    ONCE_PER_WEEK("1x por semana");

    private final String displayName;

    EvacuationFrequencyType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static EvacuationFrequencyType fromDisplayName(String displayName) {
        for (EvacuationFrequencyType type : EvacuationFrequencyType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para EvacuationFrequencyType: " + displayName);
    }


    @Override
    public String toString() {
        return displayName;
    }
}
