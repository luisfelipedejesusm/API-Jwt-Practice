package com.luisfelipedejesusm.apicrudpractice.Repositories;

import com.luisfelipedejesusm.apicrudpractice.Models.Loan;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findByLoanNumber(String loanNumber);
}
