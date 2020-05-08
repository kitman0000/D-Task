import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Menu from "../components/menu.vue"
import Role from "../components/role.vue"
import test from "../components/justForTest.vue"
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
	component: Menu,
  },
  {
    path: '/Role',
    name: 'Role',
	component: Role,
  },
  {
    path: '/test',
    name: 'test',
  	component: test
  },
]

const router = new VueRouter({
  routes
})

export default router
