package com.luisfelipedejesusm.apicrudpractice.Payloads.Request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RequestLoanPayload {
    private double amount;
    private double rate;
}
