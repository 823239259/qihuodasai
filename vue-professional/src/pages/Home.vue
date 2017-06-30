<template>
	<div id="home">
		<div id="disconnect" v-show='!isconnected'>
			<div><s></s>&nbsp;&nbsp;行情连接已断开，<span>{{time}}</span>秒后自动重连</div>
		</div>
		<div>
			<topbar title="行情" :connected='!isconnected'></topbar>
			<button id="refresh" @tap="refresh"></button>
			<!--选择条-->

			<div id="selectbar">
				<ul>
					<li @tap="selectClass" class="fl fontsm current">商品</li>
					<li @tap="selectClass" class="fl fontsm">亚洲指数</li>
					<li @tap="selectClass" class="fl fontsm">欧美指数</li>
				</ul>
			</div>
			<div id="datalist">
				<ul>
					<li>
						<ol>
							<li class="fl fontgray">合约名称</li>
							<li class="fl fontgray">成交量</li>
							<li class="fl fontgray">最新价</li>
							<li class="fl fontgray">涨跌幅 <i></i></li>
						</ol>
					</li>
				</ul>
				<ul>
					<template>
						<li class="list cl" @tap='toDetail'>
							<ol>
								<li class="fl">
									<h5 class="fontwhite">11富时A50</h5>
									<h5 class="fontgray">CNQ16</h5>
								</li>
								<li class="fl">10086</li>
								<li class="fl fontred">257.00</li>
								<li class="fl fontred">+0.03%</li>
							</ol>
						</li>
					</template>
				</ul>
			</div>
		</div>

	</div>

</template>

