<template>
	<div id="regist">
		<inputs types='text' typeclass='mobile' holder='请输入您的账号'></inputs>
		<inputs types='password' typeclass='password' holder='请输入您的密码'></inputs>
		<btn btnName="下一步" colorname="gray" @click.native="getVal"></btn>
	</div>
</template>

<script>
	import inputs from '../../components/Inputs.vue'
	import btn from '../../components/Button.vue'
	export default{
		name:'regist',
		components:{
			inputs,btn
		},
		methods:{
			getVal:function(){
				console.log(this.$children[0].val);
				console.log(this.$children[1].val);
				//值已经取得，后面就是提交了
			}
		}
	}
	
	
</script>

<style scoped lang="less">
@import url("../../assets/css/main.less");
#regist{
	background: #202020;
	height: 100%;
}
</style>
