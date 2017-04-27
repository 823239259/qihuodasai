package com.tzdr.common.api.contact;

/**
 * 国际期货业务类型 
 * @author zhouchen
 * 2016年3月24日 上午10:01:22
 */
public enum BusinessTypeEnum {
	
	    CN(0),
	    CN_CONFIG(5),
	    CL(6), 
	    HSI(7), 
	    MULTIPLE(8),
	    YM(9),
		NQ(10),
		ES(11),
		FDAX(12),
		NK(13),
		MHI(14),
		GC(15),
		HH(16),
		MCH(17),
		HG(18),
		SI(19),
		QM(20),
		FDXM(21),
		NG(22);

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
	 
	    	if (BusinessTypeEnum.CN.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.CL.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.YM.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.NQ.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.ES.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.FDAX.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.NK.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.MHI.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.GC.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.HH.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.MCH.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.HG.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.SI.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.QM.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.FDXM.getValue()==businessType){
	    		return true;
	    	}
	    	if (BusinessTypeEnum.NG.getValue()==businessType){
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
	 
	    	if (BusinessTypeEnum.CN.getValue()==businessType){
	    		return "投资新华富时A50（划款）。";
	    	}
	    	if (BusinessTypeEnum.CL.getValue()==businessType){
	    		return "投资国际原油（划款）。";
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return "投资香港恒生指数（划款）。";
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return "投资国际综合(划款)。";
	    	}
	    	if (BusinessTypeEnum.YM.getValue()==businessType){
	    		return "投资迷你道琼(划款)。";
	    	}
	    	if (BusinessTypeEnum.NQ.getValue()==businessType){
	    		return "投资迷你纳斯达克(划款)。";
	    	}
	    	if (BusinessTypeEnum.ES.getValue()==businessType){
	    		return "投资迷你标准普尔(划款)。";
	    	}
	    	if (BusinessTypeEnum.FDAX.getValue()==businessType){
	    		return "投资德国DAX指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.NK.getValue()==businessType){
	    		return "投资日经指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.MHI.getValue()==businessType){
	    		return "投资小恒指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.GC.getValue()==businessType){
	    		return "投资美黄金(划款)。";
	    	}
	    	if (BusinessTypeEnum.HH.getValue()==businessType){
	    		return "投资H股指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.MCH.getValue()==businessType){
	    		return "投资小H股指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.HG.getValue()==businessType){
	    		return "投资美铜(划款)。";
	    	}
	    	if (BusinessTypeEnum.SI.getValue()==businessType){
	    		return "投资美白银(划款)。";
	    	}
	    	if (BusinessTypeEnum.QM.getValue()==businessType){
	    		return "投资小原油(划款)。";
	    	}
	    	if (BusinessTypeEnum.FDXM.getValue()==businessType){
	    		return "投资迷你德国DAX指数(划款)。";
	    	}
	    	if (BusinessTypeEnum.NG.getValue()==businessType){
	    		return "投资天然气(划款)。";
	    	}
	    	return "";
	    }
	    /**
	    * 根据业务类型  获取对应的平仓结算时的备注信息
	    * @param businessType
	    * @return
	    */
	    public static String getBussinessSettleRemark(int businessType){
	 
	    	if (BusinessTypeEnum.CN.getValue()==businessType){
	    		return "新华富时A50平仓结算";
	    	}
	    	if (BusinessTypeEnum.CL.getValue()==businessType){
	    		return "国际原油平仓结算";
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return "香港恒生指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return "国际综合平仓结算";
	    	}
	    	if (BusinessTypeEnum.YM.getValue()==businessType){
	    		return "迷你道琼平仓结算";
	    	}
	    	if (BusinessTypeEnum.NQ.getValue()==businessType){
	    		return "迷你纳斯达克平仓结算";
	    	}
	    	if (BusinessTypeEnum.ES.getValue()==businessType){
	    		return "迷你标准普尔平仓结算";
	    	}
	    	if (BusinessTypeEnum.FDAX.getValue()==businessType){
	    		return "德国DAX指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.NK.getValue()==businessType){
	    		return "日经指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.MHI.getValue()==businessType){
	    		return "小恒指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.GC.getValue()==businessType){
	    		return "美黄金平仓结算";
	    	}
	    	if (BusinessTypeEnum.HH.getValue()==businessType){
	    		return "H股指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.MCH.getValue()==businessType){
	    		return "小H股指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.HG.getValue()==businessType){
	    		return "美铜平仓结算";
	    	}
	    	if (BusinessTypeEnum.SI.getValue()==businessType){
	    		return "美白银平仓结算";
	    	}
	    	if (BusinessTypeEnum.QM.getValue()==businessType){
	    		return "小原油平仓结算";
	    	}
	    	if (BusinessTypeEnum.FDXM.getValue()==businessType){
	    		return "迷你德国DAX指数平仓结算";
	    	}
	    	if (BusinessTypeEnum.NG.getValue()==businessType){
	    		return "天然气平仓结算";
	    	}
	    	return "国际期货平仓结算";
	    }
    
