package com.itvillage.afridigaming.dto.response;


public class PaymentDetailsResponse {
    private String username;
    private String email;
    private String gameName;
    private String winningStatus;
    private String updatedAt;
    private int perKillInGame;
    private int gamePerInvest;
    private Double incomeInPerGame;
    private Double currentAccount;
    private Double reFound;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getReFound() {
        return reFound;
    }

    public void setReFound(Double reFound) {
        this.reFound = reFound;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getWinningStatus() {
        return winningStatus;
    }

    public void setWinningStatus(String winningStatus) {
        this.winningStatus = winningStatus;
    }

    public int getPerKillInGame() {
        return perKillInGame;
    }

    public void setPerKillInGame(int perKillInGame) {
        this.perKillInGame = perKillInGame;
    }

    public int getGamePerInvest() {
        return gamePerInvest;
    }

    public void setGamePerInvest(int gamePerInvest) {
        this.gamePerInvest = gamePerInvest;
    }

    public Double getIncomeInPerGame() {
        return incomeInPerGame;
    }

    public void setIncomeInPerGame(Double incomeInPerGame) {
        this.incomeInPerGame = incomeInPerGame;
    }

    public Double getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Double currentAccount) {
        this.currentAccount = currentAccount;
    }
}
