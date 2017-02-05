<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>恒指期货方案列表 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=${v}" type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/public.css?v=${v}">
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet"
	type="text/css" />
<style type="text/css">
table.data_web {
    margin:4px;
    margin-right:8px;
}
table.data_web th,table.data_web td{
height:40px; line-height:40px;color:#34B3E0; border-bottom:1px dotted #f1f1f1; padding:5px 0;
}
table.data_web td {
 color:black;
 background-color:white;
 vertical-align:middle;
}
table.data_web td.selected{
  border-bottom:1px dotted #F1F1F1;
}
table.data_web td a { padding:0 5px; color:#f80; }
.tzdr-data01 { width:98%; margin:0 auto;}
#window_detail_accountInfo table { padding:5px 0 5px 30px;}
#window_detail_accountInfo table tr td { height:24px; line-height:24px;}
</style>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hsi/hsiCommon.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/hsi/hsiList.js?v=${v}"></script>
	
<script type="text/javascript">
    
    var isOut = false; 
    var index="${index}";
    <!--访问记录  -->
    $(document).ready(function(){
    	$("#oAccount").find("a").removeClass("on");
	    $('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
		});
	    $('.navlist li a').removeClass('on');
		$("#nav_my").addClass("on");
	    $("#hsi").parent().addClass("on");
	    $.easyui.rechangeSetValue(".tzdr-tab",null,function(tag){
	    	$("#appStateHSIPage").html("");
	    	$(".tzdr-data01").hide();
	    	var tabId = $(tag).attr("id");
	    	var id = tabId + "Data";
	    	var type = "";
	    	if(id == "appStateHSIData"){  //恒生指数
	    		type = "7";
	    		getHSIDataList(page_index,type,
			    		tabId,"appStateHSIPage","pay");
	    	}
	    	$("#" + id).show();
	    },null);
	    
    	$("#appStateHSI").trigger("click");
    });
    
</script>

