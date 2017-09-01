<template>
	<div id="tradeRecord">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="操盘记录"></topbar>
		<back></back>
		<cs title="操盘细则" type="1" @tap.native="toTradersRules"></cs>
		<div class="page_cont">
			<ul>
				<template v-for="key in tradeList">
					<li @tap="toDetails" :type="key.stateType" :id="key.id">
						<div class="record_title">
							<span :class="{yellow: key.stateType == 1, blue: key.stateType == 4, grey: key.stateType == 5, deepblue: key.stateType == 6}">{{key.stateType | operateState}}</span>
							<i class="icon_arrow fr"></i>
							<span class="fr">{{key.appTime | getTime('yyyy-MM-dd HH:mm')}}</span>
						</div>
						<div class="record_cont">
							<p>交易品种：<span>{{key.businessType | varieties}}</span></p>
							<p>总操盘资金：<span>{{key.traderTotal}}美元</span></p>
							<p>开仓手数：<span class="spe">{{key.tranLever | operateNumber}}</span></p>
							<p>亏损平仓线：<span>{{key.lineLoss}}美元</span></p>
						</div>
					</li>
				</template>
			</ul>
		</div>
		<!--无操盘记录-->
		<div class="empty" id="empty" v-show="emptyShow">
			<i class="icon_none"></i>
			<p>您还没有操盘记录</p>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	export default{
		name:'tradeRecord',
		components: {topbar, back, cs, tipsDialog},
		filters: {
			varieties: function(e){    //交易品种
				switch(e) {
					case 8:
						return "国际综合";
						break;
					case 7:
						return "恒指期货";
						break;
					case 6:
						return "国际原油";
						break;
					case 0:
						return "富时A50";
						break;
				}
			},
			operateState: function(e){     //状态
				switch(e) {
					case 1:
						return "开户中";
						break;
					case 2:
						return "申请结算";
						break;
					case 3:
						return "待结算";
					case 4:
						return "操盘中";
						break;
					case 5:
						return "开户失败";
						break;
					case 6:
						return "已结算";
						break;
				}
			},
			operateNumber: function(e){
				if(!e){
					return "初始可持仓手数";	
				}else {
					return e +"手";	
				}
			},
			getTime: function(e, format) {
				var t = new Date(e * 1000);
				var tf = function(i) {
					return(i < 10 ? '0' : '') + i
				};
				return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
					switch(a) {
						case 'yyyy':
							return tf(t.getFullYear());
							break;
						case 'MM':
							return tf(t.getMonth() + 1);
							break;
						case 'mm':
							return tf(t.getMinutes());
							break;
						case 'dd':
							return tf(t.getDate());
							break;
						case 'HH':
							return tf(t.getHours());
							break;
						case 'ss':
							return tf(t.getSeconds());
							break;
					};
				});
			}
		},
		data(){
			return {
				emptyShow: false,
				tradeList: '',
				userInfo: ''
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			}
		},
		methods: {
			toTradersRules: function(){
				this.$router.push({path: '/tradersRules'});
			},
			toDetails: function(e){
				var type = $(e.currentTarget).attr("type");
				var id = $(e.currentTarget).attr("id");
				this.$router.push({path: '/tradeDetails', query: {type: type, id: id}});
			},
			getOperateDetails: function(){
				this.$http.post(this.PATH + '/user/ftrade/list', {emulateJSON: true}, {
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {},
					timeout: 5000
				}).then(function(e) {
					var data = e.body;
					if(data.success == true ){
						if(data.code == 1){
	            			this.tradeList = data.data.tradeList;
	            			if(this.tradeList == null) this.emptyShow = true;
						}
					}else{
						switch (data.code){
							case '-1':
								this.$children[0].isShow = true;
								this.msg = '认证失败';
								break;
							default:
								break;
						}
					}
				}.bind(this), function() {
					this.$children[0].isShow = true;
					this.msg = '网络不给力，请稍后再试！'
				});
			}
		},
		mounted: function(){
			//页面高度计算
			var h = window.screen.height - $("#topbar").height() - 20;
			$(".page_cont").height(h);
		},
		activated: function(){
			this.userInfo = JSON.parse(localStorage.user);
			//获取操盘记录列表
			this.getOperateDetails();
		}
		
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #tradeRecord{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    	.page_cont{
    		width: 100%;
    		overflow-y: scroll;
    		li{
    			overflow: hidden;
    			background: @deepblue;
    			margin-bottom: 10px;
    			&.settlement{
    				background: #2d3040;
    			}
    			&:last-child{
    				margin: 0;
    			}
    			.record_title{
    				height: 44px;
    				padding: 0 15px;
    				border-bottom: 1px solid @black;
    				span{
    					line-height: 44px;
    					color: #7a7f99;
    					font-size: @fs14;
    					&:first-child{
    						display: inline-block;
    						height: 22px;
    						line-height: 22px;
    						padding: 0 15px;
    						overflow: hidden;
    						font-size: @fs14;
    						color: @black;
    						border-radius: 4px;
    						margin: 11px;
    					}
    					&.yellow{
    						background: @yellow;
    					}
    					&.blue{
    						background: @blue;
    					}
    					&.deepblue{
    						color: @blue;
    						background: @deepblueshallow;
    					}
    					&.grey{
    						background: #ddd;
    					}
    				}
    				.icon_arrow{
    					width: 24px;
    					height: 44px;
    					background: url(../../assets/img/arrow.png) no-repeat right 15px;
    					background-size: 8px 14px;
    				}
    			}
    			.record_cont{
    				height: 88px;
    				padding: 18px 15px 12px 15px;
    				p{
    					float: left;
    					width: 50%;
    					line-height: 28px;
    					font-size: @fs16;
    					color: @blue;
    					&:nth-child(2), &:nth-child(4){
    						text-align: right;
    					}
    					span{
    						color: @white;
    						&.spe{
    							font-size: @fs14;
    							color: #7a7f99;
    						}
    					}
    				}
    			}
    		}
    	}
    	.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px*@ip6 0 0 -207px*@ip6;
			width: 414px*@ip6;
			height: 92px*@ip6;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px*@ip6;
				height: 56px*@ip6;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px*@ip6;
			}
		}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#tradeRecord{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		width: 100%;
    		overflow-y: scroll;
    		li{
    			overflow: hidden;
    			background: @deepblue;
    			margin-bottom: 10px*@ip6;
    			&.settlement{
    				background: #2d3040;
    			}
    			&:last-child{
    				margin: 0;
    			}
    			.record_title{
    				height: 44px*@ip6;
    				padding: 0 15px*@ip6;
    				border-bottom: 1px solid @black;
    				span{
    					line-height: 44px*@ip6;
    					color: #7a7f99;
    					font-size: @fs14*@ip6;
    					&:first-child{
    						display: inline-block;
    						height: 22px*@ip6;
    						line-height: 22px*@ip6;
    						padding: 0 15px*@ip6;
    						overflow: hidden;
    						font-size: @fs14*@ip6;
    						color: @black;
    						border-radius: 4px*@ip6;
    						margin: 11px*@ip6;
    					}
    					&.yellow{
    						background: @yellow;
    					}
    					&.blue{
    						background: @blue;
    					}
    					&.deepblue{
    						color: @blue;
    						background: @deepblueshallow;
    					}
    					&.grey{
    						background: #ddd;
    					}
    				}
    				.icon_arrow{
    					width: 24px*@ip6;
    					height: 44px*@ip6;
    					background: url(../../assets/img/arrow.png) no-repeat right 15px*@ip6;
    					background-size: 8px*@ip6 14px*@ip6;
    				}
    			}
    			.record_cont{
    				height: 88px*@ip6;
    				padding: 18px*@ip6 15px*@ip6 12px*@ip6 15px*@ip6;
    				p{
    					float: left;
    					width: 50%;
    					line-height: 28px*@ip6;
    					font-size: @fs16*@ip6;
    					color: @blue;
    					&:nth-child(2), &:nth-child(4){
    						text-align: right;
    					}
    					span{
    						color: @white;
    						&.spe{
    							font-size: @fs14*@ip6;
    							color: #7a7f99;
    						}
    					}
    				}
    			}
    		}
    	}
    	.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px*@ip6 0 0 -207px*@ip6;
			width: 414px*@ip6;
			height: 92px*@ip6;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px*@ip6;
				height: 56px*@ip6;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px*@ip6;
			}
		}
    }
}
/*ip5*/
@media(max-width:370px) {
	#tradeRecord{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		width: 100%;
    		overflow-y: scroll;
    		li{
    			overflow: hidden;
    			background: @deepblue;
    			margin-bottom: 10px*@ip5;
    			&.settlement{
    				background: #2d3040;
    			}
    			&:last-child{
    				margin: 0;
    			}
    			.record_title{
    				height: 44px*@ip5;
    				padding: 0 15px*@ip5;
    				border-bottom: 1px solid @black;
    				span{
    					line-height: 44px*@ip5;
    					color: #7a7f99;
    					font-size: @fs14*@ip5;
    					&:first-child{
    						display: inline-block;
    						height: 22px*@ip5;
    						line-height: 22px*@ip5;
    						padding: 0 15px*@ip5;
    						overflow: hidden;
    						font-size: @fs14*@ip5;
    						color: @black;
    						border-radius: 4px*@ip5;
    						margin: 11px*@ip5;
    					}
    					&.yellow{
    						background: @yellow;
    					}
    					&.blue{
    						background: @blue;
    					}
    					&.deepblue{
    						color: @blue;
    						background: @deepblueshallow;
    					}
    					&.grey{
    						background: #ddd;
    					}
    				}
    				.icon_arrow{
    					width: 24px*@ip5;
    					height: 44px*@ip5;
    					background: url(../../assets/img/arrow.png) no-repeat right 15px*@ip5;
    					background-size: 8px*@ip5 14px*@ip5;
    				}
    			}
    			.record_cont{
    				height: 88px*@ip5;
    				padding: 18px*@ip5 15px*@ip5 12px*@ip5 15px*@ip5;
    				p{
    					float: left;
    					width: 50%;
    					line-height: 28px*@ip5;
    					font-size: @fs16*@ip5;
    					color: @blue;
    					&:nth-child(2), &:nth-child(4){
    						text-align: right;
    					}
    					span{
    						color: @white;
    						&.spe{
    							font-size: @fs14*@ip5;
    							color: #7a7f99;
    						}
    					}
    				}
    			}
    		}
    	}
    	.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px*@ip5 0 0 -207px*@ip5;
			width: 414px*@ip5;
			height: 92px*@ip5;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px*@ip5;
				height: 56px*@ip5;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px*@ip5;
			}
		}
    }
}
</style>