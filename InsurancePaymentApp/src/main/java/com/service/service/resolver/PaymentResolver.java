/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.resolver;

import com.service.service.inputTypes.CreatePaymentInput;
import com.service.service.inputTypes.StripeRequest;
import com.service.service.inputTypes.StripeResponse;
import com.service.service.models.Payment;
import com.service.service.models.User;
import com.service.service.repositories.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
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
import org.springframework.stereotype.Controller;

/**
 *
 * @author Arya
 */
@Controller
public class PaymentResolver {

    @Autowired
    PaymentRepository paymentRepo;

    @QueryMapping
    public List<Payment> getPayments() {
        return paymentRepo.findAll();
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Payment createPayment(@Argument CreatePaymentInput createPaymentInput, DataFetchingEnvironment env) {
        GraphQLContext ctx = env.getGraphQlContext();
        SecurityContext securityContext = (SecurityContext) ctx.get("org.springframework.security.core.context.SecurityContext");
        User user = (User) securityContext.getAuthentication().getPrincipal();
        Payment payment = new Payment();
        payment.setAuto_pay(createPaymentInput.auto_pay);
        payment.setId(UUID.randomUUID().toString());
        payment.setInterest(createPaymentInput.interest);
        payment.setPaymentName(createPaymentInput.paymentName);
        payment.setPaymentType(createPaymentInput.paymentType);
        payment.setPolicy_id(createPaymentInput.policy_id);
        payment.setPrice(createPaymentInput.price);
        payment.setStatus(createPaymentInput.status);
        payment.setUser_email(user.getEmail());
        return paymentRepo.save(payment);
    }

    @MutationMapping
    public StripeResponse createStripePayment(@Argument StripeRequest stripeRequest) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(stripeRequest.getPrice().longValue() * 100L)
                .putMetadata("Payment Name", stripeRequest.getPaymentName())
                .putMetadata("Payee", stripeRequest.getUser_email())
                .setCurrency("inr")
                .setDescription("Some description....")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                ).build();
        PaymentIntent intent = PaymentIntent.create(params);
        return new StripeResponse(intent.getId(), intent.getClientSecret());
    }
}
