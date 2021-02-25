package com.luisfelipedejesusm.apicrudpractice.Controllers;

import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.PayLoanRequest;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.RequestLoanPayload;
import com.luisfelipedejesusm.apicrudpractice.Services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/api/luimi/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("requestLoan")
    public ResponseEntity<?> requestLoan(@Valid @RequestBody RequestLoanPayload payload){
        loanService.createLoan(payload);
        return ResponseEntity.ok("Loan Created Successfully");
    }

    @PostMapping("payLoan")
    public ResponseEntity<?> payLoanResponse(@Valid @RequestBody PayLoanRequest payload){
        double balance = loanService.payLoan(payload);
        return ResponseEntity.ok("Loan Payment Successfully Performed. Remaining Balance: " + balance);
    }
}
