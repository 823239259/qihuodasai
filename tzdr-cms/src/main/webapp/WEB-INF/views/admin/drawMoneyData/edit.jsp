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
<title>补录充值表单</title>
<script src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type="text/javascript">
	function saveInfo(type){
		var id=$("#id").val();
		var type=$("#type").val();
		if(type=="1"){
			$("#firstAuditId").val("");
			$("#reAuditId").val("");
		}
		if ($("#addForm").form('validate') == false) {
			return;
		}
		if (id){
				baseUtils.saveOrUpdate('admin/drawMoneyData/update', 'iframe');
			}else{
				baseUtils.saveOrUpdate('admin/drawMoneyData/create', 'iframe');	
			
			 }
		}
	
	function setData(){
		var type=$("#type").val();
		if(type=="2"||type=="3"){
			$("#firstAuditTr").show();
			$("#reAuditTr").show();
			
		}else{
			$("#reAuditTr").hide();
			$("#firstAuditTr").hide();
		}
	}
	
	$(document).ready(function(){
		setData();
	});
	
</script>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:10%;">
		<input type="hidden" id="id" name="id" value="${entity.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable" >
						<tr>
							<td width="13%">审核方式:</td>
							<td width="35%">
								<select name="type" id="type" style="width:150px" onchange="setData();">
				                 	<c:forEach items="${datamap}" var="item">
				                 		 <option value="${item.valueKey }" ${item.valueKey eq entity.type ? 'selected' : ''}>${item.valueName}</option>
									</c:forEach>
				                 </select>
							</td>
						</tr>
						
						<tr>
							<td>提现审核金额:</td>
							<td>
								<input  id="minmoney" style="width:150px" class="easyui-numberbox" name="minmoney" data-options="required:true,validType:['money']" value="${entity.minmoney }">
								-
								<input  id="maxmoney" style="width:150px" class="easyui-numberbox" name="maxmoney" data-options="required:true,validType:['money']" value="${entity.maxmoney }">
								(元)
							</td>
						</tr>
						
							<tr id="firstAuditTr">
								<td width="15%">初审人:</td>
								<td>
									<select name="firstAuditId" id="firstAuditId" style="width:150px">
					                 	 <option value=" " ></option>
					                 	<c:forEach items="${list}" var="item">
					                 	 <option value="${item.id}" ${item.id eq entity.firstAuditId ? 'selected' : ''}>${item.realname}</option>
										</c:forEach>
					                 </select>
								</td>
							</tr>
							<tr id="reAuditTr">
								<td>复审人:</td>
								<td>
									<select name="reAuditId" id="reAuditId" style="width:150px">
					                 	 <option value=" " ></option>
					                 	<c:forEach items="${list}" var="item">
					                 	 	<option value="${item.id }" ${item.id eq entity.reAuditId ? 'selected' : ''}>${item.realname}</option>
										</c:forEach>
					                 </select>
								</td>
							</tr>
						
						<tr >
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="saveInfo(1)">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="saveInfo(2)">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>