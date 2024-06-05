/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceService.InsuranceService.inputTypes;

import java.util.List;

/**
 *
 * @author pcran
 */
public class CreateInsuranceInput {

    public String logo;
    public String phone;
    public List<String> offeredPolicies;
    public Float customerRating;
    public String insuranceAccount;
    public String insuranceBank;

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
