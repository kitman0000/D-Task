<template>
  <div class="home">
    <div style="left: 40%;position: absolute;">
    	<!-- <div style="margin-left: 12%;"><h3>登录</h3></div> -->
    	<img style="margin-left: 25%;" src="../assets/logo.png"/>
    	<div style="margin-left: 25%;margin-top: 50px;">
    		<a :class="{'theChosenOne':isNormalUser}" style="cursor: pointer;" @click="login_changeToNormalUser()">本系统用户</a>
    		<a :class="{'theChosenOne':isPrimelUser}" style="margin-left: 35px;cursor:pointer" @click="login_changeToPrimeUser()">上级系统用户</a>
    	</div>
    	<el-form label-width="80px" style="margin-top: 50px;">
    		<el-form-item label="用户名:">
    			<el-input v-model="userName" style="width: 100%;"></el-input>
    		</el-form-item>
    		<el-form-item label="密码:">
    			<el-input v-model="pwd" style="width: 100%;" type="password"></el-input>
    		</el-form-item>
    	</el-form>
    	 <el-button round style="margin-left: 25%;background-color: purple;width: 70%;color: white;margin-top: 40px;" @click="login()">登录</el-button>
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
				userLogin.append("pwd", this.$md5(this.pwd));
				axios.post('/api/account/localLogin', userLogin)
					.then(res=> {
						var response = res.data;
						if(response.ret == 1){
							localStorage.setItem("token",response.data);
							this.$router.push({path:"/Menu"});
						}
						else if(response.ret == 2){
							alert("账号或者密码错误");
							location.reload();
						}
						else if(response.ret == 3){
							alert("该账号已被锁定");
							location.reload();
						}
						else if(response.ret == 4){
							alert("该账号已被禁止");
							location.reload();
						}
						
					}).catch(err=>{
						alert("请求异常");
					})
			},
		},
	}
</script>
<style>
	.theChosenOne{
		text-decoration: underline;
	}
</style>
