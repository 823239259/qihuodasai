<template>
	<div id="liveStream">
		<div class="title">
			<ul>
				<li>直播</li>
				<li>（2017年07月05日 18:05:38）</li>
			</ul>
			<ul>
				<li><i class="ifont ifont_click">&#xe600;</i>60</li>
				<li>秒后自动刷新</li>
				<li><i class="ifont ifont_refresh">&#xe6d1;</i></li>
			</ul>
		</div>
		<div class="content">
			<ul v-for="item in arrList">
				<li>15:37</li>
				<li>英国金融市场行为监督的什么东西我也不会到，但是还是要多打一点字来吧这个东西撑起来，所以还是要不同的打字打字</li>
			</ul>
			<div class="content_timelist">
				<i class="ifont ifont_time">&#xe752;</i>
				<span>2017年07月07日</span>
			</div>
			<ul v-for="item in arrList1">
				<li>15:37</li>
				<li>英国金融市场行为监督的什么东西我也不会到，但是还是要多打一点字来吧这个东西撑起来，所以还是要不同的打字打字</li>
			</ul>
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
	export default{
		name:'liveStream',
		data(){
			return{
				arrList:10,
				arrList1:5
			}
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 90;
			var contH = $("#liveStream").height();
			if(contH > _h){
				$("#liveStream").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$("#liveStream").height(_h);
				}
			});
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
				height: 40px;
				border-bottom:1px solid $bottom_color;
				border-left:1px solid $bottom_color;
				border-right:1px solid $bottom_color;
				float: left;
				&:nth-child(1){
					font-size: $fs16;
					width: 8%;
					text-align: center;
					line-height: 40px;
					font-weight: 500;
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