	//获取持仓均线的值
    function getPositionValue(){
    	var text=$("#commodity_title").text();
		var length=$(".tab_content .position0").length;
		var positionValue=0
		if(length!=0){
			for(var i=0;i<length;i++){
				var text1=$(".tab_content .position0").eq(i).text();
				if(text.indexOf(text1)>=0){
					positionValue=$(".tab_content .position3").eq(i).text();
				}
			}
		}
		return positionValue;
    }
    $(function(){
    	$(".carbon_time").on("click",function(){
				var HisQuoteType=$(this).attr("data");
				$(this).addClass("active");
				$(this).siblings('li').removeClass('active');
				if(HisQuoteType==0){
					$("#time").css({"z-index":"999","display":"block"});
					$("#Candlestick").css({"z-index":"998","display":"none"})
					$("#container1").css("z-index","997");
        					$("#container").css("z-index","998");
				}else if(HisQuoteType==2){
					      $("#container1").css("z-index","998");
        					$("#container").css("z-index","997");
				}else{
					$("#time").css({"z-index":"998","display":"none"});
					$("#Candlestick").css({"z-index":"999","display":"block"})
					$("#container1").css("z-index","997");
        			$("#container").css("z-index","998");
				}
				sendHistory(HisQuoteType);
			});
    })
