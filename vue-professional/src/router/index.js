import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import home from '../pages/Home.vue'
import tradeapply from '../pages/TradeApply.vue'
import information from '../pages/Information.vue'
import test from '../pages/test.vue'
//import traderecord from '../pages/TradeRecord.vue'
import account from '../pages/Account1.vue'
import regist from '../pages/account/Regist.vue'
import conditions from '../pages/market/conditions.vue'
import historyTrade from '../pages/market/historyTrade.vue'
import moneyDetails from '../pages/market/moneyDetails.vue'
import stopMoney from '../pages/market/stopMoney.vue'
import orderdetail from '../pages/market/orderdetail.vue'
import space from '../pages/space.vue'
Vue.use(Router)

 
const router=new Router({
	mode: 'history',
	routes: [{
			path: '/',
			component: home
		},
		{
			path: '/index',
			component: home
		},
		{
			path: '/tradeapply',
			component: tradeapply
		},
		{
			path: '/information',
			component: information
		},
//		{
//			path: '/traderecord',
//			component: traderecord
//		},
		{
			path: '/account',
			component: account
		},
		{
			path: '/regist',
			component: regist
		},
		{
			path: '/test',
			component: test
		},
		{
			path: '/conditions',
			component: conditions
		},
		{
			path: '/historytrade',
			component: historyTrade
		},
		{
			path: '/moneydetails',
			component: moneyDetails
		},
		{
			path: '/stopmoney',
			component: stopMoney
		},
		{
			path:'/orderdetail',
			component: orderdetail
		},
		{
			path:'/space',
			component: space
		},
		{
			path: '*',
			component: home
		}
	]
})
router.afterEach(route => {
	var txt=route.path.slice(-5);
	if(!(txt==".html" || route.path=='/' || route.path=='/home' ||  route.path=='/index' ||route.path=='/tradeapply' || route.path=='/information' ||route.path=='/account')){
		store.state.isshow.navBarShow=false;
	}else{
		store.state.isshow.navBarShow=true;
	}
})


export default router