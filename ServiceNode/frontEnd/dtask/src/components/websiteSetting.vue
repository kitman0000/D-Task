<template>
  <div id="app">
    <el-container>
      <el-header>
        <h1>网站管理</h1>
      </el-header>
      <el-main>
        <!--      网站管理部分      -->
        <el-form label-width="100px">

          <el-form-item label="网站名称:">
            <el-input
              v-model="websiteName"
              placeholder="请输入内容"
              class="tx">
            </el-input>
          </el-form-item>

          <el-form-item label="缓存设置:">
            <el-select
              v-model="cache"
              placeholder="请选择"
              class="tx">
              <el-option
                v-for="item in options"
                :key="item.key"
                :label="item.label"
                :value="item.value">
              </el-option></el-select>
          </el-form-item>

          <el-form-item label="最大登录次数:">
            <el-input-number
              v-model="maxLogin"
              :min="1" :max="10"
              label="最大登陆次数"
              class="tx">
            </el-input-number>
          </el-form-item>

          <el-form-item label="任务日志开关:">
            <el-switch
              v-model="taskLog"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="开"
              inactive-text="关">
            </el-switch>
          </el-form-item>

          <el-form-item label="用户操作日志:">
            <el-switch
              v-model="userLog"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="开"
              inactive-text="关">
            </el-switch>
          </el-form-item>

          <el-button type="primary"
                     @click="changeWebInfo()"
                     icon="el-icon-check"
                     style="background: #24375E;
                     border: 0px;
                     position: relative; left:100px">
            确认提交</el-button>

        </el-form>

      </el-main>

      <el-header>
        <h1>状态管理</h1>
      </el-header>
      <el-main>
        <!--      状态管理部分      -->
        <el-form label-width="100px">

          <el-form-item label="网站开关:">
            <el-button
              type="danger"
              @click="webChange()"
              icon="el-icon-switch-button">
              关闭网站</el-button>
          </el-form-item>

        </el-form>

      </el-main>


    </el-container>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'App',
    components: {
    },
    data() {

      return {
        websiteName: '',
        maxLogin: null ,
        cache: '',
        options: [{
          value: 1,
          label: 'Redis'
        }, {
          value: 2,
          label: 'Ehcache'
        }],
        taskLog: false, //任务日志开关
        userLog: false, //用户日志开关
        webClose: false,//网站开关
        problem: 0,
      }
    },
    methods: {
      caChange: function() {
        console.log('缓存使用:' + this.cache)
      },

      changeWebInfo(){

        var params = new URLSearchParams();
        params.append("websiteName",this.websiteName);
        params.append("maxLogin",this.maxLogin);
        params.append("cache",this.cache);
        params.append("taJudge",this.taskLog);
        params.append("userJudge",this.userLog);

        axios.post("api/webSiteSettings/setting",params,{
            headers:{
              token:localStorage.getItem("token"),
            }
          }).then(res => {
            var response = res.data;
            var retObj = eval(response.data);
          }).catch(err =>{
          console.log("timeout");
          console.log(err);
          })

          this.$message({
            type: 'success',
            message: `提交成功`
          })

      },
      getWebInfo(){
        axios.get("/api/webSiteSettings/setting",{
          headers:{
            token:localStorage.getItem("token"),
          }
        })
        .then(res =>{
          console.log(res);
          var response = res.data;
          var webObj = eval(response.data);
          this.websiteName = webObj.websiteName;
          this.maxLogin= webObj.maxLogin;
          this.cache = webObj.cache;
          this.taskLog = webObj.taskLog;
          this.userLog = webObj.userLog;
        }).catch(err =>{
          console.log("timeout");
          console.log(err);
        })
      },
      webChange:function(){

        this.$confirm('网站即将被关闭, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.webClosedialog = true;
          this.webClose = !this.webClose;
          var params = new URLSearchParams();
          params.append("webClose",this.webClose);
          axios.post("/api/webSiteSettings/shutDown",null,{
            headers:{
              token:localStorage.getItem("token"),
            }
          }).then(res => {
            var response = res.data;
            var retObj = eval(response.data);
          }).catch(err =>{
            console.log("timeout");
            console.log(err);
          })
          this.$message({
            type: 'success',
            message: '网站已关闭!'
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '网站关闭已取消!'
          });
        });

      }
    },
    //网站开关
    beforeMount:function() {
      this.getWebInfo();
    }
  }
</script>

<style>
  .tx{
    width: 220px;
    position: relative;
    top: -5px;
  }
</style>

