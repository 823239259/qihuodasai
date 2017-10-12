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
					<i>客服热线<span>400-852-8008</span></i>
				</li>
				<li>
					<span id="register" v-on:click="toRegister">注册</span>
				</li>
				<li>
					<span id="login" v-on:click="toLogin">登录</span>
				</li>
			</ul>
		</div>
		<div id="container_buttom">
			<span>国际原油<label></label></span>
			<span>美黄金<label></label></span>
			<span>恒指期货<label></label></span>
		</div>
		<router-view></router-view>
		<div id="login" style="display: none;">
			<input type="text"  id=""  placeholder="请输入手机号码"/>
			<input type="password"  id=""  placeholder="请输入密码"/>
			<p>忘记密码</p>
			<button>登录</button>
			<p>还没有期货大赛账号？<span>立即注册</span></p>
		</div>
		<div id="register" style="display: none;">
			<input type="text"  id=""  placeholder="请输入手机号码"/>
			<input type="password"  id=""  placeholder="验证码"/>
			<input type="password" id="" placeholder="请输入密码（6-16位密码）" />
			<p>注册即表示同意并已阅读<span>《用户注册协议》</span></p>
			<button>注册</button>
			<p>已有期货大赛账号？<span>立即登录</span></p>
		</div>
		<div id="forgetPassword" style="display: none;">
			<input type="text"  id="" placeholder="请输入手机号码" />
			<input type="password" id="" placeholder="验证码" />
			<label>60s</label>
			<button>下一步</button>
			<p>没有期货大赛账号？<span>立即注册</span></p>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'app',
		methods : {
			toRegister : function(){
				layer.open({
					  title: '注册',
					  content: '这里是注册'
					}); 
			},
			toLogin : function(item){
				layer.open({  
                     type : 1,  
                     title : "登录",  
                     area : ["300px", "230px"], 
                     content : $("#login"),  
                     btn : ["确认"],  
                             yes: function (index) {  
  
                                 alert("确认");  
  
                             },  
                             cancel: function () { //点击关闭按钮  
                     }  
                 });  
			}
		}
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
	    ul {
	    	li {
	    		float: left;
	    		margin: 0 20px;
	    		span {
	    			font-size: $fs12;
	    		}
	    		&:nth-child(1){
	    			i {
		    			color: $lightblue;
		    		}
	    		}
	    		&:nth-child(2){
	    			i {
		    			color: $yellow;
		    		}
		    		span {
		    			color: $yellow;
		    		}
	    		}
	    		&:nth-child(3){
	    			i {
		    			color: $lightblue;
		    		}
	    		}
	    		&:nth-child(4){
	    			float: right;
	    		}
	    		&:nth-child(5){
	    			float: right;
	    		}
	    		
		    }
	    }
	    #register {
	    	border: 1px solid $yellow;
	    	border-radius: 10px;
	    	padding: 5px 8px;	
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