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

    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        if (accountRepository.existsById(accountId)) {
            return accountRepository.findAllMessagesByAccountId(accountId);
        } else {
            return null;
        }
    }
}
