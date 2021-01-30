package com.itvillage.afridigaming.dto.response;


import java.time.LocalDateTime;

public class WithDrawMoneyResponse {

    private String id;
    private String paymentGetawayName;
    private Double amount;
    private String lastThreeDigitOfPayableMobileNo;
    private String userName;
    private Double currentBalance;
    private String updatedAt;
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

    public String getLastThreeDigitOfPayableMobileNo() {
        return lastThreeDigitOfPayableMobileNo;
    }

    public void setLastThreeDigitOfPayableMobileNo(String lastThreeDigitOfPayableMobileNo) {
        this.lastThreeDigitOfPayableMobileNo = lastThreeDigitOfPayableMobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
