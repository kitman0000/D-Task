<template>
	<div>
			<el-button style="margin-top: 15px;margin-left: 15px;" @click="handleChange()">反选</el-button>
			<el-button style="float: right;margin-top: 15px;" @click="addRole()">添加角色</el-button>
		<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
			<el-table-column type="selection" width="55"></el-table-column>
			<el-table-column prop="roleID" label="角色ID" width="120">
			</el-table-column>
			<el-table-column prop="roleName" label="角色名称">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deleteRole(scope.row)">删除角色</el-button>
					<el-button type="text" size="small" @click="editRoleID(scope.row)">编辑角色ID</el-button>
					<el-button type="text" size="small" @click="editRoleName(scope.row)">编辑角色名</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				tableData: [],
				multipleSelection: [],
				selectedDataTemp: [],
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
			deleteRole(index){
				axios.delete('/api/role/role', {
						params: {
							roleID: index.roleID,
						},
				
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(function(response) {
				
						if (response == "ROLE_DELETE_SUCCESS") {
							alert("删除成功");
						}
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			addRole() {
				var addroleID;
				this.$prompt('请输入角色id', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					addroleID = value;
					this.$message({
						type: 'success',
						message: 'id: ' + value
					});
					var roleid = new URLSearchParams();
					roleid.append("id", addroleID);
					axios.post('/api/role/role', roleid, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(function(response) {
							alert("成功");
						})
						.catch(function(error) {
							alert("请求失败！");
						});
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '失败'
					});
				});
			},
			
			editRoleID(index){
				var newID;
				this.$prompt('请输入新ID', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					newID = value;
					this.$message({
						type: 'success',
						message: 'ID: ' + value
					});
					var title = new URLSearchParams();
					title.append("roleName", index.roleName);
					title.append("roleID", newID);
					axios.post('/api/role/role', title, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							alert("成功");
						})
				});
			},
			
			editRoleName(index){
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
					axios.post('/api/role/role', title, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							alert("成功");
						})
				});
			},
			
		},
		
		created: function() {
			axios.get('/api/role/role', {
					params: {},
					headers: {
						"token": localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					this.tableData = eval(response);
				})
				.catch(function(error) {
					console.log(error);
				});
		}
	}
</script>

<style>
</style>
