<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>国际原油方案参数设置</title>
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
		$("#tranLever").val("");
		$("#tranFees").val("");
		$("#feeManage").val("");
		$("#traderBond").val("");
		$("#traderMoney").val("");
		$("#lineLoss").val("");
		$("#goldenMoney").val("");
		$("#passWin").show();
		$("#passWin").window('open');
	} else if(type==2){
		var rows = $("#edatagrid").datagrid('getSelections');
		if (Check.validateSelectItems($("#edatagrid"),1)) {
			$("#tranLever").val(rows[0].tranLever);
			$("#tranFees").val(rows[0].tranFees);
			$("#feeManage").val(rows[0].feeManage);
			$("#traderBond").val(rows[0].traderBond);
			$("#traderMoney").val(rows[0].traderMoney);
			$("#lineLoss").val(rows[0].lineLoss);
			$("#goldenMoney").val(rows[0].goldenMoney);
			$("#passWin").show();
			$("#passWin").window('open');
		}
	}

};
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if ($("#passWin").form("validate")) {
		var tranLever = $("#tranLever").val();
		var tranFees = $("#tranFees").val();
		var feeManage = $("#feeManage").val();
		var traderBond = $("#traderBond").val();
		var traderMoney = $("#traderMoney").val();
		var lineLoss = $("#lineLoss").val();
		var goldenMoney = $("#goldenMoney").val();
		var parameters = '{}';
		if(setParameterType==1){
			parameters = {"tranLever":tranLever,"tranFees":tranFees,"feeManage":feeManage,"traderBond":traderBond,"traderMoney":traderMoney,"lineLoss":lineLoss,"goldenMoney":goldenMoney};
		} else if(setParameterType==2){
			parameters = {"id":rows[0].id,"tranLever":tranLever,"tranFees":tranFees,"feeManage":feeManage,"traderBond":traderBond,"traderMoney":traderMoney,"lineLoss":lineLoss,"goldenMoney":goldenMoney};
		}
		$.post(Check.rootPath() + "/admin/crudeSetParameter/saveOrUpdateConfig",
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
				$.post(Check.rootPath() + "/admin/crudeSetParameter/deleteConfig" 
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
	<shiro:hasPermission name="sys:settingParams:crudeSetParameter:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="国际原油参数设置" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:settingParams:crudeSetParameter:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">新增</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:settingParams:crudeSetParameter:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:settingParams:crudeSetParameter:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/crudeSetParameter/getDatas"
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
							<th field="typeName" width="150" sortable="true">交易品种</th>
							<th field="tranLever" width="150">开仓手数</th>
							<th field="tranFees" width="150">手续费</th>
							<th field="feeManage" width="150">管理费</th>
							<th field="traderBond" width="160">操盘保证金(人民币)</th>
							<th field="traderMoney" width="160">总操盘金(美元)</th>
							<th field="lineLoss" width="160">亏损平仓线(美元)</th>
							<th field="goldenMoney" width="160">入金金额(美元)</th>
							<th field="updateTime" width="120">修改时间</th>
							<th field="updateUser" width="120">操作人</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	<div id="passWin" class="easyui-window" title="编辑" 
		style="width:450px;height:350px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">开仓手数:</td>
                <td>
                   <input id="tranLever" name="tranLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">手续费:</td>
                <td>
                   <input id="tranFees" name="tranFees" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>  
                <td class="label right">管理费:</td>
                <td>
                   <input id="feeManage" name="feeManage" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">操盘保证金(￥):</td>
                <td>
                   <input id="traderBond" name="traderBond" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">总操盘金($):</td>
                <td>
                   <input id="traderMoney" name="traderMoney" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">亏损平仓线($):</td>
                <td>
                   <input id="lineLoss" name="lineLoss" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">入金总金额($):</td>
                <td>
                   <input id="goldenMoney" name="goldenMoney" class="easyui-validatebox"  data-options="required:true"/>
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
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:settingParams:crudeSetParameter:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>