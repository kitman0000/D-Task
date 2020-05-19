<template>
	<el-container>
		<img src="../assets/TIM图片20200401203159.png" @click="showList()" :class="{'showListButton':isShow,'hideListButton':!isShow}">
		<el-aside width="200px" style="min-height: 700px;background-color: #24375E; overflow-x: hidden;" v-if="isShow">
		</el-aside>
		<el-main>
			<el-input placeholder="用户名" v-model="username" style="width: 300px;"></el-input>
			<el-input placeholder="密码" v-model="pwd" style="width: 300px;"></el-input>
			<el-input placeholder="昵称" v-model="nickname" style="width: 300px;"></el-input>
			<el-input placeholder="手机号" v-model="phone" style="width: 300px;"></el-input>
			<el-input placeholder="邮箱" v-model="email" style="width: 300px;"></el-input>
			 <div class="block">
			    <span class="demonstration">入职日期</span>
			    <el-date-picker
			      v-model="onboardDate"
			      type="date"
			      placeholder="选择日期">
			    </el-date-picker>
			  </div>
			 <div class="block">
			    <span class="demonstration">出生日期</span>
			    <el-date-picker
			      v-model="birthday"
			      type="date"
			      placeholder="选择日期">
			    </el-date-picker>
			  </div>
			<el-select v-model="role" placeholder="请选择角色">
			    <el-option
			      v-for="item in roleList"
			      :key="item.roleID"
			      :label="item.roleName"
			      :value="item.roleID">
			    </el-option>
			  </el-select>
			  <el-select v-model="department" placeholder="请选择部门">
			      <el-option
			        v-for="item in departmentList"
			        :key="item.id"
			        :label="item.departmentName"
			        :value="item.id">
			      </el-option>
			    </el-select>
			<span style="color: red; font-size: 10px;">{{message}}</span>
			<el-button type="primary" @click="addUser()" icon="el-icon-search" style="margin-left: 10px;">增加</el-button>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				pwd:"",
				role:"",
				department:"",
				onboardDate:"",
				username:"",
				nickname:"",
				phone:"",
				email:"",
				birthday:"",
				roleList:[],
				departmentList:[],
				message:"",
				isShow: true,
			}
		},
		methods:{
			addUser(){
				if (!this.usename) {
					this.message = 'username不可为空';
				}
				else if (!this.pwd) {
					this.message = 'pwd不可为空';
				}
				else if (!this.nickname) {
					this.message = 'nickname不能为空';
				}
				else if (!this.phone) {
					this.message = 'phone不可为空';
				}
				else if (!this.email) {
					this.message = 'email不能为空';
				}
				else if (!this.roleID) {
					this.message = 'role不可为空';
				}
				else if (!this.departmentID) {
					this.message = 'department不能为空';
				}
				else if (!this.birthday) {
					this.message = 'birthday不可为空';
				}
				else if (!this.onboardDate) {
					this.message = 'onboardDate不能为空';
				}
				else{
					var params = new URLSearchParams();
					var onboardDate = new Date(this.onboardDate).toLocaleDateString().replace(/\//g, '-');
					var birthday = new Date(this.birthday).toLocaleDateString().replace(/\//g, '-');
					params.append("usename",this.username);
					params.append("pwd",this.pwd);
					params.append("nickname",this.nickname);
					params.append("phone",this.phone);
					params.append("email",this.email);
					params.append("roleID",this.roleID);
					params.append("departmentID",this.department);
					params.append("birthday",birthday);
					params.append("onboardDate",onboardDate);
					axios.post("/api/user/user",{
						params:params,
						headers:{
							token:localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						var retObj = eval(response.data);
						console.log(retObj);
						if(retObj.message=="添加成功"){
							this.$router.push({
								path:'/user',
							})
						}
					})
				}
			},
			getRole(){
				axios.get("/api/role/role",{
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var roleObj = eval(response.data);
					console.log(roleObj);
					this.roleList = roleObj;
				})
			},
			getDepartment(){
				axios.get("/api/department/department",{
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var departmentObj = eval(response.data);
					console.log(departmentObj);
					this.departmentList = departmentObj;
				})
			},
			showList(){
				this.isShow=!this.isShow;
			}
		},
		beforeMount:function() {
			this.getDepartment();
			this.getRole();
		}
	}
</script>

<style>
	.theSelectedMenu {
		background-color: #24375E;
	}
	
	.otherSubMenu {
		background-color: #334E85;
	}
	
	.showListButton {
		position: fixed;
		left: 208px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
	
	.hideListButton {
		position: fixed;
		left: 0px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
</style>
