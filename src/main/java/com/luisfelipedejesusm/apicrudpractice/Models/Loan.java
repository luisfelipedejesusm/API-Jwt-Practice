package com.luisfelipedejesusm.apicrudpractice.Models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String loanNumber;

    private double originalAmt;

    private double currentAmt;

    private double loanRate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
