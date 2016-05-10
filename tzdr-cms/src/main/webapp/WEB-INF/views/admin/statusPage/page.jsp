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
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/statusPage/statusPage.js?version=20150629"></script>
<script type="text/javascript" src="${ctx}/static/script/hundsunAccount/subList.js"></script>
<script type="text/javascript" src="${ctx}/static/script/mainframe.js"></script>


<script type="text/javascript">
</script>
</head>
<body>
    <div style="top:100px;margin-top: 20px;">
	     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="homeUpdate()">首页更新</a>
	
		<shiro:hasPermission name="sys:system:refreshData:refresh"> 
	 			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload"  onclick="subAccount.refreshCombine()">刷新组合信息</a>
	 				<a id="refreshCache" onclick="main.refreshCache()" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新缓存</a>
	 				<a id="refreshToken" onclick="refreshToken('cms')" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新CMS恒生Token</a>
	 			 	<a id="refreshToken" onclick="refreshToken('app1')" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新APP1恒生Token</a>
	 			 	<div style="margin-top: 20px;"></div>
	 			 	<a id="refreshToken" onclick="refreshToken('app2')" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新APP2恒生Token</a>
	 			 	<a id="refreshToken" onclick="refreshToken('api')" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新API恒生Token</a>
	 			 	<a id="updateQuartz" onclick="openQuartzWin()" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">更新定时任务下次执行时间</a>
	 			 	<a id="executeFailQuartz" onclick="openFailQuartzWin()" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">手动执行佣金失败定时任务</a>
	 			 	<a id="handleUserFundFail" onclick="handleUserFundFail()" iconCls="icon-reload" href="javascript:void(0);" class="easyui-linkbutton" data-options="">手动执行资金明细-佣金收入失败数据</a>
	 	</shiro:hasPermission>
 	</div>
 	<!-- ADD window -->
	<div id="addWin"  class="easyui-window"  data-options="collapsible:false,minimizable:false,maximizable:false,width:500,height:250,
	title:'更新定时任务下次执行时间',loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true">
		<form method="post" id="addForm" style="padding-left: 18%;padding-top: 25px;">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>定时任务名称:</td>
							<td>
								<input id="triggerName" class="easyui-combobox" id="accountType" value="${parentAccount.accountType}" name="accountType" data-options="
										url:'${ctx}/admin/status/getAllTriggers',
										valueField:'valueField',
										panelHeight:100,
										required:true,
										textField:'textField'">
							</td>
						</tr>
						
						<tr>
							<td>下次执行时间:</td>
							<td>
								<input id="nextFireTime"  class="Wdate" name="nextFireTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s'})"/>
							</td>
						</tr>
						
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="updateQuartz()">确定</a>
							</td>
						</tr>
					</table>
		</form>
	</div>
	
	<!-- ADD window -->
	<div id="openFailQuartzWin"  class="easyui-window"  data-options="collapsible:false,minimizable:false,maximizable:false,width:502,height:200,
	title:'手动执行失败佣金收入定时任务',loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true">
		<form method="post" id="addForm" style="padding-left: 18%;padding-top: 25px;">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
			<table class="formTable">
				<tr>
					<td>需要执行的管理费时间:</td>
					<td>
						<input id="managementFeeTime" class="easyui-datebox"  name="managementFeeTime" type="text" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" id="executeFailQuartz" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="executeFailQuartz()">开始执行</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>