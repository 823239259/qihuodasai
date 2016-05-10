	var initData={dataGridId:'auditData'}
	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:basepath+'admin/withdrawAudit/getDatas?type=auditList', 
				rownumbers:true,
				toolbar:'#audittb',
				pagination:true,
				fitColumns:true,// 自适应列宽
				singleSelect:true,
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'uid',hidden:true},
				    {field:'tname',title:'客户姓名',width:100,sortable:true},
					{field:'mobile',title:'手机号',width:100,sortable:true},
					{field:'paymentChannelStr',title:'网银通道',width:100},	
					{field:'bank',title:'提现银行',width:170,sortable:true},
					{field:'card',title:'银行卡号',width:180,sortable:true},
					{field:'money',title:'提现金额',width:100,sortable:true},
					{field:'balance',title:'平台余额',width:100,sortable:true,hidden:false},
					{field:'addtime',title:'提现申请时间',width:150,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'isAudit',title:'审核状态',width:80,hidden:true,sortable:true,formatter: function(value,row,index){
						if (value==1){
							return "通过";
						}else if((value==0 || value==-1 ) && row.id != null ){
							return "待审核";
						} else if(row.id == null){
							return "";
						}
						return "未通过";
					}},
					{field:'firstAuditUser',title:'初审人',width:80,sortable:true,hidden:false},
					{field:'firstAuditTime',title:'初审时间',width:150,sortable:true,hidden:false},
					{field:'auditUser',title:'复审人',width:80,sortable:true,hidden:true},
					{field:'auditTime',title:'复审时间',width:150,hidden:true,sortable:true,formatter: function(value,row,index){
						if (validateIsNull(value)){
							return;
						}
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}},
					{field:'sourceStr',title:'来源网站',width:100,hidden:false,formatter:function(value,row,index){
						if(row.mobile!="合计"){
							return value;
						}
					}}
				]],
				onLoadSuccess:function(data){
					auditWithDraw.loadSuccess(data,initData.dataGridId);
					}
	}

$(document).ready(function(){
	gridParams.queryParams={search_EQ_status:21,search_EQ_isAudit:0,search_EQ_belowLine:0};
	//$("#"+initData.dataGridId).datagrid(gridParams);
	$('#withdrawalAuditTab').tabs({
	    onSelect:function(title,index){
	    	//alert(gridParams.columns[0][6].hidden);
	    	if (title == '待审核列表'){
	    		initData.dataGridId="auditData";
	    		gridParams.toolbar='#audittb';
	    		gridParams.columns[0][8].hidden=false;
	    		gridParams.columns[0][11].hidden=true;
	    		gridParams.columns[0][10].hidden=true;
	    		gridParams.columns[0][12].hidden=false;
	    		gridParams.columns[0][13].hidden=false;
	    		gridParams.frozenColumns=[[{field:'ck',checkbox:true}]];
	    		gridParams.queryParams={search_EQ_status:21,search_EQ_isAudit:0,search_EQ_belowLine:0};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '审核列表'){
	    		initData.dataGridId="hasAuditData";
	    		gridParams.toolbar='#hasAaudittb';
	    		gridParams.columns[0][8].hidden=true;
	    		gridParams.columns[0][12].hidden=false;
	    		gridParams.columns[0][10].hidden=false;
	    		gridParams.columns[0][13].hidden=false;
	    		gridParams.columns[0][14].hidden=false;
	    		gridParams.queryParams={search_GTE_isAudit:-1,search_EQ_belowLine:0,search_NQ_status:3};
	    		$("#"+initData.dataGridId).datagrid(gridParams);
	    	}
	    	
	    	if (title == '线下转账待审核列表'){
	    		$("#belowLineAuditData").datagrid("options").url=basepath+"admin/withdrawAudit/getDatas?type=auditList";
	    		$("#belowLineAuditData").datagrid("reload");
	    	}
	    	
	    	if (title == '线下转账已审核列表'){
	    		$("#belowLineHasAuditData").datagrid("options").url=basepath+"admin/withdrawAudit/getDatas?type=auditList";
	    		$("#belowLineHasAuditData").datagrid("reload");
	    	}
	    	
	    	if (title == '提现待审核【初审】'){
	    		
	    		$("#datagrid2").datagrid("options").url=basepath+"admin/withdrawAudit/listFirstAuditDrawData?type=firstAudit";
	    		$("#datagrid2").datagrid("reload");
	    	}
	    	if (title == '提现待审核【复审】'){
	    		$("#datagrid3").datagrid("options").url=basepath+"admin/withdrawAudit/listFirstAuditDrawData?type=reAudit";
	    		$("#datagrid3").datagrid("reload");
	    	}
	    	
	    }
	});
});

	
	

