package com.tzdr.olog.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import com.google.common.collect.Maps;
import com.tzdr.common.utils.Reflections;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.Servlets;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.olog.OlogManager;
import com.tzdr.olog.aop.annotation.OlogAction;
import com.tzdr.olog.aop.annotation.OlogIgnorParameters;
import com.tzdr.olog.aop.annotation.OlogModule;
import com.tzdr.olog.aop.annotation.OlogParameterMapping;
import com.tzdr.olog.db.domain.Olog;



public class OlogControllerAdvice {

	private static final Logger logger = LoggerFactory
			.getLogger(OlogControllerAdvice.class);

	private String ignoreClasses;
	private String ignoreMethods;
	private boolean needRecordParameter = true;
	private String ignoreParameters;
	private Map<String, String> methodNamesMapping = new HashMap<String, String>();
	private String userInSessionKey = "";
	private ModuleNameMappingFactory moduleNameMappingFactory;

	private OlogManager ologManager;

	/**
	 * Spring3mvc 拦截日志处理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	public Object interceptOlog(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;
		// 计算处理时间，注意這個處理時間因為是代理執行數據庫操作原因，可能存在誤差。
		StopWatch clock = new StopWatch();
		clock.start(); // 计时开始
		try {
			result = pjp.proceed();
		} finally {
			clock.stop(); // 计时结束
		}

		long executeMilliseconds = clock.getTime();
		if (isIgnoreClasse(pjp) || isIgnoreMethod(pjp)) {
			return result;
		}
		Object target = pjp.getTarget();
		String methodName = pjp.getSignature().getName();
		// 收集日志信息，并持久化，如果出错忽略。
		try {
			Olog olog = new Olog();
			olog.setExecuteMilliseconds(executeMilliseconds);
			handleAction(target, methodName, olog);
			handleOperater(olog, pjp.getArgs());
			HttpServletRequest request = getRequest(pjp.getArgs());
			handleModule(request, target, olog);
			handleClientInformations(request, olog);
			handleParameters(request, target, methodName, pjp.getArgs(), olog);
			handleResult(olog, result, request);
			ologManager.logger(olog);
			logger.debug("Save olog success. " + olog.toString());
		} catch (Exception e) {
			logger.debug("Save olog failure, direct retuen. [" + e.getMessage()
					+ "]");
			// DON'T DO ANYTHING, JUST IGNORE.
		}
		return result;
	}

	protected void handleParameters(HttpServletRequest request, Object target,
			String methodName, Object args[], Olog olog) {
		if (!needRecordParameter) {
			return;
		}
		String requestParameters = Arrays.toString(args);
		if (request != null) {
			requestParameters = getRequestParameters(request, target,
					methodName);
		}
		requestParameters = StringUtils.substring(requestParameters, 0, 256);
		olog.setRequestParameters(requestParameters);
	}

	protected void handleClientInformations(HttpServletRequest request,
			Olog olog) {
		if (request != null) {
			olog.setClientInformations(getClientInformations(request));
		}
	}

	/**
	 * 获取MODULE和MODULENAME
	 * 
	 * @param request
	 * @param olog
	 */
	protected void handleModule(HttpServletRequest request, Object target,
			Olog olog) {
		if (request != null) {
			logger.debug("Find one argument is HttpServletRequest, handle module with HttpServletRequest and resourceMapping.");
			String module = request.getRequestURI();
			olog.setModule(module);
			olog.setModuleName(getModuleName(module));
		}

		// Annotation方式标注模块和模块名称优先级高于配置
		OlogModule ologModule = (OlogModule) target.getClass().getAnnotation(
				OlogModule.class);
		if (ologModule != null) {
			if (StringUtils.isNotBlank(ologModule.module())) {
				olog.setModule(ologModule.module());
			}
			if (StringUtils.isNotBlank(ologModule.moduleName())) {
				olog.setModuleName(ologModule.moduleName());
			}
		}
	}

	/**
	 * 处理action和ActionName,并设置到Olog中
	 * 
	 * @param target
	 * @param methodName
	 */
	protected void handleAction(Object target, String methodName, Olog olog) {
		// 获取Action和ActionName,Annotation优先于mapping配置
		String action = methodName;
		String actionName = getActionName(action);
		Method method = Reflections.getAccessibleMethodByName(target,
				methodName);
		OlogAction ologAction = (OlogAction) method
				.getAnnotation(OlogAction.class);
		if (ologAction != null) {
			if (StringUtils.isNotBlank(ologAction.action())) {
				action = ologAction.action();
			}
			if (StringUtils.isNotBlank(ologAction.actionName())) {
				actionName = ologAction.actionName();
			}
		}
		olog.setAction(action);
		olog.setActionName(actionName);

	}

