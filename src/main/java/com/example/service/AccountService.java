package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) throws Exception {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());

        if (existingAccount != null) {
            throw new Exception("Username already taken");
        }

        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            throw new Exception("Username and password length requirment not met");
        }
      
        return accountRepository.save(account);
    }

    public Account login(Account account) throws Exception {
        Account loginAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());

        if (loginAccount == null) {
            throw new Exception("Account doesn't exist");
        }

        return loginAccount;
    }
}
