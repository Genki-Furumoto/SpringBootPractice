package com.example.demo.service;

import com.example.demo.form.SignupForm;


// インターフェイスでは、メソッドのシグネチャである
// メソッド名、引数、戻り値の型だけを定義する

// なぜこうするのか
// 　依存性の注入：コントローラ、サービス層などでインターフェイス
// 　を使って、依存性の注入を行う。これにより、実装の変更が容易に
// 　なり、柔軟な設計が可能になる。

//　 柔軟性と拡張：ContactServiceImpleという具体的なクラスに依存せず、
// 　ContactServiceというインターフェイスに”依存する”ことで、
// 　他の実装クラスに簡単に差し替えられるから。

public interface SignupService {
	void saveSignup(SignupForm signupForm);
}