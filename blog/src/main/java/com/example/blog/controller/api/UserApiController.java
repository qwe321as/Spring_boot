package com.example.blog.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.dto.ResponseDto;
import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { 
		System.out.println("UserApi컨트롤러 호출됨 : save 호출됨");
		//실제로 DB에 insert하고 아래에서 return
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	

}

/* 전통적인 방식의 로그인 구현하기 
 * @PostMapping("/api/user/login") public ResponseDto<Integer>
 * login(@RequestBody User user, HttpSession session){
 * System.out.println("UserApiController: login호출됨" ); User principal =
 * userService.로그인(user); //principal <-접근주체 if (principal != null) {
 * session.setAttribute("principal", principal); } return new
 * ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 */