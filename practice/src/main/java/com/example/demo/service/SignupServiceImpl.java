package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Signup;
import com.example.demo.form.SignupForm;
import com.example.demo.repository.SignupRepository;
import com.example.demo.util.AESUtil;

@Service
public class SignupServiceImpl implements SignupService {
	
	@Autowired
	private SignupRepository signupRepository;
	
	@Override
	public void saveSignup(SignupForm signupForm) {
		Signup signup = new Signup();
		
		signup.setLastName(signupForm.getLastName());
		signup.setFirstName(signupForm.getFirstName());
		signup.setEmail(signupForm.getEmail());
		
		// 例外処理は以下の２パターンある。
		// ①try-catch構文：
		// tryで補足した例外をこのcatchで処理。
		// このクラス内で完結する。
		// 
		// ②throws構文
		// メソッド内で発生した例外を、メソッドの呼び出し元
		// ここでいうControllerクラスに例外処理を委譲する。
		// その委譲先でも、①か②を選ばなければならない。
		try {
			// パスワードを暗号化
			String encryptedPassword = AESUtil.encrypt(signupForm.getPassword());
			signup.setPassword(encryptedPassword);
		
		} catch (Exception e) {
			//例外処理（ログ出力や適切なエラーメッセージを返す）
			System.out.println("暗号化エラー：" + e.getMessage());
		}
		signupRepository.save(signup);
		
	}
}
	