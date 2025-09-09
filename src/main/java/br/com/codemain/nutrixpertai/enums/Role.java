package br.com.codemain.nutrixpertai.enums;

public enum Role {

    ROLE_ADMIN("Administrator"),

    ROLE_USER("Standard User"),
    
    ROLE_NUTRITIONIST("Nutritionist");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
