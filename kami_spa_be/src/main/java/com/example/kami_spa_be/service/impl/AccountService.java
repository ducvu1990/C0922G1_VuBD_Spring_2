package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.Account;
import com.example.kami_spa_be.repository.IAccountRepository;
import com.example.kami_spa_be.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    /**
     * HoangNM
     */
    @Autowired
    IAccountRepository iAccountRepository;

    /**
     * HoangNM
     */
    @Override
    public Account findAccountByEmployeeEmail(String username) {
        return iAccountRepository.findAccountByEmployeeEmail(username);
    }

    /**
     * HoangNM
     */
    @Override
    public void saveNewPassword(String newPassword,Long accountId) {
        iAccountRepository.saveNewPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)),accountId);
    }

    /**
     * HoangNM
     */
    @Override
    public boolean checkPassword(String password, String oldPassword) {
        return BCrypt.checkpw(password,oldPassword);
    }


}
