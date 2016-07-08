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
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/withdrawal/list.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:finance:withdrawal:view">
	<div id="withdrawalTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="所有记录" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="alltb" style="padding: 5px; height: auto">
					<div>
					<form id="searchAllForm" method="post">
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">
									<span>手机号：</span>
								</td>
								<td>
								<input name="search_LIKE_mobile" class="easyui-validatebox" id="mobile1">
								</td>
								
								
								<td class="label right">
									<span>提现时间:</span>
								</td>
								<td>
									<input name="search_date_GTE_addtime" id="startTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
									      至  <input name="search_date_LTE_addtime" id="endTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>							      
								</td>
								
							</tr>
							<tr>
								
								<td class="label right">
									<span>提现银行：</span>
								</td>
								<td>
								<input name="search_LIKE_bank" class="easyui-validatebox" id="bank1">
								</td>
								<td class="label right">
									<span>到账时间:</span>
								</td>
								<td>
									<input name="search_date_GTE_oktime" id="startTime1" class="Wdate" type="text" onFocus="var endTime1=$dp.$('endTime1');WdatePicker({onpicked:function(){endTime1.focus();},maxDate:'#F{$dp.$D(\'endTime1\')}'})"/>
									      至  <input name="search_date_LTE_oktime" id="endTime1" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime1\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">
									<span>提现状态：</span>
								</td>
								<td>
									<select id="status" class="easyui-combobox" name="search_EQ_status" style="width:174px;">
									    <option value="">请选择</option>
									    <option value="31">成功</option>
									    <option  value="4">失败</option>
									    <option  value="21">处理中</option>
									    <option value="3">已取消</option>
									</select>
								</td>
								<td class="label right">
									<span>客户姓名：</span>
								</td>
								<td>
									<input name="search_LIKE_tname" class="easyui-validatebox" id="tname">
								</td>
							</tr>
							<tr>
								<td class="label right">网银通道:</td>
				                <td>
				                    <select class="easyui-combobox" name="search_EQ_paymentChannel" id="paymentChannel">
				                        <option value="">--请选择--</option>
				                        <option value="1">联动优势</option>
				                        <option value="2">币币支付</option>
				                    </select>
				                </td>
				                <td class="label right">来源网站:</td>
				                <td>
				                    <select class="easyui-combobox" name="search_EQ_source" id="source1">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
								<td class="label right">
								</td>
								<td>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="withdrawal.list.doSearch1()">查询</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('allData','searchAllForm')">导出</a>
								</td>
							</tr>
						</table>	
						</form>		
					</div>
				</div>
				<table id="allData" data-options="toolbar:alltb"></table>
			</div>
			<div title="提现处理中审核" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="processingtb" style="padding: 5px; height: auto">
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"   style="width: 106px">
									<span>手机号：</span>
									</td>
									<td style="width: 295px">
								<input class="easyui-validatebox" id="mobile2">
								</td>
								 <td class="label right"  style="width: 106px">来源网站:</td>
				                <td >
				                    <select class="easyui-combobox" name="source2"  id="source2">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
				                <td class="label right">
								</td>
				                <td>
				                
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="withdrawal.list.doSearch2()">查询</a>
								</td>
							</tr>
						</table>				
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:finance:withdrawal:withdrawSuccess">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="withdrawal.changeStatus(31)">提现成功</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:finance:withdrawal:withdrawFail">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="withdrawal.changeStatus(3)">提现失败</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="processingData" data-options="toolbar:processingtb"></table>
			</div>
			
			<div title="提现失败" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="failtb" style="padding: 5px; height: auto">
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"  style="width: 106px">
									<span>手机号：</span>
								</td>
								<td style="width: 295px">
								
								<input class="easyui-validatebox" id="mobile3">
								</td>
								 <td class="label right"  style="width: 106px">来源网站:</td>
				                <td >
				                    <select class="easyui-combobox" name="source3" id="source3">
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
				                <td class="label right"></td>
								<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="withdrawal.list.doSearch3()">查询</a>
								</td>
							</tr>
						</table>			
					</div>
				</div>
				<table id="failData" data-options="toolbar:failtb"></table>
			</div>
			<div title="提现成功" data-options="tools:'#p-tools'" style="padding:20px;">
			
				<div id="successtb" style="padding: 5px; height: auto">
					<div>
						<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right" style="width: 106px">
									<span>手机号：</span>
								</td>
								<td style="width: 295px">
								<input class="easyui-validatebox" id="mobile4">
								</td>
								 <td class="label right"  style="width: 106px">来源网站:</td>
				                <td >
				                    <select class="easyui-combobox" name="source4" id="source4" >
				                        <option value="">--请选择--</option>
				                        <option value="1">维胜</option>
				                        <option value="2">配股宝</option>
				                    </select>
				                </td>
				                </tr>
				                <tr>
				                <td class="label right"></td>
								<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="withdrawal.list.doSearch4()">查询</a>
								</td>
							</tr>
						</table>		
					</div>
				</div>
				<table id="successData"  data-options="toolbar:successtb"></table>
			</div>
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:withdrawal:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>