package com.tzdr.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jodd.exception.ExceptionUtil;
import jodd.io.FileUtil;
import jodd.mail.Email;
import jodd.mail.EmailMessage;
import jodd.util.StringUtil;

/**
 * @Description: TODO(邮件工具类)
 * @ClassName: EmailUtils
 * @author wangpinqun
 * @date 2014年12月17日 下午2:11:33
 */
public class EmailUtils {

	/**
	 * 单例对象
	 */
	private static EmailUtils instance;

	/**
	 * 发件人邮箱地址
	 */
	private String userName;

	/**
	 * 发件人邮箱昵称
	 */
	private String nickname;

	/**
	 * 邮件服务链接对象
	 */
	private SendMailSessionUtil sendMailSessionUtil;

	private EmailUtils() {
		// 邮件类型
		String host = ConfUtil.getContext("mail.smtp.host");
		// 邮件类型端口
		int port = Integer.parseInt(ConfUtil.getContext("mail.smtp.port"));
		// 账户
		userName = ConfUtil.getContext("mail.smtp.user");
		// 昵称
		nickname = ConfUtil.getContext("mail.smtp.nickname");
		// 密码
		String password = ConfUtil.getContext("mail.smtp.password");
		// 创建邮件服务链接
		sendMailSessionUtil = new SmtpServerUtils(host, port, userName, password).createSession();
	}

