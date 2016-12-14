$.fn.clickup=function(){
    $(this).click(function(){

        $(this).parent().slideUp().siblings().slideDown('fast');
        $(this).siblings('li').css('right', '-100%');
    });
};
$.fn.clickdown=function(){
    $(this).click(function(){
        console.log($(this).index());
        $(this).siblings('.list-se').slideToggle('fast').siblings().slideToggle('fast');
        $(this).parent().siblings().children('.list-se').slideUp('fast').siblings().slideDown('fast');
        var li = $(this).siblings('.list-se').children('li');
        for(var i=0;i<li.length;i++){
            li.eq(i).animate({right:'0'},0);
        }
        /*$(this).parents(".list_lis").find(".list-se").children('li').find("p").removeClass("on").end().eq(0).find("p").addClass("on");*/
        $(this).parent().siblings().children('.list-se').children('li').css('right', '-100%');
    })
};
$.fn.ad=function(){
    var t = $(this);
    for(var i = 0 ; i<t.length ; i++){
        var count = t.eq(i).children().children().length;
        if (count>1) {
            t.eq(i).children('p[class=title]').append('<span style="color: #ccc;">✚</span>');
            t.eq(i).children('.list-se').children('p[class=title]').append('<span style="font-size: 20px;">━</span>');
        }
    }
};