$(document).ready(function(){

    // 进入登录页，则获取login ticket，该函数在下面定义。
    flushLoginTicket();

    //校验手机号码
    $("#phone").blur(function(){
        var $this = $(this);
        var mobileValue =  $.trim($this.val());
        verifyMobile($this) //检验手机号码;
    });

    //检验手机号码格式
    function verifyMobile(obj){
        var mobileValue =  $.trim(obj.val());
        if(mobileValue == null || mobileValue.length <= 0){  //判断手机号码是否为空  
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("请输入正确的手机号码");
            return false;
        }else if(!mobileValue.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("<i>*</i>手机号码输入有误");
            return false;
        }
        obj.parent().find("i").css({display: "none"});
        obj.parent().find("i").html("请输入正确的手机号码");
        return true;
    }

    //打开短信验证码框
    $("#openYZMBox").live("click",function(){
        var $this = $(this);
        var mobileObject = $("#phone");
        var status =  $(this).attr("status");
        if(verifyMobile(mobileObject) && status == "true"){
            $this.attr("status",false);
            $(".yzm_fl_mask").show();
            $(".yzm_box").show();
            $("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
        }
    });

    //关闭短信验证码框
    function colseYZMBox(){
        $("#openYZMBox").attr("status",true);
        $(".yzm_fl_mask").hide();
        $(".yzm_box").hide();
        $(".yzm").val('');
    }

    //关闭短信验证码框
    $(".yzm_box_close").live("click",function(){
        colseYZMBox();
    });

    //刷新注册码
    $(".refresh").live("click",function(){
        $("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
    });

    //验证码倒计时
    var wait=60;

    //获取短信验证码
    $("#getSMSCode").live("click",function(){
        var $this = $(this);
        var status =  $(this).attr("status");
        if(status == "true"){
            $this.attr("status",false);
            $this.css({background:'#D3D3D3'});
            var yzm = $("#yzm").val();
            var mobile = $($("#phone")).val();
            if(yzm == null || yzm.length <= 0){
                $this.attr("status",true);
                $this.css({background:'#ff9c00'});
                $.alertTip($this,"验证码不能空 ！");
                return;
            }
           
            $.post(basepath+"send_mobile_sms_code",{mobile:mobile,yzmCode:yzm,ajax:1},function(data){  //获取短信验证码信息
                $("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
                if(data.success){
                    if(data.message!="" && data.message!=null){
                        if(data.message == "mobileIsExist"){
                            $this.attr("status",true);
                            $this.css({background:'#ff9c00'});
                            $.alertTip($("#getSMSCode"),"该手机号码已经存在 ！");
                            return;
                        }else if(data.message == "mobileIsNull" || data.message == "mobileFormatError"){
                            $this.attr("status",true);
                            $this.css({background:'#ff9c00'});
                            $.alertTip($("#getSMSCode"),"手机号码错误 ！");
                            return;
                        }else if(data.message == "yzmStrError"){
                            $this.attr("status",true);
                            $this.css({background:'#ff9c00'});
                            $.alertTip($("#getSMSCode"),"验证码错误！");
                            return;
                        }else{
                            var openYZMBoxOject = $("#openYZMBox");
                            colseYZMBox();
                            $this.attr("status",true);
                            $this.css({background:'#ff9c00'});
                            openYZMBoxOject.attr("status",false);
                            var mobileObj = $("#phone");
                            mobileObj.parent().find("i").css({display: "none"});
                            mobileObj.parent().find("p").html("请输入正确的手机号码");
                            openYZMBoxOject.css({background:'#D3D3D3'});
                            openYZMBoxOject.text("重新发送(" + wait + ")");
                            $.alertTip($("#openYZMBox"),data.message == "highOperation" ? "操作过于频繁，请稍后再重试！" : "每天只能获取5次验证码 ！");
                            countdown(openYZMBoxOject);  //按钮设置倒计时
                            return;
                        }
                    }else{
                        var openYZMBoxOject = $("#openYZMBox");
                        colseYZMBox();
                        $this.attr("status",true);
                        $this.css({background:'#ff9c00'});
                        openYZMBoxOject.attr("status",false);
                        var mobileObj = $("#phone");
                        mobileObj.parent().find("i").css({display: "none"});
                        mobileObj.parent().find("p").html("请输入正确的手机号码");
                        openYZMBoxOject.css({background:'#D3D3D3'});
                        openYZMBoxOject.text("重新发送(" + wait + ")");
                        countdown(openYZMBoxOject);  //按钮设置倒计时
                    }
                }else{
                    $this.attr("status",true);
                    $this.css({background:'#ff9c00'});
                    $.alertTip($(this),"系统繁忙，请重试......");
                }
            },"json");
        }
    });

    //验证码倒计时
    function countdown(obj) {
        if (wait <= 0) {
            obj.attr("status",true);
            obj.text("发送验证码");
            obj.css({background:'#f97602'});
            wait = 60;
        } else {
            obj.attr("status",false);
            obj.text("重新发送(" + wait + ")");
            wait--;
            setTimeout(function() {
                    countdown(obj);
                },
                1000)
        }
    }

    //校验密码  
    $("#password").blur(function(){
        verifyPassword($(this));  //校验密码格式
    });

    //校验密码格式
    function verifyPassword(obj){
        var passwordValue =  $.trim(obj.val());
        if(passwordValue == null || passwordValue.length <= 0 || !/^[0-9a-zA-Z]{6,16}$/.test(passwordValue) || passwordValue.length != $.trim(passwordValue).length ){
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("密码由6-16位字母和数字组成");
            return false;
        }else if(!/[0-9]{1,}/.test(passwordValue) || !/[a-zA-Z]{1,}/.test(passwordValue)){
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("密码由6-16位字母和数字组成");
            return false;
        }else if(passwordValue.length < 6 || passwordValue.length > 20){
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("密码由6-16位字母和数字组成");
            return false;
        }
        obj.parent().find("i").css({display: "none"});
        obj.parent().find("i").html("密码由6-16位字母和数字组成");
        return true;
    }

    //校验密码  
    $("#checkPassword").blur(function(){
        checkPassword($(this),null);  //校验确认密码格式
    });

    //校验确认密码格式
    function checkPassword(obj){
        var checkPasswordValue =  $.trim(obj.val());
        if(checkPasswordValue != $("#password").val()){
            //密码不一致验证
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("两次密码不一致");
            return false;
        }else if(checkPasswordValue == null || checkPasswordValue.length <= 0){
            //确认密码为空验证
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("请输入确认密码");
            return false;
        }
        obj.parent().find("i").css({display: "none"});
        obj.parent().find("i").html("两次密码不一致");
        return true;
    }

    //校验验证码 
    $("#code").blur(function(){
        verifyCode($(this),null);  //校验确认密码格式
    });

    //校验手机验证码格式
    function verifyCode(obj){
        var codeValue =  $.trim(obj.val());
        if(codeValue == null || codeValue.length <= 0){
            obj.parent().find("i").css({display: "block"});
            obj.parent().find("i").html("请填写验证码");
            return false;
        }
        obj.parent().find("i").css({display: "none"});
        obj.parent().find("i").html("请填写验证码");
        return true;
    }


    //协议选择操作
    $("#agreement").on("click",function(){
        var $this = $(this);
        var status = $this.attr("checked");
        if(status == null || status == "undefined"){
            $this.removeAttr("checked");
            $("#hkStock").attr("status",false);
            $("#hkStock").css({background:'#D3D3D3'});
        }else{
            $this.attr("checked","checked");
            $("#hkStock").attr("status",true);
            $("#hkStock").css({background:'#f97602'});
        }
    });

    //注册
    $("#hkStock").on("click", function(){
        var $this = $(this);
        if($(this).attr("status") == "true"){
            $this.attr("status",false);
            $this.text("正在注册");
            var mobileObject =  $("#phone");
            var mobileValue =  $.trim(mobileObject.val());
            var passwordObject =  $("#password");
            var passwordValue =  passwordObject.val();
            var checkPasswordObject = $("#check_password");
            var checkPasswordValue = checkPasswordObject.val();
            var codeObject =  $("#code");
            var codeValue =  $.trim(codeObject.val());
            //校验手机号码、验证码、密码
            if(!verifyMobile(mobileObject) || !verifyCode(codeObject) || !verifyPassword(passwordObject) || !checkPassword(checkPasswordObject)){
                $this.attr("status",true);
                $this.text("立即注册");
                return;
            }

            $.post(basepath+"signin_operation",{ 'source':1,'mobile':mobileValue,'code':codeValue,'password':passwordValue,ajax:1},function(data){ //注册
                if(data.success){
                    if(data.message!="" && data.message!=null){
                        if(data.message=="mobileIsExist"){
                            mobileObject.parent().find("i").css({display: "block"});
                            mobileObject.parent().find("i").html("该手机号码已经存在");
                        }else if(data.message=="codeError"){
                            codeObject.parent().find("i").css({display: "block"});
                            codeObject.parent().find("i").html("验证码错误,请重新获取");
                        }else if(data.message=="codeTimeOut"){
                            codeObject.parent().find("i").css({display: "block"});
                            codeObject.parent().find("i").html("验证码已失效,请重新获取");
                        }
                        $this.attr("status",true);
                        $this.text("立即注册");
                    }else{
                        alert("恭喜，注册成功！");
                        //隐藏向CAS服务端提交登录的用户名和密码
                        $("#loginUsername").val(mobileValue);
                        $("#loginPassword").val(passwordValue);
                        $("#loginForm").submit();
                    }
                }else{
                    $this.attr("status",true);
                    $this.text("立即注册");
                    showMsgDialog("提示","系统繁忙，请重试......");
                }
            },"json");
        }
    });

    var form = $("#form");

    // 回车事件
    form.on("keypress", "input", function(event){
        if(event.keyCode == 13){
            $("#hkStock").trigger("click");
            return false;
        }
    });

    //初始化定义光标位置
    $("#phone").focus();
});

//显示协议
function showAgreement(){
    var htmlHeight = 685;  //网页高度
    var htmlWidth = 1221;  //网页宽度
    var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
    window.open(basepath+'websiteAgreement','投资达人网站服务协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');
}

//登录验证函数, 由 onsubmit 事件触发
function loginValidate(){
    deleteIFrame('#ssoLoginFrame');// 删除用完的iframe,但是一定不要在回调前删除，Firefox可能有问题的
    // 重新刷新 login ticket
    flushLoginTicket();
    // 验证成功后，动态创建用于提交登录的 iframe
    $('body').append($('<iframe/>').attr({
        style : "display:none;width:0;height:0",
        id : "ssoLoginFrame",
        name : "ssoLoginFrame",
        src : "javascript:false;"
    }));
    return true;
}

function deleteIFrame(iframeName) {
    var iframe = $(iframeName);
    if (iframe) { // 删除用完的iframe，避免页面刷新或前进、后退时，重复执行该iframe的请求
        iframe.remove()
    }
};

// 由于一个 login ticket 只允许使用一次, 当每次登录需要调用该函数刷新 lt
function flushLoginTicket() {
    var backData=$.trim($("#backData").val());
    var source = $.trim($("#source").val());
    var service = basepath+"commodity/index";
    if(backData && source == '5'){  //系统内部跳转：backData（url）非空，source（来源）为空
        service = backData;
    }
    var _services = 'service='
        + encodeURIComponent(service);
    $.getScript(casServerLoginUrl+'?'
        + _services + '&get-lt=true&n=' + new Date().getTime(), function() {
        // 将返回的 _loginTicket 变量设置到  input name="lt" 的value中。
        $('#J_LoginTicket').val(_loginTicket);
        $('#J_FlowExecutionKey').val(_flowExecutionKey);
    });
};