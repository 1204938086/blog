<template>
  <el-table :data="tableData" style="width: 100%" max-height="720" row-key="id" :expand-row-keys="expands" @expand-change="expeandChange">
    <el-table-column type="expand">
      <template #default="props">
        <el-table :data="props.row.comment" style="width: 100%" empty-text="暂时没有评论">
          <el-table-column prop="commentContent" label="评论内容" />
          <el-table-column prop="nickname" label="昵称" width="180" />
          <el-table-column prop="email" label="邮箱" width="180" />
          <el-table-column label="创建时间" width="180">
            <template #default="scope">
              {{formatDate(scope.row.createTime)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-popconfirm title="你确定要删除这条评论吗？" confirmButtonText="确定" cancelButtonText="取消" @confirm="removeComment(props.row.id,scope.row.id)">
                <template #reference>
                  <el-button type="text">删除</el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

      </template>
    </el-table-column>
    <el-table-column label="文章名称" prop="title" />
  </el-table>
</template>

<script>
import { ref } from '@vue/reactivity';
import request from '@/utils/request';
import moment from 'moment';
import { ElMessage } from 'element-plus';
export default {
    setup() {
        let tableData = ref([]); // 文章数据
        let expands = ref([]);
        // 获取文章数据
        request.post('/admin/allarticle').then((res) => {
            tableData.value = res.data;
        });

        /**
         * 展开列表
         */
        function expeandChange(row){
          let index = expands.value.indexOf(row.id);
                if (index < 0) {
                    expands.value.push(row.id);
                } else {
                    expands.value.splice(index, 1);
                }
        }

        /**
         * 格式化日期
         */
        function formatDate(time) {
            return moment(time).format('YYYY-MM-DD HH:mm:ss');
        }

        /**
         * 删除本地数据
         */
        function delectLocalData(commentId) {
            for (let i = 0; i < tableData.value.length; i++) {
                if (tableData.value[i].comment) {
                    const temp = tableData.value[i].comment.filter((comment) => {
                        return comment.id !== commentId;
                    });
                    tableData.value[i].comment = temp;
                }
            }
        }

        /**
         * 删除评论
         */
        function removeComment(articleId, commentId) {
            // console.log(articleId, commentId);
            // delectLocalData(commentId);
            request.post("/admin/removecomment",{articleId,commentId}).then(res=>{
              if(res.data.code === 200){
                ElMessage.success("删除成功");
                delectLocalData(commentId);
              }else{
                ElMessage.error("删除失败");
              }
            })
        }
        return { tableData, formatDate, removeComment,expands,expeandChange };
    },
};
</script>

<style>
</style>
