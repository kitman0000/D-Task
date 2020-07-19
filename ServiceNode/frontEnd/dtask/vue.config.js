
module.exports = {
    lintOnSave: true,
   
    devServer: {
      proxy: {
        // proxy all requests starting with /api to jsonplaceholder
        'api': {
          target: 'http://47.100.23.230:8080', //代理接口
          changeOrigin: true
          //,
          // pathRewrite: {
          //   '^/api/*': '/api'    //代理的路径
          // }
        }
      }
    }
}