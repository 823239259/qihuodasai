<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<%@include file="../../common/import-easyui-js.jspf"%>
<title>爬虫网络参数设置</title>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
</head>
<body>
		<div style="margin-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="save()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"   onclick="update()" >修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deletes()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="start()">执行</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="stop()">停止</a>
		</div>
		<table id="dg"  class="easyui-datagrid"  width="100%" style="height:auto;" 
			url="${ctx }/admin/crawler/url/listData"
			toolbar="#toolbar"  pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="id" data-options="checkbox:true"></th>
					<th field="urlTitle" width="50" >网站标题</th>
					<th field="urlUrl" width="50" >网站路劲</th>
					<th field="status" width="50">执行状态</th>
					<th field="execRule" width="50" >执行规则</th>
					<th field="urlMethod" width="50">请求方式</th>
					<!-- <th field="urlCreatetime" width="50" >创建时间</th> -->
					<th field="urlRemarks" width="50" >备注</th>
				</tr>
			</thead>
		</table>
		
		<div id="add" class="easyui-dialog" title="新增连接" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:300px;padding:10px;">
				<input type = "hidden" id = "urlId" value="" name = "urlId"/>
			<table>
				<tr><td><b>网站标题:</b><input type="text" value = "" name = "urlTitle" id = "urlTitle"/></td></tr>
				<tr><td><b>网站路劲:</b><input type="text" value = "" name = "urlUrl" id = "urlUrl" style = "width: 300px;"/></td></tr>
				<tr><td><b>参数设置:</b><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addParam('','')">新增</a></td></tr>
				<tr><td>
						<div id = "param">
						</div>
					</td>
				</tr>
				<tr>
					<td><b>执行规则:</b><input type="text" value = "" name = "execRule" id = "execRule"/></td>
				</tr>
				<tr>
					<td><b>请求数据类型:</b>
							<select id = "type">
								<option value = "0">
										实时
								</option>
								<option value = "1">
										日历
								</option>
							</select>
						</td>
				</tr>
				<tr><td><b>请求方式:</b>
						<select id = "urlMethod">
							<option value = "GET">
									GET
							</option>
							<option value = "POST">
									POST
							</option>
						</select>
					</td></tr>
					<tr><td><b>备注:</b>
						<textarea id = "urlRemarks" name = "urlRemarks" rows="6" cols="100"></textarea>
					</td></tr>
					<tr>
						<td><a href="#" id= "save" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a></td>
					</tr>
			</table>
		</div>
