import axios from 'axios';
// 创建实例对象
const request = axios.create({
  baseURL: 'http://localhost/',     // 基本地址
  timeout: 5000,                    // 超时时间
  headers:{"Access-Control-Allow-Origin":"*"}
});

export default request;