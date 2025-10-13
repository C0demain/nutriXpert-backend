package br.com.codemain.nutrixpertai.enums.anamnese;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SurgeryType {
    NONE("não"),
    BARIATRIC("Bariátrica"),
    GALLBLADDER("Vesícula"),
    HIATAL_HERNIA("Hérnia de hiato (cirurgia do refluxo)"),
    ORTHOPEDIC("Ortopédica"),
    CESAREAN_GYNECOLOGICAL("Cesárea / Ginecológica"),
    OTHER("Outro");

    private final String displayName;

    SurgeryType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static SurgeryType fromDisplayName(String displayName) {
        for (SurgeryType type : SurgeryType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para SurgeryType: " + displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
