/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceService.InsuranceService.models;

import com.InsuranceService.InsuranceService.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author pcran
 */
@Document(collection = "Insurance")
public class Insurance {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Field("_id")
    public String _id;
    public String id;
    public String logo;
    public String phone;
    public String insuranceType;
    public List<String> offeredPolicies;
    public Float customerRating;
    public String insuranceAccount;
    public String insuranceBank;

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getOfferedPolicies() {
        return offeredPolicies;
    }

    public void setOfferedPolicies(List<String> offeredPolicies) {
        this.offeredPolicies = offeredPolicies;
    }

    public Float getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(Float customerRating) {
        this.customerRating = customerRating;
    }

    public String getInsuranceAccount() {
        return insuranceAccount;
    }

    public void setInsuranceAccount(String insuranceAccount) {
        this.insuranceAccount = insuranceAccount;
    }

    public String getInsuranceBank() {
        return insuranceBank;
    }

    public void setInsuranceBank(String insuranceBank) {
        this.insuranceBank = insuranceBank;
    }

}
