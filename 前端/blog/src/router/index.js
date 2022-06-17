import { createRouter,createWebHashHistory} from "vue-router";
import Main from "@/components/main/Main.vue";
import Type from "@/components/type/Type.vue";
import Article from "@/components/article/Article.vue";
import Search from "@/components/search/Search.vue";
import Tags from "@/components/tags/Tags.vue";

// 路由规则
const routes = [
  {path:"/",redirect:"/main", meta: { keepAlive: true }},
  {path:"/main",name:"main",component:Main, meta: { keepAlive: true } },
  {path:"/type",name:"type",component:Type},
  {path:"/article",name:"article",component:Article},
  {path:"/search",name:"search",component:Search},
  {path:"/tags",name:"tags",component:Tags}
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})
// 创建路由对象



// 路由前处理事项
router.beforeEach(()=>{
  // 路由前回到页面顶端
  window.scrollTo(0,0)
})

export {router};