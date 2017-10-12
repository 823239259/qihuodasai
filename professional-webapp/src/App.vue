<template>
	<div id="app">
		<div id="container_left">
				<ul>
					<li>
						<img src="./assets/images/logo.png" alt="" />
					</li>
					<li>
						<i class="spirt_1"></i>
						行情
					</li>
					<li>
						<i class="spirt_2"></i>
						交易
					</li>
					<li>
						<i class="spirt_3"></i>
						开户
					</li>
					<li>
						<i class="spirt_4"></i>
						直播
					</li>
					<li>
						<i class="spirt_5"></i>
						日历
					</li>
					<li>
						<i class="spirt_6"></i>
						下载
					</li>
					<li>
						<i class="spirt_7"></i>
						我的
					</li>
				</ul>
			</div>
		<div id="container_top">
			<ul>
				<li>放大</li>
				<li>在线客服</li>
				<li>客服热线</li>
				<li>登录</li>
				<li>注册</li>
			</ul>
		</div>
		<div id="container_buttom">
			<span>国际原油<label></label></span>
			<span>美黄金<label></label></span>
			<span>恒指期货<label></label></span>
		</div>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		name: 'app'
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "./assets/css/common.scss";
	/*容器框架*/
	#app {
	    width: 100%;
	    height: 980px;
	    min-width: 1280px;
	}
	/*左部导航*/
	#container_left {
	    width: 60px;
	    background-color: $depLight;
	    float: left;
	    height: 100%;
	    font-size: $fs12;
	    color: $yellow;
	    ul {
	        li {
	            display: block;
	            height: 60px;
	            text-align: center;
	        }
	    }
	}
	/*顶部栏*/
	#container_top {
	    width: 95%;
	    float: left;
	    background-color: $depLight;
	    height: 40px;
	    line-height: 40px;
	    margin: 5px 10px;
	    font-size: $fs12;
	}
	#container_top li {
	    float: left;
	}
	/*底部栏*/
	#container_buttom {
	    position: absolute;
	    top: 100%;
	    background-color: $bottom_color;
	    width: 100%;
	    height: 30px;
	    line-height: 30px;
	    font-size: $fs12;
	}
</style>