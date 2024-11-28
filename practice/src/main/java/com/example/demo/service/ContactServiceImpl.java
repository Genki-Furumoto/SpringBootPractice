package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.entity.Signup;
import com.example.demo.form.ContactForm;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.ContactRepository;
import com.example.demo.repository.SignupRepository;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
	@Autowired
	private SignupRepository signupRepository;
	
	@Override
	public void saveSignup(SignupForm signupForm) {
		Signup signup = new Signup();
		
		signup.setLastName(signupForm.getLastName());
		signup.setFirstName(signupForm.getFirstName());
		signup.setEmail(signupForm.getEmail());
		signup.setPassword(signupForm.getPassword());
		
		signupRepository.save(signup);
		
	}
	
	@Override
	public void saveContact(ContactForm contactForm) {
		
		// Entityクラス（DB用クラス）のインスタンスを作成し、
		// 一時保存。EntityクラスをまるごとcontactRepositoryという
		// リポジトリに保存する。
		
		// リポジトリはクラスではなく、インターフェイスで実装する
		// 実際の中身はJpaRepositoryである
		// JpaRepositoryは、Spring Data JPAによって提供されるインターフェイスで、
		// データベースとのやり取り(CRUD操作）を簡単に行うための便利なメソッドを
		// 持っている。これによりSQLを書かなくても、DB操作ができるようになる。
		Contact contact = new Contact();
		
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		contact.setCreatedAt(LocalDateTime.now());
		contact.setUpdateAt(LocalDateTime.now());
		
		
		// JpaRepository等を拡張して作成されるリポジトリ。
		// リポジトリはデータアクセスの役割を果たし、エンティティのデータを
		// データベースに保存したり取得したりする責任を持つ。
		contactRepository.save(contact);
	}
	
	public void updateContact(ContactForm contactForm, Contact contactForCreatedAt) {
		Contact contact = new Contact();
		
		contact.setId(contactForm.getId());
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		contact.setUpdateAt(LocalDateTime.now());
		
		// CreatedAtだけ、該当idに関して、MySQLから引っ張ってきて
		// contactにセッターを使って代入する。
		// これで、CreateAtは現状維持できる。

		contact.setCreatedAt(contactForCreatedAt.getCreatedAt());
		
		contactRepository.save(contact);
	}
	
	
	@Override
	public List<Contact> getAllContacts() {
		return contactRepository.findAll();
	}
	
	@Override
	public Contact getContactById(Long id) {
		return contactRepository.findById(id).orElse(null);
	}
	
	@Override
	public void deleteContactById(Long id) {
		contactRepository.deleteById(id);
	}
 }