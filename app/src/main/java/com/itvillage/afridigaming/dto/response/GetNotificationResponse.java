package com.itvillage.afridigaming.dto.response;


public class GetNotificationResponse {

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

    @Override
    public String toString() {
        return "GetNotificationResponse{" +
                "notificationSubject='" + notificationSubject + '\'' +
                ", notificationBody='" + notificationBody + '\'' +
                '}';
    }
}
