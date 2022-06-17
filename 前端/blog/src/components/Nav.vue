<template>
  <nav>
    <div class="blogname">编程杂记</div>
    <router-link active-class="active" class="nav-link" to="/main">主页</router-link>
    <router-link active-class="active" class="nav-link" to="/type">分类</router-link>
    <router-link active-class="active" class="nav-link" to="/tags">标签</router-link>
    <search-input class="search-input" ></search-input>

  </nav>
</template>

<script>
import SearchInput from "@/components/search/SearchInput.vue"
export default {
  components:{SearchInput}
};
</script>

<style scoped>
nav {
  height: 70px;
  width: 100%;
  background-color: #fff;
  box-shadow: var(--box-shadow);
  display: flex;
  position:fixed;
  top: 0;
  left: 0;
  z-index: 1;
}
.nav-link {
  height: 100%;
  width: 100px;
  line-height: 70px;
  text-align: center;
  transition: color 0.5s,border-color 0.5s;
}
.nav-link:link,
.nav-link:visited,
.nav-link:active {
  color: black;
  text-decoration: none;
}
.nav-link:hover,.active {
  color: rgb(4, 196, 36)!important;
  border-bottom: rgb(4, 196, 36) solid 3px;
}
.blogname {
  width: 12%;
  height: 70px;
  font-size: 50px;
  font-weight: bolder;
  font-family: sanjixing;
  padding-left: 5%;
  padding-right: 5%;
  min-width: 200px;
}

.search-input{
  align-self: center;
  margin-left: 400px;
}

</style>