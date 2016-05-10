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
  <shiro:hasPermission name="sys:operationalConfig:volume:view">
    <div  id="proxy" style="padding:1px;height:auto;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/volume/listData"  pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="typeStr" width="150" sortable="true">类型 </th>
				<th field="name" width="150" sortable="true">名称 </th>
				<th field="typeCode" width="150" sortable="true">类型编号</th>
				<th field="useTypeStr" width="150" sortable="true">使用方式</th>
				<th field="money" width="150" sortable="true">金额</th>
				<th field="releaseNum" width="150" sortable="true">张数</th>
				<th field="useNum" width="150" sortable="true">使用张数</th>
				<th field="startupDateValueStr" width="150" sortable="true">启用日期</th>
				<th field="endDateValueStr" width="150" sortable="true">有效日期 </th>
				<th field="endDayValueStr" width="150" sortable="true">有效周期</th>
				<th field="dealStartDateNotTimeValueStr" width="150" sortable="true">发布日期</th>
				<th field="dealEndDateNotTimeValueStr" width="150" sortable="true">结束日期</th>
				<th field="createUserName" width="150" sortable="true">创建人</th>
				<th field="createTimeStr" width="150" sortable="true">创建日期</th>
				<th field="stateValueStr" width="150" sortable="true">状态</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	         <shiro:hasPermission name="sys:operationalConfig:volume:create">
		     	 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addVolumeOpen()">新增</a>
	         </shiro:hasPermission>
	         <shiro:hasPermission name="sys:operationalConfig:volume:update">
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateVolumeOpen()">修改</a>
		     </shiro:hasPermission>
		     <shiro:hasPermission name="sys:operationalConfig:volume:delete">
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteVolume()">删除</a>
		     </shiro:hasPermission>
		     <shiro:hasPermission name="sys:operationalConfig:volume:stop">
		         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="stopVolume()">停用</a>
		     </shiro:hasPermission>
	    </div>
    </div>

<div id="addVolume" class="easyui-window" title="新增抵扣卷" style="width:350px;height:390px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="voForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">抵扣券名称:</td>
                <td>
                   <input name="name" class="easyui-validatebox" id="addName" data-options="required:true"  type="text" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>
            <tr>
                <td class="label right">类型:</td>
                <td>
                   <input name="type" id="addType"  class="easyui-combobox" value="1" type="text"
                    data-options="required:true,valueField: 'id', textField:'text',url:'${ctx}/admin/component/dictCombobox?typeKey=volumeType'" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>  
            <tr>
                <td class="label right">使用方式:</td>
                <td>
                   <input class="easyui-combobox"  id="addUseType" name="useType" value="1"
                  data-options="required:true,valueField: 'id', textField:'text',url:'${ctx}/admin/component/dictCombobox?typeKey=volumeUseType'" 
                  type="text"  />
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">金额:</td>
                <td><input class="easyui-numberbox" data-options="required:true"  id="addMoney" name="money" data-options="required:true,min:0,precision:2" type="text"  /></td>
                <td></td>
            </tr>
             <tr>
                <td class="label right">张数:</td>
                <td><input class="easyui-numberbox" id="addReleaseNum" name="releaseNum" data-options="required:true,min:0,precision:0,max:9999" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">发放开始时间:</td>
                <td><input class="easyui-datetimebox" data-options="required:true" id="addDealStartDateValueStr" name="dealStartDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">发放结束时间:</td>
                <td><input class="easyui-datetimebox" data-options="required:true"  id="addDealEndDateValueStr" name="dealEndDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">启动日期:</td>
                <td><input class="easyui-datetimebox" data-options="required:true" id="addStartupDateValue" name="startupDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr id="allChooseDeadlineType">
                <td class="label right">有效日期:</td>
                <td>
                    <table border="0" class="many" cellpadding="0" cellspacing="0">
                        <tr>
                            <td><input type="radio" class="hasDateClass" value="0" checked="checked" name="deadlineType" ></td>
                            <td>截止日期</td>
                            <td><input type="radio" class="hasDateClass" value="1" name="deadlineType"></td>
                            <td>使用周期</td>
                        </tr>
                    </table>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr id="in_datetime">
                <td class="label right">&nbsp;</td>
                <td><input class="easyui-datetimebox"  id="addEndDateValue" name="endDateValueStr"  /></td>
                <td>&nbsp;</td>
            </tr>
            <tr style="display:none;" id="in_day">
                <td class="label right">&nbsp;</td>
                <td>
                   <table class="many" border="0" cellpadding="0" cellspacing="0">
                       <tr>
                           <td><input class="easyui-numberbox"  id="addEndDayValue" name="endDayValue" data-options="min:0,precision:0,max:9999" type="text" /></td>
                           <td>天</td>
                       </tr>
                   </table>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="addVolumeSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="addVolumeClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>

