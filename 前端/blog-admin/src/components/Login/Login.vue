<template>
  <div class="main">
    <div class="login-box">
      <h2>个人博客-管理系统</h2>
      <div class="login-col">
        <input type="text" required v-model="username" ref="input_username">
        <label>用户名</label>
      </div>
      <div class="login-col">
        <input type="password" required v-model="password" ref="input_password" @keydown.enter="login">
        <label>密码</label>
      </div>
      <a @click="login">
        登录
        <!-- 这四个span用于实现登录按钮边框跑马灯效果 -->
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      </a>
    </div>
  </div>

</template>

<script>
import { ref } from '@vue/reactivity';
import { nextTick, watch } from '@vue/runtime-core';
import axios from 'axios';
import { setToken } from '@/utils/token';
import { host } from '@/config/config';
import { useRouter } from 'vue-router';

export default {
    name: 'login',
    setup() {
        const $router = useRouter();

        let username = ref(''); // 用户名
        let password = ref(''); // 密码

        let input_username = ref(null); // 用户名输入框对象
        let input_password = ref(null); // 密码输入框对象

        /**
         *  登录函数
         */
        function login() {
            if (username.value === '' || username.value === undefined || username.value === null) {
                // 用户名为空
                nextTick(() => {
                    input_username.value.focus();
                });
            } else if (password.value === '' || password.value === undefined || password.value === null) {
                // 密码为空
                nextTick(() => {
                    input_password.value.focus();
                });
            } else {

                // 检查通过，发送登录请求
                axios.post(host + '/login', { username: username.value, password: password.value }).then((res) => {
                    const data = res.data;
                    // 返回码为200，登录成功
                    if (data.code === 200) {
                        setToken(data.token); // 跳转
                        $router.push({ path: '/' }); // 跳转到主页
                    } else if (data.code == 501) {
                        // 返回码501，用户名不存在
                        // 修改input_username样式为错误样式
                        input_username.value.style.borderBottom = '1px solid red';
                    } else if (data.code == 502) {
                        // 返回码502，密码错误
                        // 修改input_password样式为错误样式
                        input_password.value.style.borderBottom = '1px solid red';
                    }
                });
            }
        }
        /**
         * 监视username，当修改时，将错误样式改变为正常样式
         */
        watch(username, () => {
            input_username.value.style.borderBottom = '1px solid #fff';
        });
        /**
         * 监视password，当修改时，将错误样式改变为正常样式
         */
        watch(password, () => {
            input_password.value.style.borderBottom = '1px solid #fff';
        });

        return { username, password, login, input_password, input_username };
    },
};
</script>

