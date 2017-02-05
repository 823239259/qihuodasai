$(function() {
	$('#edatagrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		url : basepath + 'admin/commodityFutureEarning/getData',// 请求路径
		toolbar : '#tb',// 工具栏
		rownumbers : true,// 显示行号
		pagination : true,// 底部显示分页工具栏
		singleSelect : true,// 单选
		fitColumns : true,// 自适应列宽
		// 冻结在左侧的列
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],
		columns : [ [ {
			field : 'id',
			hidden : true
		}, {
			field : 'uid',
			hidden : true
		}, {
			field : 'mobile',
			title : '手机号码',
			width : 160,
			sortable : true
		}, {
			field : 'username',
			title : '客户姓名',
			width : 100,
			sortable : true
		}, {
			field : 'businessType',
			title : '交易品种',
			width : 50,
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 0:
					return "富时A50";
				case 1:
					return "沪金";
				case 2:
					return "沪银";
				case 3:
					return "沪铜";
				case 4:
					return "橡胶";
				case 20:
					return "商品综合";
				default:
					break;
				}
				return "异常";
			}
		}, {
			field : 'tranAccount',
			title : '操盘账号',
			width : 100,
			sortable : true
		}, {
			field : 'appTime',
			title : '申请时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'traderBond',
			title : '操盘保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		} ,{
			field : 'endActualMoney',
			title : '抵扣保证金（元）',
			width : 150,
			sortable : true,
			
		}, {
			field : 'tranLever',
			title : '可开仓手数',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				if(value != null && value != 0) {
					return value;
				}
				return "";
			}
		}, {
			field : 'updateTime',
			title : '开户处理时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'appendTraderBond',
			title : '补充保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return moneyUtils.format(value, 2);
			}
		}, {
			field : 'appEndTime',
			title : '申请结算时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'endTime',
			title : '结算时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'tranFeesTotal',
			title : '实际手续费（元）',
			width : 150,
			sortable : true,
			
		},  {
			field : 'discountActualMoney',
			title : '抵扣手续费（元）',
			width : 150,
			sortable : true,
			
		},  {
			field : 'discountMoney',
			title : '折扣券（折）',
			width : 150,
			sortable : true
		}, {
			field : 'actualProfitLoss',
			title : '实际盈亏（元）',
			width : 150,
			
		}, {
			field : 'stateType',
			title : '结算状态',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 1:
					return "开户中";
				case 2:
					return "申请结算";
				case 3:
					return "待结算";
				case 4:
					return "操盘中";
				case 5:
					return "审核不通过";
				case 6:
					return "已结算";
				default:
					break;
				}
				return "异常";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, 'edatagrid');
		}
	});
})

