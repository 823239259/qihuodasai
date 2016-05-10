$(document).ready(function(){
    $('#userTree').combotree({
        url:basepath+'admin/org/getUserTreeData?parentId=0',
        valueField:'id',
        textField:'text',
        multiple:true,
        cascadeCheck:false,
        panelHeight:200,
        width:300,
        required:true,
        onlyLeafCheck:true,
        onBeforeExpand:function(node){   
        	$('#userTree').combotree('tree').tree('options').url=basepath+"admin/org/getUserTreeData?parentId="+node.id;
        }
    }); 
});

var grantRole = {
	selectRole : function(obj) {
		var cloneObj = $(obj).clone();
		var parentID = $(obj).parent().attr("id");
		$(obj).remove();
		if (parentID == 'rolep2') {
			cloneObj.appendTo($("#rolep1"));
			return;
		}
		cloneObj.appendTo($("#rolep2"));
	},
	roleIds:[],
	userIds:[],
	/**
	 * 获取选择的用户列表
	 */
	getUserIds:function(){
		var resourceTree = $('#userTree').combotree('tree');      // 得到树对象 
		var nodes = resourceTree.tree('getChecked');          // 得到选择的节点 数组 
		for (var i=0;i<nodes.length;i++){
			var node = nodes[i];
			var uid = node.id;
			if (uid.indexOf('U')==0){
				grantRole.userIds.push(uid.substring(1,uid.length));
			}
		}		
	},
	saveUserRole:function(url){
		grantRole.userIds.length=0;
		grantRole.roleIds.length=0;
		
		if ($("#userRoleForm").form('validate') == false) {
			return;
		}	
		var roleNum =  $("#rolep2 .easyui-linkbutton").size();
		if (roleNum==0){
			 eyWindow.walert("错误提示",'请选择角色', 'error');
			 return ;
		}
		$("#rolep2 .easyui-linkbutton").each(function(){
			grantRole.roleIds.push(this.id);
		});
		
		grantRole.getUserIds();
		
	    ajaxPost({
			url : basepath + url,
			cache : false,
			async : false,
			data : {
				"roleIds":grantRole.roleIds.join(','),
				"userIds":grantRole.userIds.join(',')
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
}