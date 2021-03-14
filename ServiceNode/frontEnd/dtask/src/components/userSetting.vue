<template>
    <el-container>
      <el-header>
        <h1>用户设置</h1>
      </el-header>
      <el-main>
        <el-form  label-width="80px">
          <el-form-item label="昵称:">
            <el-input style="width: 300px;" v-model="nickname"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="旧密码:">
            <el-input style="width: 300px;" placeholder="请输入旧密码"
                      v-model="oldPassword"
                      class="inp"
                      show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码:">
            <el-input style="width: 300px;" placeholder="请输入新密码"
                      v-model="pwd"
                      class="inp"
                      show-password></el-input>
          </el-form-item>
          <el-form-item label="手机:">
            <el-input style="width: 300px;" v-model="phone"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="邮箱:">
            <el-input style="width: 300px;" v-model="email"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="生日:">
            <div class="block">
              <span class="demonstration"></span>
              <el-date-picker
                v-model="birthday"
                type="date"
                placeholder="选择日期"
                style="width: 200px;">
              </el-date-picker>
            </div>
          </el-form-item>
          <el-form-item label="入职日期:">
            <el-input style="width: 300px;"
              placeholder="请输入内容"
              v-model="onboardDate"
			  :disabled="true"
              class="inp">
            </el-input>
          </el-form-item>
          <el-form-item label="所属角色:">
            <el-input style="width: 300px;"
            placeholder="请输入内容"
            v-model="roleName"
            :disabled="true"
            class="inp">
          </el-input>
          </el-form-item>
          <el-form-item label="所属部门:">
            <el-input style="width: 300px;"
              placeholder="请输入内容"
              v-model="departmentName"
              :disabled="true"
              class="inp">
            </el-input>
          </el-form-item>
        </el-form>
		<span style="color: red; font-size: 10px;">{{message}}</span>
        <div class="dbd">
          <el-button type="primary"
                     @click="postSetting"
                     icon="el-icon-check"
                     style="background: #24375E;
                     border: 0px;
                     position: relative; left:10px">确认提交</el-button>
		
			<el-button type="primary"
                     @click="logout"
                     icon="el-icon-close"
                     style="background: rgb(159, 7, 7);
                     border: 0px;
                     position: relative; left:10px">登出账号</el-button>
		</div>

      </el-main>
    </el-container>
</template>

<script>
  import axios from 'axios';
	export default {
	  data(){
		return{
			nickname:"",
			oldPassword:"",
			pwd:"",
			phone:"",
			email:"",
			birthday:"",
			onboardDate:"",
			roleName:"",
			departmentName:"",
			message:""
		}
	  },
	  methods: {
		postSetting(){
			if (!this.nickname) {
				this.message = '昵称不能为空';
			}
			else if (!this.phone) {
				this.message = '手机号不可为空';
			}
			else if (!this.email) {
				this.message = '邮箱不能为空';
			}
			else if (!this.birthday) {
				this.message = '出生日期不可为空';
			}
			else{
				var pwd;
				var oldPassword;
				// 如果密码为空，则不修改密码
				if (this.pwd==""&&this.oldPassword=="") {
					this.pwd = '';
					this.oldPassword='';
					pwd = "";
					oldPassword = "";
				}
				else if(this.pwd!=""&&this.oldPassword!=""){
					pwd = this.$md5(this.pwd);
					oldPassword = this.$md5(this.oldPassword)
				}
				else{
					this.message="旧密码和新密码不能为空";
					return 0;
				}
				console.log(pwd);
				console.log(oldPassword);
				var params = new URLSearchParams();
				var birthday = new Date(this.birthday).toLocaleDateString().replace(/\//g, '-');
				params.append("nickname",this.nickname);
				params.append("phone",this.phone);
				params.append("email",this.email);
				params.append("birthday",birthday);
				params.append("pwd",pwd);
				params.append("oldPassword",oldPassword);
				axios.post("/api/user/changeUserDetail",
					params,
					{
						headers:{
							token:localStorage.getItem("token"),
						}
					})
				.then(res=>{
					var retObj = eval(res.data);
					console.log(retObj);
					if(retObj.ret==1){
						if(pwd!=""&&oldPassword!=""){
							this.$router.push({
								path:'/',
							})
						}
						else{
							location.reload();
						}
					}
					else if(retObj.ret==2){
						this.message="旧密码错误"
					}
				})
				.catch(err => {
					// alert("请求异常");
				});
			}
			
		},
		getSetting(){
			axios.get("/api/user/userOwnDetail",{
				headers:{
					token:localStorage.getItem("token"),
				}
			})
			.then(res=>{
				var response = res.data;
				var userObj = eval(response.data);
				console.log(userObj);
				this.nickname = userObj.nickName;
				this.email = userObj.email;
				this.roleName = userObj.roleName;
				this.phone = userObj.phone;
				this.birthday = userObj.birthday;
				this.departmentName = userObj.departmentName;
				this.onboardDate = userObj.onboardDate;
			})
			.catch(err => {
				// alert("请求异常");
			});
		},
		logout(){
			axios.post("/api/account/localLogout",null,{
				headers:{
					token:localStorage.getItem("token"),
				}
			})
			.then(res=>{
				localStorage.setItem("token","");
				this.$router.push({
								path:'/',
							});
			})
		},
	},
	beforeMount:function() {
		this.getSetting();
	}
	}


</script>

<style>
  .dbd{
    top: 120px;
    height: 50px;
    min-width:1366px;

  }
  .inp{
    width: 200px;
  }
</style>
