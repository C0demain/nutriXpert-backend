package br.com.codemain.nutrixpertai.enums.anamnese;

public enum EvacuationFrequencyType {
    EVERY_DAY("Todo dia"),
    FIVE_TIMES_PER_WEEK("5x por semana"),
    THREE_TIMES_PER_WEEK("3x por semana"),
    ONCE_PER_WEEK("1x por semana");

    private final String displayName;

    EvacuationFrequencyType(String displayName) {
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
