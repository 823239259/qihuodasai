$(function() {
	//申请列表
	$('#applyGrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		toolbar : '#applyTool',// 工具栏
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
			field : 'traderBond',
			title : '操盘保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		},{
			field : 'voucherActualMoney',
			title : '实际代金券(元)',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return value == null ? '' : formatMoney(value);
			}
		}, {
			field : 'lineLoss',
			title : '亏损平仓线（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'financeMoney',
			title : '融资金额（元）',
			width : 150,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'traderTotal',
			title : '总操盘金额（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'tranLever',
			title : '可开仓手数',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				if (value != null && value != 0) {
					return value;
				}
				return "";
			}
		}, {
			field : 'tranAccount',
			title : '操盘账号',
			width : 100,
			sortable : true
		}, {
			field : 'tranPassword',
			title : '操盘密码',
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
			field : 'updateTime',
			title : '处理时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'programNo',
			title : '方案编号',
			width : 150,
			sortable : true
		}, {
			field : 'stateType',
			title : '状态',
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
			datagridUtils.loadSuccess(data, 'applyGrid');
		}
	});
	//追加保证金
	$('#bondGrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		toolbar : '#bondTool',// 工具栏
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
			field : 'type',
			title : '交易品种',
			width : 80,
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
			field : 'appendMoney',
			title : '补充保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'appendDate',
			title : '申请追加时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'updateTime',
			title : '处理时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'status',
			title : '状态',
			width : 100,
			sortable : true,
			formatter : function(value, row, index) {
				switch (value) {
				case 0:
					return "未处理";
				case 1:
					return "已处理";
				default:
					break;
				}
				return "异常";
			}
		} ] ],
		onLoadSuccess : function(data) {
			datagridUtils.loadSuccess(data, 'bondGrid');
		}
	});
	//方案管理
	$('#planGrid').datagrid({
		nowrap : true,// 在同一行中显示数据
		autoRowHeight : false,// 自动设置行高
		striped : true,// True 就把行条纹化。（即奇偶行使用不同背景色）
		collapsible : true,// 折叠面板
		toolbar : '#planTool',// 工具栏
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
			title : '方案申请时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
		}, {
			field : 'appStartTime',
			title : '启用时间',
			width : 200,
			sortable : true,
			formatter : function(value, row, index) {
				if(value!=null){
					return getSmpFormatDateByLong(value*1000, true);
				}
				return "";
			}
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
			field : 'useTranDay',
			title : '已操盘时间',
			width : 50
		}, {
			field : 'traderBond',
			title : '操盘保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, 
		{
			field : 'voucherActualMoney',
			title : '实际代金券(元)',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return value == null ? '' : formatMoney(value);
			}
		},{
			field : 'traderTotal',
			title : '总操盘金额（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'financeMoney',
			title : '融资金额（元）',
			width : 150,
			formatter : function(value, row, index) {
				return formatMoney(value);
			}
		}, {
			field : 'appendTraderBond',
			title : '补充保证金（元）',
			width : 150,
			sortable : true,
			formatter : function(value, row, index) {
				if(value == null){
					return "";
				}
				return formatMoney(value);
			}
		}, {
			field : 'tranProfitLoss',
			title : '交易盈亏（元）',
			width : 150,
			sortable : true
		},
		/*	2016-3-29 列表中，多了一列“交易手数”，需要删除。
		 * {
			field : 'tranActualLever',
			title : '交易手数',
			width : 150,
			sortable : true
		},*/
		{
			field : 'tranFeesTotal',
			title : '实际手续费（元）',
			width : 150,
			sortable : true
		},
		 {
			field : 'discountMoneyStr',
			title : '优惠券',
			width : 150,
			sortable : true
		},
		 {
			field : 'discountActualMoney',
			title : '抵扣手续费(元)',
			width : 150,
			sortable : true
		},{
			field : 'endAmountCal',
			title : '结算金额（元）',
			width : 150,
			sortable : true
		},
		{
			field : 'endAmount',
			title : '实际结算金额（元）',
			width : 150,
			sortable : true
		},{
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
			field : 'programNo',
			title : '方案编号',
			width : 150,
			sortable : true
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
			datagridUtils.loadSuccess(data, 'planGrid');
		}
	});
	$('#commodityRiskTab').tabs(
			{
				onSelect : function(title, index) {
					if (title == '申请列表') {
						$('#applyGrid').datagrid('options').url = basepath
								+ 'admin/commodityFutureRisk/getData?type=1';
						$('#applyGrid').datagrid('reload');
					}
					if (title == '补充保证金') {
						$('#bondGrid').datagrid('options').url = basepath
						+ 'admin/commodityFutureRisk/getData?type=2';
						$('#bondGrid').datagrid('reload');
					}
					if (title == '方案管理') {
						$('#planGrid').datagrid('options').url = basepath
						+ 'admin/commodityFutureRisk/getData?type=3';
						$('#planGrid').datagrid('reload');
					}
				}
			});
})

/**
 * 去除金额中的逗号
 * @param money
 * @returns
 */
function formatMoney(value){
	var money=moneyUtils.format(value, 2).toString();
	if(money=="0" || money=="0.00"){
		return "0";
	}
	if(money.indexOf(".00")>0){
		money=money.substring(0,money.indexOf(".00"));
	}else if(money.indexOf(".")>0 && money.lastIndexOf("0")==money.length-1){
		money=money.substring(0,money.lastIndexOf("0"));
	}

	return money.replace(",","");
}

