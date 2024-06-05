/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.services;

import com.InsuranceAggregatorFrontEnd.InsuranceAggregatorFrontEnd.models.InsuranceProvider;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Arya
 */
@Service
public class InsuranceProviderService {

    private final HttpGraphQlClient graphqlClient;

    public InsuranceProviderService() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8083/graphql").build();
        graphqlClient = HttpGraphQlClient.builder(client).build();
    }

    public Mono<InsuranceProvider> getInsuranceProvider(String providerId) {
        String document = """
                          query MyQuery($providerId: String!) {
                            getInsuranceProviderById(providerId: $providerId) {
                              _id
                              company_logo
                              company_name
                              providerId
                              username
                            }
                          }
                          """;
        return graphqlClient.document(document).variable("providerId", providerId).retrieve("etInsuranceProviderById").toEntity(InsuranceProvider.class);
    }
}
