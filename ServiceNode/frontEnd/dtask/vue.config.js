
module.exports = {
    lintOnSave: true,
   
    devServer: {
      proxy: {
        // proxy all requests starting with /api to jsonplaceholder
        'api': {
          target: 'http://127.0.0.1:8080', //代理接口
          changeOrigin: true
          //,
          // pathRewrite: {
          //   '^/api/*': '/api'    //代理的路径
          // }
        }
      }
    }
}