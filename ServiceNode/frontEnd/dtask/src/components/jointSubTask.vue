<template>
	<div>
		<el-container>
			<el-main>
				<el-button type="primary" style="float: right;margin-top: 15px;background: #24375E;border: 0px ;" icon="el-icon-delete"
				 v-if="role != 3" @click="deleteTasks()">删除所选子任务</el-button>
				<el-button type="primary" style="float: right;margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
				 icon="el-icon-plus" v-if="role != 3" @click="addTask()">添加子任务</el-button>
				<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
					<el-table-column type="selection" width="55"></el-table-column>
					<el-table-column prop="name" label="子任务名称" width="150px">
					</el-table-column>
					<el-table-column prop="startTime" label="开始时间">
					</el-table-column>
					<el-table-column prop="deadline" label="截止时间">
					</el-table-column>
					<el-table-column prop="level" label="级别" width="50px">
					</el-table-column>
					<el-table-column prop="status" label="状态" width="100px">
						<template slot-scope="scope">
							<p v-if="scope.row.status == 0" style="color: #3399FF;"><img class="icon" src="../assets/计划.png" /> 计划中</p>
							<p v-if="scope.row.status == 1" style="color: #FF6600;"><img class="icon" src="../assets/执行.png" /> 执行中</p>
							<p v-if="scope.row.status == 2" style="color: #009933;"><img class="icon" src="../assets/完成.png" /> 已完成</p>
							<p v-if="scope.row.status == 3" style="color: #C0C0C0;"><img class="icon" src="../assets/取消.png" /> 已取消</p>
						</template>
					</el-table-column>
					<el-table-column prop="star" label="星级" width="150px">
						<template slot-scope="scope">
							<span class='el-icon-star-on' v-if="scope.row.star == 0" style="color: blue;">一般</span>
							<span class='el-icon-star-on' v-if="scope.row.star == 1" style="color: darkgreen;">较重要</span>
							<span class='el-icon-star-on' v-if="scope.row.star == 2" style="color: orange;">重要</span>
							<span class='el-icon-star-on' v-if="scope.row.star == 3" style="color: red;">特别重要</span>
						</template>
					</el-table-column>
					<el-table-column label="操作">
						<template slot-scope="scope">
							<el-button type="text" size="small" v-if="role != 3" @click="deleteTask(scope.row)">删除子任务</el-button>
							<el-button type="text" size="small" @click="TaskDetail(scope.row)">查看子任务详情</el-button>
							<el-dialog title="子任务详情" :visible.sync="dialogVisible" width="30%">
								<p>子任务名：{{name}}</p>
								<p>子任务内容：{{content}}</p>
								<p>开始时间：{{startTime}}</p>
								<p>截止时间：{{deadline}}</p>
								<p>等级：{{level}}</p>
								<p>状态：<span v-if="scope.row.status == 0 && !stateOperation">计划中</span>
									<span v-if="scope.row.status == 1 && !stateOperation">执行中</span>
									<span v-if="scope.row.status == 2 && !stateOperation">已完成</span>
									<span v-if="scope.row.status == 3 && !stateOperation">已取消</span>
									<el-select v-model="value" placeholder="请选择状态" v-if="stateOperation">
									    <el-option
									      v-for="item in options"
									      :key="item.value"
									      :label="item.label"
									      :value="item.value">
									    </el-option>
									  </el-select>
									
									</p>
								<p>标签：</p>
								<el-tag :key="tag" v-for="tag in tag" :disable-transitions="false">
									{{tag}}
								</el-tag>
								<p>星级：<span class='el-icon-star-on' v-if="star == 0" style="color: blue;">一般</span>
									<span class='el-icon-star-on' v-if="star == 1" style="color: darkgreen;">较重要</span>
									<span class='el-icon-star-on' v-if="star == 2" style="color: orange;">重要</span>
									<span class='el-icon-star-on' v-if="star == 3" style="color: red;">特别重要</span></p>
								<el-button type="primary" style="margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
								v-if="role != 3" @click="editTask()">编辑子任务详情</el-button>
								<el-button type="primary" style="float: right;margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
								v-if="stateOperation" icon="el-icon-check" @click="editTaskState()">确定修改</el-button>
							</el-dialog>
						</template>
					</el-table-column>
				</el-table>
				<el-pagination @current-change="handleCurrentChange" :current-page.sync="currentPage3" :page-size="10" layout="prev, pager, next, jumper"
				 :total="page">
				</el-pagination>
			</el-main>
			<el-aside width="250px">
				<div id="statusChart" style="height:150px"></div>
				<div id="starChart" style="height:150px"></div>
				<div id="levelChart" style="height:150px"></div>
				<div style="margin-top: 20px">
					<el-radio-group v-model="dayName" @change="getSubTaskEchartsAmount()" size="mini">
					<el-radio-button label="一周"></el-radio-button>
					<el-radio-button label="30天"></el-radio-button>
					<el-radio-button label="半年"></el-radio-button>
					<el-radio-button label="一年"></el-radio-button>
					</el-radio-group>
				</div>
				<p>
					<b>拥有者：</b>
					<p>{{creator}}</p>
				</p>
				<p>
					<b>管理者：</b>
					<p v-for="manager in managers">{{manager}}</p>
				</p>
				<p>
					<b>参与者：</b>
					<p v-for="employee in employees">{{employee}}</p>
				</p>
				<el-button type="primary" style="margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
				v-if="role != 3" icon="el-icon-edit" @click="editParticipator()">任务人员编辑</el-button>
			</el-aside>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
	var echarts = require("echarts/lib/echarts")
	require('echarts/lib/component/tooltip')
	require('echarts/lib/component/title')
	require('echarts/lib/chart/pie')
	export default {
		data() {
			return {
				tableData: [],
				multipleSelection: [],
				selectedDataTemp: [],
				page: 1,
				role: 0,
				dialogVisible: false,
				taskID: null,
				id: null,
				name: null,
				content: null,
				deadline: null,
				status: null,
				startTime: null,
				level: null,
				creator:null,
				creatorID:null,
				tag: [],
				star: null,
				currentPage3: 1,
				stateOperation: null,
				options:[
					{
						value:0,
						label:'计划中'
					},
					{
						value:1,
						label:'执行中'
					},
					{
						value:2,
						label:'已完成'
					},
					{
						value:3,
						label:'已取消'
					},
				],
				value:null,
				managers:[],
				employees:[],
				urlManagers:[],
				urlEmployees:[],
				dayName:"一周",
				day:0,
				planningStatusAmount:0,
				doingStatusAmount:0,
				completedStatusAmount:0,
				cancelledStatusAmount:0,
				normalStarAmount:0,
				relativelyImportantStarAmount:0,
				importantStarAmount:0,
				particularlyImportantStarAmount:0,
				level0Amount:0,
				level1Amount:0,
				level2Amount:0,
				level3Amount:0
			}
		},
		methods: {
			subTaskStateEcharts(){
				var statusChart = echarts.init(document.getElementById('statusChart'));
				statusChart.setOption({
					title:{
						 text: '任务状态统计',
						 left: 'center',
						 textStyle: {
							 fontSize:17
						 }
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						top: '17%',
						left: 'center',
						itemWidth: 17,   
            			itemHeight: 8,
						textStyle: {
							 fontSize:9.5
						}
					},
					color:['#3399FF','#FF6600','#009933','#C0C0C0'],
					series: [
						{
							name: '状态',
							type: 'pie',
							radius: ['55%','65%'],
							center: ['50%','65%'],
							avoidLabelOverlap: false,
							label: {
								show: false,
								position: 'center',
							},
							emphasis: {
								label: {
									show: true,
									fontSize: '8',
									fontWeight: 'bold'
								}
							},
							labelLine: {
								show: false
							},
							data: [
								{value:this.planningStatusAmount, name: '计划中'},
								{value:this.doingStatusAmount, name: '执行中'},
								{value:this.completedStatusAmount, name: '已完成'},
								{value:this.cancelledStatusAmount, name: '已取消'}
							]
						}
					]
				});
				var starChart = echarts.init(document.getElementById('starChart'));
				starChart.setOption({
					title:{
						 text: '任务星级统计',
						 left: 'center',
						 textStyle: {
							 fontSize:17
						 }
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						top: '17%',
						left: 'center',
						itemWidth: 17,   
            			itemHeight: 8,
						textStyle: {
							 fontSize:9.5
						}
					},
					color:['blue','darkgreen','orange','red'],
					series: [
						{
							name: '星级',
							type: 'pie',
							radius: ['55%','65%'],
							center: ['50%','65%'],
							avoidLabelOverlap: false,
							label: {
								show: false,
								position: 'center',
							},
							emphasis: {
								label: {
									show: true,
									fontSize: '8',
									fontWeight: 'bold'
								}
							},
							labelLine: {
								show: false
							},
							data: [
								{value:this.normalStarAmount, name: '一般'},
								{value:this.relativelyImportantStarAmount, name: '较重要'},
								{value:this.importantStarAmount, name: '重要'},
								{value:this.particularlyImportantStarAmount, name: '特别重要'}
							]
						}
					]
				});
				var levelChart = echarts.init(document.getElementById('levelChart'));
				levelChart.setOption({
					title:{
						 text: '任务级别统计',
						 left: 'center',
						 textStyle: {
							 fontSize:17
						 }
					},
					tooltip: {
						trigger: 'item'
					},
					legend: {
						top: '17%',
						left: 'center',
						itemWidth: 17,   
            			itemHeight: 8,
						textStyle: {
							 fontSize:9.5
						}
					},
					series: [
						{
							name: '级别',
							type: 'pie',
							radius: ['55%','65%'],
							center: ['50%','65%'],
							avoidLabelOverlap: false,
							label: {
								show: false,
								position: 'center',
							},
							emphasis: {
								label: {
									show: true,
									fontSize: '8',
									fontWeight: 'bold'
								}
							},
							labelLine: {
								show: false
							},
							data: [
								{value:this.level0Amount, name: '等级0'},
								{value:this.level1Amount, name: '等级1'},
								{value:this.level2Amount, name: '等级2'},
								{value:this.level3Amount, name: '等级3'}
							]
						}
					]
				});
			},
			getSubTaskEchartsAmount(){
				switch(this.dayName){
					case '一周':
						this.day=7;
						break;
					case '30天':
						this.day=30;
						break;
					case '半年':
						this.day=183;
						break;
					case '一年':
						this.day=365;
						break;
				}
				axios.get('/api/remoteTask/remoteSubTaskChart', {
						params: {
							'day':this.day,
							'taskID': localStorage.getItem('taskID')
							
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						this.level0Amount=0;
						this.level1Amount=0;
						this.level2Amount=0;
						this.level3Amount=0;
						this.planningStatusAmount=0;
						this.doingStatusAmount=0;
						this.completedStatusAmount=0;
						this.cancelledStatusAmount=0;
						this.normalStarAmount=0;
						this.relativelyImportantStarAmount=0;
						this.importantStarAmount=0;
						this.particularlyImportantStarAmount=0;
						var response = res.data.data;
						var a = eval("("+response + ")");
						var i=0;
						for(i=0;i<a.chartLevelBo.length;i++){
							switch(a.chartLevelBo[i].level){
								case 0:
									this.level0Amount=a.chartLevelBo[i].levelAmount;
									break;
								case 1:
									this.level1Amount=a.chartLevelBo[i].levelAmount;
									break;
								case 2:
									this.level2Amount=a.chartLevelBo[i].levelAmount;
									break;
								case 3:
									this.level3Amount=a.chartLevelBo[i].levelAmount;	
									break;
							}
						};
						for(i=0;i<a.chartStarBo.length;i++){
							switch(a.chartStarBo[i].star){
								case 0:
									this.normalStarAmount=a.chartStarBo[i].starAmount;
									break;
								case 1:
									this.relativelyImportantStarAmount=a.chartStarBo[i].starAmount;
									break;
								case 2:
									this.importantStarAmount=a.chartStarBo[i].starAmount;
									break;
								case 3:
									this.particularlyImportantStarAmount=a.chartStarBo[i].starAmount;	
									break;
							}
						};
						for(i=0;i<a.chartStatusBo.length;i++){
							switch(a.chartStatusBo[i].status){
								case 0:
									this.planningStatusAmount=a.chartStatusBo[i].statusAmount;
									break;
								case 1:
									this.doingStatusAmount=a.chartStatusBo[i].statusAmount;
									break;
								case 2:
									this.completedStatusAmount=a.chartStatusBo[i].statusAmount;
									break;
								case 3:
									this.cancelledStatusAmount=a.chartStatusBo[i].statusAmount;	
									break;
							}
						};
						this.subTaskStateEcharts();
					})
			},
			handleSelectionChange(val) {
				let that_ = this;
				that_.multipleSelection = val;
				let a = true;
				that_.selectedDataTemp = that_.multipleSelection;
				that_.selectedDataTemp = that_.multipleSelection.filter(item => {
					return !item.a
				});
			},
			handleCurrentChange() {
				
				axios.get('/api/remoteTask/remoteSubTaskList', {
						params: {
							/* 测试用子任务ID */
							'taskID': localStorage.getItem('taskID'),
							'page': this.currentPage3
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						for(var i=0;i<a.length;i++){
							a[i].deadline = new Date(a[i].deadline).toLocaleDateString().replace(/\//g, '-');
							a[i].startTime = new Date(a[i].startTime).toLocaleDateString().replace(/\//g, '-');
						}
						this.tableData = a;
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			getDefaultSubTask() {
				var url = window.location.href.split('?')[1].split('&');
				var taskID = url[0].split('=')[1];
				this.creatorID = url[1].split('=')[1];
				var nodeID = url[2].split('=')[1];
				localStorage.setItem('nodeID',nodeID);
				localStorage.setItem('taskID',taskID);
				
				axios.get('/api/remoteTask/remoteSubTaskNumber', {
						params: {
							/* 测试用子任务ID */
							'taskID': localStorage.getItem('taskID'),
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.page = response * 10;
					})
					.catch(function(error) {
						alert(error);
					});

				axios.get('/api/remoteTask/remoteSubTaskList', {
						params: {
							/* 测试用子任务ID */
							'taskID': localStorage.getItem('taskID'),
							'page': 1
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						
						
						for(var i=0;i<a.length;i++){
							a[i].deadline = new Date(a[i].deadline).toLocaleDateString().replace(/\//g, '-');
							a[i].startTime = new Date(a[i].startTime).toLocaleDateString().replace(/\//g, '-');
						}
						this.tableData =a ;
					})
					.catch(function(error) {
						console.log(error);
					});


				axios.get('/api/remoteTask/userRole', {
						params: {
							/* 测试用子任务ID */
							'taskID': localStorage.getItem('taskID'),
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.role = response;
					})
					.catch(function(error) {
						console.log(error);
					});

				axios.get('/api/remoteTask/allowUserChangeStatus', {
						params: {
							/* 测试用子任务ID */
							'taskID': localStorage.getItem('taskID'),
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.stateOperation = response;
					})
					.catch(function(error) {
						console.log(error);
					});
					
					axios.get('/api/remoteTask/remoteTaskMember', {
							params: {
								/* 测试用子任务ID */
								'taskID':localStorage.getItem('taskID') ,
							},
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							var response = eval(res.data.data);
							for(var i =0;i<response.length;i++){
								if(response[i].userID == this.creatorID && response[i].nodeID == nodeID ){
									this.creator = response[i].username;
									continue;
								}
								if(response[i].admin && response[i]){
									this.managers.push(response[i].username);
									this.urlManagers.push(response[i]);
								}
								else{
									this.employees.push(response[i].username);
									this.urlEmployees.push(response[i]);
								}
							}
						})
						.catch(function(error) {
							console.log(error);
						});
			},
			deleteTask(index) {
				this.$confirm('删除该子任务, 是否确定?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/remoteTask/remoteSubTask', {
							params: {
								'id': index.id,
							},

							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(function(response) {
							this.$alert('删除成功', '提示', {
								confirmButtonText: '确定',
							});
							window.location.reload();
						})
						.catch(function(error) {
							console.log(error);
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			deleteTasks() {
				var selectedArray = new Array();
				for (var i = 0; i < this.multipleSelection.length; i++) {
					selectedArray.push(this.multipleSelection[i].id);
				}
				

				this.$confirm('删除这些子任务, 是否确定?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/remoteTask/remoteSubTask', {
							params: {
								
								'id': selectedArray,
								/* 测试用子任务ID */
								'taskID':localStorage.getItem('taskID')
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
								 this.$alert('删除成功', '提示', {
								          confirmButtonText: '确定',
								        });
							} else {
								this.$alert('删除失败', '提示', {
								         confirmButtonText: '确定',
								       });
							}
						})
						.catch(function(error) {
							console.log(error);
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			TaskDetail(detail) {
				localStorage.setItem('taskDetail',JSON.stringify(detail));
				this.taskID = detail.taskID;
				this.id = detail.id;
				this.name = detail.name;
				this.content = detail.content;
				this.deadline = detail.deadline;
				this.status = detail.status;
				this.value = detail.status;
				this.startTime = detail.startTime;
				this.level = detail.level;
				this.tag = detail.tag.replace('{', '').replace('}', '').split(',');
				this.star = detail.star;
				this.dialogVisible = true;
			},
			editTaskState(){
				var a = new URLSearchParams();
				a.append("status",this.value);
				a.append("taskID", localStorage.getItem('taskID'));
				a.append("id",this.id);
				axios.put('/api/remoteTask/remoteSubTaskStatus', a, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							if (res.data.ret == 1) {
								this.$alert('修改成功', '提示', {
								         confirmButtonText: '确定',
							   }).then(() => {
							  window.location.reload();
							  });
							} else{
								this.$alert('权限不足', '提示', {
								         confirmButtonText: '确定',
								       });
									   window.location.reload();
							}
						});
			},
			editTask(){
				 this.$router.push({path:'/editJointSubTask'});
			},
			addTask(){
				localStorage.setItem('add',true);
				this.$router.push({path:'/editJointSubTask'});
			},
			editParticipator(){
				this.$router.push({path:'/editJoinParticipator'});
			},
		},
		beforeMount: function() {
			this.getDefaultSubTask();
			this.getSubTaskEchartsAmount();
		}
	}
</script>

<style>
	.icon{
		height: 17px;
		width: 17px;
	}
</style>
