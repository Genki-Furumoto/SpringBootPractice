package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

@Controller
@RequestMapping("/admin/contacts")
public class EdtiDashboardController {
	
	@Autowired
	private ContactService contactService;

	// PathVariableでは、detail_dashboard.htmlで読み込まれた、ModelオブジェクトのcontactById.idが読み取られる。
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable Long id, Model model, HttpServletRequest request) {
		
		Contact contactById = contactService.getContactById(id);
		model.addAttribute("contactById", contactById);
		
		// 削除用にここでセッションに一度保存する。
		HttpSession session = request.getSession();
		session.setAttribute("contactById", contactById);
		
		return "dashboard/edit";
	}
	
	
	// editテンプレートに、idのinputタグはない。しかし、URLパスに含まれているidが、
	// 送信リクエストに含まれ、@ModelAttirubteで自動的にContactFormにバインディングされる。
	@PostMapping("/{id}/edit")
	public String edit(@PathVariable Long id, @Validated @ModelAttribute("contactById") ContactForm contactById, BindingResult errorResult, HttpServletRequest request) {
	
		if(errorResult.hasErrors()) {
			return "dashboard/edit";
		}
		
		// redirectした先では、Modelのデータは保持できない。
		// だからsessionを使う。
		// 編集版のセッションを上書き保存する。
		HttpSession session = request.getSession();
		session.setAttribute("contactById", contactById);
		
		// idはPathVariableで受け取っている。この時点でcontactByIdには格納されていない。
		return "redirect:/admin/contacts/" + id +  "/edit/confirm";
	}
	
	@GetMapping("/{id}/edit/delete")
	public String delete(Model model, @PathVariable Long id, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Contact contactById = (Contact) session.getAttribute("contactById");
		model.addAttribute("contactById", contactById);
		
		contactService.deleteContactById(id);
		return "dashboard/delete_completion";
		
	}
	
	@GetMapping("/{id}/edit/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		ContactForm contactById = (ContactForm) session.getAttribute("contactById");
		
		model.addAttribute("contactById", contactById);
		
		return "dashboard/confirmation";
	}
	
	@PostMapping("/{id}/edit/register")
	public String register(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ContactForm contactById = (ContactForm) session.getAttribute("contactById");
		
		Contact contactForCreatedAt = contactService.getContactById(contactById.getId());
		contactService.updateContact(contactById, contactForCreatedAt);
		
		return "redirect:/admin/contacts/edit/complete";
	}
	
	@GetMapping("/edit/complete")
	public String complete(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		ContactForm contactById = (ContactForm) session.getAttribute("contactById");
		model.addAttribute("contactById", contactById);
		
		session.invalidate();
		
		return "dashboard/completion";
	}
	
	
}
