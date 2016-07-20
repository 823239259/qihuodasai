$(document).ready(function() {
	
	//大标题切换
	$(".fr a").removeClass("dja");

	$(".tzde_help").addClass("dja");
	if(tab==""){
		$('.hp_siderbar').find("a").eq(0).addClass("on");
		
	}
	//标题切换
	$(".helpTitle").live("click",function(){
		var $this = $(this);
		$(".helpTitle").removeClass('on');
		$this.addClass('on');
		var data_type = $.trim($this.attr("data-type"));
		$(".hp_content").css({display: "none"});
		if(data_type == "configuration"){
			$(".configuration").css({display: ""});
			window.location.href=basepath+"help?tab=configuration&leftMenu=1";
		}else if(data_type == "heart"){
			$(".heart").css({display: ""});
		}else if(data_type == "demo"){
			$(".demo").css({display: ""});
		}else if(data_type == "rule"){
			$(".rule").css({display: ""});
			 window.location.href=basepath+"help?tab=rule&leftMenu=9";
		}else if(data_type == "safety"){
			$(".safety").css({display: ""});
			 window.location.href=basepath+"help?tab=safety&leftMenu=1";
		}else if(data_type == "software"){
			$(".software").css({display: ""});
			 window.location.href=basepath+"help?tab=software&leftMenu=1";
		}else{
			$(".newbie").css({display: ""});
			 window.location.href=basepath+"help?tab=newbie&leftMenu=1";
		}
	});
	 
	//股票融资-->左菜单切换
	 $('.configuration a').each(function() {
	        $(this).click(function() {
	        	/*$('.configuration a').removeClass('on');
	            $(this).addClass('on');*/
	            var value = $(this).attr('data');
	            /*$(".configuration").find('.hp_mainbar').hide();
	            $(".configuration").find('.hp_mbox'+value).show();*/
	            window.location.href=basepath+"help?tab=configuration&leftMenu="+value;
	        });
	    });
	
	//实盘申请-->左菜单切换
    $('.heart_siderbar a').each(function() {
        $(this).click(function() {
        	$('.heart_siderbar a').removeClass('on');
            $(this).addClass('on');
            var value = $(this).attr('data');
            $(".heart").find('.hp_mainbar').hide();
            $(".heart").find('.hp_mbox'+value).show();
        });
    });
    
    //操盘规则-->左菜单切换
	$('.rule_siderbar a').each(function() {
		$(this).click(function() {
		   /* $('.rule_siderbar a').removeClass('on');
		    $(this).addClass('on');*/
		    var value = $(this).attr('data');
		   /* $(".rule").find('.hp_mainbar').hide();
		    $(".rule").find('.hp_mbox'+value).show();*/
		    window.location.href=basepath+"help?tab=rule&leftMenu="+value;
		    });
	});
    
    //安全保障-->左菜单切换
    $('.safety_siderbar a').each(function() {
        $(this).click(function() {
           /* $('.safety_siderbar a').removeClass('on');
            $(this).addClass('on');*/
            var value = $(this).attr('data');
           /* $(".safety").find('.hp_mainbar').hide();
            $(".safety").find('.hp_mbox'+value).show();*/
            window.location.href=basepath+"help?tab=safety&leftMenu="+value;
        });
    });
    
    //交易软件下载-->左菜单切换
	$('.software_siderbar a').each(function() {
	    $(this).click(function() {
	      /*  $('.software_siderbar a').removeClass('on');
	        $(this).addClass('on');*/
	        var value = $(this).attr('data');
	        /*$(".software").find('.hp_mainbar').hide();
	        $(".software").find('.hp_mbox'+value).show();*/
	        window.location.href=basepath+"help?tab=software&leftMenu="+value;
	    });
	});
    
	//交易软件下载-->左菜单切换
	$('.newbie_siderbar a').each(function() {
	    $(this).click(function() {
	      /*  $('.software_siderbar a').removeClass('on');
	        $(this).addClass('on');*/
	        var value = $(this).attr('data');
	        /*$(".software").find('.hp_mainbar').hide();
	        $(".software").find('.hp_mbox'+value).show();*/
	        window.location.href=basepath+"help?tab=newbie&leftMenu="+value;
	    });
	});
});