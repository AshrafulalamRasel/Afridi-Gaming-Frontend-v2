package com.itvillage.afridigaming.dto.response;


import java.util.List;

public class RequestedNotificationResponse {

    private String id;


    private String createdBy;


    private String createdAt;


    private String updatedBy;


    private String updatedAt;

    private String name;

    private String userId;

    private String paymentGetawayName;

    private Double amount;

    private int lastThreeDigitOfPayableMobileNo;

    private boolean isAuthorityProcessed;

    private String balanceStatus;

    public String getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(String balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentGetawayName() {
        return paymentGetawayName;
    }

    public void setPaymentGetawayName(String paymentGetawayName) {
        this.paymentGetawayName = paymentGetawayName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getLastThreeDigitOfPayableMobileNo() {
        return lastThreeDigitOfPayableMobileNo;
    }

    public void setLastThreeDigitOfPayableMobileNo(int lastThreeDigitOfPayableMobileNo) {
        this.lastThreeDigitOfPayableMobileNo = lastThreeDigitOfPayableMobileNo;
    }

    public boolean isAuthorityProcessed() {
        return isAuthorityProcessed;
    }

    public void setAuthorityProcessed(boolean authorityProcessed) {
        isAuthorityProcessed = authorityProcessed;
    }
}
