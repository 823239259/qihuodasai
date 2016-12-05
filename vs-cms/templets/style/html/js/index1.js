/**
 * Created by 丽丽 on 2016/10/21.
 */
var url=window.location.href;
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
        $("#login").bind("click",function(){
            var urls = url.split("?");
            var loginUrl = url;
            if(urls.length > 0){
                loginUrl = urls[0];
            }
            location.href =funUrl+"user/redirectVsNet?url="+loginUrl;
        });
     window.onload = loadUserInfo;
});
function initLoadParam(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r!=null) return unescape(r[2]); return null; //返回参数值
}
function loadUserInfo(){
    if(check == undefined || check == null){
        location.href = funUrl+"login/user/redirect/account?url="+url;
        return ;
    }
    var mobile = null;
    if(param != null){
        $.ajax({
            type:"get",
            url:funUrl+"login/user/getAccount",
            data:{
                mobile:param
            },
            dataType:'json',//服务器返回json格式数据
            success:function(result){
                var data = result.data.data;
                if(data != null){
                    mobile = data.mobile;
                    if(mobile != null){
                        console.log(mobile);
                        mobile=mobile.substring(0,3)+"****"+mobile.substring(7,11);
                        $("#login").html("欢迎您，<a href='http://www.vs.com:80/user/account'><span>"+mobile+"</span></a>"); 
                        $("#registerALL").html("<a href='"+funUrl+"logout' id='signOut'>退出</a>");
                        $("#personalCenter").html("我的账户");
                    }
                }
            }
        });
    }
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
