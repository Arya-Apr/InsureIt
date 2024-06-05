/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAccountService.InsuranceAccountService.inputTypes;

/**
 *
 * @author Arya
 */
public class CreateAccountInput {
    public Float balance;
    public String password;
    public String associated_bank;

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

    public String getAssociated_bank() {
        return associated_bank;
    }

    public void setAssociated_bank(String associated_bank) {
        this.associated_bank = associated_bank;
    }
    
}
