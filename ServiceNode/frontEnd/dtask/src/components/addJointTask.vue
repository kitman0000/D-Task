<template>
	<el-container>
		<el-main>
			<div class="demo-input-suffix" style="height: 60px;">
				<div style="float: left; line-height: 35px; width: 100px;">任务名：</div>
			<el-input placeholder="任务名" v-model="name" style="width: 300px;"></el-input>
			</div>
			<span style="color: red; font-size: 10px;">{{message}}</span>
			<el-button type="primary" @click="addTask()" icon="el-icon-check" style="position: relative; left: 90px; margin-left: 10px;background: #24375E;border: 0px ;">增加</el-button>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				name:"",
				message:"",
				isShow: true,
			}
		},
		methods:{
			addTask(){
				if (!this.name) {
					this.message = '任务名不可为空';
				}
				else{
					var params = new URLSearchParams();
					params.append("name",this.name);
					axios.post("/api/remoteTask/remoteTask",
					params,
					{
						headers:{
							token:localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						var retObj = eval(response.data);
						console.log(retObj);
					})
					.catch(function(error) {
						console.log(error);
					});
					this.$router.push({
						path:'/jointTask',
					})
				}
			},
			showList(){
				this.isShow=!this.isShow;
			}
		}
	}
</script>

<style>
	.theSelectedMenu {
		background-color: #24375E;
	}
	
	.otherSubMenu {
		background-color: #334E85;
	}
	
	.showListButton {
		position: fixed;
		left: 208px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
	
	.hideListButton {
		position: fixed;
		left: 0px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
</style>
