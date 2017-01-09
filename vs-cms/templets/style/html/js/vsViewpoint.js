$(function () {
    var  firstThree = $(".vsViewpoint_right .tab_all .tab_lis");
    firstThree.each(function(){
        var _this = $(this);
        for(var i=0;i<_this.find(".tab_center").length;i++){
            _this.find("span").eq(i).text("NO."+(i+1));
        }
        _this.find("span").eq(0).css({
            "background":"#ffb319"
        });
        _this.find("span").eq(1).css({
            "background":"#19b2ff"
        });
    });
});