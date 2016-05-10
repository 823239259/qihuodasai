package com.tzdr.cms.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.domain.entity.HundsunToken;

/**
 * @Description: 每个小时刷新一下token
 * @ClassName:
 * @author LinFeng
 * @date
 */
@Component
public class HundsunTokenRefresh {

	public static final Logger logger = LoggerFactory
			.getLogger(HundsunTokenRefresh.class);

	public void executeRefreh() {
		logger.info("------------------恒生token自动刷新任务--------begin-------------------");		
		String operatorNo = ConfUtil.getContext("hundsun.manager.operator.no");
		try {
			HundsunToken hundsunToken = HundsunJres.getInstance()
					.findHundsunToken(operatorNo);
			if (hundsunToken != null) {
				HundsunJres.getInstance().LogoutOnce(hundsunToken.getToken());
			}
			HundsunJres.getInstance().get(operatorNo);
		} catch (T2SDKException e) {
			logger.error(Exceptions.getStackTraceAsString(e));
			HandleException(e, "executeInternal");
		}
		logger.info("------------------恒生token自动刷新任务--------end-------------------");		

	}

	/**
	 * 异常处理
	 * 
	 * @param e
	 * @param method
	 */
	private void HandleException(Exception e, String method) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail, "exceptionemail",
					pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}
}
