package com.itvillage.afridigaming.dto.response;


public class GetNotificationResponse {
    private String id;
    private String notificationSubject;
    private String notificationBody;

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationBody() {
        return notificationBody;
    }

    public void setNotificationBody(String notificationBody) {
        this.notificationBody = notificationBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetNotificationResponse{" +
                "id='" + id + '\'' +
                ", notificationSubject='" + notificationSubject + '\'' +
                ", notificationBody='" + notificationBody + '\'' +
                '}';
    }
}
