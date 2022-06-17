import { createApp } from 'vue'
import App from './App.vue'
import {router} from "@/router/index.js";

const app = createApp(App);
// 路由
app.use(router); 

// 全局参数配置
import {host} from "@/config/config"
app.config.globalProperties.$host=host;

app.mount('#app');
