<template>
	<div id="download">
		<div class="bg"></div>
		<div class="download">
			<div class="page1" v-show="show_page1">
				<div class="page1_left">
					<div class="page1_list">
						<i></i>
						<h2>实时行情</h2>
						<p>全球期货行情永久免费下载</p>
						<ul>
							<li><i></i></li>
							<li><i></i><span>IOS下载 </span></li>
							<li><i></i><span>Android下载</span></li>
						</ul>
						<b>扫描下载APP</b>
					</div>
				</div>
				<div class="page1_right">
					<div class="page1_container">
						<div class="page1_right_ring"></div>
						<div class="page1_right_app"></div>
						<div class="page1_shadow"></div>
						<div class="page1_shadow1"></div>
						<div class="page1_right_app1"></div>
					</div>
				</div>
				<!--<div class="triangle"></div>-->
			</div>
			<div class="page2"  v-show="show_page2">
				<div class="page2_left"> 
					<div class="page2_container">
						<div class="page2_left_ring"></div>
						<div class="page2_left_app"></div>
						<div class="page2_shadow"></div>
						<div class="page2_shadow1"></div>
						<div class="page2_left_app1"></div>
					</div>
				</div>
				<div class="page2_right">
					<div class="page2_list">
						<i></i>
						<h2>模拟交易</h2>
						<p>盈利之前做好充分准备</p>
						<ul>
							<li><i></i></li>
							<li><i></i><span>IOS下载 </span></li>
							<li><i></i><span>Android下载</span></li>
						</ul>
						<b>扫描下载APP</b>
					</div>
				</div>
			</div>
			<div class="page3" v-show="show_page3">
				<div class="page3_left">
					<div class="page3_list">
						<i></i>
						<h2>自在下单</h2>
						<p>限价市价委托样样拿手</p>
						<ul>
							<li><i></i></li>
							<li><i></i><span>IOS下载 </span></li>
							<li><i></i><span>Android下载</span></li>
						</ul>
						<b>扫描下载APP</b>
					</div>
				</div>
				<div class="page3_right">
					<div class="page3_container">
						<div class="page3_right_ring"></div>
						<div class="page3_right_app"></div>
						<div class="page3_shadow"></div>
						<div class="page3_shadow1"></div>
						<div class="page3_right_app1"></div>
						</div>
				</div>
			</div>
			<div class="page4" v-show="show_page4">
				<div class="page4_left">
					<div class="page4_container">
						<div class="page4_left_ring"></div>
						<div class="page4_left_app"></div>
						<div class="page4_shadow"></div>
						<div class="page4_shadow1"></div>
						<div class="page4_left_app1"></div>
					</div>
				</div>
				<div class="page4_right">
					<div class="page4_list">
						<i></i>
						<h2>财经日历</h2>
						<p>热点数据事件面面俱到</p>
						<ul>
							<li><i></i></li>
							<li><i></i><span>IOS下载 </span></li>
							<li><i></i><span>Android下载</span></li>
						</ul>
						<b>扫描下载APP</b>
					</div>
				</div>
			</div>
			<div class="page5" v-show="show_page5">
				<div class="page5_left">
					<div class="page5_list">
						<i></i>
						<h2>资讯直播</h2>
						<p>7X24覆盖全球市场异动</p>
						<ul>
							<li><i></i></li>
							<li><i></i><span>IOS下载 </span></li>
							<li><i></i><span>Android下载</span></li>
						</ul>
						<b>扫描下载APP</b>
					</div>
				</div>
				<div class="page5_right">
					<div class="page5_container">
						<div class="page5_right_ring"></div>
						<div class="page5_right_app"></div>
						<div class="page5_shadow"></div>
						<div class="page5_shadow1"></div>
						<div class="page5_right_app1"></div>
					</div>
				</div>
			</div>
			<div class="nextPage" v-on:click="nextPage"></div>
			<div class="progress">
				<span v-for="(k,index) in 5" :class="{current:current == index}"></span>
			</div>
			<div class="sanjiaox" v-show="show_sanjiao"></div>
		</div>
	</div>
</template>

