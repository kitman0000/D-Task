<template>
	<div>
			<el-button style="float: right;margin-top: 15px;" @click="deleteRoles()">删除所选角色</el-button>
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
			deleteRole(index){
				
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
				          			alert("删除成功");
				          		}
				          		else{
				          			alert("删除失败请重试");
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
					axios.put('/api/role/role', title, {
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
					axios.put('/api/role/role', title, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							alert("成功");
						})
				});
			},
			getRole(){
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
						console.log(error);
					});
			},
			//删除多个角色
			deleteRoles(){
				var selectedArray = new Array();
				for(var i=0;i<this.multipleSelection.length;i++){
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
				  		      return qs.stringify(params, { indices: false })
				  		    },
				  		headers: {
				  			"token": localStorage.getItem("token"),
				  		}
				  	})
				  	.then(function(response) {
				  
				  		if (response.data.ret == 1) {
				  			alert("删除成功");
				  		}
				  		else{
				  			alert("删除失败请重试");
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
			
			editPermission(index){
				localStorage.setItem("roleID",index.roleID);
				this.$router.push({path:"/PermissionOperation"});
			}
			
		},
		
		beforeMount: function() {
			this.getRole();
		}
	}
</script>

<style>
</style>
