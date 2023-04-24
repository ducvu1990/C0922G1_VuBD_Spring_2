package com.example.kami_spa_be.service;


import com.example.kami_spa_be.model.Account;

public interface IAccountService {
    /**
     * HoangNM
     */
    Account findAccountByEmployeeEmail(String username);

    /**
     * HoangNM
     */
    void saveNewPassword(String newPassword,Long accountId);

    /**
     * HoangNM
     */
    boolean checkPassword(String password, String oldPassword);
}
