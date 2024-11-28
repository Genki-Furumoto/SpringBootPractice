package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Signup;
import com.example.demo.service.ContactService;

@Controller
public class DashboardController {

	@Autowired
	private ContactService contactService;
	
	@GetMapping("/admin/contacts")
	public String dashboard(Model model, HttpSession session) {
		Signup user = (Signup) session.getAttribute("loggedInUser");
		
		if(user == null) {
			return "redirect:/admin/signin";
		}
		
		List<Contact> contacts = contactService.getAllContacts();
		model.addAttribute("contacts", contacts);

		
		return "dashboard/dashboard";
	}
	
	@GetMapping("/admin/contacts/signout")
	public String signout(HttpSession session) {
		
		session.removeAttribute("loggedInUser");
		return "redirect:/admin/signin";
	}
	
}
