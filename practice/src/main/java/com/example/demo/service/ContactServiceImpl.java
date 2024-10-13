package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private ContactRepository contactRepository;
	
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
		
		
		// JpaRepository等を拡張して作成されるリポジトリ。
		// リポジトリはデータアクセスの役割を果たし、エンティティのデータを
		// データベースに保存したり取得したりする責任を持つ。
		contactRepository.save(contact);
	}
}