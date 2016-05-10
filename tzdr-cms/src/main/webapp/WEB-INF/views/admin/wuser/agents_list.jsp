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
<script type="text/javascript" src="${ctx}/static/script/wuser/agents_list.js"></script>
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
    <shiro:hasPermission name="sys:customerService:agent:view">
         <!--  data-options="queryParams:Check.loadFormData($('#queryForm'))"  -->
    <div title="代理商维护" id="proxy" style="padding:1px;height:auto;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/agent/agentsList"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
				<th field="uname" width="150">代理名称</th>
				<th field="level" width="150">所属层级</th>
				<th field="childNumber" width="150">直属下级</th>
				<th field="allChildNumber" width="150">所有下级</th>
				<th field="chTitle" width="150">称号</th>
				<th field="rebateStr" width="150">佣金返点(%)</th>
				<th field="createUsername" width="150">创建人 </th>
				<th field="ctime" width="150">创建时间</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">代理名称:</td>
	                <td><input name="uname" type="text"  /></td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	         <shiro:hasPermission name="sys:customerService:agent:create">
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addAgentOpen()">新增代理</a>
		     </shiro:hasPermission>
		      <shiro:hasPermission name="sys:customerService:agent:update">
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateAgentOpen()">修改代理信息</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateAgentTreeOpen()">修改代理树</a>
	   		</shiro:hasPermission>
	    </div>
    </div>
     

<div id="addAgent" class="easyui-window" title="新增代理商" style="width:350px;height:240px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="voForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">手机号<input type="hidden" id="handlerUsername_token" value="true" /></td>
                <td><input name="mobile" type="text" id="handlerUsername" class="easyui-textbox" precision="0" data-options="required:true" /></td>
                <td>
                    <span id="userExitsLabel"></span>
                </td>
            </tr>  
            <tr>
                <td class="label right">密码</td>
                <td><input class="easyui-textbox" name="password"  type="password"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">确认密码</td>
                <td><input class="easyui-textbox" name="repassword"  type="password"  /></td>
                <td></td>
            </tr>
             <tr>
                <td class="label right">代理名称</td>
                <td><input class="easyui-textbox" name="uname" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">设置返点</td>
                <td><input class="easyui-numberbox" name="rebate"  data-options="min:0,precision:2,max:100"  />%</td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="addAgentSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="addAgentClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>


<div id="updateAgent" class="easyui-window" title="修改代理信息" style="padding:2px;width:350px;height:150px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="bankPayForm">
        <table border="0" style="font-size:12px;" cellpadding="0" width="100%" class="conn" cellspacing="0">
             <tr>
                <td class="label right">代理名称</td>
                <td><input  class="easyui-textbox" id="updateAgentNameId" type="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">设置返点</td>
                <td><input  class="easyui-numberbox" id="updateRebateValId" data-options="min:0,precision:2,max:100"  type="text"/>%</td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateAgent()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="updateAgentClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>

<div id="updateAgentTreeOpen" class="easyui-window" title="修改代理树" style="padding:2px;width:380px;height:150px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="bankPayForm">
        <input type="hidden" id="uid" value="">
        <table border="0" style="font-size:12px;" cellpadding="0" width="100%" class="conn" cellspacing="0">
             <tr>
                <td class="label right">新上级手机号</td>
                <td><input  class="easyui-numberbox" id="newSuperiorMobile" maxlength="11" type="text"/></td>
                <td class="label"><a id="btnSuperior" href="#" onclick="getWUser()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
            </tr>
            <tr>
                <td class="label right">新上级姓名</td>
                <td><input  class="easyui-textbox" disabled="disabled" id="newSuperiorName" type="text"/></td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateAgentTree()" class="easyui-linkbutton">确认</a>
               <a id="btn" href="#" onclick="updateAgentTreeClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>
</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:agent:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>