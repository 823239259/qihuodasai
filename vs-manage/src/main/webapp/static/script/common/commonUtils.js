/**
 * 金额格式化
 */
var moneyUtils={
		//格式话金额   
		format:function(value, n)//将数字转换成逗号分隔的样式,保留两位小数s:value,n:小数位数     
		{  
			if (null ==value || value.length==0){
				return 0;
			}
			s = Math.abs(value); 
		    n = n > 0 && n <= 20 ? n : 2;  
		    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
		    var l = s.split(".")[0].split("").reverse(),  
		    r = s.split(".")[1];  
		    t = "";  
		    for(i = 0; i < l.length; i ++ )  
		    {  
		    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
		    }  
		    
		    s =  t.split("").reverse().join("") + "." + r;  
		    if (value < 0){
		    	return "-"+s;
		    }
		    return s;
		} , 
		//还原金额  
		restore :function (s)  
		{  
		    return parseFloat(s.replace(/[^\d\.-]/g, ""));  
		}
}

/**
 * 登录方法
 */
function login(){
	if ($("#loginForm").form('validate') == false) {
		return;
	}
	$("#loginForm").submit();
}
/**
 * 校验输入框值的是否为空
 * 
 */
function  validateIsNull(elementValue)
{
	if(null == elementValue || typeof(elementValue)=="undefined"||elementValue==""||elementValue.length==0)
	{
		return true;
	}
	
	return false;
}

/**
 * 所有的ajax提交都通过此方法，返回json对象，统一错误处理
 * @param url 提交url，必填
 * @param dataType 数据类型，非必填
 * @param data 提交数据，非必填
 * @param async 同步标志，非必填
 * @param error ajax错误处理，非必填
 * @param success ajax回调函数，必填
 * @param beforeSend ajax开始发送前回调函数，参数function (XMLHttpRequest) ,非必填
 * @param complete ajax请求结束回调函数，无论成功与否都进入，参数function (XMLHttpRequest, textStatus),非必填
 * @param loading 加载遮罩参数，默认{tip:'加载数据中...', mask:true, ajaxLoadingImg:true}, 非必填
 * @param dateCols 指定需要转换格式的时间列数组集合, 如['birthday','endDate'], 非必填
 * @param isTip	是否需要遮罩效果,默认为[true]需要。
 */
var IS_AJAX="1";

function ajaxPost(config){
	config.isTip = (config.isTip) ? true : false;
	var data = config.data ? config.data : {};
	if ((typeof data=='string') && data.constructor==String){
		data = "ajax=1&"+data;
	}else
	{
		data["ajax"] = IS_AJAX;		
	}
	$.ajax({
		url: config.url,
		type: 'post',
		timeout:300000,
		cache:config.cache === true?true:false,
		dataType: config.dataType?config.dataType:'json',
		data: data,
		error: function(XMLHttpRequest, textStatus, errorThrown){
			eyWindow.closeProgress();
			if(config.error){
				config.error(XMLHttpRequest, textStatus, errorThrown);
			}
		},
		beforeSend: function(XMLHttpRequest){
			eyWindow.wprogress("等待提示","系统处理中...请稍等");
		},
		complete: config.complete? config.complete : null,
		success: function(responseData){
			eyWindow.closeProgress();
			config.success(responseData);
		}
	});
}

/**
 * 弹出框
 */
var eyWindow={
	walert:function(title,msg,type){
		$.messager.alert(title,msg,type);
	},
	wprogress:function(title,msg){
		$.messager.progress({
			title:title,
			msg:msg,
			text:''
		});
		setTimeout(eyWindow.closeProgress,180000);
	},
	closeProgress:function(){
		$.messager.progress('close');
	}
}

/**
 * 文件上传
 */
var uploadUtils={
	showErrorMsg:function(errorCode){
		if("u00000002"==errorCode){
			eyWindow.walert("错误提示","不允许上传这类型扩展名文件！","error");	
		}
		else if("u00000003"==errorCode){
			eyWindow.walert("错误提示","文件大小超过限制！","error");
		}
		else if("u00000004"==errorCode){
			eyWindow.walert("错误提示","文件名过长,请修改文件名！","error");
		}else{
			eyWindow.walert("错误提示","系统异常，上传失败！","error");
		}
	}	
} 
/**
 * datagrid 工具
 */
