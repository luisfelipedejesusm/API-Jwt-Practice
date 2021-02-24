package com.luisfelipedejesusm.apicrudpractice.Models;

import com.luisfelipedejesusm.apicrudpractice.Enums.ESavingAcc;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "savingAccounts")
public class SavingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String accountNumber;

    @NotNull
    private double balance;

    @Enumerated(EnumType.STRING)
    private ESavingAcc savingAccType;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
