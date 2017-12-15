<template>
	<div id="liveStream">
		<div class="title">
			<ul>
				<li>直播</li>
				<li>({{showNowDay}}&nbsp;{{showNowTime}})</li>
			</ul>
			<ul>
				<li><i class="ifont ifont_click" v-on:click="autoRefresh">&#xe634;</i>{{time}}</li>
				<li>秒后自动刷新</li>
				<li><i class="ifont ifont_refresh" v-on:click="handRefresh">&#xe6d1;</i></li>
			</ul>
		</div>
		<div class="content">
			<!--<ul v-for="(item,index) in arrList">
				<li>{{item.createdAt | showTime}}</li>
				<li>{{item.liveTitle}}</li>
			</ul>-->
			<div v-for="(item,index) in arrList" class="todayInfo">
				<p>
					<i>{{item.createdAt | showTime}}</i>
					<span>{{item.liveTitle}}</span>
				</p>
			</div>
			<div class="content_timelist" v-show="showbeforeDay">
				<i class="ifont ifont_time">&#xe752;</i>
				<span>2017年07月07日</span>
			</div>
			<ul v-for="item in arrList1" v-show="show_beforeList">
				<li>15:37</li>
				<li>英国金融市场行为监督的什么东西我也不会到，但是还是要多打一点字来吧这个东西撑起来，所以还是要不同的打字打字</li>
			</ul>
			<div class="add_list">
				<span v-on:click="getMore">加载更多</span>
			</div>
		</div>
		<div class="btm">
			<span>投资有风险，入市需谨慎</span>
		</div>
	</div>
</template>

