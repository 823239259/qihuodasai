var main={
		openUpdatePwdWin:function(){
			//var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/user/edit?fromType=add" style="width:100%;height:100%;"></iframe>';
			$('#updatePwdWin').window({collapsible:false,minimizable:false,maximizable:false,width:500,height:230,title:'修改密码',iconCls:'icon-edit',closed:true,modal:true});
			$('#updatePwdWin').window('open');
		},
		refreshCache:function(){
			ajaxPost({
				url : basepath + "admin/cache/init",
				cache : false,
				async : false,
				data : {},
				success : function(data) {
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
		updatePasswod:function(){
			if ($("#updateForm").form('validate') == false) {
				return;
			}
			
			ajaxPost({
				url : basepath + "admin/updatePasswod",
				cache : false,
				async : false,
				data : {
					"id" : $("#userid").val(),
					"password" : $("#password").val(),
					"newPasswod" : $("#password1").val()
				},
				success : function(data) {
					//$('#updatePwdWin').window('close');
					if (data.success) {
						eyWindow.walert("成功提示", data.message+",3秒后将跳转到登录页面", 'info');
						setTimeout(function(){
							window.location.href=basepath+"admin/login";
						}, 3000);
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