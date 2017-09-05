<template>
	<div id="codeDialog">
		<div class="bg"></div>
		<div class="page_cont">
			<i></i>
			<h3 class="title">标题</h3>
			<div class="code_box">
				<input type="number" class="fl" />
				<a href="#" class="fr"><img src="../../dist/static/img/banner01.png"/></a>
			</div>
			<div class="btn_box">
				<a href="#" class="fl">确定</a>
				<a href="#" class="fl">取消</a>
			</div>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'codeDialog',
		methods: {
			close: function(){
				
			},
			confirm: function(){
				
			}
		}
	}
</script>

<style lang="less" scoped>
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    .bg{
    	position: absolute;
    	top: 0;
    	left: 0;
    	right: 0;
    	bottom: 0;
    	z-index: 1010;
    	background: #000;
    	opacity: 0.5;
    }
    .page_cont{
    	width: 330px;
    	height: 200px;
    	overflow: hidden;
    	border-radius: 5px;
    	box-shadow: 5px 5px 10px #000;
    	background: @black;
    	position: absolute;
    	top: 50%;
    	left: 50%;
    	z-index: 1011;
    	margin: -100px 0 0 -165px;
    	i{
			position: absolute;
			right: 0;
			top: 0;
			z-index: 1011;
			width: 32px;
			height: 32px;
			background: url('../assets/img/x.png') no-repeat center center;
			background-size: 100% 100%;
		}
    	.title{
    		height: 44px;
    		line-height: 44px;
    		text-align: center;
    		font-size: @fs18;
    		color: @white;
    		background: @deepblue;
    		border-bottom: 1px solid @black;
    	}
    	.code_box{
    		height: 100px;
    		background: @deepblue;
    		padding: 23px 15px;
    		input, a{
    			width: 143px;
    			height: 54px;
    			overflow: hidden;
    			border: 1px solid #171721;
    			border-radius: 5px;
    			background: @black;
    			img{
    				width: 143px;
    				height: 54px;
    			}
    		}
    	}
    	.btn_box{
    		height: 45px;
    		line-height: 45px;
    		margin-top: 10px;
    		background: @deepblue;
    		a{
    			width: 50%;
    			text-align: center;
    			font-size: @fs16;
    		}
    	}
    }
    
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    
}
/*ip5*/
@media(max-width:370px) {

}
</style>