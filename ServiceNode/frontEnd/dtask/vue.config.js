
module.exports = {
    lintOnSave: true,
   
    devServer: {
      proxy: {
        // proxy all requests starting with /api to jsonplaceholder
        'api': {
          target: 'http://192.168.30.10:8080', //代理接口
          changeOrigin: true
          //,
          // pathRewrite: {
          //   '^/api/*': '/api'    //代理的路径
          // }
        }
      }
    }
}