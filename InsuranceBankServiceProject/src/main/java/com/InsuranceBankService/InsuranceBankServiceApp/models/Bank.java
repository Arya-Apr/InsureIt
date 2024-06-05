/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceBankService.InsuranceBankServiceApp.models;

import com.InsuranceBankService.InsuranceBankServiceApp.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author pcran
 */
@Document(collection = "Bank")
public class Bank {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Field("_id")
    public ObjectId _id;
    public String id;
    public String bank_name;
    public List<String> offeredPolicies;
    public float interestRate;

    public String getId() {
        return id;
    }

    public List<String> getOfferedPolicies() {
        return offeredPolicies;
    }

    public void setOfferedPolicies(List<String> offeredPolicies) {
        this.offeredPolicies = offeredPolicies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
}
