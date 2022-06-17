import { createRouter, createWebHashHistory } from "vue-router";
import Main from "@/components/Main/Main.vue";
import Login from "@/components/Login/Login.vue";
import DashBoard from "@/components/Main/MainCompoents/DashBoard.vue";
import CreateArticle from "@/components/Main/MainCompoents/CreateArticle.vue";
import ArticleManger from "@/components/Main/MainCompoents/ArticleManger.vue";
import ModifyArticle from "@/components/Main/MainCompoents/ModifyArticle.vue";
import TypeManger from "@/components/Main/MainCompoents/TypeManger.vue";
import TagsManger from "@/components/Main/MainCompoents/TagsManger.vue";
import CommentManger from "@/components/Main/MainCompoents/CommentManger.vue"

const routes = [
  { path: "/", redirect: "/main/dashboard" },
  {
    path: "/main",
    name: "main",
    component: Main,
    children: [
      { path: "dashboard", component: DashBoard },
      { path: "createarticle", component: CreateArticle, meta: { keepAlive: true } },
      { path: "articlemanger", component: ArticleManger },
      { path: "modifyarticle", name: "ModifyArticle", component: ModifyArticle },
      { path: "typemanger", name: "TypeManger", component: TypeManger },
      { path: "tagsmanger", name: "TagsManger", component: TagsManger },
      { path: "commentmanger", name: "CommentManger", component: CommentManger },

    ]
  },
  { path: "/login", name: "login", component: Login },
]


const router = createRouter({
  history: createWebHashHistory(),
  routes: routes
})


// 路由前处理事项
router.beforeEach(() => {
  // 路由前回到页面顶端
  window.scrollTo(0, 0)
})

export { router };