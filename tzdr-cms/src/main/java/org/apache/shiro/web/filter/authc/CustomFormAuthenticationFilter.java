package org.apache.shiro.web.filter.authc;

import javax.servlet.ServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzdr.business.cms.service.user.UserService;

/**
 * <p/>
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

   

    @Override
    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        request.setAttribute(getFailureKeyAttribute(), ae);
    }

    /**
     * 默认的成功地址
     */
    private String defaultSuccessUrl;

  
    public void setDefaultSuccessUrl(String defaultSuccessUrl) {
        this.defaultSuccessUrl = defaultSuccessUrl;
    }


    public String getDefaultSuccessUrl() {
        return defaultSuccessUrl;
    }


    /**
     * 根据用户选择成功地址
     *
     * @return
     */
    @Override
    public String getSuccessUrl() {
        return getDefaultSuccessUrl();
    }
}
