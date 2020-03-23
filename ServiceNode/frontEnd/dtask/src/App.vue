<template>
	<div>
		<el-container>
			<el-header style="background-color: purple;">
				<h1 style="color: white;margin-left: 80px;">D-task</h1>
			</el-header>
			<el-main>
				<router-view>
				<div style="left: 40%;position: absolute;">
					<!-- <div style="margin-left: 12%;"><h3>登录</h3></div> -->
					<img style="margin-left: 25%;" src="./assets/logo.png"/>
					<div style="margin-left: 25%;margin-top: 50px;">
						<a :class="{theChosenOne:isNormalUser}" @click="login_changeToNormalUser()">本系统用户</a>
						<a :class="{theChosenOne:isPrimelUser}" style="margin-left: 35px" @click="login_changeToPrimeUser()">上级系统用户</a>
					</div>
					<el-form label-width="80px" style="margin-top: 50px;">
						<el-form-item label="用户名">
							<el-input v-model="userName" style="width: 100%;"></el-input>
						</el-form-item>
						<el-form-item label="密码">
							<el-input v-model="pwd" style="width: 100%;" type="password"></el-input>
						</el-form-item>
					</el-form>
					 <el-button round style="margin-left: 25%;background-color: purple;width: 70%;color: white;margin-top: 40px;" @click="login()">登录</el-button>
						</div>
				</router-view>
			</el-main>
		</el-container>

	</div>
</template>
<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				labelPosition: 'right',
				userName: '',
				pwd: '',
				isNormalUser:true,
				isPrimelUser:false,
			};
		},
		methods:{
			login_changeToNormalUser(){
				this.isNormalUser=true;
				this.isPrimelUser=false;
			},
			login_changeToPrimeUser(){
				this.isNormalUser=false;
				this.isPrimelUser=true;
			},
			login(){
				var userLogin = new URLSearchParams();
				userLogin.append("username", this.userName);
				userLogin.append("password", this.$md5(this.pwd));
				axios.post('/api/account/localLogin', userLogin)
					.then(res=> {
						alert("成功");
					}).catch(err=>{
						alert("请求异常");
					})
			},
		},
	}
</script>
<style lang="less">
.theChosenOne{
	text-decoration: underline;
}
</style>
