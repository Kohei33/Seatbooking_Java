package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserRepository {    
    User findByUsername(String username);
}
