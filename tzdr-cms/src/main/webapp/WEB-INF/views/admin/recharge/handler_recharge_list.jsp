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
<script type="text/javascript" src="${ctx}/static/script/recharge/handler_recharge_list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
</head>
<body>
<shiro:hasPermission name="sys:finance:rechargeHand:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
    <div title="手工现金充值" style="padding:1px;">
        <table id="dg003"  class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar"
             url="${ctx}/admin/rechargeHand/listDataHandler" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
           
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
                <th field="tname" width="150">用户姓名 </th>
				<th field="mobile" width="150">手机号 </th>
				<th field="typeStr" width="150">调账类型 </th>
				<th field="no" width="150">流水号 </th>
				<th field="money" width="150">金额 </th>
				<th field="amount" width="150">用户余额 </th>
				<th field="remark" width="150">原因 </th>
				<th field="uptimeStr" width="150">充值时间</th>
				<th field="realname" width="150">审核人</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	       <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td>
	                <input id="recharge_type" type="text" name="mobile" />
	                </td>
	                <td class="label right">调账类型:</td>
	                <td>
	                    <select class="easyui-combobox" name="type">
	                        <option value="">--请选择--</option>
	                        <option value="3">系统调账</option>
	                        <option value="4">系统冲账</option>
	                    </select>
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">充值时间:</td>
	                <td colspan="3">
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="uptimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="uptimeStr_end"></td>
	                        </tr>
	                    </table>

	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td colspan="3">
	                 <shiro:hasPermission name="sys:finance:rechargeHand:view"> 
	                <a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                </shiro:hasPermission>
	                 <shiro:hasPermission name="sys:finance:rechargeHand:export">                
	               		 <a id="excelBtn" href="#" onclick="$.easyui.exportExcel('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a>
	                </shiro:hasPermission>
	                </td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:finance:rechargeHand:recharge"> 
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="handlerPayOpen()">充值</a>
	        </shiro:hasPermission>
	    </div>
    </div>
     
</div>

<div id="addHandler" class="easyui-window" title="新增充值" style="padding:2px;width:350px;height:300px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="bankPayForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">手机号</td>
                <td><input type="text" class="easyui-validatebox" data-options="required:true" id="handlerUsername" /></td>
                <td>
                    <span id="userExitsLabel"></span>
                </td>
            </tr>
            <tr>
             <td class="label right">账户余额：</td>
              <td>
                    <span id="userAmount">0</span>元
              </td>
            </tr>
             <tr>
                <td class="label right">调账类型</td>
                <td>
	                <select id="sysTypeId" class="easyui-combobox" data-options="required:true" name="sysType" style="width:158px;">
					    <option value="1">系统调账</option>
					    <option value="2">系统冲账</option>
					</select>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td class="label right">金额</td>
                <td><input  class="easyui-numberbox" id="handlerAmountId" data-options="min:0.01,precision:2,required:true" id="bankTradeNoId" type="text" name="name" /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">流水号</td>
                <td><input  id="noId" id="bankTradeNoId" class="easyui-validatebox" data-options="required:true" type="text" name="name" /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">原因</td>
                <td>
                <textarea id="handlerReasonId" class="easyui-validatebox" data-options="required:true" style="width:100%;height:100px;"></textarea>
                </td>
                <td></td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="confirmHandlerPay()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="handlerClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>
  </shiro:hasPermission>
   <shiro:lacksPermission name="sys:finance:rechargeHand:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>