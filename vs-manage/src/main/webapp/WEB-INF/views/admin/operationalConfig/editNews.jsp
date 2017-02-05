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
<%@include file="../../common/import-kindeditor-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>

<title>编辑新闻</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:4%;">
		<input type="hidden" id="dataID" name="id"  value="${config.id}">
		<input type="hidden" id="type" name="type"  value="3">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>新闻标题:</td>
							<td>
									<input class="easyui-validatebox" value="${config.name}" id="name" name="name" data-options="required:true">
							</td>
							<td>摘要:</td>
							<td>
									<input class="easyui-validatebox" value="${config.summary}" id="summary" name="summary">
							</td>
						</tr>
						<tr>
							<td>关键字:</td>
							<td>
									<input class="easyui-validatebox" value="${config.keyWord}" id="keyWord" name="keyWord">
							</td>
								<td>新闻栏目:</td>
								<td>
									<input class="easyui-combobox" id="parentConfig" value="${config.parentConfig.id}" name="parentConfig.id" data-options="
										url:'${ctx}/admin/config/newsColumn/getComboboxData',
										valueField:'id', 
										panelHeight:100,
										required:true,
										editable:false,
										textField:'name'">
								</td>
						</tr>
						<tr>
							<td>是否置顶:</td>
							<td>
							<c:choose>
								<c:when test="${config.isTop==true}">
										<input type="radio" name="isTop" value="true" checked="checked" ><span>是</span></input>
										<input type="radio" name="isTop" value="false"><span>否</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="isTop" value="true"><span>是</span></input>
									<input type="radio" name="isTop" value="false"  checked="checked"><span>否</span></input>
								</c:otherwise>
							</c:choose>
							</td>	
							<td>
								发布时间
							</td>
							<td>
								<input id="defineReleaseTime" value="${config.defineReleaseTime}" name="defineReleaseTime" class="" type="text"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<textarea  style="height:200px;" id="newscontent" name="content">${config.content}</textarea>
							</td>
						</tr>
						<tr>
								<td colspan="4" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/config/news/create','iframe')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/config/news/update','iframe')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
<script type="text/javascript">
KindEditor.ready(function(K) {
	var options = {
			themeType: 'simple',
            uploadJson: basepath+'kindeditor/upload',
            allowFileManager: false,
            minWidth:580,
            items : [
						'source','preview','link','unlink','|','undo','redo','selectall','|','fontname', 'fontsize', '|', 'forecolor','wordpaste', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', '|','subscript','superscript','table'],
			      afterBlur: function(){this.sync();}
    };
    window.editor = K.create('#newscontent',options);
});
</script>
</html>