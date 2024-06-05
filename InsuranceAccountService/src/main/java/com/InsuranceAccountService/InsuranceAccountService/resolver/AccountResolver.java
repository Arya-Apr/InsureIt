/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAccountService.InsuranceAccountService.resolver;

import com.InsuranceAccountService.InsuranceAccountService.inputTypes.CreateAccountInput;
import com.InsuranceAccountService.InsuranceAccountService.models.Account;
import com.InsuranceAccountService.InsuranceAccountService.models.User;
import com.InsuranceAccountService.InsuranceAccountService.repositories.AccountRepository;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Arya
 */
@Controller
public class AccountResolver {

    private final PasswordEncoder passwordEncoder;

    public AccountResolver(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    AccountRepository accountRepo;

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @QueryMapping
    public List<Account> getAccounts() {
        return accountRepo.findAll();
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @MutationMapping
    public Account createAccount(@Argument CreateAccountInput createAccountInput, DataFetchingEnvironment env) {
        Account account = new Account();
        GraphQLContext ctx = env.getGraphQlContext();
        SecurityContext securityContext = (SecurityContext) ctx.get("org.springframework.security.core.context.SecurityContext");
        User user = (User) securityContext.getAuthentication().getPrincipal();
        account.setAccount_id(UUID.randomUUID().toString());
        account.setAccount_holder(user.getUsername());
        account.setAssociated_bank(createAccountInput.associated_bank);
        account.setBalance(createAccountInput.balance);
        account.setPassword(passwordEncoder.encode(createAccountInput.password));
        return accountRepo.save(account);
    }
}
