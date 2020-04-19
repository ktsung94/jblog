package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.CategoryVo;

@RestController("BlogApiController")
@RequestMapping("/api/category")
public class BlogController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@GetMapping("/list/{id}")
	public JsonResult adminCategory(@PathVariable("id") String id) {
		List<CategoryVo> list = categoryService.findAll(id);
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setPostCount(postService.findPostCount(list.get(i).getNo()));    
		}
		return JsonResult.success(list);
	}

	@PostMapping("/add/{id}")
	public JsonResult add(@PathVariable String id, @RequestBody CategoryVo categoryVo) {
		categoryVo.setBlogId(id);
		categoryService.insertCategory(categoryVo);
		return JsonResult.success(categoryVo);
	}
	
	@DeleteMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") Long no) {
		int result = categoryService.deleteCategory(no);
		return JsonResult.success(result);
	}
}
