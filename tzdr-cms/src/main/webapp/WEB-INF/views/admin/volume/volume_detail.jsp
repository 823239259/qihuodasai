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
<title>抵扣券列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/volume/volume_list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
    <div  id="proxy" style="padding:1px;height:auto;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/volume/detailData"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="code" width="150">券编号 </th>
				<th field="name" width="150">名称 </th>
				<th field="typeStr" width="150">类型</th>
				<th field="useTypeStr" width="150">使用方式</th>
				<th field="volumeMoney" width="150">券金额</th>
				<th field="realPayAmount" width="150">抵扣金额</th>
				<th field="useStateStr" width="150">状态</th>
				<th field="useDateValueStr" width="150">使用日期</th>
				<th field="useUsername" width="150">使用人</th>
				<th field="mobile" width="150">电话号码</th>
 				<th field="remark" width="150">备注</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">名称:</td>
	                <td><input name="name" type="text" /></td>
	                <td class="label right">类型:</td>
	                <td>
	                <input class="easyui-combobox" name="type" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=volumeType'">
	                </td>
	            </tr>
	            
	            <tr>
	                <td class="label right">使用方式:</td>
	                <td>
	                    <input class="easyui-combobox" name="useType" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=volumeUseType'">
	                </td>
	                <td class="label right" style="width:100px;">使用时间:<input type="hidden" name="uid" id="uid" /></td>
	                <td>
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datebox" name="useDateValue_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datebox" name="useDateValue_end"></td>
	                        </tr>
	                    </table>
	                </td>
	            </tr>
	            <tr>
	            	<td class="label right">状态:</td>
	                <td>
	                    <input class="easyui-combobox" name="stateType" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=volumeUseState'">
	                </td>
	                <td class="label right">电话号码：</td>
	                <td>
	                	<input class="easyui-validatebox" id="mobile" name="mobile">
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3">
	                	<a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                	<a id="grandbtn" href="#" onclick="grantVolumeOpen()" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">发放</a>
	                </td>
	            </tr>
	          </table>
	        </form>
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('dg003','queryForm')">导出</a>
	    </div>
    </div>

<div id="grantVolume" class="easyui-window" title="发放抵扣卷" style="width:380px;height:150px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="grantVoForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">电话:</td>
                <td>
                   <input name="phone" class="easyui-validatebox" id="phone" data-options="required:true"  type="text" />
                </td>
                <td>
                    <a id="getNamebtn" href="#" onclick="getName()" class="easyui-linkbutton">获取姓名</a>
                </td>
            </tr>
            <tr>
                <td class="label right">姓名:</td>
                <td>
                   <input name="name" id="name" disabled="true" class="easyui-validatebox"  type="text" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>  
            <tr>
                <td align="center" colspan="3">
                
               <a id="surebtn" href="#" onclick="grantVolumeSave()" class="easyui-linkbutton">确定</a>
               <a id="cancelbtn" href="#" onclick="grantVolumeClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
</body>
</html>