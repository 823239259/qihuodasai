<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>登录</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hkstock/hkappendLevelMoney/hkappendLevelMoneylist.js?version=20151214"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:hkappendLevelMoney:view">
	<div id="hkappendLevelMoneyTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="处理列表" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="audittb" style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
					<input name="search_GTE_status" value="0" type="hidden">
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">
								</td>
								
								
								<td class="label right">
									<span>交易帐号:</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_accountNo" name="search_LIKE_accountNo">
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>交易帐户名：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_accountName" name="search_LIKE_accountName">
								</td>
								<td class="label right">
									<span>追加时间:</span>
								</td>
								<td>
										<input id="startTime3" name="search_datetime_GTE_appendDate" class="" type="text" onFocus="var endTime3=$dp.$('endTime3');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime3.focus();},maxDate:'#F{$dp.$D(\'endTime3\')}'})"/>
									      至  <input id="endTime3" name="search_datetime_LTE_appendDate" class="" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime3\')}'})"/>							      
							
								</td>
							</tr>
							<tr>
								
								<td class="label right">
									<span>处理人：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_handlerName" name="search_LIKE_handlerName">
								</td>
								<td class="label right">
									<span>处理时间:</span>
								</td>
								<td>
										<input id="startTime3_1" name="search_datetime_GTE_handleDate" class="" type="text" onFocus="var endTime4=$dp.$('endTime4');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime4.focus();},maxDate:'#F{$dp.$D(\'endTime4\')}'})"/>
									      至  <input id="endTime3_1" name="search_datetime_LTE_handleDate" class="" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime4\')}'})"/>							      
							
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>账户类型：</span>
								</td>
								<td>
									<input class="easyui-combobox"    id="feeType" name="search_IN_feeType" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=hktradechannel',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
								</td>
								<td class="label right">
									<span>处理状态:</span>
								</td>
								<td>
									<select id="status" class="easyui-combobox" name="status" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="1">已处理</option>
									    <option  value="0">未处理</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="hkappendMoney.list.doSearch1()">查询</a>
								</td>
							</tr>
						</table>			
					</div>
					
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:hkappendLevelMoney:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-edit"  plain="true" onclick="hkappendMoney.handleMoney()">已处理</a>
						</shiro:hasPermission>
						 <shiro:hasPermission name="sys:riskmanager:hkappendLevelMoney:export">  
					     	<a id="excelBtn" href="javascript:void(0)"  iconCls="icon-excel" plain="true" class="easyui-linkbutton" onclick="$.easyui.exportExcel('auditData','queryForm')" >导出</a>
	         			 </shiro:hasPermission>  
					</div>
					</form>
				</div>
				
				<table id="auditData" data-options="toolbar:audittb"></table>
			</div>
			<%-- <div title="已处理列表" data-options="tools:'#p-tools'" style="padding:20px;" >
				<div id="hasAaudittb" style="padding: 5px; height: auto">
				<form id="queryhasForm" method="post">
				<input name="search_EQ_status" value="1" type="hidden">
					<div>
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_mobile1" name="search_LIKE_mobile">
								</td>
								
								
								<td class="label right">
									<span>恒生帐号:</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_accountNo1" name="search_LIKE_accountNo">
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>恒生帐户名：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_accountName1" name="search_LIKE_accountName">
								</td>
								<td class="label right">
									<span>追加时间:</span>
								</td>
								<td>
										<input id="startTime4" name="search_datetime_GTE_appendDate" class="" type="text" onFocus="var endTime4=$dp.$('endTime4');WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:function(){endTime4.focus();},maxDate:'#F{$dp.$D(\'endTime4\')}'})"/>
									      至  <input id="endTime4" name="search_datetime_LTE_appendDate" class="" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime4\')}'})"/>							      
							
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>账户类型:</span>
								</td>
								<td>
									<input class="easyui-combobox"    id="feeType1" name="search_IN_feeType" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=feetype',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
								</td>
								<td class="label right">
									<span>处理人：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="search_LIKE_handlerName" name="search_LIKE_handlerName">
								</td>
							</tr>
							<tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="appendMoneyFail.list.doSearch2()">查询</a>
								</td>
							</tr>
						</table>		
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:hkappendLevelMoney:view">  
							<a id="excelBtn" href="javascript:void(0)" onclick="$.easyui.exportExcel('hasAuditData','queryhasForm')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
	         			</shiro:hasPermission>
					</div> 
					</form>
				</div>
				<table id="hasAuditData" data-options="toolbar:hasAaudittb"></table>
			</div> --%>
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;">
		
	</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:hkappendLevelMoney:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>