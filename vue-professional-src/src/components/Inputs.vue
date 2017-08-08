<template>
	<div id="inputs">
		<label :for="typeclass">{{typeclassName}}</label>
		<input :type="types" :id="typeclass" :placeholder="holder" @blur='getVal'/>
		<i :class="eye" v-show="eyeShow"></i>
		<div :class="code" v-show="codeShow" @tap="getCode">获取验证码</div>
	</div>
</template>

<script>
	export default{
		name:'inputs',
		data(){
			return {
				val:'',
				eyeShow: false,
				codeShow: false
			}
		},
		props:['types','typeclass','holder','eyes', 'codes'], //types:input的type；typeclass:字段名比如mobile或者password;
		computed:{
			typeclassName:function(){
				if(this.typeclass=='mobile'){
					return '手机号';
				}else if(this.typeclass=='password'){
					return '密码';
				}else if(this.typeclass=='code'){
					return '验证码';
				}else if(this.typeclass=='setpwd'){
					return '新密码';
				}else if(this.typeclass=='confirmpwd'){
					return '确认密码';
				}
				
			},
			eye: function(){
				if(this.eyes == 'true') this.eyeShow = true;
				return 'eye';
			},
			code: function(){
				if(this.codes == 'true') this.codeShow = true;
				return 'code';
			}
			
		},
		methods:{
			getCode: function(e){
				var time = 60;
				$(e.target).css("background","#2d3040");
				var timing = setInterval(function(){
					time--;
					if(time < 0){
						$(e.target).text('获取验证码');
						clearInterval(timing);
					}else{
						$(e.target).text(time + '秒');
					}
				}, 1000);
			},
			getVal:function(){
				//获取input的值
				this.val=$('#'+this.typeclass).val();
//				console.log(this.val);
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
	#inputs{
		width: 92.75%;
		height: 54px;
		margin: 0 3.625% 15px 3.625%;
		position: relative;
		label{
			display: inline-block;
			width: 90px;
			height: 54px;
			line-height: 54px;
			color: @blue;
			font-size: @fs16;
			padding-left: 25px;
			position: absolute;
			top: 0;
			left: 0;
			z-index: 5;
		}
		input{
			position: absolute;
			top: 0;
			left: 0;
			z-index: 2;
			display: inline-block;
			width: 100%;
			height: 54px;
			border: 1px solid #12121a;
			border-radius: 5px;
			padding: 10px 90px 10px 100px;
			color: @white;
			font-size: @fs16;
			background: @black;
			&:focus{
				border-color: @yellow;
			}
		}
		.code{
			position: absolute;
			top: 1px;
			right: 1px;
			z-index: 5;
			width: 85px;
			height: 52px;
			line-height: 52px;
			text-align: center;
			background: @deepblue;
			border-bottom-right-radius: 5px;
			border-top-right-radius: 5px;
			color: @blue;
			font-size: @fs14;
			border-left: 1px solid @black;
		}
		.eye{
			position: absolute;
			top: 0;
			right: 0;
			z-index: 5;
			display: inline-block;
			width: 54px;
			height: 54px;
			overflow: hidden;
			background: url(../assets/img/eye.png) no-repeat 16px 21px;
			background-size: 22px 12px;
		}
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #inputs{
		width: 92.75%;
		height: 54px*@ip6;
		margin: 0 3.625% 15px*@ip6 3.625%;
		position: relative;
		label{
			display: inline-block;
			width: 90px*@ip6;
			height: 54px*@ip6;
			line-height: 54px*@ip6;
			color: @blue;
			font-size: @fs16*@ip6;
			padding-left: 25px*@ip6;
			position: absolute;
			top: 0;
			left: 0;
			z-index: 5;
		}
		input{
			position: absolute;
			top: 0;
			left: 0;
			z-index: 2;
			display: inline-block;
			width: 100%;
			height: 54px*@ip6;
			border: 1px solid #12121a;
			border-radius: 5px*@ip6;
			padding: 10px*@ip6 90px*@ip6 10px*@ip6 100px*@ip6;
			color: @white;
			font-size: @fs16*@ip6;
			background: @black;
			&:focus{
				border-color: @yellow;
			}
		}
		.code{
			position: absolute;
			top: 1px;
			right: 1px;
			z-index: 5;
			width: 85px*@ip6;
			height: 52px*@ip6;
			line-height: 52px*@ip6;
			text-align: center;
			background: @deepblue;
			border-bottom-right-radius: 5px*@ip6;
			border-top-right-radius: 5px*@ip6;
			color: @blue;
			font-size: @fs14*@ip6;
			border-left: 1px solid @black;
		}
		.eye{
			position: absolute;
			top: 0;
			right: 0;
			z-index: 5;
			display: inline-block;
			width: 54px*@ip6;
			height: 54px*@ip6;
			overflow: hidden;
			background: url(../assets/img/eye.png) no-repeat 16px*@ip6 21px*@ip6;
			background-size: 22px*@ip6 12px*@ip6;
		}
	}
}
/*ip5*/
@media(max-width:370px) {
	#inputs{
		width: 92.75%;
		height: 54px*@ip5;
		margin: 0 3.625% 15px*@ip5 3.625%;
		position: relative;
		label{
			display: inline-block;
			width: 90px*@ip5;
			height: 54px*@ip5;
			line-height: 54px*@ip5;
			color: @blue;
			font-size: @fs16*@ip5;
			padding-left: 25px*@ip5;
			position: absolute;
			top: 0;
			left: 0;
			z-index: 5;
		}
		input{
			position: absolute;
			top: 0;
			left: 0;
			z-index: 2;
			display: inline-block;
			width: 100%;
			height: 54px*@ip5;
			border: 1px solid #12121a;
			border-radius: 5px*@ip5;
			padding: 10px*@ip5 90px*@ip5 10px*@ip5 100px*@ip5;
			color: @white;
			font-size: @fs16*@ip5;
			background: @black;
			&:focus{
				border-color: @yellow;
			}
		}
		.code{
			position: absolute;
			top: 1px;
			right: 1px;
			z-index: 5;
			width: 85px*@ip5;
			height: 52px*@ip5;
			line-height: 52px*@ip5;
			text-align: center;
			background: @deepblue;
			border-bottom-right-radius: 5px*@ip5;
			border-top-right-radius: 5px*@ip5;
			color: @blue;
			font-size: @fs14*@ip5;
			border-left: 1px solid @black;
		}
		.eye{
			position: absolute;
			top: 0;
			right: 0;
			z-index: 5;
			display: inline-block;
			width: 54px*@ip5;
			height: 54px*@ip5;
			overflow: hidden;
			background: url(../assets/img/eye.png) no-repeat 16px*@ip5 21px*@ip5;
			background-size: 22px*@ip5 12px*@ip5;
		}
	}
}
</style>
