<template>
	<div id="dialog" v-show='isshow'>
		<div class="dialog_cont">
			<i @tap="close"></i>
			<p class="title">按国家</p>
			<div class="cont">
				<ul>
					<template>
						<li @tap='chooseCou' :class="{current:key.sel}" v-for="key in countrylists">{{key.name}}<span style="display: none;">{{key.id}}</span></li>
					</template>
				</ul>
			</div>
			<p class="title">按重要性</p>
			<div class="cont">
				<ul>
					<li @tap='chooseCou' :class="{current:key.sel}" v-for="key in importentlist">{{key.name}} </li>
				</ul>
			</div>
			<div class="btn">
				<span @tap="confirm">确认</span>
				<span @tap="close">取消</span>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'dialog',
		watch:{
			isshow:function(){
				this.countrylists.forEach(function(e,k){
					if(k!=9){
						e.sel=false
					}else{
						e.sel=true
					}
				});
				this.importentlist.forEach(function(e,k){
					if(k!=0){
						e.sel=false
					}else{
						e.sel=true
					}
				});
			}
		},
		data() {
			return {
				isshow: false,
				countArr: ['全部'],
				isimp: false,
				countrylists: [{
						name: '中国',
						sel: false,
						id: 0
					},
					{
						name: '美国',
						sel: false,
						id: 1
					},
					{
						name: '欧元区',
						sel: false,
						id: 2
					},
					{
						name: '日本',
						sel: false,
						id: 3
					},
					{
						name: '英国',
						sel: false,
						id: 4
					},
					{
						name: '加拿大',
						sel: false,
						id: 5
					},
					{
						name: '瑞士',
						sel: false,
						id: 6
					},
					{
						name: '德国',
						sel: false,
						id: 7
					},
					{
						name: '澳大利亚',
						sel: false,
						id: 8
					},
					{
						name: '全部',
						sel: true,
						id: 9
					}
				],
				importentlist: [{
						name: '全部',
						sel: true,
						id: 0
					},
					{
						name: '重要',
						sel: false,
						id: 1
					}
				]
			}
		},
		methods: {
			chooseCou: function(e) {
				//点击全部按钮的时候
				if(e.target.innerText == '全部') {
					//下面的全部
					if($(e.target).siblings().length < 9) {
						this.importentlist[0].sel = true;
						this.importentlist[1].sel = false;
						//上面的全部
					} else {
						this.countrylists[9].sel = true;
						this.countrylists[0].sel = false;
						this.countrylists[1].sel = false;
						this.countrylists[2].sel = false;
						this.countrylists[3].sel = false;
						this.countrylists[4].sel = false;
						this.countrylists[5].sel = false;
						this.countrylists[6].sel = false;
						this.countrylists[7].sel = false;
						this.countrylists[8].sel = false;
					}
					//点击重要的时候
				} else if(e.target.innerText == '重要') {
					this.importentlist[1].sel = true;
					this.importentlist[0].sel = false;
					//点击其他按钮的时候
				} else {
					if($(e.target).hasClass('current')) {
						this.countrylists[$(e.target).children('span').text()].sel = false;
						var i;
						for(i = 0; i < this.countrylists.length - 1; i++) {
							if(this.countrylists[i].sel == true) {
								break;
							}
						}
						if(i == 9) {
							this.countrylists[9].sel = true
						}
					} else {
						this.countrylists[9].sel = false;
						//						console.log($(e.target).children('span').text());
						this.countrylists[$(e.target).children('span').text()].sel = true
					}
				}
				//				console.log(this.countArr)
			},
			confirm: function() {
				this.isshow = false;
				this.countArr=[];
				this.countrylists.forEach(function(e,k){
					if(e.sel==true){
						this.countArr.push(e.name);
					}
				}.bind(this));
				this.isimp=this.importentlist[1].sel;
				this.$parent.$data.countArr=this.countArr;
				this.$parent.$data.isimp=this.isimp;
//				console.log(this.$parent.$data.countArr);
//				console.log(this.$parent.$data.isimp);
			},
			close: function() {
				this.isshow = false;
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/base.less");
	#dialog {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		.dialog_cont {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 330px;
			height: 400px;
			overflow: hidden;
			margin: -200px 0 0 -165px;
			i {
				display: inline-block;
				width: 34px;
				height: 34px;
				overflow: hidden;
				background: url(../assets/img/x.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				top: 0;
				right: 0;
			}
			.title {
				height: 44px;
				line-height: 44px;
				text-align: center;
				background: @deepblue;
				border-bottom: 1px solid @black;
				color: @white;
				font-size: @fs16;
			}
			.cont {
				overflow: hidden;
				background: @deepblue;
				padding: 15px 25px 0 25px;
				margin-bottom: 10px;
				ul {
					margin-left: -15px;
					li {
						float: left;
						width: 83px;
						height: 28px;
						line-height: 28px;
						text-align: center;
						background: @blue;
						border-radius: 4px;
						color: @black;
						font-size: @fs14;
						margin: 0 0 15px 15px;
						&.current {
							background: @yellow;
						}
					}
				}
			}
			.btn {
				width: 100%;
				overflow: hidden;
				background: @deepblue;
				span {
					float: left;
					display: inline-block;
					width: 50%;
					height: 44px;
					line-height: 44px;
					text-align: center;
					color: #ccd5ff;
					font-size: @fs14;
				}
			}
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		.dialog_cont {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 330px*@ip6;
			height: 400px*@ip6;
			overflow: hidden;
			margin: -200px*@ip6 0 0 -165px*@ip6;
			i {
				display: inline-block;
				width: 34px*@ip6;
				height: 34px*@ip6;
				overflow: hidden;
				background: url(../assets/img/x.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				top: 0;
				right: 0;
			}
			.title {
				height: 44px*@ip6;
				line-height: 44px*@ip6;
				text-align: center;
				background: @deepblue;
				border-bottom: 1px solid @black;
				color: @white;
				font-size: @fs16*@ip6;
			}
			.cont {
				overflow: hidden;
				background: @deepblue;
				padding: 15px*@ip6 25px*@ip6 0 25px*@ip6;
				margin-bottom: 10px*@ip6;
				ul {
					margin-left: -15px*@ip6;
					li {
						float: left;
						width: 83px*@ip6;
						height: 28px*@ip6;
						line-height: 26px;
						text-align: center;
						background: @blue;
						border-radius: 4px*@ip6;
						color: @black;
						font-size: @fs14*@ip6;
						margin: 0 0 15px*@ip6 15px*@ip6;
						&.current {
							background: @yellow;
						}
					}
				}
			}
			.btn {
				width: 100%;
				overflow: hidden;
				background: @deepblue;
				span {
					float: left;
					display: inline-block;
					width: 50%;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					text-align: center;
					color: #ccd5ff;
					font-size: @fs14*@ip6;
				}
			}
		}
	}
	/*ip5*/
	
	@media(max-width:370px) {
		.dialog_cont {
			position: absolute;
			top: 50%;
			left: 50%;
			width: 330px*@ip5;
			height: 400px*@ip5;
			overflow: hidden;
			margin: -200px*@ip5 0 0 -165px*@ip5;
			i {
				display: inline-block;
				width: 34px*@ip5;
				height: 34px*@ip5;
				overflow: hidden;
				background: url(../assets/img/x.png) no-repeat center center;
				background-size: 100% 100%;
				position: absolute;
				top: 0;
				right: 0;
			}
			.title {
				height: 44px*@ip5;
				line-height: 44px*@ip5;
				text-align: center;
				background: @deepblue;
				border-bottom: 1px solid @black;
				color: @white;
				font-size: @fs16*@ip5;
			}
			.cont {
				overflow: hidden;
				background: @deepblue;
				padding: 15px*@ip5 25px*@ip5 0 25px*@ip5;
				margin-bottom: 10px*@ip5;
				ul {
					margin-left: -15px*@ip5;
					li {
						float: left;
						width: 83px*@ip5;
						height: 28px*@ip5;
						line-height: 22px;
						text-align: center;
						background: @blue;
						border-radius: 4px*@ip5;
						color: @black;
						font-size: @fs14*@ip5;
						margin: 0 0 15px*@ip5 15px*@ip5;
						&.current {
							background: @yellow;
						}
					}
				}
			}
			.btn {
				width: 100%;
				overflow: hidden;
				background: @deepblue;
				span {
					float: left;
					display: inline-block;
					width: 50%;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					text-align: center;
					color: #ccd5ff;
					font-size: @fs14*@ip5;
				}
			}
		}
	}
</style>