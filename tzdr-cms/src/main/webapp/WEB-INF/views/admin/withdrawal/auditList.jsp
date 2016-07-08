<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>登录</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/withdrawal/auditList.js?version=20150703"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">


</script>
</head>
<body>
<input type="hidden" id="auditMoney" value="${auditMoney}">
	<shiro:hasPermission name="sys:finance:withdrawAudit:view">
	<div id="withdrawalAuditTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			
			<div title="提现待审核【初审】" style="padding:1px;">
     			<div>
					<form id="queryForm1" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="mobile" name="mobile">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input id="starttime" name="starttime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('endtime');WdatePicker({onpicked:function(){endtime.focus();},maxDate:'#F{$dp.$D(\'endtime\')}'})"/>
									      至  <input id="endtime" name="endtime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\')}'})"/>							      
								</td>
								
							</tr>
						
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="tname" name="tname">
								</td>
								<td class="label right">
									<span>提现金额:</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="minmoney" id="minmoney"  data-options="validType:'money'"> 至 <input class="easyui-validatebox" id="maxmoney" name="maxmoney" data-options="validType:'money'">
								</td>
								
							</tr>
								<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="bank" name="bank">
								</td>
								<td class="label right">网银通道:</td>
				                <td>
				                    <select class="easyui-combobox" name="paymentChannel">
				                        <option value="">--请选择--</option>
				                        <option value="1">联动优势</option>
				                        <option value="2">币币支付</option>
				                    </select>
				                </td>
							</tr>
							<tr>
							<!--  
								<td class="label right">
									<span>审核状态：</span>
								</td>
								<td>
									<select id="auditstatus" class="easyui-combobox" name="auditstatus" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="1">通过</option>
									    <option  value="2">未通过</option>
									</select>
								</td>
								-->
								<td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="source">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('datagrid2','queryForm1')">查询</a>
								</td>
							</tr>
						</table>	
					</form>	
				</div>
				<div>
		        <table id="datagrid2" class="easyui-datagrid" width="100%" toolbar="#dg002Toolbar"
		             url="${ctx}/admin/withdrawAudit/listFirstAuditDrawData?type=firstAudit" pagination="true"
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
		                <th field="id" data-options="checkbox:true"></th>
						<th field="tname" width="150">客户姓名 </th>
						<th field="mobile" width="150">手机号</th>
						<th field="paymentChannelStr" width="150">网银通道</th>
						<th field="bank" width="150">提现银行</th>
						<th field="card" width="150">银行卡号</th>
						<th field="money" width="150">提现金额</th>
						<th field="balance" width="150" formatter="balanceformatter">平台余额</th>
						<th field="addtime" width="150">提现申请时间</th>
						<th field="sourceStr" width="150" formatter="sourceformatter">来源网站</th>
		            </tr>
		        </thead>
		    </table>
		    </div>
		    <div id="dg002Toolbar">
			     <shiro:hasPermission name="sys:finance:withdrawAudit:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search"  plain="true" onclick="auditWithDraw.openFundDetail('datagrid2')">查看资金明细</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.firstauditMoney()">审核通过</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.auditNotPass(1,'datagrid2')">审核不通过</a>
				</shiro:hasPermission>
		    </div>
   		</div>
			<div title="提现待审核【复审】" style="padding:1px;">
     			<div>
					<form id="queryForm2" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="mobile" name="mobile">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input id="starttime" name="starttime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('endtime');WdatePicker({onpicked:function(){endtime.focus();},maxDate:'#F{$dp.$D(\'endtime\')}'})"/>
									      至  <input id="endtime" name="endtime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime\')}'})"/>							      
								</td>
								
							</tr>
								<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="tname" name="tname">
								</td>
								<td class="label right">
									<span>提现金额:</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="minmoney" id="minmoney"  data-options="validType:'money'"> 至 <input class="easyui-validatebox" id="maxmoney" name="maxmoney" data-options="validType:'money'">
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="bank" name="bank">
								</td>
								<td class="label right">网银通道:</td>
				                <td>
				                    <select class="easyui-combobox" name="paymentChannel">
				                        <option value="">--请选择--</option>
				                        <option value="1">联动优势</option>
				                        <option value="2">币币支付</option>
				                    </select>
				                </td>
							</tr>
						
							<tr>
							<!-- 
								<td class="label right">
									<span>审核状态：</span>
								</td>
								<td>
									<select id="auditstatus" class="easyui-combobox" name="auditstatus" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="1">通过</option>
									    <option  value="2">未通过</option>
									</select>
								</td>
								-->
								<td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="source">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('datagrid3','queryForm2')">查询</a>
								</td>
							</tr>
						</table>	
					</form>	
				</div>
				<div>
		        <table id="datagrid3" class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar"
		             url="${ctx}/admin/withdrawAudit/listFirstAuditDrawData?type=reAudit" pagination="true"
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
		                <th field="id" data-options="checkbox:true"></th>
						<th field="tname" width="150">客户姓名 </th>
						<th field="mobile" width="150">手机号</th>
						<th field="paymentChannelStr" width="150">网银通道</th>
						<th field="bank" width="150">提现银行</th>
						<th field="card" width="150">银行卡号</th>
						<th field="money" width="150">提现金额</th>
						<th field="balance" width="150" >平台余额</th>
						<th field="addtime" width="150">提现申请时间</th>
						<th field="sourceStr" width="150"  formatter="sourceformatter">来源网站</th>
		            </tr>
		        </thead>
		    </table>
		    </div>
		    <div id="dg003Toolbar">
			     <shiro:hasPermission name="sys:finance:withdrawAudit:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search"  plain="true" onclick="auditWithDraw.openFundDetail('datagrid3')">查看资金明细</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.reAuditMoney()">审核通过</a>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.auditNotPass(3,'datagrid3')">审核不通过</a>
				</shiro:hasPermission>
		    </div>
   		</div>
			
			<div title="审核列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="hasAaudittb" style="padding: 5px; height: auto">
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="mobile2">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input id="startTime3" class="Wdate" type="text" onFocus="var endTime3=$dp.$('endTime3');WdatePicker({onpicked:function(){endTime3.focus();},maxDate:'#F{$dp.$D(\'endTime3\')}'})"/>
									      至  <input id="endTime3" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime3\')}'})"/>							      
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input class="easyui-validatebox" id="bank2">
								</td>
								<td class="label right">
									<span>复审时间:</span>
								</td>
								<td>
									<input id="startTime4" class="Wdate" type="text" onFocus="var endTime4=$dp.$('endTime4');WdatePicker({onpicked:function(){endTime4.focus();},maxDate:'#F{$dp.$D(\'endTime4\')}'})"/>
									      至  <input id="endTime4" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime4\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="tname2">
								</td>
								<td class="label right">
									<span>提现金额:</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="startmoney2"  data-options="validType:'money'"> 至 <input class="easyui-validatebox" id="endmoney2" data-options="validType:'money'">
								</td>
								
							</tr>
							<tr>
								<td class="label right">
									<span>审核状态：</span>
								</td>
								<td>
									<select id="auditStatus" class="easyui-combobox" name="auditStatus" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="-1,0">待审核</option>
									    <option value="1">通过</option>
									    <option  value="2">未通过</option>
									</select>
								</td>
									<td class="label right">
									<span>初审时间:</span>
								</td>
								<td>
									<input id="firstStartTime" class="Wdate" type="text" onFocus="var firstEndTime=$dp.$('firstEndTime');WdatePicker({onpicked:function(){firstEndTime.focus();},maxDate:'#F{$dp.$D(\'firstEndTime\')}'})"/>
									      至  <input id="firstEndTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'firstStartTime\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">网银通道:</td>
				                <td>
				                    <select class="easyui-combobox" name="paymentChannel" id="paymentChannel">
				                        <option value="">--请选择--</option>
				                        <option value="1">联动优势</option>
				                        <option value="2">币币支付</option>
				                    </select>
				                </td>
				                <td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="source" id="source">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
								<td class="label right">
								</td>
								<td>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="auditWithDraw.list.doSearch2()">查询</a>
								</td>
							</tr>
						</table>		
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:finance:withdrawAudit:view">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.auditNotPass(2,'hasAuditData')">查看未通过原因</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="hasAuditData" data-options="toolbar:hasAaudittb"></table>
			</div>
			
			<!-- 线下 -->
			<div title="线下转账待审核列表" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="belowLineAudittb" style="padding: 5px; height: auto">
					<div>
						<form id="belowLineAuditSearchForm" method="post">
						<input type="hidden" name="search_EQ_isAudit" value="0">
						<input type="hidden" name="search_EQ_status" value="21">
						<input type="hidden" name="search_EQ_belowLine" value="1">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" name="search_LIKE_mobile">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input id="startTime5" name="search_date_GTE_addtime" class="Wdate" type="text" onFocus="var endTime5=$dp.$('endTime5');WdatePicker({onpicked:function(){endTime5.focus();},maxDate:'#F{$dp.$D(\'endTime5\')}'})"/>
									      至  <input id="endTime5" name="search_date_LTE_addtime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime5\')}'})"/>							      
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input class="easyui-validatebox"  name="search_LIKE_bank">
								</td>
								<td class="label right">
									<span>提现金额:</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="search_GTE_money"  data-options="validType:'money'"> 至 <input class="easyui-validatebox" name="search_LTE_money" data-options="validType:'money'">
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="search_LIKE_tname">
								</td>
								<td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="search_EQ_source">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('belowLineAuditData','belowLineAuditSearchForm')">查询</a>
								</td>
							</tr>
						</table>	
						</form>		
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:finance:withdrawAudit:view">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search"  plain="true" onclick="auditWithDraw.openFundDetail('belowLineAuditData')">查看资金明细</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.lineAuditPass()">审核通过</a>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="sys:finance:withdrawAudit:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.auditNotPass(4,'belowLineAuditData')">审核不通过</a>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="sys:finance:withdrawAudit:export">  
						 <a  href="javascript:void(0)" onclick="$.easyui.exportExcel('belowLineAuditData','belowLineAuditSearchForm')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
	         
						</shiro:hasPermission>
						
					</div>
				</div>
				<table id="belowLineAuditData" class="easyui-datagrid"
					width="100%" 
		            pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#belowLineAudittb',
		            queryParams:{search_EQ_status:21,search_EQ_isAudit:0,search_EQ_belowLine:1},
		            singleSelect:true,
					frozenColumns:[[
				            {field:'ck',checkbox:true}
					]],
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'belowLineAuditData');
						}"
		            rownumbers="true" fitColumns="true" singleSelect="true" >
			        <thead>
			            <tr>
			            	<th field="id" width="150" hidden="true"></th>
			            	<th field="uid" width="150" hidden="true"></th>
			            	<th field="isAudit" width="150" hidden="true"></th>
							<th field="mobile" width="180" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="bank" width="150">提现银行</th>
							<th field="card" width="150">银行卡号</th>
							<th field="money" width="150"  sortable="true">提现金额</th>
							<th field="balance" width="100"  formatter="balanceformatter">平台余额</th>
							<th field="exportAddtime" width="200">提现申请时间</th>
							<th field="sourceStr" width="150"  formatter="sourceformatter">来源网站</th>
			            </tr>
			        </thead>				
				</table>
			</div>
			<div title="线下转账已审核列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="belowLineHasAaudittb" style="padding: 5px; height: auto">
					<div>
						<form id="belowLineHasAuditSearchForm">
						<input type="hidden" name="search_GT_isAudit" value="0">
						<input type="hidden" name="search_EQ_belowLine" value="1">
						<input type="hidden" name="search_NQ_status" value="3">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input class="easyui-validatebox" name="search_LIKE_mobile">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input id="startTime6" name="search_date_GTE_addtime" class="Wdate" type="text" onFocus="var endTime6=$dp.$('endTime6');WdatePicker({onpicked:function(){endTime6.focus();},maxDate:'#F{$dp.$D(\'endTime6\')}'})"/>
									      至  <input id="endTime6" name="search_date_LTE_addtime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime6\')}'})"/>							      
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input class="easyui-validatebox" name="search_LIKE_bank">
								</td>
								<td class="label right">
									<span>审核时间:</span>
								</td>
								<td>
									<input id="startTime7" name="search_date_GTE_auditTime" class="Wdate" type="text" onFocus="var endTime7=$dp.$('endTime7');WdatePicker({onpicked:function(){endTime7.focus();},maxDate:'#F{$dp.$D(\'endTime4\')}'})"/>
									      至  <input id="endTime7" name="search_date_LTE_auditTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime7\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="search_LIKE_tname">
								</td>
								<td class="label right">
									<span>提现金额:</span>
								</td>
								<td>
									<input class="easyui-validatebox" name="search_GTE_money"  data-options="validType:'money'"> 至 <input class="easyui-validatebox" name="search_LTE_money" data-options="validType:'money'">
								</td>
								
							</tr>
							<tr>
								<td class="label right">
									<span>审核状态：</span>
								</td>
								<td>
									<select id="auditStatus" class="easyui-combobox" name="search_EQ_isAudit" style="width:100px;height:30px;">
									    <option value="">请选择</option>
									    <option value="1">通过</option>
									    <option  value="2">未通过</option>
									</select>
								</td>
								<td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="search_EQ_source">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
								<td class="label right">
								</td>
								<td>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('belowLineHasAuditData','belowLineHasAuditSearchForm')">查询</a>
								</td>
							</tr>
						</table>	
						</form>	
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:finance:withdrawAudit:view">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="auditWithDraw.auditNotPass(2,'belowLineHasAuditData')">查看未通过原因</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="belowLineHasAuditData"  class="easyui-datagrid"
					width="100%"
			            pagination="true"
			            data-options="checkOnSelect:true,toolbar:'#belowLineHasAaudittb',
			            queryParams:{search_GT_isAudit:0,search_EQ_belowLine:1,search_NQ_status:1},
			            singleSelect:true,
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
			            onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'belowLineHasAuditData');
						}"
			            rownumbers="true" fitColumns="true" singleSelect="true">
				        <thead>
				            <tr>
				            	<th field="id" width="150" hidden="true"></th>
				            	<th field="uid" width="150" hidden="true"></th>
				            	<th field="isAudit" width="150" hidden="true"></th>
								<th field="mobile" width="180" sortable="true">手机号码</th>
								<th field="tname" width="150">客户姓名</th>
								<th field="bank" width="150">提现银行</th>
								<th field="card" width="150">银行卡号</th>
								<th field="money" width="150"  sortable="true">提现金额</th>
								<th field="exportAddtime" width="200">提现申请时间</th>
								<th field="auditStatusValue" width="150" sortable="true" >审核状态</th>
								<th field="auditUser" width="150" sortable="true">审核人</th>
								<th field="auditTimeValue" width="200" sortable="true">审核时间</th>
								<th field="sourceStr" width="150"  formatter="sourceformatter">来源网站</th>
				            </tr>
				        </thead>				
					</table>
			</div>
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;">
		
	</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:withdrawAudit:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
<script type="text/javascript">
function balanceformatter(value,rowData,rowIndex){
	if(rowData.mobile=='合计'){
		return '';
	}else{
		return value;
	}
}

function sourceformatter(value,rowData,rowIndex){
	if(rowData.mobile=='合计'){
		return '';
	}else{
		return value;
	}
}
</script>
</html>