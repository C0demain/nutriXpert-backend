package br.com.codemain.nutrixpertai.entity;

import br.com.codemain.nutrixpertai.entity.id.EventId;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "events")
@IdClass(EventId.class)
public class Event {

    @Id
    @Column(name = "id", length = 128, nullable = false)
    private String id;

    @Id
    @Column(name = "app_name", length = 128, nullable = false)
    private String appName;

    @Id
    @Column(name = "user_id", length = 128, nullable = false)
    private String userId;

    @Id
    @Column(name = "session_id", length = 128, nullable = false)
    private String sessionId;

    @Column(name = "invocation_id", length = 256, nullable = false)
    private String invocationId;

    @Column(name = "author", length = 256, nullable = false)
    private String author;

    @Lob
    @Column(name = "actions", nullable = false, columnDefinition = "bytea")
    private byte[] actions;

    @Column(name = "long_running_tool_ids_json", columnDefinition = "text")
    private String longRunningToolIdsJson;

    @Column(name = "branch", length = 256)
    private String branch;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "content", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String content;

    @Column(name = "grounding_metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String groundingMetadata;

    @Column(name = "custom_metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String customMetadata;

    @Column(name = "partial")
    private Boolean partial;

    @Column(name = "turn_complete")
    private Boolean turnComplete;

    @Column(name = "error_code", length = 256)
    private String errorCode;

    @Column(name = "error_message", length = 1024)
    private String errorMessage;

    @Column(name = "interrupted")
    private Boolean interrupted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "app_name", referencedColumnName = "app_name", insertable = false, updatable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false),
            @JoinColumn(name = "session_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private Session session;

    public Event() {
    }

    public Event(String id, String appName, String userId, String sessionId,
                 String invocationId, String author, byte[] actions, LocalDateTime timestamp) {
        this.id = id;
        this.appName = appName;
        this.userId = userId;
        this.sessionId = sessionId;
        this.invocationId = invocationId;
        this.author = author;
        this.actions = actions;
        this.timestamp = timestamp;
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

    public String getInvocationId() {
        return invocationId;
    }

    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public byte[] getActions() {
        return actions;
    }

    public void setActions(byte[] actions) {
        this.actions = actions;
    }

    public String getLongRunningToolIdsJson() {
        return longRunningToolIdsJson;
    }

    public void setLongRunningToolIdsJson(String longRunningToolIdsJson) {
        this.longRunningToolIdsJson = longRunningToolIdsJson;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroundingMetadata() {
        return groundingMetadata;
    }

    public void setGroundingMetadata(String groundingMetadata) {
        this.groundingMetadata = groundingMetadata;
    }

    public String getCustomMetadata() {
        return customMetadata;
    }

    public void setCustomMetadata(String customMetadata) {
        this.customMetadata = customMetadata;
    }

    public Boolean getPartial() {
        return partial;
    }

    public void setPartial(Boolean partial) {
        this.partial = partial;
    }

    public Boolean getTurnComplete() {
        return turnComplete;
    }

    public void setTurnComplete(Boolean turnComplete) {
        this.turnComplete = turnComplete;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getInterrupted() {
        return interrupted;
    }

    public void setInterrupted(Boolean interrupted) {
        this.interrupted = interrupted;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(appName, event.appName) &&
                Objects.equals(userId, event.userId) &&
                Objects.equals(sessionId, event.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appName, userId, sessionId);
    }
}