	public static synchronized EmailUtils getInstance() {
		if (instance == null) {
			instance = new EmailUtils();
		}
		return instance;
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param mail
	 *            内容
	 * @return boolean 返回类型
	 */
	public boolean sendMail(Email mail) {
		boolean isSendFlag = false;
		try {
			sendMailSessionUtil.open();
			mail.from(userName);
			if (Boolean.valueOf(ConfUtil.getContext("mail.smtp.isOpen"))) {
				sendMailSessionUtil.sendMail(mail, nickname);
			}
			isSendFlag = true;
		} catch (Exception ex) {
			System.out.println("邮件发送异常." + ExceptionUtil.exceptionToString(ex));
		} finally {
			sendMailSessionUtil.close();
		}
		return isSendFlag;
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param to
	 *            收件人邮箱地址
	 * @param content
	 *            内容
	 * @return boolean 返回类型
	 */
	public boolean sendMail(String to, String content) {
		return sendMail(to, null, content, null);
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param to
	 *            收件人邮箱地址
	 * @param content
	 *            内容
	 * @param attachFiles
	 *            附件
	 * @return boolean 返回类型
	 */
	public boolean sendMail(String to, String content, List<File> attachFiles) {
		return sendMail(to, null, content, attachFiles);
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param to
	 *            收件人邮箱地址
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param content
	 *            内容
	 * @return boolean 返回类型
	 */
	public boolean sendMail(String to, String subject, String content) {
		return sendMail(to, subject, content, null);
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param to
	 *            收件人邮箱地址
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param content
	 *            内容
	 * @param attachFiles
	 *            附件
	 * @return boolean 返回类型
	 */
	public boolean sendMail(String to, String subject, String content, List<File> attachFiles) {
		subject = subject == null || StringUtil.isEmpty(subject.trim()) ? ConfUtil.getContext("mail.smtp.subject")
				: subject;
		Email mail = Email.create();
		mail.to(to);
		mail.subject(subject);
		mail.addHtml(content);
		if (attachFiles != null && attachFiles.size() > 0) {
			for (File file : attachFiles) {
				mail.attachFile(file);
			}
		}
		return sendMail(mail);
	}

	/**
	 * @Description: TODO(发送邮件)
	 * @throws 异常
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param content
	 *            内容
	 * @param attachFiles
	 *            附件
	 * @param tos
	 *            收件人邮箱地址
	 * @return boolean 返回类型
	 */
	public boolean sendBatchMail(String subject, String content, List<File> attachFiles, String... tos) {
		subject = subject == null || StringUtil.isEmpty(subject.trim()) ? ConfUtil.getContext("mail.smtp.subject")
				: subject;
		Email mail = Email.create();
		mail.to(tos);
		mail.subject(subject);
		mail.addHtml(content);
		if (attachFiles != null && attachFiles.size() > 0) {
			for (File file : attachFiles) {
				mail.attachFile(file);
			}
		}
		return sendMail(mail);
	}

	/**
	 * @Description: TODO(发送带模板的邮件)
	 * @Title: sendMailTemp
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param filePath
	 *            邮件模板路径 (默认配置文件mail.temp.url的值)
	 * @param fileName
	 *            邮件模板名称 如：emailTemp.ftl的emailTemp为模板名称
	 * @param fileSuffix
	 *            邮件模板后缀(默认配置文件mail.temp.suffix的值) 如：emailTemp.ftl的.ftl为模板后缀
	 * @param params
	 *            动态参数 替换模板中某些位置的信息,被替换信息与替换信息个数必须一致 如：1、替换的值 List
	 *            <String> pramas = new ArrayList<String>();
	 *            pramas.add("081913"); pramas.add("投资达人") ;
	 *            2、模板信息（第一个%s将被替换成081913、第二个%s将被替换成投资达人）
	 *            您正在进行邮箱绑定，本次验证码：%s，请输入验证码完成绑定。【%s】
	 * @param attachFiles
	 *            附件
	 * @param tos
	 *            收件人邮箱地址
	 * @throws Exception
	 *             异常
	 * @return boolean 返回类型
	 */
	public boolean sendBatchMailTemp(String subject, String filePath, String fileName, String fileSuffix,
			List<String> params, List<File> attachFiles, String... tos) throws Exception {
		fileSuffix = fileSuffix == null || "".equals(fileSuffix.trim()) ? ConfUtil.getContext("mail.temp.suffix")
				: fileSuffix;
		filePath = filePath == null || "".equals(filePath.trim()) ? ConfUtil.getContext("mail.temp.url") : filePath;
		String tempStr = FileUtil
				.readUTFString(EmailUtils.class.getClassLoader().getResourceAsStream(filePath + fileName + fileSuffix));
		String content = params == null || params.isEmpty() ? tempStr : String.format(tempStr, params.toArray());
		return sendBatchMail(subject, content, attachFiles, tos);
	}

	/**
	 * @Description: TODO(发送带模板的邮件)
	 * @Title: sendMailTemp
	 * @param to
	 *            收件人邮箱地址
	 * @param fileName
	 *            邮件模板名称 如：emailTemp.ftl的emailTemp为模板名称
	 * @param params
	 *            动态参数 替换模板中某些位置的信息,被替换信息与替换信息个数必须一致 如：1、替换的值 List
	 *            <String> pramas = new ArrayList<String>();
	 *            pramas.add("081913"); pramas.add("投资达人") ;
	 *            2、模板信息（第一个%s将被替换成081913、第二个%s将被替换成投资达人）
	 *            您正在进行邮箱绑定，本次验证码：%s，请输入验证码完成绑定。【%s】
	 * @throws Exception
	 *             异常
	 * @return boolean 返回类型
	 */
	public boolean sendMailTemp(String to, String fileName, List<String> params) throws Exception {
		return sendMailTemp(to, null, null, fileName, null, params, null);
	}

	/**
	 * @Description: TODO(发送带模板的邮件)
	 * @Title: sendMailTemp
	 * @param to
	 *            收件人邮箱地址
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param fileName
	 *            邮件模板名称 如：emailTemp.ftl的emailTemp为模板名称
	 * @param params
	 *            动态参数 替换模板中某些位置的信息,被替换信息与替换信息个数必须一致 如：1、替换的值 List
	 *            <String> pramas = new ArrayList<String>();
	 *            pramas.add("081913"); pramas.add("投资达人") ;
	 *            2、模板信息（第一个%s将被替换成081913、第二个%s将被替换成投资达人）
	 *            您正在进行邮箱绑定，本次验证码：%s，请输入验证码完成绑定。【%s】
	 * @throws Exception
	 *             异常
	 * @return boolean 返回类型
	 */
	public boolean sendMailTemp(String to, String subject, String fileName, List<String> params) throws Exception {
		return sendMailTemp(to, subject, null, fileName, null, params, null);
	}

	/**
	 * @Description: TODO(发送带模板的邮件)
	 * @Title: sendMailTemp
	 * @param to
	 *            收件人邮箱地址
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param fileName
	 *            邮件模板名称 如：emailTemp.ftl的emailTemp为模板名称
	 * @param params
	 *            动态参数 替换模板中某些位置的信息,被替换信息与替换信息个数必须一致 如：1、替换的值 List
	 *            <String> pramas = new ArrayList<String>();
	 *            pramas.add("081913"); pramas.add("投资达人") ;
	 *            2、模板信息（第一个%s将被替换成081913、第二个%s将被替换成投资达人）
	 *            您正在进行邮箱绑定，本次验证码：%s，请输入验证码完成绑定。【%s】
	 * @param attachFiles
	 *            附件
	 * @throws Exception
	 *             异常
	 * @return boolean 返回类型
	 */
	public boolean sendMailTemp(String to, String subject, String fileName, List<String> params, List<File> attachFiles)
			throws Exception {
		return sendMailTemp(to, subject, null, fileName, null, params, attachFiles);
	}

	/**
	 * @Description: TODO(发送带模板的邮件)
	 * @Title: sendMailTemp
	 * @param to
	 *            收件人邮箱地址
	 * @param subject
	 *            发送标题 (默认配置文件mail.smtp.subject的值)
	 * @param filePath
	 *            邮件模板路径 (默认配置文件mail.temp.url的值)
	 * @param fileName
	 *            邮件模板名称 如：emailTemp.ftl的emailTemp为模板名称
	 * @param fileSuffix
	 *            邮件模板后缀(默认配置文件mail.temp.suffix的值) 如：emailTemp.ftl的.ftl为模板后缀
	 * @param params
	 *            动态参数 替换模板中某些位置的信息,被替换信息与替换信息个数必须一致 如：1、替换的值 List
	 *            <String> pramas = new ArrayList<String>();
	 *            pramas.add("081913"); pramas.add("投资达人") ;
	 *            2、模板信息（第一个%s将被替换成081913、第二个%s将被替换成投资达人）
	 *            您正在进行邮箱绑定，本次验证码：%s，请输入验证码完成绑定。【%s】
	 * @param attachFiles
	 *            附件
	 * @throws Exception
	 *             异常
	 * @return boolean 返回类型
	 */
	public boolean sendMailTemp(String to, String subject, String filePath, String fileName, String fileSuffix,
			List<String> params, List<File> attachFiles) throws Exception {
		fileSuffix = fileSuffix == null || "".equals(fileSuffix.trim()) ? ConfUtil.getContext("mail.temp.suffix")
				: fileSuffix;
		filePath = filePath == null || "".equals(filePath.trim()) ? ConfUtil.getContext("mail.temp.url") : filePath;
		String tempStr = FileUtil
				.readUTFString(EmailUtils.class.getClassLoader().getResourceAsStream(filePath + fileName + fileSuffix));
		String content = params == null || params.isEmpty() ? tempStr : String.format(tempStr, params.toArray());
		return sendMail(to, subject, content, attachFiles);
	}

	public static void main(String[] args) {

	      try{
				String to = "chendong@vs.com";
				String subject = "我的主题我做主";

				Email email = Email.create();
				email.addMessage(new EmailMessage("Hello, I am Vincen"));
				email.addText("这里写的是Test");
				email.to(to);
				email.subject(subject);
				// EmailUtils.getInstance().sendMail(email);

				// String content = "<html><META http-equiv=Content-Type
				// content=\"text/html;
				// charset=utf-8\"><body><h1>你好v0000</h1></body></html>";
				// EmailUtils.getInstance().sendMail(to, content);
				// EmailUtils.getInstance().sendMail(to, subject, content);

				// List<File> files = new ArrayList<File>();
				// files.add(new File("F:\\","6.jpg"));
				// files.add(new File("F:\\","7.jpg"));
				List<String> pramas = new ArrayList<String>();
				pramas.add("081913");
				pramas.add("投资达人");
				EmailUtils.getInstance().sendMailTemp(to, "email", pramas);
				// pramas.add("投资达人qweqwe");
				// EmailUtils.getInstance().sendMailTemp(to, subject,
				// "commissionIncome/", "commissionIncomeExceptioneMail",
				// ".ftl", pramas, null);
				// EmailUtils.getInstance().sendMail(to, subject, content,
				// files);
				System.out.println("发送成功!...");
				// System.out.println(FileUtil.readUTFString(EmailUtils.class.getClassLoader().getResourceAsStream("email.ftl")));
			} catch (Exception e) {
				e.printStackTrace();
	}
}
}
