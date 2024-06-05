/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceBankService.InsuranceBankServiceApp.resolvers;

import com.InsuranceBankService.InsuranceBankServiceApp.inputTypes.CreateBankInput;
import com.InsuranceBankService.InsuranceBankServiceApp.models.Bank;
import com.InsuranceBankService.InsuranceBankServiceApp.repository.BankRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 *
 * @author pcran
 */
@Controller
public class BankResolver {

    @Autowired
    BankRepository bankRepository;

    @QueryMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public List<Bank> getBanks() {
        return bankRepository.findAll();
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Bank createBank(@Argument CreateBankInput createBankInput) {
        Bank bank = new Bank();
        bank.setId(UUID.randomUUID().toString());
        bank.setBank_name(createBankInput.bank_name);
        bank.setOfferedPolicies(createBankInput.offeredPolicies);
        bank.setInterestRate(createBankInput.interestRate);
        return bankRepository.save(bank);
    }
}
