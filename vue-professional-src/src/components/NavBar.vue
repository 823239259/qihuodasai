<template>
	<div id="navbar">
		<nav>
			<ul>
				<li v-for="(key,index) in navList" class="fl" @tap="handleClick(key,index)" :key="index">
					<img :src="currentIndex===index?key.currentSrc:key.src"  />
					<p :class="{'current':currentIndex === index}">{{key.name}}</p>
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
				currentIndex: 0,
				navList: [{
						name: '行情',
						src: require('../assets/img/01.png'),
						currentSrc: require('../assets/img/01y.png'),
						path: '/home'
					},
					{
						name: '开户',
						src: require('../assets/img/02.png'),
						currentSrc: require('../assets/img/02y.png'),
						path: '/tradeapply'
					},
					{
						name: '直播',
						src: require('../assets/img/03.png'),
						currentSrc: require('../assets/img/03y.png'),
						path: '/information'
					},
					{
						name: '我的',
						src: require('../assets/img/04.png'),
						currentSrc: require('../assets/img/04y.png'),
						path: '/account'
					}
				]
			}
		},
		methods: {
			goto (path) {
				if (path === '/account'&&!localStorage.user) {
					this.$router.push({
						path: '/login'
					});
					return;
				}
				this.$router.push({
					path
				});
			},
			changeCurrentIndex (index) {
				if (this.currentIndex === index) return;
				this.currentIndex = index
			},
			handleClick (item, index) {
				this.goto(item.path)
				this.changeCurrentIndex(index)
			}
		},
		
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