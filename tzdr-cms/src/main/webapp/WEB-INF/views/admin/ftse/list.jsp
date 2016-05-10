<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>A50</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/ftse/list.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
function timeConvert(value,rowData,rowIndex) {
    if (value != null && value != ''){
    	return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss')
    }else if (value == 2){
    	return ""; 
    }  
} 
</script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:wellGoldA50:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="申请列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<form id="auditSearchForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">					
								</td>
								<td class="label right">
									<span>手机号码：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>提交时间：</span>
								</td>
								<td>
									<input id="startTime" name="search_date_GTE_appTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
								      至  <input id="endTime" name="search_date_LTE_appTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>							      
								</td>
								<td class="label right">
									<span>开仓手数</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_EQ_tranLever" name="search_EQ_tranLever">					
								</td>
								
							</tr>
							<tr>
								<td class="label right">
									<span> </span>
								</td>
								<td >
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','auditSearchForm')">查询</a>
								</td>
							</tr>
						</table>	
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:pass">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">分配账户</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:refuse">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="refuse()">拒绝申请</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:refuse">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel"   plain="true" onclick="$.easyui.exportExcel('edatagrid','auditSearchForm')">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:refuse">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/wellGoldA50/getDatas?type=0"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="mobile" width="150" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="traderBond" width="150">操盘保证金(元)</th>
							<th field="bondDollar" width="150">入金金额(美元)</th>
							<th field="lossMoney" width="160">授信金额(美元)</th>
							<th field="lineLoss" width="160">亏损平仓线(美元)</th>
							<th field="traderTotal" width="160">总操盘资金(美元)</th>
							<th field="tranLever" width="160">可开仓手数</th>
							<th field="tranAccount" width="120">操盘账户</th>
							<th field="tranPassword" width="120">操盘密码</th>
							<th field="appTime" width="200">提交时间</th>
							<th field="updateTime" width="200">处理时间</th>
							<th field="programNo" width="150">方案编号</th>
							<th field="stateType" width="100">状态</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="方案管理" >
				<div id="hasAuditToolbar" style="padding: 5px; height: auto">
					<form id="queryForm3" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>手机号码：</span></td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">
								</td>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">							      
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>操盘账户：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tranAccount" name="search_LIKE_tranAccount">
								</td>
								<td class="label right">
									<span>结算状态：</span>
								</td>
								<td>
									<select id="search_EQ_stateType" class="easyui-combobox" name="search_EQ_stateType" style="width:100px;height:27px;">
									    <option value="" selected="selected">所有状态</option>
									    <option value="4">操盘中</option>
									    <option value="2">申请结算</option>
									    <option value="3">待结算</option>
									    <option value="6">已结算</option>
									</select>
								</td>
								
							</tr>
							<tr>
							<td class="label right">
									<span>启用日期：</span>
								</td>
								<td>
									<input id="appStarttime" name="search_date_GTE_appStarttime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('appEndtime');WdatePicker({onpicked:function(){appEndtime.focus();},maxDate:'#F{$dp.$D(\'appEndtime\')}'})"/>
									      至  <input id="appEndtime" name="search_date_LTE_appStarttime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'appStarttime\')}'})"/>
								</td>
								<td class="label right">
									<span>申请结算日期：</span>
								</td>
								<td>
									<input id="startTime2" name="search_date_GTE_appEndTime" class="Wdate" type="text" onFocus="var endTime5=$dp.$('endTime2');WdatePicker({onpicked:function(){endTime2.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}'})"/>
									      至  <input id="endTime2" name="search_date_LTE_appEndTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}'})"/>
								
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>开仓手数</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="search_EQ_tranLever" name="search_EQ_tranLever">					
								</td>
								<td colspan="2">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('hasAuditData','queryForm3')">查询</a>
								</td>
							</tr>
						</table>	
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:input">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="input()">录入结果</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:end">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="end()">结算</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:wellGoldA50:export">  
							<a  href="javascript:void(0)" onclick="$.easyui.exportExcel('hasAuditData','queryForm3')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
						</shiro:hasPermission>
					</div>
				</div>
				<div>
					<table id="hasAuditData" class="easyui-datagrid" pagination="true"
			            width="100%" toolbar='#hasAuditToolbar' url="${ctx}/admin/wellGoldA50/getDatas?type=1"
			            rownumbers="true" fitColumns="true" singleSelect="true"
						data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
				        <thead>
				            <tr>
				            	<th field="id" hidden="true"></th>
								<th field="mobile" width="150" >手机号码</th>
								<th field="tname" width="150">客户姓名</th>
								<th field="tranAccount" width="150">操盘账户</th>
								<th field="appStarttime" width="150">账户启用日期</th>
								<th field="tranLever" width="160">可开仓手数</th>
								<th field="useTranDay" width="150">已操盘时间</th>
								<th field="traderBond" width="150">操盘保证金(元)</th>
								<th field="traderTotal" width="160">总操盘资金(美元)</th>
								<th field="tranProfitLoss" width="160">交易盈亏(美元)</th>
								<th field="tranActualLever" width="160">交易手数</th>
								<th field="tranFeesTotal" width="160">交易手续费(元)</th>
								<th field="endParities" width="160">汇率</th>
								<th field="endAmount" width="160">结算金额(元)</th>
								<th field="appEndTime" width="160">申请结算时间</th>
								<th field="endTime" width="160">结算时间</th>
								<th field="programNo" width="150">方案编号</th>
								<th field="stateType" width="100">结算状态</th>
				            </tr>
				        </thead>
	   				</table>
				</div>
			</div>
	</div>	
	<!-- window 申请 分配帐号弹框-->
	<div id="passWin" class="easyui-window" title="分配/修改 账号" 
		style="width:350px;height:150px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">账号:</td>
                <td>
                   <input id="tranAccount" name="tranAccount" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">密码:</td>
                <td>
                   <input id="tranPassword" name="tranPassword" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	<!-- window 结算录入信息弹框 -->
	<div id="inputWin" class="easyui-window" title="录入结果" 
		style="width:450px;height:250px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="inputForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">手机号码:</td>
                <td>
                   <input id="mobile" name="mobile" class="easyui-textbox" disabled="disabled"  />
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">操盘账号:</td>
                <td>
                   <input id="Account" name="Account"  class="easyui-textbox" disabled="disabled" />
                </td>
                <td><span></span></td>
            </tr> 
            <tr>
                <td class="label right">操盘保证金:</td>
                <td>
                   <input id="traderBond" name="traderBond"  class="easyui-textbox" disabled="disabled" />
                </td>
                <td><span></span></td>
            </tr> 
            <tr>
                <td class="label right">交易盈亏($):</td>
                <td>
                   <input id="tranProfitLoss" name="tranProfitLoss"  class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span style="color: red;">盈亏为负，需添加 “-”号</span></td>
            </tr>  
            <tr>
                <td class="label right">交易手数:</td>
                <td>
                   <input id="tranActualLever" name="tranActualLever"  class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span></span></td>
            </tr>   
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="javascript:void(0);" onclick="inputSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="javascript:void(0);" onclick="inputClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:wellGoldA50:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>