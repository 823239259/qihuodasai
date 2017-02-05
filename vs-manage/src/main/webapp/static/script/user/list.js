$(document).ready(function(){
	$("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		url:basepath+'admin/user/easyuiPage',
		toolbar:'#tb',
		rownumbers:true,
		pagination:true,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false,search_NOT_admin:true},
		frozenColumns:[[
            {field:'ck',checkbox:true}
		]],
		columns:[[
		    {field:'id',hidden:true},
			{field:'realname',title:'姓名',width:100,sortable:true},
			{field:'email',title:'电子邮箱',width:200,sortable:true},
			{field:'mobilePhoneNumber',title:'手机号码',width:200,sortable:true},
			/*{field:'admin',title:'是否管理员',width:100,sortable:true},*/
			{field:'organization',title:'所属组织',width:100,sortable:true,formatter: function(value,row,index){
				return value.name;
			}},
			{field:'createDate',title:'创建时间',width:150,sortable:true},
		]],
		onLoadSuccess:function(data){
			datagridUtils.loadSuccess(data,"edatagrid");
			}
		});
});
var user={
	list:{
		doSearch:function(){
			$('#edatagrid').datagrid('load',{
				search_LIKE_realname : $('#name').val(),
				search_EQ_deleted:false,
				search_NOT_admin:true
			});
		},
		resetPassword:function(){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("重置提示","请选择需要重密码的用户", 'info');
				return;
			}
			
			var ids = [];
			for (var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].id);
			}
			
			// 重置密码
			$.messager.confirm("确认提示","确认是否重置选中用户的密码？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/user/resetPasswod',
						cache : false,
						async : false,
						data : {
							"ids" : ids.join(",")
						},
						success : function(data) {
							if (data.success) {
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
		saveOrUpdate:function(url,orgID){
			if ($("#addForm").form('validate') == false) {
				return;
			}
			if (!orgID)
			{
				orgID = $("#organization").combobox('getValue')
			}
			
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : {
					"id" : $("#dataID").val(),
					"realname" : $("#realname").val(),
					"password" : $("#password").val(),
					"mobilePhoneNumber" : $("#mobilePhoneNumber").val(),
					"email" : $("#email").val(),
					"organization.id":orgID
				},
				success : function(data) {
					if (data.success) {
						$('#addWin').window('close');
						$("#edatagrid").datagrid('reload');
						//$("#eytreegrid").treegrid('options').url = basepath+"/admin/org/getNodeData?parentId="+orgID;
						$("#eytreegrid").treegrid('reload');
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
	}
}