<script>
	export default{
		name:'download',
		data(){
			return{
				current:0,
				show_page1:true,
				show_page2:false,
				show_page4:false,
				show_page3:false,
				show_page5:false,
				show_sanjiao:true
			}
		},
		methods:{
			nextPage:function(){
				if(this.current <4){
					this.current++;
					this.show_sanjiao =false
				}else{
					this.current =0;
					this.show_sanjiao = true
				}
				var a = parseInt(this.current%5);
				if(a == 0){
					this.show_page1 = true;
					this.show_page2 = false;
					this.show_page3 = false;
					this.show_page4 = false;
					this.show_page5 = false;
				}else if(a == 1){
					this.show_page1 = false;
					this.show_page2 = true;
					this.show_page3 = false;
					this.show_page4 = false;
					this.show_page5 = false;
				}else if(a == 2){
					this.show_page1 = false;
					this.show_page2 = false;
					this.show_page3 = true;
					this.show_page4 = false;
					this.show_page5 = false;
				}else if(a == 3){
					this.show_page1 = false;
					this.show_page2 = false;
					this.show_page3 = false;
					this.show_page4 = true;
					this.show_page5 = false;
				}else if(a == 4){
					this.show_page1 = false;
					this.show_page2 = false;
					this.show_page3 = false;
					this.show_page4 = false;
					this.show_page5 = true;
				}
			}
		},
		mounted:function(){
			var screenWidth = window.screen.width;
			if(screenWidth == 1366){
				$("#download").css("height","768px");
				$(".page1_list").css("top","10%");
				$(".page2_list").css("top","10%");
				$(".page3_list").css("top","10%");
				$(".page4_list").css("top","10%");
				$(".page5_list").css("top","10%");
				$(".page1_container").css("top","2%");
				$(".page2_container").css("top","2%").css("left","15%");
				$(".page3_container").css("top","2%")
				$(".page4_container").css("top","2%").css("left","15%");
				$(".page5_container").css("top","2%")
			}else if(screenWidth == 1920){
				$("#download").css("height","920px");
			}
		}
	}
</script>

