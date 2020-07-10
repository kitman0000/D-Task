<template>
	<div class="home">
		<div style="left: 35%;position: absolute;border:rgb(36, 55, 94) 1px solid; width: 500px;margin-top: 100px;padding-bottom: 50px;">
			<div style="margin-top: 50px;width: 100%; height:40px;background: #24375E;color: #fff;">
				<div style="padding-top: 7px;padding-left: 75px;font-size: 18px;">登录</div>
			</div>
			<div style="padding-left: 50px;padding-right: 50px;">
				<img src="../assets/logo.png" />
				<div style="margin-left: 25%;margin-top: 50px;">
					<a :class="{'theChosenOne':isNormalUser}" style="cursor: pointer;padding: 10px;" @click="login_changeToNormalUser()">本系统用户</a>
					<a :class="{'theChosenOne':isPrimelUser}" style="margin-left: 15px;cursor:pointer;padding: 10px;" @click="login_changeToPrimeUser()">上级系统用户</a>
				</div>
				<el-form label-width="80px" style="margin-top: 50px;" v-if="isNormalUser == true">
					<el-form-item label="用户名:">
						<el-input v-model="userName" style="width: 100%;"></el-input>
					</el-form-item>
					<el-form-item label="密码:">
						<el-input v-model="pwd" style="width: 100%;" type="password"></el-input>
					</el-form-item>
				</el-form>
				<el-form label-width="80px" style="margin-top: 50px;" v-if="isPrimelUser == true">
					<el-form-item label="父节点:">
					<el-select v-model="value" placeholder="请先选择节点">
						<el-option v-for="item in options" :key="item.id" :label="item.nodeName" :value="item.id">
						</el-option>
					</el-select>
					</el-form-item>
					<el-form-item label="用户名:">
						<el-input v-model="userName" style="width: 100%;"></el-input>
					</el-form-item>
					<el-form-item label="密码:">
						<el-input v-model="pwd" style="width: 100%;" type="password"></el-input>
					</el-form-item>
				</el-form>

				<el-button style="margin-left: 20%;background-color: rgb(36, 55, 94);width: 30%;color: white;" @click="login()">登录</el-button>
			</div>
		</div>
	</div>
</template>

<script>
	// @ is an alias to /src
	import HelloWorld from '@/components/HelloWorld.vue'
	import axios from 'axios';


	export default {
		data() {
			return {
				labelPosition: 'right',
				userName: '',
				pwd: '',
				isNormalUser: true,
				isPrimelUser: false,
				options:[],
				value:'',
			};
		},
		methods: {
			login_changeToNormalUser() {
				this.isNormalUser = true;
				this.isPrimelUser = false;
			},
			login_changeToPrimeUser() {
				this.isNormalUser = false;
				this.isPrimelUser = true;
			},
			login() {
				var userLogin = new URLSearchParams();
				userLogin.append("username", this.userName);
				userLogin.append("pwd", this.$md5(this.pwd));
				if(this.isNormalUser){
					axios.post('/api/account/localLogin', userLogin)
						.then(res => {
							var response = res.data;
							if (response.ret == 1) {
								localStorage.setItem("token", response.data);
								this.$router.push({
									path: "/Menu"
								});
							} else if (response.ret == 2) {
								alert("账号或者密码错误");
								location.reload();
							} else if (response.ret == 3) {
								alert("该账号已被锁定");
								location.reload();
							} else if (response.ret == 4) {
								alert("该账号已被禁止");
								location.reload();
							}
					
						}).catch(err => {
							alert("请求异常");
						});
				}
				else{
					userLogin.append('userNodeID',this.value);
					axios.post('/api/account/remoteLogin', userLogin)
						.then(res => {
							var response = res.data;
							if (response.ret == 1) {
								localStorage.setItem("token", response.data);
								this.$router.push({
									path: "/Menu"
								});
							} else if (response.ret == 2) {
								alert("用户不存在或不具有权限");
								location.reload();
							} else if (response.ret == 3) {
								alert("密码错误");
								location.reload();
							} else if (response.ret == 4) {
								alert("未知错误");
								location.reload();
							}
					
						}).catch(err => {
							alert("请求异常");
						});
				}
				
			},
			getParentNode(){
				axios.get('/api/account/parentNodes', {
						params: {},
					})
					.then(res => {
						var response = res.data.data;
						this.options = eval(response);
					})
					.catch(function(error) {
						console.log(error);
					});
			}
		},
		beforeMount:function(){
			this.getParentNode();
		}
	}
</script>
<style>
	.theChosenOne {
		background: #24375E;
		border-radius: 10px;
		color: #fefefe;
	}
</style>
