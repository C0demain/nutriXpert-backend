package br.com.codemain.nutrixpertai.entity.id;

import java.util.Objects;

public class EventId implements java.io.Serializable {
    private String id;
    private String appName;
    private String userId;
    private String sessionId;

    public EventId() {}

    public EventId(String id, String appName, String userId, String sessionId) {
        this.id = id;
        this.appName = appName;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventId eventId = (EventId) o;
        return Objects.equals(id, eventId.id) &&
                Objects.equals(appName, eventId.appName) &&
                Objects.equals(userId, eventId.userId) &&
                Objects.equals(sessionId, eventId.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appName, userId, sessionId);
    }
}