<template>
	<div>
		<el-button type="primary" style="float: right;margin-top: 15px;background: #24375E;border: 0px ;" icon="el-icon-delete"
		 @click="deleteRoles()">删除所选角色</el-button>
		<el-button type="primary" style="float: right;margin-top: 15px;margin-right: 10px;background: #24375E;border: 0px ;"
		 icon="el-icon-plus" @click="addRole()">添加角色</el-button>
		<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="roleID" label="角色ID" width="120">
			</el-table-column>
			<el-table-column prop="roleName" label="角色名称">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deleteRole(scope.row)">删除角色</el-button>
					<el-button type="text" size="small" @click="editRoleName(scope.row)">编辑角色名</el-button>
					<el-button type="text" size="small" @click="editPermission(scope.row)">查看权限</el-button>
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
				tableData: [],
				multipleSelection: [],
				selectedDataTemp: [],
			}
		},

		methods: {
			//反选方法(测试结果失败)
			handleSelectionChange(val) {
				let that_ = this;
				that_.multipleSelection = val;
				let a = true;
				that_.selectedDataTemp = that_.multipleSelection;
				that_.selectedDataTemp = that_.multipleSelection.filter(item => {
					return !item.a
				});
			},
			handleChange() {
				let that_ = this;
				for (let i = that_.multipleSelection.length - 1; i >= 0; i--) {
					that_.multipleSelection[i].a = true;
				}
				that_.multipleSelection.forEach(row => {
					that_.$refs.multipleTable.toggleRowSelection(row, false)
				});
				that_.selectedDataTemp.forEach(row => {
					if (!row.a) {
						that_.$refs.multipleTable.toggleRowSelection(row)
					}
				});
				for (let y = that_.selectedDataTemp.length - 1; y >= 0; y--) {
					if (that_.selectedDataTemp[y].a) {
						delete that_.selectedDataTemp[y].a;
					}
				}
			},
			//删除单个角色
			deleteRole(index) {

				this.$confirm('删除该角色, 是否确定?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/role/role', {
							params: {
								roleID: index.roleID,
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
							this.$alert('请求失败', '提示', {
								confirmButtonText: '确定',
							});
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});


			},
			addRole() {
				var addroleName;
				this.$prompt('请输入角色名', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					addroleName = value;
					this.$message({
						type: 'success',
						message: 'id: ' + value
					});
					var roleid = new URLSearchParams();
					roleid.append("roleName", addroleName);
					axios.post('/api/role/role', roleid, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(function(response) {
							this.$alert('添加成功', '提示', {
								confirmButtonText: '确定',
								callback: action => {
									window.location.reload();
								}
							});
						})
						.catch(function(error) {
							this.$alert('请求失败', '提示', {
								confirmButtonText: '确定',
							});
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '失败'
					});
				});
			},
			editRoleName(index) {
				var newName;
				this.$prompt('请输入新名称', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					newName = value;
					this.$message({
						type: 'success',
						message: '名称: ' + value
					});
					var title = new URLSearchParams();
					title.append("roleID", index.roleID);
					title.append("roleName", newName);
					axios.put('/api/role/role', title, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							this.$alert('编辑成功', '提示', {
								confirmButtonText: '确定',
							});
							window.location.reload();
						})
				});
			},
			getRole() {
				axios.get('/api/role/role', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.tableData = eval(response);
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
							confirmButtonText: '确定',
						});
					});
			},
			//删除多个角色
			deleteRoles() {
				var selectedArray = new Array();
				for (var i = 0; i < this.multipleSelection.length; i++) {
					selectedArray.push(this.multipleSelection[i].roleID);
				}

				this.$confirm('删除这些角色, 是否确定?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					axios.delete('/api/role/role', {
							params: {
								roleID: selectedArray,
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
							this.$alert('请求失败', '提示', {
								confirmButtonText: '确定',
							});
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});



			},

			editPermission(index) {
				localStorage.setItem("roleID", index.roleID);
				this.$router.push({
					path: "/PermissionOperation"
				});
			}

		},

		beforeMount: function() {
			this.getRole();
		}
	}
</script>

<style>
</style>
