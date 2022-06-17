import { createApp } from 'vue'
import App from './App.vue'
import {router} from "./router"
// import * as ElIconModules from '@element-plus/icons-vue'

const app = createApp(App);
app.use(router)
// app.use(ElIconModules)
app.mount('#app')
