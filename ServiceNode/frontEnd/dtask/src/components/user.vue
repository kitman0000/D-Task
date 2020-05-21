<template>
	<div>
		<el-container>
			<img src="../assets/TIM图片20200401203159.png" @click="showList()" :class="{'showListButton':isShow,'hideListButton':!isShow}">
			<el-aside width="200px" style="min-height: 700px;background-color: #24375E; overflow-x: hidden;" v-if="isShow">
			</el-aside>
			<el-main>
				<span class="demonstration">用户名：</span>
				<el-input placeholder="用户名" v-model="username"  style="width: 15%; margin: 20px;"></el-input>
				<span class="demonstration">昵称：</span>
				<el-input placeholder="昵称" v-model="nickname"  style="width: 15%; margin: 20px;"></el-input>
				<span class="demonstration">手机号：</span>
				<el-input placeholder="手机号" v-model="phone"  style="width: 15%; margin: 20px;"></el-input>
				<span class="demonstration">邮箱：</span>
				<el-input placeholder="邮箱" v-model="email"  style="width: 15%; margin: 20px;"></el-input>
				</br>
				    <span class="demonstration">入职日：</span>
				    <el-date-picker
					 style="width: 15%; margin: 20px;"
				      v-model="onboardDate"
				      type="daterange"
					  unlink-panels
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期">
				    </el-date-picker>
				    <span class="demonstration">生日：</span>
				    <el-date-picker
					 style="width: 15%; margin: 20px;"
				      v-model="birthday"
				      type="daterange"
				      unlink-panels
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期">
				    </el-date-picker>
				<span class="demonstration">角色名：</span>
				<el-select v-model="role" placeholder="请选择角色"  style="width: 15%; margin: 20px;">
				    <el-option
				      v-for="item in roleList"
				      :key="item.roleID"
				      :label="item.roleName"
				      :value="item.roleID">
				    </el-option>
				  </el-select>
				  <span class="demonstration">部门：</span>
				  <el-select v-model="department" placeholder="请选择部门" style="width: 15%; margin: 20px;">
				      <el-option
				        v-for="item in departmentList"
				        :key="item.id"
				        :label="item.departmentName"
				        :value="item.id">
				      </el-option>
				    </el-select>
				<el-button type="primary" @click="getUserNumber(),handleUserList()" icon="el-icon-search" style="margin-left: 10px;">搜索</el-button>
				<el-table style="width: 100%;" :data="userList.slice((currentPage-1)*pagesize,currentPage*pagesize)">
					<el-table-column type="index" width="50">
					</el-table-column>
					<el-table-column label="用户名" prop="username" width="180">
					</el-table-column>
					<el-table-column label="昵称" prop="nickname" width="180">
					</el-table-column>
					<el-table-column label="邮箱" prop="email" width="180">
					</el-table-column>
				</el-table>
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
				 :page-sizes="[10]" :page-size="pagesize" layout=" sizes, prev, pager, next, jumper" :total="userNumber">
				</el-pagination>
				<el-button type="primary" @click="addUser()" icon="el-icon-search" style="margin-left: 10px;">增加</el-button>
				<el-input placeholder="userID" v-model="userID" style="width: 300px;"></el-input>
				<el-button type="primary" @click="changeUser()" icon="el-icon-search" style="margin-left: 10px;">修改</el-button>
			</el-main>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		
		data() {			
			return {
				userID:"",
				onboardDate:"",
				birthday:"",
				role:"",
				department:"",
				username:"",
				nickname:"",
				phone:"",
				email:"",
				userNumber:1,
				currentPage:1,
				pagesize:10,
				roleList:[],
				departmentList:[],
				userList:[
				{
					username:'',
					nickname:'',
					email:''
				},
				],
				isShow: true,
			}
		},
		methods: {
			handleSizeChange(size){
				this.pagesize = size;
				console.log(this.pagesize);
			},
			handleCurrentChange(currentPage){
				this.currentPage = currentPage;
				console.log(this.currentPage);
			},
			test(){
				var nowdate = new Date(this.value1[0]).toLocaleDateString().replace(/\//g, '-');
				console.log(nowdate);
			},
			handleUserList(){
				var params = new URLSearchParams();
				var onboardDateStart = new Date(this.onboardDate[0]).toLocaleDateString().replace(/\//g, '-');
				var onboardDateEnd = new Date(this.onboardDate[1]).toLocaleDateString().replace(/\//g, '-');
				var birthdayStart = new Date(this.birthday[0]).toLocaleDateString().replace(/\//g, '-');
				var birthdayEnd = new Date(this.birthday[1]).toLocaleDateString().replace(/\//g, '-');
				var roleID;
				var departmentID;
				if(onboardDateStart == "Invalid Date"){
					onboardDateStart = "";
				}
				
				if(onboardDateEnd == "Invalid Date"){
					onboardDateEnd = "";
				}
				
				if(birthdayStart == "Invalid Date"){
					birthdayStart = "";
				}
				
				if(birthdayEnd == "Invalid Date"){
					birthdayEnd = "";
				}
					
				if(!this.role){
					roleID = -1;
				}
				else{
					roleID = this.role;
				}
				if(!this.department){
					departmentID = -1;
				}
				else{
					departmentID = this.department;
				}
				params.append("usename",this.username);
				params.append("nickname",this.nickname);
				params.append("phone",this.phone);
				params.append("email",this.email);
				params.append("onboardDateStart",onboardDateStart);
				params.append("onboardDateEnd",onboardDateEnd);
				params.append("roleID",roleID);
				params.append("departmentID",departmentID);
				params.append("birthdayStart",birthdayStart);
				params.append("birthdayEnd",birthdayEnd);
				params.append("page",this.currentPage);
				axios.get("/api/user/userList",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var userObj = eval(response.data);
					console.log(userObj);
					this.userList = userObj;
				})
				.catch(err => {
					alert("请求异常");
				});
			},
			getUserNumber(){
				var params = new URLSearchParams();
				var onboardDateStart = new Date(this.onboardDate[0]).toLocaleDateString().replace(/\//g, '-');
				var onboardDateEnd = new Date(this.onboardDate[1]).toLocaleDateString().replace(/\//g, '-');
				var birthdayStart = new Date(this.birthday[0]).toLocaleDateString().replace(/\//g, '-');
				var birthdayEnd = new Date(this.birthday[1]).toLocaleDateString().replace(/\//g, '-');
				var roleID;
				var departmentID;
				if(!this.role){
					roleID = -1;
				}
				else{
					roleID = this.role;
				}
				if(!this.department){
					departmentID = -1;
				}
				else{
					departmentID = this.department;
				}
				params.append("usename",this.username);
				params.append("nickname",this.nickname);
				params.append("phone",this.phone);
				params.append("email",this.email);
				params.append("onboardDateStart",onboardDateStart);
				params.append("onboardDateEnd",onboardDateEnd);
				params.append("roleID",roleID);
				params.append("departmentID",departmentID);
				params.append("birthdayStart",birthdayStart);
				params.append("birthdayEnd",birthdayEnd);
				axios.get("/api/user/userNumber",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var userNumberObj = eval(response.data);
					console.log(userNumberObj);
					this.userNumber = userNumberObj*10;
				})
				.catch(err => {
					alert("请求异常");
				});
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
			addUser(){
				this.$router.push({
					path:'/user/adduser',
				})
			},
			changeUser(){
				localStorage.setItem("userID", this.userID);
				this.$router.push({
					path:'/user/changeuser',
				})
			},
			showList() {
				this.isShow = !this.isShow;
			},
			
		},
		beforeMount: function() {
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

