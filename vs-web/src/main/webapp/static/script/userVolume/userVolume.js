$(document).ready(function(){
	$("#oAccount").find("a").removeClass("on");
	//设置当前停留顶菜单位置
	$(".navlist li a").removeClass("on");
	$("#nav_my").addClass("on");
	
	//设置当前停留左菜单位置
	$("#userVolume").parent().addClass("on");
	
	//切换tab
	$("#ticketTab").live("click",function(){
		var $this = $(this);
		$this.parent().find("a").removeClass("on");
		$this.find("a").addClass("on");
		var volumeType = $this.find("a").attr("data-volumeType");
		var data = {ajax:1};
		if(volumeType != 0){
			data = {volumeType:volumeType,ajax:1}
		}
		$.post(basepath+"uservolume/getvolumelist",data,function(data){
			if(data.success){
				$("#ticketList").empty();
				var dynamicData = "";
				$(data.obj).each(function(){
					var isUseCss = "";
					var isUseSpan = "";
					if(this.useState == 1 || this.useState==2){
						isUseCss = "uc_tcbg";
						if(this.useState == 1){
							isUseSpan = "<span class='uc_tcuse'></span>";
						}else{
							isUseSpan = "<span class='uc_tctime'></span>";
						}
					}
					dynamicData +="<div class='uc_tcone "+isUseCss+"'>";
					dynamicData +="<h3><i>"+$.formatMoney(this.money,0)+"</i><em>"+this.useTypeName+"</em></h3>";
					dynamicData +="<p>编号："+this.code+"</p>";
					dynamicData +="<p>获得日期： "+getFormatDateByLong(this.timeValueOfGetVolume,"yyyy-MM-dd")+"</p>"
					if(this.useState == 1){
						dynamicData +="<p>使用日期： "+getFormatDateByLong(this.userDateValue,"yyyy-MM-dd")+"</p>"
					}else{
						dynamicData +="<p>有效日期： "+getFormatDateByLong(this.endDateValue,"yyyy-MM-dd")+"</p>"
					}
					dynamicData +="<p>备注："+this.name+"</p>"
					dynamicData +=isUseSpan;
					dynamicData +="</div>";
					$("#ticketList").html(dynamicData);
				});
			}else if(!data.success && data.errorCode=='errorCode'){
				showMsgDialog("提示","系统繁忙，请重试......");
			}
		},"json");
	});
})