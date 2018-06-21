package com.emagsoftware.frame.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class WXBizDataCrypt {
	
	public static String illegalAesKey = "-41001";//非法密钥
	public static String illegalIv = "-41002";//非法初始向量
	public static String illegalBuffer = "-41003";//非法密文
	public static String decodeBase64Error = "-41004"; //解码错误
	public static String noData = "-41005"; //数据不正确

	private String appid;

	private String sessionKey;

	public WXBizDataCrypt(String appid, String sessionKey) {
		this.appid = appid;
		this.sessionKey = sessionKey;
	}

	/**
	 * 检验数据的真实性，并且获取解密后的明文.
	 * @param encryptedData  string 加密的用户数据
	 * @param iv  string 与用户数据一同返回的初始向量
	 * @return data string 解密后的原文
	 * @return String 返回用户信息
	 */
	public String decryptData(String encryptedData, String iv) {
		if (StringUtils.length(sessionKey) != 24) {
			return illegalAesKey;
		}
		// 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
		byte[] aesKey = Base64.decodeBase64(sessionKey);

		if (StringUtils.length(iv) != 24) {
			return illegalIv;
		}
		// 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
		byte[] aesIV = Base64.decodeBase64(iv);

		// 对称解密的目标密文为 Base64_Decode(encryptedData)
		byte[] aesCipher = Base64.decodeBase64(encryptedData);

		try {
			byte[] resultByte = AESUtil.decrypt(aesCipher, aesKey, aesIV);
			if (null != resultByte && resultByte.length > 0) {
				
				String userInfo = new String(resultByte, "UTF-8");
				JSONObject jsons = JSON.parseObject(userInfo);
				String id = jsons.getJSONObject("watermark").getString("appid");
				if (!StringUtils.equals(id, appid)) {
					return illegalBuffer;
				}
				return userInfo;
			} else {
				return noData;
			}
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * encryptedData 和 iv 两个参数通过小程序wx.getUserInfo()方法获取
	 * @param args
	 * @see
	 */
	public static void main(String[] args) {
		String appId = "wx4f4bc4dec97d474b";
		String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
		String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"
				+ "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"
				+ "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"
				+ "3hVbJSRgv+4lGOETKUQz6OYStslQ142d"
				+ "NCuabNPGBzlooOmB231qMM85d2/fV6Ch"
				+ "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"
				+ "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"
				+ "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"
				+ "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"
				+ "8LOddcQhULW4ucetDf96JcR3g0gfRK4P"
				+ "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"
				+ "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"
				+ "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"
				+ "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"
				+ "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"
				+ "20f0a04COwfneQAGGwd5oa+T8yO5hzuy"
				+ "Db/XcxxmK01EpqOyuxINew==";
		String iv = "r7BXXKkLb8qrSNn05n0qiA==";

		//WXBizDataCrypt biz = new WXBizDataCrypt(appId, sessionKey);

		//System.out.println(biz.decryptData(encryptedData, iv));
		
	}
}
