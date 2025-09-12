package br.com.codemain.nutrixpertai.enums;

public enum Role {

    ADMIN("Administrator"),

    USER("User"),
    
    NUTRITIONIST("Nutritionist");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
