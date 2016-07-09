$(function () {
	// banner切换
	num = $(".bannerlist .slide_box a").size();
	i = 0;
	theInt = null;
	$(".bannerlist .slide_box a").eq(0).fadeIn(500);
	$(".ad_slider a").eq(0).addClass("on");
	$(".ad_slider a").each(function (i) {
		$(this).click(function () {
			HuanDeng(i);
			Change(i);
		});
	});
	HuanDeng = function (p) {
	clearInterval(theInt);
	theInt = setInterval(function () {
		p++;
		if (p < num) {
			Change(p);
		} else {
			p = 0
			Change(p);
		}
		}, 5000);
	}
	HuanDeng(0);
	function Change(num) {
		$(".bannerlist .slide_box a").fadeOut(500);
		$(".bannerlist .slide_box a").eq(num).fadeIn(500);
		$(".ad_slider a").removeClass("on");
		$(".ad_slider a").eq(num).addClass("on");
	}
	
	var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
    left_xiangmu.each(function(){
        left_xiangmu.click(function(){
            left_xiangmu.removeClass('on');
            $(this).addClass('on');
            alert(12);
        });
    })
});