<style scoped lang="scss">
	@import "../assets/css/common.scss";
	#download{
		width: 100%;
		/*height: 920px;*/
	}
	.bg{
		background-color: $white;
		opacity:1;
		vertical-align:middle;
	}
	.download{
		width: 100%;
		height: 100%;
		z-index: 120;
		position: absolute;
		top: 0;
		left: 0;
	}
	.page1{
		width:100%;
		height: 100%;
		.page1_left{
			position: relative;
			float: left;
			width: 45%;
			/*background-image: url(../assets/images/dowload_left_1.png);*/
			height: 100%;
			background-size: 100% 100%;
			background-color: #242633;
			.page1_list{
				position: absolute;
				left: 18.75%;
				top: 21.74%;
				i{
					width: 40px;
					height: 40px;
					display: inline-block;
					background-image: url(../assets/images/dowload_logo.png);
					background-color: #242633;
				}
				h2{
					font-size: $fs56;
					color: $white;
					margin-top: 50px;
				}
				p{
					color: $yellow;
					font-size: $fs28;
					margin-top: 50px;
					letter-spacing:20px;
				}
				ul{
					margin-top: 50px;
					width: 360px;
					height: 140px;
					li{
						&:nth-child(1){
							float: left;
							width: 140px;
							height: 140px;
							background-color: $white;
							i{
								display: block;
								width: 130px;
								height: 130px;
								background-image: url(../assets/images/download_erweima.png);
								outline: none;
								margin-left: 5px;
								margin-top: 5px;
							}
						}
						&:nth-child(2){
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_apple.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
						&:nth-child(3){
							margin-top: 20px;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_android.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
					}
				}
				b{
					color: $white;
					font-size: $fs16;
					position: relative;
					top: 20px;
					left: 20px;
				}
			}
		}
		.page1_right{
			float: left;
			width: 55%;
			height: 100%;
			.page1_container{
				width: 100%;
				height: 100%;
				position: relative;
				top: 18%;
				left: 35%;
				.page1_right_ring{
					position: absolute;
					width: 300px;
					height: 600px;
					border: 1px solid $yellow;
					z-index: 130;
				}
				.page1_right_app{
					position: absolute;
					top: 1%;
					left: -6%;
					z-index: 133;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_app1.png);
				}
				.page1_shadow{
					position: absolute;
					top: 1.75%;
					left: -5%;
					z-index: 131;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page1_shadow1{
					position: absolute;
					top: 2.5%;
					left: -4%;
					z-index: 132;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page1_right_app1{
					position: absolute;
					left: -20%;
					top: 25%;
					z-index: 134;
					width: 400px;
					height: 200px;
					background-image: url(../assets/images/page1_app2.png);
				}
			}
			
		}
	}
	.page2{
		width: 100%;
		height: 100%;
		background-size: 100% 100%;
		background-image: url(../assets/images/page2_background.png);
		.page2_left{
			width: 50%;
			height: 100%;
			float: left;
			.page2_container{
				width:100%;
				height: 100%;
				position: relative;
				top: 18%;
				left: 35%;
				.page2_left_ring{
					width: 300px;
					height: 600px;
					border: 1px solid $yellow;
					z-index: 130;
				}
				.page2_left_app{
					position: absolute;
					top: 1%;
					left: 5%;
					z-index: 133;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page2_app1.png);
				}
				.page2_shadow{
					position: absolute;
					top: 1.75%;
					left:4%;
					z-index: 131;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page2_shadow1{
					position: absolute;
					top: 2.5%;
					left: 3%;
					z-index: 132;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page2_left_app1{
					position: absolute;
					left: 11%;
					top: 25%;
					z-index: 134;
					width: 400px;
					height: 200px;
					background-image: url(../assets/images/page2_app2.png);
				}
			}
		}
		.page2_right{
			width: 50%;
			height: 100%;
			float: left;
			position: relative;
			.page2_list{
				position: absolute;
				left: 18.75%;
				top: 21.74%;
				i{
					width: 40px;
					height: 40px;
					display: inline-block;
					background-image: url(../assets/images/dowload_logo.png);
				}
				h2{
					font-size: $fs56;
					color: #242633;
					margin-top: 50px;
				}
				p{
					color: #3e4049;
					font-size: $fs28;
					margin-top: 50px;
					letter-spacing:12px;
				}
				ul{
					margin-top: 50px;
					width: 360px;
					height: 140px;
					li{
						&:nth-child(1){
							box-shadow:0px 0px 5px 5px #dfe0e1;
							float: left;
							width: 140px;
							height: 140px;
							background-color: $white;
							i{
								display: block;
								width: 130px;
								height: 130px;
								background-image: url(../assets/images/download_erweima.png);
								outline: none;
								margin-left: 5px;
								margin-top: 5px;
							}
						}
						&:nth-child(2){
							box-shadow: 0 0 5px 5px #fff4bc;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_apple.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 40px;
								letter-spacing:2px;
							}
						}
						&:nth-child(3){
							box-shadow: 0 0 5px 5px #fff4bc;
							margin-top: 20px;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_android.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 40px;
								letter-spacing:2px;
							}
						}
					}
				}
				b{
					color: #3e4049;
					font-size: $fs16;
					position: relative;
					top: 20px;
					left: 20px;
					font-weight: 500;
				}
			}
		}
	}
	.page3{
		width:100%;
		height: 100%;
		background-image: url(../assets/images/page2_background.png);
		background-size: 100% 100%;
		.page3_left{
			position: relative;
			float: left;
			width: 50%;
			height: 100%;
			.page3_list{
				position: absolute;
				left: 40%;
				top: 21.74%;
				i{
					width: 40px;
					height: 40px;
					display: inline-block;
					background-image: url(../assets/images/dowload_logo.png);
				}
				h2{
					font-size: $fs56;
					color: black;
					margin-top: 50px;
				}
				p{
					color: #242633;
					font-size: $fs28;
					margin-top: 50px;
					letter-spacing:20px;
				}
				ul{
					margin-top: 50px;
					width: 360px;
					height: 140px;
					li{
						&:nth-child(1){
							box-shadow:0px 0px 5px 5px #dfe0e1;
							float: left;
							width: 140px;
							height: 140px;
							background-color: $white;
							i{
								display: block;
								width: 130px;
								height: 130px;
								background-image: url(../assets/images/download_erweima.png);
								outline: none;
								margin-left: 5px;
								margin-top: 5px;
							}
						}
						&:nth-child(2){
							box-shadow: 0 0 5px 5px #fff4bc;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_apple.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
						&:nth-child(3){
							box-shadow: 0 0 5px 5px #fff4bc;
							margin-top: 20px;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_android.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
					}
				}
				b{
					color: #242633;
					font-size: $fs16;
					position: relative;
					top: 20px;
					left: 20px;
					font-weight: 500;
				}
			}
		}
		.page3_right{
			float: left;
			width: 50%;
			height: 100%;
			.page3_container{
				width:100%;
				height: 100%;
				position: relative;
				top: 18%;
				left: 35%;
				.page3_right_ring{
					width: 300px;
					height: 600px;
					border: 1px solid $yellow;
					z-index: 130;
				}
				.page3_right_app{
					position: absolute;
					top: 1%;
					left: -6%;
					z-index: 133;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page3_app1.png);
				}
				.page3_shadow{
					position: absolute;
					top: 1.75%;
					left: -5%;
					z-index: 131;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page3_shadow1{
					position: absolute;
					top: 2.5%;
					left: -4%;
					z-index: 132;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page3_right_app1{
					position: absolute;
					left: -20%;
					top: 25%;
					z-index: 134;
					width: 400px;
					height: 200px;
					background-image: url(../assets/images/page3_app2.png);
				}
			}
		}
	}
	.page4{
		width: 100%;
		height: 100%;
		background-size: 100% 100%;
		background-image: url(../assets/images/page2_background.png);
		.page4_left{
			width: 50%;
			height: 100%;
			float: left;
			.page4_container{
				width:100%;
				height: 100%;
				position: relative;
				top: 18%;
				left: 35%;
				.page4_left_ring{
					width: 300px;
					height: 600px;
					border: 1px solid $yellow;
					z-index: 130;
				}
				.page4_left_app{
					position: absolute;
					top: 1%;
					left: 5%;
					z-index: 133;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page4_app1.png);
				}
				.page4_shadow{
					position: absolute;
					top: 1.75%;
					left:4%;
					z-index: 131;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page4_shadow1{
					position: absolute;
					top: 2.5%;
					left: 3%;
					z-index: 132;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page4_left_app1{
					position: absolute;
					left: 11%;
					top: 25%;
					z-index: 134;
					width: 400px;
					height: 200px;
					background-image: url(../assets/images/page4_app2.png);
				}
			}
		}
		.page4_right{
			width: 50%;
			height: 100%;
			float: left;
			position: relative;
			.page4_list{
				position: absolute;
				left: 18.75%;
				top: 21.74%;
				i{
					width: 40px;
					height: 40px;
					display: inline-block;
					background-image: url(../assets/images/dowload_logo.png);
				}
				h2{
					font-size: $fs56;
					color: #242633;
					margin-top: 50px;
				}
				p{
					color: #3e4049;
					font-size: $fs28;
					margin-top: 50px;
					letter-spacing:12px;
				}
				ul{
					margin-top: 50px;
					width: 360px;
					height: 140px;
					li{
						&:nth-child(1){
							box-shadow:0px 0px 5px 5px #dfe0e1;
							float: left;
							width: 140px;
							height: 140px;
							background-color: $white;
							i{
								display: block;
								width: 130px;
								height: 130px;
								background-image: url(../assets/images/download_erweima.png);
								outline: none;
								margin-left: 5px;
								margin-top: 5px;
							}
						}
						&:nth-child(2){
							box-shadow: 0 0 5px 5px #fff4bc;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_apple.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 40px;
								letter-spacing:2px;
							}
						}
						&:nth-child(3){
							box-shadow: 0 0 5px 5px #fff4bc;
							margin-top: 20px;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_android.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 40px;
								letter-spacing:2px;
							}
						}
					}
				}
				b{
					color: #3e4049;
					font-size: $fs16;
					position: relative;
					top: 20px;
					left: 20px;
					font-weight: 500;
				}
			}
		}
	}
	.page5{
		width:100%;
		height: 100%;
		background-image: url(../assets/images/page2_background.png);
		background-size: 100% 100%;
		.page5_left{
			position: relative;
			float: left;
			width: 50%;
			height: 100%;
			.page5_list{
				position: absolute;
				left: 40%;
				top: 21.74%;
				i{
					width: 40px;
					height: 40px;
					display: inline-block;
					background-image: url(../assets/images/dowload_logo.png);
				}
				h2{
					font-size: $fs56;
					color: black;
					margin-top: 50px;
				}
				p{
					color: #242633;
					font-size: $fs28;
					margin-top: 50px;
					letter-spacing:20px;
				}
				ul{
					margin-top: 50px;
					width: 360px;
					height: 140px;
					li{
						&:nth-child(1){
							box-shadow:0px 0px 5px 5px #dfe0e1;
							float: left;
							width: 140px;
							height: 140px;
							background-color: $white;
							i{
								display: block;
								width: 130px;
								height: 130px;
								background-image: url(../assets/images/download_erweima.png);
								outline: none;
								margin-left: 5px;
								margin-top: 5px;
							}
						}
						&:nth-child(2){
							box-shadow: 0 0 5px 5px #fff4bc;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_apple.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
						&:nth-child(3){
							box-shadow: 0 0 5px 5px #fff4bc;
							margin-top: 20px;
							float: right;
							width: 180px;
							height: 60px;
							background-color: $yellow;
							border-radius: 5px;
							line-height: 60px;
							i{
								position: relative;
								top:5px;
								left: 20px;
								display: inline-block;
								height: 25px;
								width: 20px;
								background-image: url(../assets/images/download_android.png);
								background-color: $yellow;
							}
							span{
								color: #242633;
								margin-left: 50px;
								letter-spacing:2px;
							}
						}
					}
				}
				b{
					color: #242633;
					font-size: $fs16;
					position: relative;
					top: 20px;
					left: 20px;
					font-weight: 500;
				}
			}
		}
		.page5_right{
			float: left;
			width: 50%;
			height: 100%;
			.page5_container{
				width:100%;
				height: 100%;
				position: relative;
				top: 18%;
				left: 35%;
				.page5_right_ring{
					width: 300px;
					height: 600px;
					border: 1px solid $yellow;
					z-index: 130;
				}
				.page5_right_app{
					position: absolute;
					top: 1%;
					left: -6%;
					z-index: 133;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page5_app1.png);
				}
				.page5_shadow{
					position: absolute;
					top: 1.75%;
					left: -5%;
					z-index: 131;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page5_shadow1{
					position: absolute;
					top: 2.5%;
					left: -4%;
					z-index: 132;
					width: 320px;
					height: 570px;
					background-image: url(../assets/images/page1_shadow.png);
				}
				.page5_right_app1{
					position: absolute;
					left: -20%;
					top: 25%;
					z-index: 134;
					width: 400px;
					height: 200px;
					background-image: url(../assets/images/page5_app2.png);
				}
			}
		}
	}
	.nextPage{
		width: 42px;
		height: 40px;
		position: fixed;
		z-index: 199;
		top: 92.5%;
		left: 49%;
		background-image: url(../assets/images/download_next.png);
		cursor: pointer;
	}
	.progress{
		width: 15px;
		height: 100;
		position: fixed;
		top: 47.67%;
		right: 6.25%;
		span{
			display: inline-block;
			width: 12px;
			height: 12px;
			border-radius: 50%;
			border: 2px solid black;
			margin-top: 10px;
		}
		.current{
			background-color: black;
		}
	}
	.sanjiaox{
		position: absolute;
		top: 50%;
		left: 44.5%;
		width: 0;
	    height: 0;
	    border-top: 10px solid transparent;
	    border-right: 15px solid $white;
	    border-bottom: 10px solid transparent;
		z-index: 200;
	}
</style>