</head>
<body>
	
	<!-- 恒生指数账单明细 -->
	<div class="sif_money sif_money_div ftse_money" id="detailInfoHSI" style="display:none;">
		<div class="fl_navtitle">
			<h3>账单明细</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#detailInfoHSI')" class="close"></a>
		</div>
		<ul id="window_detail_endInfoHSI">
			<li>
				<h3>操盘保证金</h3>
				<p></p>
			</li>
			<li>
				<h3>补充保证金</h3>
				<p></p>
			</li>
			<li style="width:240px;">
				<h3>交易盈亏</h3>
				<p></p>
			</li>
			<li style="width:120px;">
				<h3>汇率<em>(美元：人民币)</em></h3>
				<p></p>
			</li>
			<li>
				<h3>交易手续费</h3>
				<p></p>
			</li>			
			<li>
				<h3>结算金额</h3>
				<p></p>
			</li>
		</ul>
	</div>
	
	<!-- 恒生指数 -->
	<div class="sif_money sif_money_div" id="accountDetailInfoHSI" style="display:none;">
		<div class="fl_navtitle">
			<h3>交易账户</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#accountDetailInfoHSI')" class="close"></a>
		</div>
		<ul id="window_detail_accountInfoHSI">
		     <table style="font-size:13px;" border="1">		     	 
		     	 <tr>
		             <td>交易账号：</td>
		             <td><span>50728</span></td>
		         </tr>
		         <tr>
		             <td>交易密码：</td>
		             <td><span>123</span></td>
		         </tr>
		         <tr>
		             <td>交易软件：</td>
		             <td><a target="_blank" href="${ctx}/help?tab=software&leftMenu=1">交易软件下载</a></td>
		         </tr>
		         <tr>
		             <td></td>
		             <td><a target="_blank" href="${ctx}/help?tab=rule&leftMenu=0">恒指期货操盘细则</a></td>
		         </tr>
		     </table>
		</ul>
		<p class="fl_uc_userpromt"><!-- 注意：从2016.03.23 18:00开始，我们的操盘软件已更换成直达快抢手快速下单软件，请新申请操盘方案的用户务必下载新软件！ --></p>
		<a href="#" target="_blank" style="display:none; width:100%; height:40px; line-height:40px; font-size:14px; color:#f00; text-align: center; margin:10px auto -10px;">新年High翻天，开箱有壕礼！</a>
	</div>
	
	<!-- 追加保证金 -->
	<div class="fl_box fl_uc_trade sif_money_div" id="appendMoneyDetailInfo" name="appendMoneyDetailInfo" style="display:none;">
		<div class="fl_navtitle">
			<h3>补充保证金</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#appendMoneyDetailInfo')" class="close"></a>
		</div>
		<div class="fl_uc_main">			
			<p style="font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">系统将在下个交易日前为您的期货账户补充保证金;<br/>添加成功将短信通知您,最低补充金额为2000元;<br/>
			<span id = 'rate' style="color: #FF0000;"></span>
			<input type="hidden" id = 'rateValue'>
			</p>
			<ul class="fl_uc_list">
				<li>
					<label>账户资金：</label>
					<input type="hidden" id="append_avlBal" name="append_avlBal">
					<span><i>0.00</i>元</span>
				</li>
				<li>
					<label>补充保证金：</label>
					<input type="hidden" id="append_minAppendMoney" name="append_minAppendMoney" value="2000"> 
					<input type="text" id="appendMoney" name="appendMoney" maxlength="9"> 
					<span>元 </span>
				</li>
				<li>
					<label>换算美元：</label>
					<span><i id="convertDollar">0.00</i>美元</span>
				</li>
			</ul>			
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" status="true" id="f_appendMoney" data_no="" name="f_appendMoney" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);"  id="f_appendMoney_cance" name="f_appendMoney_cance" class="fl_uc_cancelbtn">取消</a>
		</div>
	</div>
	
	<!-- 终止操盘 -->
	<div class="fl_box fl_uc_trade sif_money_div" id="applyEndTrade" name="applyEndTrade"  style="display:none;">
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#applyEndTrade')" class="close"></a>
		</div>
		<div class="fl_uc_main">						
			<p style="font-size:20px;text-align: center;font-family: '微软雅黑'; ">确定要终结操盘方案？</p>
			<p id="f_parities" style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;color:#FF0000;display:none;" >当前汇率：1美元 = <i id="parities">6.3122</i>人民币</p>
			<p style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;">系统将在下个交易日开盘前终结</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" status="true" id="f_applyEndTrade" business_type="" data_no="" name="f_applyEndTrade" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);" id="f_applyEndTrade_cance" name="f_applyEndTrade_cance" class="fl_uc_cancelbtn">取消</a>
		</div>
		<a href="#" target="_blank" style="display:none; width:100%; height:40px; line-height:40px; font-size:14px; color:#f00; text-align: center; margin:10px auto -10px;">新年High翻天，开箱有壕礼！</a>
	</div>
    
	<!--顶部 -->
	<!-- DINGBU -->
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>

		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay">
				<ul class="uc_paynav">
					<li data="hsiAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateHSI" class="on">方案列表</a></li>
				</ul>
				
				<div id="appStateHSIData" class="tzdr-data01" style="padding:2px; width:98%; margin:0 auto;">
				    <table  cellspacing="0" class="data_web" cellpadding="0" width="100%" border="0" >
				        <thead>
					        <th>申请时间</th>
					        <th>已使用时间</th>
					        <th>开仓手数</th>
					        <th>方案状态</th>
					        <th>总操盘资金</th>
					        <th>亏损平仓线</th>
					        <th>操盘保证金</th>
					        <th>补充保证金</th>
					        <th>结算金额</th>
					        <th>操作</th>
				        </thead>
				        <tbody style="text-align:center;">
				            
				        </tbody>
				    </table>
				</div>
				
				<div id="appStateHSIPage"></div>
				<!-- 全部申请 -->
				<div class="uc_trade">					
					<div id="content"></div>
				</div>
				<div id="Pagination" style="margin: 20px 0;"></div>
			</div>

		</div>
		<!-- 遮罩层 -->
		<div class="fl_mask" style="display:none; z-index:20;"></div>

	</div>
	<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

