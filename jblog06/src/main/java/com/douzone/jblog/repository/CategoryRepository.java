package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(CategoryVo categoryVo) {
		return sqlSession.insert("category.insert", categoryVo);
	}

	public List<CategoryVo> findAll(String id) {
		return sqlSession.selectList("category.findall", id);
	}

	public int delete(Long no) {
		return sqlSession.delete("category.delete", no);
	}

	public Long findFirstCategoryNo(String id) {
		return sqlSession.selectOne("category.findFirstCategoryNo", id);
	}

	public CategoryVo findFirstCategory(String id) {
		return sqlSession.selectOne("category.findFirstCategory", id);
	}

	public int postCount(Long no) {
		return sqlSession.selectOne("category.postCount", no);
	}

	public int categoryCount(Long no) {
		return sqlSession.selectOne("category.categoryCount", no);
	}

}
