package com.coderscampus.assignment13.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	List<Account> findByUsers(Optional<User> user);

	List<Account> findByAccountId(Long accountId);
}
