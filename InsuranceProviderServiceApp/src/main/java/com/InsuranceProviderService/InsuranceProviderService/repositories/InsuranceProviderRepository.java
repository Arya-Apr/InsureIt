/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceProviderService.InsuranceProviderService.repositories;

import com.InsuranceProviderService.InsuranceProviderService.models.InsuranceProvider;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Arya
 */
public interface InsuranceProviderRepository extends MongoRepository<InsuranceProvider, ObjectId> {

    public InsuranceProvider getInsuranceProviderByProviderId(String providerId);
}
