<template>
  <el-container>
    <el-header class="admin-header">
      <span>个人博客-管理系统</span>
      <el-popconfirm title="你确定要退出登录吗?" confirm-button-text="确定" cancel-button-text="取消" @confirm="quit">
    <template #reference>
      <el-button type="text">退出</el-button>
    </template>
  </el-popconfirm>
      
    </el-header>
    <el-container class="main">
      <Menu></Menu>
      <el-main>
        <router-view v-slot="{ Component }">
          <keep-alive>
            <component :is="Component" v-if="$route.meta.keepAlive" />
          </keep-alive>
          <component :is="Component"  v-if="!$route.meta.keepAlive"/>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Menu from '@/components/Main/framework/Menu.vue';
import {removeToken} from "@/utils/token"
import { useRouter } from 'vue-router';
export default {
    setup() {
      const router = useRouter();
      function quit(){
        removeToken()
        router.push({path:"/login"});
      }
      return {quit}
    },
    components: { Menu},
};
</script>

<style scoped>
.main {
    overflow: hidden;
}

.aside {
    height: 100%;
}
.el-header {
    background-color: #001529;
    display: flex;
    align-items: center;
    margin-bottom: 3px;
}
/* 标题样式 */
.el-header span {
    color: #fff;
}
.el-main {
    flex: 9;
    background-color: #f8f8f8;
    overflow: hidden;
}

.admin-header{
  display: flex;
  justify-content: space-between;
}
</style>