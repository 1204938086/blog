<template>
  <div class="box">
    <el-row class="head-tools">
      <el-col :span="6">
        <el-input class="title-input" v-model="title" placeholder="标题..." ref="title_input" />
      </el-col>
      <el-col :span="2">
        <el-select v-model="type_" placeholder="选择分类">
          <el-option v-for="item in types" :key="item.type" :label="item.type" :value="item.type">
          </el-option>
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="tag" multiple collapse-tags :clearable="true" placeholder="选择标签">
          <el-option v-for="item in tags" :key="item.tag" :label="item.tag" :value="item.tag">
          </el-option>
        </el-select>
      </el-col>
      <!-- <el-col :span="2">

      </el-col> -->
      <el-col :span="6">
        <el-button @click="save" :loading="saveIsLoad">保存</el-button>
      </el-col>
    </el-row>

    <div class="markdown-box">
      <textarea class="input-markdown" v-model="markdown" spellcheck="false" placeholder="开始编辑..."></textarea>
      <!-- <el-input type="textarea" v-model="markdown" placeholder="标题..." /> -->
      <div class="output-markdown" ref="showmarked"></div>
    </div>
  </div>
</template>

<script>
import { marked } from 'marked';
import { ref } from 'vue';
import { onMounted, watch } from '@vue/runtime-core';
import request from '@/utils/request';
import { ElMessageBox } from 'element-plus';
import { useRoute } from 'vue-router';
import render from "@/utils/render";
import "@/assets/css/markdown.css";
import "@/assets/css/prism.css";

export default {
    name: 'ModfiyArticle',
    setup() {
        // =============== 接收参数,初始化数据 ===============
        const $route = useRoute();
        const id = $route.query.id;
        onMounted(() => {
            request.post('/admin/article', { id }).then((res) => {
                if (res.data) {
                    const data = res.data;
                    console.log(data);
                    title.value = data.title;
                    type_.value = data.type;
                    tag.value = data.tag;
                    markdown.value = data.markdown;
                }
            });
        });
        // ================ DOM对象 ===============
        const title_input = ref(null); // 标题输入框
        const showmarked = ref(null); // Markdown展示Div

        // ================ 数据对象 ===============
        let title = ref(''); // 标题对象
        let markdown = ref(''); // 内容对象
        let tag = ref([]); // 标签对象
        let type_ = ref([]); // 分类对象

        // =============== 基础数据 =================
        // 标签
        let tags = ref([]);
        // 分类
        let types = ref([]);

        request.post('/admin/selectalltag').then((res) => {
            if (res.data) {
                tags.value = res.data;
            }
        });
        request.post('/admin/selectalltype').then((res) => {
            if (res.data) {
                types.value = res.data;
            }
        });

        // =============== 监听事件 ==================
        // 检测markdown输入,并且解析
        watch(markdown, () => {
            showmarked.value.innerHTML = marked(markdown.value,render);
        });

        // =============== 状态变量 =============
        let saveIsLoad = ref(false);

        // =============== 事件 ================

        /**
         * 保存前检查
         */
        function check() {
            if (title.value.length === 0) {
                ElMessage({
                    message: '请输入标题',
                    type: 'warning',
                });
                return false;
            } else if (markdown.value.length === 0) {
                ElMessage({
                    message: '请输入内容',
                    type: 'warning',
                });
                return false;
            } else {
                return true;
            }
        }
        /**
         * 保存事件
         */
        function save() {
            if (check()) {
                // 进入loading状态
                saveIsLoad.value = true;
                request
                    .post('/admin/modifyarticle', { id: id, title: title.value, markdown: markdown.value, tag: tag.value, type: type_.value })
                    .then((res) => {
                        if (res.data.code === 200) {
                            ElMessage.success('保存成功');
                        } else {
                            ElMessageBox.alert('保存失败', '提示');
                        }
                        saveIsLoad.value = false;
                    })
                    .catch((err) => {
                        ElMessageBox.alert('保存失败', '提示');
                    });
            }
        }

        return { markdown, showmarked, title, tags, tag, types, type_, title_input, save, saveIsLoad };
    },
};
</script>

<style scoped>
.box {
    display: flex;
    /* 上下结构 */
    flex-flow: column;
    height: 100%;
}

/* 设置顶部工具栏 */
.head-tools {
    margin-bottom: 10px;
}
.head-tools .el-col {
    margin-right: 20px;
}

/* markdown输入展示区域,整体 */
.markdown-box {
    display: flex;
    height: 100%;
}
/* markdown输入区域 */
.input-markdown {
    flex: 1;
    height: 80%;
    font-size: 15px;
    line-height: 1.5;
    resize: none;
    border: none;
    padding: 20px;
}
.input-markdown:focus-visible {
    resize: none;
    outline: none;
    border: none;
}
/* markdown展示 */
.output-markdown {
    flex: 1;
    height: 80%;
    background-color: rgb(251, 251, 251);
    overflow: auto;
    padding: 20px;
}
</style>