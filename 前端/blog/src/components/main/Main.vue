<template>
  <div id="main">
    <div class="articleList">
      <article-card
        v-for="t in articleData"
        :key="t.id"
        :article="t"
      ></article-card>
      <div class="load-more" @click="loadMore">{{ loadMessage }}</div>
    </div>
    <!-- <div class="sidebar">侧边栏</div> -->
    <side-bar></side-bar>
  </div>
</template>

<script>
import axios from "axios";
import ArticleCard from "@/components/main/ArticleCard/ArticleCard.vue";
import SideBar from "@/components/main/SideBar/SideBar.vue";
import { ref } from "@vue/reactivity";
import { onUnmounted } from "@vue/runtime-core";
export default {
  name: "Main",
  setup() {
    const pagesize = 10;
    let page = 1;
    let articleData = ref([]);
    let canLoad = true;
    let loadMessage = ref("加载更多");

    function loadData(pagesize, page) {
      axios
        .get("http://localhost/article/page/" + pagesize + "/" + page)
        .then((response) => {
          articleData.value = articleData.value.concat(response.data);
          if (
            response.data.length === 0 ||
            articleData.value.length < pagesize
          ) {
            canLoad = false;
            loadMessage.value = "没有了";
          }
        });
    }
    // =================== 初始化数据 ===================
    loadData(pagesize, page);

    function loadMore() {
      if (canLoad) {
        page++;
        loadData(pagesize, page);
      }
    }
    onUnmounted(() => {
      console.log("主页销毁");
    });
    return { articleData, loadMore, loadMessage };
  },
  components: {
    ArticleCard,
    SideBar,
  },
  mounted() {},
};
</script>

<style scoped>
#main {
  margin: 0 auto;
  padding-top: 100px;
  width: 70%;
  /* overflow: hidden; */
}
.articleList {
  float: left;
  width: 70%;
  min-width: 700px;
}

/* 加载更多按钮样式 */
.load-more {
  margin: 0 auto;
  display: flex;
  width: 100px;
  height: 40px;
  background-color: #fff;
  justify-content: center;
  align-items: center;
  border-radius: 50px;
  box-shadow: var(--box-shadow);
  cursor: pointer;
  transition: all 0.5s;
}
.load-more:hover {
  box-shadow: 0 1px 8px 0 rgba(0, 0, 0, 0.5);
}
</style>