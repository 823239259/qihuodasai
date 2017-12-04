<template>
	<div id="liveStream">
		<div class="title">
			<ul>
				<li>直播</li>
				<li>{{latest | getYMD}}{{latest_hms | getHMS}}</li>
			</ul>
			<ul>
				<li><i class="ifont ifont_click">&#xe600;</i>60</li>
				<li>秒后自动刷新</li>
				<li><i class="ifont ifont_refresh">&#xe6d1;</i></li>
			</ul>
		</div>
		<div class="content">
			<ul v-for="(item,index) in arrList">
				<li>{{item.createdAt | showTime}}</li>
				<li>{{item.liveTitle}}</li>
			</ul>
			<!--<div class="content_timelist">
				<i class="ifont ifont_time">&#xe752;</i>
				<span>2017年07月07日</span>
			</div>-->
			<!--<ul v-for="item in arrList1">
				<li>15:37</li>
				<li>英国金融市场行为监督的什么东西我也不会到，但是还是要多打一点字来吧这个东西撑起来，所以还是要不同的打字打字</li>
			</ul>-->
			<div class="add_list">
				<span>加载更多</span>
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
				latest_hms:''
			}
		},
		methods:{
			getInfoList:function(){
				var data ={
					pageIndex:1,
					size:20,
					minTime:'',
					maxTime:'',
					keyword:''
				}
				pro.fetch('post','/crawler/getCrawler',data,{}).then((res)=>{
					console.log(res);
					if(res.success == true){
						if(res.code == ''){
							this.arrList = res.data.data;
							var c = [];
							console.log(this.arrList.length)
							console.log(11111111111111)
							for (var a=0;a<this.arrList.length;a++){
								var b= pro.getDate("y-m-d",this.arrList[a].createdAt*1000);
								c.push(b)
							}
							console.log("------------");
							console.log(c);
						}
					}
				}).catch((err)=>{
//					console.log("-----------------");
//					console.log(err);
				})
			}
		},
		mounted:function(){
			//获取初始数据
			this.getInfoList();
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 47;
			var contH = $("#liveStream").height();
			console.log(contH)
			if(contH > _h){
				$("#liveStream").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 47;
				if(contH > _h){
					$("#liveStream").height(_h);
				}
			});
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
					}
					.ifont_refresh{
						color: $lightblue;
						margin-left: 20px;
						font-size: $fs18;
						cursor: pointer;
					}
				}
			}
		}
	}
	.content{
		float: left;
		width: 100%;
		background-color: $deepblue;
		ul{
			li{
				/*height: 40px;*/
				border-bottom:1px solid $bottom_color;
				border-left:1px solid $bottom_color;
				border-right:1px solid $bottom_color;
				float: left;
				&:nth-child(1){
					font-size: $fs16;
					width: 8%;
					text-align: center;
					/*line-height: 40px;*/
					font-weight: 600;
				}
				&:nth-child(2){
					font-size: $fs14;
					width: 92%;
					padding-left: 10px;
					line-height: 40px;
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
	}
</style>