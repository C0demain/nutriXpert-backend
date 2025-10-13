package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AllergyIntoleranceType {
    NONE("Não"),
    LACTOSE_INTOLERANCE("Intolerância à lactose"),
    GLUTEN_SENSITIVITY("Sensibilidade ao glúten / doença celíaca"),
    FOOD_ALLERGY("Alergia alimentar"),
    MEDICATION_ALLERGY("Alergia medicamentosa"),
    OTHER("Outro");

    private final String displayName;

    AllergyIntoleranceType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static AllergyIntoleranceType fromDisplayName(String displayName) {
        for (AllergyIntoleranceType type : AllergyIntoleranceType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para AllergyIntoleranceType: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
