package com.emagsoftware.frame.utils;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AESUtil {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	private static Logger logger = Logger.getLogger(AESUtil.class);

	/**
	 * 密钥
	 */
	private static final String KEY = "migugongfang2017";

	/**
	 * 算法
	 */
	private static final String ALGORITHMSTR = "AES/CBC/NoPadding";

	public static String encrypt(String data) throws Exception {
		try {
			if (StringUtils.isNotBlank(data)) {
				String iv = KEY;
				Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
				int blockSize = cipher.getBlockSize();
				byte[] dataBytes = data.getBytes("utf-8");
				int plaintextLength = dataBytes.length;
				if (plaintextLength % blockSize != 0) {
					plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
				}
				byte[] plaintext = new byte[plaintextLength];
				System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
				SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes("utf-8"), "AES");
				IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("utf-8"));
				cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
				byte[] encrypted = cipher.doFinal(plaintext);
				String reval = Base64.encodeBytes(encrypted);
				logger.info("ACE加密成功");
				return reval;
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("ACE加密失败");
			return "";
		}
	}

	public static String desEncrypt(String data) throws Exception {
		try {
			if (StringUtils.isNotBlank(data)) {
				data = data.trim();
				String iv = KEY;
				byte[] encrypted = Base64.decode(data);
				Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
				SecretKeySpec keyspec = new SecretKeySpec(KEY.getBytes("utf-8"), "AES");
				IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes("utf-8"));
				cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
				byte[] original = cipher.doFinal(encrypted);
				String originalString = new String(original,"utf-8");
				logger.info("ACE解密成功");
				return originalString.trim();
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("ACE解密失败");
			return "";
		}
	}
	
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException{
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			//生成iv
			AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
				params.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, params);// 初始化
			return cipher.doFinal(content);
		}catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
