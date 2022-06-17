<template>
  <router-link class="card" :to="link">
    <!-- 标题 -->
    <div class="title-box">
      <div class="title">{{ article.title }}</div>
      <!-- <hit class="hit" :hit="article.hit"></hit> -->
    </div>
    <!-- 内容简单展示 -->
    <!-- <div class="content-box">
      {{ article.content }}
    </div> -->
    <!-- 信息展示 -->
    <div class="info-box">
      <div class="info-left">
        <tag v-for="tag in article.tag" :key="tag" :tagTitle="tag"></tag>
      </div>

      <div class="info-right">
        <create-date :createDate="createDate"></create-date>
        <modify-date :modifyDate="modifyDate"></modify-date>
      </div>
    </div>
  </router-link>
</template>

<script>
import moment from 'moment';
import Tag from '@/components/main/ArticleCard/Tag.vue';
import CreateDate from '@/components/main/ArticleCard/CreateDate.vue';
import ModifyDate from '@/components/main/ArticleCard/ModifyDate.vue';
import Hit from '@/components/main/ArticleCard/Hit.vue';
export default {
    name: 'ArticleCard',
    props: ['article'],
    components: { Tag, CreateDate, ModifyDate, Hit },
    computed: {
        // 格式化创建日期
        createDate() {
            return moment(this.article.createDate).format('YYYY/MM/DD');
        },
        modifyDate() {
            return moment(this.article.modifyDate).format('YYYY/MM/DD');
        },
        // 拼接地址
        link() {
            return '/article?id=' + this.article.id;
        },
    },
};
</script>

<style scoped>
.card {
    --font-color: #bdb4b0;
}
.card {
    width: 90%;
    height: 130px;
    background-color: #fff;
    margin: 0 5% 20px 5%;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    display: flex;
    flex-direction: column;
    transition: all 0.5s;
}
.card:hover{
  box-shadow: 0 1px 8px 0 rgba(0, 0, 0, 0.5);
}
.card:hover .title{
  color: #2196f3;
}
.info-font {
    color: var(--font-color);
}
/* 标题样式 */
.title {
    width: 85%;
    font-weight: bolder;
    font-size: x-large;
    margin-left: 5%;
    padding-top: 15px;
    transition: all 0.5s;
}


svg {
    width: 1em;
    height: 1em;
    position: relative;
    top: 0.15em;
}
.title-box {
    display: flex;
    flex: 2;
    justify-content: space-between;
    margin-right: 5%;
}
.content-box {
    flex: 2;
    margin-left: 5%;
    margin-right: 5%;
}
.info-box {
    margin-bottom: 15px;
    flex: 1;
    margin-left: 5%;
    margin-right: 5%;
    display: flex;
    justify-content: space-between;
}
.info-left {
    display: flex;
}
.info-right {
    display: flex;
}
</style>