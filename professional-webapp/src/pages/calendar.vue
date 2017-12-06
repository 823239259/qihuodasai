<template>
	<div id="calendar">
		<div class="calendar_container">
			<div class="calendar">
				<div class="calendar_left">
					<span><</span>
					<span v-on:click="lastWeek">上周</span>
				</div>
				<div class="calendar_center">
					<!--<div class="calendar_center1">-->
						<ul>
							<!--<li>
								<span>03</span></br>
								<span>周日</span>
							</li>
							<li>
								<span>04</span></br>
								<span>周一</span>
							</li>
							<li>
								<span>05</span></br>
								<span>周二</span>
							</li>
							<li>
								<span class="span_white">06</span></br>
								<span class="span_color">周三</span>
							</li>
							<li>
								<span>07</span></br>
								<span>周四</span>
							</li>
							<li>
								<span>08</span></br>
								<span>周五</span>
							</li>
							<li>
								<span>09</span></br>
								<span>周六</span>
							</li>-->
							<li v-for="(k,index) in weekDayList" v-on:click="chooseDay" >
								<span :class="{span_white:current == index}">{{k.day | showToday}}</span></br>
								<span :class="{span_color:current == index}">{{k.weekday}}</span>
							</li>
						</ul>
					<!--</div>-->
					<!--<div class="calendar_center2">
						<ul>
							<li><span >{{lastdaythree.weekday}}</span></li>
							<li><span>{{lastdaytwo.weekday}}</span></li>
							<li><span>{{lastdayone.weekday}}</span></li>
							<li><span class="span_color">{{weekday}}</span></li>
							<li><span>{{predayone.weekday}}</span></li>
							<li><span>{{predaytwo.weekday}}</span></li>
							<li><span>{{predaythree.weekday}}</span></li>
						</ul>
					</div>-->
				</div>
				<div class="calendar_right">
					<span>></span>
					<span v-on:click="nextWeek">下周</span>
				</div>
			</div>
		</div>
		<div class="country">
			<p class="p_center">按国家分类</p>
			<div class="country_top">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>全部</span>
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>中国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>美国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>俄罗斯</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>英国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
				</ul>
			</div>
			<div class="country_btm">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>法国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>德国</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>加拿大</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>意大利</span>
						<img src="../assets/images/china.png" alt="" />
					</li>
				</ul>
			</div>
		</div>
		<div class="important">
			<p class="p_center">按重要性分类</p>
			<div class="important_center">
				<ul>
					<li>
						<i class="ifont">&#xe634;</i><span>全部</span>
					</li>
					<li>
						<i class="ifont">&#xe634;</i><span>重要</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="infoList">
			<div class="list_title">
				<ul>
					<li>日历图标</li>
					<li>{{show_weekDay}}</li>
					<li>{{show_day}}</li>
				</ul>
			</div>
			<div class="list">
				<ul class="list_container">
					<li>时间</li>
					<li>地区</li>
					<li>事件</li>
					<li>重要性</li>
					<li>今值</li>
					<li>预期</li>
					<li>前值</li>
				</ul>
				<ul class="list_details" v-for="k in list">
					<li>{{k.timestamp | getTime}}</li>
					<li><img :src="k.flagUrl" /></li>
					<li>{{k.title}}</li>
					<li>{{k.importance}}</li>
					<li>{{k.actual}}</li>
					<li>{{k.forecast}}</li>
					<li>{{k.previous}}</li>
				</ul>
			</div>
		</div>
		<div class="btm">
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from '../assets/js/common.js'
	export default{
		name:'calendar',
		data(){
			return{
				list:5,
				weekDayList:[],
				startTime:'',
				endTime:'',
				country:'',
				importance:'',
				current:3,
				show_weekDay:'',
				show_day:''
			}
		},
		methods:{
//			updateTime:function(){
//				this.sevenlist=[];
//				var time = this.startT;
//				this.timec = this.time.getTime();
//				this.timec = this.timec-(1 * 24 * 3600 * 1000);
//				var times=new Date(this.timec);
//				var year=times.getFullYear();
//				var month=times.getMonth()+1;
//				var day=times.getDate();
//				var today=year+'-'+month+'-'+day;
//				if((month.toString().length) < 2) {
//					month = '0' + month;
//				}
//				if(day.toString().length < 2) {
//					day = '0' + day
//				}
//				this.selectDate=today;
//				
//			},
//			selectday: function(e) {
//				var objstr = $(e.currentTarget).children('div').text();
//				var obj = JSON.parse(objstr);
//				var time = obj.y + '-' + obj.m + '-' + obj.day;
//				this.selectDate = time;
//				this.timec = this.time.getTime();
//			},
//			getdate: function() {
//				this.time = new Date();
//				this.timec = this.time.getTime();
//				this.day.y = this.time.getFullYear();
//				this.day.m = this.time.getMonth() + 1;
//				this.day.day = this.time.getDate();
//				this.day.D = this.time.getDay();
//				if((this.day.m.toString().length) < 2) {
//					this.day.m = '0' + this.day.m;
//				}
//				if(this.day.day.toString().length < 2) {
//					this.day.day = '0' + this.day.day
//				}
//				this.selectDate = this.day.y + '-' + this.day.m + '-' + this.day.day;
//			},
			getInfoList:function(startTime,endTime,country,importance){
				var data = {
					pageIndex:'1',
				    size:'20',
					startTime:startTime,
					endTime:endTime,
					country:country,
					importance:importance
				}
				pro.fetch('post','/crawler/getCrawlerCalendar',data,"").then((res)=>{
					console.log(res)
					if(res.success == true && res.code == ''){
						this.list = res.data.data.slice(0,5);
					}
				}).catch((err)=>{
					console.log(err);
				})
			},
			getDayList:function(e){
			    var timec = Date.parse(e)/1000;
			    console.log("666666666666666666");
			    console.log(timec);
			    var today = pro.getDate("y-m-d",timec*1000);
			    //1-3天前
			    var todayBefore3 = pro.getDate("y-m-d",(timec-3*24*60*60)*1000);
			    var todayBefore2 = pro.getDate("y-m-d",(timec-2*24*60*60)*1000);
			    var todayBefore1 = pro.getDate("y-m-d",(timec-24*60*60)*1000);
			    //1-3天后
			    var todayAfter3 = pro.getDate("y-m-d",(timec+3*24*60*60)*1000);
			    var todayAfter2 = pro.getDate("y-m-d",(timec+2*24*60*60)*1000);
			    var todayAfter1 = pro.getDate("y-m-d",(timec+24*60*60)*1000);
			    var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
			    var time = new Date(timec*1000);
			    var week = arr[time.getDay()];
			    var weekBefore3 = arr[new Date(timec*1000-3*24*60*60*1000).getDay()];
			    var weekBefore2 = arr[new Date(timec*1000-2*24*60*60*1000).getDay()];
			    var weekBefore1 = arr[new Date(timec*1000-24*60*60*1000).getDay()];
			    var weekAfter3 = arr[new Date(timec*1000+3*24*60*60*1000).getDay()];
			    var weekAfter2 = arr[new Date(timec*1000+2*24*60*60*1000).getDay()];
			    var weekAfter1 = arr[new Date(timec*1000+24*60*60*1000).getDay()];
			    var todayObj1 = {};todayObj1.day = todayBefore3;todayObj1.weekday = weekBefore3;
			    var todayObj2 = {};todayObj2.day = todayBefore2;todayObj2.weekday = weekBefore2;
			   	var todayObj3 = {};todayObj3.day = todayBefore1;todayObj3.weekday = weekBefore1;
			   	var todayObj4 = {};todayObj4.day = today;todayObj4.weekday = week;
			   	var todayObj5 = {};todayObj5.day = todayAfter1;todayObj5.weekday = weekAfter1;
			   	var todayObj6 = {};todayObj6.day = todayAfter2;todayObj6.weekday = weekAfter2;
			   	var todayObj7 = {};todayObj7.day = todayAfter3;todayObj7.weekday = weekAfter3;
			   	var aaa = []
			   	for(var i = 0;i<7;i++){
			   		aaa[i]=eval("todayObj"+(i+1));
			   	}
			   	this.show_weekDay = week;
			   	this.weekDayList = aaa;
			},
			chooseDay:function(e){
				var index = $(e.currentTarget).index();
				this.current = index;
				this.startTime = this.weekDayList[index].day;
				this.endTime = pro.getDate("y-m-d",(Date.parse(this.startTime)/1000+24*60*60)*1000);
				this.getInfoList(this.startTime,this.endTime,'','');
				this.show_day = pro.getDate("yy-mm-dd",Date.parse(this.weekDayList[index].day))
				this.show_weekDay = this.weekDayList[index].weekday;
			},
			lastWeek:function(){
				console.log("000000000000000000");
				var lastWeekDay =  pro.getDate("y-m-d",(Date.parse(this.startTime)/1000-7*24*60*60)*1000);
				var lastWeekDay1 = pro.getDate("y-m-d",(Date.parse(lastWeekDay)/1000+24*60*60)*1000);
				console.log(lastWeekDay);
				console.log(lastWeekDay1);
				this.getDayList(lastWeekDay);
				this.getInfoList(lastWeekDay,lastWeekDay1,'','')
			},
			nextWeek:function(){
				console.log("1111111111111111111");
				var nextWeekDay = pro.getDate("y-m-d",(Date.parse(this.startTime)/1000+7*24*60*60)*1000);
				var nextWeekDay1 = pro.getDate("y-m-d",(Date.parse(nextWeekDay)/1000+24*60*60)*1000);
				console.log(nextWeekDay);
				this.getDayList(nextWeekDay);
				this.getInfoList(nextWeekDay,nextWeekDay1,'','')
			}
		},
		mounted:function(){
			this.startTime = pro.getDate("y-m-d",Date.parse(new Date()));
			this.endTime = pro.getDate("y-m-d",(Date.parse(new Date())/1000+24*60*60)*1000);
			this.getInfoList(this.startTime,this.endTime,'','');
			//获取当天的时间和星期
			this.show_day = pro.getDate("yy-mm-dd",Date.parse(new Date()));
			console.log(this.startTime);
			this.getDayList(this.startTime);
		},
		filters:{
			getTime:function(e){
				var a = pro.getDate("h:m",e*1000);
				return a;
			},
			showToday:function(e){
				var a = Date.parse(e)/1000;
				a = pro.getDate("d",a*1000);
				return a;
			}
		},
		computed:{
//			weekday: function() {
//				var arr = [];
//				arr[0] = "周日";
//				arr[1] = "周一";
//				arr[2] = "周二";
//				arr[3] = "周三";
//				arr[4] = "周四";
//				arr[5] = "周五";
//				arr[6] = "周六";
//				return arr[this.day.D]
//			},
//			lastdayone: function() {
//				var obj = {};
//				var date = new Date(this.timec - 1 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			},
//			lastdaytwo: function() {
//				var obj = {};
//				var date = new Date(this.timec - 2 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			},
//			lastdaythree: function() {
//				var obj = {};
//				var date = new Date(this.timec - 3 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			},
//			predayone: function() {
//				var obj = {};
//				var date = new Date(this.timec + 1 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			},
//			predaytwo: function() {
//				var obj = {};
//				var date = new Date(this.timec + 2 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			},
//			predaythree: function() {
//				var obj = {};
//				var date = new Date(this.timec + 3 * 24 * 3600 * 1000);
//				obj.y = date.getFullYear();
//				obj.m = date.getMonth() + 1;
//				obj.day = date.getDate();
//				obj.D = date.getDay();
//				var arr = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
//				obj.weekday = arr[obj.D];
//				if((obj.m.toString().length) < 2) {
//					obj.m = '0' + obj.m;
//				}
//				if(obj.day.toString().length < 2) {
//					obj.day = '0' + obj.day
//				}
//				return obj
//			}
		},
		watch:{
//			selectDate: function(n, o) {
//				var arr = n.split('-');
//				this.day.y = Number(arr[0]);
//				this.day.m = Number(arr[1]);
//				this.day.day = Number(arr[2]);
//				this.time = new Date(this.day.y, this.day.m - 1, this.day.day);
//				if((this.day.m.toString().length) < 2) {
//					this.day.m = '0' + this.day.m;
//				}
//				if(this.day.day.toString().length < 2) {
//					this.day.day = '0' + this.day.day
//				}
//				this.day.D = this.time.getDay();
//				this.timec = this.time.getTime();
//			}
		}
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#calendar{
		width: 1000px;
		margin: auto;
		/*overflow-y: auto;*/
	}
	.calendar_container{
		background-color: $blue;
		height: 100px;
		width: 100%;
		.calendar{
			height: 100px;
			width: 680px;
			margin: auto;
			.calendar_left{
				line-height: 100px;
				width: 90px;
				height: 100px;
				float: left;
				cursor: pointer;
				span{
					&:nth-child(1){
						font-weight: 800;
					}
				}
			}
			.calendar_center{
				width: 500px;
				height: 100px;
				float: left;
				li{
					height: 100px;
					text-align: center;
					width: 14.2%;
					float: left;
					display: block;
					span{
						cursor: pointer;
						&:nth-child(1){
							height: 60PX;
							font-weight: 600;
							font-size: $fs24;
							line-height: 60px;
							
						}
						&:nth-child(2){
							line-height:40px; 
							height: 40px;
							font-size: $fs14;
							display:block;
						}
					}
					.span_white{
						border-radius:50%;
						padding:5px;
						background-color: #ccd5ff;
						color: $blue;
					}
					.span_color{
						color: #ccd5ff;
					}
				}
			}
			.calendar_right{
				cursor: pointer;
				line-height: 100px;
				text-align: right;
				width: 90px;
				height: 100px;
				float: right;
				span{
					&:nth-child(1){
						font-weight: 800;
					}
				}
			}
		}
	}
	.country{
		width: 100%;
		height: 160px;
		background-color: $blue;
		margin-top: 5px;
		.ifont{
			font-size: $fs12;
			color: $lightblue;
			padding-right: 10px;
			cursor: pointer;
		}
		.country_top{
			padding-left: 10px;
			height: 60px;
			width: 100%;
			line-height: 60px;
			li{
				width: 160px;
				float: left;
				img{
					position: relative;
					top: 5px;
				}
			}
		}
		.country_btm{
			padding-left: 10px;
			width: 100%;
			height: 60px;
			line-height: 50px;
			li{
				width: 160px;
				float: left;
				&:nth-child(1){
					margin-left: 160px;
				}
				img{
					position: relative;
					top: 5px;
				}
			}
		}
	}
	.important{
		border-bottom: 1px solid $bottom_color;
		width: 100%;
		height: 100px;
		background-color: $blue;
		.ifont{
			font-size: $fs12;
			color: $lightblue;
			padding-right: 10px;
			cursor: pointer;
		}
		ul{
			width: 100%;
			height: 60px;
			line-height: 55px;
			li{
				&:nth-child(1){
					margin-left: 170px;
				}
				width: 160px;
				float: left;
			}
		}
		
	}
	.infoList{
		width: 100%;
		background-color:$blue;
		.list_title{
			padding-left: 10px;
			height: 40px;
			line-height: 40px;
			li{
				float: left;
				font-size: $fs12;
				&:nth-child(2){
					color: $white;
					padding: 0 10px;
				}
			}
		}
		.list_container{
			font-size: $fs12;
			text-indent: 10px;
			height: 30px;
			line-height: 30px;
			background-color:$bottom_color ;
			li{
				float: left;
				&:nth-child(1){
					width: 10%;
				}
				&:nth-child(2){
					width: 10%;
				}
				&:nth-child(3){
					width: 45%;
				}
				&:nth-child(4){
					width: 10%;
				}
				&:nth-child(5){
					width: 10%;
				}
				&:nth-child(6){
					width: 10%;
				}
				&:nth-child(7){
					width: 5%;
				}
			}
		}
		.list_details{
			font-size: $fs14;
			line-height: 40px;
			text-indent: 10px;
			height: 40px;
			border-bottom: 1px solid $bottom_color;
			li{
				float: left;
				&:nth-child(1){
					width: 10%;
				}
				&:nth-child(2){
					width: 10%;
				}
				&:nth-child(3){
					width: 45%;
				}
				&:nth-child(4){
					width: 10%;
				}
				&:nth-child(5){
					width: 10%;
				}
				&:nth-child(6){
					width: 10%;
				}
				&:nth-child(7){
					width: 5%;
				}
				img{
					width: 30px;
					height: 20px;
					position: relative;
					top: 5px;
				}
			}
		}
	}
	.btm{
		margin-top: 5px;
		height: 40px;
		line-height: 40px;
		text-align: center;
		background-color: $blue;
		p{
			font-size: $fs12;
		}
	}
	.p_center{
		height: 40px;
		font-size: $fs16;
		line-height: 40px;
		background-color: $bottom_color;
		padding-left: 10px;
		color: $white;
	}
	
</style>