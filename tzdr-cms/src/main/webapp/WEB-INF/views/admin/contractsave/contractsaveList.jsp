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
<title>协议保全列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<script type="text/javascript">
	function showview(id){
		document.forms["showContract"].action=basepath+"admin/contractsave/viewInfo?saveId="+id;
		document.forms["showContract"].submit();
	}

</script>

<body>
<!-- toolbar="#toolbar" -->
       <div id="edatagridToolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	             <tr>
	                <td class="label right">配资时间:</td>
	                <td style="width:35%">
	                <table class="many">
	                <tr>
	                <td>
	                  <input name="tradestarttime" id="tradestarttime" type="text" class="easyui-datebox" />
	                	<font style="font-size:12px;">至</font>
	                </td>
	                
	                <td>
	                <input name="tradeendtime" id="tradeendtime" type="text" class="easyui-datebox" />
	                </td>
	                </tr>
	                </table>
	              
	                </td>
	                <td class="label right">保全时间:</td>
	               <td>
	                  <input name="savestarttime" id="savestarttime" type="text" class="easyui-datebox" />
	                	<font style="font-size:12px;">至</font>
	                <input name="saveendtime" id="saveendtime" type="text" class="easyui-datebox" />
	                </td>
	            </tr>
					<tr>
	                <td class="label right">手机号码:</td>
	                <td>
	                  <input name="mobile" id="mobile" type="text" />
	                </td>
              		<td class="label right">
	               		  方案编号:
	                </td>
	                <td>
	                <input name="programNo" id="programNo" type="text" />
	                </td>
	               </tr>
	               <tr>
	                <td class="label right">真实姓名:</td>
	                <td style="width:15%">
	                <input name="tname" id="tname" >
	                </td>
	                <td class="label right">佣金id:</td>
	                <td>
	                 	<input name="saveid" id="saveid"　type="text" />
	                </td>
	            </tr>
	              <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="5"><a id="btn" href="javascript:void(0)" onclick="$.easyui.datagridQuery('edatagrid','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	           </table>
	        </form>
	        	
		    </div>
	<table id="edatagrid" title="协议保全列表" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/contractsave/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="false">
        <thead>
             <tr>
				<th field="mobile" width="100">手机号码 </th>
				<th field="tname" width="100">真实姓名</th>
				<th field="programNo" width="100">方案编号</th>
				<th field="tradetime" width="100">配资时间</th>
				<th field="saveid" width="100">保全id</th>
				<th field="savetime" width="100">保全时间</th>
				<th field="viewsaveinfo" width="150">保全信息</th>
            </tr>
        </thead>
    </table>
    	<div id="addWin" style="padding:10px;top: 20px;"></div>
    	  <form id="showContract" name="showContract" action="" method="post" target="_blank"/>
</body>
</html>