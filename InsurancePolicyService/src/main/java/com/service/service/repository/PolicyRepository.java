/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.service.service.repository;

import com.service.service.models.Policy;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Arya
 */
public interface PolicyRepository extends MongoRepository<Policy, ObjectId> {

    public List<Policy> findByInsuranceProviderId(String insuranceProviderId);
}
