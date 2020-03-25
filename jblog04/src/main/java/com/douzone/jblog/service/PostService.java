package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public int insertPost(PostVo vo) {
		return postRepository.insert(vo);
	}

	public int findPostCount(Long no) {
		return postRepository.findPostCount(no);
	}

	public PostVo findFirstPost(Long no) {
		return postRepository.findFirstPost(no);
	}

	public PostVo findPost(Long postNo) {
		return postRepository.findPost(postNo);
	}

	public List<PostVo> findAll(Long categoryNo) {
		return postRepository.findAll(categoryNo);
	}

}
