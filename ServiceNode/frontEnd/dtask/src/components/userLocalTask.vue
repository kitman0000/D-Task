<template>
	<div>
		<el-container>
			</el-aside>
			<el-main>
				<span class="demonstration">任务名：</span>
				<el-input placeholder="任务名" v-model="taskName"  style="width: 15%; margin: 20px;"></el-input>
				<span class="demonstration">任务创建者名：</span>
				<el-input placeholder="任务创建者名" v-model="taskCreatorName"  style="width: 15%; margin: 20px;"></el-input>
				<el-button type="primary" @click="getTaskNumber(),handleTaskList()" icon="el-icon-search" style="margin-left: 10px;background: #24375E;border: 0px ;">搜索</el-button>
				<el-table style="width: 100%;" :data="taskList" @row-click="userTaskDetail">
					<el-table-column label="任务ID" prop="id" width="180">
					</el-table-column>
					<el-table-column label="任务名" prop="name" width="180">
					</el-table-column>
					<el-table-column label="任务创建者ID" prop="creator" width="180">
					</el-table-column>
					<el-table-column label="任务创建者名" prop="creatorName" width="180">
					</el-table-column>
					<!-- <el-table-column label="删除" align="center" min-width="100">
						<template slot-scope="scope">
							<el-button type="text" @click="deleteTask(scope.row.id)">删除</el-button>
						</template>
					</el-table-column>
					<el-table-column label="修改" align="center" min-width="100">
						<template slot-scope="scope">
							<el-button type="text" @click="changeTask(scope.row.id)">修改</el-button>
						</template>
					</el-table-column> -->
				</el-table>
				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
				layout=" prev, pager, next, jumper" :total="taskNumber">
				</el-pagination>
				<!-- <el-button type="primary" @click="addTask()" icon="el-icon-plus" style="margin-left: 10px;background: #24375E;border: 0px ;">增加</el-button> -->
			</el-main>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		
		data() {			
			return {
				taskID:"",
				taskName:"",
				taskCreatorName:"",
				taskCreatorID:"",
				taskNumber:1,
				currentPage:1,
				pagesize:10,
				taskList:[
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
				var params = new URLSearchParams();
				params.append("taskName",this.taskName);
				params.append("taskCreatorName",this.taskCreatorName);
				params.append("page",this.currentPage);
				axios.get("/api/localTask/UserLocalTaskList",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var taskObj = eval(response.data);
					console.log(taskObj);
					this.taskList = taskObj;
				})
				.catch(err => {
					alert("请求异常");
				});
			},
			test(){
				var nowdate = new Date(this.value1[0]).toLocaleDateString().replace(/\//g, '-');
				console.log(nowdate);
			},
			handleTaskList(){
				var params = new URLSearchParams();
				params.append("taskName",this.taskName);
				params.append("taskCreatorName",this.taskCreatorName);
				params.append("page",this.currentPage);
				axios.get("/api/localTask/UserLocalTaskList",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var taskObj = eval(response.data);
					console.log(taskObj);
					this.taskList = taskObj;
				})
				.catch(err => {
					alert("请求异常");
				});
			},
			getTaskNumber(){
				var params = new URLSearchParams();
				params.append("taskName",this.taskName);
				params.append("taskCreatorName",this.taskCreatorName);
				axios.get("/api/localTask/UserLocalTaskPage",{
					params:params,
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var taskNumberObj = eval(response.data);
					console.log(taskNumberObj);
					this.taskNumber = taskNumberObj*10;
				})
				.catch(err => {
					alert("请求异常");
				});
			},
			userTaskDetail(row,event){
				this.$router.push({path:"/SubTask?taskID="+row.id+"&creator="+row.creator});
			},
			// addTask(){
			// 	this.$router.push({
			// 		path:'/addLocalTask',
			// 	})
			// },
			// deleteTask(taskID){
			// 	var params = new URLSearchParams();
			// 	params.append("id",this.taskID);
			// 	axios.get("/api/localTask/localTask",{
			// 		params:params,
			// 		headers:{
			// 			token:localStorage.getItem("token"),
			// 		}
			// 	})
			// 	.catch(err => {
			// 		alert("请求异常");
			// 	});
			// },
			// changeTask(taskID){
			// 	localStorage.setItem("taskID", taskID);
			// 	this.$router.push({
			// 		path:'/changeLocalTask',
			// 	})
			// },
			showList() {
				this.isShow = !this.isShow;
			},
		},
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


