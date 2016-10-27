package com.zipingfang.aihuan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public class ValidateUtil {

	/**
	 * 验证号码 -手机号/固话均可
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 */
	public static boolean isPhoneOrNumber(String source) {
		String pattern = "((^((13[0-9])|(147)||(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		return isVerify(source, pattern);
	}

	/**
	 * 验证只能是数字或字母
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isNumOrString(String source) {
		String pattern = "^[A-Za-z0-9]+$";
		return isVerify(source, pattern);
	}

	/**
	 * 验证只能是数字
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isNum(String source) {
		String pattern = "^[0-9]+$";
		return isVerify(source, pattern);
	}

	/**
	 * 验证只能是字母
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isString(String source) {
		String pattern = "^[A-Za-z]+$";
		return isVerify(source, pattern);
	}

	/**
	 * 验证号码-手机号
	 * 13[0-9])|(147[0-8])||(15[0-9])
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isPhone(String source) {
		String pattern = "^((13[0-9])|(147)||(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
		return isVerify(source, pattern);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isEmail(String source) {
		String pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return isVerify(source, pattern);
	}

	/**
	 * 验证只能是数字或字母
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isPwd(String source) {
		String pattern = "^[A-Za-z0-9]{6,16}$";
		return isVerify(source, pattern) && !isContinuityCharacter(source) && !isEasyPwd(source);
	}
	private static boolean isContinuityCharacter(String str){
		boolean continuity = true;
		char[] data = str.toCharArray();
		for(int i=1; i<data.length; i++){
			String s1=data[i]+"";
			String s2=data[i-1]+"";
			if (!s1.equals(s2)) continuity = false;
		}
		return continuity;
	}
	private static boolean isEasyPwd(String str){
		return "123456".equals(str) ||"1234567".equals(str) || "12345678".equals(str) || "123456789".equals(str);
	}
	
	/**
	 * 验证身份证号码
	 * 
	 * @param source
	 *            号码
	 * @return 是否成功
	 * */
	public static boolean isCardNo(String source) {
		String pattern = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
		return isVerify(source, pattern);
	}

	/**
	 * 正则表达式验证
	 * 
	 * @param source
	 *            验证文本
	 * @param pattern
	 *            正则表达式
	 * @return 是否成功
	 */
	public static boolean isVerify(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}
}
