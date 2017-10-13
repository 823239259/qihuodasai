<template>
	<div id="account">
			<ul>
				<li>
					<span>账户概括</span>
				</li>
				<li>
					<span>开户明细</span>
				</li>
				<li>
					<span>安全设置</span>
				</li>
			</ul>
			<div class="account_survey">
				<p>账户资金</p>
				<div class="account_info">
					<div class="info_left">
						<ul>
							<li>
								<img src="../assets/images/icon_smileFace.png" alt="笑脸" />
								1555****888
								<img src="../assets/images/icon_acc1.png" alt="账户" />
								<img src="../assets/images/icon_password1.png" alt="体现密码" />
							</li>
							<li>
								<button class="btn yellow">充值</button>
								<button class="btn blue">体现</button>
							</li>
						</ul>
					</div> 
					<div class="info_right">
						<ul>
							<li>
								<img src="../assets/images/icon_moneyuse.png" alt="可用资金" />
								<div>
									<p>可用资金<i class="ifont"></i></p>
									<span>￥1000.00</span>
								</div>
							</li>
							<li>
								<img src="../assets/images/icon_money.png" alt="冻结资金" />
								<div>
									<p>冻结资金<i class="ifont"></i></p>
									<span>￥10.00</span>
								</div>
							</li>
							<li>
								<p id="color_blue">注：账户总资产=可用资金+冻结资金</p>
							</li>
						</ul>
					</div>
				</div>
				<p>资金明细</p>
				<div class="account_money"></div>
				<p id="color_dea">收入<span class="white">10</span>笔，共<i class="color_yellow">1000000.00</i>元 支出<span class="white">5</span>笔，共<i  class="color_yellow">2750</i>元  </p>
				<ul>
					<li>今天</li>
					<li>7天</li>	
					<li>15天</li>
					<li>30天</li>
					<li>起始时间</li>
					<li><select name="">
						<option value="">2017-10-15</option>
					</select></li>
					<li><i class="ifont">&#xe604;</i></li>
					<li><select name="">
						<option value="">2017-10-15</option>
					</select></li>
				</ul>
				<div class="moneyDetail_list">
					<table>
						<thead>
							<tr>
								<td>时间</td>
								<td>类型</td>
								<td>金额</td>
								<td>账户余额</td>
								<td>详情</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>收入</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>收入</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div id="account_openDetail">
				
			</div>
			<div class="account_safe">
				<div class="account_safe_top">
					<div class="safe_left">
						<img src="../assets/images/icon_accountsafe.png" alt="safelogo" />
					</div>
					<div class="safe_right">
						<p>*威力&nbsp;<span>158****8888</span></p>
						<p>平台实时保护您的账户和资金安全</p>
					</div>
				</div>
				<div class="acount_safe_btm">
					<p>您设置了<span>3</span>个保护，还有<span>2</span>保护项可设置</p>
					<table>
						<tbody>
							<tr>
								<td><img src="../assets/images/icon_acc1.png" alt="" /></td>
								<td>实名认证</td>
								<td>已认证</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td></td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_password1.png" alt="" /></td>
								<td>体现密码</td>
								<td>未设置	</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td>设置</td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_bindcard.png" alt="" /></td>
								<td>绑定银行卡</td>
								<td>未绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td>绑定</td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_loginpassword.png" alt="" /></td>
								<td>登录密码</td>
								<td>已设置</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td>修改</td>
							</tr>
								<tr>
								<td><img src="../assets/images/icon_bindtel.png" alt="" /></td>
								<td>绑定手机</td>
								<td>已绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td>修改</td>
							</tr>
						</tbody>
					</table>
					<p class="p_center">投资有风险，入市需谨慎</p>
				</div>
			</div>
	</div>
</template>

<script>
	export default{
		name:'account',
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../assets/css/common.scss";
	#account {
		margin-left: 120px;
		width : 1000px;
		li {
			&:hover {
				color: $yellow;
			}
			text-align: center;
			float : left;	
			width : 120px;
			height : 30px;
			margin: 5px 10px 5px 0;
			line-height : 30px;
			&:nth-child(1){
				color: $yellow;
			}
			span {
				background-color : $blue;
				display: block;
				width: 120px;
				height: 30px;
			}
		}
		.account_survey {
			background-color: $blue;
			width: 100%;
			float: left;
			display: none;
			p {
				height: 40px;
				line-height: 40px;
				color: $white;
				width: 100%;
				background-color: $bottom_color;
				text-indent: 5px;
			}
			.ifont {
				font-size: 20px;
				color: $black;
				background-color: $highLight;
				width: 150px;
			}
		}
		.account_money {
			width: 100%;
		}
		.account_info {
			width: 100%;
			height: 240px;
		}
		.info_left {
			width: 50%;
			float: left;
			li {
				width: 100%;
				height: 90px;
				padding-top: 30px;
			}
			img {
				&:nth-child(1) {
					width: 90px;
					height: 90px;
				}
				&:nth-child(3) {
					width: 20px;
					height: 20px;
					position:relative;
					top: -40px;
					left: 10px;
				}
				&:nth-child(2) {
					width: 20px;
					height: 14px;
					border: 1px solid $yellow;
					padding: 3px 1px 0 1px;
					position:relative;
					top: -40px;
					left: 10px;
				}
			}
			.btn {
				width: 120px;
				height: 30px;
				margin-top: 30px;
				color: $black;	
			}
		}
		.info_right {
			width: 50%;
			float: left;
			li {
				display: flex;
				justify-content:center;
				width: 100%;
				margin-top: 30px;
				height: 40px;
			}
		}
		#color_blue {
					color: $lightblue;
					background-color: $blue;
				}
		#color_dea {
			background-color: $blue;
			color: $lightblue;
		}
		.color_yellow  {
			color: $yellow;
			margin: 0 5px;
			font-weight: 700;
		}
		.white {
			color: $white;
			margin: 0 5px;
		}
		/*table {
			margin-left: 5px;
		}*/
		/*安全设置tab*/
		.account_safe {
			width: 100%;
			height: 540px;
			background-color: #242633;
			float: left;
			.account_safe_top {
				height: 150px;
				width:100%;
				.safe_left {
					float: left;
					width: 150px;
					height:100%;
					background-color: $blue;
					img {
						padding-left: 50px;
						padding-top: 30px;
					}
				}
				.safe_right {
					float: left;
					width: 850px;
					height: 100%;
					background-color: $blue;
					p {
						width: 100%;
						font-size: $fs16;
						color: $white;
						padding-top: 60px;
						&:nth-child(2){
							color: $lightblue;
							padding-top: 5px;
							font-size: $fs14;
						}
					}
				}
				
			}
			.acount_safe_btm {
				width: 100%;
				p {
					font-size: $fs14;
					text-indent: 5px;
					width: 100%;
					height: 40px;
					background-color: $bottom_color;
					line-height: 40px;
					span {
						color: $white;
						margin: 0 3px;
					}
				}
				.p_center {
					text-align: center;
					margin-top: 5px;
				}
				table {
					margin-top: 5px;
				}
				td {
					height: 60px;
					text-indent: 5px;
					background-color: $blue;
					border-bottom: 1px solid $bottom_color;
				}
				
			}
		}
	}
</style>