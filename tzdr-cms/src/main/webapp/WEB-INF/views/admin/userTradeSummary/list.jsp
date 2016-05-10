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
<title>配资用户列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:customerService:userTradeSummary:view">
<!-- toolbar="#toolbar" -->
       <div id="edatagridToolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	             
					<tr>
			                <td class="label right">手机号:</td>
			                <td style="width:15%">
			                <input name="mobile" id="mobile" >
			                </td>
			                <td class="label right">姓名:</td>
			                <td>
			                 	<input name="tname" id="tname"　type="text" />
			                </td>
	              <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="5"><a id="btn" href="javascript:void(0)" onclick="$.easyui.datagridQuery('edatagrid','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	           </table>
	        </form>
	          <shiro:hasPermission name="sys:customerService:userTradeSummary:export">  
		     <a id="excelBtn" href="javascript:void(0)" onclick="$.easyui.exportExcel('edatagrid','queryForm')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
	         </shiro:hasPermission>  
		    </div>
	<table id="edatagrid" title="配资用户列表" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/userTradeSummary/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="false">
        <thead>
        
            <tr>
				<th field="mobile" width="100">手机号 </th>
				<th field="tname" width="100">姓名</th>
				<th field="useProNo" width="100">操盘中配资方案数</th>
				<th field="countProNo" width="100">累计配资方案数</th>
				<th field="leverMoney" width="100">累计配资保证金</th>
				<th field="totalmoney" width="100">累计配资金额 </th>
				<th field="appendLeverMoney" width="100">累计追加保证金</th>
				<th field="profit" width="100">累计提取利润</th>
				<th field="startdays" width="100">累计配资天数</th>
				<th field="usedays" width="100">累计使用天数</th>
				<th field="lasttime" width="100">最后一次配资时间</th>
            </tr>
        </thead>
    </table>
  
    	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:userTradeSummary:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>