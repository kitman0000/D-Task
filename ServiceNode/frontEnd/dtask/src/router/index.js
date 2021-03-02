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
import localSubTask from '../components/localSubTask.vue'
import EditTask from '../components/editTask.vue'
import editParticipator from '../components/editParticipator.vue'
import localTask from "../components/localTask.vue"
import userLocalTask from "../components/userLocalTask.vue"
import addLocalTask from "../components/addLocalTask.vue"
import changeLocalTask from "../components/changeLocalTask.vue"
import editJoinParticipator from '../components/editJointParticiptor.vue'
import editJointSubTask from '../components/editJointSubTask.vue'
import jointSubTask from '../components/jointSubTask.vue'
import userJointTask from '../components/userJointTask.vue'
import jointTask from '../components/jointTask.vue'
import addJointTask from '../components/addJointTask.vue'
import changeJointTask from '../components/changeJointTask.vue'
import userManual from '../components/userManual.vue'
import pluginPage from '../components/pluginPage.vue'

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
		path:'/userManual',
		name:'userManual',
		component:userManual
	},
	{
		path: '/Menu',
		name: 'Menu',
		component: Menu,
		children: [{
			path: "/Role",
			name: "Role",
			component: Role,
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
			path: '/localSubTask',
			name: 'localSubTask',
			component: localSubTask
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
			path:'/adduser',
			name:'adduser',
			component:adduser
		},
		{
			path:'/checkuser',
			name:'checkuser',
			component:checkuser
		},
		{
			path:'/changeuser',
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
			path:'/userLocalTask',
			name:'userLocalTask',
			component:userLocalTask
		},
		{
			path:'/addLocalTask',
			name:'addLocalTask',
			component:addLocalTask
		},
		{
			path:'/changeLocalTask',
			name:'changeLocalTask',
			component:changeLocalTask
		},
		{
			path:'/editJoinParticipator',
			name:'editJoinParticipator',
			component:editJoinParticipator
		},
		{
			path:'/editJointSubTask',
			name:'editJointSubTask',
			component:editJointSubTask
		},
		{
			path:'/jointSubTask',
			name:'jointSubTask',
			component:jointSubTask
		},
		{
			path:'/userJointTask',
			name:'userJointTask',
			component:userJointTask
		},
		{
			path:'/jointTask',
			name:'jointTask',
			component:jointTask
		},
		{
			path:'/addJointTask',
			name:'addJointTask',
			component:addJointTask
		},
		{
			path:'/changeJointTask',
			name:'changeJointTask',
			component:changeJointTask
		},
		{
			path:'/webUserManual',
			name:'userManual',
			component:userManual
		},
		{
			path:'/pluginPage',
			name:'pluginPage',
			component:pluginPage
		}
		]}
	]
	
	


const router = new VueRouter({
	routes
})

export default router
