package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HealthConditionType {

    DIABETES_TYPE_1("Diabetes tipo 1"),
    DIABETES_TYPE_2("Diabetes tipo 2"),
    HYPERTENSION("Hipertensão arterial"),
    DYSLIPIDEMIA("Dislipidemia (colesterol, triglicerídeos)"),
    KIDNEY_DISEASE("Doença renal"),
    LIVER_DISEASE("Doença hepática"),
    GASTRITIS_REFLUX("Gastrite / refluxo"),
    INTESTINAL_DISORDER("Intestino preso / diarreia"),
    OSTEOPOROSIS("Osteoporose"),
    CARDIOVASCULAR_DISEASE("Doença cardiovascular (infarto, insuficiência cardíaca)"),
    CANCER("Câncer"),
    DEPRESSION_ANXIETY("Depressão / Ansiedade"),
    AUTOIMMUNE_DISEASE("Doenças autoimunes"),
    OTHER("Outro");

    private final String displayName;

    HealthConditionType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static HealthConditionType fromDisplayName(String displayName) {
        for (HealthConditionType type : HealthConditionType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para HealthConditionType: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
