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
<title>客户信息维护</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/customer/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
function isSignInStr(value,rowData,rowIndex) {
    if (value == 0){
    	return "否"; 	
    }else if (value == 1){
    	return "是"; 
    }  
}
function isTradeStr(value,rowData,rowIndex) {
    if (value == 0){
    	return "否"; 	
    }else if (value == 1){
    	return "是"; 
    }  
}
function longTimetoStr(value,rowData,rowIndex) {
    if (value != null && value != ''){
    	return getFormatDateByLong(value,'yyyy-MM-dd')
    }else if (value == 2){
    	return ""; 
    }  
} 
</script>
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
    <shiro:hasPermission name="sys:customerService:customer:view">
    <div title="客户信息维护" id="proxy" style="padding:1px;height:auto;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/customer/listData"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="false"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="name" width="73">客户姓名 </th>
				<th field="mobile" width="73">手机号</th>
				<th field="isSignIn" formatter="isSignInStr" width="99">是否注册</th>
				<th field="signInTime" formatter="longTimetoStr" width="122">注册时间</th>
				<th field="isTrade" formatter="isTradeStr" width="72">是否配资</th>
				<th field="firstTradeTime" formatter="longTimetoStr" width="122">首次配资时间</th>
				<th field="lastTradeTime"  formatter="longTimetoStr" width="122">最后配资时间</th>
				<th field="lastContactTime" formatter="longTimetoStr" width="116">最后联系时间 </th>
				<th field="belongMarket" data-options="hidden:true"></th>
				<th field="realName" width="100">所属销售 </th>
				<shiro:hasPermission name="sys:customerService:customer:salesControllerCusLine ">
				<th field="assignTime" formatter="longTimetoStr" width="115">分配时间 </th>
				</shiro:hasPermission>
				<th field="createTime" formatter="longTimetoStr" width="115">创建时间</th>
            </tr>
       </thead>
       </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">客户姓名:</td>
	                <td><input name="name" type="text" /></td>
	                <td class="label right">注册时间:</td>
					<td>
						<input id="startSignInTime" name="startSignInTime" class="Wdate" type="text" onFocus="var endSignInTime=$dp.$('endSignInTime');WdatePicker({onpicked:function(){endSignInTime.focus();},maxDate:'#F{$dp.$D(\'endSignInTime\')}'})"/>
						      至  <input id="endSignInTime" name="endSignInTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startSignInTime\')}'})"/>							      
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">手机号码:</td>
	                <td><input name="mobile" type="text"  /></td>
	                 <td class="label right">首配时间:</td>
					<td>
						<input id="startFirstTradeTime" name="startFirstTradeTime" class="Wdate" type="text" onFocus="var endFirstTradeTime=$dp.$('endFirstTradeTime');WdatePicker({onpicked:function(){endFirstTradeTime.focus();},maxDate:'#F{$dp.$D(\'endFirstTradeTime\')}'})"/>
						      至  <input id="endFirstTradeTime" name="endFirstTradeTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startFirstTradeTime\')}'})"/>							      
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">是否注册:</td>
	                <td>
	                	<select id="isSignIn" class="easyui-combobox" name="isSignIn" style="width:100px;height:30px;">
						    <option value="">请选择</option>
						    <option value="0">否</option>
						    <option value="1">是</option>
						</select>
	                </td>
	                <td class="label right">最后配资时间:</td>
					<td>
						<input id="startLastTradeTime" name="startLastTradeTime" class="Wdate" type="text" onFocus="var endLastTradeTime=$dp.$('endLastTradeTime');WdatePicker({onpicked:function(){endLastTradeTime.focus();},maxDate:'#F{$dp.$D(\'endLastTradeTime\')}'})"/>
						      至  <input id="endLastTradeTime" name="endLastTradeTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startLastTradeTime\')}'})"/>							      
					</td>
	            </tr>
	            <tr>
	            	<td class="label right">是否配资:</td>
	                <td>
	                	<select id="isTrade" class="easyui-combobox" name="isTrade" style="width:100px;height:30px;">
						    <option value="">请选择</option>
						    <option value="0">否</option>
						    <option value="1">是</option>
						</select>
	                </td>
					<td class="label right">最后联系时间:</td>
					<td>
						<input id="startLastContactTime" name="startLastContactTime" class="Wdate" type="text" onFocus="var endLastContactTime=$dp.$('endLastContactTime');WdatePicker({onpicked:function(){endLastContactTime.focus();},maxDate:'#F{$dp.$D(\'endLastContactTime\')}'})"/>
						      至  <input id="endLastContactTime" name="endLastContactTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startLastContactTime\')}'})"/>							      
					</td>
	            </tr>
	            <tr>
		            <shiro:hasPermission name="sys:customerService:customer:salesCustomerAllocation">
		            	<td class="label right">所属销售:</td>
	                	<td><input class="easyui-combobox" id="belongMarket" name="belongMarket" data-options="
										url:'${ctx}/admin/org/getDepUserDatas?depId=20',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
						</td>
		            </shiro:hasPermission>
	            	<td class="label right">创建时间:</td>
					<td>
						<input id="startCreateTime" name="startCreateTime" class="Wdate" type="text" onFocus="var endCreateTime=$dp.$('endCreateTime');WdatePicker({onpicked:function(){endCreateTime.focus();},maxDate:'#F{$dp.$D(\'endCreateTime\')}'})"/>
						      至  <input id="endCreateTime" name="endCreateTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startCreateTime\')}'})"/>							      
					</td>
	            </tr>
	            <tr>
	            	<td class="label right"></td>
	                <td><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            	<td class="label right"></td>
	                <td></td>
	            </tr>
	            <shiro:hasPermission name="sys:customerService:customer:salesCustomerAllocation">
	            <tr>
	            	<td class="label right">分配给新销售:</td>
	                <td><input class="easyui-combobox" id="newBelongMarket" name="newBelongMarket" data-options="
										url:'${ctx}/admin/org/getDepUserDatas?depId=20',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'"></td>
	                <td class="label right"></td>
	                <td><a id="btn" href="#" onclick="assign()" class="easyui-linkbutton" iconCls="icon-edit">分配客户</a></td>
	            </tr>
	            </shiro:hasPermission>
	          </table>
	        </form>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addCustomerOpen()">新增客户</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="showDetail()">查看详情</a>
	    </div>
    </div>
     
	<div id="addCustomer" class="easyui-window" title="新增客户" style="width:350px;height:280px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="voForm">
	        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">客户姓名*</td>
	                <td><input class="easyui-validatebox" name="name" id="name" maxlength="100" type="text" data-options="required:true" /></td>
	                <td></td>
	            </tr>
	            <tr>
	                <td class="label right">电话号码*</td>
	                <td><input class="easyui-validatebox" name="mobile" id="mobile" maxlength="11"  type="text" data-options="required:true" /></td>
	                <td></td>
	            </tr>
	             <tr>
	                <td class="label right">联系时间*</td>
	                <td><input class="easyui-datebox" name="contactTimeStr" id="contactTimeStr" data-options="required:true"></td>
	                <td></td>
	            </tr>
	            <tr>
	                <td class="label right">联系情况*</td>
	                <td><textarea class="easyui-validatebox" rows="7" cols="30"  name="remark" id="remark"  maxlength="120" data-options="required:true"></textarea></td>
	                <td></td>
	            </tr>
	            <tr>
	                <td align="center" colspan="3">
	                <a id="btn" href="#" onclick="addCustomerSave()" class="easyui-linkbutton">提交</a>
	               <a id="btn" href="#" onclick="addCustomerClose()" class="easyui-linkbutton">取消</a>
	               </td>
	            </tr>
	        </table>
        </form>
</div>

</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:customer:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>