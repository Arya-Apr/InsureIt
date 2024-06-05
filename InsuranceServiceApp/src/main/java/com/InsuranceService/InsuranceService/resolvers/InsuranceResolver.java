/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceService.InsuranceService.resolvers;

import com.InsuranceService.InsuranceService.models.Insurance;
import com.InsuranceService.InsuranceService.repository.InsuranceRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * @author pcran
 */
@Controller
public class InsuranceResolver {

    @Autowired
    InsuranceRepository insuranceRepo;

    @QueryMapping
    public List<Insurance> getInsurance() {
        return insuranceRepo.findAll();
    }

}
