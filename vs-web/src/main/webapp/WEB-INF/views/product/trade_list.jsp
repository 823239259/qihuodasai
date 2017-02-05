<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品期货方案列表 - 维胜</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/public.css?v=${v}">
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=${v}" type="text/css">
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
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>

<script type="text/javascript"
	src="${ctx}/static/script/product/common.js?v=${v}"></script>
	
<script type="text/javascript"
	src="${ctx}/static/script/product/list.js?v=${v}"></script>
	
	
<script type="text/javascript">
    
    var isOut = false; 
    <!--访问记录  -->
    $(document).ready(function(){
    	$("#oAccount").find("a").removeClass("on");
	    $('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
		});
	    $('.navlist li a').removeClass('on');
		$("#nav_my").addClass("on");
	    $("#product").parent().addClass("on");
	    $.easyui.rechangeSetValue(".tzdr-tab",null,function(tag){
	    	$("#appStateCommodityPage").html("");
	    	/* $("#appStateRubberPage").html("");
	    	$("#appStateSliverPage").html("");
	    	$("#appStateCopperPage").html(""); */
	    	$(".tzdr-data01").hide();
	    	var tabId = $(tag).attr("id");
	    	var id = tabId + "Data";
	    	var type = "";
	    	//1.期指随心乐
	    	if (id == "appStateCommodityData") { //商品期货
	    		type = "1";
	    		getDataList(page_index,type,
			    		tabId,"appStateCommodityPage","pay");
	    	}/* else if(id == "appStateSliverData"){  //沪银
	    		type = "2";
	    		getDataList(page_index,type,
			    		tabId,"appStateSliverPage","pay");
	    	}else if(id == "appStateCopperData"){  //沪铜
	    		type = "3";
	    		getDataList(page_index,type,
			    		tabId,"appStateCopperPage","pay");
	    	}else if(id == "appStateRubberData"){  //橡胶
	    		type = "4";
	    		getDataList(page_index,type,
			    		tabId,"appStateRubberPage","pay");
	    	} */
	    	$("#" + id).show();
	    },null);
	    $("#appStateCommodity").trigger("click"); //初始化显示商品期货页面
    });
  
    
    
</script>

