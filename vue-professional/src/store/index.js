import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

var state = {
	navBarShow: true,
	isconnected:false
}


export default new Vuex.Store({
	state,
})