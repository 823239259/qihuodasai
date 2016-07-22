$(document).ready(function(){
	//alert(1);
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/allTrades/queryChildTrades',
		//toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_groupId:$("#groupId").val()},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'programNo',title:'子方案ID',width:114,sortable:true},
		    {field:'totalOperateMoney',title:'总操盘资金',width:100,formatter: function(value,row,index){
		    	return moneyUtils.format(value,2);
		    }},
		    {field:'totalLending',title:'配资金额',width:100,sortable:true,formatter: function(value,row,index){
		    	return moneyUtils.format(value,2);
		    }},
		    {field:'totalLeverMoney',title:'配资保证金',width:100,sortable:true,formatter: function(value,row,index){
				return moneyUtils.format(value,2);
			}},
			{field:'feeDay',title:'配资管理费',width:80},
			{field:'apr',title:'应收利息',width:90,sortable:true},
			
			{field:'dapr',title:'实收利息',width:90,sortable:true,formatter: function(value,row,index){
		    	if(value < 0){
		    		return "0";
		    	}else{
		    		return null==value?0:value.toFixed(2);
		    	}
		    	
		    }},
			{field:'addtime',title:'配资日期',width:160,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});