$(function(){
    var currentDate = new Date();//获取当前时间
    var dataALL;//数据
    $("#clock").html(currentDate.getHours()+":"+currentDate.getMinutes()+":"+currentDate.getSeconds());
    var clock = setInterval(function(){
        var currentDate = new Date();//获取当前时间
        var 
        if(){
        	
        }
        $("#clock").html(currentDate.getHours()+":"+currentDate.getMinutes()+":"+currentDate.getSeconds());
    },1000);
    $(".quotation_title").click(function(){
        var _this = $(this);
        $(".quotation_title").removeClass("on").eq(_this.index()-1).addClass("on");
    });
    var url="/crawler/getCrawlerByChannelLiveContent";
    var params={
        pageIndex:0,
        size: 10,
        channelset:1
    };
    var num=1;
    queryData(url,params,insertData);
    $(".more").click(function () {
        console.log(dataALL.length);
        if(dataALL.length>=10){
            var params1={
                pageIndex:num,
                size: 10,
                channelset:1
            };
            queryData(url,params1,insertData);
            num++;
        }else{
            $(".more a").html("没有更多数据");
        }
    });
    function insertData(data){
        var data=data;
        dataALL=data;
        for(var i=0;i<data.length-1;i++){
            if("calendar-link".indexOf(data[i].liveContent) > 0){
             $(".calendar-link").css("display","none");
            }
            var date=new Date(parseInt(data[i].createdAt) * 1000).Format("hh:mm");
            var date1=new Date(parseInt(data[i].createdAt) * 1000).Format("yyyy-MM-dd");
            var  date2=new Date(parseInt(data[i+1].createdAt) * 1000).Format("yyyy-MM-dd");
            if(date1==date2){
                $("#insertNew").append(
                    "<div>"+
                    "<span class='icon_time'>"+date+"</span>"+
                    "<label class='title'>"+data[i].liveContent +"<label>"+
                    "<span class='operation'></span>"+
                    "</div>");
            }else{
                $("#insertNew").append( "<div>"+
                    "<label class='title'>"+ date2+"<label>"+
                    "</div>");
                $("#insertNew").append(
                    "<div>"+
                    "<span class='icon_time'>"+date+"</span>"+
                    "<label class='title'>"+data[i].liveContent +"<label>"+
                    "<span class='operation'></span>"+
                    "</div>");
            }
        }
        $(".title").css({" overflow": "hidden","min-height":"24px"});
    }
});