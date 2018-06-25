package com.common.utils.regex;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 正则检验工具类
 * 
 * @author SilverHu
 *
 */
public class RegexUtil {

	private RegexUtil() {

	}

	/**
	 * 手机号验证
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isMobile(String phone) {
		return isMatch("^[1][3,4,5,7,8][0-9]{9}$", phone);
	}

	/**
	 * 中文名校验
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isChineseName(String name) {
		if (StringUtils.isBlank(name) || name.indexOf("先生") != -1 || name.indexOf("小姐") != -1) {
			return false;
		}
		if (name.indexOf("女士") != -1 || name.indexOf("顾客") != -1) {
			return false;
		}
		return isMatch("^[\u4e00-\u9fa5]{2,12}$", name);// 中文字符：[\u4e00-\u9fa5]
	}

	/**
	 * 邮箱验证
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return isMatch("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", email);
	}

	/**
	 * 数字验证
	 * 
	 * @param orginal
	 * @return
	 */
	public static boolean isNumber(String orginal) {
		return isMatch("^(-?\\d+)(\\.\\d+)?$", orginal);
	}

	/**
	 * https协议校验
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isHttps(String url) {
		return isMatch("https?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
	}

	/**
	 * http协议校验
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url) {
		return isMatch("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", url);
	}

	/**
	 * 校验方法
	 * 
	 * @param regex
	 *            正则表达式
	 * @param orginal
	 *            校验值
	 * @return
	 */
	private static boolean isMatch(String regex, String orginal) {
		if (StringUtils.isBlank(regex) || StringUtils.isBlank(orginal)) {
			return false;
		}
		return Pattern.matches(regex, orginal);
	}
}
