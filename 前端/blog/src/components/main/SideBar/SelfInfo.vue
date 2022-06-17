<template>
  <div class="selfinfo-box">
    <span class="blogname">{{ BlogName }}</span>
    <span class="catchphrase">{{ Catchphrase }}</span>
    <div class="contact-box">
      <a v-if="contact.qq" class="qq icon" @click="CallQQ" :href='qqhref' target="_blank" ></a>
      <a v-if="contact.email" class="email icon" :href='emailhref'  target="_blank"></a>
      <a v-if="contact.github" class="github icon" :href='githubhref' target="_blank"></a>
      <a v-if="contact.bilibili" class="bilibili icon" :href='bilibilihref'  target="_blank"></a>
    </div>
  </div>
</template>

<script>
export default {
    name: 'SelfInfo',
    data() {
        return {
            BlogName: '编程杂记',
            Catchphrase: '乾坤未定，你我皆是黑马',
            contact: {
                qq: '123456',
                email: '123456@qq.com',
                github: '123456',
                bilibili: '123456',
            },
        };
    },
    computed:{
        qqhref(){
            return 'https://wpa.qq.com/msgrd?v=3&uin=' + this.contact.qq + '&site=qq&menu=yes';
        },
        emailhref(){
            return 'mailto:' + this.contact.email
        },
        githubhref(){
            return 'https://github.com/' + this.contact.github
        },
        bilibilihref(){
            return "https://space.bilibili.com/"+this.contact.bilibili
        }
    }
};
</script>

<style scoped>
.selfinfo-box {
    width: 300px;
    height: 300px;
    min-width: 300px;
    padding: 10px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: var(--box-shadow);
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
    margin-bottom: 30px;
}
.blogname {
    font-size: 50px;
    font-weight: bolder;
    font-family: sanjixing;
}
.catchphrase {
    color: #fb6c28;
    font-size: 20px;
    font-weight: bolder;
    font-family: shouxiezashu;
    margin: 20px;
}
.contact-box {
    font-family: 'iconfont' !important;
    width: 100%;
    font-size: 30px;
    display: flex;
    justify-content: center;
}
a:link,a:active,a:visited{
    color: black;
    text-decoration: none;

}
.qq::before {
    content: '\e882';
}
.email::before {
    content: '\e77e';
}
.github::before {
    content: '\e885';
}
.bilibili::before {
    content: '\e69e';
}
.icon {
    margin: 20px;
}
</style>