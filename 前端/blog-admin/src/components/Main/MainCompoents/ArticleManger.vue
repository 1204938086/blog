<template>
  <div class="box">
    <!-- <div class="top-tools">
      <el-input></el-input>
    </div> -->
    <el-table :data="showData" row-key="id" max-height="705" style="width: 100%;">
      <el-table-column prop="title" label="标题" width="180" />
      <el-table-column prop="type" label="分类" width="180" />
      <el-table-column prop="tag" label="标签">
        <template #default="scope">
          <el-tag v-for="t in scope.row.tag" :key="t">
            {{t}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="hit" label="点击量" width="180" />
      <el-table-column prop="createDate" label="创建时间">
        <template #default="scope">
          {{formatTime(scope.row.createDate)}}
        </template>
      </el-table-column>
      <el-table-column prop="modifyDate" label="修改时间">
        <template #default="scope">
          {{formatTime(scope.row.modifyDate)}}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template #default="scope">
          <el-button type="text" size="small" @click="modify(scope.row.id)">修改</el-button>
          <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" title="你确定要删除吗?" @confirm="remove(scope.row.id)">
            <template #reference>
              <el-button type="text" size="small">删除</el-button>
            </template>
          </el-popconfirm>

        </template>
      </el-table-column>
    </el-table>
    <div class="bottom-tools">
      <el-pagination layout="prev, pager, next,jumper,sizes" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total="total"></el-pagination>
    </div>
  </div>

</template>

<script>
import request from '@/utils/request';
import { computed, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
import moment from 'moment';
import { Fancybox } from "@fancyapps/ui";           // 图片全屏查看组件
import "@fancyapps/ui/dist/fancybox.css";           // 图片全屏查看样式
export default {
    name: 'ArticleManger',
    setup() {
        // ============== 初始化设置 ===============
        const $router = useRouter();
        /**
         * 获取文章数据
         */
        request.post('/admin/allarticle').then((res) => {
            tableData.value = res.data;
            total.value = tableData.value.length;
        });


        //#region ============== 数据对象 =================
        let tableData = ref([]); // 表格数据
        let total = ref(0); // 分页长度
        let currentPage = ref(1); // 当前分页
        let pagesize = ref(10); // 单页大小
        //#endregion

        //#region ============== 计算属性 ==============
        let showData = computed(() => {
            return tableData.value.slice((currentPage.value - 1) * pagesize.value, currentPage.value * pagesize.value);
        });
        //#endregion
        //#region ============== 事件 =====================
        /**
         * 处理分页切换事件
         */
        function handleCurrentChange(curr) {
            currentPage.value = curr;
        }
        /**
         * 处理分页大小改变事件
         */
        function handleSizeChange(size) {
            pagesize.value = size;
        }
        /**
         * 处理修改事件
         */
        function modify(id) {
            console.log(id);
            console.log('修改事件');
            $router.push({ name: 'ModifyArticle', query: { id } });
        }

        /**
         * 在远程删除后修改本地的数据
         */
        function remove_localtion(id) {
            const temp = tableData.value.filter((obj) => {
                // console.log(id, obj, obj.id !== id);
                return obj.id !== id;
            });
            tableData.value = temp;
            // 更新记录总数
            total.value = tableData.value.length;
        }

        /**
         * 删除事件
         */
        function remove(id) {
            console.log(id);
            console.log('删除事件');
            request.post('/admin/removearticle', { id }).then((res) => {
                if (res.data.code === 200) {
                    remove_localtion(id);
                    ElMessage({ showClose: true, message: '删除成功', type: 'success' });
                }
            });
        }
        function formatTime(time) {
            return moment(time).format('YYYY/MM/DD');
        }
        //#endregion

        return { tableData, showData, total, currentPage, pagesize, handleCurrentChange, remove, modify, handleSizeChange, formatTime };
    },
};
</script>

<style>
.box {
    display: flex;
    width: 100%;
    height: 100%;
    flex-flow: column;
}

.top-tools {
    margin-bottom: 20px;
}
.bottom-tools {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
}
.el-tag {
    margin: 5px;
}
</style>