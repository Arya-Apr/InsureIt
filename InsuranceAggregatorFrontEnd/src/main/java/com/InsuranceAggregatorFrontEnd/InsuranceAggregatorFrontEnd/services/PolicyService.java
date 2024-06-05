/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.Policy;
import java.util.List;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Arya
 */
@Service
public class PolicyService {

    private final HttpGraphQlClient graphqlClient;

    public PolicyService() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8082/graphql").build();
        graphqlClient = HttpGraphQlClient.builder(client).build();
    }

    public Mono<List<Policy>> getPolicies() {
        String document = """
                query MyQuery {
                  getPolicies {
                    _id
                    averageMonthlyPremium
                    customerRating
                    id
                    insuranceProviderId
                    policyDescription
                    policyName
                    policyPrice
                    policyType
                    premiumDuration
                    totalRatings
                    usersEnrolled
                    policyInterest
                  }
                }
                """;
        return graphqlClient.document(document).retrieve("getPolicies").toEntityList(Policy.class);
    }

    public Mono<Policy> getPolicyById(String policyId) {
        String document = """
                            query MyQuery($policyId: String!) {
                              getPolicyById(policyId: $policyId ) {
                                _id
                                averageMonthlyPremium
                                customerRating
                                id
                                insuranceProviderId
                                policyDescription
                                policyInterest
                                policyName
                                policyPrice
                                policyType
                                premiumDuration
                                totalRatings
                                usersEnrolled
                              }
                            }
                          """;
        return graphqlClient.document(document).variable("policyId", policyId).retrieve("getPolicyById").toEntity(Policy.class);
    }
}
