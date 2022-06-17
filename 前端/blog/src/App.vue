<template>
  <Nav></Nav>
  <div class="router">
    <router-view v-slot="{ Component }" >
      <keep-alive>
        <component :is="Component" v-if="$route.meta.keepAlive" :key="$route.fullPath"/>
      </keep-alive>
      <component :is="Component" v-if="!$route.meta.keepAlive" :key="$route.fullPath"/>
    </router-view>
  </div>

  <go-top></go-top>
</template>

<script>
import '@/assets/fonts/fonts.css';
import GoTop from '@/components/GoTop.vue';
import Nav from '@/components/Nav.vue';
export default {
    name: 'App',
    components: {
        'go-top': GoTop,
        Nav,
    },
};
</script>

<style>
* {
    padding: 0;
    margin: 0;
}
body {
    background-color: rgb(235, 234, 234);
    /* 盒子阴影颜色 */
    --box-shadow: 0 1px 8px 0 rgba(0, 0, 0, 0.1);
}
.router {
    display: flex;
    justify-content: center;
}
a:link,
a:visited {
    text-decoration: none;
    color: #000;
}
</style>
