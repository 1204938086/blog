<template>
  <div class="search-box">
    <div class="search-icon" :class="input_class" @click="chenge">
      <svg t="1647936179961" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2215">
        <path fill="#fff"
              d="M469.333333 768c-166.4 0-298.666667-132.266667-298.666666-298.666667s132.266667-298.666667 298.666666-298.666666 298.666667 132.266667 298.666667 298.666666-132.266667 298.666667-298.666667 298.666667z m0-85.333333c119.466667 0 213.333333-93.866667 213.333334-213.333334s-93.866667-213.333333-213.333334-213.333333-213.333333 93.866667-213.333333 213.333333 93.866667 213.333333 213.333333 213.333334z m251.733334 0l119.466666 119.466666-59.733333 59.733334-119.466667-119.466667 59.733334-59.733333z"
              p-id="2216"></path>
      </svg>
    </div>
    <transition name="search">
      <input type="text" v-model="search_content" class="search-input" @blur="leave" @keydown.enter="chenge" v-show="show" ref="search_input">
    </transition>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import { nextTick } from '@vue/runtime-core';
import { useRouter } from 'vue-router';
export default {
    name: 'SearchInput',
    setup() {
        const router = useRouter();

        let show = ref(false);          // 是否展示搜索框
        let input_class = ref([]);      // 搜索输入框的样式
        let search_content = ref('');   // 搜索内容
        let search_input = ref(null);   // 搜索框对象
        /**
         * 点击事件
         */
        function chenge() {
            if (show.value === false) {
                // 设置样式
                input_class.value = ['search-out'];
                // 光标定位
                nextTick(() => {
                    search_input.value.focus();
                });
                show.value = true;
            } else {
                search_content.value = search_content.value.replaceAll("\*","");
                // console.log(search_content.value);
                router.push({path:"/search",query:{titleOrContent:search_content.value}});
                search_content.value = "";
                leave();
            }
        }
        /**
         * 光标离开事件
         */
        function leave() {
            if (search_content.value === '') {
                input_class.value = ['search-in'];
                show.value = false;
            }
        }
        return { show, input_class, chenge, search_content, ref, search_input, leave };
    },
};
</script>

<style scoped>
.search-box {
    display: flex;
}
.icon {
    height: 100%;
    /* fill: #fff; */
}
.search-icon {
    /* border: 1px solid red; */
    background-color: #666;
    box-shadow: 5px 2px 10px #666;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
    transition: all 0.5s linear;
    transform: translateX(0);
}
.search-input {
    /* display: none; */
    /* border: none; */
    width: 200px;
    border-radius: 25px;
    padding-left: 20px;
}
.search-input:focus-visible {
    outline: none;
}
.search-in {
    transform: translateX(0);
    z-index: 999;
}
.search-out {
    transform: translateX(calc(100% + 156px));
    z-index: 999;
}

@keyframes in {
    from {
        /* transform: translateX(-100%); */
        opacity: 0;
        background-color: transparent;
    }
    to {
        /* transform: translateX(0); */
        opacity: 1;
        background-color: #f1f2f3;
    }
}
.search-enter-active {
    animation: in 0.5s linear;
}
.search-leave-active {
    animation: in 0.5s linear reverse;
}
</style>