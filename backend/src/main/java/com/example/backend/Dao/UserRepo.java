package com.example.backend.Dao;

import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

@Transactional
public interface UserRepo extends JpaRepository<User,Integer> {

    User findByUsername(String username);

}
