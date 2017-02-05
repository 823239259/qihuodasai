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
<script type="text/javascript" src="${ctx}/static/script/wuser/check_list.js?v=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
</head>
<body>
<shiro:hasPermission name="sys:customerService:wuserAuth:view">

<input id="pathPrefix" type="hidden" value="<%=com.tzdr.common.utils.ConfUtil.getContext("web.address")%>/" />
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
    <div title="未通过" style="padding:1px;">
        <table id="dg001" class="easyui-datagrid" toolbar="#dg001Toolbar"
             width="100%"
             url="${ctx}/admin/wuserAuth/wuserDataOne" pagination="true"
             data-options="checkOnSelect:true"
            rownumbers="true" fitColumns="true" singleSelect="false">
	        <thead>
	            <tr>
	                <th field="id" data-options="checkbox:true"></th>
					<th field="mobile" width="150">手机号 </th>
					<th field="tname" width="150">真实姓名</th>
					<th field="idCard" width="150">认证身份证号</th>
					<th field="ctime" width="150">注册时间 </th>
					<th field="lastSubmitVerifiedTime" width="150">最后提交认证时间</th>
	            </tr>
	        </thead>
        </table>
        <div id="dg001Toolbar">
       	 <shiro:hasPermission name="sys:customerService:wuserAuth:reset">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resetStateValue()">重置状态</a>
        	</shiro:hasPermission>
        </div>
        
    </div>
    <div title="照片认证未完成" style="padding:1px;">
        <table id="dg002" class="easyui-datagrid" width="100%" toolbar="#dg002Toolbar"
             url="${ctx}/admin/wuserAuth/wuserDataTwo" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
				<th field="tname" width="150">真实姓名</th>
				<th field="idCard" width="150">认证身份证号</th>
				<th field="ctime" width="150">注册时间 </th>
				<th field="lastSubmitVerifiedTime" width="150">最后提交认证时间</th>
            </tr>
        </thead>
    </table>
    <div id="dg002Toolbar">
     <shiro:hasPermission name="sys:customerService:wuserAuth:audit">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="sidApplyWinShow()">照片审核</a>
   	</shiro:hasPermission>
    </div>
    
    </div>
<div title="照片认证记录" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar"
             url="${ctx}/admin/wuserAuth/wuserDataThree" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
           
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
				<th field="tname" width="150">真实姓名</th>
				<th field="idCard" width="150">认证身份证号</th>
				<th field="updateUsername" width="150">修改人员 </th>
				<th field="status" width="150">照片审核情况 </th>
				<th field="notByReason" width="150">不通过原因 </th>
				<th field="ctime" width="150">注册时间 </th>
				<th field="lastSubmitVerifiedTime" width="150">最后提交认证时间</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	    	 <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                 <td class="label right">照片审核情况:</td>
	                <td>
	                	<select id="status" class="easyui-combobox" name="status" style="width:100px;height:30px;">
						    <option value="">请选择</option>
						    <option value="2">通过</option>
						    <option  value="1">未通过</option>
						</select>
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	         <shiro:hasPermission name="sys:customerService:wuserAuth:audit">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="threeApplyWinShow()">照片审核</a>
	   		</shiro:hasPermission>
	    </div>
    </div>
     
</div>



<div id="sidApplyWin" class="easyui-window" title="身份证照片审核" style="padding:2px;width:600px;height:400px;display:none; overflow: auto;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td><a id="imgA001" href="javascript:void(0)" target="_blank" ><img border="0" id="imgId001" class="frontImg" src=""></a></td>
                <td><a id="imgA002" href="javascript:void(0)" target="_blank" ><img border="0" id="imgId002" class="frontImg" src=""></a></td>
            </tr>
            <tr>
                <td><a id="imgA003" href="javascript:void(0)" target="_blank" ><img border="0" id="imgId003" class="frontImg" src=""></a></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="sidPass()" class="easyui-linkbutton">通过</a>
               <a id="btn" href="#" onclick="applayReasonShow()" class="easyui-linkbutton">不通过</a>
               </td>
            </tr>
        </table>
        
</div>

<div id="applyReasonWin" class="easyui-window" title="审核不通过原因" style="padding:2px;width:400px;height:300px;display:none; overflow:auto;font-size: 12px;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" class="conn" style="margin: 2px;font-size: 12px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right" class="label right">原因:</td>
                <td><textarea style="height:200px;width: 100%" id="applyNotByReasonId"></textarea></td>
            </tr>
            
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="sidNotPass()" class="easyui-linkbutton">提交</a>
               </td>
            </tr>
        </table>
        
</div>


<div id="threeApplyWin" class="easyui-window" title="身份证照片审核" style="padding:2px;width:600px;height:400px;display:none; overflow:auto;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td><img border="0" id="threeImgId001" class="frontImg" src=""></td>
                <td><img border="0" id="threeImgId002" class="frontImg" src=""></td>
            </tr>
            <tr>
                <td><img border="0" id="threeImgId003" class="frontImg" src=""></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="threePass()" class="easyui-linkbutton">通过</a>
               <a id="btn" href="#" onclick="threeApplayReasonShow()" class="easyui-linkbutton">不通过</a>
               </td>
            </tr>
        </table>
</div>

<div id="threeApplyReasonWin" class="easyui-window" title="审核不通过原因" style="padding:2px;width:400px;height:300px;display:none; overflow: hidden;font-size: 12px;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;font-size: 12px;" class="conn" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right" class="label right" style="width:100px;">原因:</td>
                <td><textarea style="height:200px;width: 100%" id="threeApplyNotByReasonId"></textarea></td>
            </tr>
            
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="threeNotPass()" class="easyui-linkbutton">提交</a>
               </td>
            </tr>
        </table>
        
</div>
</shiro:hasPermission>
   <shiro:lacksPermission name="sys:customerService:wuserAuth:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>