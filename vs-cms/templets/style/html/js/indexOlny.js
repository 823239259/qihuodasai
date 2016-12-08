/**
 * Created by 丽丽 on 2016/11/24.
 */

$(function(){
    $("#nav ul li a").eq(0).css("color","#ffb319");
    $("#nav ul li .Triangle").eq(0).css("display","block");
});
//初始化滚动条
window.onload= function () {
    $("#productList").panel({iWheelStep:32});
    $("#nav>ul>li>a").hover(function() {
        $(this).addClass("yellowFont");
    }, function() {
        $(this).removeClass("yellowFont");
    });
    $(".hoverBlue").hover(function() {
        $(this).addClass("blueFont");
    }, function() {
        $(this).removeClass("blueFont");
    });
    $("#RechargeTrade").mouseover(function () {
        $("#RechargeTrade img").attr("src","/templets/style/html/images/image/RechargeTrade .png");
    }).mouseout(function(){
        $("#RechargeTrade img").attr("src","/templets/style/html/images/image/RechargeTrade Hover.png");
    });

    $("#CooperativePartner ul li img").css({"width":"149px","height":"60px"})
    $("#newTitle ul li").click(function () {
        var index=$(this).index();
        $("#newTitle ul li").eq($(this).index()).addClass("newTitleActiveColor").siblings().removeClass("newTitleActiveColor");
        if(index==0){
            $("#TriangleIcon").css("left","45px");
            $("#realTimeNew").css("display","block");
            $("#relateInformation").css("display","none");
            $("#DynamicTrader").css("display","none");
        }else if(index==1){
            $("#TriangleIcon").css("left","151px");
            $("#realTimeNew").css("display","none");
            $("#relateInformation").css("display","block");
            $("#DynamicTrader").css("display","none");
        }else{
            $("#TriangleIcon").css("left","257px");
            $("#realTimeNew").css("display","none");
            $("#relateInformation").css("display","none");
            $("#DynamicTrader").css("display","block");
        }
    });
    $("#CooperativePartnerList ul li img").css({"width":"150px","height":"60px"});
    $("#CooperativePartnerList ul li:last").addClass("lastImage");
    $("#backDiv").click(function () {
        $("html,body").animate({scrollTop:0},500);
    });
};
var num=0;
$(document).ready(function(){
    $("#searchIcon").click(function () {
        if(num==0){
            $("#searchInput").css("display","inline-block");
            num=1;
        }else{
            $("#searchInput").css("display","none");
            num=0
        }
    });
    $("#productList ul li").click(function () {
        $("#productList ul li").eq($(this).index()).addClass("clickLiColor").siblings().removeClass("clickLiColor");
        //alert($(this).index());
    });
    $("#directSeedingList ul").on("click","li", function() {
        $("#directSeedingList ul li ").eq($(this).index()).addClass("directSeedingListAcitve").siblings().removeClass("directSeedingListAcitve");
    });
    // banner切换
    var num = $("#slide-box a").size();
    var i = 0;
    var theInt = null;
    $("#ad-slider a").eq(0).addClass("on");
    $("#slide-box a").eq(0).fadeIn(500);
    $("#ad-slider a").each(function (i) {
        $(this).click(function () {
            Change(i);
            HuanDeng(i);
        });
    });
    HuanDeng = function (i) {
        clearInterval(theInt);
        theInt = setInterval(function () {
            i++;
            if (i < num) {
                Change(i);
            } else {
                i = 0
                Change(i);
            }
        }, 5000);
    }
    HuanDeng(0);
    function Change(i) {
        $("#slide-box a").fadeOut(500);
        $("#slide-box a").eq(i).fadeIn(500);
        $("#ad-slider a").removeClass("on");
        $("#ad-slider a").eq(i).addClass("on");

    }
});
function GetDateStrDate(date) {
    var dd = new Date(date);
    dd.setDate(dd.getDate()+1);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;//获取当前月份的日期
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}
//获取财经日历的数据
$(function(){
    var url="/crawler/getCrawlerCalendar";
    var startDate=new Date().Format("yyyy-MM-dd");
    var endDate=GetDateStrDate(startDate,1);
    var params={
        pageIndex:0,
        startTime:startDate,
        endTime:endDate,
        size:5,
        type:"FD"
    };
    queryData(url,params,insertData);
});
var num=0;
function insertData(data){
    var srcY="/templets/style/html/images/image/yellowStart.png";
    var srcG="/templets/style/html/images/image/grayStart.png";
    for(var i=0;i<data.length;i++){
        var date= new Date(parseInt(data[i].timestamp) * 1000).Format("hh:mm");
        var star;
        if(data[i].importance==1){
            star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
        }else if(data[i].importance==2){
            star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/grayStart.png'>"
        }else if(data[i].importance==3){
            star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
        }else{
            star="<img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'><img src='/templets/style/html/images/image/yellowStart.png'>"
        }
        if(data[i].actual==null || data[i].actual=="null" || data[i].actual==""){
            data[i].actual="--";
        }
        if(data[i].forecast==null || data[i].forecast=="null" || data[i].forecast==""){
            data[i].forecast="--";
        }
        if(data[i].previous==null || data[i].previous=="null" || data[i].previous==""){
            data[i].previous="--";
        }
          $("#EconomicCalendarList").append("<ul class='calendar_time_Content'>"+
              "<li class='data'>"+date+"</li>"+
              "<li class='importance'>"+star+"</li>"+
              " <li class='countries'>"+data[i].country+"</li>"+
              "  <li class='incident'>"+data[i].title+"</li>"+
              " <li class='newValue'>"+data[i].actual+"</li>"+
              "<li class='expect'>"+data[i].forecast+"</li>"+
              "<li class='formerValue'>"+data[i].previous+"</li>"+
              " </ul>");
    }
    $(".calendar_time_Content li").css({"font-size":"14px"})
}
//获取7*24小时的数据
$(function () {
    var url="/crawler/getCrawlerByChannelLiveContent";
    var params={
        pageIndex:0,
        size: 11,
        channelset:1
    };
    queryData(url,params,insertDataSeeding);
});
function insertDataSeeding(dataAll){
    for(var i=0;i<dataAll.length;i++){
        var date=getLocalTime(dataAll[i].createdAt);
        $("#directSeedingList ul").append("<li>" +
            "" +  "<div class='directSeedingListContent'>"
            +  "<div class='directSeedingListContentTop'>"
            +  "<p class='directSeedingDate'>"+date+"</p>"
            +  "<p class='directSeedingTitle'><a  class='hoverBlue'>"+dataAll[i].liveTitle+"</a></p>"
            +  "</div><div class='directSeedingContent'>"
            + dataAll[i].liveContent + "</div></div></li>")
    }
    $("#directSeedingList ul li").eq(0).addClass("directSeedingListAcitve");
}
//公告滚动效果
$(document).ready(function(){
    $("#h_scroll").scrollQ({"line":2,"scrollNum":2,"scrollTime":2000});
});
$(function () {
    var  firstThree = $(".viewpointContentList ul ");
    firstThree.each(function(){
        var _this = $(this);
        for(var i=0;i<_this.find("li").length;i++){
            _this.find(".numberCode").eq(i).text("NO."+(i+1));
        }
        $(this).find(".numberCode").slice(0,3).css({
            "color":"#333",
            "background":"#ffb319"
        });
    });
});
$(function () {
    var  firstThree = $(".rightContent");
    firstThree.each(function(){
        var _this = $(this);
        for(var i=0;i<_this.find("li").length;i++){
            _this.find(".numberIcon").eq(i).text("0"+(i+1));
        }
    });
});
