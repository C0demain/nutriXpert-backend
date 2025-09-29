package br.com.codemain.nutrixpertai.entity;

import br.com.codemain.nutrixpertai.entity.id.SessionId;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "sessions")
@IdClass(SessionId.class)
public class Session {

    @Id
    @Column(name = "app_name", length = 128, nullable = false)
    private String appName;

    @Id
    @Column(name = "user_id", length = 128, nullable = false)
    private String userId;

    @Id
    @Column(name = "id", length = 128, nullable = false)
    private String id;

    @Column(name = "state", nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String state;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    public Session() {
    }

    public Session(String appName, String userId, String id, String state) {
        this.appName = appName;
        this.userId = userId;
        this.id = id;
        this.state = state;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appName, userId, id);
    }
}