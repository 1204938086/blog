<template>
  <router-view></router-view>
</template>

<script>
import { host } from '@/config/config';
import { getToken,removeToken } from '@/utils/token';
import { useRouter } from 'vue-router';
import axios from 'axios';
export default {
    name: 'App',
    setup() {
        let $router = useRouter();
        if (!getToken()) {
            $router.push({ path: '/login' });
        } else {
            axios.post(host + 'verifytoken', { token: getToken() })
            .then((res) => {
                const data = res.data;
                if (data.code === 512) {
                    removeToken();// 删除Token
                    // 弹出提示框，提示Token已经过期了
                    ElMessageBox.alert('Token已经过期,请重新登录', '提示', {
                        confirmButtonText: 'OK',
                        callback:()=>{
                            $router.push({ path: '/login' });
                        }
                    });
                }else if(data.code === 511){
                    removeToken();// 删除Token
                    // 弹出提示框，提示Token无效
                    ElMessageBox.alert('Token无效,请重新登录', '提示', {
                        confirmButtonText: 'OK',
                        callback:()=>{
                            $router.push({ path: '/login' });
                        }
                    });
                }
            })
            .catch((err)=>{
                console.log(err)
                ElMessageBox.alert('连接服务器失败！', '提示', {
                        confirmButtonText: 'OK',
                        callback:()=>{
                            $router.push({ path: '/login' });
                        }
                    });
            });
        }
    },
    components: {},
};
</script>

<style>
* {
    margin: 0;
    padding: 0;
}
html,
body,
#app,
.el-container {
  padding: 0px;
  margin: 0px;
  height: 100%;
}
body{
    height: 100vh;
    width: 100vw;
}
</style>
