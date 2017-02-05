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
<title>填写恒生账户信息</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript">
$(document).ready(function(){
	$('#parentAccountId').combobox({
        url: basepath + 'admin/parentAccount/getComboboxData',
        editable: false,
        required:true,
        panelHeight:100,
        valueField: 'id',
        textField: 'accountName',
        onSelect: function (record) {
            var accountGenre = record.accountGenre;
            $('#accountGenre').val(accountGenre);
            
            if(accountGenre==0){
            	$('#accountType').val("钱江版");
            	$("#assetId").attr("class","easyui-validatebox");
            	$("#assetId").attr("data-options","required:true");
            }else if(accountGenre==1){
            	$('#accountType').val("钱隆TTS");
            	$("#assetId").removeAttr("class");
            	$("#assetId").removeAttr("data-options");
            }else if(accountGenre==2){
            	$("#assetId").removeAttr("class");
            	$("#assetId").removeAttr("data-options");
            	$('#accountType').val("同花顺");
            }
        }
    });
});
</script>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 20%;">
		<input type="hidden" name="tradeId" value="${tradeId}">
		<input type="hidden" name="activityType" value="${activityType}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>母账户名称:</td>
							<td>
								<input class="easyui-combobox" id="parentAccountId" name="parentAccountId"
                               		data-options="valueField:'id', textField:'accountName', panelHeight:'auto'" >
							</td>
						</tr>
						<tr>
							<td>交易通道类型:</td>
							<td>
								<input type="hidden" id="accountGenre"  name="accountGenre"/>
								<input class="easyui-validatebox"  id="accountType"  name="accountType" readonly="readonly" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td>单元序号:</td>
							<td><input id="assetId"  name="assetId"></td>
						</tr>
						<tr>
							<td>交易账户名:</td>
							<td><input class="easyui-validatebox"  id="accountName"  name="accountName" data-options="required:true"></td>
						</tr>
						<tr>
							<td>交易帐号:</td>
							<td><input class="easyui-validatebox"  id="account"  name="account" data-options="required:true"></td>
						</tr>
				
						<tr>
							<td>交易密码:</td>
							<td><input class="easyui-validatebox"  id="password"  name="password" data-options="required:true"></td>
						</tr>
						
						<tr>
							<td>保险单号:</td>
							<td><input class="easyui-validatebox" value="" id="insuranceNo"  name="insuranceNo" data-options=""></td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/wellGold/auditPass','iframe')">保存</a>
							</td>
						</tr>
					</table>
		</form>
</body>
</html>