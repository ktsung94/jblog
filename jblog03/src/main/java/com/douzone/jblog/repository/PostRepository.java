package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;

	public int insert(PostVo vo) {
		return sqlSession.insert("post.insert", vo);
	}

	public int findPostCount(Long no) {
		return sqlSession.selectOne("post.count", no);
	}

	public PostVo findFirstPost(Long no) {
		return sqlSession.selectOne("post.findFirstPost", no);
	}

	public PostVo findPost(Long no) {
		return sqlSession.selectOne("post.findPost", no);
	}

	public List<PostVo> findAll(Long categoryNo) {
		return sqlSession.selectList("post.findAll", categoryNo);
	}
}
