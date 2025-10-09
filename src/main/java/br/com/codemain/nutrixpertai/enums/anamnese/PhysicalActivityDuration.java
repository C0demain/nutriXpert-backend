package br.com.codemain.nutrixpertai.enums.anamnese;

public enum PhysicalActivityDuration {
    THIRTY_MIN("30 min"),
    SIXTY_MIN("60 min"),
    NINETY_MIN("90 min");

    private final String displayName;

    PhysicalActivityDuration(String displayName) {
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
