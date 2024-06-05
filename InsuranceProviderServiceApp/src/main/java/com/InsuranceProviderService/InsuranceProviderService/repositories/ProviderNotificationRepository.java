/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceProviderService.InsuranceProviderService.repositories;

import com.InsuranceProviderService.InsuranceProviderService.models.ProviderNotification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Arya
 */
public interface ProviderNotificationRepository extends MongoRepository<ProviderNotification, ObjectId> {

    public ProviderNotification findByUsername(String username);
}