<style scoped>
* {
    /* 清除默认边距 */
    margin: 0;
    padding: 0;
    /* 采用盒模型 */
    box-sizing: border-box;
}
.main {
    /* 弹性布局 */
    display: flex;
    /* 水平居中 */
    justify-content: center;
    /* 垂直居中 */
    align-items: center;
    /* 始终占据浏览器可视高度 */
    height: 100vh;
    /* 背景渐变色 */
    background: linear-gradient(#141e30, #243b55);
}

.login-box {
    /* 弹性布局 */
    display: flex;
    /* 主轴为Y轴(沿Y轴排列) */
    flex-flow: column;
    /* 水平居中 */
    align-items: center;
    /* 宽度 */
    width: 400px;
    padding: 40px;
    /* 背景色 */
    background-color: rgba(0, 0, 0, 0.2);
    /* 边框阴影 */
    box-shadow: 0 15px 15px rgba(0, 0, 0, 0.4);
}

.login-box h2 {
    /* 字体颜色为白色 */
    color: #fff;
    /* 距离下方元素30px */
    margin-bottom: 30px;
}

.login-box .login-col {
    /* 相对定位 */
    position: relative;
    /* 占满一行 */
    width: 100%;
    /* 外边距20px */
    margin: 20px;
}
.login-box .login-col input {
    /* 取消input自带的样式 */
    outline: none;
    border: none;
    /* 占满父元素 */
    width: 100%;
    /* 上下内边距10px */
    padding: 10px 0;
    /* 字体颜色为白色 */
    color: #fff;
    /* 字体大小16 */
    font-size: 16px;
    /* 设置下边框 */
    border-bottom: 1px solid #fff;
    /* 背景透明 */
    background-color: transparent;
    /* 过渡效果 */
    transition: all 0.5s;
}
.login-box .login-col label {
    /* 绝对定位 */
    position: absolute;
    /* 左上角 */
    left: 0;
    top: 0;
    /* 上下内边距10px */
    padding: 10px 0;
    /* 白色字体 */
    color: #fff;
    /* 取消指针事件,也就是不响应hover、click等事件 */
    pointer-events: none;
    /* 加个过渡动画 */
    transition: all 0.5s;
}
/* input框 获得焦点时 相邻的label属性改变 */
.login-box .login-col input:focus + label,
.login-box .login-col input:valid + label {
    /* 向上移动20px */
    top: -20px;
    /* 改变颜色 */
    color: #03e9f4;
    /* 缩小字体 */
    font-size: 12px;
}
.login-box a {
    /* 相对定位 */
    position: relative;
    /* 上下内边距10 左右内边距20 */
    padding: 10px 20px;
    /* 改变颜色 */
    color: #03e9f4;
    /* 取消下划线 */
    text-decoration: none;
    /* 鼠标变成手形 */
    cursor: pointer;
    /* 过渡 */
    transition: all 0.5s;
    /* 隐藏所有多余部分，用于跑马灯效果 */
    overflow: hidden;
}
.login-box a p{
  /* 取消指针事件,也就是不响应hover、click等事件 */
    pointer-events: none;
}
/* 鼠标悬停效果 */
.login-box a:hover {
    /* 改变颜色 */
    color: #fff;
    /* 圆角边框 */
    border-radius: 5px;
    /* 背景 */
    background-color: #03e9f4;
    /* 阴影，多个阴影，逐渐放大，实现发光效果 */
    box-shadow: 0 0 5px #03e9f4, 0 0 25px #03e9f4, 0 0 50px #03e9f4, 0 0 100px #03e9f4;
}

/* 实现边框跑马灯效果 */
.login-box a span {
    /* 绝对定位 */
    position: absolute;
}
/* 顶部 */
.login-box a span:nth-child(1) {
    /* 移动到顶部 */
    top: 0;
    /* 左侧移出自身大小 */
    left: -100%;
    /* 占满父元素 */
    width: 100%;
    /* 高度2px */
    height: 2px;
    /* 背景线性渐变,向右渐变,从透明到#03e9f4 */
    background: linear-gradient(to right, transparent, #03e9f4);
    /* 动画 */
    animation: move1 1s linear infinite;
}
/* 右部 */
.login-box a span:nth-child(2) {
    /* 顶部移出自身大小 */
    top: -100%;
    /* 移动到右侧 */
    right: 0;
    /* 占满父元素 */
    height: 100%;
    /* 宽度2px */
    width: 2px;
    /* 背景线性渐变,向下渐变,从透明到#03e9f4 */
    background: linear-gradient(to bottom, transparent, #03e9f4);
    /* 动画 */
    animation: move2 1s linear 0.25s infinite;
}

/* 底部 */
.login-box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(to left, transparent, #03e9f4);
    /* 动画 */
    animation: move3 1s linear 0.5s infinite;
}

/* 左部 */
.login-box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    height: 100%;
    width: 2px;
    background: linear-gradient(to top, transparent, #03e9f4);
    /* 动画 */
    animation: move4 1s linear 0.75s infinite;
}

/* 动画1 */
@keyframes move1 {
    0% {
        left: -100%;
    }
    50%,
    100% {
        left: 100%;
    }
}

/* 动画2 */
@keyframes move2 {
    0% {
        top: -100%;
    }
    50%,
    100% {
        top: 100%;
    }
}

/* 动画3 */
@keyframes move3 {
    0% {
        right: -100%;
    }
    50%,
    100% {
        right: 100%;
    }
}

/* 动画4 */
@keyframes move4 {
    0% {
        bottom: -100%;
    }
    50%,
    100% {
        bottom: 100%;
    }
}
</style>