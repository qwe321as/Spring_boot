package com.example.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.blog.model.Board;
import com.example.blog.model.User;
import com.example.blog.repository.BoardRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록해줌. IoC를 해준다.
@Service 
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
}