	/**
	 * 获取操作员信息，与框架耦合
	 * 
	 * @param olog
	 * @param args
	 */
	protected void handleOperater(Olog olog, Object[] args) {
		User currentUser=null;
		// 操作员获取和设置
		if(SecurityUtils.getSubject().isAuthenticated()){
		 currentUser = (User) SecurityUtils.getSubject().getPrincipal();
		}
		if (currentUser != null) {
			olog.setOperateUser(currentUser.getId() + "/"
					+ currentUser.getUsername());
			olog.setOperateUserId(String.valueOf(currentUser.getId()));
		} else {
			// 如果是登录失败，没有Session的情况，写入登录的Token
			for (Object object : args) {
				if (object instanceof AuthenticationToken) {
					olog.setOperateUser(object.toString());
				}
			}
		}
	}

	/**
	 * 获取处理结果 分别根据返回值和request中的会话变量进行判断处理，与框架耦合。
	 * 
	 * @param result
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	protected void handleResult(Olog olog, Object result,
			HttpServletRequest request) {
		// 2.0.0 json方式，通过返回值判读
		if (result instanceof JsonResult) {
			JsonResult r = (JsonResult) result;
			olog.setOperateResult((r.isSuccess() ? Olog.OPERATE_RESULT_SUCCESS
					: Olog.OPERATE_RESULT_FAILURE));
			olog.setOperateMessage(r.getMessage());
			return;
		}

		// 1.0.0版本基于临时Session的消息判读
		List<String> messages = (List<String>) WebUtils.getSessionAttribute(
				request, "messages");
		int operateResult = Olog.OPERATE_RESULT_SUCCESS;
		if (messages != null && messages.size() > 0) {
			for (String message : messages) {
				if (StringUtils.contains(message, "错误")
						|| StringUtils.contains(message, "失败")) {
					operateResult = Olog.OPERATE_RESULT_FAILURE;
					break;
				}
			}
			String operateMessage = messages.toString();
			if (operateMessage.length() > 128) {
				operateMessage = StringUtils.substring(operateMessage, 0, 128)
						+ "...]";
			}
			olog.setOperateResult(operateResult);
			olog.setOperateMessage(operateMessage);
			return;
		}

	}

	private HttpServletRequest getRequest(Object[] args) {
		HttpServletRequest request = null;
		for (Object arg : args) {
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				break;
			}
		}
		return request;
	}

	/**
	 * 获取请求客户端信息
	 * 
	 * @param request
	 * @return
	 */
	private String getClientInformations(HttpServletRequest request) {
		String clientIP = request.getRemoteAddr();
		String requestUserAgent = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(requestUserAgent);
		OperatingSystem os = userAgent.getOperatingSystem();
		Browser browser = userAgent.getBrowser();
		String clientInfo = clientIP + " - " + os.getName() + " - "
				+ browser.getName() + "/" + browser.getBrowserType().getName();
		return clientInfo;

	}

	private String getRequestParameters(HttpServletRequest request,
			Object target, String methodName) {
		String parameters = "";
		if (needRecordParameter) {
			Map<String, Object> requestParameters = Servlets
					.getParametersStartingWith(request, null);
			Map<String, String> paramNameMapping = getParameterMapping(target,
					methodName);

			String ignorParams = "";
			Method method = Reflections.getAccessibleMethodByName(target,
					methodName);
			OlogIgnorParameters ologIgnorParameters = (OlogIgnorParameters) method
					.getAnnotation(OlogIgnorParameters.class);
			if (ologIgnorParameters != null) {
				String ignors[] = ologIgnorParameters.value();
				if (ignors != null && ignors.length > 0) {
					ignorParams = "," + StringUtils.join(ignors, ",") + ",";
				}
			}

			Map<String, Object> confirmParameters = new TreeMap<String, Object>();
			for (Map.Entry<String, Object> entry : requestParameters.entrySet()) {
				if (!isIgnoreParameter(entry.getKey())
						&& !StringUtils.contains(ignorParams,
								"," + entry.getKey() + ",")
						&& entry.getValue() != null
						&& StringUtils.isNotBlank(entry.getValue().toString())) {
					String key = entry.getKey();
					if (StringUtils.isNotBlank(paramNameMapping.get(key))) {
						key = paramNameMapping.get(key);
					}
					confirmParameters.put(key, entry.getValue());
				}
			}
			if (confirmParameters.size() > 0) {
				parameters = confirmParameters.toString();
			}
		}
		return parameters;
	}

