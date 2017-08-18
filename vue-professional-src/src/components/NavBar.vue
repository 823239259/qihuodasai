<template>
	<div id="navbar">
		<nav>
			<ul>
				<li v-for="key in navList" class="fl" @tap="toPages">
					<img :src="key.src" />
					<p>{{key.name}}</p>
				</li>
			</ul>
		</nav>
	</div>
</template>

<script>
	export default {
		name: 'navbar',
		data() {
			return {
				navList: [{
						name: '行情',
						src: require('../assets/img/01.png')
					},
					{
						name: '开户',
						src: require('../assets/img/02.png')
					},
					{
						name: '直播',
						src: require('../assets/img/03.png')
					},
					{
						name: '我的',
						src: require('../assets/img/04.png')
					}
				]
			}
		},
		computed:{
			routepath(){
				return this.$route.path;
			}
		},
		watch:{
			routepath:function(n,o){
				switch(n){
					case '/home':
						$('nav li>p').removeClass('current');
						$('nav li:first-child>p').addClass('current');
						this.navList[0].src = require('../assets/img/01y.png');
						this.navList[1].src = require('../assets/img/02.png');
						this.navList[2].src = require('../assets/img/03.png');
						this.navList[3].src = require('../assets/img/04.png');
						break;
					case '/tradeapply':
						$('nav li>p').removeClass('current');
						$('nav li:nth-child(2)>p').addClass('current');
						this.navList[1].src = require('../assets/img/02y.png');
						this.navList[0].src = require('../assets/img/01.png');
						this.navList[2].src = require('../assets/img/03.png');
						this.navList[3].src = require('../assets/img/04.png');
						break;
					case '/information':
						$('nav li>p').removeClass('current');
						$('nav li:nth-child(3)>p').addClass('current');
						this.navList[2].src = require('../assets/img/03y.png');
						this.navList[1].src = require('../assets/img/02.png');
						this.navList[0].src = require('../assets/img/01.png');
						this.navList[3].src = require('../assets/img/04.png');
						break;
					case '/account':
						$('nav li>p').removeClass('current');
						$('nav li:nth-child(4)>p').addClass('current');
						this.navList[3].src = require('../assets/img/04y.png');
						this.navList[2].src = require('../assets/img/03.png');
						this.navList[1].src = require('../assets/img/02.png');
						this.navList[0].src = require('../assets/img/01.png');
						break;
				}
			}
		},
		methods: {
			toPages: function(e) {
				// 此方法用于跳转页面，并更改高亮图标
				var tar = e.currentTarget.children[1].innerHTML;
				switch(tar) {
					case '行情':
						this.$router.push({
							path: '/home'
						});
						break;
					case '开户':
						this.$router.push({
							path: '/tradeapply'
						});
						break;
					case '直播':
						this.$router.push({
							path: '/information'
						});
						break;
					case '我的':
						if(!this.$store.state.account.islogin) {
							this.$router.push({
								path: '/login'
							});
							return;
						}
						this.$router.push({
							path: '/account'
						});
						break;
				}
			}
		},
		mounted: function() {
			//			var width=parseInt($('#navbar').css('width'));
			//			var height=width*0.128;
			//			$('#navbar').css({'height':height+'px'});
			//			导航被挂载的时候,判定哪个按钮进行高亮
			var path = this.$route.path;
			switch(path) {
				case '/home':
					$('nav li>p').removeClass('current');
					$('nav li:first-child>p').addClass('current');
					this.navList[0].src = require('../assets/img/01y.png');
					break;
				case '/tradeapply':
					$('nav li>p').removeClass('current');
					$('nav li:nth-child(2)>p').addClass('current');
					this.navList[1].src = require('../assets/img/02y.png');
					break;
				case '/information':
					$('nav li>p').removeClass('current');
					$('nav li:nth-child(3)>p').addClass('current');
					this.navList[2].src = require('../assets/img/03y.png');
					break;
				case '/account':
					$('nav li>p').removeClass('current');
					$('nav li:nth-child(4)>p').addClass('current');
					this.navList[3].src = require('../assets/img/04y.png');
					break;
				default:
					$('nav li>p').removeClass('current');
					$('nav li:first-child>p').addClass('current');
					this.navList[0].src = require('../assets/img/01y.png');
					break;
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*使用该组件的时候，请在挂载的页面ID样式加上padding-bottom:50px*/
	
	#navbar {
		width: 100%;
		position: fixed;
		padding-top: 1%;
		box-shadow: 1px 1px 1px #000;
		bottom: 0;
		background: #242633;
		z-index: 999;
	}
	
	ul {
		width: 100%;
		height: 100%;
	}
	
	li {
		width: 25%;
		height: 100%;
		text-align: center;
		padding-top: 1%;
	}
	
	img {
		display: block;
		width: 20px;
		height: 20px;
		margin: 1% auto;
	}
	
	p {
		font-size: 12px;
		color: #a3aacc;
	}
	
	p.current {
		color: #ffd400;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#navbar {
			height: 50px*@ip6;
		}
		img {
			width: 20px*@ip5;
			height: 20px*@ip5;
		}
		p {
			font-size: 12px;
			color: #a3aacc;
			transform: scale(0.8);
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#navbar {
			height: 50px;
		}
		img {
			width: 20px*@ip6;
			height: 20px*@ip6;
		}
		p {
			font-size: 12px;
			color: #a3aacc;
			transform: scale(0.9);
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#navbar {
			height: 47px;
			padding-bottom: 3px;
			box-sizing: content-box;
		}
	}
</style>