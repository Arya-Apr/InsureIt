/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.inputTypes;

/**
 *
 * @author Arya
 */
public class StripeResponse {

    private String intentID;
    private String clientSecret;

    public StripeResponse(String intentId, String clientSecret) {
        this.intentID = intentId;
        this.clientSecret = clientSecret;
    }

    public String getIntentID() {
        return intentID;
    }

    public void setIntentID(String intentID) {
        this.intentID = intentID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