var auditWithDraw={
	list:{
		doSearch1:function(){
			$('#auditData').datagrid('load',{
				search_LIKE_mobile : $('#mobile1').val(),
				search_LIKE_bank : $('#bank1').val(),
				search_date_GTE_addtime:$("#startTime").val(),
				search_date_LTE_addtime:$("#endTime").val(),
				search_GTE_money:$("#startmoney1").val(),
				search_LTE_money:$("#endmoney1").val(),
				search_LIKE_tname:$("#tname").val(),
				search_EQ_status:21,
				search_EQ_isAudit:0
			});
		},
		doSearch2:function(){
			$('#hasAuditData').datagrid('load',{
				search_LIKE_mobile : $('#mobile2').val(),
				search_LIKE_bank : $('#bank2').val(),
				search_date_GTE_addtime:$("#startTime3").val(),
				search_date_LTE_addtime:$("#endTime3").val(),
				search_date_GTE_auditTime:$("#startTime4").val(),
				search_date_LTE_auditTime:$("#endTime4").val(),
				search_LIKE_tname:$("#tname2").val(),
				search_GTE_money:$("#startmoney2").val(),
				search_LTE_money:$("#endmoney2").val(),
//				search_GTE_isAudit:-1,
				search_EQ_belowLine:0,
				search_NQ_status:3,
				search_IN_isAudit:$('#auditStatus').combobox('getValue'),
				search_date_GTE_firstAuditTime:$("#firstStartTime").val(),
				search_date_LTE_firstAuditTime:$("#firstEndTime").val(),
				search_EQ_paymentChannel : $('#paymentChannel').combobox('getValue'),
				search_EQ_source : $('#source').combobox('getValue')
			});
		}
	},
	 firstauditMoney:function(){
		 var rows = $("#datagrid2").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("提示","请选择数据", 'info');
				return;
			}
			
			$.messager.confirm("确认提示","请确认您选择的记录是否通过审核？", function(result){
				if (result){
					ajaxPost({
						url : basepath + "admin/withdrawAudit/firstAudit",
						cache : false,
						async : false,
						data : {
							"id" :rows[0].id,
						},
						success : function(data) {
							if (data.success) {
								$("#datagrid2").datagrid('reload');
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
			});	
	},
	
	auditNotPass:function(type,datagridId){
		var rows = $("#"+datagridId).datagrid('getChecked');
		if (type==1){
			if (!rows || rows.length==0){
				eyWindow.walert("提示","请选择对应的数据记录", 'info');
				return;
			}
		}
		if (type==2){
			if (!rows || rows.length==0 || rows[0].id==null){
				eyWindow.walert("提示","请选择对应的数据记录", 'info');
				return;
			}
			
			var isAudit = rows[0].isAudit;
			if (isAudit==1){
				eyWindow.walert("提示","审核通过记录无审核原因!", 'info');
				return;
			}
		}
		
		baseUtils.openIframeWin(600, 300,'审核不通过原因','admin/withdrawAudit/auditNotPass?fromType='+type+'&id='+rows[0].id+"&datagridId="+datagridId);

	},
	lineAuditPass:function(){
		var rows = $("#belowLineAuditData").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		
		
		$.messager.confirm("确认提示","请确认您选择的记录是否通过审核？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/withdrawAudit/lineAuditPass",
					cache : false,
					async : false,
					data : {
						"id" :rows[0].id
					},
					success : function(data) {
						if (data.success) {
							$("#belowLineAuditData").datagrid('reload');
							eyWindow.walert("成功提示", data.message, 'info');
							return;
						}
						eyWindow.walert("错误提示", data.message, 'error');
					},
					error : function() {
						eyWindow.walert("错误提示", "系统异常", 'error');
					}
				});
			}});
	},

	reAuditMoney:function(){
		var rows = $("#datagrid3").datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		
		
		$.messager.confirm("确认提示","请确认您选择的记录是否通过审核？", function(result){
			if (result){
				ajaxPost({
					url : basepath + "admin/withdrawAudit/audit",
					cache : false,
					async : false,
					data : {
						"id" :rows[0].id,
					},
					success : function(data) {
						if (data.success) {
							$("#datagrid3").datagrid('reload');
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
		});
	},
	loadSuccess:function(data,datagridID){
		//alert($('#'+datagridID).closest('div.datagrid-wrap').html());
		if ($('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo')){
			$('#'+datagridID).closest('div.datagrid-wrap').find('.noDataInfo').remove();
		}
		
		if(data.total>0){
			$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').show();
	        return;
		}
		/*$('#'+datagridID).datagrid('appendRow',{
			username: '<div style="text-align:center;color:red">没有相关记录！</div>' }).datagrid('mergeCells', { index: 0, field:filedName, colspan: filedNum });*/
		 //隐藏分页导航条，这个需要熟悉datagrid的html结构，直接用jquery操作DOM对象，easyui datagrid没有提供相关方法隐藏导航条
		$('#'+datagridID).closest('div.datagrid-wrap').find('div.datagrid-pager').hide();
		$('#'+datagridID).closest('div.datagrid-wrap').append('<div id="noDataInfo" class="noDataInfo" style="text-align:center;color:red;padding:5px;">没有相关记录！</div>');
	},
	openFundDetail:function(datagridId){
		var rows = $("#"+datagridId).datagrid('getChecked');
		if (!rows || rows.length==0){
			eyWindow.walert("提示","请选择数据", 'info');
			return;
		}
		
		baseUtils.openIframeWin(800, 400,'资金明细','admin/withdrawAudit/getUserFundDetail?uid='+rows[0].uid);
	},
	saveOrUpdate:function(url,iframe,datagridId){
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
					$("#"+datagridId).datagrid('reload');
					if (iframe){
						parent.$('#addWin').window('close');
						parent.$('#'+datagridId).datagrid('reload');
						parent.eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
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