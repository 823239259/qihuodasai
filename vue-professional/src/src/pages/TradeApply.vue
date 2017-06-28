<template>
	<div id="tradeapply">
		<topbar title="开户"></topbar>
		<button id="refresh">客服</button>
		<!--轮播-->
		<div class="mui-slider">
			<div class="mui-slider-group">
				<!--第一个内容区容器-->
				<div class="mui-slider-item">
					<img id="banner1" src="/src/assets/img/banner01.png"/>
				</div>
			</div>
		</div>
		<!--列表-->
		<template>
			<ul class="datalist">
				<li>
					<ol>
						<li>
							<h5 class="fl">国际综合</h5>
							<p class="fr">一个账号可交易17个品种 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li>
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li>
									交易时间：<span>09:05-16:30, 17-29-01:30</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li>
							<h5 class="fl">富时A50</h5>
							<p class="fr">A股行情风向标 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li>
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li>
									交易时间：<span>09:05-16:30, 17-29-01:30</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li>
							<h5 class="fl">国际原油</h5>
							<p class="fr">一个账号可交易17个品种 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li>
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li>
									交易时间：<span>09:05-16:30, 17-29-01:30</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li>
							<h5 class="fl">恒指期货</h5>
							<p class="fr">一个账号可交易17个品种 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li>
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li>
									交易时间：<span>09:05-16:30, 17-29-01:30</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
				<li>
					<ol>
						<li>
							<h5 class="fl">随机菜单</h5>
							<p class="fr">一个账号可交易17个品种 <i>&nbsp;&nbsp;></i></p>
						</li>
						<li class="cl">
							<ul>
								<li>
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li>
									交易时间：<span>09:05-16:30, 17-29-01:30</span>
								</li>
							</ul>
						</li>
					</ol>
				</li>
			</ul>
		</template>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	export default {
		name: 'tradeapply',
		components: {
			topbar
		},
		mounted: function() {
			$('#banner1').attr('src', require("../../src/assets/img/banner01.png"));
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 100%;
	#tradeapply {
		padding-bottom: 50px;
		background-color: #1b1b26;
		padding-top: 50px;
	}
	
	.mui-slider {
		width: @width;
		height: @width*0.4;
		padding: 3%;
		background-color: #242633;
	}
	
	.datalist {
		overflow-y: scroll;
	}
	
	h5 {
		color: #ffd400;
		font-size: 14px;
	}
	
	li,
	p,
	span {
		font-size: 13px;
	}
	
	.datalist>li {
		width: @width;
		height: @width*0.24;
		background-color: #242633;
		padding: 3%;
		border-top: 5px solid #1b1b26;
	}
	
	p {
		color: #9ba1c2;
	}
	
	.cl {
		border-top: 1px solid #1b1b26;
	}
	
	.cl li {
		color: #9ba1c2;
	}
	
	.cl span {
		color: #fff;
	}
	
	ol {
		height: 100%;
	}
	
	ol>li:first-child {
		overflow: hidden;
		height: 45%;
	}
	
	ol>li:nth-child(2) {
		height: 55%;
	}
	#refresh {
		color:#fff;
		background-color: transparent;
		border: none;
		outline: none;
		position: fixed;
		z-index: 1000;
		right: 10%;
		top: 2%;
	}
</style>