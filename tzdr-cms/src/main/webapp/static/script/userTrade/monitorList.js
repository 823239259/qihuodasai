$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/monitor/data',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'groupId',hidden:true},
		    {field:'accountName',title:'恒生账户名',width:100,sortable:true},
			{field:'account',title:'恒生账号',width:100,sortable:true},
			{field:'mobile',title:'手机号码',width:120,sortable:true},
			{field:'uname',title:'用户姓名',width:100,sortable:true},
			{field:'totalLeverMoney',title:'风险保证金',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalLending',title:'配资金额',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalOperateMoney',title:'总操盘资金',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'warning',title:'亏损补仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'open',title:'亏损平仓线',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'assetTotalValue',title:'资产总值',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'stockAssets',title:'持仓市值',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'stockAssetsProportion',title:'持仓占比',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2)+"%";
			}},
			{field:'openDistance',title:'平仓距离',width:80},
			{field:'wearingDistance',title:'穿仓距离',width:80},
			{field:'buyStatus',title:'买入状态',width:80,sortable:true},
			{field:'sellStatus',title:'卖出状态',width:80,sortable:true},
			{field:'tradeAccrual',title:'方案盈亏',width:100,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'isBreakStore',title:'是否穿仓',width:80}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var monitor={
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
		changeStatus:function(status, dgid){
			var _dg = (dgid) ? $('#'+dgid) : $("#edatagrid"),
				rows = _dg.datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			monitor.checkedData.length=0;
			var groupIds = [];
			for (var i=0;i<rows.length;i++)
			{
				monitor.checkedData.push(rows[i]);
				groupIds.push(rows[i].groupId);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否修改选中账户状态？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/monitor/changeOperationStatus',
						cache : false,
						async : false,
						data : {
							"groupIds" : groupIds.join(","),
							"operationStatus":status
						},
						success : function(data) {
							if (data.success) {
								_dg.datagrid('reload');
								_dg.datagrid('clearSelections');//清除选中
								eyWindow.walert("成功提示", data.message, 'info');
								return;
							}
							eyWindow.walert("错误提示", data.message, 'error');
						},
						error : function() {
							eyWindow.walert("错误提示", "系统异常", 'error');
						}
					});
				}
			});
		},
		endHandTrade:function(dataId){
			var rowData=$('#'+dataId).datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要终结的方案', 'info');
				return;
			}
		
			$.messager.confirm("确认提示","确认是否终结方案？", function(result){
				if (result){
					$.post(Check.rootPath()+"/admin/monitor/saveHandTrade",{"groupId":rowData.groupId,"uid":rowData.uid},function(data){  
						if(data.success){
							$("#"+dataId).datagrid('reload');
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
				eyWindow.walert("修改提示",'请选择要补仓的方案', 'info');
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
				if(index == 1 && assetTotalValue >= warning){
					eyWindow.walert("补仓提示",'资产总值大于亏损补仓线，不能进行补仓操作', 'info');
					return;
				}else if(index == 2 && assetTotalValue >= open){
					eyWindow.walert("补仓提示",'资产总值大于亏损平仓线，不能进行补仓操作', 'info');
					return;
				}
				var coverMoney = index == 1 ? warning - assetTotalValue : open - assetTotalValue;
				$('#coverMoney').val(Math.abs(coverMoney));
			}
			$('#coverWin').window({collapsible:false,minimizable:false,maximizable:false,width:323,height:120,title:'补仓-'+name,iconCls:'icon-edit'});
			$("#coverWin").show();
			$('#coverWin').window('open');
		},
		
		closeCoverWin:function(){
			$("#coverWin").show();
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
					ajaxPost({
						url : basepath + 'admin/monitor/saveTradeCover',
						cache : false,
						async : false,
						data : {"type":type,"index":index,"groupId":groupId,"uid":uid,"coverMoney":coverMoney},
						success : function(data) {
							if (data.success) {
								$("#coverWin").show();
								$('#coverWin').window('close');
								eyWindow.walert("成功提示", data.message, 'info');
								if(type == 0){
									$("#edatagrid").datagrid('reload');
								}else{
									$("#datagrid2").datagrid('reload');
								}
								return;
							}
							eyWindow.walert("错误提示",data.message, 'info');
						},
						error : function() {
							eyWindow.walert("错误提示", "系统异常", 'error');
						}
					});
					
					/*$.post(basepath+"/admin/monitor/saveTradeCover",{"type":type,"index":index,"groupId":groupId,"uid":uid,"coverMoney":coverMoney},function(data){  
						if(data.success){
							$("#coverWin").show();
							$('#coverWin').window('close');
							eyWindow.walert("成功提示", data.message, 'info');
							if(type == 0){
								$("#edatagrid").datagrid('reload');
							}else{
								$("#datagrid2").datagrid('reload');
							}
						}else{
							eyWindow.walert("错误提示",data.message, 'info');
						}
					},"json"); */
				}
			});
		}
		
}