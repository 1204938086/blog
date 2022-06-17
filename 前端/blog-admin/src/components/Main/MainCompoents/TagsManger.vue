<template>
  <div class="top-tools">
    <el-input v-model="tag" placeholder="请输入标签"></el-input>
    <el-button @click="save">创建标签</el-button>
  </div>
  <el-table :data="showData" row-key="id" max-height="705" style="width: 100%;">
    <el-table-column prop="tag" label="标签">
      <template #default="scope">
        <el-tag>
          {{scope.row.tag}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" title="你确定要删除吗?" @confirm="remove(scope.row.tag)">
          <template #reference>
            <el-button type="text">删除</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <div class="bottom-tools">
    <el-pagination layout="prev, pager, next,jumper,sizes" @size-change="handleSizeChange" @current-change="handleCurrentChange" :total="total"></el-pagination>
  </div>
</template>

<script>
import { computed, ref } from 'vue';
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
export default {
    setup() {
        let tag = ref('');

        // ===================== 初始化 ============================
        load();

        // 计算属性
        let showData = computed(() => {
            return tableData.value.slice((currentPage.value - 1) * pagesize.value, currentPage.value * pagesize.value);
        });

        let tableData = ref([]); // 表格数据
        let total = ref(0); // 分页长度
        let currentPage = ref(1); // 当前分页
        let pagesize = ref(10); // 单页大小
        // ============== 方法 ====================
        function load() {
            request.post('/admin/selectalltag').then((res) => {
                tableData.value = res.data;
                total.value = tableData.value.length;
            });
        }
        // ============== 事件 =====================
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
         * 保存前检查
         */
        function check() {
            if (tag.value.length === 0) {
                ElMessage.error('请输入标签');
                return false;
            }
            return true;
        }
        /**
         * 保存事件
         */
        function save() {
            if (check()) {
                request.post('/admin/createtag', { tag: tag.value }).then((res) => {
                    if (res.data.code === 200) {
                        tag.value = '';
                        load();
                        ElMessage.success('创建成功');
                    } else if (res.data.code === 532) {
                        tag.value = '';
                        ElMessage.error('标签已经存在');
                    }
                });
            }
        }
        /**
         * 修改后删除本地数据
         */
        function remove_localtion(tag) {
            const temp = tableData.value.filter((obj) => {
                return obj.tag !== tag;
            });
            // 替换表格记录
            tableData.value = temp;
            // 更新记录总数
            total.value = tableData.value.length;
        }
        /**
         * 删除事件
         */
        function remove(tag) {
            request.post('/admin/removetag', { tag }).then((res) => {
                if (res.data.code === 200) {
                    remove_localtion(tag);
                    ElMessage.success('删除成功');
                } else {
                    ElMessage.error('删除失败');
                }
            });
        }
        return { tag, save, tableData, total, currentPage, pagesize, showData, handleCurrentChange, handleSizeChange, remove };
    },
};
</script>

<style>
.top-tools {
    display: flex;
}
</style>