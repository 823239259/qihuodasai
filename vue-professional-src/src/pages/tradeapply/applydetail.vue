<template>
	<div id="applydetail">
		<topbar title='国际期货'></topbar>
		<back></back>
		<kf title="操盘细则" type="1" @tap.native="toTradersRules"></kf>
		<!--选择操盘保证金-->
		<div class="margin_trade">
			<p class="fontwhite">选择操盘保证金（金额越多，可持仓手数越多）</p>
			<ul>
				<li class="fl" @tap='choose'>
					<mr num='3000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='6000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='10000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='12000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='15000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='50000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='100000'></mr>
				</li>
				<li class="fl" @tap='choose'>
					<mr num='200000'></mr>
				</li>
			</ul>
		</div>
		<!--操盘保证金及资金显示-->
		<div class="margin_trade_show">
			<ul>
				<li class="fontgray">操盘保证金：<span class="fontwhite">{{chooseType | moneytype(chooseType)}}元</span></li>
				<li class="fontgray">总操盘资金：<span class="fontwhite">{{traderTotal | moneytype(chooseType)}}美元</span></li>
				<li class="fontgray">亏损平仓线：<span class="fontwhite">{{lineLoss | moneytype(chooseType)}}美元</span></li>
			</ul>
		</div>
		<!--可交易品种-->
		<div class="notice">
			<p class="fontwhite">可交易品种（一个账号可同时交易17种期货产品）</p>
			<p class="fontgray">
				<span class="fontyellow">注意：</span>请不要在交易时间外持单，以免被系统强制平仓
			</p>
		</div>
		<!--列表-->
		<ul class="datalist">
			<li>
				<ol>
					<li class="fl fontgray">期货产品</li>
					<li class="fl fontgray">交易手续费</li>
					<li class="fl fontgray">初始可持仓</li>
					<li class="fl fontgray">交易时间段</li>
				</ol>
			</li>
			<template v-for="v in temp.contractList">
				<li>
					<ol>
						<li class="fl">
							<p class="fontwhite">{{v.tradeType | cnname}}</p>
							<p class="fontgray">{{v.mainContract}}</p>
						</li>
						<li class="fl fontwhite">{{v.price}}元/手</li>
						<li class="fl fontwhite">{{v.shoushu | filtershoushu(chooseType)}}手</li>
						<li class="fl fontwhite">{{v.tradTime}}</li>
					</ol>
				</li>
			</template>

		</ul>
		<!--提交申请-->
		<ul class="info">
			<li class="fontgray">提交申请表示阅读并同意 <span class="fontwhite" @tap="toAgreement"> 《国际期货操盘合作协议》 </span> </li>
			<li class="fontgray">
				客服咨询： <span class="fontwhite"> 400-852-8008 </span>
			</li>
		</ul>
		<!--最底下按钮-->
		<ul class="bottom">
			<li class="fontyellow">总计：<span><span class="fontyellow">{{chooseType | moneytype(chooseType)}}元</span></span>
			</li>
			<li>
				<bbtn name='提交操盘申请' @tap.native='toConfirm'></bbtn>
			</li>
		</ul>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import bbtn from '../../components/bigBtn.vue'
	import back from '../../components/back.vue'
	import kf from '../../components/customerService.vue'
	import mr from '../../components/moneyradio.vue'
	export default {
		name: 'applydetail',
		components: {
			topbar,
			bbtn,
			back,
			kf,
			mr
		},
		filters: {
			filtershoushu:function(arr,chooseType){
				switch(chooseType){
					case 3000:
					return arr[0];
					break;
					case 6000:
					return arr[1];
					break;
					case 10000:
					return arr[2];
					break;
					case 12000:
					return arr[3];
					break;
					case 15000:
					return arr[4];
					break;
					case 50000:
					return arr[5];
					break;
					case 100000:
					return arr[6];
					break;
					case 200000:
					return arr[7];
					break;
				}
			},
			moneytype: function(num) {
				return num.toLocaleString();
			},
			cnname: function(a) {
				switch(a) {
					case 0:
						return '富时A50'
						break;
					case 6:
						return '国际原油'
						break;
					case 7:
						return '恒指期货'
						break;
					case 9:
						return '迷你道指'
						break;
					case 10:
						return '迷你纳指'
						break;
					case 11:
						return '迷你标普'
						break;
					case 12:
						return '德国DAX'
						break;
					case 13:
						return '日经225'
						break;
					case 14:
						return '小恒指'
						break;
					case 15:
						return '美黄金'
						break;
					case 16:
						return 'H股指数'
						break;
					case 17:
						return '小H股指数'
						break;
					case 18:
						return '美铜'
						break;
					case 19:
						return '美白银'
						break;
					case 20:
						return '小原油'
						break;
					case 21:
						return '迷你德国DAX指数'  //迷你德国DAX指数
						break;
					case 22:
						return '天然气'
						break;
				}

			}
		},
		data() {
			return {
				chooseType: 3000
			}
		},
		computed: {
			temp() {
				return this.$store.state.tempTradeapply;
			},
			islogin() {
				return this.$store.state.account.islogin;
			},
			PATH() {
				return this.$store.getters.PATH
			},
			traderTotal: function() {
				switch(this.chooseType) {
					case 3000:
						return this.temp.paramList[0].traderTotal
						break;
					case 6000:
						return this.temp.paramList[1].traderTotal
						break;
					case 10000:
						return this.temp.paramList[2].traderTotal
						break;
					case 12000:
						return this.temp.paramList[3].traderTotal
						break;
					case 15000:
						return this.temp.paramList[4].traderTotal
						break;
					case 50000:
						return this.temp.paramList[5].traderTotal
						break;
					case 100000:
						return this.temp.paramList[6].traderTotal
						break;
					case 200000:
						return this.temp.paramList[7].traderTotal
						break;
				}
			},
			lineLoss: function() {
				switch(this.chooseType) {
					case 3000:
						return this.temp.paramList[0].lineLoss
						break;
					case 6000:
						return this.temp.paramList[1].lineLoss
						break;
					case 10000:
						return this.temp.paramList[2].lineLoss
						break;
					case 12000:
						return this.temp.paramList[3].lineLoss
						break;
					case 15000:
						return this.temp.paramList[4].lineLoss
						break;
					case 50000:
						return this.temp.paramList[5].lineLoss
						break;
					case 100000:
						return this.temp.paramList[6].lineLoss
						break;
					case 200000:
						return this.temp.paramList[7].lineLoss
						break;
				}
			}
		},
		methods: {
			toTradersRules: function(){
				this.$router.push({path: '/tradersRules'});
			},
			toAgreement: function(){
				this.$router.push({path: '/agreement'});
			},
			choose: function(e) {
				if($(e.target).get(0).tagName == 'DIV') {
					$(e.target).addClass('current');
					$(e.target).parents('li').siblings('li').children().children('div').removeClass('current');
					this.chooseType = parseInt($(e.target).text());
				} else if($(e.target).get(0).tagName == 'SPAN') {
					$(e.target).parent('div').addClass('current');
					$(e.target).parents('li').siblings('li').children().children('div').removeClass('current');
					this.chooseType = parseInt($(e.target).parent('div').text());
				}
			},
			toConfirm: function() {
				this.$router.push({
					path: '/payconfirm',
					query:{
						'chooseType':this.chooseType,
						'traderTotal':this.traderTotal,
						'lineLoss':this.lineLoss
					}
				})
			}
		},
		mounted: function() {
			$('.margin_trade>ul>li:first-child>div>div').addClass('current');
		},
		activated: function() {
			if(this.islogin == false) {
				this.$router.replace({
					'path': '/login'
				});
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	/*ip5*/
	@media(max-width:370px) {
		#applydetail {
			height: 2104px/2 *@ip5 - 20px;
			padding-top: 50px*@ip5;
		}
		.margin_trade {
			background: #242633;
			height: 308px/2 *@ip5;
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			&:before {
				content: '';
				display: table;
			}
			p {
				font-weight: normal;
				margin-left: 15px*@ip5;
				margin-top: 15px*@ip5;
			}
		}
		.margin_trade li {
			width: 25%;
			margin-top: 13px*@ip5;
		}
		.margin_trade li>div {
			margin: 0 auto;
		}
		.margin_trade_show {
			height: 260px/2 *@ip5;
			background: #242633;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			li {
				height: 40px*@ip5;
				line-height: 40px*@ip5;
				font-size: 14px*@ip5;
				padding-left: 15px*@ip5;
				&:nth-child(2) {
					border-top: 1px solid #1b1b26;
					border-bottom: 1px solid #1b1b26;
				}
			}
		}
		.notice {
			font-size: 12px*@ip5;
			height: 70px*@ip5;
			background: #242633;
			padding-left: 15px*@ip5;
			&:before {
				content: '';
				display: table;
			}
			p:nth-child(1) {
				margin-top: 15px*@ip5;
			}
			p {
				font-size: 12px*@ip5;
			}
		}
		ol>li {
			width: 25%;
			font-size: 14px*@ip5;
		}
		.datalist {
			width: 414px*@ip5;
			overflow-x: scroll;
			>li {
				height: 50px*@ip5;
				width: 716px*@ip5;
				border-bottom: 1px solid #1b1b26;
				line-height: 50px*@ip5;
				background: #242633;
				>ol {
					height: 50px*@ip5;
					width: 680px*@ip5; 
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px*@ip5;
					>li {
						height: 50px*@ip5;
						width: 150px*@ip5;
					}
					>li:first-child p {
						height: 25px*@ip5;
						line-height: 25px*@ip5;
						margin-top: 3%;
						font-size: 14px*@ip5;
					}
					>li:nth-child(2){
						width: 120px*@ip5;
					}
					>li:nth-child(3){
						width: 100px*@ip5;
					}
					>li:last-child {
						line-height: 50px*@ip5;
						width: 290px*@ip5;
					}
					>li:first-child p:nth-child(2) {
						margin-top: -5%;
					}
				}
			}
			>li:first-child {
				height: 40px*@ip6;
				background-color: #36394d;
				border-bottom: 1px solid #1b1b26;
				line-height: 40px*@ip5;
				>ol {
					height: 40px*@ip5;
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px*@ip5;
					>li {
						height: 40px*@ip5;
					}
					>li:last-child {
						padding-top: 0;
						line-height: 40px*@ip5;
					}
				}
			}
		}
		.info {
			height: 90px*@ip5;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			background: #242633;
			>li {
				height: 40px*@ip5;
				padding-left: 15px*@ip5;
				line-height: 40px*@ip5;
				font-size: 14px*@ip5;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
			}
		}
		.bottom {
			height: 100px*@ip5;
			background: #242633;
			>li {
				height: 40px*@ip5;
				line-height: 40px*@ip5;
				text-align: center;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
				font-size: 16px * @ip5;
			}
			>li:last-child {
				padding-top: 5px*@ip5;
			}
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		#applydetail {
			height: 2104px/2 *@ip6 - 20px;
			padding-top: 50px*@ip6;
		}
		.margin_trade {
			background: #242633;
			height: 308px/2 *@ip6;
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			&:before {
				content: '';
				display: table;
			}
			p {
				font-weight: normal;
				margin-left: 15px*@ip6;
				margin-top: 15px*@ip6;
			}
		}
		.margin_trade li {
			width: 25%;
			margin-top: 13px*@ip6;
		}
		.margin_trade li>div {
			margin: 0 auto;
		}
		.margin_trade_show {
			height: 260px/2 *@ip6;
			background: #242633;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			li {
				height: 40px*@ip6;
				line-height: 40px*@ip6;
				font-size: 14px*@ip6;
				padding-left: 15px*@ip6;
				&:nth-child(2) {
					border-top: 1px solid #1b1b26;
					border-bottom: 1px solid #1b1b26;
				}
			}
		}
		.notice {
			font-size: 14px*@ip6;
			height: 70px*@ip6;
			background: #242633;
			padding-left: 15px*@ip6;
			&:before {
				content: '';
				display: table;
			}
			p:nth-child(1) {
				margin-top: 15px*@ip6;
			}
		}
		ol>li {
			width: 25%;
			font-size: 14px*@ip6;
		}
		.datalist {
			width: 414px*@ip6;
			overflow-x: scroll;
			>li {
				height: 50px*@ip6;
				width: 716px*@ip6;
				border-bottom: 1px solid #1b1b26;
				line-height: 50px*@ip6;
				background: #242633;
				>ol {
					height: 50px*@ip6;
					width:650px*@ip6; 
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px*@ip6;
					>li {
						height: 50px*@ip6;
						width: 150px*@ip6;
					}
					>li:first-child p {
						height: 25px*@ip6;
						line-height: 25px*@ip6;
						margin-top: 3%;
						font-size: 14px*@ip6;
					}
					>li:nth-child(2){
						width: 120px*@ip6;
					}
					>li:nth-child(3){
						width: 100px*@ip6;
					}
					>li:last-child {
						line-height: 50px*@ip6;
						width: 260px*@ip6;
					}
					>li:first-child p:nth-child(2) {
						margin-top: -5%;
					}
				}
			}
			>li:first-child {
				height: 40px*@ip6;
				background-color: #36394d;
				border-bottom: 1px solid #1b1b26;
				line-height: 40px*@ip6;
				>ol {
					height: 40px*@ip6;
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px*@ip6;
					>li {
						height: 40px*@ip6;
					}
					>li:last-child {
						padding-top: 0;
						line-height: 40px*@ip6;
					}
				}
			}
		}
		.info {
			height: 90px*@ip6;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			background: #242633;
			>li {
				height: 40px*@ip6;
				padding-left: 15px*@ip6;
				line-height: 40px*@ip6;
				font-size: 14px*@ip6;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
			}
		}
		.bottom {
			height: 100px*@ip6;
			background: #242633;
			>li {
				height: 40px*@ip6;
				line-height: 40px*@ip6;
				text-align: center;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
				font-size: 16px * @ip6;
			}
			>li:last-child {
				padding-top: 5px*@ip6;
			}
		}
	}
	/*ip6p及以上*/
	@media (min-width:411px) {
		#applydetail {
			height: 2104px/2 - 20px;
			padding-top: 50px;
		}
		.margin_trade {
			background: #242633;
			height: 308px/2;
			&:after {
				content: '';
				display: block;
				clear: both;
			}
			&:before {
				content: '';
				display: table;
			}
			p {
				font-weight: normal;
				margin-left: 15px;
				margin-top: 15px;
			}
		}
		.margin_trade li {
			width: 25%;
			margin-top: 13px;
		}
		.margin_trade li>div {
			margin: 0 auto;
		}
		.margin_trade_show {
			height: 260px/2;
			background: #242633;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			li {
				height: 40px;
				line-height: 40px;
				font-size: 14px;
				padding-left: 15px;
				&:nth-child(2) {
					border-top: 1px solid #1b1b26;
					border-bottom: 1px solid #1b1b26;
				}
			}
		}
		.notice {
			font-size: 14px;
			height: 70px;
			background: #242633;
			padding-left: 15px;
			&:before {
				content: '';
				display: table;
			}
			p:nth-child(1) {
				margin-top: 15px;
			}
		}
		ol>li {
			width: 25%;
			font-size: 14px;
		}
		.datalist {
			width: 414px;
			overflow-x: scroll;
			>li {
				height: 50px;
				width: 716px;
				border-bottom: 1px solid #1b1b26;
				line-height: 50px;
				background: #242633;
				>ol {
					height: 50px;
					width:650px; 
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px;
					>li {
						height: 50px;
						width: 150px;
					}
					>li:first-child p {
						font-size: 14px;
						height: 25px;
						line-height: 25px;
						margin-top: 3%;
					}
					>li:nth-child(2){
						width: 120px;
					}
					>li:nth-child(3){
						width: 100px;
					}
					>li:last-child {
						line-height: 50px;
						width: 260px;
					}
					>li:first-child p:nth-child(2) {
						margin-top: -5%;
					}
				}
			}
			>li:first-child {
				height: 40px;
				background-color: #36394d;
				border-bottom: 1px solid #1b1b26;
				line-height: 40px;
				>ol {
					height: 40px;
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					margin-left: 15px;
					>li {
						height: 40px;
					}
					>li:last-child {
						padding-top: 0;
						line-height: 40px;
					}
				}
			}
		}
		.info {
			height: 90px;
			border-bottom: 5px solid #1b1b26;
			border-top: 5px solid #1b1b26;
			background: #242633;
			>li {
				height: 40px;
				padding-left: 15px;
				line-height: 40px;
				font-size: 14px;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
			}
		}
		.bottom {
			height: 100px;
			background: #242633;
			>li {
				height: 40px;
				line-height: 40px;
				text-align: center;
			}
			>li:first-child {
				border-bottom: 1px solid #1b1b26;
				font-size: 16px * @ip6p;
			}
			>li:last-child {
				padding-top: 5px;
			}
		}
	}
</style>