/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.service.dtos.PaymentRequestDto;
import com.service.service.models.Payment;
import com.service.service.repositories.PaymentRepository;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author Arya
 */
@Service
public class KafkaPaymentListenerService {

    @Autowired
    PaymentRepository paymentRepo;

    //working fine, to update try including only the total amount to the payment collection
    @KafkaListener(topics = "demoTopic", groupId = "groupId")
    void listener(ConsumerRecord<String, String> createPaymentInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentRequestDto request = mapper.readValue(createPaymentInput.value(), PaymentRequestDto.class);
        if (createPaymentInput.key().contains("AddPaymentFor:")) {
            for (int i = 1; i <= request.paymentDuration; i++) {
                Payment payment = new Payment();
                payment.setAuto_pay(request.auto_pay);
                payment.setId(UUID.randomUUID().toString());
                payment.setInterest(request.policyInterest);
                payment.setPaymentName("Premium " + i + " Payment For Policy: " + request.policy_id.toString());
                payment.setPaymentType(request.paymentType);
                payment.setPolicy_id(request.policy_id.toString());
                payment.setPrice(request.policyPrice);
                payment.setStatus("Pending");
                payment.setUser_email(request.user_email);
                paymentRepo.save(payment);
            }
        }
    }
}
