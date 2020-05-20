import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Menu from "../components/menu.vue"
import Role from "../components/role.vue"
import test from "../components/justForTest.vue"
import user from "../components/user.vue"
import adduser from "../components/adduser.vue"
import changeuser from "../components/changeuser.vue"
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
	children:[{
	        path:'Role',
	        component: Role,
			}
			]
  },
  {
    path: '/test',
    name: 'test',
  	component: test
  },
  {
	path:'/user',
	name:'user',
	component:user
  },
  {
  	path:'/user/adduser',
  	name:'adduser',
  	component:adduser
  },
  {
  	path:'/user/changeuser',
  	name:'changeuser',
  	component:changeuser
  },

]

const router = new VueRouter({
	routes
})

export default router
