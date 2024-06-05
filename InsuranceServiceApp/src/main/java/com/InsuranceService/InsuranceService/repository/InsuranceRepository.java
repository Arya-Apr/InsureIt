/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceService.InsuranceService.repository;

import com.InsuranceService.InsuranceService.models.Insurance;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author pcran
 */
public interface InsuranceRepository extends MongoRepository<Insurance, ObjectId> {

}
