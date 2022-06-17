<template>
  <el-aside >
    <el-menu active-text-color="#ffd04b" background-color="#001529"  default-active="dashboard" text-color="#fff" :router="true">
      <el-menu-item index="dashboard">
          <el-icon><trend-charts /></el-icon>
        <span>仪表盘</span>
      </el-menu-item>
      <el-menu-item index="createarticle">
          <el-icon><Plus /></el-icon>
        <span>新建文章</span>
      </el-menu-item>
      <el-menu-item index="articlemanger">
          <el-icon><tickets /></el-icon>
        <span>文章管理</span>
      </el-menu-item>
      <el-menu-item index="typemanger">
          <el-icon><box /></el-icon>
        <span>分类管理</span>
      </el-menu-item>
      <el-menu-item index="tagsmanger">
          <el-icon><collection-tag /></el-icon>
        <span>标签管理</span>
      </el-menu-item>
      <el-menu-item index="commentmanger">
          <el-icon><chat-line-square /></el-icon>
        <span>评论管理</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script>
import {Plus,House,Tickets,TrendCharts,CollectionTag,Box,ChatLineSquare} from "@element-plus/icons-vue"
export default {
    name: 'Menu',
    components:{Plus,House,Tickets,TrendCharts,CollectionTag,Box,ChatLineSquare}
};
</script>

<style scoped>
.el-menu {
    height: 100%;
    /* width: 150px; */
    overflow: hidden;

}
.el-menu-item{
    display: flex;
    justify-content: flex-start;
}
.el-menu-item:hover{
  background-color: #666;
}
.el-aside{
    margin-right: 3px;
    margin: 3px;
    border-radius: 5px;
    flex: 1;
}
</style>