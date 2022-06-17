const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer:{
      port:81
  },
  transpileDependencies: true,
  lintOnSave:false,
  chainWebpack: ((config) => {
    config.plugin('html').tap((args) => {
      args[0].title = '个人博客'
      return args
    })}),
    
})
