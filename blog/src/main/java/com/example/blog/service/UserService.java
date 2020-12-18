package com.example.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록해줌. IoC를 해준다.
@Service 
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //비밀번호 원문
		String encPassword = encoder.encode(rawPassword); //해쉬 비밀번호
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
}

/* 전통적인 방식의 로그인
 * @Transactional(readOnly = true) //select할떄 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
 * public User 로그인(User user) { return
 * userRepository.findByUsernameAndPassword(user.getUsername(),
 * user.getPassword());}
 */