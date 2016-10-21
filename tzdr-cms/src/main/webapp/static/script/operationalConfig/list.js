	var gridParams={
				nowrap: true,
				autoRowHeight: false,
				striped: true,//True 就把行条纹化。（即奇偶行使用不同背景色）
				collapsible:true,
				url:'',
				rownumbers:true,
				toolbar:'#tb',
				pagination:true,
				fitColumns:true,// 自适应列宽
				queryParams:{search_EQ_deleted:false},
				frozenColumns:[[
		            {field:'ck',checkbox:true}
				]],
				columns:[[
				    {field:'id',hidden:true},
				    {field:'parentConfig',hidden:true},
					{field:'name',title:'名称',width:200,sortable:true,hidden:false},
				    {field:'linkUrl',title:'地址',width:200,sortable:true,hidden:false},
				    {field:'isEnable',title:'启用状态',width:200,sortable:true,hidden:false,formatter: function(value,row,index){
						if (value){
							return "启用";
						}
						return "禁用";
					}},
				    {field:'lanmuName',title:'栏目',hidden:true,width:200,sortable:true,formatter: function(value,row,index){
						if (row.parentConfig){
							return row.parentConfig.name;
						}
						return value;
					}},
				    {field:'isRelease',title:'发布状态',width:200,sortable:true,hidden:true,formatter: function(value,row,index){
						if (value){
							return "已发布";
						}
						return "未发布";
					}},
					{field:'summary',title:'摘要',width:200,sortable:true,hidden:true},
					{field:'keyWord',title:'关键字',width:200,sortable:true,hidden:true},
					{field:'valueType',title:'排序权重',width:100,sortable:true,hidden:true},
					{field:'isTop',title:'是否置顶',width:100,sortable:true,hidden:true,formatter: function(value,row,index){
						if (value){
							return "已置顶";
						}
						return "未置顶";
					}},
					{field:'releaseTime',title:'发布时间',width:180,sortable:true,hidden:true,formatter:function(value,row,index){
						 var date = new Date(value*1000);
						 var  Y = date.getFullYear() + '-';
						 var  M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
						 var  D = date.getDate() + ' ';
						 var  h = date.getHours() + ':';
						 var  m = date.getMinutes() + ':';
						 var  s = date.getSeconds(); 
						 return Y+M+D+h+m+s;
					}},
					{field:'createUser',title:'创建人',width:100,sortable:true},
				    {field:'createTime',title:'创建时间',width:180,sortable:true,formatter: function(value,row,index){
						return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss');
					}}
				]],
				onLoadSuccess:function(data){
					
				}
	}

$(document).ready(function(){
	var type = $("#configType").val();
	//1:友情链接 2：新闻栏目 3：新闻 4：banner 5：合作伙伴
	switch (Number(type)) {
		case 1:
			gridParams.url=basepath+'admin/config/friendlyLink/easyuiPage';
			gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:1};
			break;
		case 2:
			gridParams.url=basepath+'admin/config/newsColumn/easyuiPage';
			gridParams.columns[0][3].hidden=true;
			gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:2};
			break;
		case 3:
			gridParams.url=basepath+'admin/config/news/easyuiPage';
			gridParams.columns[0][0].title='ID';
			gridParams.columns[0][0].hidden=false;
			gridParams.columns[0][2].title='标题';
			gridParams.columns[0][3].hidden=true;
			gridParams.columns[0][4].hidden=true;
			gridParams.columns[0][5].hidden=false;
			gridParams.columns[0][6].hidden=false;
			gridParams.columns[0][7].hidden=false;
			gridParams.columns[0][8].hidden=false;
			gridParams.columns[0][10].hidden=false;
			gridParams.columns[0][11].hidden=false;
			gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:3,sort:'createTime',order:'desc'};
			break;
		case 4:
			gridParams.url=basepath+'admin/config/banner/easyuiPage';
			gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:4};
			gridParams.columns[0][9].hidden=false;
		break;
		case 5:
			gridParams.url=basepath+'admin/config/partners/easyuiPage';
			gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:5};
			gridParams.columns[0][3].hidden=true;
		break;
		default:
			break;
	}
	
	$("#edatagrid").datagrid(gridParams);
	$("#bannerTab").tabs({
		border:false,
	    onSelect:function(title){
	    	if(title=="配股宝banner"){
				gridParams.toolbar="#tb_pgb";
				gridParams.url=basepath+'admin/config/banner/easyuiPage';
				gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:8};
				gridParams.columns[0][9].hidden=false;
				$("#edatagrid_pgb").datagrid(gridParams);
			}else if(title=="维胜APP-banner"){
				gridParams.toolbar="#tb_tzdrApp";
				gridParams.url=basepath+'admin/config/banner/easyuiPage';
				gridParams.queryParams={search_EQ_deleted:false,search_EQ_type:9};
				gridParams.columns[0][9].hidden=false;
				$("#edatagrid_tzdrApp").datagrid(gridParams);
			}
	    }
	})
	loadProgram();
});
	
