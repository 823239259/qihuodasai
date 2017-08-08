<template>
	<div id="sevensearch">
		<div id="searchtopbar">
			<back></back>
			<input type="text" id='searchinput' placeholder="你在寻找什么呢？" v-model.trim="inpt" @focus="cl" @keyup.enter='keyup' @blur='keyup'/>
		</div>
		<ul class="searchlist" v-if="searchshow">
			<li>
				<div class="fontwhite fl">
					搜索记录
				</div>
				<div class="fontwhite fr" @tap='clear'>
					清除搜索记录
				</div>
			</li>
			<template v-for='key in searcharr'>
				<li class="fontgray" @tap='usedata'>{{key}}</li>
			</template>
		</ul>
		<!--搜索列表-->
		<div id="seven" v-else-if="contShow">
			<div>
				<p>{{this.selectDate}}</p>
			</div>
			<ul class="sevenlist">
				<template v-for="key in news">
					<li>
						<p class="fl fontgray">
							{{ key.createdAt | getTime('HH:mm') }}
						</p>
						<p class="fl">

						</p>
						<p class="fl fontgray ps">
							{{key.liveTitle}}
						</p>
						<button class="fontwhite fontxs" @tap='showmore'>展开</button>
					</li>
				</template>

			</ul>
		</div>
		<!--搜索无内容-->
		<div class="empty" id="empty" v-else>
			<i class="icon_none"></i>
			<p>没有找到相关内容</p>
		</div>
	</div>
</template>

