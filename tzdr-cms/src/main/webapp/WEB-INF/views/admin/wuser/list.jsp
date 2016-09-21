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
<script type="text/javascript" src="${ctx}/static/script/wuser/list.js?v=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:customerService:wuser:view">
<!-- toolbar="#toolbar" -->
	<table id="dg" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/wUser/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="userType" width="150">用户类型</th>
				<th field="allocationMoney" width="150">配资金额</th>
				<th field="avlBal" width="150">账号余额</th>
				<th field="frzBal" width="150">冻结金额</th>
				<th field="totalCharge" width="150">总充值金额</th>
				<th field="totalOperate" width="150">总申请操盘金额</th>
				<th field="htranActualLever" width="150">恒指操盘手数</th>
				<th field="ytranActualLever" width="150">原油操盘手数</th>
				<th field="atranActualLever" width="150">富时A50操盘手数</th>
				<th field="interActualLever" width="150">国际综合操盘手数</th>
			    <th field="withDrawMoney" width="150">累计提现金额</th>
				<th field="idcard" width="150">身份证号</th>
				<th field="email" width="150">邮箱 </th>
				<th field="alipayAccount" width="150">支付宝帐号</th>
				<th field="ctimeStr" width="150">注册时间 </th>
				<th field="lastLoginTimeStr" width="150">最后登陆时间</th>
				<th field="sourceName" width="120">来源网站</th>
				<th field="channel" width="120">渠道来源</th>
				<th field="keyword" width="120">关键词来源</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">注册时间:</td>
	                <td>
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="ctimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="ctimeStr_end"></td>
	                        </tr>
	                    </table>
	                </td>
	                <td class="label right">最后登录时间:</td>
	                <td> 
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="lastLoginTimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="lastLoginTimeStr_end"></td>
	                        </tr>
	                    </table>
	                 </td>
	                
	            </tr>
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">邮箱:</td>
	                <td><input name="mail">
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">真实姓名:</td>
	                <td><input name="tname" type="text" /></td>
	                <td class="label right">支付宝帐号:</td>
	                <td><input name="alipayAccount">
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">渠道来源:</td>
	                <td><input name="channel" type="text" /></td>
	                <td class="label right">关键词来源:</td>
	                <td><input name="keyword">
	                </td>
	            </tr>
	            <tr>
	            	<td class="label right">来源网站:</td>
	                <td>
						<select id="source" class="easyui-combobox" name="source" style="width:100px;height:30px;">
						    <option value="">请选择</option>
						    <option value="7">维胜APP</option>
						    <option value="1">维胜Web</option>
						    <option value="5">配股宝Web</option>
						    <option value="2">配股宝Wap</option>
						    <option value="6">喊单直播间</option>
						</select>
					</td>
	                <td class="label right">&nbsp;</td>
	                <td><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openMoneyDetail()">查询资金明细</a>
           <%--  <shiro:hasPermission name="sys:customerService:wuser:insteadActivityUser">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="insteadActivityUser()">变为6600活动用户</a>
            </shiro:hasPermission> --%>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('dg','queryForm')">导出</a>
    </div>
    <!-- <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div> -->
   </shiro:hasPermission>
   <shiro:lacksPermission name="sys:customerService:wuser:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
	
	<div id="moneyDetail" class="easyui-window" title="资金明细列表" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:800px;height:400px;">
        
        <form id="queryFormDetail" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right" style="width:100px;">时间:<input type="hidden" name="uid" id="uid" /></td>
	                <td>
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="uptimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="uptimeStr_end"></td>
	                        </tr>
	                    </table>
	                </td>
	                <td class="label right">收支类型:</td>
	                <td>
	                    <input class="easyui-combobox" name="type" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=userfundType'">
	                </td>
	            </tr>
                <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="detailBtn" href="#" onclick="$.easyui.datagridQuery('dgdetail','queryFormDetail')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>		
	                
	         </table>
	   </form>
	   <table id="dgdetail" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/wUser/queryUserFundList" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true" showFooter="true">
	        <thead>
	            <tr>
					<th field="addtimeStr" width="130" sortable="true">提交时间 </th>
					<th field="uptimeStr" width="130" sortable="true">到账时间 </th>
					<th field="typeStr" width="100">类型</th>
					<th field="inMoney" width="120">收入</th>
					<th field="outMoney" width="100">支出</th>
					<th field="payStatusStr" width="100">支付状态</th>
					<th field="amount" width="120">平台余额</th>
	            </tr>
	        </thead>
       </table>       
        
    </div>
	
</body>
</html>