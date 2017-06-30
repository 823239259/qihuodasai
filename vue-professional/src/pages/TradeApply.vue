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
								<li class="lii">
									交易手续费：<span>19.5元/单边 起</span>
								</li>
								<li class="lii">
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
	
	/*ip5*/
@media(max-width:370px) {
#tradeapply {
		padding-bottom: 50px*@ip5;
		background-color: #1b1b26;
		padding-top: 50px*@ip5;
		height:736px*@ip5 - 20px;
	}
	
	.mui-slider {
		width: @width;
		height: 150px*@ip5;
		padding: 15px*@ip5;
		background-color: #242633;
	}
	.mui-slider-group{
		height: 120px*@ip5;
	}
	img{
		width: 100%;
		height: 120px*@ip5;
		vertical-align: bottom;
	}
	
	.datalist {
		overflow-y: scroll;
	}
	
	h5 {
		margin-top: 14px*@ip5;
		color: #ffd400;
		font-size: 14px*@ip5;
	}
	
	li,
	p,
	span {
		font-size: 13px*@ip5;
	}
	
	.datalist>li {
		width: @width;
		height: 90px*@ip5;
		background-color: #242633;
		padding:0 15px*@ip5;
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
		height: 40px*@ip5;
		line-height: 40px*@ip5;
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
	ol>li:nth-child(2)>ul>li{
		transform: scale(0.8);
		margin-left: -32px;
	}
	ol>li:nth-child(2)>ul>li:nth-child(2){
		margin-top: -8px;
		
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
     #tradeapply {
		padding-bottom: 50px*@ip6;
		background-color: #1b1b26;
		padding-top: 50px*@ip6;
		height:736px*@ip6 - 20px;
	}
	
	.mui-slider {
		width: @width;
		height: 150px*@ip6;
		padding: 15px*@ip6;
		background-color: #242633;
	}
	.mui-slider-group{
		height: 120px*@ip6;
	}
	img{
		width: 100%;
		height: 120px*@ip6;
		vertical-align: bottom;
	}
	
	.datalist {
		overflow-y: scroll;
	}
	
	h5 {
		color: #ffd400;
		font-size: 14px*@ip6;
		margin-top: 14px*@ip6;
	}
	
	li,
	p,
	span {
		font-size: 13px*@ip6;
	}
	
	.datalist>li {
		width: @width;
		height: 90px*@ip6;
		background-color: #242633;
		padding:0 15px*@ip6;
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
				height: 40px*@ip6;
		line-height: 40px*@ip6;
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
	
}
/*ip6p及以上*/
@media (min-width:411px) {
    #tradeapply {
		padding-bottom: 50px;
		background-color: #1b1b26;
		padding-top: 50px;
		height:736px-20px;
	}
	
	.mui-slider {
		width: @width;
		height: 150px;
		padding: 15px;
		background-color: #242633;
	}
	.mui-slider-group{
		height: 120px;
	}
	img{
		width: 100%;
		height: 120px;
		vertical-align: bottom;
	}
	
	.datalist {
		overflow-y: scroll;
	}
	
	h5 {
		color: #ffd400;
		font-size: 14px;
		margin-top: 14px
	}
	
	li,
	p,
	span {
		font-size: 13px;
	}
	
	.datalist>li {
		width: @width;
		height: 90px;
		background-color: #242633;
		padding: 0 15px;
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
		height: 40px;
		line-height: 40px;
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
}
</style>