<div id="updateVolume" class="easyui-window" title="修改抵扣卷" style="width:350px;height:390px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="voUpdateForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td class="label right">抵扣券名称:</td>
                <td>
                   <input type="hidden" name="id" id="updateId">
                   <input name="name" id="updateName" disabled="disabled" type="text" />
                </td>
                <td>
                    <span ></span>
                </td>
            </tr>
        
            <tr>
                <td class="label right">类型:</td>
                <td>
                   <input name="type" id="updateType" class="easyui-combobox" value="1" type="text"
                    data-options="disabled:true,valueField: 'id', textField:'text',url:'${ctx}/admin/component/dictCombobox?typeKey=volumeType'" />
                </td>
                <td>
                    <span id="userExitsLabel"></span>
                </td>
            </tr>  
            <tr>
                <td class="label right">使用方式:</td>
                <td>
                   <input class="easyui-combobox" id="updateUseType" name="useType" value="1"
                  data-options="disabled:true,valueField: 'id', textField:'text',url:'${ctx}/admin/component/dictCombobox?typeKey=volumeUseType'" 
                  type="text"  />
                </td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">金额:</td>
                <td><input class="easyui-numberbox" id="updateMoney" name="money" data-options="disabled:true,min:0,precision:2" type="text"  /></td>
                <td></td>
            </tr>
             <tr>
                <td class="label right">张数:</td>
                <td><input class="easyui-numberbox" id="updateReleaseNum" name="releaseNum" data-options="required:true,min:0,precision:0,max:9999" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">发放开始时间:</td>
                <td><input class="easyui-datetimebox" id="updateDealStartDateValueStr" data-options="disabled:true" name="dealStartDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">发放结束时间:</td>
                <td><input class="easyui-datetimebox" id="updateDealEndDateValueStr" data-options="disabled:true" name="dealEndDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">启动日期:</td>
                <td><input class="easyui-datetimebox" id="updateStartupDateValue" disabled="true" name="startupDateValueStr" type="text"  /></td>
                <td></td>
            </tr>
            <tr>
                <td class="label right">有效日期:</td>
                <td>
                    <table border="0" class="many" cellpadding="0" cellspacing="0">
                        <tr>
                            <td><input type="radio" disabled="disabled" class="hasDateClass" value="0" checked="checked" name="deadlineType" ></td>
                            <td>截止日期</td>
                            <td><input type="radio" disabled="disabled" class="hasDateClass" value="1" name="deadlineType"></td>
                            <td>使用周期</td>
                        </tr>
                    </table>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr id="update_in_datetime">
                <td class="label right">&nbsp;</td>
                <td><input class="easyui-datetimebox" id="updateEndDateValue" data-options="disabled:true" name="endDateValueStr"  /></td>
                <td>&nbsp;</td>
            </tr>
            <tr style="display:none;" id="update_in_day">
                <td class="label right">&nbsp;</td>
                <td>
                   <table class="many" border="0" cellpadding="0" cellspacing="0">
                       <tr>
                           <td><input name="endDayValue" id="updateEndDayValue" disabled="disabled" type="text" /></td>
                           <td>天</td>
                       </tr>
                   </table>
                </td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="center" colspan="3">
                <a id="btn" href="#" onclick="updateVolumeSave()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="updateVolumeClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
</div>
</shiro:hasPermission>
<shiro:lacksPermission name="sys:operationalConfig:volume:view">
			<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>