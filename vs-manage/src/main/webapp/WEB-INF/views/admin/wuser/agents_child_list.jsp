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
<title>代理商列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/agents_child_list.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
 <shiro:hasPermission name="sys:customerService:lowerAgent:view">
         <!--  data-options="queryParams:Check.loadFormData($('#queryForm'))"  -->
    <div  id="proxy" style="padding:1px;height:auto;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/lowerAgent/agentsChildList"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
                <th field="addDateStr" width="150">日期</th>
				<th field="tname" width="150">姓名</th>
				<th field="mobile" width="150">电话</th>
				<th field="childNumber" width="150">直属下级数量</th>
				<th field="allChildNumber" width="150">所有下级</th>
				<th field="totalAmount" width="150">总额</th>
				<th field="schemeNumber" width="150">方案数</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	            	<input type="hidden" name="first" value="1">
	                <td class="label right">下级查询:</td>
	                <td><input name="mobile" type="text" /></td>
	                 <td class="label right">上级查询:</td>
	                <td><input name="superMobile" type="text" /></td>
	            </tr>
	            
	            <tr>
	            	 <td class="label right">姓名:</td>
	                <td><input name="tname" type="text" /></td>
	                <td class="label right">日期:</td>
	                <td>
		                <table class="many">
	                        <tr>
	                            <td><input class="easyui-datebox" name="addtime_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datebox" name="addtime_end"></td>
	                        </tr>
		                </table>
	                </td>
	               
	            </tr>
	            
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openSchemeDetail()">查看方案</a>
	    </div>
    </div>
<div id="scheme_id_window" class="easyui-window" title="方案列表" 
    style="display:block;border:none;overflow: auto;"
        data-options="modal:true,closed:true,width:850,height:340">
        <table id="dg001" class="easyui-datagrid" 
        toolbar="#dg001Toolbar" url="${ctx}/admin/lowerAgent/agentsDetailChildList"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <!--th field="id" data-options="checkbox:true"></th-->
                <th field="programId" width="150">方案号</th>
				<th field="leverMoney" width="150">保证金</th>
				<th field="lever" width="150">倍数</th>
				<th field="starttimeStr" width="150">开始时间</th>
				<th field="endtimeStr" width="150">结束时间</th>
            </tr>
       </thead>
    </table>
	    <div id="dg001Toolbar">
	        <form id="queryForm_scheme" method="post">
	           <input type="hidden" id="show_scheme_id" name="agentId">
	          <a id="schemeBtn" href="#" style="display:none;" onclick="$.easyui.datagridQuery('dg001','queryForm_scheme')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	        </form>
	    </div>
</div>
</shiro:hasPermission>
<shiro:lacksPermission name="sys:customerService:lowerAgent:view">
	<%@ include file="../../common/noPermission.jsp"%>
</shiro:lacksPermission>
</body>
</html>