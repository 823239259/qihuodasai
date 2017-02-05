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
<title>定制配资方案列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/bespokeTrade/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
  <shiro:hasPermission name="sys:settingParams:bespokeTrade:view">
    <div  id="proxy" style="padding:1px;height:auto;">
        <table id="edatagrid" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/bespokeTrade/easyuiPage"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="name" width="150" >配资名称 </th>
				<th field="minBond" width="150" >最低保证金</th>
				<th field="maxBond" width="150" >最高保证金</th>
				<th field="multipleValue" width="150" >配资倍数</th>
				<th field="durationValue" width="150" >配资时长</th>
				<th field="shortestDuration" width="150" >最短保留时长</th>
				<th field="interestValue" width="100" >配资利息 </th>
				<th field="expeneseValue" width="100" >管理费</th>
				<th field="startTimeValue" width="200" >配资开始时间</th>
				<th field="stateValue" width="80" >状态</th>
				<th field="createUser" width="150" >创建人</th>
				<th field="createTimeValue" width="200" >创建日期</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	    	<form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                
	                <td class="label right">状态:</td>
	                <td>
	               		 <input class="easyui-combobox" name="search_EQ_state" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=bespokeTradeState'">
	                </td>
	                <td class="label right">配资开始时间:</td>
	                <td>
	                	<input class="easyui-datebox" name="search_date_GTE_startTime">至<input class="easyui-datebox" name="search_date_LTE_startTime"></td>
	                </td>
	                <td>
	                <a id="btn" href="#" onclick="datagridUtils.datagridQuery('edatagrid','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	         <shiro:hasPermission name="sys:settingParams:bespokeTrade:create">
		     	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addBespokeTrade()">新增</a>
	         </shiro:hasPermission>

		    <%--  <shiro:hasPermission name="sys:settingParams:bespokeTrade:delete">
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteBespokeTrade()">删除</a>
		     </shiro:hasPermission> --%>
		     <shiro:hasPermission name="sys:settingParams:bespokeTrade:stop">
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="stopBespokeTrade()">停用</a>
		     </shiro:hasPermission>
	    </div>
    </div>

<div id="addWin" class="easyui-window" title="新增定制方案" style="width:350px;height:390px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="addForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">方案名称:</td>
                <td>
                   <input id="addName" name="name" class="easyui-validatebox"  data-options="required:true"   />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>
            <tr>
                <td class="label right">最低保证金:</td>
                <td>
                   <input id="addMinBond" name="minBond"  class="easyui-numberspinner" data-options="min:0,required:true" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>  
            <tr>
                <td class="label right">最高保证金:</td>
                <td>
                  <input id="addMaxBond" name="maxBond"  class="easyui-numberspinner"  data-options="min:0,required:true"/>
                </td>
                <td></td>
            </tr>
            <tr>
            <tr>
                <td class="label right">配资倍数:</td>
                <td>
                	<input id="addMinMultiple" name="minMultiple" class="easyui-numberspinner"  
                	        style="width:70px" data-options="min:0,required:true"/>&nbsp;至&nbsp;
                	<input id="addMaxMultiple" name="maxMultiple" class="easyui-numberspinner"  
                	        style="width:70px" data-options="min:0,required:true"/>&nbsp;倍&nbsp;
                </td>
                <td></td>
            </tr>
             <tr>
                <td class="label right">配资时长:</td>
                <td>
                	<input id="addMinDuration" name="minDuration" class="easyui-numberspinner"  
                	        style="width:70px" data-options="min:1,required:true"/>&nbsp;至&nbsp;
                	<input id="addMaxDuration" name="maxDuration" class="easyui-numberspinner"  
                	        style="width:70px" data-options="min:1,required:true"/>&nbsp;天&nbsp;
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">最短保留时长:</td>
                <td>
                	<input id="addShortestDuration" name="shortestDuration" class="easyui-numberspinner"  
                           style="width:100px"  data-options="min:0, required:true"/>&nbsp;天&nbsp;
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">配资利息:</td>
                <td>
                	<input id="addInterest" name="interest" class="easyui-numberspinner"  
                           precision="2" increment="0.1" style="width:100px"  data-options=" min:0.00,required:true"/>&nbsp;%&nbsp;
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">管理费:</td>
                <td>
                	<input id="addExpenese" name="expenese" class="easyui-numberspinner"  
                           precision="2" increment="0.1" style="width:100px"  data-options=" min:0.00 ,required:true"/>&nbsp;%&nbsp;
                </td>
                <td></td>
            </tr>
            <tr >
                <td class="label right">配资开始时间:</td>
                <td>
                    <input id="addStartTime" class="easyui-datetimebox"   name="startTimeValue" data-options="required:true" />
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="addBespokeTradeSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="addBespokeTradeClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>

</shiro:hasPermission>
<shiro:lacksPermission name="sys:settingParams:bespokeTrade:view">
			<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>