var futureRiskOptions = {
	//跳转到分配账号页
	toAccount : function(width, height, title, type) {
		var rowData = $('#applyGrid').datagrid('getSelected');
		if (null == rowData) {
			eyWindow.walert('提示', '请选择要操作的记录', 'info');
			return;
		}else if(rowData.stateType==5){
			eyWindow.walert('提示', '审核不通过的记录不能分配账户', 'info');
			return;
		}else if(type==1 && rowData.stateType==4){
			eyWindow.walert('提示', '操盘中的记录不能分配账户', 'info');
			return;
		}else if(type==2 && rowData.stateType==1){
			eyWindow.walert('提示', '开户中的记录不能修改', 'info');
			return;
		}
		$('#addWin').window(
				{
					collapsible : false,
					minimizable : false,
					maximizable : false,
					width : width,
					height : height,
					title : title,
					loadingMessage : '正在加载,请等待......',
					iconCls : 'icon-edit',
					closed : true,
					modal : true,
					href : basepath + 'admin/commodityFutureRisk/toAccount?id='
							+ rowData.id+'&type='+type
				});
		$('#addWin').window('open');
	},
	//申请列表——拒绝申请
	refuseApply:function(){
		var rowData=$('#applyGrid').datagrid('getSelected');
		if (rowData==null){
			eyWindow.walert("提示",'请选择要拒绝申请的记录', 'info');
			return;
		}else if(rowData.stateType!=1){
			eyWindow.walert("提示",'请选择状态为\"开户中\"的记录', 'info');
			return;
		}
		$.messager.confirm("确认提示","确认是否拒绝申请？", function(result){
			if(result){
				ajaxPost({
					url : basepath + 'admin/commodityFutureRisk/refuseApply',
					cache : false,
					async : false,
					data : {id : rowData.id},
					success : function(data) {
						if (data.success) {
							$("#applyGrid").datagrid('reload');
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
	//补充保证金——已处理
	changeStatus:function(){
		var rowData=$('#bondGrid').datagrid('getSelected');
		if (rowData==null){
			eyWindow.walert("提示",'请选择要处理的记录', 'info');
			return;
		}else if(rowData.status!=0){
			eyWindow.walert("提示",'请选择状态为\"未处理\"的记录', 'info');
			return;
		}
		$.messager.confirm("确认提示","确认是否已处理？", function(result){
			if(result){
				ajaxPost({
					url : basepath + 'admin/commodityFutureRisk/changeStatus',
					cache : false,
					async : false,
					data : {id : rowData.id},
					success : function(data) {
						if (data.success) {
							$("#bondGrid").datagrid('reload');
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
	//跳转到录入结果页
	toResult : function(width, height, title, type) {
		var rowData = $('#planGrid').datagrid('getSelected');
		if (null == rowData) {
			eyWindow.walert('提示', '请选择要操作的记录', 'info');
			return;
		}else if(rowData.stateType==6){
			eyWindow.walert('提示', '已结算的记录不能录入结果', 'info');
			return;
		}
		$('#addWin').window(
				{
					collapsible : false,
					minimizable : false,
					maximizable : false,
					width : width,
					height : height,
					title : title,
					loadingMessage : '正在加载,请等待......',
					iconCls : 'icon-edit',
					closed : true,
					modal : true,
					href : basepath + 'admin/commodityFutureRisk/toResult?id='
							+ rowData.id+'&mobile='+rowData.mobile
				});
		$('#addWin').window('open');
	},
	//方案管理——结算
	settle:function(){
		var rowData=$('#planGrid').datagrid('getSelected');
		if (rowData==null){
			eyWindow.walert("提示",'请选择要处理的记录', 'info');
			return;
		}else if(rowData.stateType!=3){
			eyWindow.walert("提示",'请选择状态为\"待结算\"的记录', 'info');
			return;
		}
		$.messager.confirm("确认提示","确认是否结算？", function(result){
			if(result){
				ajaxPost({
					url : basepath + 'admin/commodityFutureRisk/settle',
					cache : false,
					async : false,
					data : {id : rowData.id},
					success : function(data) {
						if (data.success) {
							$("#planGrid").datagrid('reload');
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
	//取消按钮
	cancel:function(){
		$('#addWin').window('close');
	},
	saveOrUpdate:function(url,gridId){
		if($("#tranFeesTotal").length>0){
			var tranFeesTotal=$("#tranFeesTotal").val();
			if(tranFeesTotal < 0){
				eyWindow.walert("错误提示", "输入的数据有误！", 'error');
				return;
			}
		}
		if ($("#addForm").form('validate') == false) {
			return;
		}
		//alert($("#addForm").serialize());
		ajaxPost({
			url : basepath + url,
			cache : false,
			async : false,
			data : $("#addForm").serialize(),
			success : function(data) {
				if (data.success) {
					$('#addWin').window('close');
					$("#"+gridId).datagrid('reload');
					eyWindow.walert("成功提示", data.message, 'info');
					return;
				}
				eyWindow.walert("错误提示", data.message, 'error');
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
			}
		});
	}
}
