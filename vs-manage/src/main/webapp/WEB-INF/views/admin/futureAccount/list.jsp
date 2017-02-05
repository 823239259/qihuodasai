<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>秒配账号</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%@include file="../../common/import-fileupload-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
  function changeTime(value, row, index) {
	if(value!=null){
		return getSmpFormatDateByLong(value*1000, true);
	}
	return "";
}
</script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:futureAccount:view">
		<div id="futureAccountTab" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="账号统计" data-options="tools:'#p-tools'" style="padding:20px;">
				<div style="padding-left:9px">
					<table style="font-size:12px;" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="reloadTheTable()">刷新</a>
							</td>
						</tr>
					</table> 
				</div>
				<div id="audittb" style="padding: 5px; height: auto">
					<table id="futureAccountCount" border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0"></table>	
				</div> 	
			</div>
	
			<div title="账号管理" id="proxy" style="padding:20px">
				<table id="dg003" class="easyui-datagrid"
			           toolbar="#dg003Toolbar" url="${ctx}/admin/futureMatch/getAccountDate"  pagination="true"
			           rownumbers="true" fitColumns="true" singleSelect="false">
					<thead>
						<tr>
							<th field="id" data-options="checkbox:true"></th>
							<th field="bueinessTypeStr" width="150">期货品种 </th>
							<th field="account" width="150">交易账号</th>
							<th field="password" width="150">交易密码</th>
							<th field="lever" width="150">持仓手数</th>
							<th field="tradeMoney" width="150">总操盘资金</th>
							<th field="createTimeStr" width="130" sortable="true">导入时间</th>
						</tr>
					</thead>
				</table>
			    <div id="dg003Toolbar" style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
						<table class="conn" border="0" style="font-size:12px;"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">期货品种:</td>
								<td width="15%">
									<select name="search_EQ_businessType" id="channerl1" style="width: 99%">
										<option value="">所有品种</option>
										<option value="1">商品综合</option>
										<option value="2">国际综合</option>
										<option value="3">富时A50</option>
										<option value="4">国际原油</option>
										<option value="5">恒生指数</option>
										<option value="6">小恒指</option>
									</select>
								</td>
								<td class="label right">交易账号:</td>
								<td width="15%">
									<input type="text" name="search_LIKE_account" id="pm" />
								</td>
								<td class="label right">持仓手数:</td>
								<td width="15%">
									<input type="text" name="search_LIKE_lever" id="channer12" style="width:99%"/>
								</td>
								<td class="label right">总操盘资金:</td>
								<td width="15%">
									<input type="text" name="search_LIKE_tradeMoney" id="uk"/>
								</td>

							</tr>
						</table>
					</form>
					<a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton"  data-options="iconCls:'icon-search'">查询</a>
					<!-- ResetButton -->
					<shiro:hasPermission name="sys:riskmanager:futureAccount:import">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="importExcelData()">导入</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:futureAccount:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="deleteData()">删除</a>
					</shiro:hasPermission>
					<a href="${ctx}/admin/futureMatch/downloadTemplate" class="easyui-linkbutton" iconCls="icon-ok">下载excel模版</a>
					<shiro:hasPermission name="sys:riskmanager:futureAccount:export">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('dg003','queryForm');">导出</a>
					</shiro:hasPermission>
				</div>
			</div>

			<div title="分配记录" style="padding: 20px;">
				<div id="audittbP" style="padding: 5px; height: auto">
					<form id="queryForm3" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">期货品种：</td>
								<td>
									<select id="search_EQ_businessType" name="search_EQ_businessType" style="width: 150px;" class="easyui-combobox" editable="false">
										<option value="">所有品种</option>
										<option value="1">商品综合</option>
										<option value="2">国际综合</option>
										<option value="3">富时A50</option>
										<option value="4">国际原油</option>
										<option value="5">恒生指数</option>
										<option value="6">小恒指</option>
									</select>
								</td>
	
								<td class="label right">交易帐号：</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_account" name="search_LIKE_account">						
								</td>
								<td class="label right">持仓手数：</td>
								<td>
									<input class="easyui-validatebox" id="search_EQ_lever" name="search_LIKE_lever">
								</td>
								<td class="label right">总操盘资金：</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_tradeMoney" name="search_LIKE_tradeMoney">
								</td>
							</tr>
							<tr>
								<td class="label right">真实姓名：</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_username" name="search_LIKE_username">
								</td>
								<td class="label right">手机号码：</td>
								<td>
									<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">
								</td>
								<td class="label right">分配时间：</td>
								<td colspan="2"><input id="startTime1" name="search_date_GTE_assignTime"
									class="Wdate" type="text"
									onFocus="var endTime1=$dp.$('endTime1');WdatePicker({onpicked:function(){endTime1.focus();},maxDate:'#F{$dp.$D(\'endTime1\')}'})" />
									- <input id="endTime1" name="search_date_LTE_assignTime"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime1\')}'})" />
								</td>
								<td>
									
								</td>
							</tr>
						</table>
					</form>
					
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagridP','queryForm3')">查询</a>
					<shiro:hasPermission name="sys:riskmanager:futureAccount:export">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('edatagridP','queryForm3');">导出</a>
					</shiro:hasPermission>
				</div>
				<table id="edatagridP" class="easyui-datagrid" pagination="true"
					toolbar="#audittbP" url="${ctx}/admin/futureMatch/getAssignRecord"
					rownumbers="true" fitColumns="true"
					data-options="toolbar:'#audittbP',
					        onLoadSuccess:function(data){
								datagridUtils.loadSuccess(data,'edatagridP');
							}">
					<thead>
						<tr>
							<th field="id" hidden="true"></th>
							<th field="bueinessTypeStr" width="130">期货品种</th>
							<th field="account" width="130">交易账号</th>
							<th field="password" width="130">交易密码</th>
							<th field="lever" width="130">持仓手数</th>
							<th field="tradeMoney" width="130">总操盘资金</th>
							<th field="username" width="130">真实姓名</th>
							<th field="mobile" width="130">手机号码</th>
							<th field="createTimeStr" width="130" sortable="true">导入时间</th>
							<th field="assignTimeStr" width="130" sortable="true">分配时间</th>
						</tr>
					</thead>
				</table>
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
						<td>
							<a href="#" class="easyui-linkbutton" onclick="submitInfo()">上传</a>
							<input type="submit" id="fileUploadForm" style="display: none;">
						</td>
					</tr>
				</table>
				<iframe name="target_upload" style="display: none;"></iframe>
			</form>
		</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:futureAccount:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
	
	<script type="text/javascript">
		var basepath='${ctx}'+"/";

		$('#futureAccountTab').tabs(
		{
			onSelect : function(title, index) {
				if (title == '账号统计') {
					reloadTheTable();
				}
				if (title == '账户列表') {
				}
				if (title == '分配记录') {
				}
			}
		});

		function reloadTheTable() {
			$('#futureAccountCount').empty();
			$('#futureAccountCount').append('<tr>'
					+'<td class="label center"><span>期货品种</span></td>'
					+'<td class="label center"><span>持仓手数（手）</span></td>'
					+'<td class="label center"><span>总操盘资金（元/美元）</span></td>'
					+'<td class="label center"><span>剩余账号个数（个）</span></td>'
					+'</tr>');
			$.post(Check.rootPath()+'/admin/futureMatch/getAccountStatistics',function(data){
				var ifu = data.obj.ifu;
				var gfu = data.obj.gfu;
				var afu = data.obj.afu;
				var ofu = data.obj.ofu;
				var hfu = data.obj.hfu;
				var lhfu = data.obj.lhfu;
				//alert(ifu.length);
				//国际
				 for(var i = 0;i<ifu.length;i++){
					if(i == 0){
						$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+ifu.length+'">'+ifu[i].bueinessTypeStr+'</td>'
							+'<td align = "center">'+ifu[i].lever+'</td>'
							+'<td align = "center">'+ifu[i].tradeMoney+'</td>'
							+'<td align = "center">'+ifu[i].number+'</td>'
							+'</tr>');
					}else{
						$('#futureAccountCount').append('<tr>'
								+'<td align = "center">'+ifu[i].lever+'</td>'
								+'<td align = "center">'+ifu[i].tradeMoney+'</td>'
								+'<td align = "center">'+ifu[i].number+'</td>'
								+'</tr>');
					}
				} 
				//商品
				 for(var i = 0;i<gfu.length;i++){
						if(i == 0){
							$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+gfu.length+'">'+gfu[i].bueinessTypeStr+'</td>'
								+'<td align = "center">'+gfu[i].lever+'</td>'
								+'<td align = "center">'+gfu[i].tradeMoney+'</td>'
								+'<td align = "center">'+gfu[i].number+'</td>'
								+'</tr>');
						}else{
							$('#futureAccountCount').append('<tr>'
									+'<td align = "center">'+gfu[i].lever+'</td>'
									+'<td align = "center">'+gfu[i].tradeMoney+'</td>'
									+'<td align = "center">'+gfu[i].number+'</td>'
									+'</tr>');
						}
					}
				//a50
				 for(var i = 0;i<afu.length;i++){
						if(i == 0){
							$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+afu.length+'">'+afu[i].bueinessTypeStr+'</td>'
								+'<td align = "center">'+afu[i].lever+'</td>'
								+'<td align = "center">'+afu[i].tradeMoney+'</td>'
								+'<td align = "center">'+afu[i].number+'</td>'
								+'</tr>');
						}else{
							$('#futureAccountCount').append('<tr>'
									+'<td align = "center">'+afu[i].lever+'</td>'
									+'<td align = "center">'+afu[i].tradeMoney+'</td>'
									+'<td align = "center">'+afu[i].number+'</td>'
									+'</tr>');
						}
					}
				//原油
				 for(var i = 0;i<ofu.length;i++){
						if(i == 0){
							$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+ofu.length+'">'+ofu[i].bueinessTypeStr+'</td>'
								+'<td align = "center">'+ofu[i].lever+'</td>'
								+'<td align = "center">'+ofu[i].tradeMoney+'</td>'
								+'<td align = "center">'+ofu[i].number+'</td>'
								+'</tr>');
						}else{
							$('#futureAccountCount').append('<tr>'
									+'<td align = "center">'+ofu[i].lever+'</td>'
									+'<td align = "center">'+ofu[i].tradeMoney+'</td>'
									+'<td align = "center">'+ofu[i].number+'</td>'
									+'</tr>');
						}
					}
				//恒生
				 for(var i = 0;i<hfu.length;i++){
						if(i == 0){
							$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+hfu.length+'">'+hfu[i].bueinessTypeStr+'</td>'
								+'<td align = "center">'+hfu[i].lever+'</td>'
								+'<td align = "center">'+hfu[i].tradeMoney+'</td>'
								+'<td align = "center">'+hfu[i].number+'</td>'
								+'</tr>');
						}else{
							$('#futureAccountCount').append('<tr>'
									+'<td align = "center">'+hfu[i].lever+'</td>'
									+'<td align = "center">'+hfu[i].tradeMoney+'</td>'
									+'<td align = "center">'+hfu[i].number+'</td>'
									+'</tr>');
						}
					}
				//小恒指
				 for(var i = 0;i<lhfu.length;i++){
						if(i == 0){
							$('#futureAccountCount').append('<tr><td align = "center" rowspan ="'+lhfu.length+'">'+lhfu[i].bueinessTypeStr+'</td>'
								+'<td align = "center">'+lhfu[i].lever+'</td>'
								+'<td align = "center">'+lhfu[i].tradeMoney+'</td>'
								+'<td align = "center">'+lhfu[i].number+'</td>'
								+'</tr>');
						}else{
							$('#futureAccountCount').append('<tr>'
									+'<td align = "center">'+lhfu[i].lever+'</td>'
									+'<td align = "center">'+lhfu[i].tradeMoney+'</td>'
									+'<td align = "center">'+lhfu[i].number+'</td>'
									+'</tr>');
						}
					}
			});
			
		}
		function importExcelData() {
			$('#handRechargeImportId').dialog('open');
		}

		$("#fileupload_input").fileupload({
		    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
		    dataType:'json',
		    formData:{dir:"upload/futureAccount",fileType:"excel",limitSize:10},//如果需要额外添加参数可以在这里添加
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
		  ajaxPost({
		    url : basepath + "admin/futureMatch/saveImport",
		    cache : false,
		    async : false,
		    data : $("#importForm").serialize(),
		    success : function(data) {
		      if (data.success) {
		        $('#handRechargeImportId').window('close');
		        $("#dg003").datagrid('reload');
		        eyWindow.walert("成功提示", data.message, 'info');
		        return;
		      }
				$("#fileUrl").val("");
		      eyWindow.walert("错误提示", data.message, 'error');

		    },
		    error : function(XMLHttpRequest, textStatus, errorThrown) {
		      eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
		    }
		  });
		}
		
		function deleteData(){
		  var rows = $("#dg003").datagrid('getChecked');
		  if (!rows || rows.length==0){
		    eyWindow.walert("删除提示","请选择要删除的行", 'info');
		    return;
		  }
		  // 删除数据
		  $.messager.confirm("确认提示","确认是否删除选中的数据？", function(result){
		    if (result){
		      datagridUtils.deleteData(rows,"admin/futureMatch/batchDelete","dg003");
		    }
		  });
		}
	</script>
</body>
</html>