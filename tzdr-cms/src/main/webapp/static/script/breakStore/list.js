	var initData={dataGridId:'allData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/breakStore/getDatas', 
				rownumbers:true,
				pagination:true,
				fitColumns:true,// 自适应列宽
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'mobile',title:'手机号',width:100,sortable:true},
				    {field:'tname',title:'真实姓名',width:90,sortable:true},
					{field:'programNo',title:'方案编号',width:100,sortable:true},
					{field:'accountName',title:'交易帐户名',width:100,sortable:true},
					{field:'accrual',title:'终结穿仓金额',width:100,sortable:true},
					{field:'amount',title:'补仓欠费金额',width:150,sortable:true},
					{field:'paid',hidden:false,title:'已付穿仓金额', width:120, sortable:true},
					{field:'unpayment',hidden:false,title:'未付欠费', width:100, sortable:true},
					{field:'avlBal',hidden:false,title:'账户余额', width:100, sortable:true},
					{field:'endTime',title:'方案终结时间',width:100,hidden:false,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'activityTypeStr',title:'方案类型',width:150,sortable:true}
				]],
				onLoadSuccess:function(data){
					withdrawal.loadSuccess(data,initData.dataGridId);
					}
	}

$(document).ready(function(){
	$("#"+initData.dataGridId).datagrid(gridParams);
	//alert(gridParams.columns[0][6].hidden);
	initData.dataGridId="allData";
	gridParams.queryParams={};
	gridParams.columns[0][7].hidden=false;
	gridParams.columns[0][8].hidden=false;
	$("#"+initData.dataGridId).datagrid(gridParams);
});

var withdrawal={
	list:{
		doSearch1:function(){
			$('#allData').datagrid('load', {
				search_LIKE_mobile : $('#mobile').val(),
				search_LIKE_tname : $('#tname').val(),
				search_LIKE_accountName : $('#accountName').val(),
				search_EQ_activityType : $('#activityType').combobox('getValue')
			});
		}
	},
	loadSuccess:function(data,datagridID){
		//alert($('#'+datagridID).closest('div.datagrid-wrap').html());
		if ($('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo')){
			$('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo').remove();
		}
		
		if(data.total>0){
			$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').show();
	        return;
		}
		/*$('#'+datagridID).datagrid('appendRow',{
			username: '<div style="text-align:center;color:red">没有相关记录！</div>' }).datagrid('mergeCells', { index: 0, field:filedName, colspan: filedNum });*/
		 //隐藏分页导航条，这个需要熟悉datagrid的html结构，直接用jquery操作DOM对象，easyui datagrid没有提供相关方法隐藏导航条
		$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		$('#'+datagridID).closest('div.datagrid-wrap').append('<div id="noDataInfo" class="noDataInfo" style="text-align:center;color:red;padding:5px;">没有相关记录！</div>');
	}
}