/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.inputTypes;

import java.util.List;

/**
 *
 * @author Arya
 */
public class CreatePolicyInput {

    public String policyName;
    public String policyDescription;
    public String insuranceProviderId;
    public String policyType;
    public Float averageMonthlyPremium;
    public List<String> usersEnrolled;
    public Integer premiumDuration;
    public Float policyPrice;
    public Float policyInterest;

    public Float getPolicyInterest() {
        return policyInterest;
    }

    public void setPolicyInterest(Float policyInterest) {
        this.policyInterest = policyInterest;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyDescription() {
        return policyDescription;
    }

    public void setPolicyDescription(String policyDescription) {
        this.policyDescription = policyDescription;
    }

    public String getInsuranceProviderId() {
        return insuranceProviderId;
    }

    public void setInsuranceProviderId(String insuranceProviderId) {
        this.insuranceProviderId = insuranceProviderId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public Float getAverageMonthlyPremium() {
        return averageMonthlyPremium;
    }

    public void setAverageMonthlyPremium(Float averageMonthlyPremium) {
        this.averageMonthlyPremium = averageMonthlyPremium;
    }

    public List<String> getUsersEnrolled() {
        return usersEnrolled;
    }

    public void setUsersEnrolled(List<String> usersEnrolled) {
        this.usersEnrolled = usersEnrolled;
    }

    public Integer getPremiumDuration() {
        return premiumDuration;
    }

    public void setPremiumDuration(Integer premiumDuration) {
        this.premiumDuration = premiumDuration;
    }

    public Float getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(Float policyPrice) {
        this.policyPrice = policyPrice;
    }

}
