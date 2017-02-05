$(document).ready(function(){
    $('#orgTree').tree({
        url:basepath+"admin/org/getTreeData?parentId=0",
        dnd:false,
        onBeforeExpand:function(node){                         
        	$('#orgTree').tree('options').url=basepath+"admin/org/getTreeData?parentId="+node.id;
        },
        onClick: function(node){
     	    tabUtils.addNewTab('tabPanel',node.text,'admin/org/nodeList?nodeID='+node.id);
     	}
     });
});

function reloadTree(){
	$('#orgTree').tree('options').url=basepath+"admin/org/getTreeData?parentId=0";
	$('#orgTree').tree('reload');
	//$('#orgTree').tree('expand',$('#orgTree').tree('find',1).target);
}