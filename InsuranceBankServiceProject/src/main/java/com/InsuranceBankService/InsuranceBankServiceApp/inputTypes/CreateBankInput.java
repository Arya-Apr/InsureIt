/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceBankService.InsuranceBankServiceApp.inputTypes;

import java.util.List;

/**
 *
 * @author pcran
 */
public class CreateBankInput {

    public String bank_name;
    public List<String> offeredPolicies;
    public float interestRate;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public List<String> getOfferedPolicies() {
        return offeredPolicies;
    }

    public void setOfferedPolicies(List<String> offeredPolicies) {
        this.offeredPolicies = offeredPolicies;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
}
