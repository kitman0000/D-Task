<template>
	<div>
		<el-container>
			<el-main>
				<div>
					<span class="demonstration">用户名：</span>
					<el-input placeholder="用户名" v-model="username"  style="width: 15%; margin: 15px;"></el-input>
					<span class="demonstration">昵称：</span>
					<el-input placeholder="昵称" v-model="nickname"  style="width: 15%; margin: 15px;"></el-input>
					<span class="demonstration">手机号：</span>
					<el-input placeholder="手机号" v-model="phone"  style="width: 15%; margin: 15px;"></el-input>
					<span class="demonstration">邮箱：</span>
					<el-input placeholder="邮箱" v-model="email"  style="width: 15%; margin: 15px;"></el-input>
				</div>
				<div>
				    <span class="demonstration">入职日：</span>
				    <el-date-picker
					 style="width: 15%; margin: 15px;"
				      v-model="onboardDate"
				      type="daterange"
					  unlink-panels
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期">
				    </el-date-picker>
				    <span class="demonstration">生日：</span>
				    <el-date-picker
					 style="width: 15%; margin: 15px;"
				      v-model="birthday"
				      type="daterange"
				      unlink-panels
				      range-separator="至"
				      start-placeholder="开始日期"
				      end-placeholder="结束日期">
				    </el-date-picker>
					<span class="demonstration">角色名：</span>
					<el-select v-model="role" placeholder="请选择角色"  style="width: 15%; margin: 15px;">
						<el-option
						v-for="item in roleList"
						:key="item.roleID"
						:label="item.roleName"
						:value="item.roleID">
						</el-option>
					</el-select>
					<span class="demonstration">部门：</span>
					<el-select v-model="department" placeholder="请选择部门" style="width: 15%; margin: 15px;">
						<el-option
							v-for="item in departmentList"
							:key="item.id"
							:label="item.departmentName"
							:value="item.id">
						</el-option>
						</el-select>
					<el-button type="primary" @click="getUserNumber(),handleUserList()" icon="el-icon-search" style="margin-left: 10px;background: #24375E;border: 0px ;"></el-button>
					<el-button type="primary" style="background: #24375E;border: 0px ;" icon="el-icon-delete" @click="deleteUsers()"></el-button>
				</div>
				<el-table style="width: 100%;" :data="userList" @selection-change="handleSelectionChange">
					<el-table-column type="selection" width="55">
					</el-table-column>
					<el-table-column label="用户名" prop="username" width="180">
					</el-table-column>
					<el-table-column label="昵称" prop="nickname" width="180">
					</el-table-column>
					<el-table-column label="邮箱" prop="email" width="180">
					</el-table-column>
					<el-table-column label="生日" prop="birthDay" width="180">
					</el-table-column>
					<el-table-column label="详情" align="center" min-width="100">
						<template slot-scope="scope">
							<el-button type="text" @click="checkUser(scope.row.id)">查看详情</el-button>
						</template>
					</el-table-column>
					<el-table-column label="修改" align="center" min-width="100">
						<template slot-scope="scope">
							<el-button type="text" @click="changeUser(scope.row.id)">修改</el-button>
						</template>
					</el-table-column>
				</el-table>
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
				layout=" prev, pager, next, jumper" :total="userNumber">
				</el-pagination>
				<el-button type="primary" @click="addUser()" icon="el-icon-plus" style="margin-left: 10px;margin-top:10px;background: #24375E;border: 0px ;">增加</el-button>
			</el-main>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
	export default {
		inject: ['reload'],
		
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
				],
				isShow: true,
				multipleSelection:[],
			}
		},
		methods: {
			handleSelectionChange(val) {
				console.log(val);
				let that_ = this;
				that_.multipleSelection = val;
				// let a = true;
				// that_.selectedDataTemp = that_.multipleSelection;
				// that_.selectedDataTemp = that_.multipleSelection.filter(item => {
				// 	return !item.a
				// });
			},
			handleSizeChange(size){
				this.pagesize = size;
				console.log(this.pagesize);
			},
			handleCurrentChange(currentPage){
				this.currentPage = currentPage;
				console.log(this.currentPage);
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
				params.append("username",this.username);
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
					var response = res.data.data;
					this.userList = eval(response);
				})
				.catch(err => {
					// alert("请求异常");
				});
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
				params.append("username",this.username);
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
					// alert("请求异常");
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
				params.append("username",this.username);
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
					// alert("请求异常");
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
					path:'/adduser',
				})
			},
			checkUser(userID){
				localStorage.setItem("userID", userID);
				this.$router.push({
					path:'/checkuser',
				})
			},
			changeUser(userID){
				localStorage.setItem("userID", userID);
				this.$router.push({
					path:'/changeuser',
				})
			},
			//删除多个用户
			deleteUsers() {
				var selectedArray = new Array();

				console.log(this.multipleSelection.length);

				for (var i = 0; i < this.multipleSelection.length; i++) {
					selectedArray.push(this.multipleSelection[i].id);
					console.log(this.multipleSelection[i]);
				}

				var _this = this;

				this.$confirm('删除这些用户, 是否确定?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/user/user', {
							params: {
								userID: selectedArray,
							},
							paramsSerializer: params => {
								return qs.stringify(params, {
									indices: false
								})
							},
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(function(response) {

							if (response.data.ret == 1) {
								_this.$alert('删除成功', '提示', {
									confirmButtonText: '确定',
								});
								_this.handleUserList();
							} else {
								_this.$alert('删除失败', '提示', {
									confirmButtonText: '确定',
								});
							}

						})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			showList() {
				this.isShow = !this.isShow;
			},
		},
		beforeMount: function() {
			this.getDepartment();
			this.getRole();
			this.getUserNumber();
			this.handleUserList();
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

