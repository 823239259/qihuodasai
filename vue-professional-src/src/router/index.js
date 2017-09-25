import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import guide from '../pages/Guide.vue'
import home from '../pages/Home.vue'
import tradeapply from '../pages/TradeApply.vue'
import information from '../pages/Information.vue'
import test from '../pages/test.vue'
import account from '../pages/Account.vue'
import conditions from '../pages/market/conditions.vue'
import historyTrade from '../pages/market/historyTrade.vue'
import moneyDetails from '../pages/market/moneyDetails.vue'
import stopMoney from '../pages/market/stopMoney.vue'
import orderdetail from '../pages/market/orderdetail.vue'
import space from '../pages/space.vue'
import findPwd from '../pages/account/findPwd.vue'
import login from '../pages/account/login.vue'
import register from '../pages/account/register.vue'
import ac from '../pages/tradeapply/applycomplate.vue'
import ad from '../pages/tradeapply/applydetail.vue'
import pc from '../pages/tradeapply/payConfirm.vue'
import agreement from '../pages/tradeapply/agreement.vue'
import tradersRules from '../pages/tradeapply/tradersRules.vue'
import sevensearch from '../pages/information/sevensearch.vue'
import moneyLog from '../pages/account/moneyLog.vue'
import nameCertification from '../pages/account/nameCertification.vue'
import editPhone from '../pages/account/editPhone.vue'
import editPwd from '../pages/account/editPwd.vue'
import recharge from '../pages/account/recharge.vue'
import withdrawal from '../pages/account/withdrawal.vue'
import tradeDetails from '../pages/account/tradeDetails.vue'
import bindBankCard from '../pages/account/bindBankCard.vue'
import payWays from '../pages/account/payWays.vue'
import moneyPwd from '../pages/account/moneyPwd.vue'
import addMoney from '../pages/account/addMoney.vue'
import service from '../pages/service.vue'
import tradeLogin from '../pages/market/tradeLogin.vue'
import help from '../pages/help.vue'
//各种合约交易规则
import cl from '../pages/role/cl.vue'
import cn from '../pages/role/cn.vue'
import es from '../pages/role/es.vue'
import fdax from '../pages/role/fdax.vue'
import fdxm from '../pages/role/fdxm.vue'
import gc from '../pages/role/gc.vue'
import hg from '../pages/role/hg.vue'
import hhi from '../pages/role/hhi.vue'
import hsi from '../pages/role/hsi.vue'
import mch from '../pages/role/mch.vue'
import mhi from '../pages/role/mhi.vue'
import ng from '../pages/role/ng.vue'
import nk from '../pages/role/nk.vue'
import nq from '../pages/role/nq.vue'
import qm from '../pages/role/qm.vue'
import si from '../pages/role/si.vue'
import ym from '../pages/role/ym.vue'
Vue.use(Router)

const router = new Router({
//	mode: 'history',
	routes: [
		{
			path: '/',
			component: home
		},
		{
			path: '/cl',
			component: cl
		},
		{
			path: '/cn',
			component: cn
		},
		{
			path: '/es',
			component: es
		},
		{
			path: '/fdax',
			component: fdax
		},
		{
			path: '/fdxm',
			component: fdxm
		},
		{
			path: '/gc',
			component: gc
		},
		{
			path: '/hg',
			component: hg
		},
		{
			path: '/hhi',
			component: hhi
		},
		{
			path: '/hsi',
			component: hsi
		},
		{
			path: '/mch',
			component: mch
		},
		{
			path: '/mhi',
			component: mhi
		},
		{
			path: '/ng',
			component: ng
		},
		{
			path: '/nk',
			component: nk
		},
		{
			path: '/nq',
			component: nq
		},
		{
			path: '/qm',
			component: qm
		},
		{
			path: '/si',
			component: si
		},
		{
			path: '/ym',
			component: ym
		},
		{
			path: '/help',
			component: help
		},
		{
			path: '/tradeLogin',
			component: tradeLogin
		},
		{
			path: '/tradersRules',
			component: tradersRules
		},
		{
			path: '/addMoney',
			component: addMoney
		},
		{
			path: '/agreement',
			component: agreement
		},
		{
			path: '/moneyPwd',
			component: moneyPwd
		},
		{
			path: '/payWays',
			component: payWays
		},
		{
			path: '/bindBankCard',
			component: bindBankCard
		},
		{
			path: '/tradeDetails',
			component: tradeDetails
		},
		{
			path: '/withdrawal',
			component: withdrawal
		},
		{
			path: '/recharge',
			component: recharge
		},
		{
			path: '/index',
			component: home
		},
		{
			path: '/findPwd',
			component: findPwd
		},
		{
			path: '/register',
			component: register
		},
		{
			path: '/tradeapply',
			component: tradeapply
		},
		{
			path: '/information',
			component: information,
			meta: { keepAlive: true }
		},
		{
			path: '/account',
			component: account,
//			beforeEnter: (to, from, next) => {
//				if(!store.state.account.islogin) {
//					next({
//						path: '/login'
//					});
//				} else {
//					next();
//				}
//			}
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
			path: '/orderdetail',
			component: orderdetail
		},
		{
			path: '/space',
			component: space
		},
		{
			path: '/login',
			component: login
		},
		{
			path: '/applycomplate',
			component: ac
		},
		{
			path: '/applydetail',
			component: ad
		},
		{
			path: '/payconfirm',
			component: pc
		},
		{
			path:'/sevensearch',
			component:sevensearch
		},
		{
			path:'/moneyLog',
			component:moneyLog
		},
		{
			path:'/nameCertification',
			component:nameCertification
		},
		{
			path:'/editPhone',
			component:editPhone
		},
		{
			path:'/editPwd',
			component:editPwd
		},
		{
			path:'/service',
			component:service
		},
		{
			path: '*',
			component: home
		}
	]
})
router.afterEach(route => {
	var txt = route.path.slice(-5);
	if(!(txt == ".html" || route.path == '/home' || route.path == '/' ||route.path == '/index' || route.path == '/tradeapply' || route.path == '/information' || route.path == '/account')) {
		store.state.isshow.navBarShow = false;
	} else {
		store.state.isshow.navBarShow = true;
	}
})

export default router