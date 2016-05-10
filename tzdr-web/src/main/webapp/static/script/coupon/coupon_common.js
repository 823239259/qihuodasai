function Tzdr(){
	
};

/**
 * 
 * @param jqueryFind jquery exception 
 * @param valueTag target Element Tag Object
 */
Tzdr.rechangeSetValue = function rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback) {
	$(jqueryFind).each(function(){
    	$(this).click(function(){
		    $(jqueryFind).each(function(){
		    	$(this).removeClass("on");
		    });
    		$(this).addClass("on");
    		try {
    			var value = $(this).find(".defaultVal").val();
    			$(valueTag).val(value);
    		}
    		catch (ex) {}
    		if (clickCallback) {
    			clickCallback(this);
    		}
    		
    	});
    });
	try{
		initCallback();
	}
	catch(e) {}
};

Tzdr.submitForm = function submitForm(id) {
	setTimeout(function(){
		$("#" + id).submit();
	},100);
};

/**
 * 查询对象
 */
$.extend({easyui:{}});
$.extend($.easyui,{
	submitForm:function(id){
		Tzdr.submitForm(id);
	},
	rechangeSetValue:function rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback) {
		Tzdr.rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback);
	}
});

$(function(){
	$("#notPay_close").click(function(){
		$("#notPay").hide();
	});
}); 

/**
 * 
 * @param jqueryFind
 */
function closeWindow(jqueryFind,callback) {
	$(".fl_mask").hide();
	$(jqueryFind).hide();
	try{
		callback(this);
	}catch(e){}
};

/**
 * 
 * @param jqueryFind
 */
function openWindow(jqueryFind,callback) {
	try {
		callback(this);
	}
	catch (e){}
	$(".fl_mask").show();
	$(jqueryFind).show();
};

	
	
	
	

	
	
	
	



	









