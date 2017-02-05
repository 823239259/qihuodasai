package com.tzdr.web.support.filter.cas;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.session.SingleSignOutHandler;
import org.jasig.cas.client.util.AbstractConfigurationFilter;

import com.tzdr.common.utils.ConfUtil;

public class CasSingleSignOutFilter extends AbstractConfigurationFilter {

	private static final SingleSignOutHandler HANDLER = new SingleSignOutHandler();

	private AtomicBoolean handlerInitialized = new AtomicBoolean(false);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		if (!isIgnoreInitConfiguration()) {
			HANDLER.setArtifactParameterName(getPropertyFromInitParams(
					filterConfig, "artifactParameterName",
					SingleSignOutHandler.DEFAULT_ARTIFACT_PARAMETER_NAME));
			HANDLER.setLogoutParameterName(getPropertyFromInitParams(
					filterConfig, "logoutParameterName",
					SingleSignOutHandler.DEFAULT_LOGOUT_PARAMETER_NAME));
			HANDLER.setFrontLogoutParameterName(getPropertyFromInitParams(
					filterConfig, "frontLogoutParameterName",
					SingleSignOutHandler.DEFAULT_FRONT_LOGOUT_PARAMETER_NAME));
			HANDLER.setRelayStateParameterName(getPropertyFromInitParams(
					filterConfig, "relayStateParameterName",
					SingleSignOutHandler.DEFAULT_RELAY_STATE_PARAMETER_NAME));
			HANDLER.setCasServerUrlPrefix(ConfUtil.getContext("SSO.casServerUrl"));
			HANDLER.setArtifactParameterOverPost(parseBoolean(getPropertyFromInitParams(
					filterConfig, "artifactParameterOverPost", "false")));
			HANDLER.setEagerlyCreateSessions(parseBoolean(getPropertyFromInitParams(
					filterConfig, "eagerlyCreateSessions", "true")));
		}
		HANDLER.init();
		handlerInitialized.set(true);
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;

		/**
		 * <p>
		 * Workaround for now for the fact that Spring Security will fail since
		 * it doesn't call {@link #init(javax.servlet.FilterConfig)}.
		 * </p>
		 * <p>
		 * Ultimately we need to allow deployers to actually inject their
		 * fully-initialized
		 * {@link org.jasig.cas.client.session.SingleSignOutHandler}.
		 * </p>
		 */
		if (!this.handlerInitialized.getAndSet(true)) {
			HANDLER.init();
		}

		if (HANDLER.process(request, response)) {
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
