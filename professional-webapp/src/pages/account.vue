<template>
	<div id="account">
					<span v-on:click="show_accountSurvey">账户概括</span>
					<span>开户明细</span>
					<span v-on:click="show_accountSafe">安全设置</span>
			</ul>
			<div id="account_survey" v-if="isshow_accountSurey">
				<p>账户资金</p>
				<div class="account_info">
					<div class="info_left">
						<ul>
							<li>
								<img src="../assets/images/icon_smileFace.png" alt="笑脸" />
								<span>1555****888</span>
								<img src="../assets/images/icon_acc1.png" alt="账户" />
								<img src="../assets/images/icon_password1.png" alt="提现密码" />
							</li>
							<li>
								<button class="btn yellow" >充值</button>
								<button class="btn blue" v-on:click="toWithDraw">提现</button>
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
					<div class="page_next">
						<span>上一页</span>
						<span>1</span>
						<span>2</span>
						<span>3</span>
						<span>4</span>
						<span>5</span>
						<span>下一页</span>
					</div>
					<p class="p_center">投资有风险，入市需谨慎</p>
				</div>
			</div>
			<div id="account_openDetail">
				<div class="account_openDetail_top">
					<ul>
						<li>
							<span>今天</span>
							<span>一个月</span>
							<span>三个月</span>
							<span>一年</span>
							<span>起始时间</span>
							<span>2017-07-15</span>
							<span>></span>
							<span>2017-07-16</span>
						</li>
						<li>
							<span>全部</span>
							<span>开户中</span>
							<span>操盘中</span>
							<span>已结算</span>
						</li>
					</ul>
				</div>
				<div class="account_openDetail_center">
					<table>
						<thead>
							<tr>
								<td>交易品种</td>
								<td>状态</td>
								<td>初始保证金</td>
								<td>追加保证金</td>
								<td>总操盘资金</td>
								<td>亏损平仓线</td>
								<td>申请时间</td>
								<td>结算时间</td>
								<td>结算金额</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td>追加保证金</td>
							</tr>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td>查看账号</td>
							</tr>
							<tr>
								<td>国际综合</td>
								<td>开户中</td>
								<td>3000元</td>
								<td>-</td>
								<td>3000元</td>
								<td>5270美元</td>
								<td>2017-07-06</br>16:29:55</td>
								<td>-</td>
								<td>-</td>
								<td>结算明细</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="account_openDetail_notice">
					<p>注意：</p>
					<ul>
						<li>1.交易手续费=合约手续费x手数</li>
						<li>2.买卖各算一手</li>
						<li>3.结算金额=操盘保证金+补充保证金+交易盈亏-交易手续费</li>
					</ul>
				</div>
				<div class="account_openDetail_btm">
					<p>投资有风险，入市需谨慎</p>
				</div>
			</div>
			<div id="account_safe" v-if="isshow_safe">
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
								<td>未认证</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toCertification">去认证</td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_password1.png" alt="" /></td>
								<td>提现密码</td>
								<td>未设置	</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toaWithdrawalPassword">设置</td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_bindcard.png" alt="" /></td>
								<td>绑定银行卡</td>
								<td>未绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toBindBankCard">绑定</td>
							</tr>
							<tr>
								<td><img src="../assets/images/icon_loginpassword.png" alt="" /></td>
								<td>登录密码</td>
								<td>已设置</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toResetLoginPassword">修改</td>
							</tr>
								<tr>
								<td><img src="../assets/images/icon_bindtel.png" alt="" /></td>
								<td>绑定手机</td>
								<td>已绑定</td>
								<td>认证信息：*小四，51116*************2222</td>
								<td v-on:click="toResetCellPassword">修改</td>
							</tr>
						</tbody>
					</table>
					<p class="p_center">投资有风险，入市需谨慎</p>
				</div>
			</div>
			<account_withDraw  v-if="isshow_withDraw" />
			<account_certification  v-if="isshow_certification"/> 
			<account_withdrawalPassword v-if="isshow_withdrawalPassword"/>
			<account_bindBankCard v-if="isshow_bindBankCard"/>
			<account_addBankCard v-if="isshow_addBankCard" />
			<account_resetCellPhone v-if="isshow_resetCellPhone" />
			<account_resetLoginPassword v-if="isshow_resetLoginPassword" />
	</div>
</template>

