<template  class="page">
	<div>
		<!-- <el-button type="primary" @click="changit()"  style="background: rgba(89, 104, 136, 0.4);border: 2px solid #5f5f64 ;margin-left: 10px;">展现形式变化</el-button> -->
		<el-button type="primary"  @click="setRoot()" style="background: rgba(89, 104, 136, 0.4);border: 2px solid #5f5f64 ;margin-left: 10px;float: right;">将自身设为根节点</el-button>
		<el-button type="primary"  @click="unbind()" style="background: rgba(89, 104, 136, 0.4);border: 2px solid #5f5f64 ;margin-left: 10px;float: right;">解绑自身</el-button>
		<el-table :data="data" tooltip-effect="dark" style="width: 100%;" v-if="!change">
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
		<div style="width: 100%;" align="center">
		<vue2-org-tree v-if="change && data.length !=0" :NodeClass="NodeClass" :judge="judge" @on-node-click="NodeClick" style="margin-top: 15px;margin-left: 35%;margin-right: 35%;" @on-node-mouseover="mouseOver" :style="active" :data="TreeData"/>
		</br>
		<div v-if="change" style="margin-top: 50px;">
			<span style="color:rgb(122, 151, 196)">点击节点名称，申请绑定,蓝色节点为在线状态，灰色节点为离线状态</span>
		</div>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				nowNode: 0,
				change:true,
				data: [],
				max:0,
				array:[[]],
				TreeData:{},
				active:"",
				judge:{swtich:true},
				NodeClass:[
                    "online","offline"
                ],
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
						//创建一个二维数组 a[i][j] i表示第几层 root为第0层 每一层放节点的id方便后续遍历
						var response = res.data.data;
						this.data = eval(response);
						this.TreeData.id = this.data[0].id;
						this.TreeData.label = this.data[0].nodeName;
						// 设置在线状态
						if(this.data[0].online){
							newNode.swtich = "online";
						}
						this.TreeData.children = [];
						var tmp = 1;
						this.array[tmp-1] = new Array();
						for(var i = 0;i<this.data.length;i++){
							if(this.data[i].inheritRp.split(":").length == tmp){
								this.array[tmp-1].push(this.data[i].id);
							}
							else{
								tmp = this.data[i].inheritRp.split(":").length;
								this.array[tmp-1] = new Array();
								this.array[tmp-1].push(this.data[i].id);
							}
						}
						
						for(var i = 0;i<this.data.length;i++){							
							var newNode = {
								id: this.data[i].id,
								label: this.data[i].nodeName,
								children: []
							};
							
							// 设置在线状态
							if(this.data[i].online){
								newNode.swtich = "online";
							}

														
							if(this.data[i].inheritRp.split(":").length == 2){ //根节点的子节点直接挂载
								this.TreeData.children.push(newNode);
							}
							else{
								this.pushNode(this.data[i].inheritRp.split(":"), 1, this.data[i].inheritRp.split(":").length-1, newNode, this.TreeData.children); 
							}
						}

						this.toggleExpand(this.TreeData,true);
					})

					
			},
			//data 为节点信息
			NodeClick(e,data){  
			    this.askBinding(data);
			    
			},
			mouseOver(){
				this.active="cursor:pointer";
			},
			//通过递归从根节点一步一步向下寻找所在的枝处于上层的第几个位置，直到层数和继承关系字符串一一对应
			pushNode(arr, point, size, newNode, Tree){
				if(point == size){
					Tree.push(newNode);
				}
				else{
					for(var i = 0;i<this.array[point].length;i++){
						console.log(Tree);
						if(this.array[point][i] == arr[point]){	
							this.pushNode(arr, point+1, size, newNode, Tree[i].children);
							break;
						}
					}
				}
				
			},
			//请求绑定
			askBinding(index) {
				this.$confirm('是否要将自身绑定至该节点下?', '提示', {
				          confirmButtonText: '确定',
				          cancelButtonText: '取消',
				          type: 'warning'
				        }).then(() => {
				          var id = new URLSearchParams();
				          	id.append("requestBindID", index.id);
				          	axios.post('/api/bindingCl/askBinding', id, {
				          			headers: {
				          				"token": localStorage.getItem("token"),
				          			}
				          		})
				          		.then(res => {
				          			if(res.data.ret == 1){
				          				this.$alert('请求成功', '提示', {
				          				         confirmButtonText: '确定',
				          				       });
				          			}
				          			else if(res.data.ret == 2){
				          				this.$alert('系统错误', '提示', {
				          				         confirmButtonText: '确定',
				          				       });
				          			}
				          			else{
				          				this.$alert('不允许绑定自身', '提示', {
				          				         confirmButtonText: '确定',
				          				       });
				          			}
				          		});
				        }).catch(() => {
				          this.$message({
				            type: 'info',
				            message: '已取消绑定'
				          });          
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
							this.$alert('请求成功', '提示', {
							         confirmButtonText: '确定',
							       });
						}
						else if(res.data.ret == 2){
							this.$alert('请求失败（根节点存在）', '提示', {
							         confirmButtonText: '确定',
							       });
						}
						else{
							this.$alert('其他错误', '提示', {
							         confirmButtonText: '确定',
							       });
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
					          			this.$alert('解绑成功', '提示', {
					          			         confirmButtonText: '确定',
					          			       });
					          		}
					          		else if(res.data.ret == 2){
					          			this.$alert('解绑失败（自身未绑定）', '提示', {
					          			         confirmButtonText: '确定',
					          			       });
					          		}
					          		else{
					          			this.$alert('其他错误', '提示', {
					          			         confirmButtonText: '确定',
					          			       });
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
			toggleExpand(data, val) {
                var _this = this;
                if (Array.isArray(data)) {
                    data.forEach(function(item) {
						
                        _this.$set(item, "expand", val);
                        if (item.children) {
                           _this.toggleExpand(item.children, val);
                        }
                    });
                } else {
					
                    this.$set(data, "expand", val);
                    if (data.children) {
                        _this.toggleExpand(data.children, val);
                    }
                }
        	}
		},
		beforeMount: function() {
			this.getAllNode();
			this.getNode();
		},
		beforeCreate(){
			  document.querySelector('body').setAttribute('style', 'background-color:#2a2f3d');
		},
		beforeDestroy(){
			document.querySelector('body').removeAttribute('style');
		}
	}
</script>

<style>
	 .org-tree{
		background: #2a2f3d;
		border: 0px;
	}

	.org-tree-container{
		padding: 0px !important;
	}

	.online{
		background: #5d5dbeb2 !important;
	}

	.offline{

	}

	.org-tree-node-label{
		font-size: 12px;
		background: #69708f79;
		background-image: url(../assets/节点.png);
		background-repeat: no-repeat;
		background-position: center;
		background-size: contain;
		width: 130px;
		height: 150px;
		line-height: 1px;
		border-bottom: 4px solid rgba(110, 110, 110, 0.774);
		border-radius: 10px;
	}
	
	.org-tree-node-label:hover{
		background-image: url(../assets/节点.png);
	}
	
	.org-tree-node-label-inner{
		margin-top: 160px;
		/* background: #333388; */
		color: #fff;
		height: 10px;
		line-height: 10px;
		background: #616161;
	}
	
	.org-tree-node-label-inner:hover{
		background: #6666ff !important;
	}

	/* 根节点 */
	.org-tree>.org-tree-node>.org-tree-node-label{
		font-size: 12px;
		background-image: url(../assets/根节点.png);
		background-repeat: no-repeat;
		background-position: center;
		width: 130px;
		height: 150px;
		line-height: 1px;
		border-bottom: 4px solid #888;
		border-radius: 10px;
	}

</style>