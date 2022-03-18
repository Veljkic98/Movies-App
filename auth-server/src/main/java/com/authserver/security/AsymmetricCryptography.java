package com.authserver.security;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.codec.binary.Base64;

public class AsymmetricCryptography {

    private Cipher cipher;

	public AsymmetricCryptography() throws NoSuchAlgorithmException, NoSuchPaddingException {

		this.cipher = Cipher.getInstance("RSA");
	}

    public PrivateKey getPrivate(String filename) throws Exception {

		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePrivate(spec);
	}

	public String encryptText(String msg, PrivateKey key) 
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException, 
			BadPaddingException, InvalidKeyException {

		this.cipher.init(Cipher.ENCRYPT_MODE, key);

		return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
	}

}
