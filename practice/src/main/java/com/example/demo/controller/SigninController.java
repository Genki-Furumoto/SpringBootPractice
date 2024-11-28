package com.example.demo.controller;


import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// importすることで、クラスの使用を可能にする
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Signup;
import com.example.demo.form.SigninForm;
import com.example.demo.service.SigninService;

@Controller
public class SigninController {
	
	@Autowired
	private SigninService signinService;

	@GetMapping("/admin/signin")
	public String signin (Model model) {
		
		model.addAttribute("signinForm", new SigninForm());
		return "signin/signin";
	}
	
	
	@PostMapping("/admin/signin")
	// RedirectAttributesクラスのredirectAttributesインスタンスを引数に設定すると、Springが自動でDIを行う。@Auto
	public String singin(@ModelAttribute("signinForm") SigninForm signinForm, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		
		Signup user = signinService.findUser(signinForm);
		
		if(user != null) {
			
			session.setAttribute("loggedInUser", user);
			return "redirect:/admin/contacts";
		} else {
			
			//
			redirectAttributes.addFlashAttribute("error", "メールアドレスかパスワードが無効です。");
			return "redirect:/admin/signin";
		}
		
	}
}

