package com.itvillage.afridigaming.dto.response;

public class RegisterUsersInGameEntity {

    private String userId;

    private int totalKill;

    private Double totalEarn;

    private String partnerType;

    private String partnerOneName;

    private String partnerTwoName;

    private String partnerThreeName;

    private String partnerNameFour;

    public String getPartnerNameFour() {
        return partnerNameFour;
    }

    public void setPartnerNameFour(String partnerNameFour) {
        this.partnerNameFour = partnerNameFour;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalKill() {
        return totalKill;
    }

    public void setTotalKill(int totalKill) {
        this.totalKill = totalKill;
    }

    public Double getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(Double totalEarn) {
        this.totalEarn = totalEarn;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getPartnerOneName() {
        return partnerOneName;
    }

    public void setPartnerOneName(String partnerOneName) {
        this.partnerOneName = partnerOneName;
    }

    public String getPartnerTwoName() {
        return partnerTwoName;
    }

    public void setPartnerTwoName(String partnerTwoName) {
        this.partnerTwoName = partnerTwoName;
    }

    public String getPartnerThreeName() {
        return partnerThreeName;
    }

    public void setPartnerThreeName(String partnerThreeName) {
        this.partnerThreeName = partnerThreeName;
    }
}
