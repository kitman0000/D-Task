<template>
  <div>
    <div style="left: 40%;position: absolute;">
    	<!-- <div style="margin-left: 12%;"><h3>登录</h3></div> -->
    	<img style="margin-left: 25%;" src="../assets/logo.png"/>
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
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">

</style>
