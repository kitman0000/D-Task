<template>
	<el-container>
		</el-aside>
		<el-main>
			<el-card>
				<div slot="header" class="clearfix">
					<span>基本信息</span>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
					<div style="float: left; line-height: 35px; width: 100px;">用户名</div>
				<el-input placeholder="用户名" v-model="username" style="width: 300px;" readonly="readonly" :disabled="true"></el-input>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
					<div style="float: left; line-height: 35px; width: 100px;">重置密码</div>
				<el-input placeholder="密码" v-model="pwd" style="width: 300px;"></el-input>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
					<div style="float: left; line-height: 35px; width: 100px;">昵称</div>
				<el-input placeholder="昵称" v-model="nickname" style="width: 300px;"></el-input>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
					<div style="float: left; line-height: 35px; width: 100px;">手机号</div>
				<el-input placeholder="手机号" v-model="phone" style="width: 300px;"></el-input>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
					<div style="float: left; line-height: 35px; width: 100px;">邮箱</div>
				<el-input placeholder="邮箱" v-model="email" style="width: 300px;"></el-input>
				</div>
				</div>
				<div class="block" style="height: 60px;">
					<span style="float: left; line-height: 35px; width: 100px;">入职日期：</span>
					<el-date-picker
					v-model="onboardDate"
					type="date"
					placeholder="选择日期">
					</el-date-picker>
				</div>
				<div class="block" style="height: 60px;">
					<span style="float: left; line-height: 35px; width: 100px;">出生日期：</span>
					<el-date-picker
					v-model="birthday"
					type="date"
					placeholder="选择日期">
					</el-date-picker>
				</div>
			</el-card>

			<el-card>
				<div slot="header" class="clearfix">
					<span>系统信息</span>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">角色：</div>
				<el-select style="float: left;" v-model="role" placeholder="请选择角色">
					<el-option
					v-for="item in roleList"
					:key="item.roleID"
					:label="item.roleName"
					:value="item.roleID">
					</el-option>
				</el-select>
				</div>
				<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">部门：</div>
				<el-select style="float: left;" v-model="department" placeholder="请选择部门">
					<el-option
						v-for="item in departmentList"
						:key="item.id"
						:label="item.departmentName"
						:value="item.id">
					</el-option>
					</el-select>
					</div>
			</el-card>
			<span style="color: red; font-size: 10px;">{{message}}</span><br/>
			<el-button type="primary" @click="changeUser()" icon="el-icon-change" style="position: relative;margin-top:10px;background: #24375E;border: 0px ;">修改</el-button>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				userID:1,
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
			changeUser(){
				if (!this.username) {
					this.message = '用户名不可为空';
				}
				else if (!this.nickname) {
					this.message = '昵称不能为空';
				}
				else if (!this.phone) {
					this.message = '手机号不可为空';
				}
				else if (!this.email) {
					this.message = '邮箱不能为空';
				}
				else if (!this.onboardDate) {
					this.message = '入职日期不能为空';
				}
				else if (!this.birthday) {
					this.message = '出生日期不可为空';
				}
				else if (!this.role) {
					this.message = '角色不可为空';
				}
				else if (!this.department) {
					this.message = '部门不能为空';
				}
				else{
					var pwd
					// 如果密码为空，则不修改密码
					if (this.pwd) {
						pwd = this.$md5(this.pwd);
					}
					else{
						pwd = "";
						var params = new URLSearchParams();
						var onboardDate = new Date(this.onboardDate).toLocaleDateString().replace(/\//g, '-');
						var birthday = new Date(this.birthday).toLocaleDateString().replace(/\//g, '-');
						var userID =parseInt(localStorage.getItem("userID"));
						params.append("id",userID);
						params.append("pwd",pwd);
						params.append("nickname",this.nickname);
						params.append("phone",this.phone);
						params.append("email",this.email);
						params.append("roleID",this.role);
						params.append("departmentID",this.department);
						params.append("birthday",birthday);
						params.append("onboardDate",onboardDate);
						axios.put("/api/user/user",
						params,
						{
							headers:{
								token:localStorage.getItem("token"),
							}
						})
						.then(res => {
							var response = res.data;
							var retObj = eval(response.data);
							console.log(retObj);
						})
						this.$router.push({
							path:'/user',
						})
					}
					
				}
			},
			getUserDetail(){
				var params = new URLSearchParams();
				var userID =parseInt(localStorage.getItem("userID"));
				params.append("userID",userID);
				axios.get("/api/user/userDetail",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var userObj = eval(response.data);
					console.log(userObj);
					this.username = userObj.username;
					this.pwd = userObj.pwd;
					this.nickname = userObj.nickName;
					this.email = userObj.email;
					this.role = userObj.roleID;
					this.phone = userObj.phone;
					this.birthday = userObj.birthday;
					this.department = userObj.departmentID;
					this.onboardDate = userObj.onboardDate;
				})
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
			this.getUserDetail();
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
