/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.inputTypes;

/**
 *
 * @author Arya
 */
public class CreatePaymentInput {

    public String policy_id;
    //payment type will be in use for auto payments(if cc directly deduct it from them)
    public String paymentType;
    public String status;
    public String username;
    public Float price;
    public boolean auto_pay;
    public Float interest;
    //this field will help in naming the payment(1st premium, 2nd etc)
    public String paymentName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(String policy_id) {
        this.policy_id = policy_id;
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
