<template>
	<div id="account_openDetail">
				<div class="account_openDetail_top">
					<ul>
						<li >
							<span @click="timeQUuery">今天</span>
							<span @click="timeQUuery" class="current">一个月</span>
							<span @click="timeQUuery" >三个月</span>
							<span @click="timeQUuery">一年</span>
							<span>起始时间</span>
							<!--<span>2017-07-15</span>
							<span>></span>
							<span>2017-07-16</span>-->
							<div class="time">
								<input type="text" readonly="readonly" class="fl startTime" :value="startTime" />
								<i class="ifont fl">&#xe690;</i>
								<input type="text" readonly="readonly" class="fr endTime" :value="endTime" />
							</div>
							<button class="btn blue" @click="serchEvent">搜索</button>
						</li>
						<li id="conditionQuery">
							<span @click="conditionQuery" class="current">全部</span>
							<span @click="conditionQuery">开户中</span>
							<span @click="conditionQuery">操盘中</span>
							<span @click="conditionQuery">已完结</span>
						</li>
					</ul>
				</div>
				<div class="account_openDetail_center">
					<table>
						<thead>
							<tr>
								<td>交易品种</td>
								<td>状态</td>
								<td>初始保证金</td>
								<td>追加保证金</td>
								<td>总操盘资金</td>
								<td>亏损平仓线</td>
								<td>申请时间</td>
								<td>结算时间</td>
								<td>结算金额</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(item,index) in showList" v-if="show_detail">
								<td>{{item.businessTypeStr}}</td>
								<td v-if="item.stateTypeStr == '审核不通过'">开户失败</td>
								<td v-else="item.stateTypeStr != '审核不通过'">{{item.stateTypeStr}}</td>
								<td>{{item.traderBond}}元</td>
								<td v-if="item.appendTraderBond!=''">{{item.appendTraderBond}}</td>
								<td v-else>-</td>
								<td>{{item.traderTotal}}元</td>
								<td>{{item.lineLoss}}美元</td>
								<td>{{item.appTime | getTime}}</br>{{item.appTime | getTimeTwo}}</td>
								<td v-if="item.stateTypeStr == '已完结'">{{item.endTime | getTime}}</br>{{item.endTime | getTimeTwo}}</td>
								<td v-else="item.stateTypeStr != '已完结'">-</td>
								<td v-if="item.endAmount!=''">{{item.endAmount}}</td>
								<td v-else="item.endAmount == ''">-</td>
								<td v-if="item.stateTypeStr =='开户中'">-</td>
								<td v-else-if="item.stateTypeStr=='审核不通过'">-</td>
								<td v-else-if="item.stateTypeStr=='开户失败'">-</td>
								<td v-else-if="item.stateTypeStr == '操盘中'">
									<span v-on:click="toCheckAccount(item.id)">查看账户</span></br>
									<span v-on:click="addMoney(item.id)">补充保证金</span></br>
									<span v-on:click="toCloseAccount(item.id)">结算方案</span>
								</td>
								<td v-else-if="item.stateTypeStr == '已完结'" v-on:click="toParticulars(item.id)">结算明细</td>
							</tr>
						</tbody>
					</table>
					<div v-if="show_button" class="show_button">
						<button class="yellow  btn" v-on:click="toOpenAccount">我要开户</button>
						<p>(一个账号可同时交易多种期货产品)</p>
					</div>
				</div>
				<div class="pager">
				    <button class="btn_span"   v-show="currentPage != 1" @click="currentPage-- && goIndex(currentPage--,'last')">上一页</button>
				    <span  v-for="index in pages" @click="goIndex(index)" :class="{active:currentPage == index}" :key="index">{{index}}</span>
				    <button class="btn_span"  v-show="pageCount != currentPage && pageCount != 0 " @click="currentPage++ && goIndex(currentPage++,'!last')">下一页</button>
				</div>
				<div class="account_openDetail_notice" v-show="showNotice">
					<p>注意：</p>
					<ul>
						<li>1.交易手续费=合约手续费x手数</li>
						<li>2.买卖各算一手</li>
						<li>3.结算金额=操盘保证金+补充保证金+交易盈亏-交易手续费</li>
					</ul>
				</div>
				<div class="account_openDetail_btm">
					<p>投资有风险，入市需谨慎</p>
				</div>
			</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "account_openDetail",
		data(){
			return{
				show_detail : true,
				show_button : false,
				listId:'',
				startTime: '',
				endTime: '',
				showList:[],//显示数据
				pageCount:'',//总页数
				eachPage:10,//每页条数
				showPage:true,//是否显示分页
				currentPage:1,//当前页
				totalList:[],//总数据,
				showNotice:true,
				day:'',
				query:''
			}
		},
		filters:{
			getTime: function(e){
				return pro.getDate('y-m-d',e*1000);
			},
			getTimeTwo:function(e){
				return pro.getDate('h:i:s',e*1000);
			}
		},
		methods:{
			goIndex:function(index,el){
	            if(index == this.currentPage) return;
	            this.currentPage = index;
	            var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
	            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
	            this.showList=this.totalList.slice(curtotal,tiaoshu); //当前页应显示的数据
	        },
			//日历
			serchEvent: function(){
				this.selectedNum = -1;
				this.startTime = $(".startTime").val();
				this.endTime = $(".endTime").val();
				var beginTime = $(".startTime").val() + ' 00:00:00';
				var t =  Date.parse(new Date(this.endTime)) + 86400000;
				var endTime = pro.getDate("y-m-d", t) + ' 00:00:00';
				this.getData('',beginTime,endTime,'')
			},
			//结算方案
			toCloseAccount:function(a){
				this.listId = a;
				this.$router.push({path:'/account_endScheme',query:{"id":this.listId}});
			},
			//查看账户
			toCheckAccount:function(a){
				this.listId = a;
			this.$router.push({path:'/openDetail_viewAccount',query:{"id":this.listId}});	
			},
			//添加保证金
			addMoney:function(a){
				this.listId = a;
				this.$router.push({path:"/openDetail_additionalMargin",query:{"id":this.listId}});
			},
			//去结算明细
			toParticulars:function(a){
				this.listId = a;
				this.$router.push({path:"/openDetail_billingDetails",query:{"id":this.listId}});
			},
			//去开户
			toOpenAccount :function(){
				this.$router.push({path:'/openAccount'});
			},
			//时间查询
			timeQUuery :function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).addClass("current").siblings().removeClass("current");
				switch (index){
					//今天
					case 0:
					this.getData('',this.getNowDate(),this.getNowFormatDate(),this.query)
					this.day = 0;
					break;
					//一个月
					case 1:
					this.day = 1;
					this.getData('',this.getMonthDate(),this.getNowFormatDate(),this.query)
					break;
					//三个月
					case 2:
					this.day = 2;
					this.getData('',this.getThreeMonthDate(),this.getNowFormatDate(),this.query)
					break;
					//一年
					case 3:
					this.day = 3;
					this.getData('',this.getYearDate(),this.getNowFormatDate(),this.query)
					break;
				}
			},
			//条件查询
			conditionQuery : function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).addClass("current").siblings().removeClass("current");
				switch (index){
					case 0:
					this.showNotice = true;
					this.query = '';
					switch (this.day){
						case 0:
							this.getData('',this.getNowDate(),this.getNowFormatDate(),'')
							break;
						case 1:
							this.getData('',this.getMonthDate(),this.getNowFormatDate(),'')
							break;
						case 2:
							this.getData('',this.getThreeMonthDate(),this.getNowFormatDate(),'')
							break;
						case 3:
							this.getData('',this.getYearDate(),this.getNowFormatDate(),'')
							break;
						default:
							break;
					}
						break;
					case 1:
						this.showNotice = true;
						this.query = 1;
						switch (this.day){
							case 0:
								this.getData('',this.getNowDate(),this.getNowFormatDate(),1)
								break;
							case 1:
								this.getData('',this.getMonthDate(),this.getNowFormatDate(),1)
								break;
							case 2:
								this.getData('',this.getThreeMonthDate(),this.getNowFormatDate(),1)
								break;
							case 3:
								this.getData('',this.getYearDate(),this.getNowFormatDate(),1)
								break;
							default:
								break;
						}
						break;
					case 2:
						this.showNotice = false;
						this.query = 4;
						switch (this.day){
							case 0:
								this.getData('',this.getNowDate(),this.getNowFormatDate(),4)
								break;
							case 1:
								this.getData('',this.getMonthDate(),this.getNowFormatDate(),4)
								break;
							case 2:
								this.getData('',this.getThreeMonthDate(),this.getNowFormatDate(),4)
								break;
							case 3:
								this.getData('',this.getYearDate(),this.getNowFormatDate(),4)
								break;
							default:
								break;
						}
						break;
					case 3:
						this.showNotice = false;
						this.query = 6;
						switch (this.day){
							case 0:
								this.getData('',this.getNowDate(),this.getNowFormatDate(),6)
								break;
							case 1:
								this.getData('',this.getMonthDate(),this.getNowFormatDate(),6)
								break;
							case 2:
								this.getData('',this.getThreeMonthDate(),this.getNowFormatDate(),6)
								break;
							case 3:
								this.getData('',this.getYearDate(),this.getNowFormatDate(),6)
								break;
							default:
								break;
						}
						break;
				}
			},
			//获取数据
			getData:function(page,startT,endT,chooseType){
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				var info = {
					page:page,
					rows:'',
					startTime:startT,
					endTime:endT,
					stateType:chooseType
				};
				pro.fetch("post",'/user/ftrade/list',info,headers).then(function(res){
					if(res.success == true){
						if(res.code == 1){
							this.totalList = res.data.tradeList;
							this.pageCount =Math.round(this.totalList.length/this.eachPage);
							var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
				            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
				            this.showList=this.totalList.slice(curtotal,tiaoshu); //当前页应显示的数据
						}
					}
				}.bind(this)).catch(function(err){
					layer.msg('网络不给力，请稍后重试',{time:1000})
				})
			},
			//获取结束时间
			 getNowFormatDate:function() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23:59:59";
			    return currentdate;
			},
			//获取一天
			 getNowDate:function() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + (strDate-1)+
			    " "+"00:00:00";
			    return currentdate;
			},
			//获取3个月时间
			getThreeMonthDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			   if(month<3){
			   	month = 12-month;
			   	year = year - 1;
			   }else{
			   	month = month-3
			   }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23:59:59";
			    return currentdate
			},
			//获取1年数据
			getYearDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = (date.getFullYear()-1) + seperator1 + month + seperator1 + strDate+
			    " "+"23:59:59";
			    return currentdate
			},
			//获取一个月数据
			getMonthDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    var year = date.getFullYear();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = year + seperator1 + (month-1) + seperator1 + strDate+
			    " "+"23:59:59";
			    return currentdate
			}
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 80 - 90;
			var contH = $("#account_openDetail").height();
			if(contH > _h){
				$("#account_openDetail").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$("#account_openDetail").height(_h);
				}
			});
			//获取初始开户记录
			this.getData('',this.getMonthDate(),this.getNowFormatDate(),'');
			this.day = 1;
			this.query = '';
			//调用日历插件
			dateEvent('.startTime');
			dateEvent('.endTime');
			var date = new Date();
			var time = pro.getDate("y-m-d", date.getTime()).split(' ')[0];
			this.startTime = time;
			this.endTime = time;
		},
		computed:{
			pages:function(){
                var pag = [];
                    if( this.currentPage < this.eachPage ){
                       var i = Math.min(this.eachPage,this.pageCount);
                       while(i){
                           pag.unshift(i--);
                       }
                    }else{ 
                       var middle = this.currentPage - Math.floor(this.eachPage / 2 ),
                           i = this.eachPage;
                       if( middle >  (this.pageCount - this.eachPage)  ){
                           middle = (this.pageCount - this.eachPage) + 1
                       }
                       while(i--){
                           pag.push( middle++ );
                       }
                   }
                 return pag
                }
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#account_openDetail{
			overflow-y: auto;
			float: left;
			width: 100%;
			height:1050px;
			.account_openDetail_top {
				height:110px;
				background-color: $blue;
				li {
					padding: 0 10px;
					&:nth-child(1){
						height: 70px;
						border-bottom: 1px solid $bottom_color;
						line-height: 70px;
					}
					&:nth-child(2){
						height: 40px;
						line-height: 40px;
					}
					span{
						float: left;
						margin-right: 20px;
						&:hover{
							color: $yellow;
						}
					}
					.btn{
						height: 30px;
						line-height: 30px;
						padding: 0 10px;
						margin-left: 20px;
					}
				}
				.time{
					float: left;
					width: 270px;
					height: 30px;
					overflow: hidden;
					border: 1px solid $lightblue;
					border-radius: 4px;
					margin-top: 20px;
					input{
						width: 109px;
						height: 28px;
						line-height: 28px;
						padding: 0 5px;
						color: $white;
						cursor: pointer;
					}
					.ifont{
						width: 30px;
						height: 28px;
						line-height: 28px;
						text-align: center;
						background: $lightblue;
						font-size: $fs18;
						color: $blue;
					}
				}
			}
			.account_openDetail_center{
				/*height: 500px;*/
				background-color: $blue;
				thead tr{
					height: 30px;
					line-height: 30px;
					background: $bottom_color;
				}
				td{
					padding-left: 10px;
					span{
						&:hover{
							color: $yellow;
						}
					}
				}
				tbody tr td{
					padding: 15px 0 15px 10px;
					border-bottom: 1px solid $bottom_color;
					span{
						text-decoration: underline;
						line-height: 18px;
						font-size: $fs12;
					}
				}
			}
			.account_openDetail_notice {
				height: 120px;
				background-color: $blue;
				padding: 0 10px;
				p {
					color: $yellow;
					font-size: $fs14;
					padding-top: 20px;
				}
				li {
					margin-top: 10px;
					font-size: $fs12;
				}
			}
			.account_openDetail_btm{
				height: 40px;
				p {
					width: 100%;
					height: 40px;
					line-height: 40px;
					margin-top: 5px;
					background-color: $blue;
					text-align: center;
					font-size: $fs12;
				}
			}
		}
		.yellow {
			width: 120px;
			height: 30px;
			margin-top: 85px;
			margin-bottom: 10px;
			
		}
		.show_button {
			text-align: center;
			p{
				font-size: $fs12;
			}
		}
		.current {
			color: $yellow
		}
		.pager{
			margin-right: 60px;
			float: right;
			span{
				margin: 0 10px;
			}
		}
		.btn_span{
			color: $white;
			background-color: $blue;
			border: none;
			margin: 0 10px;
		}
		.active {
			color: $yellow;
		}
</style>