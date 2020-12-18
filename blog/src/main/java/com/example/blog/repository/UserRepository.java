package com.example.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.blog.model.User;

//DAO //자동 빈 등록이 가능하다. 
public interface UserRepository extends JpaRepository<User, Integer> {
	
	//select * from user werhe username = ?;
	Optional<User> findByUsername(String username);
	
}

//jpa naming 전략 전통적인 방식의 로그인!
//select * from user where username = ? AND password = ? 자동으로 만들어짐 대문자에 유의 하여 작성 
// @Query(value = "select * from user where username = ? AND password = ? ",
// nativeQuery = true) User login(String username, String password);
//User findByUsernameAndPassword(String username, String password);