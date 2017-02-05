/*$(document).ready(function() {
	$.post(Check.rootPath()+'/admin/togetherRecord/getData',{},function(data){
		if (null != data) {
			var data1 = data;
			var i = 1;
			i++;
		}
	});
});*/

//$(function() {
//    $('#edatagrid').datagrid({
//        nowrap : true,// 在同一行中显示数据
//        autoRowHeight : false,// 自动设置行高
//        collapsible : true,// 折叠面板
//        toolbar : '#tb',// 工具栏
//        rownumbers : true,// 显示行号
//        pagination : true,// 底部显示分页工具栏
//        singleSelect : true,// 单选
//        fitColumns : true,// 自适应列宽
//        columns : [ [ {
//            field : 'tradeNo',
//            title : '方案号',
//            width : 150
//        }, {
//            field : 'tradeName',
//            title : '方案名称',
//            width : 100,
//            sortable : true
//        }, {
//            field : 'userName',
//            title : '用户名称',
//            width : 150
//        }, {
//            field : 'mobile',
//            title : '手机',
//            width : 150
//        }, {
//            field : 'copies',
//            title : '份数',
//            width : 150
//        }, {
//            field : 'payMoney',
//            title : '支付金额',
//            width : 150
//        }, {
//            field : 'poundage',
//            title : '手续费',
//            width : 150
//        }, {
//            field : 'profitLossPoint',
//            title : '盈亏点数',
//            width : 150
//        }, {
//            field : 'achieveProfitLoss',
//            title : '实现盈亏',
//            width : 150
//        }, {
//            field : 'expectSettlementMoney',
//            title : '预计计算金额',
//            width : 200
//        }, {
//            field : 'actualSettlementMoney',
//            title : '实际结算金额',
//            width : 150
//        }, {
//            field : 'settlementTime',
//            title : '结算时间',
//            width : 200
//        }
//         ] ],
//        onLoadSuccess : function(data) {
//            //datagridUtils.loadSuccess(data, 'edatagrid');
//        }
//    });
//    $('#earningsTab').tabs(
//        {
//            onSelect : function(title, index) {
//                    $('#edatagrid').datagrid('options').url = basepath
//                        + 'admin/togetherRecord/getData';
//                    $('#edatagrid').datagrid('reload');
//
//            }
//        });
//});
//
