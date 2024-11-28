package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	
	// findAllでないと、JPAの命名規則外だから動作しない。
	// 以下はJPAによって提供されるメソッドだから宣言する必要はない。
	//	List<Contact> findAll();
	
	
}