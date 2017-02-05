
package com.tzdr.business.cms.service.user;



import org.springframework.stereotype.Service;

import com.tzdr.business.cms.exception.UserPasswordNotMatchException;
import com.tzdr.business.cms.utils.UserLogUtils;
import com.tzdr.common.utils.Md5Utils;
import com.tzdr.domain.cms.entity.user.User;


/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Service
public class PasswordService {
  
    public void validate(User user, String password) {
        String username = user.getUsername();   
        if (!matches(user, password)) {
            UserLogUtils.log(
                    username,
                    "passwordError",
                    "password error! password: {} ",
                    password);
            throw new UserPasswordNotMatchException();
        } 
    }

    public boolean matches(User user, String newPassword) {
        return user.getPassword().equals(encryptPassword(user.getRealname(), newPassword, user.getSalt()));
    }
    public String encryptPassword(String username, String password, String salt) {
        return Md5Utils.hash(username + password + salt);
    }
    
    public String encryptPassword(String password, String salt) {
        return Md5Utils.hash(password + salt);
    }
}
