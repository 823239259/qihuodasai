var hkStockParamsOptions = {
	update:function(url){
		var $form=$("#addForm");
		if ($form.form('validate') == false) {
			return;
		}
		ajaxPost({
			url : basepath + url,
			cache : false,
			async : false,
			data : $form.serialize(),
			success : function(data) {
				if (data.success) {
					$.messager.alert("成功提示",data.message, 'info', function(){
						var tab=parent.$("#tabPanel").tabs('getSelected');  //获取当前的选项卡对象
						tab.panel('refresh'); //刷新选项卡
					});
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