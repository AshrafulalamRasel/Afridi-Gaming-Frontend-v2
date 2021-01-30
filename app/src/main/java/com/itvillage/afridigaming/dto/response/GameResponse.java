package com.itvillage.afridigaming.dto.response;


import java.util.List;

public class GameResponse {

    private String id;


    private String createdBy;


    private String createdAt;


    private String updatedBy;


    private String updatedAt;

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


    private boolean gameIsActive;

    private String gameOwnerId;

    private String gameplayOption;
    private String gameplayStartTime;

    public String getGameplayOption() {
        return gameplayOption;
    }

    public void setGameplayOption(String gameplayOption) {
        this.gameplayOption = gameplayOption;
    }

    public String getGameplayStartTime() {
        return gameplayStartTime;
    }

    public void setGameplayStartTime(String gameplayStartTime) {
        this.gameplayStartTime = gameplayStartTime;
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


    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

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

    public boolean isGameIsActive() {
        return gameIsActive;
    }

    public void setGameIsActive(boolean gameIsActive) {
        this.gameIsActive = gameIsActive;
    }

    public String getGameOwnerId() {
        return gameOwnerId;
    }

    public void setGameOwnerId(String gameOwnerId) {
        this.gameOwnerId = gameOwnerId;
    }

    public List<RegisterUsersInGameEntity> getRegisterUsersInGameEntities() {
        return registerUsersInGameEntities;
    }

    public void setRegisterUsersInGameEntities(List<RegisterUsersInGameEntity> registerUsersInGameEntities) {
        this.registerUsersInGameEntities = registerUsersInGameEntities;
    }

    private List<RegisterUsersInGameEntity> registerUsersInGameEntities;


}
