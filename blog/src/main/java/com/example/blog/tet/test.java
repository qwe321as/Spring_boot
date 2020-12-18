package com.example.blog.tet;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.example.blog.model.RoleType;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;



//html파일이 아니라 데이터를 리턴한느 컨트롤러
@RestController
public class test {

	@Autowired //의존성 주입
	private UserRepository userRepository;
	
	@GetMapping("/dummy/users")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> paginguser=  userRepository.findAll(pageable);
		List<User> users =  paginguser.getContent();
		return users;
	}
	
	@DeleteMapping("dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "삭제 실패, 아이디:"+id+"는 데이터 베이스에 없습니다.";
		}
		return "삭제 성공 id:"+id;
	}
	
	@Transactional //함수 종료시 자동 커밋
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: "+id);
		System.out.println("password:"+requestUser.getPassword());
		User user = userRepository.findById(id).orElseThrow(()->{
		return new IllegalArgumentException("수정 실패");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail()); //수정하고자 하는것만 set으로 설정해준다.
		//더티 체킹
		
		//userRepository.save(user); //save 함수는 id를 전달하지 않으면 isnert를 해주고, 있으면 update id는 있구 데이터는 없으면 update
		return user;
	}
	

	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("email: "+user.getEmail());
		System.out.println("Role: "+user.getRole());

		user.setRole(RoleType.USER);
		userRepository.save(user);
		return"회원가입이 완료되었습니다";
	}

	//주소로 파라메터를 전달 받을수 있음
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//빨간줄 이유 데이터베이스에서 몾찾은경우 null 이 되서 return이  null이되서 문제생김 optional로 감싸서 가져옴
		//null인지 아닌지 판ㄴ단후 리턴해랏! .get()을 쓸경우 난 그럴리 없어! 하는것
		
		//람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 존재하지 않습니다.");
		});
		
		
		/*
		 * User user = userRepository.findById(id).orElseThrow(new
		 * Supplier<IllegalArgumentException>() {
		 * 
		 * @Override public IllegalArgumentException get() { return new
		 * IllegalArgumentException("해당 유저가 없습니다. id: "+id); } });
		 */

		//요청: 웹브라우저
		//user 객체 = 자바오브젝트 리턴
		//변환 (웹브라우저가 이해할 수 있는 데이터)->json
		//스프링 부트 = MessageConverter라는 애가 응답 시 자동작용
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 JackSon 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
}
