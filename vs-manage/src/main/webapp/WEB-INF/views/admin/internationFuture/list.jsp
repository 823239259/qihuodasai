<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>国际原油</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/internationFuture/ifList.js?v=20170228"></script>
<script type="text/javascript" src="${ctx}/static/script/quote.trade/n_quote.js?v=20170228"></script>
<script type="text/javascript" src="${ctx}/static/script/quote.trade/n_trade_vo.js?v=20170228"></script>
<script type="text/javascript" src="${ctx}/static/script/quote.trade/n_trade.js?v=20170228"></script>
<script type="text/javascript" src="${ctx}/static/script/quote.trade/n_utils.js?v=20170228"></script>
<script type="text/javascript" src="${ctx}/static/script/common/ajaxfileupload.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
function timeConvert(value,rowData,rowIndex) {
    if (value != null && value != ''){
    	return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss')
    }else if (value == 2){
    	return ""; 
    }  
}   
</script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:internationFuture:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="申请列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<form id="auditSearchForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">					
								</td>
								<td class="label right">
									<span>手机号码：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>提交时间：</span>
								</td>
								<td>
									<input id="startTime" name="search_date_GTE_appTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
								      至  <input id="endTime" name="search_date_LTE_appTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
								</td>
								<td class="label right">
									<span>开仓手数</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_EQ_tranLever" name="search_EQ_tranLever">					
								</td>
								
							</tr>
							<tr>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_EQ_businessType"
									class="easyui-combobox" name="search_EQ_businessType"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="0">富时A50</option>
										<option value="6">国际原油</option>
										<option value="7">恒生指数</option>
										<option value="8">国际综合</option>
										<option value="9">小恒指</option>
								</select></td>
								
								<td class="label right"><span>平台来源：</span></td>
								<td><select id="search_EQ_source"
									class="easyui-combobox" name="search_EQ_source"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="1">网站平台</option>
										<option value="2">APP平台</option>
								</select></td>
								
							</tr>
							<tr>
							<td class="label right">
									<span> </span>
								</td>
								<td >
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','auditSearchForm')">查询</a>
							</td>
							</tr>
						</table>	
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:internationFuture:pass">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">分配账户</a> 
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:refuse">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="refuse()">拒绝申请</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:export">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel"   plain="true" onclick="$.easyui.exportExcel('edatagrid','auditSearchForm')">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:edit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/internation/future/getDatas?type=0"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]]">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="mobile" width="150" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="businessType" width="150">交易品种</th>
							<th field="traderBond" width="150">操盘保证金(元)</th>
							<th field="tranFees" width="150" data-options="formatter:function(value,row,index){
								if (row.businessType=='国际综合'){
									return '';
								}
								return value;
							}">申请手续费(元)</th>
							<th field="voucherActualMoney" width="150">实际代金券(元)</th>
							<th field="bondDollar" width="150">入金金额(美元)</th>
							<th field="lossMoney" width="160">授信金额(美元)</th>
							<th field="lineLoss" width="160">亏损平仓线(美元)</th>
							<th field="traderTotal" width="160">总操盘资金(美元)</th>
							<th field="tranLever" width="160">可开仓手数</th>
							<th field="tranAccount" width="120">操盘账户</th>
							<th field="tranPassword" width="120">操盘密码</th>
							<th field="appTime" width="200" sortable="true">提交时间</th>
							<th field="updateTime" width="200" sortable="true">处理时间</th>
							<th field="sourceStr" width="150">平台来源</th>
							<th field="programNo" width="150">方案编号</th>
							<th field="stateType" width="100">状态</th>
							<th field="operator" width="100">操作员</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="补充保证金" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="appendMoneytb" style="padding: 5px; height: auto">
					<form id="appendMoneySearchForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>操盘账号：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_tranAccount" name="search_LIKE_tranAccount">
								</td>
								<td class="label right"><span>申请追加时间：</span></td>
								<td><input id="startAppendDate" name="search_date_GTE_appendDate"
									class="Wdate" type="text"
									onFocus="var endDate=$dp.$('endAppendDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppendDate\')}'})" />
									至 <input id="endAppendDate" name="search_date_LTE_appendDate"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppendDate\')}'})" />
								</td>
							</tr>
							<tr>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_EQ_type"
									class="easyui-combobox" name="search_EQ_type"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="0">富时A50</option>
										<option value="6">国际原油</option>
										<option value="7">恒生指数</option>
										<option value="8">国际综合</option>
										<option value="9">小恒指</option>
								</select></td>
								<td class="label right"><span>状态：</span></td>
								<td><select id="search_IN_status"
									class="easyui-combobox" name="search_IN_status"
									style="width: 100px; height: 25px;">
										<option value="0,1">全部</option>
										<option value="0">未处理</option>
										<option value="1">已处理</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right"><span>平台来源：</span></td>
								<td><select id="search_EQ_source"
									class="easyui-combobox" name="search_EQ_source"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="1">网站平台</option>
										<option value="2">APP平台</option>
								</select></td>
							
								<td class="label right"><span> </span></td>
								<td colspan="3" class="label"><a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('appendMoneydatagrid','appendMoneySearchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:internationFuture:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="updateAppendMoneyStatus()">已处理</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:view">
							<a href="javascript:void(0)" class="easyui-linkbutton" 
								iconCls="icon-excel" 
								onclick="$.easyui.exportExcel('appendMoneydatagrid','appendMoneySearchForm')">导出</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="appendMoneydatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#appendMoneytb" url="${ctx}/admin/internation/future/getAppendMoneyDatas"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#appendMoneytb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]]">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="mobile" width="150" sortable="true">手机号码</th>
							<th field="username" width="150">客户姓名</th>
							<th field="typeValue" width="150">交易品种</th>
							<th field="tranAccount" width="120">操盘账户</th>
							<th field="lineLoss" width="120">亏损平仓线（$）</th>
							<th field="appendMoney" width="120">补充保证金（￥）</th>
							<th field="dollarMoney" width="120">补充保证金（$）</th>
							<th field="appendDateValue" width="120" sortable="true">申请追加时间</th>
							<th field="updateTimeValue" width="120" sortable="true">处理时间</th>
							<th field="statusValue" width="100" sortable="true">状态</th>
							<th field="sourceStr" width="150">平台来源</th>
							<th field="operator" width="100">操作员</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="方案管理" >
				<div id="hasAuditToolbar" style="padding: 5px; height: auto">
					<form id="queryForm3" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>手机号码：</span></td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">
								</td>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_EQ_businessType"
									class="easyui-combobox" name="search_EQ_businessType"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="0">富时A50</option>
										<option value="6">国际原油</option>
										<option value="7">恒生指数</option>
										<option value="8">国际综合</option>
										<option value="9">小恒指</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">							      
								</td>
								<td class="label right">
									<span>启用日期：</span>
								</td>
								<td>
									<input id="appStarttime" name="search_date_GTE_appStarttime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('appEndtime');WdatePicker({onpicked:function(){appEndtime.focus();},maxDate:'#F{$dp.$D(\'appEndtime\')}'})"/>
									      至  <input id="appEndtime" name="search_date_LTE_appStarttime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'appStarttime\')}'})"/>
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>操盘账户：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tranAccount" name="search_LIKE_tranAccount">
								</td>
								<td class="label right">
									<span>方案申请日期：</span>
								</td>
								<td>
									<input id="startAppTime" name="search_date_GTE_appTime" class="Wdate" type="text" onFocus="var endAppTime=$dp.$('endAppTime');WdatePicker({onpicked:function(){endAppTime.focus();},maxDate:'#F{$dp.$D(\'endAppTime\')}'})"/>
									      至  <input id="endAppTime" name="search_date_LTE_appTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppTime\')}'})"/>
								
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>开仓手数：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_EQ_tranLever" name="search_EQ_tranLever">					
								</td>
								<td class="label right">
									<span>申请结算日期：</span>
								</td>
								<td>
									<input id="startTime2" name="search_date_GTE_appEndTime" class="Wdate" type="text" onFocus="var endTime5=$dp.$('endTime2');WdatePicker({onpicked:function(){endTime2.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}'})"/>
									      至  <input id="endTime2" name="search_date_LTE_appEndTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}'})"/>
								
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>结算状态：</span>
								</td>
								<td >
									<select id="search_EQ_stateType" class="easyui-combobox" name="search_EQ_stateType" style="width:100px;height:27px;">
									    <option value="" selected="selected">所有状态</option>
									    <option value="4">操盘中</option>
									    <option value="2">申请结算</option>
									    <option value="3">待结算</option>
									    <option value="6">已结算</option>
									</select>
								</td>
								
								<td class="label right"><span>平台来源：</span></td>
								<td><select id="search_EQ_source"
									class="easyui-combobox" name="search_EQ_source"
									style="width: 100px; height: 25px;">
										<option value="">全部</option>
										<option value="1">网站平台</option>
										<option value="2">APP平台</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right">
									<span></span>
								</td>
								<td colspan="3">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('hasAuditData','queryForm3')">查询</a>
								</td>
							</tr>
						</table>	
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:internationFuture:input">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="refuseInput()">拒绝结算</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:input">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="autoinput()">自动导入</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:input">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="input()">手动导入</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:end">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="tradeOpenEnd()">结算</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:export">  
							<a  href="javascript:void(0)" onclick="$.easyui.exportExcel('hasAuditData','queryForm3')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:internationFuture:view">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="tradeCount()">交易手数</a>
						</shiro:hasPermission>
						<a  href= "${ctx }/admin/internation/future/downLoadTempleExcl" class="easyui-linkbutton"  iconCls="icon-excel" plain="true">下载模板</a>
					</div>
				</div>
				<div>
					<table id="hasAuditData" class="easyui-datagrid" pagination="true"
			            width="100%" toolbar='#hasAuditToolbar' url="${ctx}/admin/internation/future/getDatas?type=1"
			            rownumbers="true" fitColumns="true" singleSelect="true"
						data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]]">
				        <thead>
				            <tr>
				            	<th field="id" hidden="true"></th>
								<th field="mobile" width="150" >手机号码</th>
								<th field="tname" width="150">客户姓名</th>
								<th field="businessType" width="150">交易品种</th>
								<th field="tranAccount" width="150">操盘账户</th>
								<th field="tranFees" width="150" data-options="formatter:function(value,row,index){
									if (row.businessType=='国际综合'){
										return '';
									}
									return value;
								}">申请手续费(元)</th>
								<th field="appTime" width="150" sortable="true">方案申请时间</th>
								<th field="appStarttime" width="150" sortable="true">账户启用日期</th>
								<th field="tranLever" width="160">可开仓手数</th>
								<th field="useTranDay" width="150">已操盘时间</th>
								<th field="traderBond" width="150">操盘保证金(元)</th>
								<th field="voucherActualMoney" width="150">实际代金券(元)</th>
								<th field="appendTraderBond" width="150">补充保证金(元)</th>
								<th field="traderTotal" width="160">总操盘资金(美元)</th>
								<th field="tranProfitLoss" width="160">交易盈亏(美元)</th>
								<th field="tranActLever" width="160">交易手数</th>
								
								<!-- <th field="tranActualLever" width="160">A50交易手数</th>
								<th field="hsiTranActualLever" width="160">恒指交易手数</th>
								<th field="crudeTranActualLever" width="160">原油交易手数</th>
								<th field="mdtranActualLever" width="160">迷你道指交易手数</th>
								<th field="mntranActualLever" width="160">迷你纳指交易手数</th>
								<th field="mbtranActualLever" width="160">迷你标普交易手数</th>
								<th field="daxtranActualLever" width="160">德国DAX交易手数</th>
								<th field="nikkeiTranActualLever" width="160">日经225交易手数</th> -->
								
								<th field="tranFeesTotal" width="160">交易手续费(元)</th>
								<th field="discountMoneyStr" width="150">优惠券</th>
								<th field="discountActualMoney" width="150">抵扣手续费(元)</th>
								<th field="endParities" width="160">汇率</th>
								<th field="endAmountCal" width="160">结算金额(元)</th>
								<th field="endAmount" width="160">实际结算金额(元)</th>
								<th field="appEndTime" width="160" sortable="true">申请结算时间</th>
								<th field="endTime" width="160" sortable="true">结算时间</th>
								<th field="sourceStr" width="150">平台来源</th>
								<th field="programNo" width="150">方案编号</th>
								<th field="stateType" width="100">结算状态</th>
								<th field="endType" width="100" data-options="formatter:function(value,row,index){
									if (row.endType == 1){
										return '自动';
									}else if(row.endType == 0){
										return '手动';
									}else
									   return '';
								}">结算方式</th>
								<th field="operator" width="100">操作员</th>
				            </tr>
				            
				        </thead>
	   				</table>
				</div>
			</div>
	</div>	
	<!-- window 申请 分配帐号弹框-->
	<div id="passWin" class="easyui-window" title="分配/修改 账号" 
		style="width:350px;height:150px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <input type="hidden" name="type" id="type_win"/>
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">账号:</td>
                <td>
                   <input id="tranAccount" name="tranAccount" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">密码:</td>
                <td>
                   <input id="tranPassword" name="tranPassword" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	<div class = "easyui-window" id = "inputWin"  title="录入结果" style="width:800px;height:500px;display:none;border:none; overflow:scroll;top:4%"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        		<table  id="mainTable" border="0" style="font-size:12px;td:width=30px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        			 	<tr>
        			 		<td>手机号码</td>
        			 		<td><input id="mobile" name="mobile"  class="easyui-textbox" disabled="disabled" /></td>
        			 		<td>操盘账号</td>
        			 		<td><input id="Account" name="Account"  class="easyui-textbox" disabled="disabled" /></td>
        			 	</tr>
        			 	<tr>
        			 		<td>操盘保证金</td>
        			 		<td><input id="traderBond" name="traderBond"  class="easyui-textbox" disabled="disabled" /></td>
        			 		<td>交易盈亏($)</td>
        			 		<td><input id="tranProfitLoss" name="tranProfitLoss"  class="easyui-textbox"  data-options="required:true" /></td>
        			 	</tr>
        				<tr id = "input_file_tr">
        			 		<td><input type = "file" id = "input_file" name = "input_file"/><button  onclick="importExcl()" id = "input_import">导入明细</button></td>
        			 	</tr>
        			 	
        		</table>
        		<table  id="freeTable" border="0" style="font-size:12px;td:width=30px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        			<tr>
	        			<td class="label right"  id="a50td">交易手数:</td>
			                <td>
			                <input id="tranActualLever" name="tranActualLever"  class="easyui-validatebox"  data-options="required:true" />
		                </td>
		                <td class="label right hsiTradeNumTR" >恒指交易:</td>
		                <td>
		                    <input id="hsiTranActualLever" name="hsiTranActualLever"  class="easyui-validatebox hsiTradeNumTR"   data-options=""/>
		                </td>
			       </tr> 
			           <tr id="mdTradeNumTR">
			           
			           		<td class="label right">迷你道指交易:</td>、
			                <td>
			                   <input id="mdtranActualLever" name="mdtranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">迷你纳指交易:</td>
			                <td>
			                   <input id="mntranActualLever" name="mntranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			            </tr>
			           	<tr id="mbTradeNumTR">
			           		<td class="label right">国际原油交易:</td>
				            <td>
				                <input id="crudeTranActualLever" name="crudeTranActualLever"  class="easyui-validatebox"  data-options=""/>
				            </td>
			                <td class="label right">迷你标普交易:</td>
			                <td>
			                   <input id="mbtranActualLever" name="mbtranActualLever" class="easyui-validatebox"   data-options=""/>
			                </td>
			            </tr>
			            <tr id="nikkeiTradeNumTR">
			            	<td class="label right">德国DAX交易:</td>
			                <td>
			                   <input id="daxtranActualLever" name="daxtranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">日经225交易:</td>
			                <td>
			                   <input id="nikkeiTranActualLever" name="nikkeiTranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
			            <tr id="agTradeNumTR">
			             	<td class="label right">小恒指交易:</td>
			                <td>
			                   <input id="lhsiTranActualLever" name="lhsiTranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">美黄金交易:</td>
			                <td>
			                   <input id="agTranActualLever" name="agTranActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
				         <tr id="xHsTradeNumTR">
				        	 <td class="label right">H股指交易:</td>
			                <td>
			                   <input id="heStockMarketLever" name="heStockMarketLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">小H股指交易:</td>
			                <td>
			                   <input id="xhStockMarketLever" name="xhStockMarketLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
						 <tr  id="asTradeNumTR">
						    <td class="label right">美铜交易:</td>
			                <td>
			                   <input id="AmeCopperMarketLever" name="AmeCopperMarketLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">美白银交易:</td>
			                <td>
			                   <input id="AmeSilverMarketLever" name="AmeSilverMarketLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
						 <tr id="daxMinTradeNumTR">
						 	<td class="label right">小原油交易:</td>
			                <td>
			                   <input id="smallCrudeOilMarketLever" name="smallCrudeOilMarketLever" class="easyui-validatebox"  data-options=""/>
			                </td>
			                <td class="label right">迷你德国DAX指数:</td>
			                <td>
			                   <input id="daxtranMinActualLever" name="daxtranMinActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
						 <tr id="gasTradeNumTR">
						 	<td class="label right">天然气交易:</td>
			                <td>
			                   <input id="naturalGasActualLever" name="naturalGasActualLever" class="easyui-validatebox"  data-options=""/>
			                </td>
						</tr>
						
						  <tr>
			                <td align="center" colspan="3">
			                <a id="btn" href="javascript:void(0);" onclick="handInputSave()" class="easyui-linkbutton">提交</a>
			               <a id="btn" href="javascript:void(0);" onclick="inputClose()" class="easyui-linkbutton">取消</a>
              			 </td>
            </tr>
        		</table>
        		
        		<table id = "tradeDetail" border="0" style="font-size:12px;td:width=30px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
        		</table>
        		
        </div>
	
	<!-- window 交易手数弹框 -->
	<div id="tradeCountWin" class="easyui-window" title="交易手数" 
		style="width:800px;height:500px;display:none;border:none; overflow:scroll;top:4%"
        data-options="iconCls:'icon-save',modal:true,closed:true">
       <!--  hello ,this is window. -->
       <form id="tradeCountForm">
        	<table border="0" style="font-size:12px;" class="conn"  width="99%" cellpadding="0" cellspacing="0">
        		<tr>
        			<td class="label center">富时A50</td>
        			<td class="label center">恒指期货</td>
        			<td class="label center">国际原油</td>
        			<td class="label center">迷你道指</td>
        			<td class="label center">迷你纳指</td>
        		</tr>
        		<tr>
        			<td align="center" id='a50Count'></td>
	               	<td align="center" id='hsiCount'></td>
	               	<td align="center" id='crudeCount'></td>
	               	<td align="center" id='mdCount'></td>
	               	<td align="center" id='mnCount'></td>
        		</tr>
        	</table>
        	<br/>
        	<table border="0" style="font-size:12px;" class="conn"  width="99%" cellpadding="0" cellspacing="0">
        		<tr>
        			<td class="label center">小恒指</td>
        			<td class="label center">H股指</td>
        			<td class="label center">迷你标普</td>
        			<td class="label center">德国DAX</td>
        			<td class="label center">日经225</td>
        		</tr>
        		<tr>
        			<td align="center" id='lhsiCount'></td>
	               	<td align="center" id='hsCount'></td>
	               	<td align="center" id='mbCount'></td>
	               	<td align="center" id='daxCount'></td>
	               	<td align="center" id='nikkeiCount'></td>
        		</tr>
        	</table>
        	<br/>
        	<table border="0" style="font-size:12px;" class="conn"  width="99%" cellpadding="0" cellspacing="0">
        		<tr>
        			<td class="label center">美黄金</td>
        			<td class="label center">美铜</td>
        			<td class="label center">美白银</td>
        			<td class="label center">小原油</td>
        			<td class="label center">天然气</td>
        		</tr>
        		<tr>
        			<td align="center" id='agCount'></td>
        			<td align="center" id='acCount'></td>
	               	<td align="center" id='asCount'></td>
	               	<td align="center" id='scCount'></td>
	               	<td align="center" id='gasCount'></td>
        		</tr>
        	</table>
        	<br/>
        	<table border="0" style="font-size:12px;" class="conn"  width="99%" cellpadding="0" cellspacing="0">
        		<tr>
        			<td class="label center">小H股指</td>
        			<td class="label center">迷你德国DAX指数</td>
        		</tr>
        		<tr>
	               	<td align="center" id='xhsCount'></td>
	               	<td align="center" id='daxMinCount'></td>
	               	
	                <td align="center" id="end_type_td"></td>
        		</tr>
        	</table>
        	
        	<table id="end_tradeDetail"  border="0" style="font-size:12px;td:width=30px;" class="conn"  width="100%" cellpadding="0" cellspacing="0"></table>
        	 	<div style = "margin-left: 50%;margin-top: 10px;">
        	 		<a id="btn_end"  href="javascript:void(0);"  onclick="end()" class="easyui-linkbutton">结算</a>
        	 		<a   href="javascript:void(0);"  onclick="closeTradeCount()" class="easyui-linkbutton">取消</a>
        	 	</div>
        </form>
    </div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:internationFuture:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>