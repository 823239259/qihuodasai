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
<title>提现黑名单</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/script/freediff/freediff.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/script/drawBlackList/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:drawBlackList:view">
<!-- toolbar="#toolbar" -->
       <div id="edatagridToolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	           
					<tr>
	                
	                <td class="label right">手机号:</td>
	                <td style="width:15%">
	                <input name="mobile" id="mobile" >
	                </td>
	                <td class="label right">用户姓名:</td>
	                <td>
	                 	<input name="tname" id="tname"　type="text" />
	                </td>
	            </tr>
	              <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="5"><a id="btn" href="javascript:void(0)" onclick="$.easyui.datagridQuery('edatagrid','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	           </table>
	        </form>
	        	<shiro:hasPermission name="sys:riskmanager:drawBlackList:create">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(700,300,'增加','drawBlackList')">增加</a>
		     </shiro:hasPermission>
		     
		     <shiro:hasPermission name="sys:riskmanager:drawBlackList:update">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditIframeWin(700,300,'修改','drawBlackList')">修改</a>
		     </shiro:hasPermission>
		     	
		     	<shiro:hasPermission name="sys:riskmanager:drawBlackList:delete">  
		      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="drawBlackList.deleteData('drawBlackList')">删除</a>
		     </shiro:hasPermission>
		     <shiro:hasPermission name="sys:riskmanager:drawBlackList:import">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-excel" plain="true"
					onclick="drawBlackList.openImportWindow()">导入</a>
			</shiro:hasPermission>
		     <shiro:hasPermission name="sys:riskmanager:drawBlackList:export">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" 
				plain="true" onclick="$.easyui.exportExcel('edatagrid','queryForm')">导出</a>
			</shiro:hasPermission>
		    </div>
	<table id="edatagrid" title="提现黑名单" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/drawBlackList/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
        
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="80">手机号</th>
				<th field="tname" width="80">用户姓名</th>
				<th field="avlBal" width="80">账户余额</th>
				<th field="money" width="80">配资金额</th>
				<th field="frzBal" width="80">冻结金额</th>
				<th field="debts" width="80">欠费金额 </th>
				<th field="createUser" width="80">创建人</th>
				<th field="createtime" width="80">创建时间</th>
				 <th field="reason" width="250">原因</th>
				
            </tr>
        </thead>
    </table>
    	<div id="addWin" style="padding:10px;top: 20px;"></div>
    	<div id="excelImport" class="easyui-dialog" title="数据导入"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
				<form method="post" action="${ctx}/admin/drawBlackList/uploadFile"
					target="target_upload" enctype="multipart/form-data">
					<table class="conn" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label right">上传文件:</td>
							<td colspan="2"><input type="file" id="uploadFileInput"
								name="uploadFile"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><a href="#" class="easyui-linkbutton"
								onclick="drawBlackList.importExcelData()">上传</a></td>
						</tr>
					</table>
					<iframe name="target_upload" style="display: none;"></iframe>
				</form>
			</div>
    	
    	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:drawBlackList:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>