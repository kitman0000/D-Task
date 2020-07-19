<template>
	<el-container>
		</el-aside>
		<el-main>
			<el-form>
				<el-form-item label="任务:">
					<el-input placeholder="任务" v-model="taskName" style="width: 192px;"></el-input>
					</el-form-item>
				<el-form-item label="任务结点:">
					<el-select v-model="creatorNode" placeholder="请选择" @change="getUser()">
						<el-option v-for="item in nodeList" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="人员:">
					<el-select v-model="creatorName" placeholder="请选择">
						<el-option v-for="item in creatorList" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="是否允许用户修改:">
					<el-switch style="margin-bottom: 10px !important;"
					  v-model="allowedMemberChangeStatus"
					  active-color="#13ce66"
					  inactive-color="#ff4949">
					</el-switch>
				</el-form-item>
				<span style="color: red; font-size: 10px;">{{message}}</span>
				<el-button type="primary" @click="changeTask()" icon="el-icon-change" style="position: relative; margin-left: 10px; background: #24375E;border: 0px ;">修改</el-button>
			</el-form>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				creatorNode:"",
				creatorID:"",
				taskName:"",
				allowedMemberChangeStatus:"",
				creatorName:"",
				message:"",
				nodeList:[],
				creatorList:[],
				isShow: true,
			}
		},
		methods:{
			changeTask(){
				if (!this.taskName) {
					this.message = '任务名不可为空';
				}
				else if (!this.creatorName) {
					this.message = '创建者不能为空';
				}
				else{
					var params = new URLSearchParams();
					var taskID =parseInt(localStorage.getItem("taskID"));
					params.append("id",taskID);
					params.append("name",this.taskName);
					params.append("creator",this.creatorID);
					params.append("creatorNodeID",this.creatorNode);
					params.append("allowedMemberChangeStatus",this.allowedMemberChangeStatus);
					axios.put("/api/remoteTask/remoteTask",
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
						path:'/jointTask',
					})
				}
			},
			getTaskDetail(){
				var params = new URLSearchParams();
				var taskID =parseInt(localStorage.getItem("taskID"));
				params.append("taskID",taskID);
				axios.get("/api/remoteTask/remoteTaskDetail",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var taskObj = response.data;
					taskObj = eval("("+taskObj+")");
					this.taskName = taskObj.taskName;
					this.creatorNode = taskObj.creatorNode;
					this.creatorName = taskObj.creatorName;
					this.creatorID = taskObj.creatorID;
					this.allowedMemberChangeStatus = taskObj.allowedMemberChangeStatus;
					this.getNode();
					this.getUser();
				})
			},
			getUser(){
				console.log(this.creatorNode);
				axios.get('/api/user/remoteUser', {
						params: {
							nodeID:this.creatorNode,
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
								value: res.userID,
								label:res.nickname
								});
						})
						console.log(this.creatorList);
					})
			},
			getNode(){
				axios.get('/api/bindingCl/allNodes', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						a.forEach((res)=>{
							this.nodeList.push({
								value: res.id,
								label:res.nodeName
								});
						})
					})
					.catch(function(error) {
						this.$alert('请求任务结点失败', '提示', {
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
