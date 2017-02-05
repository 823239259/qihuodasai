/**
 * 用于系统公告列表
 */

var viewData = {
	dataGridId : 'edatagrid',
	toolDivId : 'tb',
	toolDivId_pgb:'tb_pgb',
	dataGridId_pgb : 'edatagrid_pgb',
}

var noticeGrid = {
	nowrap : true,
	autoRowHeight : true,
	striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
	collapsible : true,
	url : $.easyui.path() + '/admin/notice/listData',
	toolbar : '#' + viewData.toolDivId, // 顶部工具栏
	rownumbers : true, // 显示行数
	singleSelect : true, // 单选
	pagination : true, // 显示分页工具栏
	fitColumns : true,// 自适应列宽
	// checkOnSelect: true,//当用户点击行的时候该复选框就会被选中或取消选中
	// selectOnCheck: true,//单击复选框将永远选择行
	columns : [ [
			{
				field : 'id',
				hidden : true
			},
			{
				field : 'content',
				title : '内容',
				width : 300,
				formatter : function(value, row, index) {
					return "<textarea rows='5' readonly='readonly' nowrap='true'  style='width:100%;'>"
							+ value.replace(/<br\/>/g, '\n') + "</textarea>"
				}
			}, {
				field : 'status',
				title : '状态',
				width : 50,
				sortable : true, // 排序
				formatter : function(value, row, index) {
					if (Number(row.status) == 2) {
						return '启用';
					}
					return '停用';
				}
			} ] ]
}

var option = {
	open : function() {
		updateStatus(2);
	},
	stop : function() {
		updateStatus(1);
	},
	loadGrid : function() {
		$("#" + viewData.dataGridId).datagrid();
	},
	closeWin : function() {
		$("#" + viewData.dataGridId_pgb).datagrid('reload');
		$('#addWin').window('close');
	}
}

function updateStatus(status) {
	var $noticeGrid ="";
	
	var title=$("#noticeTab").tabs('getSelected').panel('options').title;
			if("配股宝公告"==title){
				$noticeGrid = $("#" + viewData.dataGridId_pgb);
			}else if("维胜公告"==title){
				$noticeGrid = $("#" + viewData.dataGridId);
			}
	    
	
	var row = $noticeGrid.datagrid('getSelected');
	var url = $.easyui.path() + '/admin/notice/open';
	if (status == 1) {
		url = $.easyui.path() + "/admin/notice/stop";
	}
	if (row) {
		var rowIndex = $noticeGrid.datagrid('getRowIndex', row);
		$.post(url, {
			"id" : row.id
		}, function(data) {
			if (data.success) {
				row.status = status;
			
						if("配股宝公告"==title){
							$("#" + viewData.dataGridId_pgb).datagrid('updateRow', {
								index : rowIndex,
								row : row
							});
						}else if("维胜公告"==title){
							$("#" + viewData.dataGridId).datagrid('updateRow', {
								index : rowIndex,
								row : row
							});
						}
			} else {
				eyWindow.walert("错误提示", data.message, "error")
			}
		});
	} else {
		eyWindow.walert("提示", '请选择要启用的行', 'info');
	}
}

function peigubaoNotice(){
	noticeGrid.url=$.easyui.path() + '/admin/notice/pgblistData';
	noticeGrid.toolbar='#'+viewData.toolDivId_pgb;
	$("#" + viewData.dataGridId_pgb).datagrid(noticeGrid);
}

$(function() {
	$("#" + viewData.dataGridId).datagrid(noticeGrid);
	
	
	$("#noticeTab").tabs({
		border:false,
	    onSelect:function(title){
			if("配股宝公告"==title){
				peigubaoNotice();
			}
	    }
	})
})