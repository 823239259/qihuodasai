<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付确认 - 投资达人</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/buy.css">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script src="${ctx}/static/script/togetherTrade/pay.js?v=${v}" type="text/javascript"></script>

</head>

<body>
<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
<div class="floatlayer">
    <form method="post" action="${ctx}/usertogether/success">
    <input type="hidden" name="recommendMoney" id="recommendMoney" value="${recommendMoney}"/>
    	<input type="hidden" name="totalMoney" id="totalMoney" value="${totalMoney}"/>
		<input type="hidden" name="lever" id="lever" value="${lever}"/>
		<input type="hidden" name="days" id="days" value="${days}"/>
		<input type="hidden" name="isVerified" id="isVerified" value="${isVerified}"/>
		<input type="hidden" name="avlBal" id="avlBal" value="${avlBal}"/>
		<input type="hidden" name="needPay" id="needPay" value="${needPay}"/>
		<input type="hidden" name="need" id="need" value="${need}"/>
		<input type="hidden" name="needNext" id="needNext" value="${needNext}"/>
	<div class="fl_mask" id="fl_mask" style="display:none;"></div>
    <div id="paydiv" class="fl_tkbox" style="display:none;">
        <div class="fl_tknav"><em>确认提示</em><a href="javascript:void(0);" id="cancelx"></a></div>
        <div class="fl_tkctn">
            <ul class="fl_tkmoney">
                <li>
                    <label>账户余额：</label>
                    <span><fmt:formatNumber   value="${avlBal}" pattern="###,###.##" />元</span>
                </li>
                <li>
                    <label>支付金额：</label>
                    <span><fmt:formatNumber   value="${needPay}" pattern="###,###.##" />元</span>
                </li>
            </ul>
            <p id="noNeedPay" class="fl_moneypromt" style="display:none;"></p>
        </div>
        <div class="fl_tkbtn">
            <a href="javascript:void(0);" id="cancel"class="fl_b_cancel">取消</a>
            <a href="javascript:void(0);" id="sure" class="fl_b_sure" style="background:#eae9e9;">支付确认</a>
        </div>
    </div>
    </form>
</div>
<div class="buy_pay">
    <div class="bp_box">
        <h2>操盘规则</h2>
        <table  border="0" cellspacing="0" cellpadding="0" class="bp_rule">
            <thead>         
                <tr>
                    <td>总操盘金额</td>
                    <td>亏损补仓线</td>
                    <td>亏损平仓线</td>
                    <td>操盘天数</td>
                    <td>开始操盘时间</td>
                </tr>
            </thead>        
            <tbody>
                <tr>
                    <td><i><fmt:formatNumber    value="${totalMoney}" pattern="###,###" /></i>元</td>
                    <td><i><fmt:formatNumber    value="${shortLine}" pattern="###,###" /></i>元</td>
                    <td><i><fmt:formatNumber   value="${openLine}" pattern="###,###" /></i>元</td>
                    <td><i>${days}</i>天</td>
                    <td><i>合买满标时</i></td>
                </tr>
            </tbody>
        </table>
    </div>    
    <div class="bp_box">
        <h2>注意事项</h2>
        <table  border="0" cellspacing="0" cellpadding="0" class="bp_pre">
            <thead>         
                <tr>
                    <td>合买者权益</td>
                    <td>账户管理费</td>
                </tr>
            </thead>        
            <tbody>
                <tr>
                    <td>申请时按操盘天数一次性收取，延期后再按<i><fmt:formatNumber   value="${interestFee}" pattern="###,###.##" /></i>元/天收取 </td>
                    <td><i><fmt:formatNumber   value="${manageFee}" pattern="###,###.##" /></i>元/交易日，使用一个交易日扣一次</td>
                </tr>
                <tr>
                    <td colspan="3">请保持账户余额充足，当账户余额不足以扣除利息或管理费时，系统将主动为您终结合买方案</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="bp_box">
        <h2>本次支付清单</h2>
        <div class="bp_pay">            
            <table  border="0" cellspacing="0" cellpadding="0" class="bp_p_moneylist">
                <thead>         
                    <tr>
                        <td>操盘者出资</td>
                        <td>合买者权益</td>
                        <td>今日账户管理费</td>
                    </tr>
                </thead>        
                <tbody>
                    <tr>
                        <td><i><fmt:formatNumber   value="${recommendMoney}" pattern="###,###" /></i>元</td>
                        <td><i><fmt:formatNumber   value="${totalInterestFee}" pattern="###,###.##" /></i>元</td>
                        <td><i>0</i>元</td>
                    </tr>
                </tbody>
            </table>
            <div class="bp_payfont">=</div>      
            <table  border="0" cellspacing="0" cellpadding="0" class="bp_p_money">
                <thead>         
                    <tr>
                        <td>本次应付总金额</td>
                    </tr>
                </thead>        
                <tbody>
                    <tr>
                        <td><i><fmt:formatNumber   value="${needPay}" pattern="###,###.##" /></i>元</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="bp_btn">
        <a href=" javascript:void(0);" class="bp_b_next" id="sub">下一步</a>
        <a href="${ctx}/together/index" class="bp_b_bank">返回修改</a>
    </div>
</div>
<%@ include file="../common/personfooter.jsp"%>
</body>
</html>
    