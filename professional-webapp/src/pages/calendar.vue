<template>
	<div id="calendar">
		<div class="calendar_container">
			<div class="calendar">
				<div class="calendar_left">
					<span><</span>
					<span>上周</span>
				</div>
				<div class="calendar_center">
					<div class="calendar_center1">
						<ul>
							<li><span>24</span></li>
							<li><span>25</span></li>
							<li><span>26</span></li>
							<li><span>27</span></li>
							<li><span>28</span></li>
							<li><span>29</span></li>
							<li><span>30</span></li>
						</ul>
					</div>
					<div class="calendar_center2">
						<ul>
							<li><span>周一</span></li>
							<li><span>周二</span></li>
							<li><span>周三</span></li>
							<li><span>周四</span></li>
							<li><span>周五</span></li>
							<li><span>周六</span></li>
							<li><span>周日</span></li>
						</ul>
					</div>
				</div>
				<div class="calendar_right">
					<span>></span>
					<span>下周</span>
				</div>
			</div>
		</div>
		<div class="country">
			<p class="p_center">按国家分类</p>
			<div class="country_top">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>全部</span>
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>中国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>美国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>俄罗斯</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>英国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
				</ul>
			</div>
			<div class="country_btm">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>法国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>德国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>加拿大</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>意大利</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
				</ul>
			</div>
		</div>
		<div class="important">
			<p class="p_center">按重要性分类</p>
			<div class="important_center">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>全部</span>
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>重要</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="infoList">
			<div class="list_title">
				<ul>
					<li>日历图标</li>
					<li>周五</li>
					<li>2017年07月24日</li>
				</ul>
			</div>
			<div class="list">
				<ul class="list_container">
					<li>时间</li>
					<li>地区</li>
					<li>事件</li>
					<li>重要性</li>
					<li>金值</li>
					<li>预期</li>
					<li>前值</li>
				</ul>
				<ul class="list_details" v-for="k in list">
					<li>17:22</li>
					<li><img src="../assets/images/china.png" alt="" /></li>
					<li>中国6月70成房价的变动</li>
					<li>星星</li>
					<li>3.3%</li>
					<li>3.4%</li>
					<li>3.6%</li>
				</ul>
			</div>
		</div>
		<div class="btm">
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	export default{
		name:'calendar',
		data(){
			return{
				list:5
			}
		}
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#calendar{
		width: 1000px;
		margin: auto;
		/*overflow-y: auto;*/
	}
	.calendar_container{
		background-color: $blue;
		height: 100px;
		width: 100%;
		.calendar{
			height: 100px;
			width: 680px;
			margin: auto;
			.calendar_left{
				line-height: 100px;
				width: 90px;
				height: 100px;
				float: left;
				cursor: pointer;
			}
			.calendar_center{
				width: 500px;
				height: 100px;
				float: left;
				.calendar_center1{
					width: 100%;
					height: 50px;
					li{
						line-height: 50px;
						text-align: center;
						width: 14.2%;
						float: left;
						span{
							cursor: pointer;
							font-weight: 600;
							font-size: $fs24;
						}
					}
				}
				.calendar_center2{
					width: 100%;
					height: 50px;
					li{
						text-align: center;
						width: 14.2%;
						float: left;
						span{
							cursor: pointer;
						}
					}
				}
			}
			.calendar_right{
				cursor: pointer;
				line-height: 100px;
				text-align: right;
				width: 90px;
				height: 100px;
				float: right;
			}
			
		}
	}
	.country{
		width: 100%;
		height: 160px;
		background-color: $blue;
		margin-top: 5px;
		.ifont{
			color: $lightblue;
			padding-right: 10px;
		}
		.country_top{
			padding-left: 10px;
			height: 60px;
			width: 100%;
			line-height: 60px;
			li{
				width: 160px;
				float: left;
				img{
					position: relative;
					top: 5px;
				}
			}
		}
		.country_btm{
			padding-left: 10px;
			width: 100%;
			height: 60px;
			line-height: 50px;
			li{
				width: 160px;
				float: left;
				&:nth-child(1){
					margin-left: 160px;
				}
				img{
					position: relative;
					top: 5px;
				}
			}
		}
	}
	.important{
		border-bottom: 1px solid $bottom_color;
		width: 100%;
		height: 100px;
		background-color: $blue;
		.ifont{
			color: $lightblue;
			padding-right: 10px;
		}
		ul{
			width: 100%;
			height: 60px;
			line-height: 55px;
			li{
				&:nth-child(1){
					margin-left: 170px;
				}
				width: 160px;
				float: left;
			}
		}
		
	}
	.infoList{
		width: 100%;
		background-color:$blue;
		.list_title{
			padding-left: 10px;
			height: 40px;
			line-height: 40px;
			li{
				float: left;
				font-size: $fs12;
				&:nth-child(2){
					color: $white;
					padding: 0 10px;
				}
			}
		}
		.list_container{
			font-size: $fs12;
			text-indent: 10px;
			height: 30px;
			line-height: 30px;
			background-color:$bottom_color ;
			li{
				float: left;
				&:nth-child(1){
					width: 10%;
				}
				&:nth-child(2){
					width: 10%;
				}
				&:nth-child(3){
					width: 45%;
				}
				&:nth-child(4){
					width: 10%;
				}
				&:nth-child(5){
					width: 10%;
				}
				&:nth-child(6){
					width: 10%;
				}
				&:nth-child(7){
					width: 5%;
				}
			}
		}
		.list_details{
			font-size: $fs14;
			line-height: 40px;
			text-indent: 10px;
			height: 40px;
			border-bottom: 1px solid $bottom_color;
			li{
				float: left;
				&:nth-child(1){
					width: 10%;
				}
				&:nth-child(2){
					width: 10%;
				}
				&:nth-child(3){
					width: 45%;
				}
				&:nth-child(4){
					width: 10%;
				}
				&:nth-child(5){
					width: 10%;
				}
				&:nth-child(6){
					width: 10%;
				}
				&:nth-child(7){
					width: 5%;
				}
				img{
					position: relative;
					top: 5px;
				}
			}
		}
	}
	.btm{
		margin-top: 5px;
		height: 40px;
		line-height: 40px;
		text-align: center;
		background-color: $blue;
		p{
			font-size: $fs12;
		}
	}
	.p_center{
		height: 40px;
		font-size: $fs16;
		line-height: 40px;
		background-color: $bottom_color;
		padding-left: 10px;
		color: $white;
	}
</style>