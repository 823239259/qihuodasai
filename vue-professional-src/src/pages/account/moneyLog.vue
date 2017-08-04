<template>
	<div id="moneyLog">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="资金明细"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="money_total">
				<div class="money_total_row">
					<span>收入：</span>
					<span class="white">{{incomeNum}}笔</span>
					<span class="yellow">{{incomeMoney}}元</span>
				</div>
				<div class="money_total_row">
					<span>支出：</span>
					<span class="white">{{outlayNum}}笔</span>
					<span class="yellow">{{outlayMoney}}元</span>
				</div>
			</div>
			<div class="log_list">
				<ul>
					<template v-for="key in logList.fundList">
					<li>
						<p class="time">{{key.payTime | getTime('yyyy-MM-dd HH:mm')}}</p>
						<p class="log_details" :class="{white: key.money>=0, blue: key.money < 0}"><span>{{key.money | moneyEvent}}</span><span>{{key.remark}}</span></p>
					</li>
					</template>
				</ul>
			</div>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import pro from '../../assets/common.js'
	export default{
		name: 'moneyLog',
		components: {topbar, back, cs, tipsDialog},
		filters: {
			moneyEvent: function(e){
				if(e){
					if(e > 0){
						return '+' + e;
					}else{
						return e;
					}
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
				msg: '',
				logList: '',
				incomeNum: 0,
				outlayNum: 0,
				incomeMoney: 0,
				outlayMoney: 0
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			userInfo: function(){
				return this.$store.state.account;
			},
		},
		methods: {
			getMoneyDetails: function(){
				this.$http.post(this.PATH + '/user/fund/list', {emulateJSON: true}, {
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
	            			this.logList = data.data;
	            			this.incomeNum = this.logList.incomeNum;
	            			this.outlayNum = this.logList.outlayNum;
	            			this.incomeMoney = pro.parseTwoFloat(this.logList.incomeMoney);
	            			this.outlayMoney = pro.parseTwoFloat(this.logList.outlayMoney);
						}
					}else{
						switch (data.code){
							case '-1':
								this.$children[0].isShow = true;
								this.msg = '认证失败';
								break;
							case '3':
								this.$children[0].isShow = true;
								this.msg = '用户信息不存在';
								break;
							default:
								break;
						}
					}
				}.bind(this), function() {
					this.$children[0].isShow = true;
					this.msg = '服务器连接失败'
				});
			}
		},
		mounted: function(){
			//页面高度计算
			var h = $("#topbar").height() + $(".money_total").height();
			$(".log_list").css("height", window.screen.height - 20 - h + 'px');
		},
		activated: function(){
			//获取资金明细
			this.getMoneyDetails();
		}
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #moneyLog{
    	width: 100%;
    	overflow: hidden;
    	.page_cont{
    		position: fixed;
    		top: 50px;
    		left: 0;
    		width: 100%;
    		.money_total{
	    		height: 88px;
	    		overflow: hidden;
	    		background: @deepblue;
	    		.money_total_row{
	    			height: 44px;
	    			line-height: 44px;
	    			padding: 0 15px;
	    			border-bottom: 1px solid @black;
	    			span{
	    				font-size: @fs18;
	    				&:first-child{
	    					color: @blue;
	    				}
	    				&:nth-child(2){
	    					margin-right: 15px;
	    				}
	    			}
	    		}
    		}
    		.log_list{
    			overflow-y: scroll;
    			li{
    				margin-top: 10px;
    				background: @deepblue;
    				.time{
    					height: 32px;
    					line-height: 32px;
    					background: #2d3040;
    					border-bottom: 1px solid @black;
    					padding: 0 15px;
    					color: #7a7f99;
    					font-size: @fs14;
    				}
    				.log_details{
    					width: 100%;
    					overflow: hidden;
    					padding: 0 15px;
    					&.white{
    						color: @white;
    					}
    					&.blue{
    						color: @blue;
    					}
    					span{
    						float: left;
    						display: inline-block;
    						width: 300px;
    						font-size: @fs14;
    						margin: 10px 0;
    						&:first-child{
    							width: 65px;
    							margin-right: 15px;
    						}
    					}
    				}
    			}
    		}
    		
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#moneyLog{
    	width: 100%;
    	overflow: hidden;
    	.page_cont{
    		position: fixed;
    		top: 50px*@ip6;
    		left: 0;
    		width: 100%;
    		.money_total{
	    		height: 88px*@ip6;
	    		overflow: hidden;
	    		background: @deepblue;
	    		.money_total_row{
	    			height: 44px*@ip6;
	    			line-height: 44px*@ip6;
	    			padding: 0 15px*@ip6;
	    			border-bottom: 1px solid @black;
	    			span{
	    				font-size: @fs18*@ip6;
	    				&:first-child{
	    					color: @blue;
	    				}
	    				&:nth-child(2){
	    					margin-right: 15px*@ip6;
	    				}
	    			}
	    		}
    		}
    		.log_list{
    			overflow-y: scroll;
    			li{
    				margin-top: 10px*@ip6;
    				background: @deepblue;
    				.time{
    					height: 32px*@ip6;
    					line-height: 32px*@ip6;
    					background: #2d3040;
    					border-bottom: 1px solid @black;
    					padding: 0 15px*@ip6;
    					color: #7a7f99;
    					font-size: @fs14*@ip6;
    				}
    				.log_details{
    					width: 100%;
    					overflow: hidden;
    					padding: 0 15px*@ip6;
    					&.white{
    						color: @white;
    					}
    					&.blue{
    						color: @blue;
    					}
    					span{
    						float: left;
    						display: inline-block;
    						width: 300px*@ip6;
    						font-size: @fs14*@ip6;
    						margin: 10px*@ip6 0;
    						&:first-child{
    							width: 65px*@ip6;
    							margin-right: 15px*@ip6;
    						}
    					}
    				}
    			}
    		}
    		
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#moneyLog{
    	width: 100%;
    	overflow: hidden;
    	.page_cont{
    		position: fixed;
    		top: 50px*@ip5;
    		left: 0;
    		width: 100%;
    		.money_total{
	    		height: 88px*@ip5;
	    		overflow: hidden;
	    		background: @deepblue;
	    		.money_total_row{
	    			height: 44px*@ip5;
	    			line-height: 44px*@ip5;
	    			padding: 0 15px*@ip5;
	    			border-bottom: 1px solid @black;
	    			span{
	    				font-size: @fs18*@ip5;
	    				&:first-child{
	    					color: @blue;
	    				}
	    				&:nth-child(2){
	    					margin-right: 15px*@ip5;
	    				}
	    			}
	    		}
    		}
    		.log_list{
    			overflow-y: scroll;
    			li{
    				margin-top: 10px*@ip5;
    				background: @deepblue;
    				.time{
    					height: 32px*@ip5;
    					line-height: 32px*@ip5;
    					background: #2d3040;
    					border-bottom: 1px solid @black;
    					padding: 0 15px*@ip5;
    					color: #7a7f99;
    					font-size: @fs14*@ip5;
    				}
    				.log_details{
    					width: 100%;
    					overflow: hidden;
    					padding: 0 15px*@ip5;
    					&.white{
    						color: @white;
    					}
    					&.blue{
    						color: @blue;
    					}
    					span{
    						float: left;
    						display: inline-block;
    						width: 300px*@ip5;
    						font-size: @fs14*@ip5;
    						margin: 10px*@ip5 0;
    						&:first-child{
    							width: 65px*@ip5;
    							margin-right: 15px*@ip5;
    						}
    					}
    				}
    			}
    		}
    		
    	}
    }
}
</style>