package br.com.codemain.nutrixpertai.entity.id;

import java.util.Objects;

public class SessionId implements java.io.Serializable {
    private String appName;
    private String userId;
    private String id;

    // Constructors
    public SessionId() {
    }

    public SessionId(String appName, String userId, String id) {
        this.appName = appName;
        this.userId = userId;
        this.id = id;
    }

    // Getters and Setters
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionId sessionId = (SessionId) o;
        return Objects.equals(appName, sessionId.appName) &&
                Objects.equals(userId, sessionId.userId) &&
                Objects.equals(id, sessionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appName, userId, id);
    }
}