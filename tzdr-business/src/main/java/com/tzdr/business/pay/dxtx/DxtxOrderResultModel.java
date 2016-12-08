package com.tzdr.business.pay.dxtx;

import java.io.Serializable;
/**
 * 盾行天下下单成功后的返回数据model
 * @author username
 *
 */
@SuppressWarnings("serial")
public class DxtxOrderResultModel implements Serializable{
		private String result;
		private String message;
		private String data;
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		
}
