<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<title>socket版本路劲列表</title>
</head>
<body>
		<div style="margin-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="save()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"   onclick="update()" >修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletes()">删除</a>
		</div>
		<table id = "dg" class="easyui-datagrid" title="数据列表"
			data-options="singleSelect:true,collapsible:true,url:'${ctx }/admin/socket/config/listData'"
			pagination = "true"
			rownumbers="true" 
			fitColumns="true" 
			singleSelect="true"
			>
		<thead>
			<tr>
				<th data-options="field:'id',width:80,checkbox:true"></th>
				<th data-options="field:'appVersion',width:100">app版本号</th>
				<th data-options="field:'socketUrl',width:100,align:'right'">socket地址</th>
				<th data-options="field:'socketVersion',width:100,align:'right'">socket版本号</th>
				<th data-options="field:'isModel',width:80,formatter:ismodel">模式</th>
			</tr>
		</thead>
	</table>
	<div id = "add"  class="easyui-dialog"  title="新增socket配置"   data-options="modal:true,closed:true,iconCls:'icon-save'"  style="width:500px;height:300px;padding:10px;">
		<table>
			<tr style = "display:none;">
				<td>id:</td>
				<td><input class="easyui-textbox" name = "socketId" data-options="prompt:'请输入APP版本号'" style="width:200px;height:32px"></td>
			</tr>
			<tr>
				<td>app版本号:</td>
				<td><input class="easyui-textbox" name = "appVersion" data-options="prompt:'请输入APP版本号'" style="width:200px;height:32px"></td>
			</tr>
			<tr>
				<td>socket地址:</td>
				<td><input class="easyui-textbox" name = "socketUrl" style="width:200px;height:32px"></td>
			</tr>
			<tr>
				<td>socket版本号:</td>
				<td><input class="easyui-textbox" name = "socketVersion" style="width:200px;height:32px"></td>
			</tr>
			<tr>
				<td>模式:</td>
				<td><select class="easyui-combobox" name="isModel" style="width:200px;">
				<option value="0">开发</option>
				<option value="1">生产</option>
			</select></td>
			</tr>
		</table>
		<div>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id = "save" style="width:80px;height:30px;margin-left:30%;;margin-top:30px;">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok" id = "update" style="width:80px;height:30px;margin-left:30%;;margin-top:30px;">修改</a>
		</div>
	</div>
</body>
<script type="text/javascript">
	function ismodel(value, row, index){
	    if (value == "0") {
	        return '开发';
	    }
	    else if(value = "1"){
	        return "生产";
	    }
	}
	function save(){
		clearData();
		$("#update").css("display","none");
		$("#save").css("display","block");
		$('#add').dialog('open');		
	}
	$("#save").click(function(){
		var appversion = $("input[name = 'appVersion']").val();
		var socketUrl = $("input[name = 'socketUrl']").val();
		var socketVersion = $("input[name = 'socketVersion']").val();
		var isModel = $("input[name = 'isModel']").val();
		$.ajax({
			url:"${ctx}/admin/socket/config/save",
			type:"post",
			data:{
				appVersion:appversion,
				socketUrl:socketUrl,
				socketVersion:socketVersion,
				isModel:isModel
			},
			success:function(result){
				if(result.success){
					$('#add').dialog('close');
					 $('#dg').datagrid('reload');
				}
			}
		})
	});
	$("#update").click(function(){
		var appversion = $("input[name = 'appVersion']").val();
		var socketUrl = $("input[name = 'socketUrl']").val();
		var socketVersion = $("input[name = 'socketVersion']").val();
		var isModel = $("input[name = 'isModel']").val();
		var id = $("input[name = 'socketId']").val();
		$.ajax({
			url:"${ctx}/admin/socket/config/socketUpdate",
			type:"post",
			data:{
				appVersion:appversion,
				socketUrl:socketUrl,
				socketVersion:socketVersion,
				isModel:isModel,
				id:id
			},
			success:function(result){
				if(result.success){
					$('#add').dialog('close');
					 $('#dg').datagrid('reload');
				}
			}
		})
	});
	function update(){
		$("#save").css("display","none");
		$("#update").css("display","block");
		clearData();
		var rows = $("#dg").datagrid("getSelections");
		if (Check.validateSelectItems($("#dg"),1)) {
			$.ajax({
				url:"${ctx }/admin/socket/config/doGetById",
				type:"post",
				data:{
					id:rows[0].id
				},
				success:function(result){
					var _data = result.data;
					var data = _data.data;
					var param = _data.param;
					$("input[name = 'socketId']").val(data.id);
					$("input[name = 'appVersion']").val(data.appVersion);
					$("input[name = 'socketUrl']").val(data.socketUrl);
					$("input[name = 'socketVersion']").val(data.socketVersion);
					$("input[name = 'isModel']").val(data.isModel);
				}
			});
			$('#add').dialog('open');
		}
	}
	function deletes(){
		var rows = $("#dg").datagrid("getSelections");
		if (Check.validateSelectItems($("#dg"),1)) {
			$.messager.confirm('提示', '你确定要删除该数据吗?', function(r){
				if(r){
					$.ajax({
						url:"${ctx }/admin/socket/config/socketDelete",
						type:"post",
						data:{
							id:rows[0].id
						},
						success:function(result){
							$.messager.alert('提示','删除成功','info');
							 $('#dg').datagrid('reload');
						}
					});
				}
			});
		}
	}
	function clearData(){
		$("input[name = 'appVersion']").val('');
		$("input[name = 'socketUrl']").val('');
		$("input[name = 'socketVersion']").val('');
	}
</script>
</html>