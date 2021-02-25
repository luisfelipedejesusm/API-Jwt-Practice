package com.luisfelipedejesusm.apicrudpractice.Services;

import com.luisfelipedejesusm.apicrudpractice.Exceptions.ResourceNotFoundException;
import com.luisfelipedejesusm.apicrudpractice.Models.Loan;
import com.luisfelipedejesusm.apicrudpractice.Models.SavingAccount;
import com.luisfelipedejesusm.apicrudpractice.Models.User;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.PayLoanRequest;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.RequestLoanPayload;
import com.luisfelipedejesusm.apicrudpractice.Repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public void createLoan(RequestLoanPayload payload) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Loan loan = new Loan();
        Date date = new Date();
        User user = new User();
        user.setId(userDetails.getId());

        loan.setLoanNumber(userDetails.getId().toString() + date.getTime());
        loan.setLoanRate(payload.getRate());
        loan.setOriginalAmt(payload.getAmount());
        loan.setCurrentAmt( payload.getAmount() + payload.getAmount() * payload.getRate() );
        loan.setUser(user);

        loanRepository.save(loan);
    }

    public double payLoan(PayLoanRequest payload) {
        Loan loan = loanRepository.findByLoanNumber(payload.getLoanNumber());
        if(loan == null){
            throw new ResourceNotFoundException("No Saving Account Matches Account Number: " + payload.getLoanNumber());
        }
        double balance = loan.getCurrentAmt() - payload.getPayment();
        loan.setCurrentAmt(balance);
        loanRepository.save(loan);
        return balance;
    }
}
