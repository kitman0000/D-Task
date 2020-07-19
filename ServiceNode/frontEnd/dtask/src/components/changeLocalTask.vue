<template>
	<el-container>
		</el-aside>
		<el-main>
			<el-form>
				<el-form-item label="任务:">
					<el-input placeholder="任务" v-model="name" style="width: 192px;"></el-input>
					</el-form-item>
				<el-form-item label="部门:">
					<el-select v-model="department" placeholder="请选择" @change="getUser()">
						<el-option v-for="item in departmentList" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="人员:">
					<el-select v-model="creator" placeholder="请选择">
						<el-option v-for="item in creatorList" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
						</el-form>
			<span style="color: red; font-size: 10px;">{{message}}</span>
			<el-button type="primary" @click="changeTask()" icon="el-icon-change" style="position: relative; left: 90px; margin-left: 10px; background: #24375E;border: 0px ;">修改</el-button>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				department:"",
				name:"",
				allowedMemberChangeStatus:"",
				creator:"",
				message:"",
				departmentList:[],
				creatorList:[],
				isShow: true,
			}
		},
		methods:{
			changeTask(){
				if (!this.name) {
					this.message = '任务名不可为空';
				}
				else if (!this.creator) {
					this.message = '创建者不能为空';
				}
				else{
					var params = new URLSearchParams();
					var taskID =parseInt(localStorage.getItem("taskID"));
					params.append("id",taskID);
					params.append("name",this.name);
					params.append("creator",this.creator);
					params.append("allowedMemberChangeStatus",this.allowedMemberChangeStatus);
					axios.put("/api/local/localTask",
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
						path:'/userTask',
					})
				}
			},
			getTaskDetail(){
				var params = new URLSearchParams();
				var taskID =parseInt(localStorage.getItem("taskID"));
				params.append("taskID",taskID);
				axios.get("/api/localTask/localTaskDetail",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var taskObj = eval(response.data);
					console.log(taskObj);
					this.name = taskObj.name;
					this.department = taskObj.departmentID;
					console.log(this.department);
					this.creator = taskObj.creatorName;
					this.allowedMemberChangeStatus = taskObj.allowedMemberChangeStatus;
					this.getDepartment();
					this.getUser();
				})
			},
			getUser(){
				console.log("01")
				console.log(this.department);
				axios.get('/api/user/userList', {
						params: {
							username:"",
							nickname:"",
							phone:"",
							email:"",
							onboardDateStart:"",
							onboardDateEnd:"",
							roleID:-1,
							departmentID:this.department,
							birthdayStart:"",
							birthdayEnd:"",
							page:1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						this.creatorList=[];
						a.forEach((res)=>{
							this.creatorList.push({
								value: res.id,
								label:res.username
								});
						})
			
						console.log(this.creatorList);
					})
					.catch(function(error) {
						this.$alert('请求用户失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			getDepartment(){
				axios.get('/api/department/department', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						a.forEach((res)=>{
							this.departmentList.push({
								value: res.id,
								label:res.departmentName
								});
						})
					})
					.catch(function(error) {
						this.$alert('请求部门失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
			
			showList(){
				this.isShow=!this.isShow;
			}
		},
		beforeMount:function() {
			this.getTaskDetail();
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
