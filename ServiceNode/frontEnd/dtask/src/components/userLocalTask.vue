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
				
			
				<el-row :gutter="20">
					<el-col :span="5"  v-for="task in taskList" :key="task" >
					<el-card  class="box-card taskCard" @click.native="userTaskDetail(task.id,task.creator)">
						任务名：{{task.name}}<br/>
						创建者：{{task.creatorName}}

						<div v-if="task.planning + task.working + task.finish + task.cancel != 0">
							<div style="height:250px"  v-bind:id="task.id"></div>
						</div>
						<div style="margin-top:90px;width:100%;" align="center">
							<span style="color:#ccc;font-size:20px">
								暂无数据
							</span>
						</div>
					</el-card>
					</el-col>
				</el-row>

				
				<!-- <el-table style="width: 100%;" :data="taskList" @row-click="userTaskDetail">
					<el-table-column label="任务名" prop="name" >
					</el-table-column>
					<el-table-column label="任务创建者名" prop="creatorName">
					</el-table-column>
					<el-table-column label="完成度" style="height:30px">
						<template slot-scope="scope">	
						<div style="height:30px"  v-bind:id="scope.row.id"></div>
						</template>
					</el-table-column>
				</el-table> -->
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
	var echarts = require("echarts/lib/echarts")
	require('echarts/lib/component/tooltip')
	require('echarts/lib/component/title')
	require('echarts/lib/chart/pie')
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
			locakTaskStateEcharts(id,planningStatusAmount,doingStatusAmount,completedStatusAmount,cancelledStatusAmount){
				var statusChart = echarts.init(document.getElementById(id));
				statusChart.setOption({
					tooltip: {
						trigger: 'item'
					},
					color:['#3399FF','#FF6600','#009933','#C0C0C0'],
					series: [
						{
							type: 'pie',
							radius: ['55%','65%'],
							center: ['50%','45%'],
							avoidLabelOverlap: false,
							label: {
								show: false,
								position: 'center',
							},
							emphasis: {
								label: {
									show: true,
									fontSize: '15',
									fontWeight: 'bold'
								}
							},
							labelLine: {
								show: false
							},
							data: [
								{value:planningStatusAmount, name: '计划中'},
								{value:doingStatusAmount, name: '执行中'},
								{value:completedStatusAmount, name: '已完成'},
								{value:cancelledStatusAmount, name: '已取消'}
							]
						}
					]
				});
			},
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
					var _this = this;
					this.$nextTick(function(){
						for(var i=1;i<=taskObj.length;i++){
						if(i=taskObj[i-1].id){
							_this.locakTaskStateEcharts(taskObj[i-1].id,taskObj[i-1].planning,taskObj[i-1].working,
							taskObj[i-1].finish,taskObj[i-1].cancel);
						}
					}
					});
					
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
			userTaskDetail(id,creator){
				this.$router.push({path:"/localSubTask?taskID="+id+"&creator="+creator});
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
		beforeMount() {
			this.getTaskNumber();
			this.handleTaskList();
			
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

	.taskCard{
		height: 300px;
	}
</style>


