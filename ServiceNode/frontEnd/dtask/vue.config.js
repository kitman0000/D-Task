
module.exports = {
    lintOnSave: true,
   
    devServer: {
      proxy: {
        // proxy all requests starting with /api to jsonplaceholder
        'api': {
          target: 'http://localhost:8080', //代理接口
          changeOrigin: true
        },
        'pluginPage': {
          target: 'http://localhost:8080', //代理接口
          changeOrigin: true
        }
      }
    },
    css:{
      extract: false
    }
}