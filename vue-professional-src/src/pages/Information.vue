<template>
	<div id="information">
		<topbar title="直播"></topbar>
		<button id="topbtn" @tap='toSearch' v-show='!isshow'></button>
		<i class="icon_country" v-show="isshow" @tap="openDialog"></i>
		<countryDialog></countryDialog>
		<div class="date_box" v-show="isshow" @tap="openDate">
			<input type="date" class="icon_date"  v-model.lazy="selectDate" />
		</div>
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
			<header class="fontwhite">{{day.y}}年{{day.m }}月{{day.day}}日</header>
			<p id='tishi' class="fontgray" style="text-align: center;margin-top: 20%;" v-show="isnull" @tap='getAll'>正在请求数据，请稍等</p>
			<ul>
				<li class="fl" @tap='selectday'>
					<p>{{lastdaythree.weekday}}</p>
					<h4>{{lastdaythree.day}}</h4>
					<div style="display: none;">{{lastdaythree}}</div>
				</li>
				<li class="fl" @tap='selectday'>
					<p>{{lastdaytwo.weekday}}</p>
					<h4>{{lastdaytwo.day}}</h4>
					<div style="display: none;">{{lastdaytwo}}</div>
				</li>
				<li class="fl" @tap='selectday'>
					<p>{{lastdayone.weekday}}</p>
					<h4>{{lastdayone.day}}</h4>
					<div style="display: none;">{{lastdayone}}</div>
				</li>
				<li class="fl currentday">
					<p>{{weekday}}</p>
					<h4>{{day.day}}</h4>
				</li>
				<li class="fl" @tap='selectday'>
					<p>{{predayone.weekday}}</p>
					<h4>{{predayone.day}}</h4>
					<div style="display: none;">{{predayone}}</div>
				</li>
				<li class="fl" @tap='selectday'>
					<p>{{predaytwo.weekday}}</p>
					<h4>{{predaytwo.day}}</h4>
					<div style="display: none;">{{predaytwo}}</div>
				</li>
				<li class="fl" @tap='selectday'>
					<p>{{predaythree.weekday}}</p>
					<h4>{{predaythree.day}}</h4>
					<div style="display: none;">{{predaythree}}</div>
				</li>
			</ul>
			<ol>
				<template v-for="key in news">
					<li class="lists">
						<div>
							<span id="time" class="fontgray">
								<!--{{key.localDateTime | getTime('HH:mm')}}-->
								{{key.timestamp | getTime('HH:mm')}}
							</span>
							<img :src="key.flagUrl" />
							<span class="state fontgray">
								{{key.country}}
							</span>
							<img :src='key.starimg' />
						</div>
						<p class="content fontwhite fontmd">
							{{key.title}}
						</p>
						<div>
							<span id="today" class="fontyellow">今值&nbsp; {{key.actual | isnull(key.actual)}}</span>
							<span id="expect" class="fontgray">预期&nbsp; {{key.forecast | isnull(key.forecast)}}</span>
							<span id="pre" class="fontgray">前值&nbsp; {{key.previous | isnull(key.previous)}}</span>
						</div>
					</li>
				</template>

			</ol>
		</div>
		<!--7x24-->
		<div id="seven" v-else="isshow">
				<div>
					<p>{{startT | qu0}}</p>
				</div>
				<ul class="sevenlist">
					<template v-for="key in sevenlist">
						<li>
							<p class="fl fontgray">
								{{key.createdAt | getTime('HH:mm')}}
							</p>
							<p class="fl">

							</p>
							<p class="fl fontgray ps">
								{{key.liveTitle}}
							</p>
							<button class="fontwhite fontxs" @tap='showmore'>展开</button>
						</li>
					</template>
					<li id='showmore' class="fontgray" style="text-align: center;height: 40px; margin: 0;font-size: 14px; " @tap='getMore'> {{msg}} </li>
				</ul>
		</div>
	</div>
</template>