<script>
	//	import store from '../store'
	import topbar from '../components/Topbar.vue'
	export default {
		name: 'home',
		//		store,
		data(){
			return {
				time:3
			}
		},
		components: {
			topbar
		},
		computed: {
			isconnected() {
				return this.$store.state.isshow.isconnected
			}
		},
		watch: {
			isconnected: function(n, o) {
				var sw = window.screen.width;
				if(n == true) {
					if(sw <= 370) {
						$('#home').css('padding-top', 50 * 0.7729 + 'px');
						$('#selectbar').css('top', 50 * 0.7729 + 'px');
					} else if(sw <= 410) {
						$('#home').css('padding-top', 50 * 0.9058 + 'px');
						$('#selectbar').css('top', 50 * 0.9058 + 'px');
					} else {
						$('#home').css('padding-top', '50px');
						$('#selectbar').css('top', '50px');
					}
				} else {
					if(sw <= 370) {
						$('#home').css('padding-top', 50 * 0.7729 + 30 + 'px');
						$('#selectbar').css('top', 50 * 0.7729 + 30 + 'px');
					} else if(sw <= 410) {
						$('#home').css('padding-top', 50 * 0.9058 + 30 + 'px');
						$('#selectbar').css('top', 50 * 0.9058 + 30 + 'px');
					} else {
						$('#home').css('padding-top', '80px');
						$('#selectbar').css('top', '80px');
					}
				}
			}
		},
		methods: {
			refresh: function(e) {
				this.$router.push({
					path:'/space'
				});
			},
			selectClass: function(e) {
				$(e.target).addClass('current').siblings('li').removeClass('current');
			},
			toDetail: function() {
				this.$router.push({
					path: '/orderdetail'
				})
			}
		},
		mounted: function() {
			//用于控制未连接重连
			var timer=setInterval(function(){
					this.time--;
				}.bind(this),1000);
			setTimeout(function(){
				this.$store.state.isshow.isconnected=true  //store中取值，专用来控制是否连接成功的显示与否
				clearInterval(timer);
			}.bind(this),3000);
			
			var sw = window.screen.width;
			if(this.isconnected) {
				if(sw <= 370) {
					$('#home').css('padding-top', 50 * 0.7729 + 'px');
					$('#selectbar').css('top', 50 * 0.7729 + 'px');
				} else if(sw <= 410) {
					$('#home').css('padding-top', 50 * 0.9058 + 'px');
					$('#selectbar').css('top', 50 * 0.9058 + 'px');
				} else {
					$('#home').css('padding-top', '50px');
					$('#selectbar').css('top', '50px');
				}
			} else {
				if(sw <= 370) {
					$('#home').css('padding-top', 50 * 0.7729 + 30 + 'px');
					$('#selectbar').css('top', 50 * 0.7729 + 30 + 'px');
				} else if(sw <= 410) {
					$('#home').css('padding-top', 50 * 0.9058 + 30 + 'px');
					$('#selectbar').css('top', 50 * 0.9058 + 30 + 'px');
				} else {
					$('#home').css('padding-top', '80px');
					$('#selectbar').css('top', '80px');
				}

			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 100%;
	#home {
		background: #1b1b26;
	}
	
	#home>div:only-child {
		position: absolute;
		top: 80px;
	}
	
	#disconnect {
		width: 100%;
		height: 30px;
		background-color: #fff7cc;
		line-height: 30px;
		font-size: 12px;
		color: #2a2a31;
		position: fixed;
		/*top: 50px;*/
	}
	
	#selectbar {
		width: @width;
		overflow-x: scroll;
		padding: 0 2%;
		line-height: 45px;
		background-color: #242633;
		border-bottom: 1px solid #1b1b26;
		height: 45px;
		position: fixed;
	}
	#selectbar ul {
		box-sizing: content-box;
		width: 150%;
	}
	
	#selectbar li {
		height: 45px;
		margin: 0 2%;
		color: #ccd5ff;
		text-align: center;
		border-bottom: 3px solid transparent;
		/*border-bottom: 3px solid #ffd400;*/
	}
	
	#selectbar>ul>li.current {
		color: #ffd400;
		border-bottom: 3px solid #ffd400;
	}
	
	#datalist>ul>li>ol>li {
		text-align: center;
		width: 23%;
		font-size: 14px;
	}
	
	#datalist {
		overflow: hidden;
		margin-top: 45px;
	}
	
	#datalist>ul>li>ol>li:first-child {
		width: 29%;
	}
	
	#datalist>ul:first-child {
		width: 100%;
		position: fixed;
	}
	
	#datalist>ul:nth-child(2) {
		margin-top: 40px;
	}
	
	#datalist>ul:first-child>li:first-child {
		width: @width;
		line-height: 40px;
		background: #36394d;
	}
	
	#datalist>ul>li>ol {
		width: 100%;
		height: 40px;
	}
	
	i {
		display: inline-block;
		width: 9px;
		height: 12px;
		background-size: 100% 100%;
		background-image: url(../assets/img/updown.png);
	}
	
	s {
		width: 15px;
		height: 15px;
		background-image: url(../assets/img/tanhao.png);
		background-size: 100% 100%;
		background-repeat: no-repeat;
		display: inline-block;
		transform: translateY(3px);
		margin-left: (15/414)*414px;
	}
	
	.list {
		height: 55px;
		width: 100%;
		line-height: 55px;
		background-color: #242633;
		border-bottom: 1px solid #1b1b26;
	}
	
	.list>ol>li:nth-child(2) {
		color: #fff;
	}
	
	.list h5 {
		margin-top: 8%;
	}
	
	#refresh {
		width: 17px;
		height: 17px;
		background-color: transparent;
		border: none;
		outline: none;
		background-image: url('../assets/img/refresh.png');
		background-size: 100% 100%;
		position: fixed;
		z-index: 1000;
		right: 7%;
		top: 2%;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#home {
			background: #1b1b26;
			height:736px*@ip5 - 20px;
		}
		#disconnect {
			top: 50px*@ip5
		}
		#refresh {
			width: 17px*@ip5;
			height: 17px*@ip5;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px*@ip5;
			top: 16px*@ip5;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip5+45px;*/
		}
		#home {
			padding-top: 50px*@ip5+30px;
			padding-bottom: 50px*@ip5;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip5+30px;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#home {
			background: #1b1b26;
			height:736px*@ip6 - 20px;
		}
		#disconnect {
			top: 50px*@ip6
		}
		#refresh {
			width: 17px*@ip6;
			height: 17px*@ip6;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px*@ip6;
			top: 16px*@ip6;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip6+45px;*/
		}
		#home {
			padding-top: 50px*@ip6+30px;
			padding-bottom: 50px*@ip6;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip6+30px;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#home {
			background: #1b1b26;
			height:736px*@ip6p - 20px;
		}
		#disconnect {
			top: 50px*@ip6p
		}
		#refresh {
			width: 17px*@ip6p;
			height: 17px*@ip6p;
			background-color: transparent;
			border: none;
			outline: none;
			background-image: url('../assets/img/refresh.png');
			background-size: 100% 100%;
			position: fixed;
			z-index: 1000;
			right: 16px;
			top: 16px;
		}
		#datalist>ul>li:first-child {
			/*top: 80px*@ip6p+45px;*/
		}
		#home {
			padding-top: 50px*@ip6p+30px;
			padding-bottom: 50px*@ip6p;
		}
		#datalist>ul>li:nth-child(2) {
			/*margin-top: 85px;*/
		}
		#selectbar {
			top: 50px*@ip6p+30px;
		}
	}
</style>