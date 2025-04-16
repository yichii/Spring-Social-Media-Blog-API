package com.example.service;

import java.util.List;
import java.util.Optional;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(Account account) {
        boolean accountTaken = accountRepository.existsByUsername(account.getUsername());
        if (account.getUsername().isBlank() && account.getPassword().length() < 4 && accountTaken) {
            return null;
        } 
        return accountRepository.save(account);
    }

    public Account getAccountByUsernameAndPassword(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null; 
        }
    }

}