/**
 * 相关方法
 */	
var opConfig={
		checkedIds :[],
		subData:{ids:'',isRelease:"false",isEnable:"false",isTop:"false"},
		setParams:function(status,url){
			opConfig.subData.isEnable=status;
			opConfig.subData.isRelease=status;
			opConfig.subData.isTop=status;
			opConfig.submitInfo(url);
		},
		submitInfo:function(url){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("更新提示","请选择数据", 'info');
				return;
			}
			opConfig.checkedIds.length=0;
			for (var i=0;i<rows.length;i++)
			{
				opConfig.checkedIds.push(rows[i].id);
				opConfig.subData.ids=opConfig.checkedIds.join(",");
			}
			
			//启用 或 发布
			$.messager.confirm("确认提示","确认设置选中的数据？", function(result){
				if (result){
					ajaxPost({
						url : basepath + url,
						cache : false,
						async : false,
						data :opConfig.subData,
						success : function(data) {
							if (data.success) {
								 $("#edatagrid").datagrid("reload");
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
		doSearch:function(type,TabsId){
			var $title=$("#"+TabsId).tabs('getSelected').panel('options').title;
			if($title=="配股宝banner"){
				$('#edatagrid_pgb').datagrid('load',{
					search_LIKE_name : $('#name_pgb').val(),
					search_EQ_deleted:false,
					search_EQ_type:type
				});
			}else if($title =="维胜APP-banner"){
				$('#edatagrid_tzdrApp').datagrid('load',{
					search_LIKE_name : $('#name_tzdrApp').val(),
					search_EQ_deleted:false,
					search_EQ_type:type
				})
			}else{
				$('#edatagrid').datagrid('load',{
					search_LIKE_name : $('#name').val(),
					search_EQ_deleted:false,
					search_EQ_type:type
				});
			}
		},
		serach:function(){
				var dateTimeIndex = $("#releaseTime").val();
				var dataTime = new Date();
				var isDataTime = false;
				if(dateTimeIndex.length > 0){
					if(dateTimeIndex == 0){
						dataTime = new Date(dataTime.getTime() - 1*24*60*60*1000);
						isDataTime = true;
					}else if(dateTimeIndex == 1){
						dataTime = new Date(dataTime.getTime() - 3*24*60*60*1000);
						isDataTime = true;
					}else if(dateTimeIndex == 2){
						dataTime = new Date(dataTime.getTime() - 7*24*60*60*1000);
						isDataTime = true;
					}else if(dateTimeIndex == 3){
						dataTime.setMonth(dataTime.getMonth() - 1);
						isDataTime = true;
					}else if(dateTimeIndex == 4){
						dataTime.setMonth(dataTime.getMonth() - 12);
						isDataTime = true;
					}
				}
				var date = "";
				if(isDataTime == true){
					date = dataTime.getFullYear()+"-"+(dataTime.getMonth()+1)+"-"+dataTime.getDate()+" 00:00:00";
				}
				var i = 0 ;
				var param = {}
				param = {"search_LIKE_name":$('#name').val(),"search_EQ_deleted":false,"search_EQ_type":3};
				var isRelease = $("#isRelease").val();
				if(isRelease.length > 0){
					param["search_EQ_isRelease"] = isRelease;
				}
				if(date.length > 0){
					param["search_datetime_GT_releaseTime"]=date;
				}
				var program = $("#program_name").val();
				if(program.length > 0 && program != "请选择"){
					param["search_EQ_parentConfig.id"] = program;
				}
				console.log(param);
				$('#edatagrid').datagrid('load',param);
		}
}
/**
 * 获取栏目列表
 */
function loadProgram(){
	$.ajax({
		url:basepath + "admin/config/news/doGetProgram?type=2",
		type:"get",
		success:function(result){
			var data = result.data.data;
			var length = data.length;
			var html = "<option valule = ''>请选择</option>";
			for(var i = 0 ; i < length ; i++){
				var config = data[i];
				html+="<option value = "+config.id+">"+config.name+"</option>";
			}
			$("#program_name").html(html);
		}
	});
}
/**
 * 刷新网站banner缓存数据
 */
function refreshWebHomePageBanner(){
	ajaxPost({
		url : basepath + "admin/config/banner/refreshWebHomePageBanner",
		cache : false,
		async : false,
		success : function(data) {
			if (data.success) {
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


/**
 * 刷新PGBbanner缓存数据
 */
function refreshPgbHomePageBanner(){
	ajaxPost({
		url : basepath + "admin/config/banner/refreshPgbHomePageBanner",
		cache : false,
		async : false,
		success : function(data) {
			if (data.success) {
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