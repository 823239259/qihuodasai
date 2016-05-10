$(document).ready(function(){
    $('#resource').combotree({
        url:basepath+'admin/resource/getTreeData?parentId=0',
        valueField:'id',
        textField:'text',
        multiple:true,
        cascadeCheck:false,
        panelHeight:100,
        onBeforeExpand:function(node){   
        	//alert(node.id);
        	$('#resource').combotree('tree').tree('options').url=basepath+"admin/resource/getTreeData?parentId="+node.id;
        }
    }); 
    
    $("#edatagrid").datagrid({
		nowrap: true,
		autoRowHeight: false,
		striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible:true,
		toolbar:'#tb',
		rownumbers:true,
		width:620,
		url:basepath+'admin/role/getRoleResources?id='+$("#roleId").val(),
		nowrap:false,
		fitColumns:true,// 自适应列宽
		queryParams:{search_EQ_deleted:false},
		frozenColumns:[[{field:'ck',checkbox:true}]],
		columns:[[
		    {field:'id',hidden:true},
		    {field:'permissionIds',hidden:true},
			{field:'resource',title:'资源',width:340},
			{field:'permission',title:'权限',width:200}
		]],
		onLoadSuccess:function(data){
			if(data.total==0){
				$("#noDataInfo").show();
		        return;
			}
		}
		});
    
    $("#permission p").toggle(
  		  function () {
  			    $(this).addClass("selectP");
  			  },
  			  function () {
  			    $(this).removeClass("selectP");
  			  }
  );
});

var permission = {
		selectPermissionText:[],
		selectPermissionId:[],
		selectResourceIds:[],
		getSelectPermission:function(){
			$("#permission p").each(function(i){
				  if ($(this).hasClass("selectP")){
					  permission.selectPermissionId.push(this.id.split("-")[1])
					  permission.selectPermissionText.push($(this).html());
				  }
			 });
		},
		addResource:function(){
			//清除 selectPermissionId selectPermissionText selectResourceIds数据
			permission.selectPermissionId.length=0;
			permission.selectPermissionText.length=0;
			permission.selectResourceIds.length=0;
			var resourceTree = $('#resource').combotree('tree');      // 得到树对象 
			var nodes = resourceTree.tree('getChecked');          // 得到选择的节点 数组 
			if (nodes.length==0){
				eyWindow.walert("错误提示","请选择资源", 'error');
				return;
			}
			// 获取选择的权限
			permission.getSelectPermission();
			if (permission.selectPermissionId.length==0){
				eyWindow.walert("错误提示","请选择权限", 'error');
				return;
			}
			
			if ($("#noDataInfo")){
				$("#noDataInfo").hide();
			}
			
			// 获取选中的节点
			for (var i=0;i<nodes.length;i++){
				var node = nodes[i];
				permission.selectResourceIds.push(node.id);
				if (this.isAddResource(node.id)){
					continue;
				}
				var parent = resourceTree.tree('getParent',node.target);
				if (null == parent){
					  $('#edatagrid').datagrid('insertRow',{row:{id:node.id,permissionIds:permission.selectPermissionId.join(","),resource:node.text,permission:permission.selectPermissionText.join(",")}});
					  continue;
				}
				$('#edatagrid').datagrid('insertRow',{row:{id:node.id,permissionIds:permission.selectPermissionId.join(","),resource:parent.text+"->>"+node.text,permission:permission.selectPermissionText.join(",")}});
			}
			
			// 清除树 和权限选中
			$("#permission p").removeClass("selectP");
			$('#resource').combotree('clear');
		},
		isAddResource:function (id){
			var rows =  $('#edatagrid').datagrid('getRows');
			for (var i=0;i<rows.length;i++){
				if (rows[i].id==id){
					var rowIndex = $('#edatagrid').datagrid('getRowIndex', rows[i]);
					$('#edatagrid').datagrid('updateRow',{index:rowIndex,row:{permissionIds:permission.selectPermissionId.join(","),permission:permission.selectPermissionText.join(",")}});
					return true;
				}
			}
			return false;
		},
		removeResource:function(index){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("删除提示","请选择要删除的行", 'info');
				return;
			}
			//将选中行保存在变量中
			var tempRows = [];
			for (var i=0;i<rows.length;i++){
				tempRows.push(rows[i]);
			}
			// 删除数据
			$.messager.confirm("确认提示","确认是否删除选中的数据？", function(result){
				if (result){
					for (var i=0;i<tempRows.length;i++)
					{ 
		                 var rowIndex = $('#edatagrid').datagrid('getRowIndex', tempRows[i]);
		                 $('#edatagrid').datagrid('deleteRow',rowIndex);
		                 $('#edatagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');//清除选中
					}
					// 删除之后 查看剩余数量 并重新设置树选中的值
					permission.deleteAfter();
					//$('#edatagrid').datagrid('reload');
				}
			});		 
		},
		deleteAfter:function(){
			permission.selectResourceIds.length=0;
			var rows =  $('#edatagrid').datagrid('getRows');
			if (rows.length==0){
				$("#noDataInfo").show();
				return;
			}
		},
		saveRolePermission:function(url,type){
			if ($("#permissionForm").form('validate') == false) {
				return;
			}	
			
			permission.beforeSave(); //获取需要保存的数据
			if (permission.subData.length==0){
				$("#noDataInfo").show();
				return;
			}
			//alert(permission.subData.join(";"));
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : {
					"id":$("#roleId").val(),
					"name" : $("#name").val(),
					"role" : $("#role").val(),
					"description" : $("#description").val(),
					"show" : $("input[name='show']:checked").val(),
					"resourceData" : permission.subData.join(";")
				},
				success : function(data) {
					$('#addWin').window('close');
					$("#edatagrid").datagrid('reload');
					//$("#eytreegrid").treegrid('options').url = basepath+"/admin/org/getNodeData?parentId="+orgID;
					$("#eytreegrid").treegrid('reload');
					if (data.success) {
						 eyWindow.walert("成功提示", data.message, 'info');
						 permission.addSuccess(type);
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function() {
					eyWindow.walert("错误提示", "系统异常", 'error');
				}
			});
		},
		subData:[],
		beforeSave:function(){
			var rows =  $('#edatagrid').datagrid('getRows');
			if (rows.length==0){
				$("#noDataInfo").show();
				return;
			}
			
			for (var i=0;i<rows.length;i++)
			{
				var row = rows[i];
				permission.subData.push(row.id+"-"+row.permissionIds);
			}
		},
		addSuccess:function(type){
			 var topJq = top.jQuery;
			 if (topJq('#tabPanel').tabs('exists','角色列表')){
				 topJq('#tabPanel').tabs('select','角色列表');
				 var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/role/list" style="width:100%;height:100%;"></iframe>';
				 topJq('#tabPanel').tabs('update',{tab:topJq('#tabPanel').tabs('getTab','角色列表'),options:{content:html}});
				 if ('add'==type){
					 topJq('#tabPanel').tabs("close",'新增角色');
					 return;
				 }
				 topJq('#tabPanel').tabs("close",'修改角色');
			 }
		},
		backList: function (type){
			 var topJq = top.jQuery;
			 if (topJq('#tabPanel').tabs('exists','角色列表')){
					topJq('#tabPanel').tabs('select','角色列表');
			 }
			 if ('add'==type){
				 topJq('#tabPanel').tabs("close",'新增角色');
				 return;
			 }
			 topJq('#tabPanel').tabs("close",'修改角色');
		}
}
