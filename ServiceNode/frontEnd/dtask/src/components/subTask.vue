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
					<el-table-column prop="name" label="子子任务名称" width="150px">
					</el-table-column>
					<el-table-column prop="startTime" label="开始时间">
					</el-table-column>
					<el-table-column prop="deadline" label="截止时间">
					</el-table-column>
					<el-table-column prop="level" label="级别" width="50px">
					</el-table-column>
					<el-table-column prop="status" label="状态" width="100px">
						<template slot-scope="scope">
							<p v-if="scope.row.status == 0">计划中</p>
							<p v-if="scope.row.status == 1">执行中</p>
							<p v-if="scope.row.status == 2">已完成</p>
							<p v-if="scope.row.status == 3">已取消</p>
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
			<el-aside width="200px">
				<b>拥有者：</b>
				<p>{{creator}}</p>
				<b>管理者：</b>
				<p v-for="manager in managers">{{manager}}</p>
				<b>参与者：</b>
				<p v-for="employee in employees">{{employee}}</p>
				<el-button type="primary" style="margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
				v-if="role != 3" icon="el-icon-edit" @click="editParticipator()">任务人员编辑</el-button>
			</el-aside>
		</el-container>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
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
				urlEmployees:[]
			}
		},
		methods: {
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
				axios.get('/api/localTask/localSubTaskList', {
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
				localStorage.setItem('taskID',taskID);
				console.log(url);
				
				axios.get('/api/localTask/localSubTaskNumber', {
						params: {
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

				axios.get('/api/localTask/localSubTaskList', {
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


				axios.get('/api/localTask/userRole', {
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

				axios.get('/api/localTask/allowUserChangeStatus', {
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
					
					axios.get('/api/localTask/LocalTaskMember', {
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
							console.log(response);
							for(var i =0;i<response.length;i++){
								if(response[i].userID == this.creatorID){
									this.creator = response[i].username;
								}
								if(response[i].admin){
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
					axios.delete('/api/localTask/localSubTask', {
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
					axios.delete('/api/localTask/localSubTask', {
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
				/* 测试用子任务ID */
				a.append("taskID", localStorage.getItem('taskID'));
				a.append("id",this.id);
				axios.put('/api/localTask/localSubTaskStatus', a, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							if (res.data.ret == 1) {
								this.$alert('修改成功', '提示', {
								         confirmButtonText: '确定',
										 callback: action => {
										             window.location.reload();
										           }
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
				 this.$router.push({path:'/editTask'});
			},
			addTask(){
				localStorage.setItem('add',true);
				this.$router.push({path:'/editTask'});
			},
			editParticipator(){
				this.$router.push({path:'/EditParticipator'});
			},
		},
		beforeMount: function() {
			this.getDefaultSubTask();
		}
	}
</script>

<style>
</style>
