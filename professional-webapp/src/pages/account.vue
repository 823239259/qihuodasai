<template>
	<div id="account">
		<div id="router_path">
			<span v-on:click="show_accountSurvey">账户概括</span>
			<span v-on:click="show_accountOpenDetail">开户明细</span>
			<span v-on:click="show_accountSafe">安全设置</span>
		</div>
		<div id="container">
			<router-view ></router-view>
		</div>			
	</div>
</template>

<script>
	import { mapMutations,mapActions } from 'vuex'
   	export default{
		name:'account',
		data(){
			return {
				
			}
		},
		methods : {
			show_accountSurvey : function(){
				this.$router.push({path:'/account_survey'});
			},
			show_accountSafe : function(){
				this.$router.push({path:'/account_safe'})
			},
			show_accountOpenDetail : function(){
				this.$router.push({path:'/account_openDetail'})
			}
		}
		
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../assets/css/common.scss";
	
	#account {
		width: 80%;
	}
	#router_path {
		left: 120px;
		position : relative;
		width : 80%;
		span {
			&:hover {
				color: yellow;
			}
			background-color : $blue;
			width: 120px;
			height: 30px;
			line-height : 30px;
			padding : 5px 20px 5px 20px;
		}
	}
	#container{
		width: 100%;
		float: left;
		margin-left: 120px;
	}
</style>