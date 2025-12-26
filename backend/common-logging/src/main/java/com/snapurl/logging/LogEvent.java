package com.snapurl.logging;

public class LogEvent {

    private String service;
    private String message;
    private String method;
    private String path;
    private Integer status;
    private Long timestamp;

    // empty constructor (REQUIRED by Jackson)
    public LogEvent() {
    }

    // getters & setters
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
