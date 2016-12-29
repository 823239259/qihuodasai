/**
 * Created by 丽丽 on 2016/10/21.
 */
var url=window.location.href;
var urlHost = url;
var param = initLoadParam("o");
var check = initLoadParam("check");
 var funUrl="http://www.vs.com/";
$(document).ready(function () {
        //$("#login").on("click",function(){
        //    $("#loginDivContent").css("display","block");
        //});
        //$("#close").on("click",function(){
        //    $("#loginDivContent").css("display","none");
        //});
         var urls = url.split("?");
	     if(urls.length > 0){
	         urlHost = urls[0];
	     }
        $("#login").bind("click",function(){
            location.href =funUrl+"user/account";
        });
     window.onload = loadUserInfo;
});

function initLoadParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r!=null) return unescape(r[2]); return null; //返回参数值
}
function loadUserInfo(){
    $.ajax({
        type:"get",
        url:funUrl+"user_login_check",
        dataType:'json',//服务器返回json格式数据
        success:function(result){
            var data = result.data;
            if(data != null){
                var mobile = data.mobile;
                if(mobile != null){
                    mobile=mobile.substring(0,3)+"****"+mobile.substring(7,11);
                    $("#login_on").html("<span style='float: left'>欢迎您，</span><a href='"+funUrl+"/user/account'><span>"+mobile+"</span></a>");
                    $("#registerALL").html("<a href='javascript:void(0);' id='signOut'>退出</a>");
                    /*$("#personalCenter").css({
                        "display": "block",
                        "float": "right",
                        "marginLeft": "15px",
                        "marginTop": "2px"
                    }).html("我的账户");*/
                    $("#signOut").bind("click",function(){
                    	location.href = funUrl+"login/user/logout?url="+urlHost;
                    });
                }
            }
        }
    });
}
function getLocalTime(nS) {
    return  new Date(parseInt(nS) * 1000).Format("yyyy-MM-dd hh:mm:ss");
}
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function queryData(url,params,success,error){
    url=funUrl+url;
    var success = arguments[2]?arguments[2]:function(){};
    var error = arguments[3]?arguments[3]:function(){};
    $.ajax({
        url:url,
        type:"get",
        dataType:"json",
        data:params,
        success: function (res) {
            if(res.success==true){
                success(res.data.data);
            }
        },
        error: function (res) {
            error(res);
        }
    })
}
$(function(){
    $("#backDiv").click(function(){
        $('html, body').animate({scrollTop: '0px'}, 800);
    });
});