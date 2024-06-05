/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceBankService.InsuranceBankServiceApp.repository;

import com.InsuranceBankService.InsuranceBankServiceApp.models.Bank;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author pcran
 */
public interface BankRepository extends MongoRepository<Bank, ObjectId> {

}
