<template>
	<div>
		<h3>该角色拥有的权限</h3>
		<el-button plain style="float: right;" @click="notOwnedPermission()">查看未拥有的权限</el-button>
		<el-table :data="ownedPermission" tooltip-effect="dark" style="width: 100%">
			<el-table-column prop="id" label="权限ID" width="120">
			</el-table-column>
			<el-table-column prop="poperate" label="操作">
			</el-table-column>
			<el-table-column prop="pobject" label="操作对象">
			</el-table-column>
			<el-table-column prop="pcategory" label="权限类别">
			</el-table-column>
			<el-table-column prop="pdescribe" label="权限描述">
			</el-table-column>
			<el-table-column label="权限更改">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="deletePermission(scope.row)">取消该权限</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div v-if="notOwnedStates">
		<h3>该角色未拥有的权限</h3>
		<el-table :data="notOwnedPermissions" tooltip-effect="dark" style="width: 100%">
			<el-table-column prop="id" label="权限ID" width="120">
			</el-table-column>
			<el-table-column prop="poperate" label="操作">
			</el-table-column>
			<el-table-column prop="pobject" label="操作对象">
			</el-table-column>
			<el-table-column prop="pcategory" label="权限类别">
			</el-table-column>
			<el-table-column prop="pdescribe" label="权限描述">
			</el-table-column>
			<el-table-column label="权限更改">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addPermission(scope.row)">添加权限</el-button>
				</template>
			</el-table-column>
		</el-table>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				ownedPermission:[],
				permissionNumber:-1,
				wholePermission:[],
				notOwnedPermissions:[],
				notOwnedStates:false,
			}
		},
		methods:{
			getWholePermission(){
				
				axios.get('api/role/permission', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.permissionNumber = response.length;
						this.wholePermission = eval(response);
						this.getRolePermission();
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			
			
			getRolePermission(){
				axios.get('api/role/rolePermission', {
						params: {
							roleID:localStorage.getItem("roleID")
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.ownedPermission = eval(response);
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			notOwnedPermission(){
				var i=0,j=0,k=0;
				for(i=0;i<this.permissionNumber;i++){
					if(this.wholePermission[i].id == this.ownedPermission[j].id){
							if(j<this.ownedPermission.length-1)
								j++;
					}
					else{
						this.notOwnedPermissions[k]=this.wholePermission[i];
						k++;
					}
				}
				this.notOwnedStates = true;
			},
			deletePermission(index){
				axios.delete('api/role/rolePermission', {
						params: {
							roleID: localStorage.getItem("roleID"),
							permissionID: index.id,
						},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(function(response) {
				
						if (response.data.ret == 1) {
							alert("删除成功");
							location.reload();
						}
						else{
							alert("删除失败请重试");
						}
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			
			addPermission(index){
				var parmas = new URLSearchParams();
				parmas.append("roleID",localStorage.getItem("roleID"));
				parmas.append("permissionID",index.id);
				axios.post('api/role/rolePermission', parmas, {
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						if (response.ret == 1) {
							alert("添加成功");
							location.reload();
						} else {
							alert("添加失败");
						}
				
					});
			}
		},
		beforeMount: function() {
			this.getWholePermission();
		}
		}
</script>

<style>
	
</style>
