$(document).ready(function(){
	$("#oAccount").find("a").removeClass("on");
	//设置当前停留顶菜单位置
	$(".navlist li a").removeClass("on");
	$("#nav_my").addClass("on");
	//设置当前停留左菜单位置
	$("#toMessage").parent().addClass("on");
	
	var isOut = false; 
	$('.uc_m_down').click(function(event) {
		event.stopPropagation();
		$(".uc_m_select").show();
	});

	$(document).click(function(){
		if(isOut ==  false) {
			$(".uc_m_select").hide();
		}		
	});

	$(".uc_m_select a").each(function(){
		$(this).click(function() {			
			var value = $(this).html();
			$('.uc_m_ip').val(value);
			$('.uc_m_ip').attr("data-id",$(this).attr("data-id"));
			$('.uc_m_select').hide();
		});
	});
	
	//提交留言信息
	$(".msbtn").on("click",function(){
		var type = $.trim($('.uc_m_ip').val());
		var content = $.trim($(".content").find("textarea").val()).replace(/\</gi,'&lt;').replace(/\>/gi,'&gt;');
		
		if(type == null || type.length <= 0){
			showMsgDialog("提示","请选择留言类型");
			return ;
		}else if(content == null || content.length <= 0){
			showMsgDialog("提示","请填写留言信息");
			return ;
		}
		$.post(basepath+"/message/save_message",{type:type,content:content,ajax:1},function(data){  //获取二级数据(城市)
			if(data.success){
				//刷新列表信息
				messageList();
				$(".content").find("textarea").val("");
			}else{
				showMsgDialog("提示","系统错误......");
			}
		},"json"); 
	});
	
	//刷新留言列表信息
	function messageList(){
		$.post(basepath+"/message/query_message_list",{ajax:1},function(data){  
			if(data.success){
				$(".messageList").empty();
				var dynamicData = "";
				$(data.data.messageList).each(function(){
					var recontent = this.recontent == null || this.recontent.length < 0 ? "":this.recontent;
					dynamicData += "<div class='uc_ms'>";
					dynamicData += "<p class='uc_mstitle'><em>"+this.type+"</em><a href='javascript:void(0)' data-id='"+this.id+"' class='delMessage'>删除</a><i>"+getFormatDateByLong(this.addtime,"yyyy-MM-dd")+"</i></p>";
					dynamicData += "<ul class='uc_mslist'><ol><li><i class='uc_mscl'>内容：</i><p>"+this.content+"</p></li><li><i>回复：</i><p>"+recontent+"</p></li></ol></ul>";
					dynamicData += "</div>";
				});
				$(".messageList").html(dynamicData);
			}else{
				showMsgDialog("提示","系统错误......");
			}
		},"json"); 
	}
	
	//删除留言信息
	$(".delMessage").live("click",function(){
		var $this = $(this);
		var id = $.trim($this.attr("data-id"));
		$.post(basepath+"/message/delete_message/"+id,{ajax:1},function(data){  //获取二级数据(城市)
			if(data.success){
				//刷新列表信息
				messageList();
			}else{
				showMsgDialog("提示","系统错误......");
			}
		},"json"); 
	});
});