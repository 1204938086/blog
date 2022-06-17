<template>
  <div class="main">
    <div class="search-box">
      <article-card v-for="article in Articles" :key="article.id" :article="article" />
      <div class="search-notfound" v-if="Articles.length === 0">
        <p class="search-404">404</p>
        <p class="search-message">您来到了知识的荒原</p>
        <router-link to="/main" class="search-gohome">返回首页</router-link>
      </div>
    </div>

  </div>

</template>

<script>
import { useRoute } from 'vue-router';
import ArticleCard from '@/components/main/ArticleCard/ArticleCard.vue';
import request from '@/utils/request';
import { ref } from '@vue/reactivity';
export default {
    name: 'Search',
    setup() {
        const $route = useRoute();

        let Articles = ref([1]);
        request.post('/article/query', { titleOrContent: $route.query.titleOrContent, type: $route.query.type, tag: $route.query.tag }).then((res) => {
            Articles.value = res.data;
        });
        return { Articles };
    },
    components: { ArticleCard },
};
</script>

<style scoped>
.main {
    margin-top: 100px;
    margin-bottom: 20px;
    width: 100%;
    /* height: calc(100vh - 100px); */
    min-height: calc(100vh - 100px);
    display: flex;
    justify-content: center;
}
.search-box {
    /* 盒子的样式 */
    background-color: #fff;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    width: 70%;

    /* 控制子容器布局方式 */
    display: flex;
    flex-flow: row;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: 20px;
}
/* 未找到数据样式 */
.search-notfound {
    width: 100%;
    height: 100%;
    /* background-color:#50C1C1; */

    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
}
/* 404数字样式 */
.search-404 {
    /* 背景颜色改为线性渐变颜色 */
    background-image: linear-gradient(92deg, #f35626, #feab3a); 
    /* 字体颜色改为透明 */
    color: transparent;
    /* 将背景颜色应用到文本 */
    background-clip: text;
    /* 设置字体大小 */
    font-size: 150px;
}
/* 提示信息 */
.search-message{
    font-size: 30px;
    color: #7b8993;
}
/* 回到首页按钮 */
.search-gohome{
    border: 1px solid #7b8993;
    color: #7b8993;
    background-color: transparent;
    
    border-radius: 25px;
    margin-top: 20px;
    cursor: pointer;
    font-size: 20px;
    padding: 10px 25px;

}
</style>