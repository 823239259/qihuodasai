$(function () {
    var  firstThree = $(".vsViewpoint_right .tab_all .tab_lis");
    firstThree.each(function(){
        var _this = $(this);
        for(var i=0;i<_this.find(".tab_center").length;i++){
            _this.find("span").eq(i).text("NO."+(i+1));
        }
        $(this).find("span").slice(0,3).css({
            "color":"#333",
            "background":"#ffb319"
        });
    });
});