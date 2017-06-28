<template>
	<div id="information">
		<topbar title="直播"></topbar>
		<!--选择条-->
		<div class="selectbar">
			<ul>
				<li class="fl" @tap="showdate">
					<span class="currentsel">日历</span>
				</li>
				<li class="fr" @tap="showseven">
					<span>7×24</span>
				</li>
			</ul>
		</div>
		<!--内容-->
		<!--日历-->
		<div id="calendar" v-if="isshow">
			<header class="fontwhite">2017年07月</header>
			<ul>
				<li class="fl currentday">
					<p>一</p>
					<h4>16</h4>
				</li>
				<li class="fl">
					<p>二</p>
					<h4>17</h4>
				</li>
				<li class="fl">
					<p>三</p>
					<h4>18</h4>
				</li>
				<li class="fl">
					<p>四</p>
					<h4>19</h4>
				</li>
				<li class="fl">
					<p>五</p>
					<h4>20</h4>
				</li>
				<li class="fl">
					<p>六</p>
					<h4>21</h4>
				</li>
				<li class="fl">
					<p>日</p>
					<h4>22</h4>
				</li>
			</ul>
			<ol>
				<template v-for="key in datalist">
					<li class="lists">
						<div>
							<span id="time" class="fontgray">
								{{key.msgdate}}
							</span>
							<img :src="key.stateurl" />
							<span class="state fontgray">
								{{key.state}}
							</span>
							<img :src='key.starurl' />
						</div>
						<p class="content fontwhite fontmd">
							{{key.content}}
						</p>
						<div>
							<span id="today" class="fontyellow">今值&nbsp; {{key.today}}</span>
							<span id="expect" class="fontgray">预期&nbsp; {{key.expect}}</span>
							<span id="pre" class="fontgray">前值&nbsp; {{key.pre}}</span>
						</div>
					</li>
				</template>

			</ol>
		</div>
		<!--7x24-->
		<div id="seven" v-else="isshow">
			<div>
				<p>2017-07-20</p>
			</div>
			<ul class="sevenlist">
				<template>
					<li>
						<p class="fl fontgray">
							12:30
						</p>
						<p class="fl">

						</p>
						<p class="fl fontgray">
							据工信部，2017年我国造船三大指标市场份额总体保持世界领先，造船完工量、新接订单量、手持订单量分别占世界市场份额的35.6%、65.2%和43.9%，其中完工量居第一位拓尔思、 兆日科技涨停，美亚柏科北信源等涨幅靠前。

						</p>
						<button class="fontwhite fontxs" @tap='showmore'>展开</button>
					</li>
					<li>
						<p class="fl fontgray">
							12:30
						</p>
						<p class="fl">

						</p>
						<p class="fl fontgray">
							据工信部，2017年我国造船三大指标市场份额总体保持世界领先，造船完工量、新接订单量、手持订单量分别占世界市场份额的35.6%、65.2%和43.9%，其中完工量居第一位拓尔思、 兆日科技涨停，美亚柏科北信源等涨幅靠前。

						</p>
						<button class="fontwhite fontxs"  @tap='showmore'>展开</button>
					</li>
					<li>
						<p class="fl fontgray ">
							12:30
						</p>
						<p class="fl ">
	
						</p>
						<p class="fl fontgray ">
							据工信部，2017年我国造船三大指标市场份额总体保持世界领先，造船完工量、新接订单量、手持订单量分别占世界市场份额的35.6%、65.2%和43.9%，其中完工量居第一位拓尔思、 兆日科技涨停，美亚柏科北信源等涨幅靠前。
							
						</p>
						<button class="fontwhite fontxs "" @tap='showmore'>展开</button>
					</li>
					<li>
						<p class="fl fontgray">
							12:30
						</p>
						<p class="fl">

						</p>
						<p class="fl fontgray">
							据工信部，2017年我国造船三大指标市场份额总体保持世界领先，造船完工量、新接订单量、手持订单量分别占世界市场份额的35.6%、65.2%和43.9%，其中完工量居第一位拓尔思、 兆日科技涨停，美亚柏科北信源等涨幅靠前。 Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ad maxime aperiam voluptatibus voluptates nulla et modi veritatis nostrum vel deleniti maiores delectus aut est? Repellendus quas nulla ipsum sapiente sequi.
						</p>
						<button class="fontwhite fontxs" @tap='showmore'>展开</button>
					</li>
				</template>

			</ul>
		</div>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	export default {
		name: 'information',
		components: {
			topbar
		},
		methods: {
			showdate: function() {
				$('.selectbar span').removeClass('currentsel');
				$('.selectbar li:first-child span').addClass('currentsel');
				if(this.isshow == false) {
					this.isshow = true;
				}
			},
			showseven: function() {
				$('.selectbar span').removeClass('currentsel');
				$('.selectbar li:last-child span').addClass('currentsel');
				if(this.isshow == true) {
					this.isshow = false;
				}
			},
			showmore: function(e) {
				if($(e.target).text() == '展开') {
					$(e.target).html('收起');
					$(e.target).prev().css({
						'overflow': 'visible',
						'height': 'auto'
					});
				} else {
					$(e.target).html('展开');
					$(e.currentTarget).prev().css({
						'overflow': 'hidden',
						'height': '45px'
					});
				}
			}
		},
		data() {
			return {
				isshow: true,
				datalist: [{
						msgdate: '6:30',
						stateurl: require('../assets/img/china.png'),
						state: '中国',
						starurl: require('../assets/img/star3.png'),
						content: '6月制造业产出环比',
						today: '---',
						expect: '0.20%',
						pre: '2.00%'
					},
					{
						msgdate: '7:30',
						stateurl: require('../assets/img/usa.png'),
						state: '美国',
						starurl: require('../assets/img/star1.png'),
						content: '6月制造业产出环比',
						today: '---',
						expect: '0.20%',
						pre: '2.00%'
					},
					{
						msgdate: '6:30',
						stateurl: require('../assets/img/china.png'),
						state: '中国',
						starurl: require('../assets/img/star3.png'),
						content: '6月制造业产出环比',
						today: '---',
						expect: '0.20%',
						pre: '2.00%'
					}
				]
			}

		},
		mounted: function() {
			$('#information').css('height', window.screen.height + 'px');
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	#information {
		box-sizing: content-box;
		padding-top: 100px;
		padding-bottom: 50px;
		background-color: #1b1b26;
		overflow-y: scroll;
	}
	
	.selectbar {
		position: fixed;
		top: 50px;
		width: 100%;
		z-index: 600;
	}
	
	.selectbar>ul:after {
		content: '';
		display: block;
		clear: both;
	}
	
	.selectbar>ul {
		width: 100%;
		height: 40px;
		background-color: #242633;
	}
	
	span {
		box-sizing: content-box;
		padding: 3%;
		border-bottom: 3px solid transparent;
		font-size: 14px;
		color: #fff;
	}
	
	.selectbar ul>li {
		width: 50%;
		text-align: center;
		height: 40px;
		line-height: 40px;
	}
	
	span.currentsel {
		color: #ffd400;
		border-bottom: 3px solid #ffd400;
	}
	
	.currentday {
		color: #ffd400;
	}
	
	.currentday p {
		color: inherit;
	}
	
	#calendar>ul {
		width: 100%;
		height: 45px;
		background-color: #1b1b26;
	}
	
	ul>li {
		color: #fff;
	}
	
	ul>li>p {
		color: #fff;
		font-size: 12px;
	}
	
	ul>li>h4 {
		font-weight: normal;
	}
	
	#calendar>header {
		text-align: center;
		font-size: 12px;
		height: 30px;
		line-height: 30px;
		background-color: #1b1b26;
	}
	
	#calendar>ul>li {
		width: 14.286%;
		text-align: center;
	}
	
	.lists {
		width: 100%;
		height: 120px;
		background-color: #242633;
		border-bottom: 3px solid #1b1b26;
	}
	
	.lists img {
		height: 12px;
	}
	
	.lists img:last-child {
		float: right;
		margin-top: 3%;
		margin-right: 4%;
	}
	
	.lists>div:first-child {
		height: 40px;
		line-height: 40px;
	}
	
	.lists>p {
		height: 40px;
		line-height: 40px;
		padding-left: 3%;
	}
	
	.lists>div:last-child {
		height: 40px;
		line-height: 40px;
		padding-bottom: 5%;
	}
	
	.lists>div:last-child>span {
		display: inline-block;
		width: 25%;
		text-align: left;
		height: 40px;
		line-height: 10px;
	}
	
	.sevenlist li {
		position: relative;
		margin-bottom: 10%;
	}
	
	.sevenlist li:after {
		content: '';
		display: block;
		clear: both;
	}
	
	.sevenlist p {
		/*控制文字默认高度*/
		height: 45px;
		overflow: hidden;
	}
	
	.sevenlist p:first-child {
		width: 15%;
	}
	
	.sevenlist p:nth-child(2) {
		width: 2%;
		/*border-right: 1px solid #ffd400;*/
		background-image: url('../assets/img/quanline.png');
		background-size: 100%;
		height: 120px;
		position: absolute;
		left: 10%;
		top: 15%;
	}
	
	.sevenlist p:nth-child(3) {
		width: 80%;
	}
	
	button {
		position: absolute;
		bottom: -20px;
		right: 25px;
		z-index: 30;
		background: transparent;
		outline: none;
		border: none;
	}
	
	#seven {
		padding-left: 5%;
	}
	
	#seven>div:first-child {
		width: 25%;
		height: 30px;
		line-height: 30px;
		border: 1px solid #9da3c4;
		border-radius: 50px;
		text-align: center;
		font-size: 12px;
		margin-bottom: 3%;
		margin-top: 1%;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		.selectbar {
			top: 50px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		.selectbar {
			top: 50px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {}
</style>