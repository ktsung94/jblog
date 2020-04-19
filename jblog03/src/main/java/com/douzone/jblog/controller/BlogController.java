package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;
import com.douzone.security.Auth;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String main(@PathVariable String id, Model model, @PathVariable Optional<Long> pathNo1, @PathVariable Optional<Long> pathNo2) {

		Long categoryNo = 0L;
		Long postNo = 0L;

		UserVo userVo = userService.getUserById(id);

		if(userVo == null) {
			return "error/404";
		}

		if( pathNo2.isPresent() ) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
		} 
		else if( pathNo1.isPresent() ){
			categoryNo = pathNo1.get();
			if(postService.findFirstPost(categoryNo) != null)
				postNo = postService.findFirstPost(categoryNo).getNo();
		} 
		else {
			categoryNo = categoryService.findFirstCategory(id).getNo();			
			if(postService.findFirstPost(categoryNo) != null)
				postNo = postService.findFirstPost(categoryNo).getNo();
		}

		PostVo postVo = postService.findPost(postNo);
		model.addAttribute("post", postVo);

		BlogVo vo = blogService.find(id);
		model.addAttribute("blog", vo);

		List<CategoryVo> list = categoryService.findAll(id);
		model.addAttribute("category", list);

		List<PostVo> posts = postService.findAll(categoryNo);
		model.addAttribute("posts", posts);

		return "blog/blog-main";
	}

	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable String id, Model model, HttpSession session) {	
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		BlogVo vo = blogService.find(id);
		model.addAttribute("blog", vo);
		return "blog/blog-admin-basic";
	}	

	//	@Auth
	//	@RequestMapping("/admin/category")
	//	public String adminCategory(@PathVariable String id, Model model) {
	//		BlogVo vo = blogService.find(id);
	//		model.addAttribute("blog", vo);
	//		List<CategoryVo> list = categoryService.findAll(id);		
	//
	//		for(int i = 0; i < list.size(); i++) {
	//			list.get(i).setPostCount(postService.findPostCount(list.get(i).getNo()));    
	//		}
	//
	//		model.addAttribute("list", list);
	//		return "blog/blog-admin-category";
	//	}

	// ajax
	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(Model model, @PathVariable String id) {
		BlogVo vo = blogService.find(id);
		model.addAttribute("blog", vo);
		return "blog/blog-admin-category";
	}


	@Auth
	@RequestMapping("/admin/write")
	public String adminWrite(@PathVariable String id, Model model) {
		BlogVo vo = blogService.find(id);
		model.addAttribute("blog", vo);
		List<CategoryVo> list = categoryService.findAll(id);
		model.addAttribute("category", list);
		return "blog/blog-admin-write";
	}

	@Auth
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String update(@PathVariable String id, @ModelAttribute BlogVo blogVo, @RequestParam(value = "logo-file") MultipartFile multipartFile, Model model) {
		String logo = "";
		if (multipartFile.isEmpty()) {
			logo = blogService.findlogo(id);
			System.out.println("=================" + logo);
		}
		else {
			logo = blogService.restore(multipartFile);
		}
		blogVo.setLogo(logo);		
		blogService.updateBlog(blogVo);

		return "redirect:/" + id;
	}

//	@RequestMapping(value="/insertCategory", method = RequestMethod.POST)
//	public String insertCategoty(@PathVariable String id, @ModelAttribute CategoryVo vo) {
//
//		vo.setBlogId(id);		
//		categoryService.insertCategory(vo);
//
//		return "redirect:/" + id + "/admin/category";
//	}

	@Auth
	@RequestMapping("/deleteCategory/{no}")
	public String deleteCategoty(@PathVariable String id, @PathVariable Long no) {

		if(categoryService.postCount(no) == 0 || categoryService.categoryCount(no) > 1)		
			categoryService.deleteCategory(no);

		return "redirect:/" + id + "/admin/category";
	}

	@Auth
	@RequestMapping("/insertPost")
	public String insertPost(@PathVariable String id, @ModelAttribute PostVo vo) {
		postService.insertPost(vo);


		return "redirect:/" + id + "/admin/category";
	}
}
