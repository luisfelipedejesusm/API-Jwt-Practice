package com.luisfelipedejesusm.apicrudpractice.Payloads.Request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PayLoanRequest {
    @NotNull
    private String loanNumber;

    @NotNull
    private double payment;
}
