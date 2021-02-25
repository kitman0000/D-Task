import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import md5 from 'js-md5'
import VueCookies from 'vue-cookies'
import Vue2OrgTree from 'vue-tree-color'
import * as echarts from 'echarts';
Vue.prototype.$md5 = md5;
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueCookies)
Vue.use(Vue2OrgTree)
new Vue({
	router,
	store,
	render: h => h(App),
	components: {
		App
	}
}).$mount('#app')
