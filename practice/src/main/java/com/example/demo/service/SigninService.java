package com.example.demo.service;

import com.example.demo.entity.Signup;
import com.example.demo.form.SigninForm;

public interface SigninService {
	
	Signup findUser(SigninForm signinForm);
}