<script>
	import pro from '../assets/js/common.js'
	export default{
		name:'liveStream',
		data(){
			return{
				arrList:'',
				arrList1:'',
				latest:'',
				latest_hms:'',
				counting:0,
				showbeforeDay:false,
				show_beforeList:false,
				colorState:false,
				time:10,
				showNowTime:'',
				showNowDay:'',
				timing:'',
				nowtime:""
			}
		},
		computed:{
		},
		methods:{
			//手动刷新
			handRefresh:function(){
				layer.msg("正在为您刷新最新数据",{time:1000});
				var data = {
					pageIndex:0,
					size:20,
					minTime:this.getNowFormatDate(),
					maxTime:this.getNowFormatDate1(),
					keyword:''
				}
				setTimeout(function(){
					pro.fetch("post",'/crawler/getCrawler',data,{}).then((res)=>{
						if(res.success == true){
							if(res.code == ''){
								this.arrList = res.data.data;
							}
						}
					}).catch((err)=>{
						if(err.success ==false ){
							layer.msg(err.data.message,{time:2000});
						}else{
							layer.msg("网络不给力，请稍后再试",{time:2000});
						}
					})
				}.bind(this),1000)
			},
			//自动刷新
			autoRefresh:function(e){
				var juage = $(e.currentTarget).hasClass("ifont_click1");
				if(juage){
					$(e.currentTarget).removeClass("ifont_click1").html("&#xe634;").css("color","#a3aacc");
					this.time = 10;
					clearInterval(this.timing);
				}else{
					$(e.currentTarget).addClass("ifont_click1").html("&#xe600;").css("color","#ffd400");
					this.timing = setInterval(function(){
						this.time--;
						if(this.time <= 0){
							this.time =10;
							//刷新数据
							this.getInfoList();
						}
					}.bind(this),1000);
				}
			},
			getInfoList:function(){
				var data ={
					pageIndex:0,
					size:20,
					minTime:this.getNowFormatDate(),
					maxTime:this.getNowFormatDate1(),
					keyword:''
				}
				pro.fetch('post','/crawler/getCrawler',data,{}).then((res)=>{
					if(res.success == true){
						if(res.code == ''){
							this.arrList = res.data.data;
						}
					}
				}).catch((err)=>{
					if(err.success ==false ){
						layer.msg(err.data.message,{time:2000});
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000});
					}
				})
			},
			//加载更多
			getMore:function(){
				this.counting += 1;
				var data = {
					pageIndex:this.counting,
					size:20,
					minTime:this.getNowFormatDate(),
					maxTime:this.getNowFormatDate1(),
					keyword:''
				}
				pro.fetch('post','/crawler/getCrawler',data,{}).then((res)=>{
					if(res.success == true && res.code == ''){
						var b = res.data.data;
						for(var i = 0;i<b.length;i++){
							this.arrList.push(b[i]);
						}
						$("#liveStream").css('height', 'auto');
						var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
						var _h = h - 90;
						var contH = $("#liveStream").height();
						if(contH > _h){
							$("#liveStream").height(_h);
						}
					}
				}).catch((err)=>{
					
				})
			},
			//获取今天开始时间
			getNowFormatDate:function(){
			    var date = new Date();
			    var seperator1 = "-";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
			    return currentdate;
			},
			//获取明天时间
			getNowFormatDate1:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate()+1;
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
			    return currentdate;
			},
		},
		mounted:function(){
			//获取初始数据
			this.getInfoList();
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h -40- 47;
			$("#liveStream").height(_h);
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h -40- 47;
				if(contH > _h){
					$("#liveStream").height(_h);
				}
			});
			//时间
			this.nowtime = setInterval(function(){
				var cd = new Date();
				function zeroPadding(num,digit){
				    var zero = '';
				    for(var i = 0; i < digit; i++) {
				        zero += '0';
				    }
				    return (zero + num).slice(-digit);
				}
			    this.showNowTime = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
			    this.showNowDay = zeroPadding(cd.getFullYear(), 4) + '年' + zeroPadding(cd.getMonth()+1, 2) + '月' + zeroPadding(cd.getDate(), 2)+"日";
			
			}.bind(this),1000)
		},
		filters:{
			showTime:function(e){
				var a = pro.getDate("h:m",e*1000);
				return a;
			},
			getYMD:function(e){
				var a = pro.getDate("yy-mm-dd",e*1000);
				return a;
			},
			getHMS:function(e){
				var a = pro.getDate("h:i:s",e*1000);
				return a;
			}
		}
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#liveStream{
		width: 1000px;
		margin: auto;
		overflow-y: auto;
	}
	.title{
		padding: 0  10px;
		width: 100%;
		height: 40px;
		background-color:$bottom_color;
		ul{
			line-height: 40px;
			&:nth-child(1){
				float: left;
				li{
					font-size: $fs12;
					float: left;
					&:nth-child(1){
						font-size: $fs16;
						color: $white;
						padding-right: 10px;
					}
				}
			}
			&:nth-child(2){
				font-size: $fs12;
				float: right;
				li{
					float: left;
					&:nth-child(1){
						padding:0 10px;
					}
					.ifont_click{
						color: $lightblue;
						cursor: pointer;
						padding:0 10px;
						&:hover{
							color: $yellow;
						}
					}
					.ifont_refresh{
						color: $lightblue;
						margin-left: 20px;
						font-size: $fs18;
						cursor: pointer;
						&:hover{
							color: $yellow;
						}
					}
				}
			}
		}
	}
	.content{
		float: left;
		width: 100%;
		background-color: $deepblue;
		.todayInfo{
			p{
			border-left: 1px solid $lightblue;
			width: 100%;
			line-height: 40px;
				i{
					text-align: center;
					display: inline-block;
					float: left;
					width: 8%;
					font-weight: 600;
					font-size: $fs16;
					border-top: 1px solid $bottom_color;
				}
				span{
					padding-left: 10px;
					background-color: $blue;
					float: left;
					width: 90%;
					font-size: $fs14;
					display: inline-block;
					border-top: 1px solid $bottom_color;
					border-left: 1px solid $bottom_color;
				}
			}
		}
		.content_timelist{
			width: 100%;
			height: 40px;
			background-color:$bottom_color ;
			float: left;
			padding-left: 10px;
			line-height: 40px;
			.ifont_time{
				color: $lightblue;
				font-size: $fs16;
			}
			span{
				&:nth-child(2){
					font-size: $fs12;
				}
			}
		}
		.add_list{
			float: left;
			width: 100%;
			height: 60px;
			text-align: center;
			span{
				&:hover{
					color: $yellow;
				}
				line-height: 60px;
				font-size: $fs12;
				color:#ccd5ff;
				cursor: pointer;
			}
		}
	}
	.btm{
		margin-top: 5px;
		float: left;
		width: 100%;
		height: 40px;
		background-color: $blue;
		text-align: center;
		line-height: 40px;
		font-size: $fs12;
	}</style>