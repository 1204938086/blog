<template>
    <transition>
        <div id="gotop" v-show="goTopShow" @click="goTopClick">
        <i><caret-top class="top" /></i>
        
        </div>
        
    </transition>

</template>

<script>
import {CaretTop} from "@element-plus/icons-vue";
import { ref } from '@vue/reactivity';
export default {
    name:"GoTop",
    setup(){
        let goTopShow = ref(false); // 决定是否显示的标志

        /**
         * 回到顶部方法
         */
        function goTopClick(){
            document.body.scrollTop = 0;
            document.documentElement.scrollTop = 0;
            goTopShow.value = false; 
        }
        
        return {goTopClick,goTopShow}
    },
    methods:{
        /**
         * 是否显示回到顶部按钮的事件
         * 在setup中定义该方法会出现获取不到vue this的情况
         */
        needToTop(){
            let curHeight = document.body.scrollTop || document.documentElement.scrollTop;
            if(curHeight >= 400){
                this.goTopShow = true;
            }else{
                this.goTopShow = false;
            }
        }
    },
    components:{
        CaretTop
    },
    mounted(){
        /**
         * 下一次渲染时将 滚动事件进行绑定
         */
        this.$nextTick(function(){
            window.addEventListener("scroll",this.needToTop);
        })
    }
}
</script>

<style>
 #gotop {
     width: 40px;
     height: 40px;
     background-color: #fff;
     border-radius: 50%;
     position:fixed;
     bottom: 100px;
     right: 100px;
     box-shadow: rgb(202, 202, 207) 1px 1px;
     z-index: 5;
 }
 .top{
     position: relative;
     width: 30px; 
     height: 30px; 
     /* margin-right: 8px; */
     color: cornflowerblue;
     top: 5px;
     left: 5px;

 }
</style>