	private Map<String, String> getParameterMapping(Object target,
			String methodName) {
		Method method = Reflections.getAccessibleMethodByName(target,
				methodName);
		OlogParameterMapping ologParameterMapping = (OlogParameterMapping) method
				.getAnnotation(OlogParameterMapping.class);
		Map<String, String> paramMapping = Maps.newHashMap();
		if (ologParameterMapping != null) {
			String[] mapping = ologParameterMapping.value();
			if (mapping != null && mapping.length > 0) {
				for (String e : mapping) {
					String[] entry = StringUtils.split(e, ":");
					if (entry != null && entry.length == 2) {
						paramMapping.put(entry[0], entry[1]);
					}
				}
			}
		}
		return paramMapping;
	}

	private String getModuleName(String requestUri) {
		return moduleNameMappingFactory.getModuleName(requestUri);
	}

	private String getActionName(String action) {
		// 从配置文件的mapping中获取名称
		String actionName = methodNamesMapping.get(action);
		return actionName != null ? actionName : action;
	}

	/**
	 * 判断是否忽略的参数
	 * 
	 * @param str
	 * @return
	 */
	private boolean isIgnoreParameter(String parameterName) {
		String[] ignores = StringUtils.split(ignoreParameters, ",");
		for (String ignoreStr : ignores) {
			if (isLike(ignoreStr, parameterName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isLike(String srcIncludeStar, String dest) {
		if ("*".equals(srcIncludeStar)) {
			return true;
		} else if (srcIncludeStar.indexOf("*") == 0) {
			if (dest.indexOf(srcIncludeStar.substring(1,
					srcIncludeStar.length())) == dest.length()
					- srcIncludeStar.length() + 1) {
				return true;
			} else {
				return false;
			}
		} else if (srcIncludeStar.indexOf("*") == srcIncludeStar.length() - 1) {
			if (dest.indexOf(srcIncludeStar.substring(0,
					srcIncludeStar.length() - 1)) == 0) {
				return true;
			} else {
				return false;
			}
		} else if (srcIncludeStar.equalsIgnoreCase(dest)) {
			return true;
		}
		return false;
	}

	private boolean isIgnoreClasse(ProceedingJoinPoint pjp) {
		if (StringUtils.contains(getIgnoreClasses(), pjp.getTarget().getClass()
				.getName())) {
			logger.debug(pjp.getTarget().getClass().getName()
					+ " in ignoreControllers[" + ignoreClasses
					+ "]. ignore this action, direct retuen.");
			return true;
		} else {
			return false;
		}
	}

	private boolean isIgnoreMethod(ProceedingJoinPoint pjp) {
		String methodName = pjp.getSignature().getName();
		if (StringUtils.contains(getIgnoreMethods(), methodName)) {
			logger.debug(pjp.getTarget().getClass() + "." + methodName
					+ " in IgnoreMethods[" + ignoreMethods
					+ "]. ignore this action, direct retuen.");
			return true;
		} else {
			return false;
		}
	}

	public ModuleNameMappingFactory getModuleNameMappingFactory() {
		return moduleNameMappingFactory;
	}

	public void setModuleNameMappingFactory(
			ModuleNameMappingFactory moduleNameMappingFactory) {
		this.moduleNameMappingFactory = moduleNameMappingFactory;
	}

	public String getIgnoreParameters() {
		return ignoreParameters;
	}

	public void setIgnoreParameters(String ignoreParameters) {
		this.ignoreParameters = ignoreParameters;
	}

	public Map<String, String> getMethodNamesMapping() {
		return methodNamesMapping;
	}

	public void setMethodNamesMapping(Map<String, String> methodNamesMapping) {
		this.methodNamesMapping = methodNamesMapping;
	}

	public boolean isNeedRecordParameter() {
		return needRecordParameter;
	}

	public void setNeedRecordParameter(boolean needRecordParameter) {
		this.needRecordParameter = needRecordParameter;
	}

	public String getIgnoreClasses() {
		return ignoreClasses;
	}

	public void setIgnoreClasses(String ignoreClasses) {
		this.ignoreClasses = ignoreClasses;
	}

	public String getIgnoreMethods() {
		return ignoreMethods;
	}

	public void setIgnoreMethods(String ignoreMethods) {
		this.ignoreMethods = ignoreMethods;
	}

	public String getUserInSessionKey() {
		return userInSessionKey;
	}

	public void setUserInSessionKey(String userInSessionKey) {
		this.userInSessionKey = userInSessionKey;
	}

	public void setOlogManager(OlogManager ologManager) {
		this.ologManager = ologManager;
	}

}
