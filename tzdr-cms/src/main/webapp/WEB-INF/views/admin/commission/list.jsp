<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>返利</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/commission/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
   <shiro:hasPermission name="sys:accountant:commission:view">

 <table id="dg" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/commission/listData"
              pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true" >
	        <thead>
	            <tr>
					<th field="level" width="150">代理级次 </th>
					<th field="userGrade" width="150">代理级别</th>
					<th field="parentName" width="150">上一级用户名</th>
					<th field="userName" width="150">用户名</th>
					<th field="trueName" width="150">实名</th>
					<th field="userRebate" width="150">返利率</th>
					<th field="userManageMoney" width="150">管理费</th>
					<th field="subordinateManageMoney" width="150">下级管理费</th>
					<th field="userMoney" width="150">本级返利 </th>
					<th field="subordinateMoney" width="150">下级返利</th>
					<th field="totalMoney" width="150">返利合计</th>
	            </tr>
	        </thead>
       </table>
       <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
			     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
		            <tr>
		                <td class="label right">起止日期:</td>
		                <td>
		                    <table class="many">
		                        <tr>
		                            <td><input class="easyui-datebox" name="ctimeStr_start" id="ctimeStr_start"></td>
		                            <td>至</td>
		                            <td><input class="easyui-datebox" name="ctimeStr_end" id="ctimeStr_end"></td>
		                        </tr>
		                    </table>
		                </td>
		                <td class="label right">返利率:</td>
		                <td> 
		                    <table class="many">
		                        <tr>
		                            <td><input class="easyui-numberbox" name="rebateStr_start"></td>
		                            <td>至</td>
		                            <td><input class="easyui-numberbox" name="rebateStr_end"></td>
		                        </tr>
		                    </table>
		                 </td>
		                
		            </tr>
		            <tr>
		                <td class="label right">上一级用户名/用户名:</td>
		                <td><input name="mobile" type="text" /></td>
		                <td class="label right">实名:</td>
		                <td><input name="tname" type="text"/>
		                </td>
		            </tr>
		            <tr>
		                <td class="label">&nbsp;</td>
		                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
		            </tr>
		          </table>
		        </form>
		        <shiro:hasPermission name="sys:accountant:commission:export"> 
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('dg','queryForm')">导出数据</a>
	  			</shiro:hasPermission>
	    </div>
   </shiro:hasPermission>
  <shiro:lacksPermission name="sys:accountant:commission:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
<script type="text/javascript">
	formatterDate = function(date) {
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day;
	};
	 
	/* window.onload = function () { 
		$('#ctimeStr_start').datebox('setValue', formatterDate(new Date(new Date().getTime()-1000*60*60*24)));
		$('#ctimeStr_end').datebox('setValue', formatterDate(new Date()));
	} */
</script>
</html>