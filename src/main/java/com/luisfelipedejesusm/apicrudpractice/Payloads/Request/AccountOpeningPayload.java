package com.luisfelipedejesusm.apicrudpractice.Payloads.Request;

import com.luisfelipedejesusm.apicrudpractice.Enums.ESavingAcc;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountOpeningPayload {
    @NotNull
    private double openingBalance;
    @NotNull
    private ESavingAcc accountType;
}
