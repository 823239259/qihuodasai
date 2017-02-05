$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/withdrawMoney/getDatas',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false,search_EQ_typeKey:'withdrawMoneyType',search_EQ_valueKey:'withdrawMoneyValue'},
		columns:[[
		    {field:'id',hidden:true},
		    {field:'valueName',title:'提现审核金额',width:200,sortable:true},
			{field:'createUser',title:'创建人',width:100,sortable:true},
			{field:'createTime',title:'创建时间',width:150,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}},
			{field:'updateUser',title:'修改人',width:100,sortable:true},
			{field:'updateTime',title:'修改时间',width:150,sortable:true,formatter: function(value,row,index){
				return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
			}}
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});


var withDrawMoney={
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_typeKey : $('#typeKey').val(),
				search_EQ_deleted:false
			});
		},
		openSetWin:function(){ 
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:650,height:230,title:'设置提现审核金额',loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/withdrawMoney/toEidt'});
			$('#addWin').window('open');
		},
		submitInfo:function(){
			var minMoney = $("#minMoney").val();
			var maxMoney = $("#maxMoney").val();

			if (Number(minMoney) >= Number(maxMoney)){
				eyWindow.walert("提示",'审核金额开始金额必须小于结束金额！', 'error');
				return;
			}
			
			baseUtils.saveOrUpdate('admin/withdrawMoney/setMoney', 'iframe')
		}
}