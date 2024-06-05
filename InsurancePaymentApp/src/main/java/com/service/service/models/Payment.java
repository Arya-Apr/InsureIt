/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.service.service.serializers.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Arya
 */
@Document("Payment")
public class Payment {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @Field("_id")
    public ObjectId _id;
    public String id;
    public String user_email;
    public String policy_id;
    //payment type will be in use for auto payments(if cc directly deduct it from them)
    public String paymentType;
    public String status;
    public Float price;
    public boolean auto_pay;
    public Float interest;
    //this field will help in naming the payment(1st premium, 2nd etc)
    public String paymentName;
    public String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public boolean isAuto_pay() {
        return auto_pay;
    }

    public void setAuto_pay(boolean auto_pay) {
        this.auto_pay = auto_pay;
    }

    public Float getInterest() {
        return interest;
    }

    public void setInterest(Float interest) {
        this.interest = interest;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

}
