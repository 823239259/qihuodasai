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
<title>登录</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userTrade/monitorList.js?version=2015062501"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
function openEditwin(width,height,title,modelName){
	var rowData=$('#datagrid2').datagrid('getSelected');
	if (null == rowData){
		eyWindow.walert("修改提示",'请选择要修改的方案', 'info');
		return;
	}
	//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=edit&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/'+modelName+'/edit?fromType=edit&id='+rowData.groupId});
	$('#addWin').window('open');
}



</script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:monitor:view">
	<div id="tabWindow" class="easyui-tabs" style="height:auto;">
		  <%-- <div title="钱江版" style="padding:1px;">
				<div id="tb" style="padding: 5px; height: auto">
					<div>
						<form id="searchForm" method="post">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									 <td class="label right">
									 	<span>恒生账户名 </span>
									 </td>
									 <td>
									 	<input class="easyui-validatebox" id="accountName" name="search_LIKE_accountName"> 
									 </td>
									<td class="label right">
										<span>恒生帐号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="accountNo" name="search_LIKE_account">
									 </td>
								</tr>
								<tr>
									<td class="label right">
										<span>母账户：</span>
									</td>
									<td >
										<input class="easyui-combobox" id="parentAccountNo" name="search_LIKE_parentAccountNo"  data-options="
										url:'${ctx}/admin/parentAccount/getByAccountGenre?accountGenre=0',
										valueField:'accountNo',
										panelHeight:100,
										textField:'accountName'">
									 </td>
									 <td class="label right">
										<span>方案类型：</span>
									</td>
									<td >
										<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
	                    						 url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>手机号：</span>
									</td>
									<td colspan="3">
										<input class="easyui-validatebox" id="mobile" name="search_LIKE_mobile">
									</td>
									
									<td class="label right">
										<span>是否穿仓：</span>
									</td>
									<td>
										<select id="isBreakStock" class="easyui-combobox" name="isBreakStock" style="width:174px;">
											<option value="">--请选择--</option>
											<option value="0">否</option>
											<option value="1">是</option>
										</select>
									</td>
									
								</tr>
								<tr>
									<td class="label right">
										<span></span>
									</td>
									<td class="label" colspan="3">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('edatagrid','searchForm')">导出EXCEL</a>
									 </td>
								</tr>
							</table>	
						</form>	
					</div>
					<div style="margin-bottom: 5px;margin-top: 10px;">
						<shiro:hasPermission name="sys:riskmanager:monitor:limitBuy">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(1)">限制买入</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:limitSell"> 
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(2)">限制卖出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:cancelLimit"> 
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(0)">取消限制</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:unwinding"> 
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(3)">平仓</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:end"> 
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(4)">终结方案</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:coverOverWarning"> 
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(0,1,'补到补仓线')">补到补仓线</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:monitor:coverOverOpen"> 
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(0,2,'补到平仓线')">补到平仓线</a>
						</shiro:hasPermission>
					</div>
				</div>
			<table id="edatagrid"></table>
		 </div> --%>
	     <div title="钱隆TTS" style="padding:1px;">
     			<div>
					<form id="queryForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								 <td class="label right">
								 	<span>交易账户名 </span>
								 </td>
								 <td>
								 	<input class="easyui-validatebox" id="accountName" name="accountName"> 
								 </td>
								<td class="label right">
									<span>交易帐号：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="accountNo" name="account">
								 </td>
							</tr>
							<tr>
								<td class="label right">
									<span>母账户：</span>
								</td>
								<td>
									<input class="easyui-combobox" id="parentAccountNo" name="parentAccountNo"  data-options="
										url:'${ctx}/admin/parentAccount/getByAccountGenre?accountGenre=1',
										valueField:'accountNo',
										panelHeight:100,
										textField:'accountName'">
								 </td>
								 <td class="label right">
								 	<span>使用天数： </span>
								 </td>
								 <td>
								 	<input class="easyui-validatebox" id="useDays" name="useDays"> 
								 </td>
							</tr>
							<tr>
								<td class="label right">
									<!-- <span>限制买入状态： </span> -->
									<span>用户类型：</span>
								</td>
								<td>
									<select id="state" class="easyui-combobox" name="userType" style="width:174px;">
										<option value="">--请选择--</option>
										<option value="0">普通用户</option>
										<option value="1">8800用户</option>
										<option value="2">6600用户</option>
									</select>
									<!-- <select id="state" class="easyui-combobox" name="buyStatus" style="width:174px;">
										<option value="">--请选择--</option>
										<option value="0">未限制</option>
										<option value="1">已限制</option>
									</select> -->
									<%-- <input class="easyui-combobox" id="parentAccountNo" name="search_LIKE_parentAccountNo"  data-options="
									url:'${ctx}/admin/parentAccount/getByAccountGenre?accountGenre=0',
									valueField:'accountNo',
									panelHeight:100,
									textField:'accountName'"> --%>
								</td>
								<td class="label right">
									<!-- <span>用户类型：</span> -->
									<span>方案类型：</span>
								</td>
								<td>
									<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
                  				   url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
									<%-- <input class="easyui-combobox" id="parentAccountNo" name="search_LIKE_parentAccountNo"  data-options="
									url:'${ctx}/admin/parentAccount/getByAccountGenre?accountGenre=0',
									valueField:'accountNo',
									panelHeight:100,
									textField:'accountName'"> --%>
								 </td>
							</tr>
							<tr>
								<td class="label right">
									<!-- <span>方案类型：</span> -->
									<span>手机号：</span>
								</td>
								<td >
									<input class="easyui-validatebox" id="mobile2" name="mobile">
								<%-- <input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
                  				   url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'"> --%>
								</td>
								<td class="label right">
									<!-- <span>手机号：</span> -->
									<span></span>
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('datagrid2','queryForm')">查询</a>
									<!-- <input class="easyui-validatebox" id="mobile2" name="mobile"> -->
								</td>
							</tr>
							<!-- <tr>
								<td class="label right">
									<span></span>
								</td>
								<td class="label" colspan="3">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('datagrid2','queryForm')">查询</a>
								 </td>
							</tr> -->
						</table>	
					</form>	
				</div>
				<div>
		        <table id="datagrid2" class="easyui-datagrid" width="100%" toolbar="#dg002Toolbar"
		             url="${ctx}/admin/monitor/listData?feeType=2&handType=1" pagination="true"
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
		                <th field="groupId" data-options="checkbox:true"></th>
		               
						<th field="accountName" width="150">交易账户名 </th>
						<th field="account" width="150">交易账号</th>
						<th field="mobile" width="150">手机号码</th>
						<th field="uname" width="150">用户姓名</th>
						<th field="totalLeverMoney" width="150">风险保证金</th>
						<th field="totalLending" width="150">配资金额</th>
						<th field="totalOperateMoney" width="150">总操盘资金</th>
						<th field="warning" width="150">亏损补仓线</th>
						<th field="open" width="150">亏损平仓线</th>
						<th field="useDays" width="150">使用天数</th>
						<!-- <th field="buyStatus" width="150">买入状态</th> -->
						<th field="activityTypeStr" width="150">方案类型</th>
		            </tr>
		        </thead>
		    </table>
		    </div>
		    <div id="dg002Toolbar">
		    <shiro:hasPermission name="sys:riskmanager:monitor:end">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.endHandTrade('datagrid2')">终结方案</a>
		   	</shiro:hasPermission>
		   	<%-- <shiro:hasPermission name="sys:riskmanager:monitor:limitBuy">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.changeStatus(1, 'datagrid2')">已限制</a>
			</shiro:hasPermission> --%>
			<!-- 
		   	<shiro:hasPermission name="sys:riskmanager:monitor:coverOverWarning"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(1,1,'补到补仓线')">补到补仓线</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:monitor:coverOverOpen"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(1,2,'补到平仓线')">补到平仓线</a>
			</shiro:hasPermission>
			 -->
		    </div>
   		</div>
   		
   		<%-- <div title="同花顺手动版" style="padding:1px;">
     			<div>
					<form id="queryThsForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								 <td class="label right">
								 	<span>交易账户名 </span>
								 </td>
								 <td>
								 	<input class="easyui-validatebox" id="accountName" name="accountName"> 
								 </td>
								<td class="label right">
									<span>交易帐号：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="accountNo" name="account">
								 </td>
							</tr>
							<tr>
								<td class="label right">
									<span>母账户：</span>
								</td>
								<td>
									<input class="easyui-combobox" id="parentAccountNo" name="parentAccountNo"  data-options="
										url:'${ctx}/admin/parentAccount/getByAccountGenre?accountGenre=2',
										valueField:'accountNo',
										panelHeight:100,
										textField:'accountName'">
								 </td>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td colspan="3">
									<input class="easyui-validatebox" id="mobile3" name="mobile">
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span></span>
								</td>
								<td colspan="3">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('thsDatagrid','queryThsForm')">查询</a>
								 </td>
							</tr>
						</table>	
					</form>	
				</div>
				<div>
		        <table id="thsDatagrid" class="easyui-datagrid" width="100%" toolbar="#thsToolbar"
		             url="${ctx}/admin/monitor/listData?feeType=3&handType=2" pagination="true"
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
		                <th field="groupId" data-options="checkbox:true"></th>
		               
						<th field="accountName" width="150">交易账户名 </th>
						<th field="account" width="150">交易账号</th>
						<th field="mobile" width="150">手机号码</th>
						<th field="uname" width="150">用户姓名</th>
						<th field="totalLeverMoney" width="150">风险保证金</th>
						<th field="totalLending" width="150">配资金额</th>
						<th field="totalOperateMoney" width="150">总操盘资金</th>
						<th field="warning" width="150">亏损补仓线</th>
						<th field="open" width="150">亏损平仓线</th>
		            </tr>
		        </thead>
		    </table>
		    </div>
		    <div id="thsToolbar">
		     <shiro:hasPermission name="sys:riskmanager:monitor:end">
		            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.endHandTrade('thsDatagrid')">终结方案</a>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sys:riskmanager:monitor:coverOverWarning"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(1,1,'补到补仓线')">补到补仓线</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:monitor:coverOverOpen"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="monitor.openCoverWin(1,2,'补到平仓线')">补到平仓线</a>
			</shiro:hasPermission>
		    </div>
   		</div> --%>
   		
   		
	</div>
	<!-- ADD window -->
	<!-- <div id="coverWin" class="easyui-window" data-options="closed:true,modal:true" style="padding:10px;top: 20px;">
		<form method="post" id="coverForm" style="padding-left: 22%;">
			<input type="hidden" id="uid" value="">
			<input type="hidden" id="groupId" value="">
			<input type="hidden" id="coverType" value="">
			<input type="hidden" id="coverIndex" value="">
			<table class="formTable">
				<tr>
					<td>补仓金额:</td>
					<td>
						<input class="easyui-numberbox" id="coverMoney" type="text" name="coverMoney" data-options="min:0.01,precision:2,required:true" >
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-add'"  onclick="monitor.coverMoney()">确认</a>
					</td>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="monitor.closeCoverWin()">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div> -->
	<!-- window -->
	
	<div id="coverWin" class="easyui-window" style="padding:2px;width:323px;height:120px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="bankPayForm">
        <input type="hidden" id="uid" value="">
		<input type="hidden" id="groupId" value="">
		<input type="hidden" id="coverType" value="">
		<input type="hidden" id="coverIndex" value="">
        <table border="0" style="font-size:12px;" cellpadding="0" width="100%" class="conn" cellspacing="0">
            <tr>
                <td class="label right">补仓金额:</td>
                <td><input  class="easyui-numberbox" id="coverMoney" type="text" name="coverMoney" data-options="min:0.01,precision:2,required:true"/></td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                	<a id="btn" href="javascript:void(0)" onclick="monitor.coverMoney()" class="easyui-linkbutton">确认</a>
               		<a id="btn" href="javascript:void(0)" onclick="monitor.closeCoverWin()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>
	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:monitor:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>