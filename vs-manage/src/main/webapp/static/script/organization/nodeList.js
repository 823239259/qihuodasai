$(document).ready(function(){
	$('#eytreegrid').treegrid({  
        onBeforeExpand:function(node,param){                         
        	$('#eytreegrid').treegrid('options').url=basepath+"admin/org/getNodeData?parentId="+node.id;
        },
        loadingMessage:'正在加载,请等待......',
        onLoadSuccess:function(row,data){
        	treeGridUtils.loadSuccess(data);
        }
    });  
});


var organization={
		openAddOrgWin:function(){
			$('#addOrgWin').window({collapsible:false,minimizable:false,maximizable:false,title:'新增组织',loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,href:basepath+'admin/org/edit?fromType=add&id='+orgID});
			$('#addOrgWin').window('open');
		},
		openEditOrgWin:function(){
			$('#addOrgWin').window({collapsible:false,minimizable:false,maximizable:false,title:'修改组织',loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/org/edit?fromType=edit&id='+orgID});
			$('#addOrgWin').window('open');
		},
		openAddUserWin:function(){
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,title:'新增用户',loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,href:basepath+'admin/user/edit?fromType=orgAddUser&orgID='+orgID});
			$('#addWin').window('open');
		},	
		saveOrUpdate:function(parentID,id,url){
			if ($("#orgForm").form('validate') == false) {
				return;
			}
			var isShow = $("input[name='show']:checked").val();
			var orgName = $("#name").val();
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : {
					"id" : id,
					"name" : orgName,
					"parentId" : parentID,
					"show" :isShow,
					"weight":$("#weight").val()
				},
				success : function(data) {
					$('#addOrgWin').window('close');
					//$("#eytreegrid").treegrid('reload');
					if (data.success) {
						eyWindow.walert("成功提示", data.message, 'info');
						setTimeout(organization.addSuccess(parentID,url,isShow,orgName),10000);
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function() {
					eyWindow.walert("错误提示", "系统异常", 'error');
				}
			});
		},
		addSuccess:function(nodeId,url,isShow,orgName){
			if (url.indexOf('create')>0){
				organization.refreshTree();
				organization.refreshTreeGrid(nodeId,null);
				return;
			}
			
			organization.refreshTree();
			organization.refreshTreeGrid(nodeId,orgName);
			
		/*	// 修改时是否显示  不显示则关闭tab
			if (isShow=='true'){
				organization.refreshTree();
				organization.refreshTreeGrid(nodeId,orgName);
			}
			else
			{
				//organization.refreshTreeGrid(nodeId);
				organization.refreshTree();
				organization.deleteCurrentTab();
			}
			*/
			
		},
		deleteOrg: function(id){
			// 删除数据
			$.messager.confirm("确认提示","确认要删除组织吗？", function(result){
				if (result){
					ajaxPost({
						url : basepath + 'admin/org/delete',
						cache : false,
						async : false,
						data : {
							"id" : id
						},
						success : function(data) {
							if (data.success) {
								eyWindow.walert("成功提示", data.message, 'info');
								organization.refreshTree();
								organization.deleteCurrentTab();
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
		refreshTreeGrid:function(nodeId,orgName){
			 var topJq = parent.jQuery;
			 var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/org/nodeList?nodeID='+nodeId+'" style="width:100%;height:100%;"></iframe>';
			 if (null == orgName ){
				 topJq('#tabPanel').tabs('update',{tab:topJq('#tabPanel').tabs('getSelected'),options:{content:html}});
				 return;
			 }
			 topJq('#tabPanel').tabs('update',{tab:topJq('#tabPanel').tabs('getSelected'),options:{content:html,title:orgName}});
		},
		refreshTree:function(){
			 var topJq = parent.jQuery;
			 // 刷新树
			 topJq('#orgTree').tree('options').url=basepath+"admin/org/getTreeData?parentId=0";
			 topJq('#orgTree').tree('reload');
		},
		deleteCurrentTab:function(){
			 var topJq = parent.jQuery;
			 var tabName = topJq('#tabPanel').tabs('getSelected').panel('options').title;
			 topJq('#tabPanel').tabs('close',tabName);
		}
}