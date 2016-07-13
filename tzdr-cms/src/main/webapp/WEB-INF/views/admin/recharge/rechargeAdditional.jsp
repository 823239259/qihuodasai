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
<title>补录充值表单</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<script type="text/javascript">
	function toUpdate(){
		var rowData=$('#edatagrid').datagrid('getSelected');
		if (null == rowData){
			eyWindow.walert("修改提示",'请选择要修改的行', 'info');
			return;
		}
		if (rowData.status != 0){
			eyWindow.walert("修改提示",'您选择的记录已经审核处理过,无法进行修改！', 'error');
			return;
		}
		baseUtils.openEditIframeWin(610,380,'修改充值表单','rechargeAdditional')
	}
</script>
<body>
	<shiro:hasPermission name="sys:finance:rechargeAdditional:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="searchForm">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>用户姓名：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_username" name="search_LIKE_username">					
						</td>
						<td class="label right">
							<span>手机号：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
						</td>
					</tr>
					<tr>
						<td class="label right">来源网站:</td>
		                <td>
		                    <select class="easyui-combobox" name="search_EQ_source" id="source1">
		                        <option value="">所有网站</option>
		                        <option value="1">维胜</option>
		                        <option value="2">维胜</option>
		                    </select>
		                </td>
						<td colspan="2">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
						</td>
				</tr>
				</table>	
			</form>	
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:finance:rechargeAdditional:create">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(610,380,'新增充值表单','rechargeAdditional')">新增充值表单</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:finance:rechargeAdditional:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="toUpdate()">修改充值表单</a>
			</shiro:hasPermission>
		</div>
	</div>
	
	<table id="edatagrid" class="easyui-datagrid"
            width="100%"
            url="${ctx}/admin/rechargeAdditional/getDatas" pagination="true"
            data-options="checkOnSelect:true,toolbar:'#tb',
            frozenColumns:[[
		            {field:'ck',checkbox:true}
			]],
            onLoadSuccess:function(data){
				datagridUtils.loadSuccess(data,'edatagrid');
			}"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	            	<th field="id" width="150" hidden="true"></th>
	            	<th field="status" hidden="true"></th>
					<th field="mobile" width="150" sortable="true">手机号 </th>
					<th field="username" width="150" sortable="true">用户姓名</th>
					<th field="alipayNo" width="150">支付宝账号</th>
					<th field="bankCard" width="150">银行卡号</th>
					<th field="tradeNo" width="150">交易号 </th>
					<th field="money" width="150" sortable="true">充值表单金额 </th>
					<th field="statusValue" width="150" sortable="true">状态 </th>
					<th field="createUser" width="150">创建人</th>
					<th field="createTime" width="150" sortable="true">创建时间</th>
					<th field="showSource" width="150">来源网站</th>
					
	            </tr>
	        </thead>
   </table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:rechargeAdditional:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>