package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PhysicalActivityType {
    SEDENTARY("Sedentário(a)"),
    WALKING("Caminhada"),
    WEIGHT_TRAINING("Musculação"),
    RUNNING("Corrida"),
    CROSSFIT("Crossfit"),
    SWIMMING("Natação"),
    OTHER("Outro");

    private final String displayName;

    PhysicalActivityType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static PhysicalActivityType fromDisplayName(String displayName) {
        for (PhysicalActivityType type : PhysicalActivityType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para PhysicalActivityType: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
