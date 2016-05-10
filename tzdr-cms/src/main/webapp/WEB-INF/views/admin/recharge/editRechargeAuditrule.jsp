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
<script type="text/javascript">
	$(document).ready(function(){
	    $('#userTree').combotree({
	        url:basepath+'admin/org/getUserTreeData?parentId=0',
	        valueField:'id',
	        textField:'text',
	        multiple:false,
	        cascadeCheck:false,
	        panelHeight:100,
	        width:250,
	        required:true,
	        onlyLeafCheck:true,
	        onBeforeExpand:function(node){   
	        	$('#userTree').combotree('tree').tree('options').url=basepath+"admin/org/getUserTreeData?parentId="+node.id;
	        },
	        onBeforeSelect: function(node) {
				// 判断是否是叶子节点
				var isLeaf = $(this).tree('isLeaf', node.target);
				if (!isLeaf) {
					eyWindow.walert("提示",'请选择具体的审核人员！', 'info');
					// 返回false表示取消本次选择操作
					return false;
				}
			}
	    }); 
	});
	
	function saveInfo(type){
		var beginMoney = $("#beginMoney").val();
		var endMoney = $("#endMoney").val();

		if (Number(beginMoney) >= Number(endMoney)){
			eyWindow.walert("提示",'审核金额开始金额必须小于结束金额！', 'error');
			return;
		}
		
		if (type==1){
			baseUtils.saveOrUpdate('admin/rechargeAuditRule/create', 'iframe')
		}
		else
		{
			baseUtils.saveOrUpdate('admin/rechargeAuditRule/update', 'iframe')
		}
	}
</script>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:10%;">
		<input type="hidden" name="id" value="${auditRule.id}">
		<input type="hidden" name="auditorName" value="${auditRule.auditorName}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>充值审核金额:</td>
							<td>
								<input class="easyui-validatebox" value="<fmt:formatNumber value="${auditRule.beginMoney}" pattern="##.##"/>" id="beginMoney"  name="beginMoney" data-options="required:true,validType:'money'">
								- <input class="easyui-validatebox" value="<fmt:formatNumber value="${auditRule.endMoney}" pattern="##.##"/>" id="endMoney"  name="endMoney" data-options="required:true,validType:'money'">
							</td>
						</tr>
						
						<tr>
							<td>审核人员:</td>
							<td>
								<input  id="userTree" name="auditorId" value="${auditRule.auditorName}">
							</td>
						</tr>
						<tr>
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