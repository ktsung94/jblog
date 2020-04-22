package com.douzone.jblog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.UserService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private CategoryService categoryService;

	private String successDefine = "false";

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo, HttpSession session) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null) {
			return "redirect:/";			
		}
		else {
			return "user/join";
		}
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model, @RequestParam(value = "define", required = true, defaultValue = "") String define) {
		System.out.println(define);
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		userService.insert(vo);
		// 프라이머리키를 가져온다. joinDate는 가져올수없다.
		// 가져오려면 다시 쿼리문을 짜야함

		blogService.insertBlog(vo.getId());

		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName("기타");
		categoryVo.setBlogId(vo.getId());
		categoryService.insertCategory(categoryVo);

		model.addAttribute("define", define);
		successDefine = define;
		if("true".equals(successDefine)) {
			System.out.println("join:" + define);
			return "user/joinsuccess";
		}
		else {
			return "redirect:/";
		}
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess(HttpSession session, Model model) {
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null || successDefine.equals("false")) {
			return "redirect:/";			
		}
		else {
			successDefine = "false";
			return "user/joinsuccess";
		}		
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpSession session) {	
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null) {
			return "redirect:/";			
		}
		else {
			return "user/login";
		}		
	}

}
