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
<title>客户信息维护详情</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/customer/details.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
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
<body><!-- sys:outreach:customer:view -->
    <shiro:hasPermission name="sys:customerService:customer:view">
    <div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:150px">
			<div title="客户信息维护" id="proxy" class="easyui-panel" style="padding:1px;height:auto;">
		        <table id="user_info_dg" class="easyui-datagrid" 
			        toolbar="" <%-- url="${ctx}/admin/customer/listData"  --%>
			            rownumbers="true" fitColumns="true" singleSelect="true"> 
				        <thead>
			            <tr>
			                <th field="id" data-options="checkbox:true"></th>
							<th field="name" width="73">客户姓名 </th>
							<th field="mobile" width="73">手机号</th>
							<th field="isSignIn" formatter="isSignInStr" width="99">是否注册</th>
							<th field="signInTime" formatter="longTimetoStr" width="122">注册时间</th>
							<th field="isTrade" formatter="isTradeStr" width="72">是否配资</th>
							<th field="firstTradeTime" formatter="longTimetoStr" width="122">首次配资时间</th>
							<th field="lastTradeTime" formatter="longTimetoStr" width="122">最后配资时间</th>
							<th field="realName" width="100">所属销售 </th>
							<th field="createTime" formatter="longTimetoStr" width="115">创建时间</th>
			            </tr>
			            </thead>
			            <tr>
			            	<td>${customer.id }</td>
			            	<td>${customer.name }</td>
			            	<td>${customer.mobile }</td>
			            	<td>${customer.isSignIn }</td>
			            	<td class="formatLongDate">${customer.signInTime}</td>
			            	<td>${customer.isTrade }</td>
			            	<td class="formatLongDate">${customer.firstTradeTime}</td>
			            	<td class="formatLongDate">${customer.lastTradeTime }</td>
			            	<td>${customer.realName}</td>
			            	<td class="formatLongDate">${customer.createTime }</td>
			            </tr>
		       </table>
		    </div>
		</div>
		<div data-options="region:'center',border:false">
			<div title="沟通记录" id="proxy" class="easyui-panel" style="padding:1px;height:auto;">
		        <table id="detail_dg" class="easyui-datagrid" 
			        toolbar="#detail_dg_toolbar" url="${ctx}/admin/customer/detailsData/${customerId}" 
			            rownumbers="true" fitColumns="true" singleSelect="true"> 
				        <thead>
				            <tr>
				                <th field="id" data-options="checkbox:true"></th>
								<th field="contactTime" formatter="longTimetoStr" width="73">联系时间 </th>
								<th field="remark" width="73">联系情况</th>
								<th field="updateUser" width="99">修改人</th>
								<th field="updateTime" formatter="longTimetoStr" width="122">修改时间</th>
								<th field="createUser" width="72">创建人</th>
								<th field="createTime" formatter="longTimetoStr" width="122">创建时间</th>
				            </tr>
				       </thead>
		       </table>
		       <div id="detail_dg_toolbar">
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="customerDetailsOpen(0)">新增联系</a>
				     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="customerDetailsOpen(1)">修改联系</a>
			   </div>
	       </div>
		</div>
	</div>
	
     <div id="customerDetails" class="easyui-window" style="width:350px;height:225px;display:none;border:none; overflow: hidden;"
	        data-options="iconCls:'icon-save',modal:true,closed:true">
	        <input class="easyui-validatebox" name="type" id="type" type="hidden"  />
	        <form id="voDetailsForm">
	        	<input class="easyui-validatebox" name="customerId" id="customerId" value="${customerId}" type="hidden"  />
	        	<input class="easyui-validatebox" name="id" id="id" type="hidden"  />
		        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
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
		                <a id="btn" href="#" onclick="customerDetailsSave()" class="easyui-linkbutton">确定</a>
		               <a id="btn" href="#" onclick="customerDetailsClose()" class="easyui-linkbutton">取消</a>
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