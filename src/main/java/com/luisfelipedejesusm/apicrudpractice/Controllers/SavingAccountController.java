package com.luisfelipedejesusm.apicrudpractice.Controllers;

import com.luisfelipedejesusm.apicrudpractice.Models.SavingAccount;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.AccountDepositWithdrawPayload;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.AccountOpeningPayload;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Response.SavingAccountPayload;
import com.luisfelipedejesusm.apicrudpractice.Services.SavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "/api/luimi/savingAccounts")
public class SavingAccountController {
    @Autowired
    private SavingAccountService savingAccountService;

    @PostMapping("accountOpening")
    public ResponseEntity<?> accountOpening(@Valid @RequestBody AccountOpeningPayload payload){
        savingAccountService.openAccount(payload);
        return ResponseEntity.ok("Account Created Successfully");
    }

    @PostMapping("accountDeposit")
    public ResponseEntity<?> accountDeposit(@Valid @RequestBody AccountDepositWithdrawPayload payload){
        double balance = savingAccountService.deposit(payload);
        return ResponseEntity.ok("Account accredited successfully. New Balance: " + balance);
    }

    @PostMapping("accountWithdrawal")
    public ResponseEntity<?> accountWithdraw(@Valid @RequestBody AccountDepositWithdrawPayload payload){
        double balance = savingAccountService.withdraw(payload);
        return ResponseEntity.ok("Account Withdrew Successfully. New Balance: " + balance);
    }

    @GetMapping
    public List<SavingAccountPayload> getAccountBalance(){
        return savingAccountService.getAccountBalance();
    }
}
