<template>
	<div id="app">
		<div id="container_left">
				<ul>
					<li>
						<img src="./assets/images/logo.png" alt="logo" />
					</li>
					<li>
						<img src="./assets/images/icon_quote.png" alt="行情" />
						<p>行情</p>
					</li>
					<li>
						<img src="./assets/images/icon_trade.png" alt="交易" />
						<p>交易</p>
					</li>
					<li>
						<img src="./assets/images/icon_open.png" alt="开户" />
						<p>开户</p>
					</li>
					<li>
						<img src="./assets/images/icon_live.png" alt="直播" />
						<p>直播</p>
					</li>
					<li>
						<img src="./assets/images/icon_calendar.png" alt="日历" />
						<p>日历</p>
					</li>
					<li>
						<img src="./assets/images/icon_download.png" alt="下载" />
						<p>下载</p>
					</li>
					<li>
						<img src="./assets/images/icon_account.png" alt="我的" />
						<p>我的</p>
					</li>
				</ul>
			</div>
		<div id="container_top">
			<ul>
				<li>
					<i class="ifont">&#xe62e;</i>
				</li>
				<li>
					<i class="ifont">&#xe68f;</i>
					<span>在线客服</span>
				</li>
				<li>
					<i class="ifont">&#xe611;</i>
					<span>客服热线:400-852-8008</span>
				</li>
				<li>
					<span id="login">登录</span>
				</li>
				<li>
					<span id="register">注册</span>
				</li>
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
	    background-color: $blue;
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
	    background-color: $blue;
	    height: 40px;
	    line-height: 40px;
	    margin: 5px 10px;
	    font-size: $fs12;
	    ul{
	    	li{
	    		float: left;
	    		i{
	    			color: $lightblue;
	    		}
	    		span{
	    			font-size: $fs12;
	    		}
		    }
	    }
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