<script>
	import back from '../../components/back.vue'
	export default {
		name: 'sevensearch',
		components: {
			back
		},
		updated: function() {
			this.getHeight();
		},
		filters:{
			getTime: function(e, format) {
				//				var format = function(e, format) {
				var t = new Date(e * 1000);
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
				//				};
			}	
		},
		data() {
			return {
				inpt: '',
				replace:'',
				liststr: '',
				searcharr: [],
				searchshow: true,
				contShow: false,
				news: [],
				timec: null,
				time: null,
				selectDate: '',
				day: {
					y: '2017',
					m: '7',
					day: '6',
					D: '4'
				}
			}
		},
		computed: {
			PATH() {
				return this.$store.getters.PATH
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
				obj.time = obj.y + '-' + obj.m +'-'+obj.day;
				return obj
			}
		},
		methods: {
			getHeight: function() {
				//				console.log(document.getElementsByClassName('ps')[0].offsetHeight);
				for(var i = 0; i < document.getElementsByClassName('ps').length; i++) {
					var arr=$(document.getElementsByClassName('ps')[i]).text().split(this.replace);
					var str=''
					for(var r=0;r<arr.length-1;r++){
						str+=(arr[r]+"<span style='color:#ffd400'>"+this.replace+"</span>");
					}
					str+=arr[arr.length-1];
					$(document.getElementsByClassName('ps')[i]).html(str);
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
			},
			search: function(s, e, k) {
				this.$http.post(
					this.PATH + '/crawler/getCrawler', {
						emulateJSON: true
					}, {
						params: {
							pageIndex: '0',
							size: '100',
							minTime: s,
							maxTime: e,
							keyword: k
						},
						timeout: 5000
					}
				).then(function(e) {
					//										console.log(e.body.data)
					this.news = e.body.data.data;
					if(this.news.length>0){
						this.contShow=true
					}else{
						this.contShow=false
					}
					this.msg = '点击查看更多...';
				}.bind(this), function(e) {
				});
			},
			usedata: function(e) {
				var thisdata = e.target.innerText;
				this.inpt = thisdata;
				//取得当前点击的值，后面直接跟搜索逻辑
				this.keyup();
			},
			clear: function() {
				this.searcharr = [];
				this.liststr = '';
				localStorage.searchhistory = '';
			},
			cl: function() {
				this.inpt = '';
				this.showsearch();
			},
			//未完成去重复功能
			keyup: function() {
				this.news=[];
				if(this.inpt.length != 0) {
					this.liststr += (',' + this.inpt);
					var arr = this.liststr.split(',');
					for(var i=0;i<arr.length-1;i++){
						if(this.inpt==arr[i]){
							arr.splice(i,1);
							
						}
					}
					this.liststr=arr.join(',');
					//此处限制条数
					if(arr.length>11){
						arr.shift();
						arr.shift();
						this.liststr=','+arr.join(',');
					}else{
						arr.shift();
					}
					arr.reverse();
					//转数组后存vue当前组件的data里
					this.searcharr = arr;
					//存本地
					localStorage.searchhistory = this.liststr;
					//向后台请求搜索数据。传入开始时间，结束时间，搜索内容
					this.search(this.selectDate, this.predayone.time, this.inpt);
					this.replace=this.inpt;
				}else{
					this.contShow=false;
				}
				$('#searchinput').get(0).blur();
				this.inpt = '';
				this.searchshow = false;
			},
			showsearch: function() {
				this.searchshow = true;
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
		mounted: function() {
			//从本地存储里拿到历史搜索
			var ls = localStorage.searchhistory;
			//如果存在，即转换成数组，并保存在searcharr中
			if(ls) {
				this.liststr = ls;
				var arr = ls.split(',');
				arr.shift();
				arr.reverse();
				this.searcharr = arr;
			//否则默认searcharr为空
			} else {
				this.liststr = '';
				this.searcharr = [];
			}
			this.getdate();
		}
	}
</script>

<style scoped lang="less">
	@import url("../../assets/css/main.less");
	#seven {
		background: #242633;
		padding-bottom: 30px;
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
		height: 45px;
		overflow: hidden;
	}
	
	.sevenlist p:first-child {
		width: 15%;
	}
	
	.sevenlist p:nth-child(2) {
		width: 2%;
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
	
	#sevensearch {
		height: 736px - 20px;
		background-color: #242633;
		padding-top: 50px;
		.searchlist {
			>li {
				width: 100%;
				height: 50px;
				border-bottom: 1px solid #1b1b26;
				background-color: #242633;
				line-height: 50px;
				padding-right: 15px;
				padding-left: 60px;
				font-size: 14px;
			}
			>li:first-child {
				&:after {
					content: '';
					display: block;
					clear: both;
				}
				div:nth-child(2) {
					padding-left: 23px;
					background-image: url('../../assets/img/del.png');
					background-repeat: no-repeat;
					background-size: 17px;
					background-position: 0 15px;
				}
			}
		}
	}
	
	#searchtopbar {
		z-index: 999;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		#seven>div:first-child>p{
			font-size: 12px;
		}
		#searchtopbar {
			width: 414px*@ip5;
			height: 50px*@ip5;
			line-height: 50px*@ip5;
			padding-left: 60px*@ip5;
			background: #36394d;
			position: fixed;
			top: 0;
			input {
				width: 320px*@ip5;
				height: 35px*@ip5;
				border: 1px solid #12121a;
				background-color: #242633;
				outline: none;
				text-indent: 2.5em;
				background-image: url('../../assets/img/searchgray.png');
				background-repeat: no-repeat;
				background-size: 17px*@ip5 17px*@ip5;
				background-position: 15px*@ip5 10px*@ip5;
				border-radius: 5px;
				color: #a3aacc;
				font-size: 14px*@ip5;
				&::-webkit-input-placeholder {
					color: #525566;
				}
			}
		}
		#sevensearch {
			height: 736px*@ip5 - 20px;
			background-color: #242633;
			padding-top: 50px*@ip5;
			.searchlist {
				>li {
					width: 100%;
					height: 50px*@ip5;
					border-bottom: 1px solid #1b1b26;
					background-color: #242633;
					line-height: 50px*@ip5;
					padding-right: 15px*@ip5;
					padding-left: 60px*@ip5;
					font-size: 14px*@ip5;
				}
				>li:first-child {
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					div:nth-child(2) {
						padding-left: 23px*@ip5;
						background-image: url('../../assets/img/del.png');
						background-repeat: no-repeat;
						background-size: 17px*@ip5;
						background-position: 0 15px*@ip5;
					}
				}
			}
		}
		.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px*@ip5 0 0 -207px*@ip5;
			width: 414px*@ip5;
			height: 92px*@ip5;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px*@ip5;
				height: 56px*@ip5;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px*@ip5;
			}
		}
		
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		#searchtopbar {
			width: 414px*@ip6;
			height: 50px*@ip6;
			line-height: 50px*@ip6;
			padding-left: 60px*@ip6;
			background: #36394d;
			position: fixed;
			top: 0;
			input {
				width: 320px*@ip6;
				height: 35px*@ip6;
				border: 1px solid #12121a;
				background-color: #242633;
				outline: none;
				text-indent: 2.5em;
				background-image: url('../../assets/img/searchgray.png');
				background-repeat: no-repeat;
				background-size: 17px*@ip6 17px*@ip6;
				background-position: 15px*@ip6 10px*@ip6;
				border-radius: 5px;
				color: #a3aacc;
				font-size: 14px*@ip6;
				&::-webkit-input-placeholder {
					color: #525566;
				}
			}
		}
		#sevensearch {
			height: 736px*@ip6 - 20px;
			background-color: #242633;
			padding-top: 50px*@ip6;
			.searchlist {
				>li {
					width: 100%;
					height: 50px*@ip6;
					border-bottom: 1px solid #1b1b26;
					background-color: #242633;
					line-height: 50px*@ip6;
					padding-right: 15px*@ip6;
					padding-left: 60px*@ip6;
					font-size: 14px*@ip6;
				}
				>li:first-child {
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					div:nth-child(2) {
						padding-left: 23px*@ip6;
						background-image: url('../../assets/img/del.png');
						background-repeat: no-repeat;
						background-size: 17px*@ip6;
						background-position: 0 15px*@ip6;
					}
				}
			}
		}
		.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px*@ip6 0 0 -207px*@ip6;
			width: 414px*@ip6;
			height: 92px*@ip6;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px*@ip6;
				height: 56px*@ip6;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px*@ip6;
			}
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		#searchtopbar {
			width: 414px;
			height: 50px;
			line-height: 50px;
			padding-left: 60px;
			background: #36394d;
			position: fixed;
			top: 0;
			input {
				width: 320px;
				height: 35px;
				border: 1px solid #12121a;
				background-color: #242633;
				outline: none;
				text-indent: 2.5em;
				background-image: url('../../assets/img/searchgray.png');
				background-repeat: no-repeat;
				background-size: 17px 17px;
				background-position: 15px 10px;
				border-radius: 5px;
				color: #a3aacc;
				font-size: 14px;
				&::-webkit-input-placeholder {
					color: #525566;
				}
			}
		}
		#sevensearch {
			height: 736px - 20px;
			background-color: #242633;
			padding-top: 50px;
			.searchlist {
				>li {
					width: 100%;
					height: 50px;
					border-bottom: 1px solid #1b1b26;
					background-color: #242633;
					line-height: 50px;
					padding-right: 15px;
					padding-left: 60px;
					font-size: 14px;
				}
				>li:first-child {
					&:after {
						content: '';
						display: block;
						clear: both;
					}
					div:nth-child(2) {
						padding-left: 23px;
						background-image: url('../../assets/img/del.png');
						background-repeat: no-repeat;
						background-size: 17px;
						background-position: 0 15px;
					}
				}
			}
		}
		.empty {
			position: absolute;
			top: 50%;
			left: 50%;
			margin: -46px 0 0 -207px;
			width: 414px;
			height: 92px;
			overflow: hidden;
			text-align: center;
			.icon_none {
				display: inline-block;
				width: 84px;
				height: 56px;
				overflow: hidden;
				background: url(../../assets/img/none.png) no-repeat center center;
				background-size: 100% 100%;
			}
			p {
				text-align: center;
				margin-top: 10px;
			}
		}
	}
</style>