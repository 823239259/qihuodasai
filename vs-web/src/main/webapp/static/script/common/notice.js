/**
 * 获取公告信息
 * @param $
 */
!function($) {
	$.ajax({
		type:'post',
		url: basepath + 'notice/item',
        dataType: "jsonp",  
        jsonp: "callback",
        jsonpCallback: "callback",
		success: function(data) {
			if(data.success) {
				$('#advert').show();
				$('#advert p').html(data.obj ? data.obj : '');
				
//				if(data.obj && $('#floatlayer').html()) {
//					/*$('#floatlayer').css('display', 'block');
//					$('#floatlayer .close').click(function() {
//						$('#floatlayer').css('display', 'none');
//					});
//					$('#floatlayer .fl_uc_surebtn').click(function() {
//						$('#floatlayer').css('display', 'none');
//					});*/
//					art.dialog({
//					    title: '致维胜网用户的公开信',
//					    content:data.obj,
//					    width:925,
//					    cancelVal: '关闭',
//					    background: '#BBBBBB', // 背景色 
//					    opacity:0.87, 
//					    zIndex:9900,
//					    lock:true,
//					    cancel: true 
//					});
//				}
			}
		}
	});
}(jQuery);