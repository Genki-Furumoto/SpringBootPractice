package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;

@Controller
// このコントローラーのすべてのメソッドは/adimin/contactsで始まるURLパスに
// マッピングされる。
@RequestMapping("/admin/contacts")
public class DetailDashboardController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/{id}")
	public String showDetailDashboard(@PathVariable Long id, Model model, HttpSession session) {
		// ここでidに基づいた処理を実行
		Contact contactById = contactService.getContactById(id);
		model.addAttribute("contactById",  contactById);
		
		return "dashboard/detail";
	}
			

}
