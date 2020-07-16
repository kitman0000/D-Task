import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Menu from "../components/menu.vue"
import Role from "../components/role.vue"
import test from "../components/justForTest.vue"
import PermissionOperation from "../components/permissionOperation.vue"
import GetMail from '../components/getAllMail.vue'
import MailDetail from '../components/mailDetail.vue'
import SendMail from '../components/sendMail.vue'
import GetSendedMail from '../components/getAllSendMail.vue'
import SendedMailDetail from '../components/sendedMailDetail.vue'
import user from "../components/user.vue"
import adduser from "../components/adduser.vue"
import checkuser from "../components/checkuser.vue"
import changeuser from "../components/changeuser.vue"
import DepartmentManagement from '../components/departmentManagement.vue'
import Node from '../components/node.vue'
import nodeOperation from '../components/nodeOperation.vue'
import websiteSetting from "../components/websiteSetting.vue"
import userSetting from "../components/userSetting.vue"
import SubTask from '../components/subTask.vue'
import EditTask from '../components/editTask.vue'
import editParticipator from '../components/editParticipator.vue'
import localTask from "../components/localTask.vue"
import userTask from "../components/userTask.vue"
import addUserTask from "../components/addUserTask.vue"
import changeUserTask from "../components/changeUserTask.vue"

Vue.use(VueRouter)

const routes = [{
		path: '/',
		name: 'Home',
		component: Home
	},
	{
			path: '/test',
			name: 'test',
			component: test
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
			path: '/DepartmentManagement',
			name: 'DepartmentManagement',
			component: DepartmentManagement
		},
		{
			path: '/MailDetail',
			name: 'MailDetail',
			component: MailDetail
		},
		{
			path: '/EditTask',
			name: 'EditTask',
			component: EditTask
		},
		{
			path: '/SubTask',
			name: 'SubTask',
			component: SubTask
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
		},
		{
			path: '/Node',
			name: 'Node',
			component: Node
		},
		{
			path: '/nodeOperation',
			name:'nodeOperation',
			component: nodeOperation
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
			path:'/user/checkuser',
			name:'checkuser',
			component:checkuser
		},
		{
			path:'/user/changeuser',
			name:'changeuser',
			component:changeuser
		},
		{
			path:'/websiteSetting',
			name:'websiteSetting',
			component:websiteSetting
		},
		{
			path:'/userSetting',
			name:'userSetting',
			component:userSetting
		},
		{
			path:'/editParticipator',
			name:'editParticipator',
			component:editParticipator,
		},
		{
			path:'/localTask',
			name:'localTask',
			component:localTask
		},
		{
			path:'/userTask',
			name:'userTask',
			component:userTask
		},
		{
			path:'/addUserTask',
			name:'addUserTask',
			component:addUserTask
		},
		{
			path:'/changeUserTask',
			name:'changeUserTask',
			component:changeUserTask
		},
		]
	}
	
]

const router = new VueRouter({
	routes
})

export default router
