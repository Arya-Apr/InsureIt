/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.InsuranceService.InsuranceService.exceptionHandlers;

/**
 *
 * @author pcran
 */
public class BadCredsException extends RuntimeException {

    public BadCredsException(String message) {
        super(message);
    }
}
