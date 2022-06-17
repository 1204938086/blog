<template>
  <div class="top-tools">
    <el-input v-model="type" placeholder="请输入分类"></el-input>
    <el-button @click="save">创建分类</el-button>
  </div>
  <el-table :data="showData" row-key="id" max-height="705" style="width: 100%;">
    <el-table-column prop="type" label="分类">
      <template #default="scope">
        <el-tag>
          {{scope.row.type}}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="120">
      <template #default="scope">
        
        <el-popconfirm confirm-button-text="确定" cancel-button-text="取消" title="你确定要删除吗?" @confirm="remove(scope.row.type)">
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
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import { computed, ref } from 'vue';
export default {
    name: 'TypeManger',
    setup() {
        let type = ref('');

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
            request.post('/admin/selectalltype').then((res) => {
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
            if (type.value.length === 0) {
                ElMessage.error('请输入类型');
                return false;
            }
            return true;
        }
        /**
         * 保存事件
         */
        function save() {
            if (check()) {
                request.post('/admin/createtype', { type: type.value }).then((res) => {
                    if (res.data.code === 200) {
                        type.value = '';
                        load();
                        ElMessage.success('创建成功');
                    } else if (res.data.code === 542) {
                        type.value = '';
                        ElMessage.error('类型已经存在');
                    }
                });
            }
        }
        /**
         * 修改后删除本地数据
         */
        function remove_localtion(type) {
            const temp = tableData.value.filter((obj) => {
                console.log(type, obj, obj.type !== type);

                return obj.type !== type;
            });
            // 替换表格记录
            tableData.value = temp;
            // 更新记录总数
            total.value = tableData.value.length;
        }
        /**
         * 删除事件
         */
        function remove(type) {
            request.post('/admin/removetype', { type }).then((res) => {
                if (res.data.code === 200) {
                    remove_localtion(type);
                    ElMessage.success('删除成功');
                } else {
                    ElMessage.error('删除失败');
                }
            });
        }
        return { type, save, tableData, total, currentPage, pagesize, showData, handleCurrentChange, handleSizeChange, remove };
    },
};
</script>

<style>
</style>