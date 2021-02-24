package com.luisfelipedejesusm.apicrudpractice.Payloads.Request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountDepositWithdrawPayload {
    @NotNull
    private String accountNumber;
    @NotNull
    private double amount;
}
