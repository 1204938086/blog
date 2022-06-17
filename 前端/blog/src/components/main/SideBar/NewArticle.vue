<template>
  <div class="toparticle-box">
      <div class="sidebar-title">最新文章</div>
      <ul>
          <li v-for="t in NewArticle" :key="t.id">
              <!-- <span>{{t.title}}</span> -->
              <span class="link-bottom"></span>
              <router-link class="link" :to="{name:'article',query:{id:t.id}}"  :key="t.id">{{t.title}}</router-link>
              <i></i>
          </li>
      </ul>
  </div>
</template>

<script>
import axios from "axios";
export default {
    name: 'NewArticle',
    data(){
        return{
            NewArticle:[]
        }
    },
    mounted(){
        axios.get("http://localhost/article/new").then((res)=>{
            this.NewArticle = res.data;
        })
    }
};
</script>

<style scoped>
.toparticle-box {
    width: calc(100% - 20px);
    height: 300px;
    min-width: 300px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    margin-bottom: 30px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    
}
.sidebar-title::before{
    font-family: iconfont;
    content: "\e600";
    color: red;
    font-size: 25px;
    margin-right: 10px;
}
.sidebar-title{
    font-size: 20px;
    font-weight: bold;
    color: red;
    margin-left: 10px;
    padding: 10px;
    border-bottom: #e5dbdb 1px solid;
    flex: 1;
}
ul{
    flex: 5;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}
ul li {
    list-style: none;
    color: #606266;
    font-size: 16px;
    font-weight: bold;
    display: flex;
    justify-content: space-between;
    /* border-bottom: 1px #00000000 solid; */
}
ul li i::before{
    font-family: iconfont;
    content: "\e601";

}
ul li:hover a,ul li:hover i{
    color: #2196f3;
}
ul li:hover a,ul li:hover i{
    color: #2196f3;
}

.link-bottom{
    /* float: left; */
    position: relative;
    bottom: 1px;
    border: 2px solid red;
}
.link-frist{
    position: relative;
    bottom: 1px;
    border: 2px solid red;
    border-radius: 1px;
}
.link,i{
    border: none;
    transition: all 1s;
}
.link{
    border: 2px solid transparent;

}
.link:hover {
  border-bottom-color: #fb6c28;
}
</style>