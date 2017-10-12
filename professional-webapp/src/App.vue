<template>
	<div id="app">
		<div id="nav" class="container_left">
			<ul>
				<li>
					<img src="./assets/images/logo.png" alt="logo" />
				</li>
				<li class="current">
					<i class="icon icon_quote"></i>
					<p>行情</p>
				</li>
				<li>
					<i class="icon icon_trade"></i>
					<p>交易</p>
				</li>
				<li>
					<i class="icon icon_open"></i>
					<p>开户</p>
				</li>
				<li>
					<i class="icon icon_live"></i>
					<p>直播</p>
				</li>
				<li>
					<i class="icon icon_calendar"></i>
					<p>日历</p>
				</li>
				<li>
					<i class="icon icon_download"></i>
					<p>下载</p>
				</li>
				<li>
					<i class="icon icon_account"></i>
					<p>我的</p>
				</li>
			</ul>
		</div>
		<div class="container_top">
			<div class="fl box"></div>
			<div class="fl">
				<i class="ifont zoom">&#xe62e;</i>
				<div class="customer_service fl">
					<i class="ifont">&#xe68f;</i>
					<span>在线客服</span>
				</div>
				<div class="tel fl">
					<i class="ifont">&#xe611;</i>
					<span>客服热线：</span>
					<em>400-852-8008</em>
				</div>
			</div>
			<div class="fr">
				<a href="javascript: void(0);">登录</a>
				<a href="javascript: void(0);">注册</a>
			</div>
		</div>
		<div class="container_bottom">
			<p>
				<span>国际原油</span>
				<span class="green">3360.81 -2.82 -0.08%</span>
				<i class="ifont green">&#xe76a;</i>
			</p>
			<p>
				<span>美黄金</span>
				<span class="red">3360.81 -2.82 -0.08%</span>
				<i class="ifont red">&#xe76a;</i>
			</p>
			<p>
				<span>恒指期货</span>
				<span class="green">3360.81 -2.82 -0.08%</span>
				<i class="ifont green">&#xe76a;</i>
			</p>
		</div>
		<div class="container">
			<router-view></router-view>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'app',
		methods : {
			toRegister : function(){
				console.log('111')
			},
			toLogin : function(){
				console.log('2222')  
			}
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "./assets/css/common.scss";
	/*容器框架*/
	#app {
	    width: 100%;
	    min-width: 1280px;
	}
	.icon{
		width: 20px;
		height: 20px;
		margin: 10px 0 4px 0;
	}
	/*左部导航*/
	.container_left {
		position: fixed;
		top: 0;
		left: 0;
		z-index: 2;
	    width: 60px;
	    height: 100%;
	    background: $blue;
	    ul{
	        li{
	        	width: 60px;
	            height: 60px;
	            overflow: hidden;
	            text-align: center;
	            cursor: pointer;
	            &:hover, &.current{
	            	background: $deepblue;
	            	.icon_quote{
	            		background-image: url(assets/images/icon_quote1.png);
	            	}
	            	.icon_trade{
	            		background-image: url(assets/images/icon_trade1.png);
	            	}
	            	.icon_open{
	            		background-image: url(assets/images/icon_open1.png);
	            	}
	            	.icon_live{
	            		background-image: url(assets/images/icon_live1.png);
	            	}
	            	.icon_calendar{
	            		background-image: url(assets/images/icon_calendar1.png);
	            	}
	            	.icon_download{
	            		background-image: url(assets/images/icon_download1.png);
	            	}
	            	.icon_account{
	            		background-image: url(assets/images/icon_account1.png);
	            	}
	            	p{
	            		color: $yellow;
	            	}
	            }
	            .icon_quote{
	            	background: url(assets/images/icon_quote.png) no-repeat center center;
	            }
	            .icon_trade{
	            	background: url(assets/images/icon_trade.png) no-repeat center center;
	            }
	            .icon_open{
	            	background: url(assets/images/icon_open.png) no-repeat center center;
	            }
	            .icon_live{
	            	background: url(assets/images/icon_live.png) no-repeat center center;
	            }
	            .icon_calendar{
	            	background: url(assets/images/icon_calendar.png) no-repeat center center;
	            }
	            .icon_download{
	            	background: url(assets/images/icon_download.png) no-repeat center center;
	            }
	            .icon_account{
	            	background: url(assets/images/icon_account.png) no-repeat center center;
	            }
	            p{
	            	font-size: $fs12;
	            	color: $lightblue;
	            }
	        }
	    }
	}
	/*顶部栏*/
	.container_top {
		position: fixed;
		top: 0;
		left: 0;
		z-index: 1;
	    width: 100%;
	    height: 40px;
	    line-height: 40px;
	    background: $blue;
	    margin: 5px 0;
	    font-size: $fs12;
	    .box{
	    	width: 70px;
	    	height: 40px;
	    	overflow: hidden;
	    	background: $black;
	    }
		.ifont{
			float: left;
			color: $lightblue;
			&.zoom{
				font-size: $fs20;
				margin: 0 40px 0 20px;
				cursor: pointer;
			}
		}
		span, em{
			float: left;
		}
		.customer_service, .tel{
			margin-right: 48px;
			.ifont{
				font-size: $fs16;
				margin-right: 10px;
			}
		}
		.customer_service{
			cursor: pointer;
			&:hover{
				color: $yellow;
				.ifont{
					color: $yellow;
				}
			}
		}
		em{
			color: $white;
		}
		a{
			float: left;
			display: inline-block;
			height: 30px;
			line-height: 30px;
			padding: 0 20px;
			margin: 5px 0;
			font-size: $fs12;
			color: $lightblue;
			&:last-child{
				border: 1px solid $lightblue;
				border-radius: 15px;
				margin-right: 10px;
			}
			&:hover{
				color: $yellow;
				border-color: $yellow;
			}
		}
	}
	/*底部栏*/
	.container_bottom{
	    position: fixed;
	    bottom: 0;
	    left: 0;
	    z-index: 3;
	    width: 100%;
	    height: 30px;
	    line-height: 30px;
	    background: $bottom_color;
	    p{
	    	float: left;
	    	font-size: $fs12;
	    	margin-left: 20px;
	    	.ifont{
	    		font-size: 10px;
	    	}
	    	.green{
	    		color: $green;
	    	}
	    	.red{
	    		color: $red;
	    	}
	    }
	}
	/*容器*/
	.container{
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		min-width: 1210px;
		overflow: hidden;
		overflow-x: auto;
		padding: 50px 0 0 70px;
	}
</style>