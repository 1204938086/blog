<template>
  <div class="create-time">
    <clock class="clock" :color="iconColor" />
    {{ createDate }}
  </div>
</template>

<script>
import { Clock } from "@element-plus/icons-vue";
export default {
  name: "CreateDate",
  props: ["createDate"],
  components: {
    Clock,
  },
  data() {
    return {
      iconColor: "#99918d", // SVG 图标通用颜色
    };
  },
};
</script>

<style scoped>
.create-time {
  display: flex;
  align-items: center;
  margin-right: 10px;
}
svg {
  width: 1em;
  height: 1em;
  position: relative;
  top: 0.15em;
}
</style>