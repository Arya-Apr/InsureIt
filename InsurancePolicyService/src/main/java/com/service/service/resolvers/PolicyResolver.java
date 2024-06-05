/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.resolvers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.service.dtos.PaymentRequestDto;
import com.service.service.inputTypes.CreatePolicyInput;
import com.service.service.models.Policy;
import com.service.service.repository.PolicyRepository;
import java.util.List;
import java.util.UUID;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Arya
 */
@Controller
public class PolicyResolver {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private PolicyRepository policyRepo;

    @QueryMapping
    public List<Policy> getPolicies() {
        return policyRepo.findAll();
    }

    @QueryMapping
    public List<Policy> getPoliciesOfInsurance(@Argument String insuranceProviderId) {
        return policyRepo.findByInsuranceProviderId(insuranceProviderId);
    }

    @QueryMapping
    public Policy getPolicyById(@Argument ObjectId policyId) {
        return policyRepo.findById(policyId).get();
    }

    @QueryMapping
    public List<Policy> getPoliciesByPage(@Argument Integer size, @Argument Integer page) {
        if (page != null && size != null) {
            PageRequest p = PageRequest.of(page, size);
            return policyRepo.findAll(p).getContent();
        } else {
            return policyRepo.findAll();
        }
    }

    @MutationMapping
    @PreAuthorize("hasRole('INSURANCE_PROVIDER') OR hasRole('ADMIN')")
    public Policy createPolicy(@Argument CreatePolicyInput createPolicyInput) {
        Policy policy = new Policy();
        policy.setAverageMonthlyPremium(createPolicyInput.averageMonthlyPremium);
        policy.setCustomerRating(Float.MIN_VALUE);
        policy.setTotalRatings(0);
        policy.setId(UUID.randomUUID().toString());
        policy.setInsuranceProviderId(createPolicyInput.insuranceProviderId);
        policy.setPolicyDescription(createPolicyInput.policyDescription);
        policy.setPolicyName(createPolicyInput.policyName);
        policy.setPolicyPrice(createPolicyInput.policyPrice);
        policy.setPolicyType(createPolicyInput.policyType);
        policy.setPremiumDuration(createPolicyInput.premiumDuration);
        policy.setUsersEnrolled(createPolicyInput.usersEnrolled);
        policy.setPolicyInterest(createPolicyInput.policyInterest);
        return policyRepo.save(policy);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    public Policy rateAPolicy(@Argument Float rating, @Argument ObjectId policyId) {
        Policy policy = policyRepo.findById(policyId).orElseThrow();
        Integer oldTotal = policy.getTotalRatings();
        policy.setTotalRatings(policy.getTotalRatings() + 1);
        policy.setCustomerRating((policy.getCustomerRating() * oldTotal + rating) / policy.getTotalRatings());
        return policyRepo.save(policy);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN') OR hasRole('INSURANCE_PROVIDER')")
    public Policy addUserToPolicy(@Argument PaymentRequestDto paymentRequestDto) {
        Policy policy = policyRepo.findById(paymentRequestDto.policy_id).orElseThrow();
        List<String> existingUsers = policy.getUsersEnrolled();
        if (!existingUsers.contains(paymentRequestDto.getUser_email())) {
            if (existingUsers.add(paymentRequestDto.getUser_email())) {
                //here send a message to kafka topic to add payments
                policy.setUsersEnrolled(existingUsers);
                paymentRequestDto.setPaymentDuration(policy.getPremiumDuration());
                paymentRequestDto.setPolicyInterest(policy.getPolicyInterest());
                paymentRequestDto.setPolicyPrice(policy.getPolicyPrice());
                ObjectMapper mapper = new ObjectMapper();
                String jsonString;
                try {
                    jsonString = mapper.writeValueAsString(paymentRequestDto);
                    kafkaTemplate.send("demoTopic", "AddPaymentFor:" + paymentRequestDto.policy_id.toString(), jsonString);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return policyRepo.save(policy);
            } else {
                return policyRepo.save(policy);
            }
        } else {
            return policyRepo.save(policy);
        }
    }
}
