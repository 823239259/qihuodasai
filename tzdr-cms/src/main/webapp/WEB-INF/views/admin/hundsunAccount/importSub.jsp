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
<%@include file="../../common/import-easyui-js.jspf"%>
<%@include file="../../common/import-fileupload-js.jspf"%>
<title>导入数据</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:18%;">
		<input type="hidden" id="fileUrl" name="fileUrl"/>
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>母账户名称:</td>
							<td>
									<input class="easyui-combobox" id="parentAccount"  name="parentAccountID" data-options="
										url:'${ctx}/admin/parentAccount/getComboboxData',
										valueField:'id',
										panelHeight:100,
										required:true,
										textField:'accountName'">
							</td>
						</tr>
						<tr>
								<td>选择上传excel文件:</td>
								<td>
									 <input type="file" name="files[]" id="fileupload_input" />
								</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
									<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="submitInfo()">保存</a>									
								</td>
						</tr>
					</table>
		</form>
</body>
<script type="text/javascript">
$("#fileupload_input").fileupload({
	url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/subAccount",fileType:"excel",limitSize:10},//如果需要额外添加参数可以在这里添加
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


function submitInfo(){
	var fileUrl = $("#fileUrl").val();
	if (validateIsNull(fileUrl)){
		eyWindow.walert("提示","请先上传excel文件...","info");
		return;
	}
	baseUtils.saveOrUpdate('admin/subAccount/saveImport','iframe');
	
}
</script>
</html>