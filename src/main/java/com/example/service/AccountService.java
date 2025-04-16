package com.example.service;

import java.util.Optional;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if (optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isEmpty()) {
            return null;
        } 
        Account account = optionalAccount.get();
        System.out.println(account);

        if (!account.getPassword().equals(password)) {
            return null;
        }

        return account;
    }

}