<script>
	import account_withDraw from "./account/account_withDraw.vue"
	import account_certification from "./account/account_certification.vue"
	import account_withdrawalPassword from "./account/account_withdrawalPassword.vue"
	import account_bindBankCard from "./account/account_bindBankCard.vue"
	import account_addBankCard from "./account/account_addBankCard.vue"
	import account_resetLoginPassword from "./account/account_resetLoginPassword.vue"
	import account_resetCellPhone from "./account/account_resetCellPhone.vue"
 	export default{
		name:'account',
		data(){
			return {
				isshow_withDraw : false,
				isshow_accountSurey : true,
				isshow_certification : false,
				isshow_safe : true,
				isshow_withdrawalPassword :　false,
				isshow_bindBankCard :false,
				isshow_addBankCard : false,
				isshow_resetLoginPassword : false,
				isshow_resetCellPhone :false
			}
		},
		components:{account_withDraw,account_certification,account_withdrawalPassword,
					account_bindBankCard,account_addBankCard,account_resetLoginPassword,account_resetCellPhone},
		methods : {
			toWithDraw : function (){
				this.isshow_withDraw=!this.isshow_withDraw,
				this.isshow_accountSurey=!this.isshow_accountSurey
			},
			show_accountSurvey : function(){
				this.isshow_withDraw = false,
				this.isshow_accountSurey = true
			},
			toCertification : function(){
				this.isshow_certification=!this.isshow_certification,
				this.isshow_safe = false
			},
			show_accountSafe : function(){
				this.isshow_certification = false,
				this.isshow_safe = true,
				this.isshow_withdrawalPassword = false,
				this.isshow_addBankCard = false,
				this.isshow_bindBankCard = false,
				this.isshow_resetLoginPassword = false,
				this.isshow_resetCellPhone =false
			},
			toaWithdrawalPassword : function(){
				this.isshow_withdrawalPassword=!this.isshow_withdrawalPassword,
				this.isshow_safe = false
			},
			toBindBankCard : function(){
				this.isshow_bindBankCard=!this.isshow_bindBankCard,
				this.isshow_safe = false,
				this.isshow_addBankCard = false
			},
			toResetLoginPassword : function(){
				this.isshow_resetLoginPassword=!this.isshow_resetLoginPassword,
				this.isshow_safe = false
				
			},
			toResetCellPassword : function(){
				this.isshow_resetCellPhone=!this.isshow_resetCellPhone,
				this.isshow_safe = false
			}
		}
		
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../assets/css/common.scss";
	
	#account {
		left: 120px;
		position : relative;
		width : 1000px;
		span {
			&:hover {
				color: yellow;
			}
			background-color : $blue;
			width: 120px;
			height: 30px;
			line-height : 30px;
			padding : 5px 20px 5px 20px;
		}
	}
		#account_survey {
			display: none;
			background-color: $blue;
			width: 100%;
			float: left;
			li {
				width: 60px;
			}
			p {
				height: 40px;
				line-height: 40px;
				color: $white;
				width: 100%;
				background-color: $bottom_color;
				text-indent: 5px;
			}
			.p_center {
				text-align: center;
				margin-top: 5px;
			}
			.ifont {
				font-size: 20px;
				color: $black;
				background-color: $highLight;
				width: 150px;
			}
			.account_info {
			width: 100%;
			height: 240px;			
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
						position: relative;
					}
					&:nth-child(3) {
						width: 20px;
						height: 20px;
						position: relative;
						bottom: 35px;	
					}
					&:nth-child(4) {
						width: 20px;
						height: 20px;
						position: relative;
						bottom : 35px;
					}
				}
				.btn {
					width: 120px;
					height: 30px;
					margin-top: 30px;
					color: $black;	
				}
				span {
					width: 80px;
					display: inline-block;
					position: relative;
					bottom: 40px;
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
				p {
					background-color: $blue;
				}
				img {
					position: relative;
					top: 20px;
				}
			}
		}
		}
		.page_next {
			text-align: right;
			height: 60px;
			line-height: 60px;
			span {
				margin:0 5px;
			}
		}
		.account_money {
			width: 100%;
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
		table {
			text-indent: 5px;
			td {
				height: 40px;
				border-bottom: 1px solid $bottom_color;
				background-color: $blue;
			}
		}
		#account_safe {
			display: none;
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
		#account_openDetail{
			width: 100%;
			margin-top: 5px;
			
			.account_openDetail_top {
				height: 110px;
				background-color: $blue;
				li {
					&:nth-child(1){
						height: 70px;
						border-bottom: 1px solid $bottom_color;
						line-height: 70px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
					&:nth-child(2){
						height: 40px;
						line-height: 40px;
						span {
							&:nth-child(1) {
								color: $yellow;
							}
						}
					}
				}
			}
			.account_openDetail_center{
				background-color: $blue;
			}
			.account_openDetail_notice {
				height: 120px;
				background-color: $blue;
				p {
					color: $yellow;
					font-size: $fs14;
					padding-top: 20px;
				}
				li {
					margin-top: 10px;
					font-size: $fs12;
				}
			}
			.account_openDetail_btm{
				p {
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 10px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
				}
			}
		}
</style>