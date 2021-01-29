package com.itvillage.afridigaming.dto.request;


public class GameSetRequest {

    private String gameNumber;

    private String gameType;

    private String gameName;

    private String version;

    private String map;

    private String gameStatus;

    private String roomId;

    private String roomPassword;

    private int totalPrize;

    private int winnerPrize;

    private int secondPrize;

    private int thirdPrize;

    private int perKillPrize;

    private int entryFee;

    public String getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(String roomPassword) {
        this.roomPassword = roomPassword;
    }

    public int getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(int totalPrize) {
        this.totalPrize = totalPrize;
    }

    public int getWinnerPrize() {
        return winnerPrize;
    }

    public void setWinnerPrize(int winnerPrize) {
        this.winnerPrize = winnerPrize;
    }

    public int getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(int secondPrize) {
        this.secondPrize = secondPrize;
    }

    public int getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(int thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public int getPerKillPrize() {
        return perKillPrize;
    }

    public void setPerKillPrize(int perKillPrize) {
        this.perKillPrize = perKillPrize;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }
}
