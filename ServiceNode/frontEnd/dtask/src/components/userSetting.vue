<template>
  <div id="app">
    <el-container>
      <el-header>
        <h1>用户设置</h1>
      </el-header>
      <el-main>

        <el-form  label-width="80px">
          <el-form-item label="昵称">
            <el-input v-model="nickname"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input placeholder="请输入密码"
                      v-model="password1"
                      class="inp"
                      show-password></el-input>
          </el-form-item>
          <el-form-item label="再次确认">
            <el-input placeholder="请输入密码"
                      v-model="password2"
                      class="inp"
                      show-password></el-input>
          </el-form-item>
          <el-form-item label="电话">
            <el-input v-model="telephoneNumber"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="eMail"
                      placeholder="请输入内容"
                      class="inp"></el-input>
          </el-form-item>
          <el-form-item label="生日">
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
          <el-form-item label="入职日期">
            <el-input
              placeholder="请输入内容"
              v-model="entryDate"
              :disabled="true"
              class="inp">
            </el-input>
          </el-form-item>
          <el-form-item label="所属角色">
            <el-input
            placeholder="请输入内容"
            v-model="post"
            :disabled="true"
            class="inp">
          </el-input>
          </el-form-item>
          <el-form-item label="所属部门">
            <el-input
              placeholder="请输入内容"
              v-model="department"
              :disabled="true"
              class="inp">
            </el-input>
          </el-form-item>
        </el-form>

        <div class="dbd">
          <el-button type="primary"
                     @click="btnConfirm"
                     icon="el-icon-check"
                     style="background: #24375E;
                     border: 0px;
                     position: relative; left:120px">确认提交</el-button></div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import axios from 'axios';
export default {
  name: 'App',
  data(){
    return{
      nickname : "",
      password1 : "",
      password2 : "",
      telephoneNumber: "",
      eMail: "",
      birthday: "",
      entryDate: "",
      post:"",
      department:""
    }
  },methods: {
    btnConfirm: function(){
      if(!this.password1){
        console.log("密码不能为空");
        return 0;
      }
      else if(this.password1.length<6){
        console.log("密码不得小于6位");
        return 0;
      }
      else if(this.password1!=this.password2) {
        console.log("错误！两次输入的密码不同");
        return 0;
      }
    },
    postSetting(){
      var params = new URLSearchParams();
      var onboardDate = new Date(this.onboardDate).toLocaleDateString().replace(/\//g, '-');
      var birthday = new Date(this.birthday).toLocaleDateString().replace(/\//g, '-');
      var userID =parseInt(localStorage.getItem("userID"));
      params.append("id",userID);
      params.append("pwd",this.password1);
      params.append("nickname",this.nickname);
      params.append("phone",this.telephoneNumber);
      params.append("email",this.eMail);
      params.append("roleID",this.post);
      params.append("departmentID",this.department);
      params.append("birthday",this.birthday);
      params.append("onboardDate",this.entryDate);
      axios.put("/api/user/user",
        params,
        {
          headers:{
            token:"eyJ1c2VySUQiOjEwMDEsInVzZXJuYW1lIjoiYWRtaW4yIiwiY3JlYXRlVGltZSI6MTU5MzcwMDkzNzUxM30=",
          }
        })
        .then(res => {
          var response = res.data;
          var retObj = eval(response.data);
          console.log(retObj);
        })
    },
    getSetting(){
      var params = new URLSearchParams();
      var userID =parseInt(localStorage.getItem("userID"));
      params.append("userID",userID);
      axios.get("/api/user/userDetail",{
        params:params,
        headers:{
          token:"eyJ1c2VySUQiOjEwMDEsInVzZXJuYW1lIjoiYWRtaW4yIiwiY3JlYXRlVGltZSI6MTU5MzcwMDkzNzUxM30=",
        }
      })
        .then(res => {
          var response = res.data;
          var userObj = eval(response.data);
          console.log(userObj);
          this.password1 = userObj.pwd;
          this.nickname = userObj.nickName;
          this.eMail = userObj.email;
          this.post = userObj.roleID;
          this.telephoneNumber = userObj.phone;
          this.birthday = userObj.birthday;
          this.department = userObj.departmentID;
          this.entryDate = userObj.onboardDate;
        })
      },
    },
  beforeMount:function() {
    this.getSetting();
    this.postSetting();
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
