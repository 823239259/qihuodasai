!function(w) {
	var _$ = w.$ = w.jQuery,
		MOBILE_REG = /^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/,
		AUTONYM_MAX_LENGTH = /^.{1,50}$/,
		isName = function(v) {
			if(v) {
				return AUTONYM_MAX_LENGTH.test(v);
			}
			return false;
		},
		isMobile = function(v) {
			if(!v) {
				return false;
			}
			return v.match(MOBILE_REG);
		};
	
	_$('#booking-sbm').click(function() {
		var _name = _$.trim(_$('#autonym').val()),
			_mobile = _$.trim(_$('#mobile').val());
		
		if(!_name) {
			w.showMsgDialog('提示', '请输入真实姓名');
			return false;
		}
		
		if(!isName(_name)) {
			w.showMsgDialog('提示', '请输入合法真实姓名');
			return false;
		}
		
		if(!_mobile) {
			w.showMsgDialog('提示', '请输入手机号码');
			return false;
		}
		
		if(!isMobile(_mobile)) {
			w.showMsgDialog('提示', '请输入正确的手机号码');
			return false;
		}
		
		if(isBooking()) {
			w.clickCloseRefresh('提示', '您已预约，请勿重复预约..');
			return false;
		}
		
		_$('#booking-frm').submit();
	});
	
	/**
	 * 是否已预约
	 */
	function isBooking() {
		var _bol = true;
		_$.ajax({
			url : basepath + 'spot/is_booking',
			dataType:'json',
			async : false,
			success : function(res) {
				if(res.success) {
					_bol = false;
				}
			}
		});
		return _bol;
	}
}(window);