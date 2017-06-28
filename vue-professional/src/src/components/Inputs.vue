<template>
	<div id="inputs">
		<label :for="typeclass">{{typeclassName}}</label>
		<input :type="types" :id="typeclass" :placeholder="holder" @blur='getVal'/>
	</div>
</template>

<script>
	export default{
		name:'inputs',
		data(){
			return {
				val:''
			}
		},
		props:['types','typeclass','holder'], //types:input的type；typeclass:字段名比如mobile或者password;
		computed:{
			typeclassName:function(){
				if(this.typeclass=='mobile'){
					return '手机：'
				}else if(this.typeclass=='password'){
					return '密码：'
				}
			}
		},
		methods:{
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
#inputs{
	width:90%;
	height: 40px;
	font-size: 13px;
	border-bottom: 1px solid #878787;
	margin: 0 auto;
	color: #fff;
}
#inputs input{
	width: 80%;
	font-size: 14px;
	border: none;
	outline: none;
	background: transparent;
}
</style>
