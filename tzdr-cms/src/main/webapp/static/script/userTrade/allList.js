$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/allTrades/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fitColumns:true,// 自适应列宽
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'mobile',title:'手机号码',width:114,sortable:true},
		    {field:'uname',title:'真实姓名',width:80,sortable:true},
		    {field:'userTypeStr',title:'用户类型',width:80,sortable:true},
		    {field:'accountName',title:'交易账户名',width:100,sortable:true},
			{field:'account',title:'交易账号',width:100,sortable:true},
			{field:'groupId',title:'方案编号',width:75,sortable:true},
			{field:'totalLeverMoney',title:'配资保证金',width:120,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalLending',title:'配资金额',width:120,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'totalAppendLeverMoney',title:'累计追加保证金',width:125,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			/*{field:'allExtractableProfit',title:'累计提取利润',width:105,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},*/
			{field:'tradeStatus',title:'方案状态',width:80},
			{field:'naturalDays',title:'配资天数',width:90,sortable:true},
			{field:'tradingDays',title:'已使用天数',width:100},
			{field:'shortestDuration',title:'最短保留时长(交易日)',width:100},
			{field:'addtime',title:'方案创建时间',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'starttime',title:'方案开始时间',width:120,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd');
			}},
			{field:'feeTypeValue',title:'账户类型',width:100},
			{field:'endtime',title:'方案终止时间',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'estimateEndtime',title:'预计结束时间',width:120,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd');
			}},
			{field:'activityTypeStr',title:'方案类型',width:80},
			{field:'newStatusStr',title:'是否首个配资',width:80,formatter:function(value){
				if(value=="是"){
					return '<a style="color:red;">'+value+'</a>';
				}else{
					return '<a >'+value+'</a>';
				}
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});



var allTrade={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				first : 1, 
				search_LIKE_uname : $('#uname').val(),   
				search_LIKE_accountName : $('#accountName').val(),    
				search_LIKE_mobile : $('#moblie').val(),
				search_LIKE_account : $('#account').val(),
				search_GTE_totalLeverMoney:$("#totalLeverMoney1").val(),
				search_LTE_totalLeverMoney:$("#totalLeverMoney2").val(),
				search_EQ_status : $("#tradeStatus").combobox('getValue'),
				search_date_GTE_endtime:$("#endtime1").val(),
				search_date_LTE_endtime:$("#endtime2").val(),
				search_GTE_totalLending:$("#totalLending1").val(),
				search_LTE_totalLending:$("#totalLending2").val(),
				search_date_GTE_starttime:$("#starttime1").val(),
				search_date_LTE_starttime:$("#starttime2").val(),
				search_date_GTE_addtime:$("#addtime1").val(),
				search_date_LTE_addtime:$("#addtime2").val(),
				search_GTE_totalAppendLeverMoney:$("#totalAppendLeverMoney1").val(),
				search_LTE_totalAppendLeverMoney:$("#totalAppendLeverMoney2").val(),
			/*	search_GTE_allExtractableProfit:$("#allExtractableProfit1").val(),
				search_LTE_allExtractableProfit:$("#allExtractableProfit2").val(),*/
				search_IN_feeType:$("#feeType").combobox('getValue'),
				search_EQ_activityType:$("#activityType").combobox('getValue'),
				search_EQ_userType:$('#userType').combobox('getValue'),
				search_date_GTE_estimateEndtime:$("#estimateEndtime1").val(),
				search_date_LTE_estimateEndtime:$("#estimateEndtime2").val(),
			});
		},
		openChildDetail:function(methed,fromType,title){
			var rows = $("#edatagrid").datagrid('getChecked'); 
			if (!rows || rows.length==0){
				eyWindow.walert("提示","请选择对应的方案！", 'info');
				return;
			}
			baseUtils.openIframeWin(800, 400,title,'admin/allTrades/'+methed+'?fromType='+fromType+'&groupId='+rows[0].groupId);
		},
		openAgentInfo : function(url, title) {
			if (Check.validateSelectItems($("#edatagrid"), 1)) {
				var rows = $('#edatagrid').datagrid('getSelections');
				baseUtils.openIframeWin(500, 300, title, url + '?uid=' + rows[0].uid);
			}
		}
}

