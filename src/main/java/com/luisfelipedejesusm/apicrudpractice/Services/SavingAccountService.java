package com.luisfelipedejesusm.apicrudpractice.Services;

import com.luisfelipedejesusm.apicrudpractice.Enums.ESavingAcc;
import com.luisfelipedejesusm.apicrudpractice.Exceptions.ResourceNotFoundException;
import com.luisfelipedejesusm.apicrudpractice.Models.SavingAccount;
import com.luisfelipedejesusm.apicrudpractice.Models.User;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.AccountDepositWithdrawPayload;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Request.AccountOpeningPayload;
import com.luisfelipedejesusm.apicrudpractice.Payloads.Response.SavingAccountPayload;
import com.luisfelipedejesusm.apicrudpractice.Repositories.SavingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavingAccountService {
    @Autowired
    private SavingAccountRepository savingAccountRepository;

    public void openAccount(AccountOpeningPayload payload){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SavingAccount savingAccount = new SavingAccount();
        User user = new User();
        user.setId(userDetails.getId());
        savingAccount.setUser(user);
        savingAccount.setBalance(payload.getOpeningBalance());
        Date date = new Date();
        savingAccount.setAccountNumber(user.getId().toString() + date.getTime());
        savingAccount.setSavingAccType(payload.getAccountType());
        savingAccountRepository.save(savingAccount);
    }

    public double deposit(AccountDepositWithdrawPayload payload) {
        SavingAccount savingAccount = savingAccountRepository.findByAccountNumber(payload.getAccountNumber());
        if(savingAccount == null){
            throw new ResourceNotFoundException("No Saving Account Matches Account Number: " + payload.getAccountNumber());
        }
        double balance = savingAccount.getBalance() + payload.getAmount();
        savingAccount.setBalance(balance);
        savingAccountRepository.save(savingAccount);
        return balance;
    }

    public double withdraw(AccountDepositWithdrawPayload payload) {
        SavingAccount savingAccount = savingAccountRepository.findByAccountNumber(payload.getAccountNumber());
        if(savingAccount == null){
            throw new ResourceNotFoundException("No Saving Account Matches Account Number: " + payload.getAccountNumber());
        }
        if(!withdrawOperationValid(savingAccount, payload.getAmount())){
            throw new ResourceNotFoundException("User Balance is not enough to perform this operation");
        }
        double balance = savingAccount.getBalance() - payload.getAmount();
        savingAccount.setBalance(balance);
        savingAccountRepository.save(savingAccount);
        return balance;
    }

    private boolean withdrawOperationValid(SavingAccount account, double amount) {
        if(account.getSavingAccType() == ESavingAcc.SUPER_SAVING){
            if(account.getBalance() * 0.4 <= amount){
                return false;
            }
        }
        return true;
    }

    public List<SavingAccountPayload> getAccountBalance() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SavingAccount> savingAccounts = (List<SavingAccount>) savingAccountRepository.findByUserId(userDetails.getId());
        return savingAccounts.stream().map(this::createSavingAccountPayload).collect(Collectors.toList());
    }

    private SavingAccountPayload createSavingAccountPayload(SavingAccount savingAccount){
        return new SavingAccountPayload(savingAccount.getAccountNumber(), savingAccount.getBalance());
    }
}
