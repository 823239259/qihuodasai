<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>支付银行管理</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
var setParameterType = 0;
function pass(type) {
	setParameterType = type;
	if(type==1){
		$("#bankName").val("");
		$("#abbreviation").val("");
		$("#bbpayCode").val("");
		$("#bbpayContactNumber").val("");
		$("#weight").val("");
		$("#iconPath").val("");
		
	} else if(type==2){
		var rows = $("#edatagrid").datagrid('getSelections');
		if (Check.validateSelectItems($("#edatagrid"),1)) {
			$("#bankName").val(rows[0].bankName);
			$("#abbreviation").val(rows[0].abbreviation);
			$("#bbpayCode").val(rows[0].bbpayCode);
			$("#bbpayContactNumber").val(rows[0].bbpayContactNumber);
			$("#weight").val(rows[0].weight);
			$("#iconPath").val(rows[0].iconPath);
		}
	}
	$("#passWin").show();
	$("#passWin").window('open');
};
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if ($("#passWin").form("validate")) {
		var bankName = $("#bankName").val();
		var abbreviation = $("#abbreviation").val();
		var bbpayCode = $("#bbpayCode").val();
		var bbpayContactNumber = $("#bbpayContactNumber").val();
		var weight = $("#weight").val();
		var iconPath=$("#iconPath").val();
		var parameters = '{}';
		if(setParameterType==1){
			parameters = {"bankName":bankName,"abbreviation":abbreviation,"bbpayCode":bbpayCode,"bbpayContactNumber":bbpayContactNumber,"weight":weight,"iconPath":iconPath};
			$.post(Check.rootPath() + "/admin/paySupportBank/create",
					parameters,
					function(data){
						if (data.success) {
							Check.messageBox("提示",data.message);
							$("#edatagrid").datagrid('reload');
							passClose() ;
						} else {
							Check.messageBox("提示",data.message,"error");
						}
			});
		} else if(setParameterType==2){
			parameters = {"id":rows[0].id,"bankName":bankName,"abbreviation":abbreviation,"bbpayCode":bbpayCode,"bbpayContactNumber":bbpayContactNumber,"weight":weight,"iconPath":iconPath};
			$.post(Check.rootPath() + "/admin/paySupportBank/update",
					parameters,
					function(data){
						if (data.success) {
							Check.messageBox("提示",data.message);
							$("#edatagrid").datagrid('reload');
							passClose() ;
						} else {
							Check.messageBox("提示",data.message,"error");
						}
			});
		}
		
	}
};

function passClose() {
	$("#passWin").show();
	$("#passWin").window('close');
};
function removeRecord(){
	
	var rows = $("#edatagrid").datagrid('getSelections');
	if (Check.validateSelectItems($("#edatagrid"),1)) {
		$.messager.confirm("提示", "是否确认删除该配置方案?", function (r) {
			if (r) {
				$.post(Check.rootPath() + "/admin/paySupportBank/delete" 
						,{"id":rows[0].id} ,
						function(data){
							if (data.success) {
								Check.messageBox("提示",data.message);
								$("#edatagrid").datagrid('reload');
								passClose() ;
							} else {
								Check.messageBox("提示",data.message,"error");
							}
				});
			}
		});
	}
}
</script>
</head>
<body>
	<shiro:hasPermission name="sys:operationalConfig:paySupportBank:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="支付银行管理" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:operationalConfig:paySupportBank:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">新增</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:operationalConfig:paySupportBank:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:operationalConfig:paySupportBank:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/paySupportBank/easyuiPage"
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
							<th field="bankName" width="150" sortable="true">银行名称</th>
							<th field="abbreviation" width="150">银行简称</th>
							<th field="bbpayCode" width="150">币币支付银行代码</th>
							<th field="bbpayContactNumber" width="150">币币支付提现联行号</th>
							<th field="weight" width="160">排序</th>
							<th field="iconPath" width="160">图标地址</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	<!-- window 申请 分配帐号弹框-->
	<div id="passWin" class="easyui-window" title="编辑" 
		style="width:450px;height:350px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">银行名称:</td>
                <td>
                   <input id="bankName" name="bankName" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">银行简称:</td>
                <td>
                   <input id="abbreviation" name="abbreviation" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>  
                <td class="label right">币币支付银行代码:</td>
                <td>
                   <input id="bbpayCode" name="bbpayCode" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">币币支付提现联行号:</td>
                <td>
                   <input id="bbpayContactNumber" name="bbpayContactNumber" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">排序:</td>
                <td>
                   <input id="weight" name="weight" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
           <tr>
                <td class="label right">图标地址:</td>
                <td>
                   <input id="iconPath" name="iconPath" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
             <!-- 
            <tr>
                <td class="label right">入金总金额($):</td>
                <td>
                   <input id="goldenMoney" name="goldenMoney" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr> -->
            <tr>
                <td align="center" colspan="3">
	               <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
	               <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:operationalConfig:paySupportBank:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>