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
<title>股指期货</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/spif/list.js"></script>
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
	<shiro:hasPermission name="sys:riskmanager:spif:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="申请列表" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:spif:pass">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass()">分配账户</a>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="sys:riskmanager:spif:refuse">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="refuse()">拒绝申请</a>
						</shiro:hasPermission>
						
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/spif/apply"
		             rownumbers="true" fitColumns="true" singleSelect="true"
					>
			        <thead>
			            <tr>
			            	<th field="id" data-options="checkbox:true"></th>
							<th field="mobile" width="120" >手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="traderBond" width="120">操盘保证金</th>
							<th field="traderTotal" width="120">总操盘金额</th>
							<th field="traderMoney" width="120">融资金额</th>
							<th field="tranLever" width="120">可开仓手数</th>
							<th field="tranDay" width="120">操盘时间</th>
							<th field="tranAccount" width="150">操盘账户</th>
							<th field="tranPassword" width="150">操盘密码</th>
							<th field="appTimeStr" width="200">申请时间</th>
							<th field="programNo" width="120" >方案编号</th>
							<th field="stateTypeStrOne" width="100">申请状态</th>
			            </tr>
			        </thead>
   				</table>
			</div>

			<div title="补充保证金" data-options="tools:'#p-tools'" style="padding:20px;">

				<div id="applendMoneytb" style="padding: 5px; height: auto">
						<div style="margin-bottom: 5px">
							<shiro:hasPermission name="sys:riskmanager:spif:handleFSimpleAppendMoney">  
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="handleAppendMoney()">已处理</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="sys:riskmanager:spif:export">  
								<a  href="javascript:void(0)" onclick="$.easyui.exportExcel('applendMoneyData','queryForm3')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
							</shiro:hasPermission>
						</div>
				</div>
				<div>
					<table id="applendMoneyData" class="easyui-datagrid" pagination="true"
			            width="100%" toolbar='applendMoneytb' url="${ctx}/admin/spif/appendMoney"
			            rownumbers="true" fitColumns="true" singleSelect="true">
				        <thead>
				            <tr>
				            	<th field="id" data-options="checkbox:true"></th>
								<th field="mobile" width="120" >手机号码</th>
								<th field="realName" width="150">客户姓名</th>
								<th field="traderBond" width="150">操盘保证金</th>
								<th field="tranLever" width="120">可开仓手数</th>
								<th field="tranAccount" width="150">股指期货账户</th>
								<th field="tranPassword" width="150">股指期货密码</th>
								<th field="appendMoney" width="150">补充保证金</th>
								<th field="appendDate" width="150" formatter="timeConvert">申请追加时间</th>
								<th field="handleDate" width="150" formatter="timeConvert">处理时间</th>
								<th field="statusName" width="120">状态</th>
				            </tr>
				        </thead>
	   				</table>
				</div>
			</div>
			<div title="方案管理" >
				<div>
					<form id="queryForm3" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="mobileStr" name="mobileStr">
								</td>
								<td class="label right">
									<span>客户姓名:</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="tname" name="tname">							      
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>股指期货账户：</span>
								</td>
								<td>
									<input class="easyui-validatebox" id="tranAccount" name="tranAccount">
								</td>
								<td class="label right">
									<span>启用日期:</span>
								</td>
								<td>
									<input id="appStarttime" name="appStarttime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('appEndtime');WdatePicker({onpicked:function(){appEndtime.focus();},maxDate:'#F{$dp.$D(\'appEndtime\')}'})"/>
									      至  <input id="appEndtime" name="appEndtime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'appStarttime\')}'})"/>
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>结算状态：</span>
								</td>
								<td>
									<select id="stateType" class="easyui-combobox" name="stateType" style="width:100px;height:27px;">
									    <option value="">请选择</option>
									    <option value="2">待结算</option>
									    <option  value="4">申请结算</option>
									    <option  value="3">已结算</option>
									</select>
								</td>
								<td class="label right">
									<span>方案类型:</span>
								</td>
								<td>
									<select id="businessType" class="easyui-combobox" name="businessType" style="width:100px;height:27px;">
									    <option value="">请选择</option>
									    <option value="1">股指随心乐</option>
									    <option  value="2">股指天天乐</option>
									</select>							      
								</td>
							</tr>
							<tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('hasAuditData','queryForm3')">查询</a>
								</td>
							</tr>
						</table>	
					</form>
				</div>
				<div>
					<table id="hasAuditData" class="easyui-datagrid" pagination="true"
			            width="100%" toolbar='#hasAuditToolbar' url="${ctx}/admin/spif/balance"
			            rownumbers="true" fitColumns="true" singleSelect="true">
				        <thead>
				            <tr>
				            	<th field="id" data-options="checkbox:true"></th>
								<th field="mobile" width="150" >手机号码</th>
								<th field="tname" width="150">客户姓名</th>
								<th field="tranAccount" width="150">股指期货账户</th>
								<th field="appStarttimeStr" width="150">账户启用日期</th>
								<th field="accountEndTimeStr" width="150">账户结束日期</th>
								<th field="tranDay" width="150">申请操盘日期</th>
								<th field="useTradeDay" width="150">已操盘时间</th>
								<th field="traderBond" width="150">操盘保证金</th>
								<th field="traderTotal" width="150">总操盘金额</th>
								<th field="traderMoney" width="150">融资金额</th>
								<th field="tranLever" width="150">可开仓手数</th>
								<th field="appendTraderBond" width="150">补充保证金</th>
								<th field="tranProfitLoss" width="150">交易盈亏</th>
								<th field="tranCommission" width="150">交易佣金</th>
								<th field="useFeeManage" width="120">已使用管理费</th>
								<th field="returnFeeManage" width="120">返还管理费</th>
								<th field="endAmount" width="120">结算金额</th>
								<th field="endTime" width="120" formatter="timeConvert">结算时间</th>
								<th field="programNo" width="120">方案编号</th>
								<th field="stateTypeStrTwo" width="120">结算状态</th>
				            </tr>
				        </thead>
	   				</table>
				</div>
				<div id="hasAuditToolbar">
					<shiro:hasPermission name="sys:riskmanager:spif:export">  
						<a  href="javascript:void(0)" onclick="$.easyui.exportExcel('hasAuditData','queryForm3')" iconCls="icon-excel" plain="true" class="easyui-linkbutton" >导出</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:spif:end">  
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="end()">结算</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:spif:input">  
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="input()">录入结果</a>
					</shiro:hasPermission>
				</div>
			</div>
	</div>	
	<!-- window -->
