/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.dtos;

import com.service.service.serializers.ObjectIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;

/**
 *
 * @author Arya
 */
public class PaymentRequestDto {

    public String user_email;
    @JsonSerialize(using = ObjectIdSerializer.class)
    public ObjectId policy_id;
    public String paymentType;
    public boolean auto_pay;
    public Integer paymentDuration;
    public Float policyInterest;
    public Float policyPrice;

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public ObjectId getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(ObjectId policy_id) {
        this.policy_id = policy_id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isAuto_pay() {
        return auto_pay;
    }

    public void setAuto_pay(boolean auto_pay) {
        this.auto_pay = auto_pay;
    }

    public Integer getPaymentDuration() {
        return paymentDuration;
    }

    public void setPaymentDuration(Integer paymentDuration) {
        this.paymentDuration = paymentDuration;
    }

    public Float getPolicyInterest() {
        return policyInterest;
    }

    public void setPolicyInterest(Float policyInterest) {
        this.policyInterest = policyInterest;
    }

    public Float getPolicyPrice() {
        return policyPrice;
    }

    public void setPolicyPrice(Float policyPrice) {
        this.policyPrice = policyPrice;
    }

}
