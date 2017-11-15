<template>
	<div id="account">
		<div id="router_path">
			<template v-for="(v, index) in tabList">
				<span :class="{current: currentNum == index}" @click="show_accountSurvey(index)">{{v}}</span>
			</template>
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
				tabList: ['账户概括','开户明细','安全设置'],
				currentNum: 0,
			}
		},
		methods : {
			show_accountSurvey : function(index){
				console.log(index);
				this.currentNum = index;
				if(index == 0){
					this.$router.push({path:'/account_survey'});
				}else if(index == 1){
					this.$router.push({path:'/account_openDetail'});
				}else if(index == 2){
					this.$router.push({path:'/account_safe'});
				}
			},
		},
		activated: function(){
			this.currentNum = 0;
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
			display: inline-block;
			width: 120px;
			height: 32px;
			line-height: 32px;
			text-align: center;
			background-color : $blue;
			margin-right: 5px;
			cursor: pointer;
			border-bottom: 5px solid $black;
			&:hover, &.current {
				color: $yellow;
				border-color: $blue;
			}
		}
	}
	#container{
		width: 100%;
		float: left;
		margin-left: 120px;
	}
</style>