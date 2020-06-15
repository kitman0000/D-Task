<template>
  <div id="app">
    <el-container>
      <el-main>
				<el-header style="font-size: 18px; position: relative;"><b>站长设置</b></el-header>
        <el-tabs v-model="activeName" tabPosition="left" style="">
          <el-tab-pane label="网站管理" name="first">
            <span>
              网站名称 : {{webName}}
              <br>
              <el-input
                v-model="webName"
                placeholder="请输入内容"
                style="width: 430px; padding: 20px"></el-input>
            </span>
            <br>
            <span style="color: #303133;">
              缓存使用设置:
              <el-select
                v-model="cacheChose"
                placeholder="请选择"
                @change="caChange()"
                style="position: relative; top: -5px;">
              <el-option
                v-for="item in options"
                :key="item.key"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
              </el-select>
            </span>
            <br>
            <span>
              最大登陆次数:
              <el-input-number
                v-model="maxLogin"
                @change="" :min="1" :max="10"
                label="最大登陆次数"
                style="width: 220px; position: relative; top: -5px;">
              </el-input-number>
            </span>
          </el-tab-pane>
          <el-tab-pane
            label="日志管理"
            name="second"
            style="font-size: 30px; height: 100px; position: relative; top: 10px;">
            <span>
              任务日志开关:
              <el-switch
                v-model="taJudge"
                @change="taChange()"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="开"
                inactive-text="关">
              </el-switch>
            </span>
            <br>
            <span style="position: relative; top: 15px;">
              管理员日志开关:
              <el-switch
                v-model="adJudge"
                @change="adChange()"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="开"
                inactive-text="关">
              </el-switch>
            </span>
          </el-tab-pane>
          <el-tab-pane
            label="状态管理"
            name="third"
            style="height: 150px; position: relative; top: 10px;">
            <span>
              网站开关:
              <el-switch
                v-model="swJudge"
                @change="swChange()"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="开"
                inactive-text="关">
              </el-switch>
            </span>
            <br>
            <span style="position: relative; top: 15px;">
              网站重启:
              <el-popconfirm
                confirmButtonText='好的'
                cancelButtonText='不用了'
                @onConfirm="onConfirm()"
                iconColor="red"
                title="确定要重启网站吗？"
              >
              <el-button
                type="success"
                icon="el-icon-switch-button"
                slot="reference"
                round
                style="position: relative; top: -5px;">
                重启按钮
              </el-button>
              </el-popconfirm>
            </span>
          </el-tab-pane>
        </el-tabs>
      </el-main>

    </el-container>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  },
  data() {

    return {
      activeName: 'first',
      webName: '请输入网站名称',
      maxLogin: 1,
      cacheChose: '1k',
      options: [{
        value: 1,
        label: '1k'
      }, {
        value: 2,
        label: '2k'
      }, {
        value: 4,
        label: '4k'
      }, {
        value: 8,
        label: '8k'
      }, {
        value: 16,
        label: '16k'
      }],
      taJudge: false, //任务日志开关
      adJudge: false, //管理员日志开关
      swJudge: this.onClick  //网站开关
    }
  },
  methods: {
    caChange: function() {
      console.log('缓存使用:' + this.cacheChose + 'k')
    },
    taChange: function() {
      console.log('任务日志发生改变')
    },//任务日志开关
    adChange: function() {
      console.log('管理员日志发生改变')
    },//管理员日志开关
    swChange: function() {
      console.log('网站发生改变')
    },//网站开关
    onConfirm: function() {
      console.log('网站重启')
    }
  }
}
</script>

<style>

</>
