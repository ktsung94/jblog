package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public int insertCategory(CategoryVo categoryVo) {
		return categoryRepository.insert(categoryVo);
	}

	public List<CategoryVo> findAll(String id) {
		return categoryRepository.findAll(id);
	}

	public int deleteCategory(Long no) {
		return categoryRepository.delete(no);
	}

	public Long findFirstCategoryNo(String id) {
		return categoryRepository.findFirstCategoryNo(id);
	}

	public CategoryVo findFirstCategory(String id) {
		return categoryRepository.findFirstCategory(id);
	}

	public int postCount(Long no) {
		return categoryRepository.postCount(no);
	}

	public int categoryCount(Long no) {
		return categoryRepository.categoryCount(no);
	}

}
