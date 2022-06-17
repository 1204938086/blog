const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')


const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  devServer: {
    port: 82
  },
  transpileDependencies: true,
  lintOnSave: false,
  chainWebpack: ((config) => {
    config.plugin('html').tap((args) => {
      args[0].title = '个人博客-管理系统'
      return args
    })
  }),
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver()],
      }),
      Components({
        resolvers: [ElementPlusResolver()],
      }),
    ],
  },
})
