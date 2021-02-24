package com.luisfelipedejesusm.apicrudpractice.Payloads.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SavingAccountPayload {
    private String accountNumber;
    private double balance;
}
