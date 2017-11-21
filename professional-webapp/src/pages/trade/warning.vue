<template>
	<div id="warning" v-show="show">
		<div class="bg"></div>
		<div class="warning">
			<div class="title">
				<h3>警告！</h3>
				<i class="ifont" @click="closeEvent">&#xe624;</i>
			</div>
			<div class="cont">
				<p class="tips">网络名称：服务发生中断</p>
				<div class="details">
					<p>
						<i class="ifont ifont_red" v-show="quoteShow">&#xe624;</i>
						<i class="ifont ifont_green" v-show="!quoteShow">&#xe62a;</i>
						<b>行情服务：</b>
						<span :class="{red: quoteShow}">{{quoteStatus}}</span>
					</p>
					<p>
						<i class="ifont ifont_red" v-show="tradeShow">&#xe624;</i>
						<i class="ifont ifont_green" v-show="!tradeShow">&#xe62a;</i>
						<b>交易服务：</b>
						<span :class="{red: tradeShow}">{{tradeStatus}}</span>
					</p>
				</div>
				<p class="status"><b v-show="statusShow">已断开，<span class="red">{{time}}s</span>后自动重连</b><b v-show="!statusShow">正在连接…</b><b class="red connect" @click="connectEvent">立即重连</b></p>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'warning',
		data(){
			return{
				show: false,
				time: '',
				timing: null,
				statusShow: true,
				quoteShow: true,
				tradeShow: true,
				quoteStatus: '已断开',
				tradeStatus: '已断开',
			}
		},
		computed: {
			warningType(){
				return this.$store.state.isshow.warningType;
			}
		},
		watch: {
			show: function(n, o){
				if(n && n == true){
					//倒计时
					this.timeEvent();
				}
			},
			warningType: function(n, o){
				if(n && n == 1){
					this.quoteShow = true;
				}else if(n && n == 2){
					this.quoteShow = false;
					this.quoteStatus = '正常';
				}
			}
		},
		methods: {
			closeEvent: function(){
				this.show = false;
				this.time = 0;
				clearInterval(this.timing);
			},
			timeEvent: function(){
				this.time = 5;
				this.timing = setInterval(function(){
					this.time--;
					if(this.time <= 0){
						clearInterval(this.timing);
						this.statusShow = false;
						setTimeout(function(){
							this.show = false;
							this.$router.push({path: '/index'});
							this.$store.state.account.isRefresh = 1;
						}.bind(this), 2000);
					}
				}.bind(this), 1000);
			},
			connectEvent: function(){
				if(this.statusShow == false) return;
				this.statusShow = false;
				setTimeout(function(){
					this.show = false;
					this.$router.push({path: '/index'});
					this.$store.state.account.isRefresh = 1;
				}.bind(this), 2000);
			}
		},
		mounted: function(){
			
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	.warning{
		width: 400px;
		height: 240px;
		overflow: hidden;
		background: $white;
		border-radius: 10px;
		position: absolute;
		top: 50%;
		left: 50%;
		z-index: 100;
		margin: -120px 0 0 -200px;
	}
	.title{
		height: 40px;
		line-height: 40px;
		text-align: center;
		background: $red;
		position: relative;
		h3{
			font-size: $fs16;
			color: $white;
		}
		i{
			position: absolute;
			top: 2px;
			right: 12px;
			color: $white;
			cursor: pointer;
		}
	}
	.cont{
		.tips{
			width: 400px;
			height: 40px;
			line-height: 40px;
			text-align: center;
			color: #292a33;
		}
		.details{
			height: 110px;
			padding: 30px 0 0 110px;
			border-top: 1px solid #e6e6e6;
			border-bottom: 1px solid #e6e6e6;
			p{
				font-size: $fs16;
				margin-bottom: 20px;
				b{
					color: #525566;
				}
				.ifont{
					margin-right: 10px;
					font-weight: bold;
				}
				.ifont_red{
					color: $red;
				}
				.ifont_green{
					color: $green;
					font-size: $fs18;
					margin-right: 7px;
				}
			}
		}
		span{
			color: $green;
			font-weight: bold;
			&.red{
				color: $red;
			}
		}
		.status{
			height: 50px;
			line-height: 50px;
			b{
				display: inline-block;
				width: 50%;
				text-align: center;
				color: #525566;
				&.red{
					color: $red;
				}
			}
			.connect{
				cursor: pointer;
			}
		}
	}
</style>