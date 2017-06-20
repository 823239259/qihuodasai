<template>

	<div id="home">
		<topbar title="行情" :connected='!isconnected'></topbar>
		<button id="refresh" @tap="refresh"></button>
		<!--选择条-->
		<div id="selectbar">
			<ul>
				<li @tap="selectClass" class="fl fontsm current">欧美指数</li>
				<li @tap="selectClass" class="fl fontsm">亚洲指数</li>
				<li @tap="selectClass" class="fl fontsm">外汇</li>
				<li @tap="selectClass" class="fl fontsm">贵金属</li>
				<li @tap="selectClass" class="fl fontsm">商品</li>
				<li @tap="selectClass" class="fl fontsm">国内</li>
				<li @tap="selectClass" class="fl fontsm">全部</li>
				<!--用于留白-->
				<li class="fl fontsm">&nbsp;&nbsp;</li>
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
				<template>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontred">257.00</li>
							<li class="fl fontred">+0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontgreen">257.00</li>
							<li class="fl fontgreen">-0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontred">257.00</li>
							<li class="fl fontred">+0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontred">257.00</li>
							<li class="fl fontred">+0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontgreen">257.00</li>
							<li class="fl fontgreen">-0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontgreen">257.00</li>
							<li class="fl fontgreen">-0.03%</li>
						</ol>
					</li>
					<li class="list cl">
						<ol>
							<li class="fl">
								<h5 class="fontwhite">富时A50</h5>
								<h5 class="fontgray">CNQ16</h5>
							</li>
							<li class="fl">10086</li>
							<li class="fl fontgreen">257.00</li>
							<li class="fl fontgreen">-0.03%</li>
						</ol>
					</li>
				</template>
			</ul>
		</div>
	</div>

</template>

<script>
	import store from '../store'
	import topbar from '../components/Topbar.vue'
	export default {
		name: 'home',
		store,
		components: {
			topbar
		},
		computed: {
			isconnected() {
				return this.$store.state.isconnected
			}
		},
		methods: {
			refresh: function(e) {
				window.location.reload();
			},
			selectClass:function(e){
				$(e.target).addClass('current').siblings('li').removeClass('current'); 
			}
		},
		mounted: function() {
			$('#home').css('height', window.screen.height + 'px');
			$('#refresh').css({
				'transition': '1s linear',
				'transform': 'rotate(360deg)'
			});
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 100%;
	#home {
		background: #1b1b26;
		overflow-y: scroll;
		padding-top: 50px;
	}
	
	#selectbar {
		width: @width;
		overflow-x: scroll;
		padding: 2%;
		background-color: #36394d;
		border-bottom: 1px solid #1b1b26;
	}
	
	#selectbar ul {
		box-sizing: content-box;
		width: 150%;
	}
	
	#selectbar li {
		padding: 0.3% 0;
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
	
	#datalist>ul>li>ol>li:first-child {
		width: 29%;
	}
	
	#datalist>ul>li:first-child {
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
</style>