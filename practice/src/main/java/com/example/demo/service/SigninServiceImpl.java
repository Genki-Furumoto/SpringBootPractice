package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Signup;
import com.example.demo.form.SigninForm;
import com.example.demo.repository.SignupRepository;
import com.example.demo.util.AESUtil;

@Service
public class SigninServiceImpl implements SigninService {
	
	@Autowired
	private SignupRepository signupRepository;
	
	
	@Override
	public Signup findUser(SigninForm signinForm) {
		String email = signinForm.getEmail();
		String password = null;
		
		try {
			
			// フォームに入力されたUTF-8の文字列を暗号化して、
			// Base64形式に変換する。
			password = AESUtil.encrypt(signinForm.getPassword());
		} catch (Exception e) {
			e.printStackTrace(); // エラーハンドリング
			return null;
		}
		
		// passwordはフォームに入力されたものをBase64形式の暗号に変換して
		// MySQLのパスワードと照合している。
		Signup user = signupRepository.findByEmailAndPassword(email, password);
		if(user != null) {
			// userがいれば、サインイン時刻をMySQLのadminsテーブルの
			// current_sign_atカラムの時刻を現在時刻に更新
			
			// サインイン成功の場合、現在時刻をセット
			user.setCurrentSingInAt(LocalDateTime.now());
			
			//サインイン時刻をデータベースに保存
			signupRepository.save(user);
		}
		
		return user;
		
	}
}