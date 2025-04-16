package com.example.service;

import java.util.Optional;
import com.example.entity.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.repository.AccountRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Creates a new account if conditions are met(See below)
     * 
     * @param account The body will contain a representation of a JSON
     *                Account, but will not contain an accountId.
     * @return If all these conditions are met, the
     *         response body should contain a JSON of the Account, including its
     *         accountId. The response status should be 200 OK, which is the
     *         default. The new account should be persisted to the database.
     *         - If the registration is not successful due to a duplicate username,
     *         the response status should be 409. (Conflict)
     *         - If the registration is not successful for some other reason, the
     *         response status should be 400. (Client error)
     */
    public Account register(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(account.getUsername());
        if (optionalAccount.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return accountRepository.save(account);
    }

    /**
     * Verifies login(if account exists in the database that matches both username
     * and password.The request body will contain a JSON representation of
     * an Account.
     * 
     * @param username
     * @param password
     * @return The login will be successful if and only if the username and password
     *         provided in the request body JSON match a real account existing on
     *         the database. If successful, the response body should contain a JSON
     *         of
     *         the account in the response body, including its accountId. The
     *         response
     *         status should be 200 OK, which is the default.
     *         - If the login is not successful, the response status should be 401.
     *         (Unauthorized)
     */
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
        // Done
        return account;
    }
}
