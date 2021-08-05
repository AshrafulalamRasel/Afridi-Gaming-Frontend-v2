package com.itvillage.afridigaming.dto.response;


public class GetProgessBarInfoResponse {
    private String totalPlayerInGame;
    private String totalJoinPlayerInGame;

    public String getTotalPlayerInGame() {
        return totalPlayerInGame;
    }

    public void setTotalPlayerInGame(String totalPlayerInGame) {
        this.totalPlayerInGame = totalPlayerInGame;
    }

    public String getTotalJoinPlayerInGame() {
        return totalJoinPlayerInGame;
    }

    public void setTotalJoinPlayerInGame(String totalJoinPlayerInGame) {
        this.totalJoinPlayerInGame = totalJoinPlayerInGame;
    }
}
