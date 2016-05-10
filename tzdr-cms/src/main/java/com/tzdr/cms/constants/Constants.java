package com.tzdr.cms.constants;


/**
 * 
 * <B>说明: </B> cms常量管理类
 * @zhouchen
 * 2016年1月20日 下午1:36:49
 */
public class Constants {

	/**
	 * 页面 编辑常量 
	 */
	public static final String EDIT = "edit";
	
	/**
	 * 页面 新增常量
	 */
	public static final String ADD = "add";
	
	/**
	 *  组织中新增用户
	 */
	public static final String ORGANIZATION_ADD_USER = "orgAddUser";
	
	/**
	 * 是否ajax请求
	 */
	public static final String IS_AJAX = "1";

	/**
	 *  ajax常量
	 */
	public static final String AJAX = "ajax";

	/**
	 *  session 失效
	 */
	public static final String LOGIN_SESSION = "loginSession";

	/**
	 * 错误码 常量
	 */
	public static final String ERROR_CODE = "errorCode";

	/**
	 *  错误信息
	 */
	public static final String ERROR_MESSAGE = "errorMessage";

	 /**
     * 操作名称
     */
	public static final String OP_NAME = "op";


    /**
     * 消息key
     */
	public static final String MESSAGE = "message";

    /**
     * 错误key
     */
	public static final String ERROR = "error";

    /**
     * 上个页面地址
     */
	public static final String BACK_URL = "BackURL";

	public static final String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
	public static final String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
	public static final String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";

	public static final String CONTEXT_PATH = "ctx";

    /**
     * 当前登录的用户
     */
	public static final String CURRENT_USER = "user";
	public static final String CURRENT_USERNAME = "username";

	public static final String ENCODING = "UTF-8";
	
	
	/**
	 * 字符串间隔符,
	 */
	public static final String SEPERATOR_COMMA = ",";
	
	/**
	 * 字符串间隔符-
	 */
	public static final String SEPERATOR_LINE = "-";
	
	/**
	 * 字符串间隔符*
	 */
	public static final String SEPERATOR_SEMICOLON  = ";";
	//支付状态
	public static class PayStatus{
		/**
		 * 未处理
		 */
		public static final int NOPROCESSING =0;
		/**
		 * 成功
		 */
		public static final int SUCCESS =21;
	}
	
	//补仓状态
	public static class coverStatus{
		
		/**
		 * 待审核
		 */
		public static final int STAYAUDIT = 0;
		
		/**
		 * 审核通过
		 */
		public static final int AUDITPASS = 1;
		
		/**
		 * 审核不通过
		 */
		public static final int AUDITUNPASS = 2;
	}
	
	/**
	 * 项目部署环境
	 * @zhouchen
	 * 2015年6月25日
	 */
	public static class Environment{
		public static final String APP1 = "app1";
		public static final String APP2 = "app2";
		public static final String API = "api";
		public static final String CMS = "cms";
	}
}
