<template>
  <div class="main">
    <div class="type-box">
      <router-link class="type" :style="randomColor()" v-for="t in tags" :key="t.tag" :to="{path:'search',query:{tag:t.tag}}">
        {{t.tag}}
      </router-link>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import Request from '@/utils/request';
export default {
    name: 'Tags',
    setup() {
        // 所有分类
        let tags = ref([]);
        Request.get('/article/alltag').then((res) => {
            tags.value = res.data;
        });
        
        /**
         * 随机标签颜色
         */
        function randomColor() {
            const color = ['#96ceb4', '#ffeead', '#d9534f', '#ffad60',"#de4307","#f29c2b","#f6d04d","#8bc24c"];
            const index = Number.parseInt(Math.random() * color.length);
            return 'background-color:' + color[index] + ';';
        }
        return { tags, randomColor };
    },
};
</script>

<style scoped>
.main {
    margin-top: 100px;
    width: 100%;
    height: calc(100vh - 100px);
    display: flex;
    justify-content: center;
}
.main {
    margin-top: 100px;
    width: 100%;
    height: calc(100vh - 100px);
    /* background-color: #000; */
    display: flex;
    justify-content: center;
}
.type-box {
    /* 盒子的样式 */
    background-color: #fff;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    width: 70%;
    height: 90%;

    /* 控制子容器布局方式 */
    display: flex;
    justify-content: flex-start;
    align-content: flex-start;
    flex-wrap: wrap;
    padding: 20px;
}
/* 分类标签样式 */
.type {
    width: 10vw;
    height: 10vh;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    background-color: #e9f2ff;
    border: 1px solid #e4e7ed;
    margin: 10px;

    /* 内部对其方式 */
    display: flex;
    justify-content: center;
    align-items: center;

    /* 字体大小 */
    font-size: 1.3em;

    /* 过渡效果 */
    transition: all 1s;

    border: 1px solid transparent;
}

</style>