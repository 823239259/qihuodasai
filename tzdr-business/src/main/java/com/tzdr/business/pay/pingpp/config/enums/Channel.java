package com.tzdr.business.pay.pingpp.config.enums;

public enum Channel {
	/**
	 * 支付宝手机支付
	 */
	ALIPAY(3,"alipay", "支付宝手机支付"),
	/**
	 * 支付宝手机网页支付
	 */
	ALIPAY_WAP(4,"alipay_wap", "支付宝手机网页支付"),
	/**
	 * 支付宝扫码支付
	 */
	ALIPAY_QR(5,"alipay_qr", "支付宝扫码支付"),
	/**
	 * 支付宝 PC 网页支付
	 */
	ALIPAY_PC_DIRECT(6,"alipay_pc_direct", " 支付宝 PC 网页支付"),
	/**
	 * 国付宝
	 */
	GO_WAY(7,"go_way","国付宝支付");
	private Integer channel;
	private String channelCode;

	private String channelNote;

	private Channel(Integer channel,String channelCode, String channelNote) {
		this.channel = channel;
		this.channelCode = channelCode;
		this.channelNote = channelNote;
	}

	public static Channel newInstance(String code) {
		for (Channel c : Channel.values()) {
			if (c.getChannelCode() == code) {
				return c;
			}
		}
		return null;
	}
	public static Channel newInstanceChannel(Integer channel){
		for (Channel c : Channel.values()) {
			if (c.getChannel() == channel) {
				return c;
			}
		}
		return null;
	}
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelNote() {
		return channelNote;
	}

	public void setChannelNote(String channelNote) {
		this.channelNote = channelNote;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

}
