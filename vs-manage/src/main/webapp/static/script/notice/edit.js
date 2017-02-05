/**
 * 用于系统公告编辑
 */

var viewData = {
	fromId : 'addForm',
	showContentId: 'showContent'
}

// 初始展示的公告内容
var initContent;

//保存操作的状态(防止用户点击间隔太短)
var saveStatus=0;

var option = {
	save : function() {
		if(saveStatus==0){
			var data=$('#'+viewData.fromId).serialize();
			//替换换行符的编码
			//过滤"<"
			data=data.replace(/%3C/g,encodeURIComponent('&lt;'));
			//过滤">"
			data=data.replace(/%3E/g,encodeURIComponent('&gt;'));
			//过滤空格
			data=data.replace(/\+\+/g,encodeURIComponent('&emsp;'));
			//过滤换行
			data=data.replace(/\%0D\%0A/g,encodeURIComponent('<br/>'));
			$.ajax({
				type:'POST',
				url:$.easyui.path() + '/admin/notice/updateContent',
				data:data,
				timeout:2000,   
				success:function(data){   
					if(data.success){
						$.messager.alert("提示", data.message, 'info', function(){							
							window.parent.option.loadGrid();
							window.parent.option.closeWin();
						});
					}else{
						eyWindow.walert("错误提示", data.message, "error") 
					}
					saveStatus=0;
                },
                error:function(jqXHR, textStatus, errorThrown){   
                    if(textStatus=="timeout"){  
                    	eyWindow.walert("错误提示", "请求超时，请重试", "error")
                    }  
                    saveStatus=0;
                }   
			});
			saveStatus=1;
		}
	},
	cancel : function() {
//		目前只需关闭修改内容的窗体，不需要还原初始公告内容
//		$('#'+viewData.showContentId).val(initContent);
		window.parent.option.closeWin();
	}
}

$(function() {
	var $showTextArea=$('#'+viewData.showContentId);
	//初始化显示的公告内容
	initContent = $showTextArea.val().replace(/<br\/>/g,'\n');
	$showTextArea.val(initContent);
})