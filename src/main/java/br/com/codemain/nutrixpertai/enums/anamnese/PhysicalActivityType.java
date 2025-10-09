package br.com.codemain.nutrixpertai.enums.anamnese;

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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
