<template>
	<div>
		<el-container>
		<img src="../assets/menuShowBtn.png"  @click="showList()" :class="{'showListButton':isShow,'hideListButton':!isShow}">
		  <el-aside width="200px" style="min-height: 1000px;background-color: #24375E; overflow-x: hidden;" v-if="isShow">
			  <el-menu   text-color="#fff" active-text-color="#FFF" >
			  	<li v-for="menu in menus">
			  		<el-submenu :index="menu.id.toString()" style="background-color: #10192B;">
			  			<template slot="title"  >{{menu.name}}</template>
			  				<div  v-for="subMenu in menu.childMenuBoList" style="border-bottom:1px solid #5C75A7;">
								<el-row>
								  <el-col :span="4" :class="{'theSelectedMenu':theSelectedSubMenu==subMenu.id.toString(),'otherSubMenu':theSelectedSubMenu!=subMenu.id.toString()}" :index="subMenu.id.toString()" style="height: 50px;"></el-col>
								  <el-col :span="20" style="height: 50px;background-color: #334E85;"><el-menu-item :index="subMenu.id.toString()" @click="test(subMenu)">
										{{subMenu.name}}
			  						</el-menu-item></el-col>
								</el-row>						
			  				</div>
			  		</el-submenu>
			  	</li>
				</el-menu>
		  </el-aside>
		  <el-main>
			 <router-view></router-view>
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
				userName: '',
				userPwd: '',
				menus:[],
				theSelectedMenu:'',
				theSelectedSubMenu:'',
				isShow:true,
				routerLink:'',
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
					alert("请求异常");
				});
			},
			test(e){
				this.theSelectedSubMenu = e.id.toString();
				this.routerLink = e.url.toString();
				 this.$router.push({path:this.routerLink});
			},
			showList(){
				this.isShow=!this.isShow;
			}
		},
		beforeMount:function() {
			this.getUserMenu();
		}
		}
</script>
	
<style>
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
</style>
