import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Menu from "../components/menu.vue"
import Role from "../components/role.vue"
import test from "../components/justForTest.vue"
<<<<<<< HEAD
import PermissionOperation from "../components/permissionOperation.vue"
import GetMail from '../components/getAllMail.vue'
import MailDetail from '../components/mailDetail.vue'
import SendMail from '../components/sendMail.vue'
import GetSendedMail from '../components/getAllSendMail.vue'
import SendedMailDetail from '../components/sendedMailDetail.vue'
Vue.use(VueRouter)

const routes = [{
		path: '/',
		name: 'Home',
		component: Home
	},
	{
		path: '/Menu',
		name: 'Menu',
		component: Menu,
		children: [{
			path: "/Role",
			name: "Role",
			component: Role
		},
		{
			path: '/PermissionOperation',
			name: 'PermissionOperation',
			component: PermissionOperation
		},
		{
			path: '/GetMail',
			name: 'GetMail',
			component: GetMail
		},
		{
			path: '/MailDetail',
			name: 'MailDetail',
			component: MailDetail
		},
		{
			path: '/SendMail',
			name: 'SendMail',
			component: SendMail
		},
		{
			path: '/GetSendedMail',
			name: 'GetSendedMail',
			component: GetSendedMail
		},
		{
			path: '/SendedMailDetail',
			name: 'SendedMailDetail',
			component: SendedMailDetail
		}
		]
	},
	{
		path: '/test',
		name: 'test',
		component: test
	},
	
	
	
	
	
=======
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

>>>>>>> cc75afe191b68da0f406f5af3b649d49b6aca5ee
]

const router = new VueRouter({
	routes
})

export default router
