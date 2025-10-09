package br.com.codemain.nutrixpertai.enums.anamnese;

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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
