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
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ContactForm;
import com.example.demo.service.ContactService;

@Controller
public class ContactController {
	
	// ServiceクラスをDIする
	@Autowired
	private ContactService contactService;
	
	
	@GetMapping("/contact")
	// Modelオブジェクトは、Spring特有のコンテナ
	// 初期化をせずにもともと使える。
	// データがある場合、ない場合をまとめていきなり引数に
	// 設定されているのだと思う。
	public String contact(Model model) {
		model.addAttribute("contactForm", new ContactForm());
		
		return "/contact/contact";
	}
	
	@PostMapping("/contact")
	// ModelAttribute("contactForm") ContactForm contactFormとは、
	// リクエストパラメータをモデル内にcontactFormというキーで追加します。
	// また、リクエストらメーターは、ContactForm型のcontactFormにバインドされる。
	// すなわち、このModelAttributeｈ、パラメータをモデルと変数両方にバ追加している。
	public String contact(@Validated @ModelAttribute("contactForm") ContactForm contactForm, BindingResult errorResult, HttpServletRequest request) {
		
		// エラーがあれば、そのままcontactの内容をビューに返す。（エラーメッセつき）
		if(errorResult.hasErrors()) {
			return "contact/contact";
		}
		
		// セッションを取得
		HttpSession session = request.getSession();
		// contactForm（Postバインディング内容）をセッションのcontactForm属性に保存
		session.setAttribute("contactForm", contactForm);
		
		return "redirect:/contact/confirm";
	}
	
	@GetMapping("/contact/confirm")
	public String confirm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		// セッションのcontactForm属性から、内容をContactForm型にキャストし、
		// contactFormオブジェクトに代入
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		
		model.addAttribute("contactForm", contactForm);
		
		return "contact/confirmation";
	}
	
	@PostMapping("/contact/register")
	public String register(Model model, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		
		contactService.saveContact(contactForm);
		
		return "redirect:/contact/complete";
	}
	
	
	// completeリダイレクト時、ビューに渡される前の処理
	@GetMapping("/contact/complete")
	public String complete(Model model,HttpServletRequest request) {
		
		// falseの場合、現在存在しているセッションだけを返し
		// 新しいセッションは作成しない。セッションがなければ
		// nullを返す
		if(request.getSession(false) == null) {
 			
			// redirect：クライアント（ブラウザ）側に新しいリクエストを発行させる。
			// URLが変更される
			// ビュー名("contact)：サーバー側でそのビューをレンダリングし、ブラウザに表示する。
			// 単純なビューの表示であれば、return "contact"で十分。
			return "redirect:/contact";
		}
		
		
		// なぜセッションからModelへ移すのか？
		// Modelオブジェクトは,Spring MVCでビューにデータを渡すための仕組み。
		// addAttributeメソッドを使って、contactFormという名前でテンプレート（ビュー）に
		// データを送ることで、テンプレートエンジンでHTML内でcontactFormの情報が表示できる。
		HttpSession session = request.getSession();
		ContactForm contactForm = (ContactForm) session.getAttribute("contactForm");
		model.addAttribute("contactForm", contactForm);
		
		// セッションを無効かする。ログアウト処理などでよく使う。
		session.invalidate();
		
		return "contact/completion";
	}
	

}