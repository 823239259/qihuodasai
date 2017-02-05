var KXHL={
	closeWindow:function (jqueryFind,callback) {
		$(".tp_flmask").hide();
		$(jqueryFind).hide();
		if(callback){
			try {
				callback(this);
			}catch (e){}
		}
	},openWindow:function (jqueryFind,callback) {
		$(".tp_flmask").show();
		$(jqueryFind).show();
		if(callback){
			try {
				callback(this);
			}catch (e){}
		}
	}
};

$(document).ready(function() {
	$("#getkxhlPrize").on("click",function(){
		if(!isLoginSSO){
			window.location.href=basepath+"/tokxhlSSO"; 
			return;
		}
		var activityTimeStart = $('#activityTimeStart').val();  //开始时间
		var activityTimeEnd = $('#activityTimeEnd').val();      //开始时间
		var $this = $(this);
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$.post(basepath+"sifactivites/getkxhlPrize",{ajax:1},function(data){
				$this.attr("status",true);
				if(data.success){
					if(data.data.reusltCode == -100 || data.data.reusltCode == -200 || data.data.reusltCode == -300){
						showMsgDialog("提示","活动时间为交易日的："+activityTimeStart+"到"+activityTimeEnd);
						return;
					}else if(data.data.reusltCode == -400){
						showMsgDialog("提示","非交易日不能进行开箱");
						return;
					}else if(data.data.reusltCode == -1){
						$("#unpackingNum").html(0);
						showMsgDialog("提示","您的抽奖次数为0，不能进行抽奖！");
						return;
					}else if(data.data.reusltCode == -2){
						$("#unpackingNum").html(0);
						showMsgDialog("提示","您的抽奖次数已使用完，不能再进行抽奖！");
						return;
					}else if(data.data.reusltCode == -3){
						showMsgDialog("提示","抱歉！奖品已经被抽完！");
						return;
					}else{
						var myboxlistTitle = $("#myboxlistTitle");
						var ol="<ol>";
							ol+="<li>"+data.data.activityKudoWebVo.kudoGetTime+"</li>";	
							ol+="<li>"+data.data.activityKudoWebVo.kudoName+"</li>";	
							ol+="<li class='last'>"+data.data.activityKudoWebVo.kudoStatusStr+"</li>";	
							ol+="</ol>";
						myboxlistTitle.after(ol);
						$("#unpackingNum").html(data.data.unpackingNum);
						$("#myprize").find("p").html(data.data.activityKudoWebVo.kudoName);
						KXHL.openWindow("#myprize",null);
					}
				}else{
					showMsgDialog("提示","系统繁忙，请稍候重试......");
				}
			},"json");
		}
	});
	
	 var oDiv = document.getElementById("h_scroll");
	    var oUl = oDiv.getElementsByTagName("ul")[0];
	    var oLis = oDiv.getElementsByTagName("li");
	    var timer = null;
	     
	    oUl.innerHTML += oUl.innerHTML;
	    oUl.style.width = ( oLis[0].offsetWidth * oLis.length ) + "px";
	     
	    function slide (){        //使用scrollLeft实现
	        oDiv.scrollLeft = (oDiv.scrollLeft + 5);
	        timer = setTimeout(arguments.callee,100);
	        if ( oDiv.scrollLeft == (oUl.offsetWidth-860)){
	            oDiv.scrollLeft = 0 ;
	        }
	    }
	     
	    timer = setTimeout(slide,100);
	    oUl.onmouseover = function(){
	         
	        clearTimeout(timer);
	    }
	    oUl.onmouseout = function(){
	        timer = setTimeout(slide,100);
	    }

});