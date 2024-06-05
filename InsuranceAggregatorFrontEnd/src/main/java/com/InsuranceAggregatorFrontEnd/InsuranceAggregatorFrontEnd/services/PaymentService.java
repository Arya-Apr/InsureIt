/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.StripeRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.ClientGraphQlResponse;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Arya
 */
@Service
public class PaymentService {

    @Value("${stripe.api.publicKey}")
    private String publicKey;
    private final HttpGraphQlClient graphqlClient;

    public PaymentService() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8081/graphql").build();
        graphqlClient = HttpGraphQlClient.builder(client).build();
    }

    public Mono<String> handlePayment(StripeRequest stripeRequest, HttpServletRequest request, Model model) {
        String document = """
                            mutation MyMutation($price: Float!, $user_email: String!, $policy_id: String!, $paymentName: String!) {
                              createStripePayment(stripeRequest: {
                                price: $price,
                                user_email: $user_email,
                                policy_id: $policy_id,
                                paymentName: $paymentName
                          }) {
                                clientSecret
                                intentID
                              }
                            }
                          """;
        Mono<ClientGraphQlResponse> res = graphqlClient.document(document).variable("price", stripeRequest.getPrice()).variable("user_email", stripeRequest.getUser_email()).variable("policy_id", stripeRequest.getPolicy_id()).variable("paymentName", stripeRequest.getPaymentName()).execute();
        return res.doOnNext(resp -> {
            LinkedHashMap<String, Object> response = resp.getData();
            LinkedHashMap<String, Object> login = (LinkedHashMap<String, Object>) response.getOrDefault("data", response);
            LinkedHashMap<String, Object> main = (LinkedHashMap<String, Object>) login.getOrDefault("createStripePayment", response);
            String clientSecret = (String) main.get("clientSecret").toString();
            String intentId = (String) main.get("intentID").toString();
            model.addAttribute("clientSecret", clientSecret);
            model.addAttribute("intentId", intentId);
            model.addAttribute("publicKey", publicKey);
            model.addAttribute("price", stripeRequest.getPrice());
            model.addAttribute("user_email", stripeRequest.getUser_email());
            model.addAttribute("paymentName", stripeRequest.getPaymentName());
            model.addAttribute("policy_id", stripeRequest.getPolicy_id());
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("jwtToken")) {
                        if (!cookie.getValue().isEmpty()) {
                            model.addAttribute("jwtToken", cookie.getValue());
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }).thenReturn("checkout");
    }
}
