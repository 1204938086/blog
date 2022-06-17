<template>
  <div class="comment-main-box">
    <div class="comment-write">
      <div class="comment-information-input">
        <input type="text" :placeholder="nicknamePlaceholder" class="comment-input" :style="nicknameErrStyle" v-model="nickname" @focus="clearNikenameErr">
        <input type="text" :placeholder="emailPlaceholder" class="comment-input" :style="emailErrStyle" v-model="email" @focus="clearEmailErr">
        <button class="comment-submit" @click="submitComment" :disabled="submitable">{{submitResult}}</button>
      </div>
      <textarea class="comment-content-input" v-model="commentContent" :style="contentErrStyle" :placeholder="contentPlaceholder" @focus="clearContentErr"></textarea>
    </div>
    <hr class="separator">
    <div class="comment-show">
      <div class="comment-item" v-for="comment in comments" :key="comment">
        <div class="comment-information-show">
          <div class="comment-nickname">{{comment.nickname}}</div>
          <div class="comment-time">{{formatTime(comment.createTime)}}</div>
        </div>
        <div class="comment-content-show">
          {{comment.commentContent}}
        </div>
      </div>
      <div v-if="!comments || comments.length === 0" style="text-align:center;">
        暂时没有评论
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import request from '@/utils/request';
import { useRoute } from 'vue-router';
import moment from 'moment';

export default {
    props: {
        comments: Array,
    },
    setup(props) {
        const $route = useRoute();
        const id = $route.query.id;

        let nickname = ref(''); // 昵称
        let email = ref(''); // 邮箱地址
        let commentContent = ref(''); // 评论内容
        let nicknameErrStyle = ref(''); // 昵称错误样式
        let emailErrStyle = ref(''); // 邮箱错误样式
        let contentErrStyle = ref(''); // 评论内容错误样式
        let nicknamePlaceholder = ref('昵称 *');
        let emailPlaceholder = ref('邮箱 *');
        let contentPlaceholder = ref('请输入评论内容');
        let submitResult = ref('评论');
        let submitable = ref(false);                     // 是否禁用评论按钮

        function formatTime(time) {
            return moment(time).format('YYYY-MM-DD HH:mm:ss');
        }

        /**
         * 清除昵称错误样式
         */
        function clearNikenameErr() {
            nicknameErrStyle.value = '';
            nicknamePlaceholder.value = '昵称 *';
        }
        /**
         * 清除邮箱格式错误样式
         */
        function clearEmailErr() {
            emailErrStyle.value = '';
            emailPlaceholder.value = '邮箱 *';
        }
        /**
         * 清除评论内容错误样式
         */
        function clearContentErr() {
            contentErrStyle.value = '';
            contentPlaceholder.value = '请输入评论内容';
        }

        /**
         * 提交评论检查
         */
        function submitCommentCheck() {
            if (nickname.value.trim() === '') {
                nicknameErrStyle.value = 'border-bottom:1px red solid;';
                nicknamePlaceholder.value = '昵称不能为空';
                return false;
            }
            if (email.value.indexOf('@') === -1 || email.value.indexOf('.') === -1) {
                emailErrStyle.value = 'border-bottom:1px red solid;';
                emailPlaceholder.value = '邮箱格式错误';
                return false;
            }
            if (commentContent.value.trim() === '') {
                contentErrStyle.value = 'border:1px red solid;';

                contentPlaceholder.value = '评论内容不能为空！';
                return false;
            }
            return true;
        }
        /**
         * 提交评论
         */
        function submitComment() {
            // 禁用提交按钮
            submitable.value = true;
            if (submitCommentCheck()) {
                let comment = { nickname: nickname.value, email: email.value, commentContent: commentContent.value, createTime: new Date() }
                request.post('/article/createcomment/' + id, comment).then((res) => {
                    if (res.data.code === 200) {
                        submitResult.value = '评论成功';
                        props.comments.push(comment)
                        setInterval(()=>{
                            submitable.value = false;
                            submitResult.value = "提交";
                        },5000);
                    } else {
                        submitResult.value = '评论失败';
                        submitable.value = false;
                    }
                });
            }else{
                submitable.value = false;
            }
            
        }
        return { nickname, email, submitComment, commentContent, nicknameErrStyle, emailErrStyle, clearNikenameErr, contentPlaceholder, clearContentErr, nicknamePlaceholder, emailPlaceholder, clearEmailErr, contentErrStyle, formatTime, submitResult ,submitable};
    },
};
</script>

<style scoped>
/* 评论盒子 */
.comment-main-box {
    width: 100%;
    /* height: 300px; */
    background-color: #fff;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
}

/* 评论输入盒子 */
.comment-write {
    width: calc(100% - 40px);
    padding: 20px;
}
/* 评论信息输入盒子 */
.comment-information-input {
    padding: 10px;
}
/* 清除所有输入框外线 */
*:focus-visible {
    outline: none;
}
/* 评论内容输入况 */
.comment-content-input {
    width: calc(100% - 20px);
    height: 200px;
    /* 不允许自定义大小 */
    resize: none;
    padding: 10px;
}

/* 评论信息输入框 */
.comment-input {
    border: none;
    height: 30px;
    font-size: 18px;
    border-bottom: 1px solid wheat;
    margin: 10px;
}

/* 评论提交按钮 */
.comment-submit {
    width: 60px;
    height: 30px;
    color: #fff;
    /* font-size: 18px; */
    background-color: #666;
    border: 1px solid transparent;
    border-radius: 5px;
    cursor: pointer;
}
/* 分割线 */
.separator {
    width: 90%;
    margin: 0 auto;
    text-align: center;
}

/* 评论展示盒子 */
.comment-show {
    width: calc(100% - 40px);
    padding: 20px;
}

/* 评论项 */
.comment-item {
    width: 100%;
    border-bottom: 1px #000 solid;
    margin-top: 10px;
}

/* 评论者信息展示盒子 */
.comment-information-show {
    display: flex;
    justify-content: space-between;
    margin: 10px;
}
/* 昵称 */
.comment-nickname {
    font-size: 24px;
    font-weight: 700;
    color: #111;
}
/* 评论时间 */
.comment-time {
}
/* 评论内容 */
.comment-content-show {
    word-wrap: break-word;
    margin: 20px;
    line-height: 1.5;
}
</style>