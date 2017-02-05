$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/coveraudit/unauditlistdata',
		toolbar:'#tb',
		rownumbers:true,
		singleSelect:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},      
		    {field:'uid',hidden:true},
		    {field:'groupId',hidden:true},
		    {field:'accountName',title:'恒生账户名',width:100,sortable:true},
			{field:'account',title:'恒生账号',width:100,sortable:true},
			{field:'mobile',title:'手机号码',width:200,sortable:true},
			{field:'tname',title:'用户姓名',width:200,sortable:true},
			{field:'coverMoney',title:'补仓金额',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'ctime',title:'提交时间',width:100,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var coverAudit={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_account: $('#accountNo').val()
			});
		},
		checkedData :[],
		reloadData:function(){
			$('#edatagrid').datagrid('clearSelections');//清除选中
			$("#edatagrid").datagrid('reload');
		},
		audit:function(status){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			coverAudit.checkedData.length=0;
			var id = rows[0].id;
			// 审核
			$.messager.confirm("确认提示","是否审核通过选中待审核记录？", function(result){
				if (result){
					$.post(Check.rootPath()+"/admin/coveraudit/audit",{"id" : id,"status" : status},function(data){  
						if(data.success){
							$("#edatagrid").datagrid('reload');
							$('#edatagrid').datagrid('clearSelections');//清除选中
							eyWindow.walert("成功提示", data.message, 'info');
						}else{
							eyWindow.walert("错误提示",data.message, 'error');
						}
					},"json"); 
				}
			});
		}/*,
		endHandTrade:function(){
			var rowData=$('#datagrid2').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要终结的方案', 'info');
				return;
			}
		
			$.messager.confirm("确认提示","确认是否终结方案？", function(result){
				if (result){
					$.post(basepath+"/admin/monitor/saveHandTrade",{"groupId":rowData.groupId,"uid":rowData.uid},function(data){  
						if(data.success){
							$("#datagrid2").datagrid('reload');
							eyWindow.walert("成功提示", data.message, 'info');
						}else{
							eyWindow.walert("成功提示",data.message, 'info');
						}
					},"json"); 
				}
			});
		},
		
		openCoverWin:function(type,index,name){
			
			var rowData= null;
			if(type == 0){
				rowData=$('#edatagrid').datagrid('getSelected');
			}else{
				rowData=$('#datagrid2').datagrid('getSelected');
			}
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要终结的方案', 'info');
				return;
			}
			
			$('#uid').val('');
			$('#groupId').val('');
			$('#coverType').val('');
			$('#coverIndex').val('');
			$('#coverMoney').val('');
			
			var uid = rowData.uid;
			var groupId = rowData.groupId;
			$('#uid').val(uid);
			$('#groupId').val(groupId);
			$('#coverType').val(type);
			$('#coverIndex').val(index);
			if(type == 0){
				var warning = rowData.warning;
				var open = rowData.open;
				var assetTotalValue = rowData.assetTotalValue;
				var coverMoney = index == 1 ? warning - assetTotalValue : open - assetTotalValue;
				$('#coverMoney').val(Math.abs(coverMoney));
			}
			$('#coverWin').window({collapsible:false,minimizable:false,maximizable:false,width:500,height:230,title:'补仓-'+name,iconCls:'icon-edit'});
			$('#coverWin').window('open');
		},
		
		closeCoverWin:function(){
			$('#coverWin').window('close');
		},
		coverMoney:function(){
			var uid = $('#uid').val();
			var groupId = $('#groupId').val();
			var coverMoney  = $('#coverMoney').val();
			var type  =  $('#coverType').val();
			var index  =  $('#coverIndex').val();
			$.messager.confirm("确认提示","确认是否给该方案补仓？", function(result){
				if (result){
					$.post(basepath+"/admin/monitor/saveTradeCover",{"type":type,"index":index,"groupId":groupId,"uid":uid,"coverMoney":coverMoney},function(data){  
						if(data.success){
							if(type == 0){
								$("#datagrid").datagrid('reload');
							}else{
								$("#datagrid2").datagrid('reload');
							}
							$('#coverWin').window('close');
							eyWindow.walert("成功提示", data.message, 'info');
						}else{
							eyWindow.walert("成功提示",data.message, 'info');
						}
					},"json"); 
				}
			});
		}*/
		
}