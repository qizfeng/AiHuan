package com.zipingfang.aihuan.receiver;

import android.content.Context;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 在欢迎页面或首页(WelcomeActivity)设置
 * 
 * <p>
 * 1.配置AndroidManifest.xml文件</br>
 * 2.自定义Application的onCreate()方法中调用JPushUtil.init()初始化极光推送</br>
 * 3.1在在欢迎页面或首页(WelcomeActivity)的onResume中加入JPushUtil.onResume()</br>
 * 3.2在在欢迎页面或首页(WelcomeActivity)的onPause中加入JPushUtil.onPause()</br>
 * 4.在登录页面(LoginActivity)设置别名和标签区别用户,在取消登录页面设置取消
 * </p>
 * 
 * @author dzw
 *
 */
public class JPushUtil {

	// 1.配置AndroidManifest.xml

	/**
	 * 2.自定义Application的onCreate()方法中调用JPushUtil.init()初始化极光推送
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(context);
	}

	public static String getRegId(Context context) {
		return JPushInterface.getRegistrationID(context);
	}

	/**
	 * 3.1在在欢迎页面或首页(WelcomeActivity)的onResume中加入
	 * 
	 * @param context
	 */
	public static void onResume(Context context) {
		JPushInterface.onResume(context);
	}

	/**
	 * 3.2在在欢迎页面或首页(WelcomeActivity)的onPause中加入
	 * 
	 * @param context
	 */
	public static void onPause(Context context) {
		JPushInterface.onPause(context);
	}

	/**
	 * 4.在登录页面(LoginActivity)设置别名和标签区别用户,在取消登录页面设置取消
	 * 
	 * 
	 * @param context
	 *            上下文
	 * @param alias
	 *            【设置alias】 null 此次调用不设置此值。（注：不是指的字符串"null"） "" （空字符串）表示取消之前的设置。
	 *            每次调用设置有效的别名，覆盖之前的设置。 有效的别名组成：字母（区分大小写）、数字、下划线、汉字。 限制：alias
	 *            命名长度限制为 40 字节。（判断长度需采用UTF-8编码）
	 * 
	 * @param tagBack
	 *            返回对应的参数 alias, tags。并返回对应的状态码：0为成功，其他返回码请参考错误码定义。
	 *            温馨提示，设置标签别名请注意处理call back结果。只有call back 返回值为 0 才设置成功，
	 *            才可以向目标推送。否则服务器 API 会返回1011错误。
	 * @param tageSet
	 *            【设置tag】null 此次调用不设置此值。（注：不是指的字符串"null"） 空数组或列表表示取消之前的设置。
	 *            每次调用至少设置一个 tag，覆盖之前的设置，不是新增。 有效的标签组成：字母（区分大小写）、数字、下划线、汉字。
	 *            限制：每个 tag 命名长度限制为 40 字节，最多支持设置 100 个
	 *            tag，但总长度不得超过1K字节。（判断长度需采用UTF-8编码）
	 */
	public static void setAliasAndTags(boolean canPush, Context context,
									   String alias, Set<String> tageSet, TagAliasCallback tagBack) {
		// TODO Auto-generated method stub
		if (canPush) {
			JPushInterface.setAliasAndTags(context, alias, tageSet, tagBack);
		} else
			JPushInterface.setAliasAndTags(context, "错误的tag", tageSet, tagBack);
	}
}
