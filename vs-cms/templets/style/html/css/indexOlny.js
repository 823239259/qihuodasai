/**
 * Created by 丽丽 on 2016/11/24.
 */
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
    $("#immediatelyButton").mouseover(function () {
        $("#immediatelyButton img").attr("src","/templets/style/html/images/image/immediatelyBottonHover.png");
    }).mouseout(function(){
        $("#immediatelyButton img").attr("src","/templets/style/html/images/image/immediatelyBotton.png");
    });
    $("#WithdrawalsButton").mouseover(function () {
        $("#WithdrawalsButton img").attr("src","/templets/style/html/images/image/WithdrawalsHover.png");
    }).mouseout(function(){
        $("#WithdrawalsButton img").attr("src","/templets/style/html/images/image/Withdrawals.png");
    });
    $("#openAccountButton").mouseover(function () {
        $("#openAccountButton img").attr("src","/templets/style/html/images/image/openAccountBottomHover.png");
    }).mouseout(function(){
        $("#openAccountButton img").attr("src","/templets/style/html/images/image/openAccountBottom.png");
    });
    $("#RechargeButton").mouseover(function () {
        $("#RechargeButton img").attr("src","/templets/style/html/images/image/RechargeHover.png");
    }).mouseout(function(){
        $("#RechargeButton img").attr("src","/templets/style/html/images/image/Recharge.png");
    });
    $("#RechargeTrade").mouseover(function () {
        $("#RechargeTrade img").attr("src","/templets/style/html/images/image/RechargeTrade .png");
    }).mouseout(function(){
        $("#RechargeTrade img").attr("src","/templets/style/html/images/image/RechargeTrade Hover.png");
    });
    $("#QRCodeDiv").mouseover(function () {
        $("#QRCodeDiv img").attr("src","/templets/style/html/images/image/icon/2.png");
    }).mouseout(function(){
        $("#QRCodeDiv img").attr("src","/templets/style/html/images/image/icon/1.png");
    });
    $("#backDiv").mouseover(function () {
        $("#backDiv img").attr("src","/templets/style/html/images/image/icon/top1.png");
    }).mouseout(function(){
        $("#backDiv img").attr("src","/templets/style/html/images/image/icon/top.png");
    });
    $("#QQDiv").mouseover(function () {
        $("#QQDiv img").attr("src","/templets/style/html/images/image/icon/qq2.png");
    }).mouseout(function(){
        $("#QQDiv img").attr("src","/templets/style/html/images/image/icon/qq.png");
    });
    $("#writePenDiv").mouseover(function () {
        $("#writePenDiv img").attr("src","/templets/style/html/images/image/icon/pen2.png");
    }).mouseout(function(){
        $("#writePenDiv img").attr("src","/templets/style/html/images/image/icon/pen.png");
    });

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
//获取财经日历的数据
$(function(){
    $.ajax({
        type:"get",
        url:"http://192.168.2.174/tzdr-web/crawler/getCrawlerCalendar",
        async:true,
        dataType:'json',//服务器返回json格式数据
        data:{
            pageIndex:0,
            size: 5
        },
        success: function (res) {
            console.log(res);
            var dataAll=res.data.data;
            //dataAll=[{
            //    timestamp:"14:50",
            //    title:"哈哈哈",
            //    description:"这是测试",
            //    importance:1,
            //    country:4545
            //}]
            var srcY="/templets/style/html/images/image/yellowStart.png";
            var srcG="/templets/style/html/images/image/grayStart.png";
            for(var i=0;i<dataAll.length;i++){
                //if(dataAll[i].importance==1){
                    $("#EconomicCalendarList").append(
                        "<ul class='calendar_time_title'>"+
                        "<li class='data'>" +dataAll[i].timestamp+"</li>"+
                        "<li class='importance'><img src="+srcY+"><img src='"+srcY+"'><img src='"+srcG+"'></li>"+
                        "<li class='countries'>" +dataAll[i].country+"</li>"+
                        "<li class='incident'>" +dataAll[i].title+"</li>"+
                        "<li class='newValue'>" +dataAll[i].country+"</li>"+
                        "<li class='expect'>" +dataAll[i].country+"</li>"+
                        "<li class='formerValue'>" +dataAll[i].country+"</li>"+
                        "</ul>"
                    );
                //}
            }
        },error: function (res) {
            console.log(res)
        }
    })
});
//获取7*24小时的数据
$(function () {
    $.ajax({
        url:"http://192.168.2.174/tzdr-web/crawler/getCrawler",
        type:"get",
        async:true,
        dataType:"json",
        data:{
            pageIndex:0,
            size: 11
        },
        success: function (res) {
            console.log(res);
            var dataAll=res.data.data;
            for(var i=0;i<dataAll.length;i++){
                $("#directSeedingList ul").append("<li>" +
                    "" +  "<div class='directSeedingListContent'>"
                    +  "<div class='directSeedingListContentTop'>"
                    +  "<p class='directSeedingDate'>"+dataAll[i].liveCreatetime+"</p>"
                    +  "<p class='directSeedingTitle'><a  class='hoverBlue'>"+dataAll[i].liveTitle+"</a></p>"
                    +  "</div><div class='directSeedingContent'>"
                    + dataAll[i].liveTitle + "</div></div></li>")
            }
            $("#directSeedingList ul li").eq(0).addClass("directSeedingListAcitve");
        },
        error: function (res) {

        }
    })
});
//公告滚动效果
$(document).ready(function(){
    $("#h_scroll").scrollQ({"line":2,"scrollNum":2,"scrollTime":2000});
});
