package com.example.demo.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	
	// private : 同クラス内からのみアクセス許可
	// static : 複数のインスタンスを通して値を保持
	private static final String ALGORITHM = "AES";
	
	// ""内の数字は、String型の文字列リテラルであり、
	// Stringクラスのオフジェクトとして扱われる。
	// Stringクラスに属するメソッドを呼び出すことができる。
	// getBytes()はStringクラスのメソッド。
	// エンコードしてくれる。
	
	// UTF-8では、ASCII文字（英数字や基本的な記号）は1バイトでエンコードされます。
	// 各文字は1バイトで表現され、合計16バイトの配列になります。
	private static final byte[] KEY = "1234567890123456".getBytes();
	
	// ★パスワードをAESで暗号化する★
	// public : パッケージの異なるほかのすべてのクラスからアクセス可能。
	// デフォルトでは（public なしだと）同じパッケージ内のみアクセス可能
	// メソッド内で何らかのエラーが発生した場合、Exceptionを
	public static String encrypt(String value) throws Exception {
		
		// SecretKetSpecは暗号化アルゴリズムに使う秘密鍵を指定するためのクラス
		// SecretKeySpecはラッパーオブジェクト。
		// ラッパーオブジェクトと呼ぶ理由は、秘密鍵とアルゴリズム名をラップして
		// 暗号化や複合化の処理に使える形にするから。
		
		// ちなみに、staticメソッドがあるクラスは、インスタンス化せずにそのメソッド
		// が利用できる。つまり、SecreteySpecクラスにはstatic修飾子はない。
		// KEYやALGRORITHMに基づいて個別の鍵（インスタンス）を作成する必要があるため、
		// staticは不適な性質となる。
		SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
		
		// 指定されたアルゴリズムを使ったCipherオブジェクトを作成する。
		// Cipherクラスは、暗号化や複合を行うためのクラス
		// この行で、指定されたアルゴリズム（AES）に基づいたCipherオブジェクト
		// が作成される
		
		// CipherクラスのgetInstanceメソッドはstaticメソッドのため、インスタンス化が不要。
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		
		// Cipherオブジェクトを暗号化モード(ENCRYPT_MODE)で初期化し、
		// 先ほど作成したkeySpec（秘密鍵）を使用するよう設定
		// これにより暗号化の準備が整いました。
		// 初期化に使う情報は：ENCRYPT_MODE, KEY(秘密鍵), ALOGORITHM(=AES)の3つ。 
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		
		
		// 実際に暗号化処理を行う
		// value.getBytes()は、暗号化した文字列valueをバイト配列に変換します。
		// doFinal()はこのバイト配列を暗号化して、暗号化された結果をバイト配列として返す
		byte[] encrypted = cipher.doFinal(value.getBytes());
		
		
		// Base64.getEncoder()は、Base64エンコーディング用のエンコーダオブジェクト(Base64.Encoder)を返すメソッド。
		// encodeToString(encrypted)は、そのエンコーダオブジェクトのメソッドで
		// バイト配列(encrypted)をBase64エンコードし、文字列として返す。
		// つまり、この一行は、エンコーダオブジェクトを取得して、そのオブジェクトで
		// エンコードを行い、結果を文字列として返す一連の操作を行っている。
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	
//	// 以下。AESで暗号化されたパスワードを復号するコード(勉強のための備忘)
//	public static String decrypt(String encryptedValue) throws Exception {
//		
//		// ラッパーオブジェクトでKEYとALGORITHMを指定して、インスタンスを作成する。
//		SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
//		
//		
//		// ALGORITHMを指定した、オブジェクトを作成する
//		Cipher cipher = Cipher.getInstance(ALGORITHM);
//		
//		// Cipherオブジェクトを復号モードで初期化。
//		cipher.init(Cipher.DECRYPT_MODE, keySpec);
//		
//		// Base64形式の暗号化文字列をバイト配列にでコードする
//		byte[] decodedBytes = Base64.getDecoder().decode(encryptedValue);
//		
//		// でコードしたバイト配列をAESアルゴリズムで複合化し、元の文字列に戻す
//		byte[] decrypted = cipher.doFinal(decodedBytes);
//		
//		// decryptedはbyte[]型の配列。この配列は、AES復号処理によって得られた暗号化されたデータ
//		// の復号結果。
//		/// new String(decrypted)はこのバイト配列を文字列に変換する。Stringクラスのコンストラクタ
//		// はbyte[]を受け取って、それを文字列に変換する。
//		
//		// バイト配列decryptedを新しいStringオブジェクトに変換して、そのオブジェクトを
//		// 返している。
//		return new String(decrypted);
//	}
}
