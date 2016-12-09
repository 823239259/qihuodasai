$(function(){
    $("#summaryContent li").mouseover(function(){
        var _this = $(this);
        _this.children(".quotation").show();
    }).mouseout(function(){
        var _this = $(this);
        _this.children(".quotation").hide();
    });

    $("#summaryContent .tab li").on("click",function(){
        var _this = $(this);
        $("#summaryContent .tab a").removeClass("on").eq(_this.index()).addClass("on");
        $("#summaryContent .tab_all .tab_lis").hide().eq(_this.index()).show();
    });
});