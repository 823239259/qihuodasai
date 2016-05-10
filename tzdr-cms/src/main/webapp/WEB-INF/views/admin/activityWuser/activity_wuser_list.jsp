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
<title>活动用户</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/activity_list.js?v=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<!-- toolbar="#toolbar" -->
	<table id="dg003" title="活动用户" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/activityWuser/activityListData" pagination="true"
             toolbar="#dg003Toolbar" 
            rownumbers="true" fitColumns="true" singleSelect="false" data-options="onClickRow:onClickRow">
        <thead>
        
            <tr>
                <th field="id" data-options="checkbox:false,hidden:true"></th>
				<th field="mobile" width="150">手机号 </th>
				<th field="uname" width="150">用户姓名</th>
				<th field="idcard" width="150">身份证号</th>
				<th field="activityTypeStr" width="150">活动类型</th>
				<th field="userTypeStr" width="150">活动是否配资 </th>
				<th field="sname" width="150">所属销售</th>
				<th field="ctimeStr" width="150">注册时间</th>
				<th field="lastLoginTimeStr" width="150">最后登录时间</th>
				<!-- <th field="ctime" width="150">方案到期时间</th>
				<th field="lastLoginTime" width="150">活动结束时间</th> -->
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">是否配资:</td>
	                <td><input class="easyui-combobox" name="assetStateValue" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=isAsset'">
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">最后登录时间:</td>
	                <td><input class="easyui-combobox" name="lastLoginStateValue" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=lastLoginTime'"></td>
	                <!-- 
	                <td colspan="2">&nbsp;</td>
	                 -->
	                <td class="label right">活动类型:</td>
	                <td><input class="easyui-combobox" name="eventTypeStateValue" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=eventType', 
	                             onSelect: function(rec) {
						            onSelectComebox(rec.id);
						        }">
	                </td>
	            </tr>
	            <tr>
	            	<td class="label right">所属销售:</td>
	                <td><input name="sname" type="text" /></td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
		     <%-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="changeStatusActivity()">平仓</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endActivitySolation()">结束方案</a> --%>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="excelImportWuser()">活动用户导入</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateWuserToPain()">变为普通账户</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activitySendMessage('beforeBegin','请确认发送活动开始前提醒短信!')">活动开始前提醒短信</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resetPassword()">达人账户密码短信</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activitySendMessage('beforeEnd','请确认发送活动结束前提醒短信!')">活动结束前提醒短信</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="activitySendMessage('end','请确认发送活动结束短信!')">活动结束短信</a>
	    </div>
    <div id="activityWuserImportId" class="easyui-dialog" title="活动用户导入" style="width:400px;height:200px;"
        data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
        <form method="post" action="${ctx}/admin/activityWuser/uploadFile" target="target_upload" enctype="multipart/form-data">
	        <table class="conn" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">上传文件:</td>
	                <td colspan="2">
		            <input type="file" id="uploadFileInput" name="uploadFile">
	                </td>
	            </tr>
	            <tr>
	              <td>&nbsp;</td>
	               <td>
			        <a href="#" class="easyui-linkbutton" onclick="excelImport()">上传</a>
			        <input type="submit" id="fileUploadForm" style="display: none;">
			         </td>
	            </tr>
	        </table>
	        <iframe name="target_upload" style="display: none;"></iframe>
        </form>
    </div>
    
</body>
</html>