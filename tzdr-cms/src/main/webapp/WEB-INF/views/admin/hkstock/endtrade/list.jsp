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
<title>港股待审核方案</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hkstock/endtrade/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:riskmanager:hkendtrade:view">
<div id="endTradeTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
   <shiro:hasPermission name="sys:riskmanager:hkendtrade:planApproval01">
    <div title="待审核列表【审1】" style="padding:1px;">
    	<table id="firsteAudit" class="easyui-datagrid"
		            width="100%" pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#firsteAuditTab',
		            singleSelect:true,
					frozenColumns:[[
			            {field:'ck',checkbox:true}
					]],
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'firsteAudit');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
					<th field="mobile" width="150">手机号 </th>
	                <th field="tname" width="150">用户姓名 </th>
	                <th field="tradeChannelValue" width="150">账户交易通道</th>
					<th field="accountName" width="150">交易账户名</th>
					<th field="accountNo" width="150">交易账号</th>
					<th field=groupId width="150">方案编号 </th>
					<th field="leverMoney" width="150">保证金(元)</th>
					<th field="totalLeverMoney" width="150">总操盘资金(港元)</th>
					<th field="endSubTimeValue" width="150" sortable="true">提交时间</th>
	            </tr>
	        </thead>
		</table>
    
	    <div id="firsteAuditTab">
	        <form id="firsteAuditSearchForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname" /></td>
	                <td class="label right">手机号:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile" /></td>
	            </tr>
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                        	<input id="startTime" name="search_date_GTE_endSubTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
									      至  <input id="endTime" name="search_date_LTE_endSubTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>	
	                        </tr>
		                </table>
	                </td>
	             <td class="label right"> 账户交易通道:</td>
					<td>
						<input class="easyui-combobox" id="tradeAccount" name="search_EQ_tradeChannel" data-options="
												url:'${ctx}/admin/recharge/dataMapCombobox?key=hktradechannel',
												valueField:'id',
												panelHeight:100,
												textField:'text'">
					</td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="datagridUtils.datagridQuery('firsteAudit','firsteAuditSearchForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:riskmanager:hkendtrade:audit">
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfTradePassOpen(1)">审核通过</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfTradeNoPassOpen(1)">审核不通过</a>
	        </shiro:hasPermission>
	    </div>
     </div>
     </shiro:hasPermission>
     
     <shiro:hasPermission name="sys:riskmanager:hkendtrade:planApproval02">
     <div title="待审核列表【审2】" style="padding:1px;">
     	<table id="recheckAudit" class="easyui-datagrid"
            width="100%"
            pagination="true"
            data-options="checkOnSelect:true,toolbar:'#recheckAuditTab',
             singleSelect:true,
            frozenColumns:[[
			            {field:'ck',checkbox:true}
					]],
            onLoadSuccess:function(data){
				datagridUtils.loadSuccess(data,'recheckAudit');
			},
			rowStyler:function(index,row){   
		        if (row.submitDays <= 2){   
		            return 'background-color:#FCF;';   
		        }   
		    }"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
					<th field="mobile" width="150">手机号 </th>
	                <th field="tname" width="150">用户姓名 </th>
	                <th field="tradeChannelValue" width="150">账户交易通道</th>
					<th field="accountName" width="150">交易账户名</th>
					<th field="accountNo" width="150">交易账号</th>
					<th field="groupId" width="150">方案编号 </th>
					<th field="leverMoney" width="150">保证金(元)</th>
					<th field="totalLeverMoney" width="150">总操盘资金(港元)</th>
					<th field="endSubTimeValue" width="150" sortable="true">提交时间</th>
					<th field="endAuditFirsteTimeValue" width="150" sortable="true">审1时间</th>
	            </tr>
	        </thead>
 		</table>
	    <div id="recheckAuditTab">
	        <form id="recheckAuditSearchForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname"/></td>
	                <td class="label right">手机号:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile" /></td>
	            </tr>
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
	                <input id="startTime" name="search_date_GTE_endSubTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
									      至  <input id="endTime" name="search_date_LTE_endSubTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>	
	                </td>
	              <td class="label right"> 账户交易通道:</td>
					<td>
						<input class="easyui-combobox" id="tradeAccount" name="search_EQ_tradeChannel" data-options="
												url:'${ctx}/admin/recharge/dataMapCombobox?key=hktradechannel',
												valueField:'id',
												panelHeight:100,
												textField:'text'">
					</td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a href="#" onclick="datagridUtils.datagridQuery('recheckAudit','recheckAuditSearchForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:riskmanager:hkendtrade:audit">
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfTradePassOpen(2)">审核通过</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfTradeNoPassOpen(2)">审核不通过</a>
	        </shiro:hasPermission>
	        <br/>&nbsp;<span style="font-family:'Arial-BoldMT', 'Arial Bold', 'Arial';font-weight:700;color: red">特别注意：</span><span style="font-family:'ArialMT', 'Arial';font-weight:400;color: red">因港股结算是T+2个交易日的16点，在此时间点前提交的终结请求(背景色为灰色的数据)，请勿审核通过。</span>
	    </div>
     </div>
     </shiro:hasPermission>
     
     <shiro:hasPermission name="sys:riskmanager:hkendtrade:planList">
     <div title="审核记录" style="padding:1px;">
     	<table id="auditData" class="easyui-datagrid"
		            width="100%"
		            pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#auditDataTab',
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'auditData');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	            	<th field="mobile" width="150">手机号 </th>
	                <th field="tname" width="150">用户姓名 </th>
	                <th field="tradeChannelValue" width="150">账户交易通道</th>
					<th field="accountName" width="150">交易账户名</th>
					<th field="accountNo" width="150">交易账号</th>
					<th field="groupId" width="150">方案编号 </th>
					<th field="leverMoney" width="150">保证金(元)</th>
					<th field="totalLeverMoney" width="150">总操盘资金(港元)</th>
					<th field="finishedMoney" width="150">结算金额(港元) </th>
					<th field="auditEndStatusValue" width="150">审核状态 </th>
					<th field="endAuditUserName" width="150">审核人 </th>
					<th field="endAuditTimeValue" width="150">审2时间 </th>
					<th field="endSubTimeValue" width="150" sortable="true">提交时间</th>
	            </tr>
	        </thead>
   		</table>
	    <div id="auditDataTab">
	        <form id="auditSearchForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname" /></td>
	                <td class="label right">手机号:</td>
	                <td><input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile" /></td>
	            </tr>
	            <tr>
	                <td class="label right">审核状态:</td>
	                <td>
	                <input class="easyui-combobox" value="-1" type="text" name="search_EQ_auditEndStatus" 
                       data-options="valueField:'id',textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=auditStaus&defaultValue=-1'" />
	                
	                </td>
	                <td class="label right">审核时间:</td>
	                <td>
		                <input id="endAuditStartTime" name="search_date_GTE_endAuditTime" class="Wdate" type="text" onFocus="var endAuditEndTime=$dp.$('endAuditEndTime');WdatePicker({onpicked:function(){endAuditEndTime.focus();},maxDate:'#F{$dp.$D(\'endAuditEndTime\')}'})"/>
									      至  <input id="endAuditEndTime" name="search_date_LTE_endAuditTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endAuditStartTime\')}'})"/>	
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
		                <input id="startTime" name="search_date_GTE_endSubTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
									      至  <input id="endTime" name="search_date_LTE_endSubTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>	
	                </td>
	               <td class="label right"> 账户交易通道:</td>
					<td>
						<input class="easyui-combobox" id="tradeAccount" name="search_EQ_tradeChannel" data-options="
												url:'${ctx}/admin/recharge/dataMapCombobox?key=hktradechannel',
												valueField:'id',
												panelHeight:100,
												textField:'text'">
					</td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3">
	                <a id="btn" href="#" onclick="datagridUtils.datagridQuery('auditData','auditSearchForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                <a id="btn0012" href="#" onclick="$.easyui.exportExcel('auditData','auditSearchForm')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	    </div>
     </div>
     </shiro:hasPermission>
 </div>
 
 <div id="endOfTrade" class="easyui-window" title="结算方案" style="padding:2px;width:350px;height:150px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="endOfForm">
        	<input type="hidden" id="tabType" name="tabType" value="">
	        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">方案结算金额:</td>
	                <td><input class="easyui-numberbox" id="endOfTradeAmount" data-options="min:0.01,precision:2" required="true" type="text" name="amount" />&nbsp;港币</td>
	            </tr>
	            <tr>
	            	<td class="label right"></td>
	            	<td><span style="color: red;">请输入港股交易账号中的总资产金额</span></td>
	            </tr>
	            <tr>
	                <td align="center" colspan="3">
		               <a id="btn" href="#" onclick="updateEndOfTradePass()" class="easyui-linkbutton">提交</a>
		               <a id="btn" href="#" onclick="endOfTradeClose()" class="easyui-linkbutton">取消</a>
	               </td>
	            </tr>
	        </table>
        </form>
</div>
 
 <div id="endOfTradeNoPass" class="easyui-window" title="审核不通过" style="padding:2px;width:350px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="noPassForm">
        	<input type="hidden" id="tabType" name="tabType" value="">
	        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">原因:</td>
	                <td>
	                <textarea name="failCause" class="easyui-validatebox" required="true" id="failCause" style="width:100%;height:100px;"></textarea>
	                </td>
	                <td></td>
	            </tr>
	            <tr>
	                <td align="center" colspan="3">
		                <a id="btn" href="#" onclick="updateEndOfTradeNoPass()" class="easyui-linkbutton">确定</a>
		               	<a id="btn" href="#" onclick="endOfTradeNoPassClose()" class="easyui-linkbutton">取消</a>
	               </td>
	            </tr>
	        </table>
        </form>
</div>

 </shiro:hasPermission>
<shiro:lacksPermission name="sys:riskmanager:hkendtrade:view">
	<%@ include file="../../../common/noPermission.jsp"%>
</shiro:lacksPermission>
</body>
</html>