package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVo;

@Repository
public class UserRespository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
	}

	public UserVo findVo(UserVo vo) {
		return sqlSession.selectOne("user.find", vo);
	}

	public UserVo getUserById(String id) {
		return sqlSession.selectOne("user.getUserById", id);
	}

}
