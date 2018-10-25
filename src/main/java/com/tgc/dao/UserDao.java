package com.tgc.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tgc.entity.User;

public interface UserDao extends JpaRepository<User, Integer> {
}
