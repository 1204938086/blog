import axios from 'axios';

import {getToken} from "@/utils/token"
import { ElMessageBox } from 'element-plus';



// 创建实例对象
const request = axios.create({
  baseURL: 'http://localhost/',     // 基本地址
  timeout: 5000,                    // 超时时间
  headers:{"Access-Control-Allow-Origin":"*"}
});

// 设置请求拦截

request.interceptors.request.use(function (config) {
  // 设置Token
  config.headers.Authorization = getToken();
  // console.log("config",config);
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 设置响应拦截
request.interceptors.response.use(function (response) {
  if(response.data.code === 511 || response.data.code === 512){
    ElMessageBox.alert('权限验证失败,请重新登录', '提示', {
      confirmButtonText: 'OK',
    })
    window.location.href="http://localhost:82/#/login";
    
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  ElMessageBox.alert('请求错误', '提示', {
    confirmButtonText: 'OK',
  })
  return Promise.reject(error);
});
export default request;