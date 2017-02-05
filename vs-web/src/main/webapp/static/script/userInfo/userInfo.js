$(document).ready(function(){
	$("#oAccount").find("a").removeClass("on");
	//设置当前停留顶菜单位置
	$(".navlist li a").removeClass("on");
	$("#nav_my").addClass("on");
	
	//设置当前停留左菜单位置
	$("#toUserInfo").parent().addClass("on");
	
	var isOut = false;
	var isOut2 = false;
	var isOut3 = false;
	var isOut4 = false;
	var isOut5 = false;
	var isOut6 = false;
	
	addClickEvent();
	
	function addClickEvent() {
		$(".uc_i_ippov").click(function(event){
			 event.stopPropagation();
			 $('.uc_i_option').hide();
			 $(".uc_i_pov").show();
		 });

		$(".uc_i_ipcity").click(function(event){
			event.stopPropagation();
			$('.uc_i_option').hide();
			$(".uc_i_city").show();
		});

		 $(".uc_i_ipedu").click(function(event){
		 	event.stopPropagation();
		 	$('.uc_i_option').hide();
			$(".uc_i_edu").show();
		 }); 


		 $(".uc_i_ipids").click(function(event){
		 	event.stopPropagation();
		 	$('.uc_i_option').hide();
			$(".uc_i_ids").show();
		 });


		 $(".uc_i_ipoff").click(function(event){
		 	event.stopPropagation();
		 	$('.uc_i_option').hide();
			$(".uc_i_off").show();
		 });

		 
		 $(".uc_i_ipicm").click(function(event){
		 	event.stopPropagation();
		 	$('.uc_i_option').hide();
			$(".uc_i_income").show();
		 });

		$(document).click(function(){
				if(isOut ==  false) {
					$(".uc_i_pov").hide();				
				}
				if(isOut2 == false) {
					$('.uc_i_city').hide();
				}
				if(isOut3 == false) {
					$('.uc_i_edu').hide();
				}
				if(isOut4 == false) {
					$('.uc_i_ids').hide();
				}
				if(isOut5 == false) {
					$('.uc_i_off').hide();
				}
				if(isOut6 == false) {
					$('.uc_i_income').hide();
				}
		});
		
		$(".uc_i_pov a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_slpov").val(value);
				$(".uc_i_slpov").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_pov").hide();			
				$('.uc_i_slpov').css('color', '#666');
				var dynamicData = null;
				$(".uc_i_slcity").val("请选择城市");
				$(".uc_i_slcity").attr("data-id",'-1');
				dynamicData = "<a href='javascript:void(0);' data-id='-1'>请选择城市</a>";
				if(value != -1){
					$.post(basepath+"/queryareas/"+$(this).attr("data-id"),{ajax:1},function(data){  //获取二级数据(城市)
						if(data.success){
							$(data.data.areaList).each(function(){
								dynamicData = dynamicData+"<a href='javascript:void(0);' data-id='"+this.id+"'>"+this.title+"</a>";
							});
						}else{
							showMsgDialog("提示","系统错误......");
						}
						 $(".uc_i_city").html(dynamicData);
					},"json"); 
				}
				
			});
		});	
		
		/* $(".uc_i_city a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_slcity").val(value);
				$(".uc_i_slcity").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_city").hide();
				$('.uc_i_slcity').css('color', '#666');
				
			});
		 });*/	
		 
		 $(".uc_i_city a").live("click",function(){
			 var value = $(this).html();
				$(".uc_i_slcity").val(value);
				$(".uc_i_slcity").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_city").hide();
				$('.uc_i_slcity').css('color', '#666');
		 })

		 $(".uc_i_edu a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_sledu").val(value);
				$(".uc_i_sledu").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_edu").hide();
				$('.uc_i_sledu').css('color', '#666');
			});
		 });
		  $(".uc_i_ids a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_slids").val(value);
				$(".uc_i_slids").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_ids").hide();
				$('.uc_i_slids').css('color', '#666');
			});
		 });

		 $(".uc_i_off a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_sloff").val(value);
				$(".uc_i_sloff").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_off").hide();
				$('.uc_i_sloff').css('color', '#666');
			});
		 });


		 $(".uc_i_income a").each(function(){
			$(this).click(function(){
				var value = $(this).html();
				$(".uc_i_slicm").val(value);
				$(".uc_i_slicm").attr("data-id",$(this).attr("data-id"));
				$(".uc_i_income").hide();
				$('.uc_i_slicm').css('color', '#666');
			});
		 });
		
	};
	
	//清除地址文本框
	$(".cleanAddress").on("click",function(){
		$(".address").val("");
	});
	
	//更新用户信息
	$(".upUserInfo").on("click",function(){
		var $this = $(this);
		var id = $(".userInfoId").val();
		var marriage = $('input:radio[name=marriage]:checked').attr("data-value");
		var province = $('.uc_i_slpov').attr("data-id");
		province = province == -1 ? null :province;
		var city= $('.uc_i_slcity').attr("data-id");
		city = city == -1 ? null :city;
		var address = $.trim($(".address").val());
		if($(this).attr("status") == "true"){
			$("#cancel").css({display:"none"});
			$this.attr("status",false);
			$this.text("正在编辑中...");
			if(address != null && address.length > 0){
				if(province == -1 || province == null){
					$this.attr("status",true);
					$this.text("修改信息");
					$("#cancel").css({display:"block"});
					showMsgDialog("提示","请选择省市");
					return;
				}else if(city == -1 || city == null){
					$this.attr("status",true);
					$this.text("修改信息");
					$("#cancel").css({display:"block"});
					showMsgDialog("提示","请选择城市");
					return;
				}
			}
			var education = $('.uc_i_sledu').attr("data-id");
			education = education == -1 ? null :education;
			var industry = $('.uc_i_slids').attr("data-id");
			industry = industry == -1 ? null :industry;
			var position = $('.uc_i_sloff').attr("data-id");
			position = position == -1 ? null :position;
			var income = $('.uc_i_slicm').attr("data-id");
			income = income == -1 ? null :income;
			var data = {id:id,marriage:marriage,province:province,city:city,address:address,education:education,industry:industry,position:position,income:income,ajax:1};
			$.post(basepath+"/userinfo/update_info",data,function(data){  //获取二级数据(城市)
				if(data.success){
					window.location.href=basepath+"userinfo/info";
				}else{
					$("#cancel").css({display:"block"});
					showMsgDialog("提示","系统错误......");
				}
			},"json");
		}
	});
	
});