<template>
	<el-container>
		</el-aside>
		<el-main>
			<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">任务名</div>
			<el-input placeholder="任务名" v-model="name" style="width: 300px;"></el-input>
			</div>
			<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">创建者</div>
			<el-input placeholder="创建者" v-model="creator" style="width: 300px;"></el-input>
			</div>
			<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">状态</div>
			</div>
			<el-input placeholder="状态" v-model="allowedMemberChangeStatus" style="width: 300px;"></el-input>
			<span style="color: red; font-size: 10px;">{{message}}</span>
			<el-button type="primary" @click="changeTask()" icon="el-icon-change" style="position: relative; left: 90px; margin-left: 10px;">修改</el-button>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				name:"",
				allowedMemberChangeStatus:"",
				creator:"",
				message:"",
				isShow: true,
			}
		},
		methods:{
			changeTask(){
				if (!this.name) {
					this.message = '用户名不可为空';
				}
				else if (!this.creator) {
					this.message = '昵称不能为空';
				}
				else if (!this.allowedMemberChangeStatus) {
					this.message = '手机号不可为空';
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
				axios.get("/api/localTask/taskDetail",{
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
					this.creator = taskObj.creator;
					this.allowedMemberChangeStatus = taskObj.allowedMemberChangeStatus;
				})
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
