import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
//控制显示与否的模块
var isshow = {
	state: {
		navBarShow: true,
		isconnected: false,
		bottomshow: false,
		pshow:false
	}
};

export default new Vuex.Store({
	modules:{
		isshow
	}
})