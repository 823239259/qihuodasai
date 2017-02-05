<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*"%>  
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
<%@page import="com.tzdr.common.utils.SpringUtils"%>
<%@page import="com.tzdr.business.service.drawMoney.impl.DrawMoneyServiceImpl"%>
<%@page import="com.tzdr.business.service.drawMoney.*"%>
<%@page import="com.tzdr.common.exception.EmailExceptionHandler"%>

<html>  
<%  
    //获取联动平台支付结果通知数据（商户应采取循环遍历方式获取平台通知数据,不应采取固定编码的方式获取固定字段，  
    //否则当平台通知数据发生变化时，容易出现接收数据验签不通过情况）  
    Map<String,String> ht = new HashMap<String,String>();  
    String name = "",values="";  
    for(Enumeration names = request.getParameterNames(); names.hasMoreElements(); ht.put(name, values))  
    {  
        name = (String)names.nextElement();  
        values = request.getParameter(name);  
        System.out.println(name+"==="+values);
        
    }  
    //获取UMPAY平台请求商户的支付结果通知数据,并对请求数据进行验签,此时商户接收到的支付结果通知会存放在这里,商户可以根据此处的trade_state订单状态来更新订单。  
     Map<String,String> reqData = new HashMap<String,String>();  
     Map<String,String> resData = new HashMap<String,String>();  
     String tradeNo="";
     String orderId="";
     String merDate="";
     String amount="";
     String tradeState="";
    try{  
        //联动交易号
        tradeNo= request.getParameter("trade_no");
       //订单号
        orderId= request.getParameter("order_id");
       //商户订单日期
        merDate= request.getParameter("mer_date");
       //付款金额
        amount= request.getParameter("amount");
        tradeState= request.getParameter("trade_state");
        System.out.println("payWithdrawNotify.jsp"+"trade_no:" + tradeNo+"-orderId:"+orderId+"-merDate:"+merDate+"-amount:"+amount+"-tradeState:"+tradeState);
        
        //如验证平台签名正确，即应响应UMPAY平台返回码为0000。【响应返回码代表通知是否成功，和通知的交易结果（支付失败、支付成功）无关】  
        //验签支付结果通知 如验签成功，则返回ret_code=0000  
        reqData = com.umpay.api.paygate.v40.Plat2Mer_v40.getPlatNotifyData(ht);  
        resData.put("ret_code","0000");
        //这个地方要写我们自己处理成功的，回写到数据库中去（还要处理多次来通知的情况）
        /*交易状态
        NO	枚举名称	说明	备注
    	1	WAIT_BUYER_PAY	交易创建，等待买家付款。	
    	2	TRADE_SUCCESS	交易成功，不能再次进行交易	
    	3	TRADE_CLOSED	交易关闭
    	在指定时间段内未支付时关闭的交易；	交易关闭，商户支付已经过期的订单后，订单状态才会改变为交易关闭
    	4	TRADE_CANCEL	交易撤销	
    	5	TRADE_FAIL	交易失败	
    	*/

        
        DrawMoneyService drawMoneyService = SpringUtils.getBean(DrawMoneyServiceImpl.class);
        drawMoneyService.updatDraw(orderId,"", tradeNo, tradeState, merDate);

    }catch(Exception e){  
        //如果验签失败，则抛出异常，返回ret_code=1111  
        System.out.println("验证签名发生异常" + e);
        resData.put("ret_code","1111");
        String dataDetail="trade_no:" + tradeNo+"-orderId:"+orderId+"merDate="+merDate+"tradeState="+tradeState+"amount="+amount;
        EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "调用取款接口失败", "payWithdrawNotify.jsp"+"dataDetail", dataDetail);
		
    }  
    

    //加上平台发给我们支付失败的标记
    //验签后的数据都组织在resData中。  
    //生成平台响应UMPAY平台数据,将该串放入META标签，以下几个参数为结果通知必备参数。  
    resData.put("mer_id", request.getParameter("mer_id"));  
    resData.put("sign_type", request.getParameter("sign_type"));  
    resData.put("version", request.getParameter("version"));  
    //支付状态通知响应需提交order_id和mer_date，退款状态通知无需提交  
    resData.put("order_id", request.getParameter("order_id"));  
    resData.put("mer_date", request.getParameter("mer_date"));
    String data = com.umpay.api.paygate.v40.Mer2Plat_v40.merNotifyResData(resData);  
%>  
  
<head>  
  <META NAME="MobilePayPlatform" CONTENT="<%=data%>" />  
  <title>result</title>  
</head>  
  