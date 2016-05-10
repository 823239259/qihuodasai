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
<title>编辑合作伙伴</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:22%;">
		<input type="hidden" id="dataID" name="id"  value="${config.id}">
		<input type="hidden" id="type" name="type"  value="5">
		<input type="hidden" id="imgPath" name="imgPath"   value="${config.imgPath}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>伙伴名称:</td>
							<td>
									<input class="easyui-validatebox" value="${config.name}" id="name" name="name" data-options="required:true,validType:['length[2,20]']">
							</td>
						</tr>
						<tr>
								<td>伙伴LOGO图:</td>
								<td>
									 <input type="file" name="files[]" id="fileupload_input" />
								</td>
						</tr>
						<tr>
								<td>链接地址:</td>
								<td>
									<input class="easyui-validatebox"  id="linkUrl" type="text" value="${config.linkUrl}"   name="linkUrl" data-options="required:true">
								</td>
						</tr>
						<tr>
							<td>启用状态:</td>
							<td>
							<c:choose>
								<c:when test="${config.isEnable==false}">
										<input type="radio" name="isEnable" value="true"><span>启用</span></input>
										<input type="radio" name="isEnable" value="false" checked="checked"><span>禁用</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isEnable" value="true" checked="checked"><span>启用</span></input>
									<input type="radio" name="isEnable" value="false"><span>禁用</span></input>
								</c:otherwise>
							</c:choose>
							</td>	
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="submitInfo(1)">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="submitInfo(2)">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
<script type="text/javascript">
$("#fileupload_input").fileupload({
	url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/partners",fileType:"img",limitSize:5},//如果需要额外添加参数可以在这里添加
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
    	$("#imgPath").val(url);
    	eyWindow.walert("提示","上传成功","info");
    }
});


function submitInfo(type){
	var imgPath = $("#imgPath").val();
	if (validateIsNull(imgPath)){
		eyWindow.walert("提示","请先上传合作伙伴LOGO图片...","info");
		return;
	}
	if (type==1){
		baseUtils.saveOrUpdate('admin/config/partners/create','iframe')
	}
	
	if (type==2){
		baseUtils.saveOrUpdate('admin/config/partners/update','iframe')
	}
}
</script>
</html>