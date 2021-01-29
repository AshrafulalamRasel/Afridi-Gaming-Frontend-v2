package com.itvillage.afridigaming.dto.response;

/**
 * Created by Rasel on 5/26/2019.
 */

public class LoginResponse {

    private String tokenType;
    private String accessToken;


    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponse(String tokenType, String accessToken,String id) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.id = id;
    }
}
