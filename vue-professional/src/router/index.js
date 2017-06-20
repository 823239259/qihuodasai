import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import home from '../pages/Home.vue'
import tradeapply from '../pages/TradeApply.vue'
import information from '../pages/Information.vue'
//import traderecord from '../pages/TradeRecord.vue'
import account from '../pages/Account1.vue'
import regist from '../pages/account/Regist.vue'

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
			path: '*',
			component: home
		}
	]
})
router.afterEach(route => {
	var txt=route.path.slice(-5);
	if(!(txt==".html" || route.path=='/' || route.path=='/index' || route.path=='/tradeapply' || route.path=='/information' ||route.path=='/account')){
		store.state.navBarShow=false;
	}else{
		store.state.navBarShow=true;
	}
})


export default router