var datagridUtils={
		loadFormData:function(form) {
				var dataArray = $(form).serializeArray();
				var obj = new Object();
				$.each(dataArray,function(i,field){
					obj[field.name] = field.value;
				});
				return obj;
		},
		datagridQuery:function(gridId,formId){
			$('#'+gridId).datagrid('load',datagridUtils.loadFormData($("#"+formId)));
		},
		deleteData:function(rows,deleteUrl,datagridID){
			var ids = [];
			for (var i=0;i<rows.length;i++)
			{
				ids.push(rows[i].id);
			}
			ajaxPost({
				url : basepath + deleteUrl,
				cache : false,
				async : false,
				data : {
					"ids" : ids.join(",")
				},
				success : function(data) {
					$("#"+datagridID).datagrid('reload');
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
		},
		/**
		 * 加载成功后  判断是否有数据
		 * 没有数据则显示没有相关记录，去掉分页栏
		 * @param data 加载成功后的返回数据
		 * @param datagridID 表格id
		 */
		loadSuccess:function(data,datagridID){
			
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
		/**
		 * 校验是否选择了数据
		 * @param edatagrid
		 * @param msg
		 * @returns {Boolean}
		 */
		checkSelected:function(edatagrid,msg){
			var rows = $("#"+edatagrid).datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("提示",msg, 'info');
				return false;
			}
			return true;
		}
}

/**
 * 树形网格工具
 */
var treeGridUtils={
		loadSuccess:function(data){
			if ($("#noDataInfo")){
				$("#noDataInfo").remove();
			}
			if(data.length>0){
		        return;
			}
			$(".datagrid-view2").find('div.datagrid-body').append('<div id="noDataInfo" style="text-align:center;color:red;padding:5px;">没有相关记录！</div>');
	
		}
}
/**
 * 选项卡工具
 */
var tabUtils={
		isExist:function(tabId,title){
			if ($('#'+tabId).tabs('exists', title)){
				$('#'+tabId).tabs('select', title);
				return true;
			}
			return false;
		},
		closeAll:function(tabId){
			var tabs = $("#"+tabId).tabs("tabs");
			var length = tabs.length;
			for(var i = 0; i < length; i++) {
			    var onetab = tabs[i];
			    var title = onetab.panel('options').tab.text();
			    $("#"+tabId).tabs("close", title);
			} 
		},
		addNewTab :function(tabId,title,url,isCloseOther){
			if(isCloseOther){	
				tabUtils.closeAll(tabId);
			}else if(tabUtils.isExist(tabId,title)){
				$('#'+tabId).tabs('close', title);
			}
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+url+'" style="width:100%;height:100%;"></iframe>';
		    //添加一个选项卡面板
		    $('#'+tabId).tabs('add',{
			    title:title,
			    content:html,
			    closable:true,
			    border:false,
			   // href:null,
			    loadingMessage:'正在加载,请等待......'
		    });
		},
		isTopExist:function(tabId,title){
			var topJq = top.jQuery;
			if (topJq('#'+tabId).tabs('exists', title)){
				topJq('#'+tabId).tabs('select', title);
				return true;
			}
			return false;
		},
		/**
		 * 子页面中点击按钮 在父页面中创建新的tab 选项卡
		 * @param tabId
		 * @param title
		 * @param url
		 */
		addTopTab :function(tabId,title,url){
			//是否存在
			if (tabUtils.isTopExist(tabId,title)){
				return;
			}
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+url+'" style="width:100%;height:100%;"></iframe>';
		    //添加一个选项卡面板
			 var topJq = top.jQuery;
			 topJq('#'+tabId).tabs('add',{
			    title:title,
			    content:html,
			    closable:true,
			    border:false,
			   // href:null,
			    loadingMessage:'正在加载,请等待......'
		    });
		}
}

/**
 * 通用的新增  修改 、删除 
 * 使用这个工具之前必须保证：
 * 1、table id 为edatagrid
 * 2、window 弹出框id 为addWin
 */
var baseUtils={
		/**
		 * 通用 弹出 新增框
		 * @param width  弹出框宽度
		 * @param height 高度
		 * @param title 名称
		 * @param modelName  模块名称  如：user、org、role。。。。
		 */
		openAddwin:function(width,height,title,modelName){
			//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=add" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,href:basepath+'admin/'+modelName+'/edit?fromType=add'});
			$('#addWin').window('open');
		},
		
		/**
		 * 通用 弹出iframe 新增框
		 * @param width  弹出框宽度
		 * @param height 高度
		 * @param title 名称
		 * @param modelName  模块名称  如：user、org、role。。。。
		 */
		openIframeWin:function(width,height,title,url){
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+url+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({href:'',collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
			$('#addWin').window('open');
		},
		
		/**
		 * 通用 弹出iframe 新增框
		 * @param width  弹出框宽度
		 * @param height 高度
		 * @param title 名称
		 * @param modelName  模块名称  如：user、org、role。。。。
		 */
		openAddIframeWin:function(width,height,title,modelName){
			var maximizableValue=false;
			if ("config/news"==modelName){
				 maximizableValue=true;
			}
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=add" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:maximizableValue,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
			$('#addWin').window('open');
		},
		/**
		 * 通用弹出修改框
		 * @param width  弹出框宽度
		 * @param height 高度
		 * @param title 名称
		 * @param modelName  模块名称  如：user、org、role。。。。
		 */
		openEditwin:function(width,height,title,modelName){
			var rowData=$('#edatagrid').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要修改的行', 'info');
				return;
			}
			//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=edit&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:false,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,href:basepath+'admin/'+modelName+'/edit?fromType=edit&id='+rowData.id});
			$('#addWin').window('open');
		},
		
		/**
		 * 通用弹出iframe修改框
		 * @param width  弹出框宽度
		 * @param height 高度
		 * @param title 名称
		 * @param modelName  模块名称  如：user、org、role。。。。
		 */
		openEditIframeWin:function(width,height,title,modelName){
			var rowData=$('#edatagrid').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要修改的行', 'info');
				return;
			}
			
			var maximizableValue=false;
			if ("config/news"==modelName){ 
				 maximizableValue=true;
			}
			
			var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
			$('#addWin').window({collapsible:false,minimizable:false,maximizable:maximizableValue,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,content:html});
			$('#addWin').window('open');
		},
		/**
		 * 通用删除方法
		 * @param modelName
		 */
		deleteData:function(modelName){
			var rows = $("#edatagrid").datagrid('getChecked');
			if (!rows || rows.length==0){
				eyWindow.walert("删除提示","请选择要删除的行", 'info');
				return;
			}
			// 删除数据
			$.messager.confirm("确认提示","确认是否删除选中的数据？", function(result){
				if (result){
					datagridUtils.deleteData(rows,"admin/"+modelName+"/batchDelete","edatagrid");
				}
			});
		},
		saveOrUpdate:function(url,iframe){
			if ($("#addForm").form('validate') == false) {
				return;
			}
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : $("#addForm").serialize(),
				success : function(data) {
					if (data.success) {
						$('#addWin').window('close');
						$("#edatagrid").datagrid('reload');
						if (iframe){
							parent.$('#addWin').window('close');
							parent.$('#edatagrid').datagrid('reload');
							parent.eyWindow.walert("成功提示", data.message, 'info');
							return;
						}
						eyWindow.walert("成功提示", data.message, 'info');
						return;
					}
					eyWindow.walert("错误提示", data.message, 'error');
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					eyWindow.walert("错误提示", "系统异常，错误类型textStatus: "+textStatus+",异常对象errorThrown: "+errorThrown, 'error');
				}
			});
		},
		saveOrUpdate2:function(url,iframe){
			if ($("#addForm2").form('validate') == false) {
				return;
			}
			ajaxPost({
				url : basepath + url,
				cache : false,
				async : false,
				data : $("#addForm2").serialize(),
				success : function(data) {
					if (data.success) {
						$('#addWin2').window('close');
						$("#edatagrid2").datagrid('reload');
						if (iframe){
							parent.$('#addWin2').window('close');
							parent.$('#edatagrid2').datagrid('reload');
							parent.eyWindow.walert("成功提示", data.message, 'info');
							return;
						}
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