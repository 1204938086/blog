<template>
  <div class="sidebar-box">
      <self-info></self-info>
      <top-article></top-article>
      <new-article></new-article>
  </div>
</template>

<script>
import SelfInfo from "@/components/main/SideBar/SelfInfo.vue"
import TopArticle from "@/components/main/SideBar/TopArticle.vue"
import NewArticle from "@/components/main/SideBar/NewArticle.vue"
export default {
    name:"SideBar",
    components:{
        SelfInfo,
        TopArticle,
        NewArticle
    }
}
</script>

<style scoped>
.sidebar-box {
  float: left;
  /* width: 30%; */
  max-width: 300px;
}

</style>