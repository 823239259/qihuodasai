$(document).ready(function(){
	reloadSel();
	

});



function saveCss(){
	$("#channerl1").css({width : "99%"});
	$("#channerl2").css({width : "99%"});
	$("#channerl3").css({width : "99%"});
}

/**
 * 渠道1联动
 */
function getChannelSel1(){
	var na = $('#channerl1').find("option:selected").text();
	if(na != '所有渠道'){
		$.post(basepath+'/admin/channelPromotion/typeList',{'type':2,'name':na},function(data){
			var list=data.obj;
			$("#channerl2").empty();
			saveCss();
			$("#channerl2").append("<option value=''>所有渠道</option>");
			for(var i=0;i<list.length;i++){
				if(list[i] != null && list[i] != ""){
					$("#channerl2").append("<option value='"+list[i]+"'>"+list[i]+"</option>");
				}
			}
		});
	}else{
		$.post(basepath+'/admin/channelPromotion/getMap',function(data){
			var list2=data.obj.two;
			$("#channerl2").empty();
			var list3=data.obj.three;
			$("#channerl3").empty();
			saveCss();
			$("#channerl2").append("<option value=''>所有渠道</option>");
			$("#channerl3").append("<option value=''>所有渠道</option>");
			for(var i=0;i<list2.length;i++){
				if(list2[i] != null && list2[i] != ""){
					$("#channerl2").append("<option value='"+list2[i]+"'>"+list2[i]+"</option>");
				}
			}
			for(var i=0;i<list3.length;i++){
				if(list3[i] != null && list3[i] != ""){
					$("#channerl3").append("<option value='"+list3[i]+"'>"+list3[i]+"</option>");
				}
			}
		});
	}
}

/**
 * 渠道2联动
 */
function getChannelSel2(){
	var na1 = $('#channerl1').find("option:selected").text();
	var na2 = $('#channerl2').find("option:selected").text();
	var na = na1 + ',' + na2;
	if( na2 != '所有渠道' ){
		$.post(basepath+'/admin/channelPromotion/typeList',{'type':3,'name':na},function(data){
			var list=data.obj;
			$("#channerl3").empty();
			saveCss();
			$("#channerl3").append("<option value=''>所有渠道</option>");
			for(var i=0;i<list.length;i++){
				if(list[i] != null && list[i] != ""){
					$("#channerl3").append("<option value='"+list[i]+"'>"+list[i]+"</option>");
				}
			}
		});
	}else{
		$.post(basepath+'/admin/channelPromotion/getMap',function(data){
			var list3=data.obj.three;
			$("#channerl3").empty();
			saveCss();
			$("#channerl3").append("<option value=''>所有渠道</option>");
			for(var i=0;i<list3.length;i++){
				if(list3[i] != null && list3[i] != ""){
					$("#channerl3").append("<option value='"+list3[i]+"'>"+list3[i]+"</option>");
				}
			}
		});
	}
	
}


function reloadSel() {
	/**
	 * 初始化一级渠道
	 */
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		//alert(data.obj.length);
		var list1=data.obj.one;
		$("#channerl1").empty();
		$("#channerl1").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list1.length;i++){
			if(list1[i] != null && list1[i] != ""){
				$("#channerl1").append("<option value='"+list1[i]+"'>"+list1[i]+"</option>");
			}
		}
	});
	/**
	 * 初始化二级渠道
	 */
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		var list2=data.obj.two;
		$("#channerl2").empty();
		$("#channerl2").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list2.length;i++){
			if(list2[i] != null && list2[i] != ""){
				$("#channerl2").append("<option value='"+list2[i]+"'>"+list2[i]+"</option>");
			}
		}
	});
	/**
	 * 初始化三级渠道
	 */
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		var list3=data.obj.three;
		$("#channerl3").empty();
		$("#channerl3").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list3.length;i++){
			if(list3[i] != null && list3[i] != ""){
				$("#channerl3").append("<option value='"+list3[i]+"'>"+list3[i]+"</option>");
			}
		}
	});
	/**
	 * addWindowInit
	 */
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		var list=data.obj.one;
		$("#addSe1").empty();
		$("#addSe1").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list.length;i++){
			if(list[i] != null && list[i] != ""){
				$("#addSe1").append("<option value='"+list[i]+"'>"+list[i]+"</option>");
			}
		}
	});
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		var list=data.obj.two;
		$("#addSe2").empty();
		$("#addSe2").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list.length;i++){
			if(list[i] != null && list[i] != ""){
				$("#addSe2").append("<option value='"+list[i]+"'>"+list[i]+"</option>");
			}
		}
	});
	$.post(basepath+'/admin/channelPromotion/getMap',function(data){
		var list=data.obj.three;
		$("#addSe3").empty();
		$("#addSe3").append("<option value=''>所有渠道</option>");
		for(var i=0;i<list.length;i++){
			if(list[i] != null && list[i] != ""){
				$("#addSe3").append("<option value='"+list[i]+"'>"+list[i]+"</option>");
			}
		}
	});
}

/**
 * 打开添加窗口
 */
function addChannelWindowOpen(){
	$("#addChannel").window("open");
	$("#addChannel").show();
	$("#addSe3").val($("#type_Three_Title").val());
	$("#addSe2").val($("#type_Two_Title").val());
	$("#addSe1").val($("#type_One_Title").val());
	$("#addUk").val("");
	$("#addPm").val("");
}


/**
 * 关闭添加窗口
 */
function addChannelClose(){
	$("#addChannel").window("close");
	$("#addChannel").hide();
}


/**
 * 选中赋值
 */
function giveValue(id){
	var text = $(id).find("option:selected").text();
	var inp = $(id).parent().prev().children();
	if(text!="所有渠道"){
		inp.val(text);
	}else{
		inp.val("");
	}
}

/**
 * 添加
 */
function addChannelSave() {
	$.post(basepath+'/admin/channelPromotion/create',$('#voForm').serialize(),function(data){
		Check.messageBox("提示",data.message);
		if(data.success){
			addChannelClose();
			reloadSel();
			$('#dg003').datagrid('reload');
		}
	});
};

/**
 * 删除
 */
function deleteChannel(){
	var nodeId = $("#dg003").datagrid("getSelected").id;
	$.messager.confirm("提示", "是否确认删除条记录?", function (r) {
		if (r) {
			eyWindow.wprogress("系统提示","系统处理中,请稍候...");
			$.post(basepath+'/admin/channelPromotion/deleteChannel',{'id':nodeId},function(data){
				if(data.success){
					eyWindow.closeProgress();
					Check.messageBox("提示",data.message);
					$("#dg003").datagrid("reload");
					reloadSel();
				}else{
					Check.messageBox("提示",data.message,"error");
				}
			});
		}
	});
	
}

function reset(){
	reloadSel();
	$("#uk").val("");
	$("#pm").val("");
}






