<template>
	<div>
		<el-container>
		<img src="../assets/menuShowBtn.png"  @click="showList()" :class="{'showListButton':isShow,'hideListButton':!isShow}">
		  <el-aside width="200px" style="min-height: 1000px;background-color: rgb(16, 25, 43); overflow-x: hidden;" v-if="isShow">
			  <el-menu   text-color="#fff" active-text-color="#FFF" style="border:0px" >
			  	<li v-for="menu in menus">
			  		<el-submenu :index="menu.id.toString()" style="background-color: rgb(42, 59, 91);">
			  			<template slot="title"  >{{menu.name}}</template>
			  				<div  v-for="subMenu in menu.childMenuBoList" style="border-bottom:1px solid #5C75A7;">
								<el-row>
									<el-col :span="4" :class="{'theSelectedMenu':theSelectedSubMenu==subMenu.id.toString(),'otherSubMenu':theSelectedSubMenu!=subMenu.id.toString()}" :index="subMenu.id.toString()" style="height: 50px;">
										<img style="margin-left: 10px;margin-top: 20px;" height="15px" v-bind:src="require(`../assets/${subMenu.icon}`)" />
									</el-col>
									<el-col :span="20" style="height: 50px;background-color: rgb(86, 123, 181)">
										<el-menu-item :index="subMenu.id.toString()" @click="test(subMenu)" style="padding-left: 10px;">
											{{subMenu.name}}
										</el-menu-item>
									</el-col>
								</el-row>						
			  				</div>
			  		</el-submenu>
			  	</li>
				</el-menu>
		  </el-aside>

		  <el-main>
			  <div class="welcomeLabel" @click="toUserSetting()">
			  	你好！<span>{{userName}}</span>
			  </div>
			  <div class="nodeName">
			   	<span style="margin-left:10px">你的位置： {{nodeName}}</span>
			  </div>
			 <router-view>
			 </router-view>
		  </el-main>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			
			const item = {};
			return {
				tableData: Array(20).fill(item),
				userName: sessionStorage.getItem("username"),
				userPwd: '',
				menus:[],
				theSelectedMenu:'',
				theSelectedSubMenu:'',
				isShow:true,
				routerLink:'',
				nodeName:'',
			}
		},
		methods:{
			getUserMenu(){
				// 获取用户菜单
				axios.get('/api/menu/userMenu', {
					headers: {
						"token": localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var menuList = eval(response.data);
					this.menus = menuList;
				})
				.catch(err => {
					this.$alert('尚未登录，正在回到主页面', '提示', {
						         confirmButtonText: '确定',
						       });
					this.$router.push({path:"/"});
				});
			},
			test(e){
				this.theSelectedSubMenu = e.id.toString();
				this.routerLink = e.url.toString();
				 this.$router.push({path:this.routerLink});
			},
			showList(){
				this.isShow=!this.isShow;
			},
			toUserSetting(){
				this.$router.push("userSetting");
			},
			getUserNodeName(){
				// 获取节点名称
				axios.get('/api/webSiteSettings/nodeName', {
					headers: {
						"token": localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					this.nodeName = response.data;
				})
			}
		},
		beforeMount:function() {
			if(localStorage.getItem("token") == null || localStorage.getItem("token") == ""){
				this.$alert('尚未登录，正在回到主页面', '提示', {
						         confirmButtonText: '确定',
						       });
					this.$router.push({path:"/"});
			}

			this.getUserMenu();
			this.getUserNodeName();
		}
		}
</script>
	
<style>
	.welcomeLabel{
		margin-top: 0px; 
		position: absolute; 
		right: 10%; 
		top: 15px;
		background: transparent;
		padding-left: 10px;
		padding-right: 10px;
		border-radius: 13px;
		border: #77a6e7 2px solid;
		color: #77a6e7;
		cursor: pointer;
		padding-top: 3px;
		padding-bottom: 3px;
	}

	.nodeName{
		color:#77a6e7;

		margin-top: 5px; 
		position: absolute; 
		left:10%; 
		top: 15px;
		background: transparent;
		padding-left: 10px;
		padding-right: 10px;
	}

	.welcomeLabel:hover{
		background: #77a6e7;
		color: #000;
	}

	body{
  margin:0;
}
	
	.theSelectedMenu{
		background-color: #24375E;
	}
	.otherSubMenu{
		background-color: #334E85;
	}
	.showListButton{
		position: fixed;
		left: 200px !important;
		top: 48% !important;
		width: 10px !important;
		height: 35px;
	}
	.hideListButton{
		position: fixed;
		left: 0px;
		top: 48% !important;
		width: 10px !important;
		height: 35px;
	}
	.el-menu-item:focus, .el-menu-item:hover {
	    outline: 0;
	    background-color: #c6d8ff;
	    color: #000 !important;
	}
	.el-submenu__title:focus,.el-submenu__title:hover{
		color: #000 !important;
	}
	.title {
		font-size: 20px;
		color: #A3B6DD;
	}
</style>
