<template>
	<div>
		<el-button type="primary" style="float: right;margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
		 icon="el-icon-plus" @click="dialogVisible = true">添加人员至该任务</el-button>
		<el-dialog title="人员添加" :visible.sync="dialogVisible" width="30%">
			<el-form>
				<el-form-item label="部门:">
					<el-select v-model="value6" placeholder="请选择" @change="getUser()">
						<el-option v-for="item in options3" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="人员:">
					<el-select v-model="value5" placeholder="请选择">
						<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
			<el-button type="primary" @click="addParticipator()" icon="el-icon-check" style="background: #24375E;border: 0px ;">确认添加</el-button>
		</el-dialog>
		</br>
		<h3>管理人员列表</h3>
		<el-table :data="urlManagers" tooltip-effect="dark" style="width: 100%">
			<el-table-column prop="userID" label="管理员ID" width="150">
			</el-table-column>
			<el-table-column prop="nickname" label="管理员姓名">
			</el-table-column>
			<el-table-column label="操作" v-if="role == 1">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deleteParticipator(scope.row)">移出该任务</el-button>
					<el-button type="text" size="small" @click="changeRole(scope.row,false)">变更为普通参与人员</el-button>
				</template>
			</el-table-column>
		</el-table>
		<h3>普通参与人员列表</h3>
		<el-table :data="urlEmployees" tooltip-effect="dark" style="width: 100%">
			<el-table-column prop="userID" label="参与人ID" width="150">
			</el-table-column>
			<el-table-column prop="nickname" label="参与人姓名">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deleteParticipator(scope.row)">移出该任务</el-button>
					<el-button type="text" size="small" v-if="role == 1" @click="changeRole(scope.row,true)">变更为管理人员</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import axios from 'axios';
	import qs from 'qs';
	export default {
		data() {
			return {
				urlManagers: [],
				urlEmployees: [],
				dialogVisible: false,
				value6: null,
				value5: null,
				options3: [],
				options: [],
				role: null,
			}
		},
		methods: {
			deleteParticipator(person) {
				this.$confirm('此操作会将该人员移出此任务, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/localTask/LocalTaskMember', {
							params: {
								'userID': person.userID,
								/* 测试用子任务ID */
								'taskID': localStorage.getItem('taskID')
							},
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							if (res.data.ret == 1) {
								this.$alert('移出成功', '提示', {
									confirmButtonText: '确定',
								});
							} else {
								this.$alert('该人员不在此任务中', '提示', {
									confirmButtonText: '确定',
								});
							}
							window.location.reload();
						})
						.catch(function(error) {
							console.log(error);
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消移出'
					});
				});
			},
			changeRole(person, bool) {
				this.$confirm('此操作会将该人员的权限变更, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var a = new URLSearchParams();
					a.append("userID", person.userID);

					a.append("taskID", localStorage.getItem('taskID'));
					a.append('isAdmin', bool);
					axios.put('/api/localTask/Admin', a, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							if (res.data.ret == 1) {
								this.$alert('修改成功', '提示', {
									confirmButtonText: '确定',
								});
							} else {
								this.$alert('修改失败', '提示', {
									confirmButtonText: '确定',
								});
							}
							window.location.reload();
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消更改'
					});
				});
			},
			getParticipator() {
				/* 获得当前角色是不是拥有者 */
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

				axios.get('/api/localTask/LocalTaskMember', {
						params: {

							'taskID': localStorage.getItem('taskID'),
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						for (var i = 0; i < response.length; i++) {
							if (response[i].admin) {
								this.urlManagers.push(response[i]);
							} else {
								this.urlEmployees.push(response[i]);
							}
						}
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			getUser() {
				console.log(this.value6);
				axios.get('/api/user/userList', {
						params: {
							username: "",
							nickname: "",
							phone: "",
							email: "",
							onboardDateStart: "",
							onboardDateEnd: "",
							roleID: -1,
							departmentID: this.value6,
							birthdayStart: "",
							birthdayEnd: "",
							page: 1,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						this.options = [];
						a.forEach((res) => {
							this.options.push({
								value: res.id,
								label: res.nickname
							});
						})

						console.log(this.options);
					})
					.catch(function(error) {
						this.$alert('请求用户失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			getDepartment() {
				axios.get('/api/department/department', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						var a = eval(response);
						a.forEach((res) => {
							this.options3.push({
								value: res.id,
								label: res.departmentName
							});
						})
					})
					.catch(function(error) {
						this.$alert('请求部门失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			addParticipator() {
				if (this.value5 == null) {
					this.$alert('必须选择一位用户添加', '提示', {
						confirmButtonText: '确定',
					});
				} else {
					var a = new URLSearchParams();
					a.append('taskID', localStorage.getItem('taskID'));
					a.append('userID', this.value5);
					axios.post('/api/localTask/LocalTaskMember', a, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							if (res.data.ret == 1) {
								this.$alert('添加成功', '提示', {
									confirmButtonText: '确定',
									callback: action => {
										window.location.reload();
									}
								});
							} else {
								this.$alert('此人员已在该任务', '提示', {
									confirmButtonText: '确定',
								});
							}

						});
				}

			}
		},
		beforeMount: function() {
			this.getParticipator();
			this.getDepartment();
		}
	}
</script>

<style>

</style>