</body>
	<script type="text/javascript">
		function save(){
			clearData();
			$('#add').dialog('open');
		}
		function deletes(){
			var rows = $("#dg").datagrid("getSelections");
			if (Check.validateSelectItems($("#dg"),1)) {
				$.messager.confirm('提示', '你确定要删除该数据吗吗?', function(r){
				if(r){
						$.ajax({
							url:"${ctx }/admin/crawler/url/doDeleteByUrlId",
							type:"post",
							data:{
								urlId:rows[0].id
							},
							success:function(result){
								$('#dg').datagrid('reload');
							}
						});
					}
				});
			}
		}
		var paramIndex = 0;
		function addParam(key ,value){
			var html = '<div id = "paramIndex'+paramIndex+'"> key:<input type="text" value = "'+key+'" name = "urlParamkey" id = "urlParamKey'+paramIndex+'"/>'+
					   ' value:<input type="text" value = "'+value+'" name = "urlParamvalue" id = "urlParamValue'+paramIndex+'"/> <a href="javascript:void(0)" id = "paramIndex-remove'+paramIndex+'" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a> </div>';
			$("#param").append(html);
			$("#paramIndex-remove"+paramIndex+"").bind("click",function(){
				$(this).parent().remove();
			});
			paramIndex++;
		}
		function update(){
			clearData();
			var rows = $("#dg").datagrid("getSelections");
			if (Check.validateSelectItems($("#dg"),1)) {
				$.ajax({
					url:"${ctx }/admin/crawler/url/doGetDataById",
					type:"get",
					data:{
						id:rows[0].id
					},
					success:function(result){
						var _data = result.data;
						var data = _data.data;
						var param = _data.param;
						$("#urlTitle").val(data.urlTitle);
						$("#urlId").val(data.id);
						$("#urlUrl").val(data.urlUrl);
						$("#execRule").val(data.execRule);
						$("#urlMethod").val(data.urlMethod);
						$("#urlRemarks").val(data.urlRemarks);
						$("#type").val(data.type);
						var paramLength = param.length;
						console.log(paramLength);
						for(var i = 0 ; i < paramLength ; i++){
							var dataParam = param[i];
							addParam(dataParam.urlParamKey,dataParam.urlParamValue);
						}
					}
				});
				$('#add').dialog('open');
			}
		}
		function start(){
			var rows = $("#dg").datagrid("getSelections");
			if (Check.validateSelectItems($("#dg"),1)) {
				$.messager.confirm('提示', '你确定要启动此任务吗?', function(r){
					if (r){
						var win = $.messager.progress({
							title:'启动进度',
							msg:'正在启动...'
						});
						$.ajax({
							url:"${ctx }/admin/crawler/url/startCrawler",
							type:"POST",
							data:{
								id:rows[0].id
							},
							success:function(result){
								$.messager.progress('close');
								if(result.success){
									 $.messager.alert('提示','任务已执行','info');
									 $('#dg').datagrid('reload');
								}else{
									$.messager.alert('提示',''+result.message+'','error');
								}
							}
						});
					}
				});
			}
		}
		function stop(){
			var rows = $("#dg").datagrid("getSelections");
			if (Check.validateSelectItems($("#dg"),1)) {
				$.messager.confirm('提示', '你确定要停止此任务吗?', function(r){
					if (r){
						var win = $.messager.progress({
							title:'停止进度',
							msg:'正在停止...'
						});
						$.ajax({
							url:"${ctx }/admin/crawler/url/stopCrawler",
							type:"POST",
							data:{
								id:rows[0].id
							},
							success:function(result){
								$.messager.progress('close');
								if(result.success){
									 $.messager.alert('提示','任务已停止','info');
									 $('#dg').datagrid('reload');
								}else{
									$.messager.alert('提示',''+result.message+'','error');
								}
							}
						});
					}
				});
			}
		}
		$("#save").click(function(){
			var urlTitle = $("#urlTitle").val();
			var urlUrl = $("#urlUrl").val();
			var urlRemarks = $("#urlRemarks").val();
			var execRule = $("#execRule").val();
			var urlMethod = $("#urlMethod").val();
			var id = $("#urlId").val();
			var type = $("#type").val();
			var urlKey = $("input[name = 'urlParamkey']");
			var urlValue = $("input[name = 'urlParamvalue']");
			var keyArray = new Array();
			var valueArray = new Array();
			$.each(urlKey,function(i,item){
				keyArray[i] = $(this).val();
			});
			$.each(urlValue,function(i,item){
				valueArray[i] = $(this).val();
			});
			var keys = keyArray.join(",");
			var values = valueArray.join(",");
			$.ajax({
				url:"${ctx}/admin/crawler/url/save",
				type:"post",
				data:{
					id:id,
					urlTitle:urlTitle,
					urlUrl:urlUrl,
					urlMethod:urlMethod,
					execRule:execRule,
					key:keys,
					value:values,
					urlRemarks:urlRemarks,
					type:type
				},success:function(result){
					$('#add').dialog('close');
					 $('#dg').datagrid('reload');
				}
			});
		});
		function clearData(){
			 $("#urlId").val("");
			 $("#urlTitle").val("");
			 $("#urlUrl").val("");
			 $("#urlRemarks").val("");
			 $("#execRule").val("");
			 $("#urlMethod").val("");
			 $("#param").html("");
		}
	</script>
</html>