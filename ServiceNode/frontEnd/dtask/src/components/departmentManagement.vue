<template>
	<div>
		<el-button style="float: right;margin-top: 15px;" @click="addDepartment()">添加部门</el-button>
		<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%">
			<el-table-column prop="id" label="部门ID号" width="120">
			</el-table-column>
			<el-table-column prop="departmentName" label="部门名称">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deleteDepartment(scope.row)">删除该部门</el-button>
					<el-button type="text" size="small" @click="editDepartmentName(scope.row)">编辑部门名称</el-button>
				</template>
			</el-table-column>
		</el-table>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				tableData: [],
			}
		},
		methods: {
			addDepartment() {
				var DepartmentName;
					this.$prompt('请输入部门名', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
					}).then(({
						value
					}) => {
						DepartmentName = value;
						var departmentName = new URLSearchParams();
						departmentName.append("name", DepartmentName);
						axios.post('/api/department/department', departmentName, {
								headers: {
									"token": localStorage.getItem("token"),
								}
							})
							.then(function(response) {
								if(response.data.ret == 2){
									alert("新部门创建失败");
								}
								else{
									window.location.reload();
								}
							})
							.catch(function(error) {
								alert("请求失败！");
							});
					}).catch(() => {
						this.$message({
							type: 'info',
							message: '取消输入'
						});
					});
			},
			
			getDepartment(){
				axios.get('/api/department/department', {
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
			deleteDepartment(index){
				this.$confirm('是否删除该部门？', '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {
				          axios.delete('/api/department/department', {
				          		params: {
				          			id: index.id,
				          		},
				          
				          		headers: {
				          			"token": localStorage.getItem("token"),
				          		}
				          	})
				          	.then(function(response) {
				          
				          		if (response.data.ret == 1) {
				          			alert("删除成功");
									window.location.reload();
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
			editDepartmentName(index){
				var newName;
				this.$prompt('请输入新名称', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					newName = value;
					var name = new URLSearchParams();
					name.append("id", index.id);
					name.append("name", newName);
					axios.put('/api/department/department', name, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res=> {
							alert("更改名称成功!");
							window.location.reload();
						})
				});
			},
		},
		
		beforeMount:function(){
			this.getDepartment();
		}
	}
</script>

<style>
</style>
