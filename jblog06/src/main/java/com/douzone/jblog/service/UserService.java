package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserRespository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRespository userRepository;
	
	public boolean insert(UserVo vo) {
		int count = userRepository.insert(vo);
		return count == 1;
	}

	public UserVo find(UserVo vo) {
		return userRepository.findVo(vo);
	}

	public UserVo getUserById(String id) {
		return userRepository.getUserById(id);
	}
}