<div id="passWin" class="easyui-window" title="分配账号" style="width:350px;height:150px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">账号:</td>
                <td>
                   <input id="account" name="account" class="easyui-validatebox"  data-options="required:true"   />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>
            <tr>
                <td class="label right">密码:</td>
                <td>
                   <input id="password" name="password"  class="easyui-validatebox" data-options="required:true" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>  

            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="passSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>

<div id="inputWin" class="easyui-window" title="录入结果" style="width:350px;height:250px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="inputForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">手机号码:</td>
                <td>
                   <input id="mobile" name="mobile" class="easyui-textbox" disabled="disabled"  />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>
            <tr>
                <td class="label right">股指期货账户:</td>
                <td>
                   <input id="inputAccount" name="account"  class="easyui-textbox" disabled="disabled" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr> 
            <tr>
                <td class="label right">操盘保证金:</td>
                <td>
                   <input id="bond" name="bond"  class="easyui-textbox" disabled="disabled" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr> 
            <tr>
                <td class="label right">追加保证金:</td>
                <td>
                   <input id="appendTraderBond" name="appendTraderBond" class="easyui-textbox" disabled="disabled"  />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr> 
            <tr>
                <td class="label right">交易盈亏:</td>
                <td>
                   <input id="profit" name="profit"  class="easyui-validatebox" data-options="required:true" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>  
            <tr>
                <td class="label right">交易佣金:</td>
                <td>
                   <input id="commission" name="commission"  class="easyui-validatebox" data-options="required:true" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>   

            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="inputSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="inputClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:spif:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>