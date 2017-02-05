package com.tzdr.sso;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import javax.validation.constraints.NotNull;

import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.HandlerResult;
import org.jasig.cas.authentication.PreventedException;
import org.jasig.cas.authentication.UsernamePasswordCredential;
import org.jasig.cas.authentication.principal.SimplePrincipal;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

/**
 * 根据数据库查询出来的密码和盐值，使用MD5算法判断密码的正确性
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年7月13日
 *
 */
public class SaltMD5AuthenticationHandler extends
		AbstractJdbcUsernamePasswordAuthenticationHandler {
	@NotNull
	private String passwordSql;

	private String saltSql;
	
	private String userSql;

	@Override
	protected HandlerResult authenticateUsernamePasswordInternal(
			UsernamePasswordCredential credential)
			throws GeneralSecurityException, PreventedException {
		final String username = credential.getUsername();
		final String password = credential.getPassword();
		try {
			final String dbPassword = getJdbcTemplate().queryForObject(
					this.passwordSql, String.class,
					getArr(passwordSql, username));
			String encryptedPassword = this.getPasswordEncoder().encode(
					password);
			if (this.saltSql != null) {
				final String salt = getJdbcTemplate().queryForObject(
						this.saltSql, String.class, getArr(saltSql, username));
				encryptedPassword = encryptPassword(password, salt);
			}
			if (!dbPassword.equals(encryptedPassword)) {
				throw new FailedLoginException(
						"Password does not match value on record.");
			}
		} catch (final IncorrectResultSizeDataAccessException e) {
			if (e.getActualSize() == 0) {
				throw new AccountNotFoundException(username
						+ " not found with SQL query");
			} else {
				throw new FailedLoginException("Multiple records found for "
						+ username);
			}
		} catch (final DataAccessException e) {
			throw new PreventedException(
					"SQL exception while executing query for " + username, e);
		}
		
		Map<String, Object> userMaps = new HashMap<String, Object>();
		
		List<UserSessionBean> userSessionBean=this.getJdbcTemplate().query(userSql, new Object[]{username, username}, org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper.newInstance(UserSessionBean.class));  
		System.out.println("userSessionBean=========" + userSessionBean);
		userMaps.put("userSessionBean", userMaps);
		this.getJdbcTemplate().update("update w_user set last_login_time=? where mobile=?",new Date().getTime()/1000,username);
		return createHandlerResult(credential, new SimplePrincipal(username, userMaps),
				null);
	}

	public String getPasswordSql() {
		return passwordSql;
	}

	public void setPasswordSql(String passwordSql) {
		this.passwordSql = passwordSql;
	}

	public String getSaltSql() {
		return saltSql;
	}

	public void setSaltSql(String saltSql) {
		this.saltSql = saltSql;
	}

	private String encryptPassword(String password, String salt) {
		return Md5Utils.hash(password + salt);
	}

	private String[] getArr(String sql, String username) {
		int num = getCount(sql);
		String[] arr = new String[num];
		for (int i = 0; i < num; i++) {
			arr[i] = username;
		}
		return arr;
	}

	private int getCount(String sql) {
		int count = 0;
		int index = -1;
		while ((index = sql.indexOf("?", index)) > -1) {
			++index;
			++count;
		}
		return count;
	}

	public String getUserSql() {
		return userSql;
	}

	public void setUserSql(String userSql) {
		this.userSql = userSql;
	}

}
