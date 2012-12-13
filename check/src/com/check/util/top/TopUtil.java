package com.check.util.top;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import com.check.util.PPUtil;
import com.taobao.api.internal.util.WebUtils;

public class TopUtil extends TimerTask{

	@Override
	public void run() {
		refreshSessionKey();
	}
	public static Map<String, String> convertBase64StringtoMap(String str,
			String encode) {
		if (str == null)
			return null;
		if (encode == null)
			encode = "GBK";
		String keyvalues = null;
		try {
			keyvalues = new String(Base64.decodeBase64(str.getBytes(encode)),
					encode);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		String[] keyvalueArray = keyvalues.split("\\&");
		Map<String, String> map = new HashMap<String, String>();
		for (String keyvalue : keyvalueArray) {
			String[] s = keyvalue.split("\\=");
			if (s == null || s.length != 2)
				return null;
			map.put(s[0], s[1]);
		}
		return map;

	}

	public static String ParametersName(String top_parameters) {

		String ret = null;

		Map<String, String> map = convertBase64StringtoMap(top_parameters, null);

		Iterator keyValuePairs = map.entrySet().iterator();

		for (int i = 0; i < map.size(); i++) {

			Map.Entry entry = (Map.Entry) keyValuePairs.next();

			Object key = entry.getKey();

			Object value = entry.getValue();

			System.out.println((String) key + "==" + (String) value);
			if (key.equals("refresh_token")) {
				ret = (String) value;
			}

		}
		return ret;
	}

	public static void refreshSessionKey() {
		try {
			String appkey = PPUtil.getProp("app_key");
			String secret = PPUtil.getProp("app_secret");
			String refreshToken = ParametersName(PPUtil
					.getProp("top_parameters"));
			String sessionkey = PPUtil.getProp("session_sey");
			Map<String, String> signParams = new TreeMap<String, String>();
			signParams.put("appkey", appkey);
			signParams.put("refresh_token", refreshToken);
			signParams.put("sessionkey", sessionkey);
			StringBuilder paramsString = new StringBuilder();
			Set<Entry<String, String>> paramsEntry = signParams.entrySet();
			for (Entry paramEntry : paramsEntry) {
				paramsString.append(paramEntry.getKey()).append(
						paramEntry.getValue());
			}
			String sign = DigestUtils.md5Hex(
					(paramsString.toString() + secret).getBytes("utf-8"))
					.toUpperCase();
			String signEncoder = URLEncoder.encode(sign, "utf-8");
			String appkeyEncoder = URLEncoder.encode(appkey, "utf-8");
			String refreshTokenEncoder = URLEncoder.encode(refreshToken,
					"utf-8");
			String sessionkeyEncoder = URLEncoder.encode(sessionkey, "utf-8");
			String freshUrl = "http://container.api.taobao.com/container/refresh?appkey="
					+ appkeyEncoder
					+ "&refresh_token="
					+ refreshTokenEncoder
					+ "&sessionkey="
					+ sessionkeyEncoder
					+ "&sign="
					+ signEncoder;
			System.out.println(freshUrl);
			String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60,
					30 * 1000 * 60);
			System.out.println(response);
		} catch (UnsupportedEncodingException e) {

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		refreshSessionKey();
	}
}
