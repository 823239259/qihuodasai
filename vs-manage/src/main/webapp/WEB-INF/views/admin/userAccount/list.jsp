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
<title>用户列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userAccount/list.js?v=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<script type="text/javascript">
/**
 * 电话号码处理
 */
function userMobileHandle(value,rows,index){
	     var val = value.substring(0,3);
		 var val1 = value.substring(7,value.length);
		 var result=val+"****"+val1;
	 	 //<shiro:hasPermission name="sys:customerService:wuser:usermobileview">
			result = value;
		//</shiro:hasPermission>
		return result;
}
</script>
<shiro:hasPermission name="sys:customerService:userAccount:view">
<!-- toolbar="#toolbar" -->
	<table id="dg" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/userAccount/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="150" data-options="formatter:userMobileHandle">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="avlBal" width="150">账号余额</th>
				<th field="htranActualLever" width="150">恒指操盘手数</th>
				<th field="ytranActualLever" width="150">原油操盘手数</th>
				<th field="atranActualLever" width="150">富时A50操盘手数</th>
				<th field="interActualLever" width="150">国际综合操盘手数</th>
				<th field="lever" width="150">总操盘手数</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">真实姓名:</td>
	                <td><input name="tname" type="text" /></td>
	                <td class="label right">手机号码:</td>
	                <td><input name="mobile"></td>
	                <td><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAccountDetail()">查看操盘信息</a>
    </div>
   </shiro:hasPermission>
   <shiro:lacksPermission name="sys:customerService:userAccount:view">
		<%@ include file="../../common/noPermission.jsp"%>
   </shiro:lacksPermission>
	
	<div id="accountDetail" class="easyui-window" title="操盘信息" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:850px;height:400px;">
       <form id="queryFormDetail" method="post">
	          <input type="hidden" name="uid" id="uid" />
	          <a id="accountBtn" href="#" onclick="$.easyui.datagridQuery('dgdetail','queryFormDetail')"  data-options="iconCls:'icon-search'">
	   </form>
	   <table id="dgdetail" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/userAccount/queryUsertradeList" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true" showFooter="true">
	        <thead>
	            <tr>
					<th field="businessTypeStr" width="100" sortable="true">交易品种 </th>
					<th field="traderBond" width="100" sortable="true">操盘保证金 </th>
					<th field="appendTraderBond" width="100">追加保证金</th>
					<th field="tranFees" width="80">手续费</th>
					<th field="tranLever" width="80">开仓手数</th>
					<th field="tranAccount" width="100">操盘账户</th>
					<th field="appTimeStr" width="140">申请时间</th>
					<th field="stateTypeStr" width="90">账户状态</th>
					<th field="endTimeStr" width="140">结算时间</th>
	            </tr>
	        </thead>
       </table>       
        
    </div>
	
</body>
</html>