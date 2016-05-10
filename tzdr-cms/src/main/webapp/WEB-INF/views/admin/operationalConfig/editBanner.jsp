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
<title>编辑Banner</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:22%;">
		<input type="hidden" id="dataID" name="id"  value="${config.id}">
		<input type="hidden" id="type" name="type"  value="${type}">
		<input type="hidden" id="imgPath" name="imgPath"   value="${config.imgPath}">
		<input type="hidden" id="content" name="content"   value="${config.content}"><!-- 背景图存放地址 -->
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>名称:</td>
							<td>
									<input class="easyui-validatebox" value="${config.name}" id="name" name="name" data-options="required:true,validType:['length[2,20]']">
							</td>
						</tr>
						<tr>
								<td>Banner图:</td>
								<td>
									 <input type="file" name="files[]" id="fileupload_input" />
								</td>
						</tr>
						<!-- <tr>
								<td>Banner背景图:</td>
								<td>
									 <input type="file" name="files[]" id="fileupload_bg" />
								</td>
						</tr> -->
						<tr>
							<c:choose>
								<c:when test="${type==9}">
									<td>新闻id:</td>
									<td>
										<input class="easyui-validatebox"  id="linkId" type="text" value="${config.linkUrl}"   name="linkUrl" />
									</td>
								</c:when>
								<c:otherwise>
									<td>新闻链接:</td>
									<td>
										<input class="easyui-validatebox"  id="linkUrl" type="text" value="${config.linkUrl}"   name="linkUrl" />
									</td>
								</c:otherwise>
							</c:choose>
						</tr>

						<tr>
								<td>排序权重:</td>
								<td>
									<input class="easyui-validatebox"  id="valueType" type="text" value="${config.valueType}"   name="valueType" />
								</td>
						</tr>
						<%-- <tr>
							<td>Banner类型:</td>
							<td>
							<c:choose>
								<c:when test="${config.valueType==2}">
										<input type="radio" name="valueType" value="1"><span>小图</span></input>
										<input type="radio" name="valueType" value="2" checked="checked"><span>大图</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="valueType" value="1" checked="checked"><span>小图</span></input>
									<input type="radio" name="valueType" value="2"><span>大图</span></input>
								</c:otherwise>
							</c:choose>
							</td>	
						</tr> --%>
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
    formData:{dir:"upload/banner",fileType:"img",limitSize:5},//如果需要额外添加参数可以在这里添加
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

$("#fileupload_bg").fileupload({
	url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/banner.bg",fileType:"img",limitSize:5},//如果需要额外添加参数可以在这里添加
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
    	$("#content").val(url);
    	eyWindow.walert("提示","上传成功","info");
    }
});


function submitInfo(type){
	var imgPath = $("#imgPath").val();
	if (validateIsNull(imgPath)){
		eyWindow.walert("提示","请先上传Banner图片...","info");
		return;
	}
	/* var content = $("#content").val();
	if (validateIsNull(content)){
		eyWindow.walert("提示","请先上传背景图片...","info");
		return;
	} */
	
	if (type==1){
		saveOrUpdate('admin/config/banner/create','iframe',$("#type").val())
	}
	
	if (type==2){
		saveOrUpdate('admin/config/banner/update','iframe',$("#type").val())
	}
}


function saveOrUpdate(url,iframe,type){
	if ($("#addForm").form('validate') == false) {
		return;
	}
	//alert($("#addForm").serialize());
	ajaxPost({
		url : basepath + url,
		cache : false,
		async : false,
		data : $("#addForm").serialize(),
		success : function(data) {
			if (data.success) {
				$('#addWin').window('close');
				if(type=='8'){
					$("#edatagrid_pgb").datagrid('reload');
				}else if(type=='9'){
					parent.$('#edatagrid_tzdrApp').datagrid('reload');
				}else{
					$("#edatagrid").datagrid('reload');
				}
				
				if (iframe){
					parent.$('#addWin').window('close');
					if(type=='8'){
						parent.$('#edatagrid_pgb').datagrid('reload');
					}else if(type=='9'){
						parent.$('#edatagrid_tzdrApp').datagrid('reload');
					} else{
						parent.$('#edatagrid').datagrid('reload');
					}
					parent.eyWindow.walert("成功提示", data.message, 'info');
					return;
				}
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