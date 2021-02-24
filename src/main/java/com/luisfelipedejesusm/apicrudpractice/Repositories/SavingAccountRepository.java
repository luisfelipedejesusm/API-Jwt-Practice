package com.luisfelipedejesusm.apicrudpractice.Repositories;

import com.luisfelipedejesusm.apicrudpractice.Models.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
    SavingAccount findByAccountNumber(String accountNumber);

    List<SavingAccount> findByUserId(Long id);
}
