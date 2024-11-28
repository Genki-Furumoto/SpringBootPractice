package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long>{
    
    
    // メールアドレスとパスワードでユーザーを検索
    Signup findByEmailAndPassword(String email, String password);

}
