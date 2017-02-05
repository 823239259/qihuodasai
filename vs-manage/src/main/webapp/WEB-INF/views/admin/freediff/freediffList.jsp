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
<title>日报表录入</title>
<%@include file="../../common/import-easyui-js.jspf"%>

<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/freediff/hkfreediff.js"></script>
<script type="text/javascript"
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:accountant:freediff:view">
		<div id="freediffTab" class="easyui-tabs"
			data-options="tools:'#tab-tools',border:false,fit:true"
			style="margin-top: 5px;">
			<div title="A股佣金过户费差" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="tb" style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">起止时间:</td>
								<td style="width: 35%">
									<table class="many">
										<tr>
											<td><input name="starttime" type="text"
												class="easyui-datebox" /> <font style="font-size: 12px;">至</font>
											</td>

											<td><input name="endtime" type="text"
												class="easyui-datebox" /></td>
										</tr>
									</table>
								</td>
								<td class="label right">恒生母账户:</td>
								<td style="width: 15%"><input name="parentaccount"
									id="parentaccount"></td>
								<td class="label right">类型:</td>
								<td><select name="qtype" id="qtype" style="width: 150px">
										<option value="">请选择</option>
										<option value="1">佣金差</option>
										<option value="2">过户费差</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right">金额区间:</td>
								<td style="width: 10%">
									<table class="many">
										<tr>
											<td><input name="minmoney" id="minmoney" type="text" />
												<font style="font-size: 12px;">至</font></td>

											<td><input name="maxmoney" id="maxmoney" 　type="text" />
											</td>
										</tr>
									</table>
								</td>
								<td class="label right">恒生子账户名:</td>
								<td style="width: 15%"><input name="account" id="account">
								</td>
								<td class="label right">录入者:</td>
								<td><input name="lrr" id="lrr" 　type="text" /></td>
							</tr>
							<tr>
								<td class="label">&nbsp;</td>
								<td class="label" colspan="5"><a id="btn" href="javascript:void(0)"
									onclick="$.easyui.datagridQuery('edatagrid','queryForm')"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:accountant:freediff:create">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-add" plain="true"
								onclick="baseUtils.openAddIframeWin(700,300,'增加','freediff')">增加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:update">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true"
								onclick="baseUtils.openEditIframeWin(700,300,'修改','freediff')">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:delete">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-remove" plain="true"
								onclick="baseUtils.deleteData('freediff')">删除</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:import">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="excelImportWuser()">导入</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:export">
							<a id="excelBtn" href="javascript:void(0)"
								onclick="$.easyui.exportExcel('edatagrid','queryForm')"
								iconCls="icon-excel" plain="true" class="easyui-linkbutton">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:export">
							<a id="excelBtn" href="javascript:void(0)"
								onclick="exportTempExcel();" iconCls="icon-excel" plain="true"
								class="easyui-linkbutton">导出模板</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="edatagrid"></table>
			</div>
			<div title="港股佣金过户费差" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="hktb" style="padding: 5px; height: auto">
					<form id="hkqueryForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">起止时间:</td>
								<td style="width: 35%">
								<input name="search_date_GTE_addtime"
								id="addtime1" class="Wdate" type="text"
								onFocus="var addtime2=$dp.$('addtime2');WdatePicker({onpicked:function(){addtime2.focus();},maxDate:'#F{$dp.$D(\'addtime2\')}'})" />
								- <input name="search_date_LTE_addtime" id="addtime2"
								class="Wdate" type="text"
								onFocus="WdatePicker({minDate:'#F{$dp.$D(\'addtime1\')}'})" />
								</td>
								<td class="label right">母账户:</td>
								<td style="width: 15%"><input name="search_LIKE_parentaccount"></td>
								<td class="label right">类型:</td>
								<td><select name="search_EQ_type" style="width: 150px">
										<option value="">请选择</option>
										<option value="1">佣金差</option>
										<option value="2">过户费差</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right">金额区间:</td>
								<td style="width: 10%">
									<table class="many">
										<tr>
											<td><input name="search_GTE_money" class="easyui-validatebox" data-options="validType:'money'" />
												<font style="font-size: 12px;">至</font></td>

											<td><input name="search_LTE_money" class="easyui-validatebox" data-options="validType:'money'" />
											</td>
										</tr>
									</table>
								</td>
								<td class="label right">交易账户名:</td>
								<td style="width: 15%"><input name="search_LIKE_account" class="easyui-validatebox">
								</td>
								<td class="label right">录入者:</td>
								<td><input name="search_LIKE_lrr" class="easyui-validatebox" /></td>
							</tr>
							<tr>
								<td class="label">&nbsp;</td>
								<td class="label" colspan="5"><a href="javascript:void(0)"
									onclick="$.easyui.datagridQuery('hkedatagrid','hkqueryForm')"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:accountant:freediff:create">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-add" plain="true"
								onclick="baseUtils.openAddIframeWin(700,300,'增加','hkstock/hkFreeDiff')">增加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:update">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true"
								onclick="hkFreeDiffOptions.openEditIframeWin(700,300,'修改')">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:delete">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-remove" plain="true"
								onclick="hkFreeDiffOptions.deleteData()">删除</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:import">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="hkFreeDiffOptions.excelImportWuser()">导入</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:export">
							<a id="excelBtn" href="javascript:void(0)"
								onclick="$.easyui.exportExcel('hkedatagrid','hkqueryForm')"
								iconCls="icon-excel" plain="true" class="easyui-linkbutton">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:accountant:freediff:export">
							<a id="excelBtn" href="javascript:void(0)"
								onclick="hkFreeDiffOptions.exportTempExcel();" iconCls="icon-excel" plain="true"
								class="easyui-linkbutton">导出模板</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="hkedatagrid"></table>
			</div>
		</div>


		<div id="freeImportId" class="easyui-dialog" title="数据导入"
			style="width: 400px; height: 200px;"
			data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
			<form method="post" action="${ctx}/admin/freediff/uploadFile"
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
							onclick="excelImport()">上传</a> <input type="submit"
							id="fileUploadForm" style="display: none;"></td>
					</tr>
				</table>
				<iframe name="target_upload" style="display: none;"></iframe>
			</form>
		</div>
		<div id="hkfreeImportId" class="easyui-dialog" title="数据导入"
			style="width: 400px; height: 200px;"
			data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
			<form method="post" action="${ctx}/admin/hkstock/hkFreeDiff/uploadFile"
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
							onclick="hkFreeDiffOptions.excelImport()">上传</a> <input type="submit"
							id="hkfileUploadForm" style="display: none;"></td>
					</tr>
				</table>
				<iframe name="target_upload" style="display: none;"></iframe>
			</form>
		</div>
		<div id="addWin" style="padding: 10px; top: 20px;"></div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountant:freediff:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>