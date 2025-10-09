package br.com.codemain.nutrixpertai.enums.anamnese;

public enum NightAwakeningFrequency {
    NAO("nao"),
    AT_LEAST_ONCE("pelo menos uma vez"),
    MORE_THAN_ONCE("mais de uma vez");

    private final String displayName;

    NightAwakeningFrequency(String displayName) {
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
