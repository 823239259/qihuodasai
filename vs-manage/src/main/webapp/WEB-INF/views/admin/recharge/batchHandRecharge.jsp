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
<%@include file="../../common/import-fileupload-js.jspf"%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
</head>
<body>
<shiro:hasPermission name="sys:finance:rechargeHandBatch:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
    <div title="手工批量现金充值" style="padding:1px;">
        <table id="dg000093"  class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar"
             url="${ctx}/admin/rechargeHandBatch/listData" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
           
        <thead>
            <tr>
                <th field="id" hidden="ture"></th>
                <th field="tname" width="150">用户姓名 </th>
				<th field="mobile" width="150">手机号 </th>
				<th field="typevalue" width="150">调账类型 </th>
				<th field="serialNumber" width="150">流水号 </th>
				<th field="money" width="150">金额 </th>
				<th field="reason" width="150">原因 </th>
				<th field="importTimeValue" width="150">导入时间</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	       <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	           
	            <tr>
	              	<td class="label right">调账类型:</td>
	                <td>
	                    <select class="easyui-combobox" name="search_EQ_type">
	                        <option value="">所有类型</option>
	                        <option value="3">系统调账</option>
	                        <option value="4">系统冲账</option>
	                    </select>
	                </td>
	                <td>
	               		 <a id="btn" href="#" onclick="$.easyui.datagridQuery('dg000093','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	        <div style="margin-bottom: 5px">
	        	<shiro:hasPermission name="sys:finance:rechargeHandBatch:delete">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="deleteData()">删除</a>
		      	 </shiro:hasPermission>
		      	 <shiro:hasPermission name="sys:finance:rechargeHandBatch:import">  
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="importExcelData()">导入</a>
		        </shiro:hasPermission>
		         <shiro:hasPermission name="sys:finance:rechargeHandBatch:correcteEntry">  
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="handleRecharge(1)">批量调帐</a>
		        </shiro:hasPermission>
		         <shiro:hasPermission name="sys:finance:rechargeHandBatch:reverseEntry">  
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="handleRecharge(2)">批量冲帐</a>
		    	</shiro:hasPermission>
		    	<a href="${ctx}/admin/rechargeHandBatch/downloadTemplate" class="easyui-linkbutton" iconCls="icon-ok">下载excel模版</a>
		    </div>
	    </div>
    </div>	
</div>
	<div id="handRechargeImportId" class="easyui-dialog" title="数据导入"
		style="width: 370px; height: 140px;"
		data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
		<form method="post" id="importForm">
			<input type="hidden" id="fileUrl" name="fileUrl"/>
			<table class="conn" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">上传文件:</td>
					<td colspan="2"> <input type="file" name="files[]" id="fileupload_input" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><a href="#" class="easyui-linkbutton"
						onclick="submitInfo()">上传</a> <input type="submit"
						id="fileUploadForm" style="display: none;"></td>
				</tr>
			</table>
			<iframe name="target_upload" style="display: none;"></iframe>
		</form>
	</div>
	
  </shiro:hasPermission>
   <shiro:lacksPermission name="sys:finance:rechargeHandBatch:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
<script type="text/javascript">
$("#fileupload_input").fileupload({
	url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/batchRecharges",fileType:"excel",limitSize:10},//如果需要额外添加参数可以在这里添加
    add:function(e,data){
    	eyWindow.wprogress("上传提示","正在上传....请稍等，谢谢！");
		data.submit();
	},
    done:function(e,result){
    	eyWindow.closeProgress();
    	result = result.result;
    	var errorCode = result.errorCode;
    	if (errorCode){
    		uploadUtils.showErrorMsg(errorCode);
    		return;
    	}
    	
    	var url = result.url;
      	$("#fileUrl").val(url);
    	eyWindow.walert("提示","上传成功","info");
    }
});

function importExcelData() {
	$('#handRechargeImportId').dialog('open');
}


function deleteData(){
	$.messager.confirm("确认提示","您确定要删除这些未处理的数据么？", function(result){
		if (result){
			ajaxPost({
				url : basepath + "admin/rechargeHandBatch/deleteData",
				cache : false,
				async : false,
				data : {},
				success : function(data) {
					if (data.success) {
						$("#dg000093").datagrid('reload');
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
				}
			});
		}
	});
}


/**
 * 系统调账或冲账
 */
function handleRecharge(systype){
	$.messager.confirm("确认提示","确认立即执行["+(systype==1?"调账":"冲账")+"]操作？", function(result){
		if (result){
			ajaxPost({
				url : basepath + "admin/rechargeHandBatch/handleRecharge",
				cache : false,
				async : false,
				data : {'sysType':systype},
				success : function(data) {
					if (data.success) {
						$("#dg000093").datagrid('reload');
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
				}
			});
		}
	});
}

function submitInfo(){
	var fileUrl = $("#fileUrl").val();
	if (validateIsNull(fileUrl)){
		eyWindow.walert("提示","请先上传excel文件...","info");
		return;
	}
	ajaxPost({
		url : basepath + "admin/rechargeHandBatch/saveImport",
		cache : false,
		async : false,
		data : $("#importForm").serialize(),
		success : function(data) {
			if (data.success) {
				$('#handRechargeImportId').window('close');
				$("#dg000093").datagrid('reload');
				eyWindow.walert("成功提示", data.message, 'info');
				return;
			}
			eyWindow.walert("错误提示", data.message, 'error');
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
		}
	});
}



</script>


</html>