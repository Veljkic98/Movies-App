package com.movieserver.security;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

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

	public PublicKey getPublic(String filename) throws Exception {

		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");

		return kf.generatePublic(spec);
	}

	public String decryptText(String msg, PublicKey key)
			throws InvalidKeyException, UnsupportedEncodingException, 
			IllegalBlockSizeException, BadPaddingException {

		this.cipher.init(Cipher.DECRYPT_MODE, key);
        
		return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
	}

}
