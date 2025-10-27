package br.com.codemain.nutrixpertai.dto.Session;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SessionWithMessagesDTO {
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("appName")
    private String appName;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("messages")
    private List<MessageDTO> messages;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("updateTime")
    private String updateTime;

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
