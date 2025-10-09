package br.com.codemain.nutrixpertai.enums.anamnese;

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

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
