<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>国际原油收益报表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
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
	<shiro:hasPermission name="sys:accountant:profitReportCrude:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="国际原油收益报表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<form id="auditSearchForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号码：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
								</td>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">					
								</td>
								<td class="label right">
									<span>状态：</span>
								</td>
								<td>
									<select id="search_EQ_stateType" class="easyui-combobox" name="search_EQ_stateType" style="width:100px;height:27px;">
									    <option value="" selected="selected">所有状态</option>
									    <option value="1">开户中</option>
									    <option value="2">申请结算</option>
									    <option value="3">待结算</option>
									    <option value="4">操盘中</option>
									    <option value="5">申请已拒绝</option>
									    <option value="6">已结算</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>申请时间：</span>
								</td>
								<td>
									<input id="startTime" name="search_date_GTE_appTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
								      至  <input id="endTime" name="search_date_LTE_appTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>							      
								</td>
								<td class="label right">
									<span>结算时间：</span>
								</td>
								<td>
									<input id="startTime2" name="search_date_GTE_endTime" class="Wdate" type="text" onFocus="var endTime2=$dp.$('endTime2');WdatePicker({onpicked:function(){endTime2.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}'})"/>
								      至  <input id="endTime2" name="search_date_LTE_endTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}'})"/>							      
								</td>
								<td class="label right">
									<span>&nbsp;</span>
								</td>
								<td >
									
								</td>
							</tr>
						</table>	
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:accountant:profitReportCrude:view">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','auditSearchForm')">查询</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:profitReportCrude:export">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel"   plain="true" onclick="$.easyui.exportExcel('edatagrid','auditSearchForm')">导出</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/profitReportCrude/getDatas?type=0"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="mobile" width="150" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="tranAccount" width="120">操盘账户</th>
							<th field="appTime" width="200">申请时间</th>
							<th field="traderBond" width="150">保证金(元)</th>
							<th field="endActualMoney" width="150">抵扣保证金(元)</th>
							
							<th field="tranLever" width="160">可开仓手数</th>
							<th field="updateTime" width="200">开户处理时间</th>
							<th field="appendTraderBond" width="200">补充保证金(元)</th>
							<th field="appEndTime" width="160">申请结算时间</th>
							<th field="endTime" width="160">结算时间</th>
							<th field="tranFeesTotal" width="160">实际手续费(元)</th>
							<th field="discountMoney" width="160">折扣券（折）</th>
							<th field="discountActualMoney" width="160">抵扣手续费(元)</th>
							<th field="actualProfitLoss" width="160">实际盈亏(元)</th>
							<th field="stateType" width="100">结算状态</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountant:profitReportCrude:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>