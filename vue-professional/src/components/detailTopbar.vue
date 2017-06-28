<template>
	<div id="detailTopbar">
		<back></back>
		<div>
			<h4 class="fontwhite">{{cname}}</h4>
			<h6 class="fontwhite"><span>{{cnum}}</span><span>{{mc}}</span></h6>
		</div>
		<refresh></refresh>
		<menus></menus>
	</div>
</template>

<script>
	import back from '../components/back.vue'
	import refresh from '../components/Refresh.vue'
	import menus from '../components/menu.vue'
	export default{
		name:'detailTopbar',
		components:{
			back,refresh,menus
		},
		props:['cname','cnum','mc']
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@height:50px;
#detailTopbar{
	width: 100%;
	height: @height;
	background-color: #242633;
	position: fixed;
	top: 0;
	z-index: 1100;
}
#detailTopbar>div:nth-child(2){
	width: 100%;
	height: @height;
	margin-top: 8px;	
}
#detailTopbar>div:nth-child(2)>h4{
	font-weight: normal;
	font-size: 16px;
	text-align: center;
}
#detailTopbar>div:nth-child(2)>h6{
	font-weight: 200;
	font-size: 13px;
	text-align: center;
}
#back{
	position: absolute;
	left: 0;
	top: 0;
}
#refresh{
	position: absolute;
	top: 0;
	right: 50px;
}
#menus{
	position: absolute;
	top: 0;
	right: 0;
	z-index: 1100;
}
#menus ol{
	z-index: 1101;
}
#menus ol li{
	z-index: 1102;
}
h6{
	margin-top: 6px;
}
/*ip5*/
@media(max-width:370px) {
    #detailTopbar{
	width: 100%;
	height: @height*@ip5;
	background-color: #242633;
	position: fixed;
	top: 0;
}
#detailTopbar>div:nth-child(2){
	width: 100%;
	height: @height*@ip5;
	margin-top: 8px*@ip5;	
}
#detailTopbar>div:nth-child(2)>h4{
	font-weight: normal;
	font-size: 16px*@ip5;
	text-align: center;
}
#detailTopbar>div:nth-child(2)>h6{
	font-weight: 200;
	font-size: 13px*@ip6;
	text-align: center;
	transform: scale(0.9);
}
#back{
	position: absolute;
	left: 0;
	top: 0;
}
#refresh{
	position: absolute;
	top: 0;
	right: 50px*@ip5;
}
#menus{
	position: absolute;
	top: 0;
	right: 0;
}
h6{
	margin-top: 6px*@ip5;
}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
    #detailTopbar{
	width: 100%;
	height: @height*@ip6;
	background-color: #242633;
	position: fixed;
	top: 0;
}
#detailTopbar>div:nth-child(2){
	width: 100%;
	height: @height*@ip6;
	margin-top: 8px*@ip6;	
}
#detailTopbar>div:nth-child(2)>h4{
	font-weight: normal;
	font-size: 16px*@ip6;
	text-align: center;
}
#detailTopbar>div:nth-child(2)>h6{
	font-weight: 200;
	font-size: 13px*@ip6;
	text-align: center;
}
#back{
	position: absolute;
	left: 0;
	top: 0;
}
#refresh{
	position: absolute;
	top: 0;
	right: 50px*@ip6;
}
#menus{
	position: absolute;
	top: 0;
	right: 0;
}
h6{
	margin-top: 6px*@ip6;
}
}
</style>