<script>
	import topbar from '../components/Topbar.vue'
	import bbtn from '../components/customerService.vue'
	import countryDialog from '../components/countryDialog.vue'
	export default {
		name: 'information',
		components: {
			topbar,
			bbtn,
			countryDialog
		},
		filters: {
			qu0:function(e){
				//2017-07-11 0:0:0
				var arr=e.split(" ");
				return arr[0]
			},
			isnull: function(e) {
				if(!e) {
					return '---'
				} else {
					return e
				}
			},
			getTime: function(e, format) {
				//var format = function(e, format) {
				if(e){
					var len = e.toString().length;
					var t;
					if(len > 10){
						t = new Date(e);
					}else{
						t = new Date(e * 1000);
					}
					var tf = function(i) {
						return(i < 10 ? '0' : '') + i
					};
					return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
						switch(a) {
							case 'yyyy':
								return tf(t.getFullYear());
								break;
							case 'MM':
								return tf(t.getMonth() + 1);
								break;
							case 'mm':
								return tf(t.getMinutes());
								break;
							case 'dd':
								return tf(t.getDate());
								break;
							case 'HH':
								return tf(t.getHours());
								break;
							case 'ss':
								return tf(t.getSeconds());
								break;
						};
					});
				}
				
				//				};
			}
		},
		updated: function() {
			this.getHeight();
		},
		methods: {
			//自动更改当前时间为前一天
			updateTime:function(){
				this.sevenlist=[];
				var time = this.startT;
				this.timec = this.time.getTime();
				this.timec = this.timec-(1 * 24 * 3600 * 1000);
				var times=new Date(this.timec);
				var year=times.getFullYear();
				var month=times.getMonth()+1;
				var day=times.getDate();
				var today=year+'-'+month+'-'+day;
				if((month.toString().length) < 2) {
					month = '0' + month;
				}
				if(day.toString().length < 2) {
					day = '0' + day
				}
				this.selectDate=today;
				
			},
			getAll: function() {
				this.isimp = false;
				this.countArr = ['全部'];
				this.getdate();
				this.getnews(this.startT, this.endT);
			},
			selectday: function(e) {
				var objstr = $(e.currentTarget).children('div').text();
				var obj = JSON.parse(objstr);
				var time = obj.y + '-' + obj.m + '-' + obj.day;
				this.selectDate = time;
				this.timec = this.time.getTime();
			},
			getnews: function(starttime, endtime) {
				if(starttime == '0-NaN-NaN' || endtime == 'NaN-NaN-NaN') {
					this.getdate();
				}
				this.$http.post(
					this.PATH + '/crawler/getCrawlerCalendarByTime', {
						emulateJSON: true
					}, {
						params: {
							startTime: starttime,
							endTime: endtime
						},
						timeout: 5000
					}

				).then(function(e) {
//					console.log(e.body.data.data);
					var arr1 = [];
					var arr2 = e.body.data.data;
					console.log(arr2);
//					arr2.forEach(function(e){
//						console.log(e.importance);
//					});
					//筛选出重要为3星的
					if(this.isimp == true) {
						arr2.forEach(function(a) {
							if(a.importance == '3') {
								arr1.push(a);
							}
						});
						this.news = arr1;
					} else {
						this.news = arr2;
					}
					//					当选择部分的时候
					if(this.countArr[0] != '全部') {
						var arrn = [];
						for(var i = 0; i < this.countArr.length; i++) {
							for(var r = 0; r < this.news.length; r++) {
								if(this.countArr[i] == this.news[r].country) {
									arrn.push(this.news[r]);
								}
							}
						}
						this.news = arrn;
					}
				}, function(e) {
//					console.log(e);
				});
			},
			getMore: function() {
				this.sevenparams.pageIndex += 1;
				$('#showmore').text('正在加载数据...');
				this.$http.post(
					this.PATH + '/crawler/getCrawler', {
						emulateJSON: true
					}, {
						params: {
							pageIndex: this.sevenparams.pageIndex,
							size: this.sevenparams.size,
							minTime:this.sstartT,
							maxTime:this.sendT
						},
						timeout: 5000
					}
				).then(function(e) {
					var sevenMore = e.body.data.data;
					if(sevenMore != null){
						sevenMore.forEach(function(o, i){
							var str = o.liveTitle.replace(/<p>/g, '');
							str = str.replace(/<\/p>/g, '');
							o.liveTitle = str;
							this.sevenlist.push(o);
						}.bind(this));
						$('#showmore').text('点击加载更多...');
					}else{
						$('#showmore').text('查询当日没有更多数据...点击加载前一天数据');
						this.updateTime();
						this.sevenlist=[];
					}
				}, function() {
					//					alert('服务器请求失败，请稍后再试');
					$('#showmore').text('点击重新请求数据...');
					this.sevenparams.pageIndex -= 1;
				});
			},
			getVal: function(s,e) {
				this.$http.post(
					this.PATH + '/crawler/getCrawler', {
						emulateJSON: true
					}, {
						params: {
							pageIndex: 0,
							size: 20,
							minTime:s,
							maxTime:e
						},
						timeout: 5000
					}

				).then(function(e) {
					this.sevenlist = e.body.data.data;
					if(this.sevenlist != null){
						this.sevenlist.forEach(function(o, i){
							var str = o.liveTitle.replace(/<p>/g, '');
							str = str.replace(/<\/p>/g, '');
							o.liveTitle = str;
						}.bind(this));
						this.msg = '点击查看更多...';
					}else{
						this.msg = '查询当日没有更多数据...点击加载前一天数据';
					}
				}.bind(this), function(e) {
					//alert('服务器请求失败，请稍后再试');
					$('#showmore').text('点击重新请求数据...');
					this.sevenparams.pageIndex -= 1;
				});
			},
			toSearch: function() {
				this.$router.push({
					path: '/sevensearch'
				});
			},
			openDialog: function() {
				this.$children[1].isshow = true;
				$(".date_box input").blur();
			},
			openDate: function(){
				this.$children[1].isshow = false;
			},
			showdate: function() {
				$('.selectbar span').removeClass('currentsel');
				$('.selectbar li:first-child span').addClass('currentsel');
				if(this.isshow == false) {
					this.isshow = true;
				}
			},
			showseven: function() {
				this.$children[1].isshow = false;
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
						'height': '42px'
					});
				}
			},
			getHeight: function() {
				//				console.log(document.getElementsByClassName('ps')[0].offsetHeight);
				for(var i = 0; i < document.getElementsByClassName('ps').length; i++) {
					$(document.getElementsByClassName('ps')[i]).css({
						'height': 'auto'
					});
					if(document.getElementsByClassName('ps')[i].offsetHeight > 42) {
						$(document.getElementsByClassName('ps')[i]).next().show();
					} else {
						$(document.getElementsByClassName('ps')[i]).next().css('display', 'none');
					}
					$(document.getElementsByClassName('ps')[i]).css({
						'height': '42px',
						'overflow': 'hidden'
					});
				}
			},
			getdate: function() {
				this.time = new Date();
				this.timec = this.time.getTime();
				this.day.y = this.time.getFullYear();
				this.day.m = this.time.getMonth() + 1;
				this.day.day = this.time.getDate();
				this.day.D = this.time.getDay();
				if((this.day.m.toString().length) < 2) {
					this.day.m = '0' + this.day.m;
				}
				if(this.day.day.toString().length < 2) {
					this.day.day = '0' + this.day.day
				}
				this.selectDate = this.day.y + '-' + this.day.m + '-' + this.day.day;
				//				console.log(this.day.y + '-' + this.day.m + '-' + this.day.day+'-'+this.weekday);
				//
				//				var now = new Date();
				//				var date = new Date(now.getTime() - 1 * 24 * 3600 * 1000);
				//				var month = date.getMonth() + 1;
				//				var day = date.getDate();
				//				console.log(month + '-' + day);
			}
		},
		watch: {
			isshow:function(n,o){
				this.getdate();
				if(n==false){
					this.sevenparams.pageIndex=0;
					//当切换到7*24的时候，重新获得当天时间
					this.getVal(this.sstartT,this.sendT);
				}
			},
			countArr: function(n, o) {
				this.getnews(this.startT, this.endT);
			},
			selectDate: function(n, o) {
				var arr = n.split('-');
				this.day.y = Number(arr[0]);
				this.day.m = Number(arr[1]);
				this.day.day = Number(arr[2]);
				this.time = new Date(this.day.y, this.day.m - 1, this.day.day);
				if((this.day.m.toString().length) < 2) {
					this.day.m = '0' + this.day.m;
				}
				if(this.day.day.toString().length < 2) {
					this.day.day = '0' + this.day.day
				}
				this.day.D = this.time.getDay();
				this.timec = this.time.getTime();
				this.getnews(this.startT, this.endT);
			},
			news: function(n, o) {
				if(n.length > 0) {
					this.isnull = false;
					$('#tishi').text('暂无所选数据，点击此处加载全部数据');
					n.forEach(function(e) {
						switch(e.importance) {
							case '1':
								e.starimg = require('../assets/img/star1.png');
								break;
							case '2':
								e.starimg = require('../assets/img/star2.png');
								break;
							case '3':
								e.starimg = require('../assets/img/star3.png');
								break;
							default:
								e.starimg = require('../assets/img/star0.png');
								break;
						}
					});
				} else {
					this.isnull = true;
					$('#tishi').text('暂无所选数据，点击此处加载全部数据');
				}
			}
		},
		data() {
			return {
				countArr: ['全部'],
				isimp: false,
				isnull: true,
				news: [],
				timec: null,
				time: null,
				selectDate: '',
				day: {
					y: '2017',
					m: '7',
					day: '6',
					D: '4'
				},
				msg: '正在请求数据，请稍后...',
				sevenparams: {
					pageIndex: 0,
					size: 20
				},
				isshow: true,
				sevenlist: []
			}

		},
		computed: {
			sevenlistlength() {
				return this.sevenlist.lenght;
			},
			PATH() {
				return this.$store.getters.PATH
			},
			weekday: function() {
				var arr = [];
				arr[0] = "日";
				arr[1] = "一";
				arr[2] = "二";
				arr[3] = "三";
				arr[4] = "四";
				arr[5] = "五";
				arr[6] = "六";
				return arr[this.day.D]
			},
			lastdayone: function() {
				var obj = {};
				var date = new Date(this.timec - 1 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			lastdaytwo: function() {
				var obj = {};
				var date = new Date(this.timec - 2 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			lastdaythree: function() {
				var obj = {};
				var date = new Date(this.timec - 3 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			predayone: function() {
				var obj = {};
				var date = new Date(this.timec + 1 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			predaytwo: function() {
				var obj = {};
				var date = new Date(this.timec + 2 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			predaythree: function() {
				var obj = {};
				var date = new Date(this.timec + 3 * 24 * 3600 * 1000);
				obj.y = date.getFullYear();
				obj.m = date.getMonth() + 1;
				obj.day = date.getDate();
				obj.D = date.getDay();
				var arr = ["日", "一", "二", "三", "四", "五", "六"];
				obj.weekday = arr[obj.D];
				if((obj.m.toString().length) < 2) {
					obj.m = '0' + obj.m;
				}
				if(obj.day.toString().length < 2) {
					obj.day = '0' + obj.day
				}
				return obj
			},
			startT: function() {
				return this.day.y + '-' + this.day.m + '-' + this.day.day
				//				return this.lastdayone.y+'-'+this.lastdayone.m+'-'+this.lastdayone.day
			},
			endT: function(n, o) {
				return this.predayone.y + '-' + this.predayone.m + '-' + this.predayone.day
				//				return '2017-07-08'
			},
			sstartT(){
				return this.day.y + '-' + this.day.m + '-' + this.day.day
			},
			sendT(){
				return this.predayone.y + '-' + this.predayone.m + '-' + this.predayone.day
			}

		},
		mounted: function() {
			$('#information').css('height', window.screen.height - 20 + 'px');
		},
		activated: function(){
			this.getVal(this.sstartT,this.sendT);
			this.getdate();
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
		.icon_country,
		.icon_date {
			position: fixed;
			top: 14px;
			z-index: 1000;
			display: inline-block;
			width: 22px;
			height: 22px;
		}
		.icon_country {
			right: 70px;
			background: url(../assets/img/country.png) no-repeat center center;
			background-size: 100% 100%;
		}
		.date_box{
			position: fixed;
			top: 0;
			right: 15px;
			z-index: 1000;
			width: 36px;
			height: 50px;
			overflow: hidden;
			background: url(../assets/img/date1.png) no-repeat 14px 14px;
			background-size: 22px 22px;
		}
		.icon_date {
			width: 36px;
			height: 50px;
			top: 0;
			right: 15px;
			padding: 0;
			margin: 0;
			border: none;
			border-radius: 0;
			opacity: 0;
		}
	}
	
	#information {
		padding-top: 90px;
		padding-bottom: 50px;
		background-color: #1b1b26;
	}
	
	.selectbar {
		position: fixed;
		top: 50px;
		width: 100%;
		z-index: 600;
		border-top: 1px solid #1B1B26;
		border-bottom: 1px solid #1B1B26;
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
	
	#calendar {
		padding-top: 75px;
	}
	
	#calendar>header {
		text-align: center;
		width: 100%;
		font-size: 12px;
		height: 30px;
		line-height: 30px;
		background-color: #1b1b26;
		position: fixed;
		top: 50px+40px;
	}
	
	#calendar>ul {
		position: fixed;
		top: 50px+40px+30px;
	}
	
	#calendar>ul>li {
		width: 14.286%;
		text-align: center;
	}
	
	.lists {
		width: 100%;
		background-color: #242633;
		border-bottom: 3px solid #1b1b26;
	}
	
	#calendar>ol {
		padding-bottom: 60px;
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
		line-height: 40px;
		padding: 0 15px;
		font-size: 16px;
		font-weight: 200;
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
		font-size: 14px;
	}
	
	#seven {
		background: #242633;
		padding-bottom: 50px;
	}
	
	#seven:before {
		content: '';
		display: table;
	}
	
	.sevenlist li {
		position: relative;
		margin-bottom: 10%;
	}
	
	.sevenlist:after {
		content: '';
		display: block;
		clear: both;
	}
	
	.sevenlist li:after {
		content: '';
		display: block;
		clear: both;
	}
	
	.sevenlist p {
		/*控制文字默认高度*/
		/*height: 42px;
		overflow: hidden;*/
	}
	
	.sevenlist p:first-child {
		width: 15%;
	}
	
	.sevenlist p:nth-child(2) {
		width: 2%;
		/*border-right: 1px solid #ffd400;*/
		/*background-image: url('../assets/img/quanline.png');*/
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
		margin-bottom: 10px;
		margin-top: 10px;
	}
	
	#topbtn {
		display: block;
		position: fixed;
		top: 0;
		right: 0;
		z-index: 999;
		height: 50px;
		width: 100px;
		background-image: url(../assets/img/search.png);
		background-size: 17px 17px;
		background-position: 100px-17px-15px 15px;
		background-repeat: no-repeat;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#calendar>header {
			position: fixed;
			top: 50px*@ip5+40px*@ip5;
		}
		#calendar>ul {
			position: fixed;
			top: 50px*@ip5+40px*@ip5+30px;
		}
		.lists>div:last-child>span {
			display: inline-block;
			width: 25%;
			text-align: left;
			height: 40px;
			line-height: 10px;
			font-size: 14px*@ip5;
		}
		.lists>p {
			line-height: 40px*@ip5;
			padding: 0 15px*@ip5;
			font-size: 16px*@ip5;
			font-weight: 200;
		}
		#calendar>ol {
			padding-bottom: 60px*@ip5;
		}
		#seven {
			background: #242633;
			padding-bottom: 50px*@ip5;
		}
		.icon_country,
		.icon_date {
			position: fixed;
			top: 14px*@ip5;
			z-index: 1000;
			display: inline-block;
			width: 22px*@ip5;
			height: 22px*@ip5;
		}
		.icon_country {
			right: 70px*@ip5;
			background: url(../assets/img/country.png) no-repeat center center;
			background-size: 100% 100%;
		}
		.date_box{
			position: fixed;
			top: 0;
			right: 15px*@ip5;
			z-index: 1000;
			width: 36px*@ip5;
			height: 50px*@ip5;
			overflow: hidden;
			background: url(../assets/img/date1.png) no-repeat 14px*@ip5 14px*@ip5;
			background-size: 22px*@ip5 22px*@ip5;
		}
		.icon_date {
			width: 36px*@ip5;
			height: 50px*@ip5;
			top: 0;
			right: 15px*@ip5;
			padding: 0;
			margin: 0;
			border: none;
			border-radius: 0;
			opacity: 0;
		}
		#seven>div:first-child>P {
			transform: scale(0.8);
		}
		.selectbar {
			top: 50px*@ip5;
		}
		#topbtn {
			display: block;
			position: fixed;
			top: 0;
			right: 0;
			z-index: 999;
			height: 50px*@ip5;
			width: 100px*@ip5;
			background-image: url(../assets/img/search.png);
			background-size: 17px*@ip5 17px*@ip5;
			background-position: (100px-17px-15px)*@ip5 15px*@ip5;
			background-repeat: no-repeat;
		}
		#information {
			padding-top: 90px*@ip5;
			padding-bottom: 50px*@ip5;
			background-color: #1b1b26;
		}
		.selectbar {
			position: fixed;
			top: 50px*@ip5;
			width: 100%;
			z-index: 600;
			border-top: 1px solid #1B1B26;
			border-bottom: 1px solid #1B1B26;
		}
		.selectbar>ul {
			width: 100%;
			height: 40px*@ip5;
			background-color: #242633;
		}
		.selectbar ul>li {
			width: 50%;
			text-align: center;
			height: 40px*@ip5;
			line-height: 40px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#calendar>header {
			position: fixed;
			top: 50px*@ip6+40px*@ip6;
		}
		#calendar>ul {
			position: fixed;
			top: 50px*@ip6+40px*@ip6+30px;
		}
		.lists>div:last-child>span {
			display: inline-block;
			width: 25%;
			text-align: left;
			height: 40px;
			line-height: 10px;
			font-size: 14px*@ip6;
		}
		.lists>p {
			line-height: 40px*@ip6;
			padding: 0 15px*@ip6;
			font-size: 16px*@ip6;
			font-weight: 200;
		}
		#calendar>ol {
			padding-bottom: 60px*@ip6;
		}
		#seven {
			background: #242633;
			padding-bottom: 50px*@ip6;
		}
		.icon_country,
		.icon_date {
			position: fixed;
			top: 14px*@ip6;
			z-index: 1000;
			display: inline-block;
			width: 22px*@ip6;
			height: 22px*@ip6;
		}
		.icon_country {
			right: 70px*@ip6;
			background: url(../assets/img/country.png) no-repeat center center;
			background-size: 100% 100%;
		}
		.date_box{
			position: fixed;
			top: 0;
			right: 15px*@ip6;
			z-index: 1000;
			width: 36px*@ip6;
			height: 36px*@ip6;
			overflow: hidden;
			background: url(../assets/img/date1.png) no-repeat 14px*@ip6 14px*@ip6;
			background-size: 22px*@ip6 22px*@ip6;
		}
		.icon_date {
			width: 50px*@ip6;
			height: 50px*@ip6;
			top: 0;
			right: 15px*@ip6;
			padding: 0;
			margin: 0;
			border: none;
			border-radius: 0;
			opacity: 0;
		}
		.selectbar {
			top: 50px*@ip6;
		}
		.selectbar {
			position: fixed;
			top: 50px*@ip6;
			width: 100%;
			z-index: 600;
			border-top: 1px solid #1B1B26;
			border-bottom: 1px solid #1B1B26;
		}
		.selectbar>ul {
			width: 100%;
			height: 40px*@ip6;
			background-color: #242633;
		}
		.selectbar ul>li {
			width: 50%;
			text-align: center;
			height: 40px*@ip6;
			line-height: 40px*@ip6;
		}
		#topbtn {
			display: block;
			position: fixed;
			top: 0;
			right: 0;
			z-index: 999;
			height: 50px*@ip6;
			width: 100px*@ip6;
			background-image: url(../assets/img/search.png);
			background-size: 17px*@ip6 17px*@ip6;
			background-position: (100px-17px-15px)*@ip6 15px*@ip6;
			background-repeat: no-repeat;
		}
		#information {
			padding-top: 90px*@ip6;
			padding-bottom: 50px*@ip6;
			background-color: #1b1b26;
		}
	}
</style>