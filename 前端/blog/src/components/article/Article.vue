<template>
  <div class="article-main-box">
    <div class="article-box">
      <div class="article">
        <div ref="MarkDown" class="markdown"></div>
      </div>
      <side-bar></side-bar>
    </div>
    <div class="comment-box">
      <comment :comments="Article.comment"></comment>
    </div>
  </div>

</template>

<script>
import request from '@/utils/request';
import marked from '@/utils/mymarked';
import render from '@/utils/render';

import { Fancybox } from '@fancyapps/ui'; // 图片全屏查看组件
import '@fancyapps/ui/dist/fancybox.css'; // 图片全屏查看样式
import '@/assets/css/prism.css';
import '@/assets/css/markdown.css';

import { useRoute } from 'vue-router';
import { reactive, ref, toRefs, onMounted, nextTick } from 'vue';

import SideBar from '@/components/main/SideBar/SideBar.vue';
import Comment from '@/components/article/Comment.vue';

export default {
    name: 'Article',
    setup() {
        const $route = useRoute();
        const id = $route.query.id;
        // =============== 数据 ==================
        const Data = reactive({
            Article: {}

        });
        // =============== DOM 对象 ==============
        const DOM = reactive({
            MarkDown: ref(),
        });
        // =============== 挂载事件 ===============
        onMounted(() => {
            request.get('/article/id/' + id).then((res) => {
                // 更新文章数据
                Data.Article = ref(res.data);
                nextTick(() => {
                    // Markdown格式化
                    DOM.MarkDown.innerHTML = marked(Data.Article.markdown, render);
                });
            });
        });

        return { ...toRefs(Data), ...toRefs(DOM),id };
    },
    updated() {
        // Prism.highlightAll()
    },
    components: {
        SideBar,
        Comment,
    },
};
</script>

<style scoped>
.article-main-box {
    width: 100%;
    display: flex;
    flex-flow: column;
    align-items: center;
}

/* 文章盒子 */
.article-box {
    width: 70%;
    margin: 0 auto;
    padding-top: 100px;
    display: flex;
    /* justify-content: center; */
}
/* 文章字体设置 */
.article {
    line-height: 2;
    font-size: 16px;
    color: #4c4948;
    font-family: 'Butterfly Font', BlinkMacSystemFont, 'Segoe UI', 'Helvetica Neue', Lato, Roboto, 'PingFang SC', 'Microsoft JhengHei', 'Microsoft YaHei', sans-serif;
}
.article {
    flex: 1;
    width: 100%;
    background: #fff;
    margin-bottom: 100px;
    border-radius: 5px;
    display: flex;
    justify-content: center;
    margin-right: 30px;
    padding: 20px;
    /* overflow: hidden; */
}

.comment-box {
    width: 70%;
    margin-bottom: 20px;
}
</style>