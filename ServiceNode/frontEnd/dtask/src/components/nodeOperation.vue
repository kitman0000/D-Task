<template>
	<div>
		<el-table :data="tableData" tooltip-effect="dark" style="width: 100%" >
			<el-table-column prop="requestNodeName" label="请求所要绑定节点的名称">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="handleBinding(scope.row,1)">通过该申请</el-button>
					<el-button type="text" size="small" @click="handleBinding(scope.row,0)">拒绝该申请</el-button>
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
				tableData:[],
			}
		},
		methods:{
			getAllNode(){
				
				axios.get('/api/bindingCl/bindRequest', {
					headers: {
						"token": localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data.data;
					this.tableData = eval(response);
					console.log(response);
				})
				.catch(err => {
					this.$alert('请求失败', '提示', {
					         confirmButtonText: '确定',
					       });
				});
			},
			handleBinding(index,accept){
				var parmas = new URLSearchParams();
				parmas.append("request",index.id);
				if(accept){
					parmas.append("accept",true);
				}
				else{
					parmas.append("accept",false);
				}
				axios.post('/api/bindingCl/handleBinding', parmas, {
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(function(response) {
						this.$alert('请求成功', '提示', {
						         confirmButtonText: '确定',
						       });
					})
					.catch(function(error) {
						this.$alert('请求失败', '提示', {
						         confirmButtonText: '确定',
						       });
					});
			},
		},
		beforeMount:function() {
			this.getAllNode();
		}
		}
	
</script>

<style>
</style>