</head>
<body>
    <!-- <div class="fl_mask" style="display:none;"></div> -->
    <!-- 账单明细-->
    <div class="sif_money sif_money_div" id="detailInfo" style="display:none;">
		<div class="fl_navtitle">
			<h3>账单明细</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#detailInfo')" class="close"></a>
		</div>
		<ul id="window_detail_endInfo">
			<li>
				<h3>操盘保证金</h3>
				<p></p>
			</li>
			<li>
				<h3>补充保证金</h3>
				<p></p>
			</li>
			<li>
				<h3>交易盈亏</h3>
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
	
	<!--交易账户 -->
   <div class="sif_money sif_money_div" id="accountDetailInfo" style="display:none;">
		<div class="fl_navtitle">
			<h3>交易账户</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#accountDetailInfo')" class="close"></a>
		</div>
		<ul id="window_detail_accountInfo">
		     <table style="font-size:13px;" border="1">
		         <tr>
		             <td>登录账号：</td>
		             <td>10018019</td>
		         </tr>
		         <tr>
		             <td>登录密码：</td>
		             <td>210926</td>
		         </tr>
		         <tr>
		             <td>交易账户：</td>
		             <td><span>1222</span>(登录后点交易)</td>
		         </tr>
		         <tr>
		             <td>交易密码：</td>
		             <td><span>009988</span></td>
		         </tr>
		         <tr>
		             <td>交易软件：</td>
		             <td><a target="_blank" href="${ctx}/help?tab=software&leftMenu=1">交易软件下载</a>   </td>
		         </tr>		         
		         <tr>
		             <td></td>
		             <td><a target="_blank" href="${ctx}/help?tab=rule&leftMenu=7" id="lookExplain">查看交易说明</a>   </td>
		             
		         </tr>
		     </table>
		</ul>
	</div>
	<!-- 追加保证金 -->
	<div class="fl_box fl_uc_trade sif_money_div" id="appendMoneyDetailInfo" name="appendMoneyDetailInfo" style="display:none;">
		<div class="fl_navtitle">
			<h3>补充保证金</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#appendMoneyDetailInfo')" class="close"></a>
		</div>
		<div class="fl_uc_main">			
			<p style="font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">系统将在下个交易日前为您的期货账户<br>补充保证金,添加成功将短信通知您</p>
			<ul class="fl_uc_list">
				<li>
					<label>账户资金：</label>
					<input type="hidden" id="append_avlBal" name="append_avlBal">
					<span><i>0.00</i>元</span>
				</li>
				<li>
					<label>补充保证金：</label>
					<input type="hidden" id="append_minAppendMoney" name="append_minAppendMoney" value="2000"> 
					<input type="text" id="appendMoney" name="appendMoney"> 
					<span>元</span>
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
			<p style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;">系统将在下个交易日开盘前终结</p>
			<p class="fl_tkuser">使用折扣券：
               	<select id="discount">
             		<option value="">无折扣券</option>
               	</select>
			</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" status="true" id="f_applyEndTrade"  data_no="" name="f_applyEndTrade" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);" id="f_applyEndTrade_cance" name="f_applyEndTrade_cance" class="fl_uc_cancelbtn">取消</a>
		</div>
         <p class="fl_uspromtfont">注：折扣券一般由维胜平台活动发放，请大家多多关注</p>。
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
					<!-- <li data="goldAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateGold" class="on">沪金</a></li>
					<li data="sliverAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateSliver">沪银</a></li>
					<li data="copperAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateCopper" >沪铜</a></li>
					<li data="RubberAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateRubber" >橡胶</a></li> -->
					
					<li data="commodityAll"><a href="javascript:void(0);" class="tzdr-tab" id="appStateCommodity" class="on">商品期货</a></li>
				</ul>
				<!-- 商品期货-->
				<div id="appStateCommodityData" class="tzdr-data01" style="padding:2px; width:98%; margin:0 auto;">
				    <table  cellspacing="0" class="data_web" cellpadding="0" width="100%" border="0" >
				        <thead>
				        	<th>交易品种</th>
					        <th>申请时间</th>
					        <th>已使用时间</th>
					        <th>开仓手数</th>
					        <th>方案状态</th>
					        <th>总操盘资金</th>
					        <th>操盘保证金</th>
					        <th>补充保证金</th>
					        <th>亏损平仓线</th>
					        <th>结算金额</th>
					        <th>操作</th>
				        </thead>
				        <tbody style="text-align:center;">
				            <td></td>
				        </tbody>
				    </table>
				</div>
				
				
				<!-- 沪银-->
				<!-- <div id="appStateSliverData" class="tzdr-data01" style="padding:2px; width:98%; margin:0 auto;display:none;">
				    <table  cellspacing="0" class="data_web" cellpadding="0" width="100%" border="0" >
				        <thead>
					        <th>申请时间</th>
					        <th>已使用时间</th>
					        <th>开仓手数</th>
					        <th>方案状态</th>
					        <th>总操盘资金</th>
					        <th>操盘保证金</th>
					        <th>补充保证金</th>
					        <th>亏损平仓线</th>
					        <th>结算金额</th>
					        <th>操作</th>
				        </thead>
				        <tbody style="text-align:center;">
				            
				        </tbody>
				    </table>
				</div>
				沪铜
				<div id="appStateCopperData" class="tzdr-data01" style="padding:2px; width:98%; margin:0 auto;display:none;">
				    <table  cellspacing="0" class="data_web" cellpadding="0" width="100%" border="0" >
				        <thead>
					        <th>申请时间</th>
					        <th>已使用时间</th>
					        <th>开仓手数</th>
					        <th>方案状态</th>
					        <th>总操盘资金</th>
					        <th>操盘保证金</th>
					        <th>补充保证金</th>
					        <th>亏损平仓线</th>
					        <th>结算金额</th>
					        <th>操作</th>
				        </thead>
				        <tbody style="text-align:center;">
				            
				        </tbody>
				    </table>
				</div>
				橡胶
				<div id="appStateRubberData" class="tzdr-data01" style="padding:2px; width:98%; margin:0 auto;display:none;">
				    <table  cellspacing="0" class="data_web" cellpadding="0" width="100%" border="0" >
				        <thead>
					        <th>申请时间</th>
					        <th>已使用时间</th>
					        <th>开仓手数</th>
					        <th>方案状态</th>
					        <th>总操盘资金</th>
					        <th>操盘保证金</th>
					        <th>补充保证金</th>
					        <th>亏损平仓线</th>
					        <th>结算金额</th>
					        <th>操作</th>
				        </thead>
				        <tbody style="text-align:center;">
				            
				        </tbody>
				    </table>
				</div> -->
				
				
				<div id="appStateCommodityPage"></div>
				
				<!-- <div id="appStateSliverPage"></div>
				<div id="appStateCopperPage"></div>
				<div id="appStateRubberPage"></div> -->
				
				<!-- 全部申请 -->
				<div class="uc_trade">					
					<div id="content"></div>
				</div>
				<div id="Pagination" style="margin: 20px 0;"></div>
			</div>

		</div>
		<!-- 遮罩层 -->
		<div class="fl_mask" style="display:none;"></div>

	</div>
	<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

