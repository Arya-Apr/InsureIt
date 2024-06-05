/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceProviderService.InsuranceProviderService.resolver;

import com.InsuranceProviderService.InsuranceProviderService.exceptionHandler.UserNotFoundException;
import com.InsuranceProviderService.InsuranceProviderService.inputTypes.CreateInsuranceProviderInput;
import com.InsuranceProviderService.InsuranceProviderService.models.InsuranceProvider;
import com.InsuranceProviderService.InsuranceProviderService.models.ProviderNotification;
import com.InsuranceProviderService.InsuranceProviderService.models.User;
import com.InsuranceProviderService.InsuranceProviderService.repositories.InsuranceProviderRepository;
import com.InsuranceProviderService.InsuranceProviderService.repositories.ProviderNotificationRepository;
import com.InsuranceProviderService.InsuranceProviderService.repositories.UserRepository;
import graphql.GraphQLContext;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Arya
 */
@Controller
public class InsuranceProviderResolver {

    @Autowired
    InsuranceProviderRepository insuranceProviderRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProviderNotificationRepository providerNotificationRepo;

    @QueryMapping
    public List<InsuranceProvider> getInsuranceProvider() {
        return insuranceProviderRepo.findAll();
    }

    @QueryMapping
    public InsuranceProvider getInsuranceProviderById(@Argument String providerId) {
        return insuranceProviderRepo.getInsuranceProviderByProviderId(providerId);
    }

    @MutationMapping
    @PreAuthorize("hasRole('INSURANCE_PROVIDER') OR hasRole('ADMIN')")
    public InsuranceProvider createInsuranceProvider(@Argument CreateInsuranceProviderInput createInsuranceProviderInput, DataFetchingEnvironment env) {
        GraphQLContext ctx = env.getGraphQlContext();
        SecurityContext securityContext = (SecurityContext) ctx.get("org.springframework.security.core.context.SecurityContext");
        User user = (User) securityContext.getAuthentication().getPrincipal();
        InsuranceProvider insuranceProvider = new InsuranceProvider();
        insuranceProvider.setProviderId(UUID.randomUUID().toString());
        insuranceProvider.setUsername(user.getEmail());
        insuranceProvider.setCompany_logo(createInsuranceProviderInput.company_logo);
        insuranceProvider.setCompany_name(createInsuranceProviderInput.company_name);
        return insuranceProviderRepo.save(insuranceProvider);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public boolean handleInsuranceProviderRequest(@Argument ObjectId user_id, @Argument List<String> roles) {
        User user = userRepo.findById(user_id).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        ProviderNotification notification = providerNotificationRepo.findByUsername(user.getUsername());
        user.setRoles(roles);
        try {
            userRepo.save(user);
            providerNotificationRepo.delete(notification);
            //here pass the event for the same subscription that the admin has taken an action in the notification model(will be done by the subscription)
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public boolean requestForInsuranceProvider(@Argument ObjectId userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User Not Found!"));
        ProviderNotification notification = new ProviderNotification();
        notification.setNotification_id(UUID.randomUUID().toString());
        notification.setUsername(user.getEmail());
        try {
            providerNotificationRepo.save(notification);
            //here pass the subscription event that a user has sent a notification(will be fetched by the subscription)
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @SubscriptionMapping("getNotifications")
    public Publisher<List<ProviderNotification>> getNotifications() {
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            List<ProviderNotification> notifications = providerNotificationRepo.findAll();
            subscriber.onNext(notifications);
        }, 0, 2, TimeUnit.SECONDS);
    }
}
