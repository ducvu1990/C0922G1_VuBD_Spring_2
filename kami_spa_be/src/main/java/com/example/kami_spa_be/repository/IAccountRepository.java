package com.example.kami_spa_be.repository;


import com.example.kami_spa_be.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a.* from account a join person e on a.person_id = e.id where e.email = ?1", nativeQuery = true)
    Account findAccountByEmployeeEmail(String username);

    @Modifying
    @Query(value = "update account set password =?1 where id =?2 ", nativeQuery = true)
    void saveNewPassword(String newPassword, Long id);
}
