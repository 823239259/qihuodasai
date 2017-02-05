<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>组织机构新增修改</title>
	</head>
	<body>
	<!-- add organization window -->
		<form method="post" id="orgForm" style="padding-left: 22%;">
					<table class="formTable" >
						<tr>
							<td>名称:</td>
							<td><input class="easyui-validatebox" value="${organization.name}" id="name"  name="name" data-options="required:true,missingMessage:'不能为空',validType:['length[3,20]','CHS'],invalidMessage:'请输入3-20个字符，并且全是中文'"></td>
						</tr>
						<tr>
							<td>是否显示:</td>
							<td>
							<c:choose>
								<c:when test="${organization.show==false}">
										<input type="radio" name="show" value="true"><span>是</span></input>
										<input type="radio" name="show" value="false" checked="checked"><span>否</span></input>
								</c:when>
								<c:otherwise>
									<input type="radio" name="show" value="true" checked="checked"><span>是</span></input>
									<input type="radio" name="show" value="false"><span>否</span></input>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<tr>
								<td>
									权重
								</td>
								<td>
									<input class="easyui-validatebox" value="${organization.weight}" id="weight"  name="weight" data-options="required:true,validType:['length[1,100]','number']">
								</td>
						</tr>
						
						<tr>
								<td colspan="2" align="center">
									<c:choose>
										<c:when test="${fromType=='add'}">
											<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="organization.saveOrUpdate('${parentID}','${organization.id}','admin/org/create')">保存</a>
										</c:when>
										<c:otherwise>
											<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="organization.saveOrUpdate('${organization.id}','${organization.id}','admin/org/update')">修改</a>
										</c:otherwise>
									</c:choose>
								</td>
						</tr>
					</table>
		</form>
	</body>
</html>