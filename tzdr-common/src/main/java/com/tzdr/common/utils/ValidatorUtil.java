package com.tzdr.common.utils;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月8日 上午11:28:47
 * 类说明
 */
public class ValidatorUtil {
	// wi =2(n-1)(mod 11);加权因子   
	static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };   
    // 校验码   
	static  final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };   
    static int[] ai = new int[18];  
	public static String  getErrorMessage(BindingResult result){
		List<ObjectError> errors = result.getAllErrors();
		
		String errormsg = "";
		for (ObjectError error : errors){
			errormsg = errormsg+error.getDefaultMessage()+";";
		}
		return errormsg;
	}
	
    // 校验身份证的校验码   
    public static boolean verifycard(String idcard) {   
        if (idcard.length() == 15) {   
           idcard = uptoeighteen(idcard);   
        }   
        if (idcard.length() != 18) {   
            return false;   
        }   
       String verify = idcard.substring(17, 18);   
        if (verify.equals(getVerify(idcard))) {   
            return true;   
        }   
        return false;   
    }   
	  
    // 15位转18位   
    public static String uptoeighteen(String fifteen) {   
        StringBuffer eighteen = new StringBuffer(fifteen);   
        eighteen = eighteen.insert(6, "19");   
        return eighteen.toString();   
    }   
	  
	 // 计算最后一位校验值   
    public static String getVerify(String eighteen) {   
       int remain = 0;   
       if (eighteen.length() == 18) {   
	            eighteen = eighteen.substring(0, 17);   
	        }   
	        if (eighteen.length() == 17) {   
	           int sum = 0;   
	            for (int i = 0; i < 17; i++) {   
	                String k = eighteen.substring(i, i + 1);   
	               ai[i] = Integer.valueOf(k);   
	           }   
	           for (int i = 0; i < 17; i++) {   
	                sum += wi[i] * ai[i];   
	           }   
	           remain = sum % 11;   
	       }   
	       return remain == 2 ? "X" : String.valueOf(vi[remain]);   
	    }   

   
}
