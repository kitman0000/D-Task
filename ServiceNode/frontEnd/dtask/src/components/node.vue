<template>
	<div>
		<el-button type="primary" @click="changit()"  style="background: #24375E;border: 0px ;margin-left: 10px;">展现形式变化</el-button>
		<el-button type="primary"  @click="setRoot()" style="background: #24375E;border: 0px ;margin-left: 10px;float: right;">将自身设为根节点</el-button>
		<el-button type="primary"  @click="unbind()" style="background: #24375E;border: 0px ;margin-left: 10px;float: right;">解绑自身</el-button>
		<el-table :data="datatest" tooltip-effect="dark" style="width: 100%" v-if="!change">
			<el-table-column prop="id" label="id" width="120px">
			</el-table-column>
			<el-table-column prop="nodeName" label="节点名">
			</el-table-column>
			<el-table-column prop="inheritRp" label="继承关系">
			</el-table-column>
			<el-table-column prop="parentNode" label="父节点">
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" v-if="scope.row.id != nowNode" @click="askBinding(scope.row)">申请绑定</el-button>
					
				</template>
			</el-table-column>
		</el-table>
		<el-tree :data="data4" :props="defaultProps" v-if="change" style="margin-top: 15px;margin-left: 35%;margin-right: 35%;" :render-content="renderContent" default-expand-all
  :expand-on-click-node="false">></el-tree>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				datatest: [],
				nowNode: 0,
				change:true,
				data4:[],
			}
		},
		methods: {
			getAllNode() {
				axios.get('/api/bindingCl/allNodes', {
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.datatest = eval(response);
						console.log(this.datatest);
						for (var i = 0; i < this.datatest.length; i++) {
							var newNode;
							if (this.datatest[i].inheritRp.split(':').length == 1) {
								newNode = {
									id: this.datatest[i].id,
									label: this.datatest[i].nodeName,
									children: []
								};
								this.data4.push(newNode)
							} else if (this.datatest[i].inheritRp.split(':').length == 2) {
								newNode = {
									id: this.datatest[i].id,
									label: this.datatest[i].nodeName,
									children: []
								};
								this.data4[0].children.push(newNode);
							} else if (this.datatest[i].inheritRp.split(':').length == 3) {
								newNode = {
									id: this.datatest[i].id,
									label: this.datatest[i].nodeName,
									children: []
								};
								for (var k = 0; k < this.data4[0].children.length + 1; k++) {
									if (this.data4[0].children[k].id == this.datatest[i].inheritRp.split(':')[1]) {
										this.data4[0].children[k].children.push(newNode);
									}
									if (k == this.data4[0].children.length) {
										eNode.push(this.datatest[i]);
									}
								}
							} else if (this.datatest[i].inheritRp.split(':').length == 4) {
								newNode = {
									id: this.datatest[i].id,
									label: this.datatest[i].nodeName,
									children: []
								};
								for (var k = 0; k < this.data4[0].children.length + 1; k++) {
									for (var m = 0; m < this.data4[0].children[k].length + 1; m++) {
										if (this.data4[0].children[k].id == this.datatest[i].inheritRp.split(':')[1] && this.data4[0].children[k].children[
												m].id == this.datatest[i].inheritRp.split(':')[2]) {
											this.data4[0].children[k].children[m].children.push(newNode);
										}
										if (m == this.data4[0].children[k].length) {
											this.eNode.push(this.datatest[i]);
										}
									}
									if (k == this.data4[0].children.length) {
										this.eNode.push(this.datatest[i]);
									}
								}
							}
						}
						for (var i = 0; i < this.eNode.length; i++) {
							if (this.eNode[i].inheritRp.split(':').length == 3) {
								var newNode = {
									id: this.eNode[i].id,
									label: this.eNode[i].nodeName,
									children: []
								};
								for (var k = 0; k < this.data4[0].children.length + 1; k++) {
									if (this.data4[0].children[k].id == this.eNode[i].inheritRp.split(':')[1]) {
										this.data4[0].children[k].children.push(newNode);
									}
								}
							} else if (this.eNode[i].inheritRp.split(':').length == 4) {
								newNode = {
									id: this.eNode[i].id,
									label: this.eNode[i].nodeName,
									children: []
								};
								for (var k = 0; k < this.data4[0].children.length + 1; k++) {
									for (var m = 0; m < this.data4[0].children[k].length + 1; m++) {
										if (this.data4[0].children[k].id == this.eNode[i].inheritRp.split(':')[1] && this.data4[0].children[k].children[
												m].id == this.datatest[i].inheritRp.split(':')[2]) {
											this.data4[0].children[k].children[m].children.push(newNode);
										}
										if (m == this.data4[0].children[k].length) {
											this.eNode.push(this.eNode[i]);
										}
									}
									if (k == this.data4[0].children.length) {
										this.eNode.push(this.eNode[i]);
									}
								}
							}
						}
					})
					.catch(err => {

					});
			},
			askBinding(index) {
				var id = new URLSearchParams();
					id.append("requestBindID", index.id);
					axios.post('/api/bindingCl/askBinding', id, {
							headers: {
								"token": localStorage.getItem("token"),
							}
						})
						.then(res => {
							if(res.data.ret == 1){
								alert('成功');
							}
							else if(res.data.ret == 1){
								alert('系统错误');
							}
							else{
								alert('申请绑定自身');
							}
						});
			},
			setRoot() {
				axios.post('/api/bindingCl/setRoot', '',{
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						if(res.data.ret == 1){
							alert('成功');
						}
						else if(res.data.ret == 2){
							alert('失败（根节点已存在）');
						}
						else{
							alert('其他错误');
						}
					});


			},
			unbind() {
				this.$confirm('解绑自身会同时解绑本节点下的所有子节点, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$confirm('最后一次提示：解绑自身会同时解绑本节点下的所有子节点, 是否继续?', '提示', {
					          confirmButtonText: '确定',
					          cancelButtonText: '取消',
					          type: 'warning'
					        }).then(() => {
					          axios.post('/api/bindingCl/unbind','',{
					          		headers: {
					          			"token": localStorage.getItem("token"),
					          		}
					          	})
					          	.then(res => {
					          		if(res.data.ret == 1){
					          			alert('成功');
					          		}
					          		else if(res.data.ret == 2){
					          			alert('失败（自身未被绑定）');
					          		}
					          		else{
					          			alert('其他错误');
					          		}
					          	});
					        }).catch(() => {
					          this.$message({
					            type: 'info',
					            message: '已取消删除'
					          });          
					        });
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			getNode(){
				axios.get('/api/bindingCl/nodeID', {
						params: {},
						headers: {
							"token": localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data.data;
						this.nowNode = response;
					})
					.catch(function(error) {
						console.log(error);
					});
			},
			changit(){
				this.change = !this.change;
			},
			renderContent(h, { node, data, store }) {
			        return (
			          <span style=" flex:1;align-items: center; justify-content: space-between; font-size: 14px; padding-right: 8px;">
			            <span>
			              <span>{node.label}</span>
			            </span>
			            <span>
			              <el-button style="font-size: 12px;margin-left:25px" type="text" on-click={ () => this.askBinding(data) }>申请绑定</el-button>
			            </span>
			          </span>);
			      }
		},
		beforeMount: function() {
			this.getAllNode();
			this.getNode();
		},
	}
</script>

<style>
</style>