	    /**
	     * 根据合约类型  获取对应的 中文表达
	     * @param businessType
	     * @return
	     */
	    public static String getBusinessTypeToBusiness(int businessType){
	    	if (BusinessTypeEnum.CN.getValue()==businessType){
	    		return "富时A50";
	    	}
	    	if (BusinessTypeEnum.CL.getValue()==businessType){
	    		return "国际原油";
	    	}
	    	if (BusinessTypeEnum.HSI.getValue()==businessType){
	    		return "恒生指数";
	    	}
	    	if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
	    		return "国际综合";
	    	}
	    	if (BusinessTypeEnum.YM.getValue()==businessType){
	    		return "迷你道琼";
	    	}
	    	if (BusinessTypeEnum.NQ.getValue()==businessType){
	    		return "迷你纳斯达克";
	    	}
	    	if (BusinessTypeEnum.ES.getValue()==businessType){
	    		return "迷你标准普尔";
	    	}
	    	if (BusinessTypeEnum.FDAX.getValue()==businessType){
	    		return "德国DAX指数";
	    	}
	    	if (BusinessTypeEnum.NK.getValue()==businessType){
	    		return "日经指数";
	    	}
	    	if (BusinessTypeEnum.MHI.getValue()==businessType){
	    		return "小恒指数";
	    	}
	    	if (BusinessTypeEnum.GC.getValue()==businessType){
	    		return "美黄金";
	    	}
	    	if (BusinessTypeEnum.HH.getValue()==businessType){
	    		return "H股指数";
	    	}
	    	if (BusinessTypeEnum.MCH.getValue()==businessType){
	    		return "小H股指数";
	    	}
	    	if (BusinessTypeEnum.HG.getValue()==businessType){
	    		return "美铜";
	    	}
	    	if (BusinessTypeEnum.SI.getValue()==businessType){
	    		return "美白银";
	    	}
	    	if (BusinessTypeEnum.QM.getValue()==businessType){
	    		return "小原油";
	    	}
	    	if (BusinessTypeEnum.FDXM.getValue()==businessType){
	    		return "迷你德国DAX指数";
	    	}
	    	if (BusinessTypeEnum.NG.getValue()==businessType){
	    		return "天然气";
	    	}
	    	return "国际综合";
	    }
	    /**
	     * 根据交易品种获取对应得 合约编号
	     * @param commodityNo
	     * @return
	     */
	    public static Integer getCommodityNoToBusinessType(String commodityNo){
	    	if (CN.name().equalsIgnoreCase(commodityNo)){
	    		return CN.getValue();
	    	}
	    	if (CL.name().equalsIgnoreCase(commodityNo)){
	    		return CL.getValue();
	    	}
	    	if (HSI.name().equalsIgnoreCase(commodityNo)){
	    		return HSI.getValue();
	    	}
	    	if (MULTIPLE.name().equalsIgnoreCase(commodityNo)){
	    		return MULTIPLE.getValue();
	    	}
	    	if (YM.name().equalsIgnoreCase(commodityNo)){
	    		return YM.getValue();
	    	}
	    	if (NQ.name().equalsIgnoreCase(commodityNo)){
	    		return NQ.getValue();
	    	}
	    	if (FDAX.name().equalsIgnoreCase(commodityNo)){
	    		return FDAX.getValue();
	    	}
	    	if (NK.name().equalsIgnoreCase(commodityNo)){
	    		return NK.getValue();
	    	}
	    	if (MHI.name().equalsIgnoreCase(commodityNo)){
	    		return MHI.getValue();
	    	}
	    	if (GC.name().equalsIgnoreCase(commodityNo)){
	    		return GC.getValue();
	    	}
	    	if (HH.name().equalsIgnoreCase(commodityNo)){
	    		return HH.getValue();
	    	}
	    	if (MCH.name().equalsIgnoreCase(commodityNo)){
	    		return MCH.getValue();
	    	}
	    	if (HG.name().equalsIgnoreCase(commodityNo)){
	    		return HG.getValue();
	    	}
	    	if (SI.name().equalsIgnoreCase(commodityNo)){
	    		return SI.getValue();
	    	}
	    	if (QM.name().equalsIgnoreCase(commodityNo)){
	    		return QM.getValue();
	    	}
	    	if (FDXM.name().equalsIgnoreCase(commodityNo)){
	    		return FDXM.getValue();
	    	}
	    	if (NG.name().equalsIgnoreCase(commodityNo)){
	    		return NG.getValue();
	    	}
	    	return -1;
	    }
}
