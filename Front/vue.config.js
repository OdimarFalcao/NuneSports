const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  pluginOptions: {
    vuetify: {
      // Configurações do Vuetify
    }
  },
  devServer: {
    proxy: 'http://localhost:8085',
  },
})
