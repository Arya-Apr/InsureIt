/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAccountService.InsuranceAccountService.models;

import com.InsuranceAccountService.InsuranceAccountService.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Arya
 */
@Document("Account")
public class Account {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Field("_id")
    public ObjectId _id;
    public String account_id;
    public Float balance;
    public String password;
    public String account_holder;
    public String associated_bank;

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount_holder() {
        return account_holder;
    }

    public void setAccount_holder(String account_holder) {
        this.account_holder = account_holder;
    }

    public String getAssociated_bank() {
        return associated_bank;
    }

    public void setAssociated_bank(String associated_bank) {
        this.associated_bank = associated_bank;
    }
}
