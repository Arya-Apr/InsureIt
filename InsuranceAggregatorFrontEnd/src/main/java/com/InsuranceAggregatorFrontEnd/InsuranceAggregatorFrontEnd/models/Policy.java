/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Arya
 */
public class Policy {

    @JsonSerialize(using = ObjectIdSerializer.class)
    public ObjectId _id;
    public String id;
    public String policyName;
    public String policyDescription;
    public String insuranceProviderId;
    public String policyType;
    public Float policyPrice;
    public Float averageMonthlyPremium;
    public Integer premiumDuration;
    public Float policyInterest;
    public Float customerRating;
    public Integer totalRatings;
    public List<String> usersEnrolled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Float getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(Float policyPrice) {
        this.policyPrice = policyPrice;
    }

    public Float getAverageMonthlyPremium() {
        return averageMonthlyPremium;
    }

    public void setAverageMonthlyPremium(Float averageMonthlyPremium) {
        this.averageMonthlyPremium = averageMonthlyPremium;
    }

    public Integer getPremiumDuration() {
        return premiumDuration;
    }

    public void setPremiumDuration(Integer premiumDuration) {
        this.premiumDuration = premiumDuration;
    }

    public Float getPolicyInterest() {
        return policyInterest;
    }

    public void setPolicyInterest(Float policyInterest) {
        this.policyInterest = policyInterest;
    }

    public Float getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Float customerRating) {
        this.customerRating = customerRating;
    }

    public Integer getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(Integer totalRatings) {
        this.totalRatings = totalRatings;
    }

    public List<String> getUsersEnrolled() {
        return usersEnrolled;
    }

    public void setUsersEnrolled(List<String> usersEnrolled) {
        this.usersEnrolled = usersEnrolled;
    }
}
