<template>
  <div id="app">
    <el-container>
      <el-header>
        <h1>用户设置</h1>
      </el-header>
      <el-main>

        <el-form :label-position="left" label-width="80px">
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
      if(this.password1!=this.password2){
        console.log("错误！两次输入的密码不同");
      }
      else{
        console.log("表单提交成功！");
        console.log("用户名:",this.nickname);
        console.log("电话:",this.telephoneNumber);
        console.log("邮箱:",this.eMail);
        console.log("生日:",this. birthday);
        console.log("入职日期:",this.entryDate);
        console.log("所属角色:",this.post);
        console.log("所属部门:",this.department);
      }

    }

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
    width: 200px !important;
  }
</style>
