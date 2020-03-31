<template>
	<div>
		<el-container>
		
		  <el-aside width="200px" style="min-height: 700px;background-color: #24375E;">
			  <el-menu :default-openeds="[]"  text-color="#fff" active-color="#1D376B" >
			  	<li v-for="menu in menus">
			  		<el-submenu :index="menu.id.toString()" style="background-color: #10192B;">
			  			<template slot="title"  >{{menu.name}}</template>
			  				<div  v-for="subMenu in menu.childMenuBoList" style="background-color: #334E85;">
			  						<el-menu-item :index="subMenu.id.toString()" >
			  								{{subMenu.name}}
			  						</el-menu-item>
	
			  				</div>
			  		</el-submenu>
			  	</li>
				</el-menu>
		  </el-aside>
		  <el-main>
			 
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
			changSubMenu(e,i){
				this.theSelectedSubMenu=i;
			},
			test(e){
				
			},
		},
		beforeMount:function() {
			this.getUserMenu();
		}
		}
</script>
	
<style>
	.theSelectedMenu{
		background-color: black;
	}
</style>
