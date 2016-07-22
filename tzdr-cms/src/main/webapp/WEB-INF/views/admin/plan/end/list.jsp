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
<title>涌金版待审核方案</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/plan/end/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:riskmanager:plan:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
   <shiro:hasPermission name="sys:riskmanager:plan:planApproval01">
    <div title="终结方案待审核列表【审核1】" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/plan/end/listData" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="groupId" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
                <th field="tname" width="150">用户姓名 </th>
				<th field="account" width="150">交易账号</th>
				<th field="accountName" width="150" sortable="true">交易账户名</th>
				<th field="programNo" width="150">方案编号 </th>
				<th field="leverMoney" width="150">保证金</th>
				<th field="voucherMoney" width="150">代金券</th><!-- voucherActualMoney实际使用的优惠券金额 -->
				<th field="totalLeverMoney" width="150" sortable="true">总操盘资金 </th>
				<th field="feeType" width="150">账户类型 </th>
				<th field="endSubTimeStr" width="150" sortable="true">提交时间</th>
				<th field="activityTypeStr" width="150" sortable="true">方案类型</th>
				<th field="activityType" hidden="true"></th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input name="tname" type="text"  /></td>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	            </tr>
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_end"></td>
	                        </tr>
		                </table>
	                </td>
	             <td class="label right"> 账户类型:</td>
					<td>
						<input class="easyui-combobox" id="feeType" name="feeType" data-options="
								url:'${ctx}/admin/recharge/dataMapCombobox?key=feetype&includes=2,3',
								valueField:'id',
								panelHeight:100,
								textField:'text'">
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">
						<span>方案类型：</span>
					</td>
					<td>
						<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" 
							data-options="valueField: 'id', textField:'text', url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
					</td>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	            									<a id="btn0012" href="#" onclick="$.easyui.exportExcel('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a></td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:riskmanager:plan:audit">
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfPlanOpen01()">审核通过</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfPlanNoPassOpen01()">审核不通过</a>
	        </shiro:hasPermission>
	    </div>
     </div>
     </shiro:hasPermission>
     
     <shiro:hasPermission name="sys:riskmanager:plan:planApproval02">
     <div title="终结方案待审核列表【审核2】" style="padding:1px;">
        <table id="dg004" class="easyui-datagrid" width="100%" 
        toolbar="#dg004Toolbar" url="${ctx}/admin/plan/end/listData02" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="groupId" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
                <th field="tname" width="150">用户姓名 </th>
				<th field="account" width="150">交易账号</th>
				<th field="accountName" width="150" sortable="true" >交易账户名</th>
				<th field="programNo" width="150">方案编号 </th>
				<th field="leverMoney" width="150">保证金</th>
				<th field="voucherMoney" width="150">代金券</th><!-- voucherActualMoney实际使用的优惠券金额 -->
				<th field="totalLeverMoney" width="150" sortable="true" >总操盘资金 </th>
				<th field="feeType" width="150">账户类型 </th>
				<th field="endSubTimeStr" width="150" sortable="true">提交时间</th>
				<th field="activityTypeStr" width="150" sortable="true">方案类型</th>
				<th field="activityType" hidden="true"></th>
            </tr>
       </thead>
    </table>
	    <div id="dg004Toolbar">
	        <form id="queryForm04" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input name="tname" type="text"  /></td>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	            </tr>
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_end"></td>
	                        </tr>
		                </table>
	                </td>
	              <td class="label right"> 账户类型:</td>
					<td>
						<input class="easyui-combobox" id="feeType" name="feeType" data-options="
								url:'${ctx}/admin/recharge/dataMapCombobox?key=feetype&includes=2,3',
								valueField:'id',
								panelHeight:100,
								textField:'text'">
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">
						<span>方案类型：</span>
					</td>
					<td>
						<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" 
							data-options="valueField: 'id', textField:'text', url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
					</td>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a href="#" onclick="$.easyui.datagridQuery('dg004','queryForm04')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	           									<a id="btn0012" href="#" onclick="$.easyui.exportExcel('dg004','queryForm04')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a></td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:riskmanager:plan:audit">
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfPlanOpen()">审核通过</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfPlanNoPassOpen()">审核不通过</a>
	        </shiro:hasPermission>
	         
	    </div>
     </div>
     </shiro:hasPermission>
     
     <shiro:hasPermission name="sys:riskmanager:plan:planList">
     <div title="终结方案审核记录" style="padding:1px;">
        <table id="dg001" class="easyui-datagrid" width="100%" 
        toolbar="#dg001Toolbar" url="${ctx}/admin/plan/end/listAllData" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
				<th field="mobile" width="150">手机号 </th>
                <th field="tname" width="150">用户姓名 </th>
				<th field="account" width="150">交易账号</th>
				<th field="accountName" width="150" sortable="true" >交易账户名</th>
				<th field="programNo" width="150">方案编号 </th>
				<th field="leverMoney" width="150">保证金</th>
				<th field="voucherMoney" width="150">代金券</th><!-- voucherActualMoney实际使用的优惠券金额 -->
				<th field="totalLeverMoney" width="150" sortable="true" >总操盘资金 </th>
				<th field="finishedMoneyStr" width="150">操盘余额</th>
				<th field="auditEndStatusStr" width="150">审核状态 </th>
				<th field="feeType" width="150">账户类型 </th>
				<th field="endAuditUserIdStr" width="150">审核人 </th>
				<th field="endAuditTimeStr" width="150">审核时间 </th>
				<th field="endSubTimeStr" width="150" sortable="true">提交时间</th>
				<th field="activityTypeStr" width="150" sortable="true">方案类型</th>
				<th field="activityType" hidden="true"></th>
            </tr>
       </thead>
    </table>
	    <div id="dg001Toolbar">
	        <form id="queryForm01" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">用户姓名:</td>
	                <td><input name="tname" type="text"  /></td>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	            </tr>
	            <tr>
	                <td class="label right">审核状态:</td>
	                <td>
	                <input class="easyui-combobox" value="-1" type="text" name="auditEndStatus" 
                       data-options="valueField:'id',textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=auditStaus&defaultValue=-1'" />
	                
	                </td>
	                <td class="label right">审核时间:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="endAuditTimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="endAuditTimeStr_end"></td>
	                        </tr>
		                </table>
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="label right">提交时间:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="endSubTimeStr_end"></td>
	                        </tr>
		                </table>
	                </td>
	               <td class="label right"> 账户类型:</td>
					<td>
						<input class="easyui-combobox" id="feeType" name="feeType" data-options="
								url:'${ctx}/admin/recharge/dataMapCombobox?key=feetype&includes=2,3',
								valueField:'id',
								panelHeight:100,
								textField:'text'">
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">
						<span>方案类型：</span>
					</td>
					<td>
						<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" 
							data-options="valueField: 'id', textField:'text', url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
					</td>
	                <td class="label">&nbsp;</td>
	                <td class="label">
		                <a id="btn" href="#" onclick="$.easyui.datagridQuery('dg001','queryForm01')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		                <a id="btn0012" href="#" onclick="$.easyui.exportExcel('dg001','queryForm01')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	    </div>
     </div>
     </shiro:hasPermission>
     
 </div>
 
 
 <div id="endOfPlan01" class="easyui-window" title="结算方案" style="padding:2px;width:350px;height:120px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="endOfForm01">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">方案结算金额:</td>
                <td><input class="easyui-numberbox" id="endOfPlanAmount01" data-options="min:0.01,precision:2" required="true" type="text" name="amount" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateEndOfPlanPass01()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="endOfPlanClose01()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
 
 
 
 <div id="endOfPlan" class="easyui-window" title="结算方案" style="padding:2px;width:350px;height:120px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="endOfForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">方案结算金额:</td>
                <td><input class="easyui-numberbox" id="endOfPlanAmount" data-options="min:0.01,precision:2" required="true" type="text" name="amount" /></td>
                <td></td>
            </tr>
            
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateEndOfPlanPass()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="endOfPlanClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
 
 <div id="endOfPlanNoPass" class="easyui-window" title="审核不通过" style="padding:2px;width:350px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="noPassForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">原因:</td>
                <td>
                <textarea name="failCause" class="easyui-validatebox" required="true" id="failCause_id" style="width:100%;height:100px;"></textarea>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateEndOfPlanNoPass()" class="easyui-linkbutton">确定</a>
               <a id="btn" href="#" onclick="endOfPlanNoPassClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>

<div id="endOfPlanNoPass01" class="easyui-window" title="审核不通过" style="padding:2px;width:350px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="noPassForm01">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">原因:</td>
                <td>
                <textarea name="failCause" class="easyui-validatebox" required="true" id="failCause_id01" style="width:100%;height:100px;"></textarea>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateEndOfPlanNoPass01()" class="easyui-linkbutton">确定</a>
               <a id="btn" href="#" onclick="endOfPlanNoPassClose01()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
 
 </shiro:hasPermission>
<shiro:lacksPermission name="sys:riskmanager:plan:view">
	<%@ include file="../../../common/noPermission.jsp"%>
</shiro:lacksPermission>
 
</body>
</html>