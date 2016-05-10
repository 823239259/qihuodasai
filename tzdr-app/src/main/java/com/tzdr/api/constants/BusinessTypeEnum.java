package com.tzdr.api.constants;

/**
 * 国际期货业务类型 
 * @author zhouchen
 * 2016年3月24日 上午10:01:22
 */
public enum BusinessTypeEnum {
	
	    A50(0),
	    A50_CONFIG(5),
	    CRUDE(6), 
	    HSI(7), 
	    MULTIPLE(8);
	    
	    private int value;
	 
	    private BusinessTypeEnum(int value) {
	        this.value = value;
	    }
	 
	    //获取对应value值
	    public int getValue() {
	        return value;
	    }
	    
	    /**
	     * 是否正确的业务类型
	     * @param businessType
	     * @return
	     */
	    public static boolean isBusinessType(int businessType){
	 
	    	if (BusinessTypeEnum.A50.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.CRUDE.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    
	   /**
	    * 根据业务类型  获取对应的申请操盘时的备注信息
	    * @param businessType
	    * @return
	    */
	    public static String getBussinessFundRemark(int businessType){
	 
	    	if (BusinessTypeEnum.A50.getValue()==businessType){
	    		return "投资新华富时A50申请（划款）。";
	    	}
	    	if (BusinessTypeEnum.CRUDE.getValue()==businessType){
	    		return "投资国际原油期货申请（划款）。";
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return "投资香港恒生指数期货申请（划款）。";
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return "投资国际综合方案申请(划款)。";
	    	}
	    	return "";
	    }
}
