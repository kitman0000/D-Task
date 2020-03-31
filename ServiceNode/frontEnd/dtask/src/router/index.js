import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Menu from "../components/menu.vue"
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/Menu',
    name: 'Menu',
	component: Menu
  }
]

const router = new VueRouter({
  routes
})

export default router
